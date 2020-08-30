package com.houdaoul.sdiff;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.houdaoul.sdiff.api.DiffController;
import com.houdaoul.sdiff.config.DifferConfiguration;
import com.houdaoul.sdiff.model.DiffRequest;
import com.houdaoul.sdiff.model.DiffResult;
import com.houdaoul.sdiff.service.Differentiator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.CacheManager;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

//@WebMvcTest
@SpringBootTest(classes = { StringDiffApplication.class, DifferConfiguration.class, Differentiator.class })
@AutoConfigureMockMvc
class StringDiffApplicationTests {

	@Autowired
	MockMvc mvc;

	@Autowired
	ObjectMapper mapper;

	@MockBean
	Differentiator differentiator;

	@Autowired
	CacheManager manager;

	private static final String EXPECTED_RESULT = "1:aaaa/2:bbb";
	private static final DiffRequest REQUEST = new DiffRequest("A aaaa bb c", "& aaa bbb c d");

	@BeforeEach
	void setup() {
		when(differentiator.computeStringDiff(any())).thenReturn(new DiffResult(EXPECTED_RESULT));
	}

	@Test
	public void testControllerPerformsCorrectly() throws Exception {
		mvc.perform(MockMvcRequestBuilders.post("/diff").contentType("application/json")
				.content(mapper.writeValueAsString(REQUEST))).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("result").value(EXPECTED_RESULT));
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

	@Test
	public void testSameRequestIsCached() throws Exception {

		mvc.perform(MockMvcRequestBuilders.post("/diff").contentType("application/json")
				.content(mapper.writeValueAsString(REQUEST))).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("result").value(EXPECTED_RESULT));

		mvc.perform(MockMvcRequestBuilders.post("/diff").contentType("application/json")
				.content(mapper.writeValueAsString(REQUEST))).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("result").value(EXPECTED_RESULT));
		verify(differentiator).computeStringDiff(REQUEST);
	}

	@AfterEach
	void tearDown() {
		// All the tests share the same controller, the cache manager needs to be cleard
		// after each test to insure test results consistency.
		manager.getCache(DiffController.CACHE_NAME).clear();
	}
}
