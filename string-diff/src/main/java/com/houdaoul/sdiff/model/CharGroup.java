package com.houdaoul.sdiff.model;

import java.util.Map.Entry;
import lombok.Data;

/**
 * 
 * @author houlachguer
 *
 */
@Data
public class CharGroup implements Comparable<CharGroup> {

	private final char c;
	private final long count;
	private final char source;
	// This will be computed lazily
	// Note: This is not thread-safe.
	private String cachedToString = null;

	public CharGroup(char c, long count, char source) {
		this.c = c;
		this.count = count;
		this.source = source;
	}

	public static CharGroup create(Entry<Character, Long> entry, char source) {
		return new CharGroup(entry.getKey(), entry.getValue(), source);
	}

	public int compareTo(CharGroup o) {
		int c = Long.compare(o.count, count);
		if (c == 0) {
			return toString().compareTo(o.toString());
		}
		return c;
	}

	public String toString() {
		return cachedToString == null ? (cachedToString = String.format("%c:%s", source, repeated(c, (int) count)))
				: cachedToString;
	}

	private static String repeated(char c, int count) {
		StringBuilder sb = new StringBuilder(count);
		for (int i = 0; i < count; ++i) {
			sb.append(c);
		}
		return sb.toString();
	}
}
