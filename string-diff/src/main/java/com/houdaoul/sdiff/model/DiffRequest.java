package com.houdaoul.sdiff.model;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 
 * @author houlachguer
 *
 */
@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiffRequest {

	@NotNull
	private String first;

	@NotNull
	private String second;
}
