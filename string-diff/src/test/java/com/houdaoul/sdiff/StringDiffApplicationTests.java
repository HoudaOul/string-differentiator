package com.houdaoul.sdiff;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.houdaoul.sdiff.model.DiffRequest;
import com.houdaoul.sdiff.model.DiffResult;
import com.houdaoul.sdiff.service.Differentiator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * 
 * @author houlachguer
 *
 */
@WebMvcTest
class StringDiffApplicationTests {

	@Autowired
	MockMvc mvc;

	@MockBean
	Differentiator diffService;

	@Autowired
	ObjectMapper mapper;

	@Test
	public void testControllerPerformsCorrectly() throws Exception {
		final String expectedResult = "1:aaaa/2:bbb";
		when(diffService.computeStringDiff(any())).thenReturn(new DiffResult(expectedResult));

		mvc.perform(MockMvcRequestBuilders.post("/diff").contentType("application/json")
				.content(mapper.writeValueAsString(new DiffRequest("A aaaa bb c", "& aaa bbb c d"))))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("result").value(expectedResult));
	}

	@Test
	public void testBadRequestErrorResponseIsReturned() throws Exception {
		mvc.perform(MockMvcRequestBuilders.post("/diff").contentType("application/json")
				.content(mapper.writeValueAsString(new DiffRequest("", null))))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());

		mvc.perform(MockMvcRequestBuilders.post("/diff").contentType("application/json")
				.content(mapper.writeValueAsString(new DiffRequest(null, ""))))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
}
