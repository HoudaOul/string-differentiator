package com.houdaoul.sdiff.service;

import com.houdaoul.sdiff.model.CharGroup;
import com.houdaoul.sdiff.model.DiffRequest;
import com.houdaoul.sdiff.model.DiffResult;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

/**
 * An frequency based differentiator.
 *
 * This implementation will use the frequency of <b>Lower case</b> characters of
 * the strings as the differentiator.
 *
 * Equality will be tie-breaked using lexicographical order.
 * 
 * @author houlachguer
 */
@Service
public class FrequencyDifferentiator implements Differentiator {

	/**
	 * Utility method to return the mapping between each lowercase character and
	 * its number of occurrences.
	 *
	 * @param s
	 *            the target string
	 * @return A new {@link Map<Character, Long>} denoting the frequency map
	 *         obtained.
	 */
	static Map<Character, Long> getFrequencyMap(String s) {
		return s.chars().mapToObj(c -> Character.valueOf((char) c)).filter(Character::isLowerCase)
				.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
	}

	@Override
	public DiffResult computeStringDiff(DiffRequest request) {
		Map<Character, Long> freq1 = getFrequencyMap(request.getFirst());
		Map<Character, Long> freq2 = getFrequencyMap(request.getSecond());
		List<CharGroup> res = new ArrayList<>();
		for (char c = 'a'; c <= 'z'; ++c) {
			boolean inS1 = freq1.containsKey(c);
			boolean inS2 = freq2.containsKey(c);
			if (inS1 && inS2) {
				long c1Count = freq1.get(c);
				long c2Count = freq2.get(c);
				res.add(new CharGroup(c, Math.max(c1Count, c2Count),
						c1Count == c2Count ? '=' : (c1Count > c2Count ? '1' : '2')));
			} else if (inS1) {
				long c1Count = freq1.get(c);
				res.add(new CharGroup(c, c1Count, '1'));
			} else if (inS2) {
				long c2Count = freq2.get(c);
				res.add(new CharGroup(c, c2Count, '2'));
			}
		}

		String result = res.stream().filter(e -> e.getCount() > 1).sorted().map(CharGroup::toString)
				.collect(Collectors.joining("/"));

		return new DiffResult(result);
	}
}
