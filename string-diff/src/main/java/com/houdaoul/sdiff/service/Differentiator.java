package com.houdaoul.sdiff.service;

import com.houdaoul.sdiff.model.DiffRequest;
import com.houdaoul.sdiff.model.DiffResult;
import org.springframework.stereotype.Service;

/**
 * Service permitting to compute the difference between two given strings
 * represented by {@link DiffRequest} object.
 * 
 * @author houlachguer
 */
@Service
public interface Differentiator {

	/**
	 * Compute The difference between the strings given in the request parameter.
	 *
	 * The method assumes that the request is validated, i.e no extra validation is
	 * going to be performed. The assumption is that none of the strings are
	 * {@code null}.
	 *
	 * @param request The target strings
	 * @return a new instance of {@link DiffResult}. If new difference can be found,
	 *         a diff result with an empty string is returned.
	 */
	DiffResult computeStringDiff(DiffRequest request);
}
