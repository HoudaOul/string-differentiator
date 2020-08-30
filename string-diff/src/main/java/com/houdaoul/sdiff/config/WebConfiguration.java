package com.houdaoul.sdiff.config;

import com.houdaoul.sdiff.api.RateLimiterInterceptor;
import io.github.bucket4j.Bucket;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

	private final Bucket bucket;

	public WebConfiguration(Bucket bucket) {
		this.bucket = bucket;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new RateLimiterInterceptor(bucket)).addPathPatterns("/diff/**");
	}
}
