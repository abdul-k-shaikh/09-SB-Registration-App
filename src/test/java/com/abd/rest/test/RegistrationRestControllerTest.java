package com.abd.rest.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.HashMap;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.abd.bindings.User;
import com.abd.constants.AppConstants;
import com.abd.rest.RegistrationRestController;
import com.abd.service.RegistrationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(value = RegistrationRestController.class)
public class RegistrationRestControllerTest {

	@MockBean
	private RegistrationService regService;

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void checkEmailTest() throws Exception {
		when(regService.uniqueEmail("abd@gmail.com")).thenReturn(true);

		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/emailcheck/abd@gmail.com");

		MvcResult mvcResult = mockMvc.perform(builder).andReturn();

		MockHttpServletResponse response = mvcResult.getResponse();

		String resBody = response.getContentAsString();

		assertEquals(AppConstants.UNIQUE, resBody);
		// System.out.println("content:: "+ status);
	}

	@Test
	public void checkEmailTest2() throws Exception {
		when(regService.uniqueEmail("abd@gmail.com")).thenReturn(false);

		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/emailcheck/ab@gmail.com");
		MvcResult mvcResult = mockMvc.perform(builder).andReturn();
		MockHttpServletResponse response = mvcResult.getResponse();
		String resBody = response.getContentAsString();
		assertEquals(AppConstants.DUPLICATE, resBody);
	}

	@Test
	public void getCountriesTest() throws Exception {
		HashMap<Integer, String> mp = new HashMap<>();
		mp.put(1, "India");
		mp.put(2, "USA");

		when(regService.getCountries()).thenReturn(mp);

		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/countries");

		MvcResult mvcResult = mockMvc.perform(builder).andReturn();
		MockHttpServletResponse response = mvcResult.getResponse();
		int status = response.getStatus();
		assertEquals(200, status);
	}

	@Test
	public void getStatesTest() throws Exception {
		HashMap<Integer, String> mp = new HashMap<>();
		mp.put(1, "Mh");
		mp.put(2, "WS");

		when(regService.getCountries()).thenReturn(mp);

		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/states/1");

		MvcResult mvcResult = mockMvc.perform(builder).andReturn();
		MockHttpServletResponse response = mvcResult.getResponse();
		int status = response.getStatus();
		assertEquals(200, status);
	}

	@Test
	public void getCityTest() throws Exception {
		HashMap<Integer, String> mp = new HashMap<>();
		mp.put(1, "Mumbai");
		mp.put(2, "jersey");

		when(regService.getCountries()).thenReturn(mp);

		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/city/1");

		MvcResult mvcResult = mockMvc.perform(builder).andReturn();
		MockHttpServletResponse response = mvcResult.getResponse();
		int status = response.getStatus();
		assertEquals(200, status);
	}

	@Test
	public void saveUserTest1() throws Exception {
		User user = new User();
		user.setUserFname("ashok");
		user.setUserLname("IT");
		user.setUserEmail("abdgmail.com");

		when(regService.registerUser(user)).thenReturn(true);

		ObjectMapper mapper = new ObjectMapper();
		String userJson = mapper.writeValueAsString(user);

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/saveuser")
																	  .contentType("application/json")
																	  .content(userJson);
		
		MvcResult result = mockMvc.perform(request).andReturn();
		String resBody = result.getResponse().getContentAsString();
		assertEquals(AppConstants.SUCCESS, resBody);

	}
	
	@Test
	public void saveUserTest2() throws Exception {
		User user = new User();
		user.setUserFname("ashok");
		user.setUserLname("IT");
		user.setUserEmail("abdgmail.com");

		when(regService.registerUser(user)).thenReturn(false);

		ObjectMapper mapper = new ObjectMapper();
		String userJson = mapper.writeValueAsString(user);

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/saveuser")
																	  .contentType("application/json")
																	  .content(userJson);
		
		MvcResult result = mockMvc.perform(request).andReturn();
		String resBody = result.getResponse().getContentAsString();
		assertEquals(AppConstants.FAIL, resBody);
	}

}
