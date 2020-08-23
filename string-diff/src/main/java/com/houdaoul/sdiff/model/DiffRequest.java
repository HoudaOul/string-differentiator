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

	public String getFirst() {
		return first;
	}

	public void setFirst(String first) {
		this.first = first;
	}

	public String getSecond() {
		return second;
	}

	public void setSecond(String second) {
		this.second = second;
	}
}
