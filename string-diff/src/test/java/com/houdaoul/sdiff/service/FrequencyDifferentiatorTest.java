package com.houdaoul.sdiff.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.houdaoul.sdiff.model.DiffRequest;
import com.houdaoul.sdiff.model.DiffResult;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsSource;

/**
 * 
 * @author houlachguer
 *
 */
class FrequencyDifferentiatorTest {

	FrequencyDifferentiator differentiator;

	@BeforeEach
	void setup() {
		differentiator = new FrequencyDifferentiator();
	}

	@Test
	public void testFrequencyDifferenceForEmptyStrings() {
		DiffResult result = differentiator.computeStringDiff(new DiffRequest("", ""));
		assertTrue(result.getResult().isEmpty());
	}

	@Test
	public void testFrequencyDifferenceForNoLowerCaseCharacters() {
		DiffResult result = differentiator
				.computeStringDiff(new DiffRequest("A.12344 1234 2ASD", "? && ASDFQW WEJ 23 ."));
		assertTrue(result.getResult().isEmpty());
	}

	@ParameterizedTest
	@ArgumentsSource(value = DiffArgumentProvider.class)
	void testExamples(String first, String second, String expected) {
		assertTrue(differentiator.computeStringDiff(new DiffRequest(first, second)).getResult().equals(expected));
	}

	static class DiffArgumentProvider implements org.junit.jupiter.params.provider.ArgumentsProvider {
		static Stream<Arguments> arguments = Stream.of(
				Arguments.of("my&friend&Paul has heavy hats! &", "my friend John has many many friends &",
						"2:nnnnn/1:aaaa/1:hhh/2:mmm/2:yyy/2:dd/2:ff/2:ii/2:rr/=:ee/=:ss"),
				Arguments.of("mmmmm m nnnnn y&friend&Paul has heavy hats! &",
						"my frie n d Joh n has ma n y ma n y frie n ds n&",
						"1:mmmmmm/=:nnnnnn/1:aaaa/1:hhh/2:yyy/2:dd/2:ff/2:ii/2:rr/=:ee/=:ss"),
				Arguments.of("Are the kids at home? aaaaa fffff", "Yes they are here! aaaaa fffff",
						"=:aaaaaa/2:eeeee/=:fffff/1:tt/2:rr/=:hh"));

		public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
			return arguments;
		}
	}

}