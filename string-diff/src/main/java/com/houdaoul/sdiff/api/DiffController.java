package com.houdaoul.sdiff.api;

import com.houdaoul.sdiff.model.DiffRequest;
import com.houdaoul.sdiff.service.Differentiator;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * @author houlachguer
 *
 */
@Log4j2
@RestController
@CrossOrigin
public class DiffController {

	private final Differentiator differentiator;

	public DiffController(Differentiator differentiator) {
		this.differentiator = differentiator;
	}

	@PostMapping(value = "/diff", consumes = "application/json")
	public ResponseEntity<?> computeDiff(@RequestBody @Validated DiffRequest request) {
		log.info("Processing difference request: " + request);
		return ResponseEntity.ok(differentiator.computeStringDiff(request));
	}
}
