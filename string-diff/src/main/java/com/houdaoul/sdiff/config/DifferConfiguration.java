package com.houdaoul.sdiff.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import java.time.Duration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Central location for the configuration of the string-diff application.
 * <ul>
 *  <li>Caffeine library for an LRU in-memory cache.</li>
 *  <li>Bucket4j library for rate limiting requests.</li>
 * </ul>
 * Cache: No provider has been configured, We used the s
 */
@Configuration
@EnableCaching
public class DifferConfiguration {

  /**
   * Configures a new {@link Caffeine} instance.
   *
   * The maximum size is chosen under these assumptions:
   * <ul>
   *   <li>Most strings in requests will be at most 100 character long</li>
   * </ul>
   * If this holds, then we can say that once object instance be roughly 500 bytes which means our
   * cache will consume roughly <b>500M</b> before it starts to release stuff from the cache, under normal
   * JVM configuration this is a reasonable cache size, but it should be monitored an probably
   * altered depending on the allowed memory and the cache hit/miss times.
   */
  @Bean
  public Caffeine caffeineConfiguration() {
    return Caffeine.newBuilder()
        .maximumSize(1_000_000);
  }

  /**
   * Configures a new {@link CacheManager} based on the caffeine instance configured above.
   *
   * @param caffeine The configured {@link Caffeine} instance.
   */
  @Bean
  public CacheManager cacheManagerConfiguration(Caffeine caffeine) {
    CaffeineCacheManager manager = new CaffeineCacheManager();
    manager.setCaffeine(caffeine);
    return manager;
  }

  /**
   * Configures and new {@link Bucket}
   *
   * The bucket configuration will be used as a rate limiter, 100 request per second seems reasonable
   * for a service that does consume so much CPU and highly cacheable.
   *
   * The lease will be refilled after each second has passed and more requests than what's
   * configured will lead to {@code 429 TOO_MANY_REQUESTS} failure.
   */
  @Bean
  public Bucket bucketConfiguration() {
    return Bucket4j.builder()
        .addLimit(Bandwidth.classic(100, Refill.intervally(100, Duration.ofSeconds(1))))
        .build();
  }
}
