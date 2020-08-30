package com.houdaoul.sdiff.api;

import io.github.bucket4j.Bucket;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * Block requests from getting processed if the threshold configured is
 * exceeded.
 */
public class RateLimiterInterceptor implements HandlerInterceptor {

	private final Bucket bucket;
	private static final Logger logger = LoggerFactory.getLogger(RateLimiterInterceptor.class.getName());

	public RateLimiterInterceptor(Bucket bucket) {
		this.bucket = bucket;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (!bucket.tryConsume(1)) {
			logger.error("Blocked processing requests");
			response.sendError(HttpStatus.TOO_MANY_REQUESTS.value());
			return false;
		}
		return true;
	}
}
