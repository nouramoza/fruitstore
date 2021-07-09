package com.cybercom.fruitstore.web.rest;

import com.cybercom.fruitstore.domain.Fruit;
import com.cybercom.fruitstore.service.FruitService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebMvcTest(FruitResource.class)
public class FruitStoreApplicationTests {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	WebApplicationContext webApplicationContext;

	@MockBean
	private FruitService fruitService;

	@Test
	public void saveFruit() throws Exception {
		String exampleFruitJson = dataToJson(null, "Banana", "Tropical", 5.0);
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/fruits")
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(exampleFruitJson)).andReturn();

		MockHttpServletResponse response = mvcResult.getResponse();
		Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());
	}

	@Test
	public void updateFruit() throws Exception {
		String exampleFruitJson = dataToJson(1L, "Banana", "Tropical", 10.0);
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/api/fruits")
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(exampleFruitJson)).andReturn();

		MockHttpServletResponse response = mvcResult.getResponse();
		Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());
	}

	@Test
	public void fruitList() throws Exception {
		when(fruitService.findAll()).thenReturn(Collections.emptyList());
		MvcResult mvcResult = mockMvc
				.perform(MockMvcRequestBuilders.get("/api/fruits").accept(MediaType.APPLICATION_JSON))
				.andReturn();
		MockHttpServletResponse response = mvcResult.getResponse();
		Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());
		verify(fruitService).findAll();
	}

	@Test
	public void getFruit() throws Exception {
		MvcResult mvcResult = mockMvc
				.perform(MockMvcRequestBuilders.get("/api/fruits/1").accept(MediaType.APPLICATION_JSON))
				.andReturn();

		String exampleFruitJson = dataToJson(1L, "Banana", "Tropical", 5.0);
		JSONAssert.assertEquals(exampleFruitJson, mvcResult.getResponse().getContentAsString(),false);
	}

	@Test
	public void deleteFruit() throws Exception {

		MvcResult mvcResult = mockMvc
				.perform(MockMvcRequestBuilders.delete("/api/fruits/1").accept(MediaType.APPLICATION_JSON))
				.andReturn();

		String exampleFruitJson = dataToJson(1L, "Banana", "Tropical", 5.0);
		JSONAssert.assertEquals(exampleFruitJson, mvcResult.getResponse().getContentAsString(),false);
	}

	private String dataToJson(Long id, String name, String type, Double price) throws JsonProcessingException {
		Fruit mockFruit = createEntity(id, name, type, price);
		return mapToJson(mockFruit);
	}

	private String mapToJson(Object obj) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(obj);
	}

	private Fruit createEntity(Long id, String name, String type, Double price) {
		Fruit mockFruit = new Fruit();
		mockFruit.setId(id);
		mockFruit.setName(name);
		mockFruit.setType(type);
		mockFruit.setPrice(price);
		return mockFruit;
	}
}
