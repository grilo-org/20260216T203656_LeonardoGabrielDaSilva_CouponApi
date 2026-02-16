package br.com.leogs.coupon.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.leogs.coupon.api.dto.CouponResponse;

@SpringBootTest
@AutoConfigureMockMvc
class CouponControllerTest {

	@Autowired
	private MockMvc mockMvc;

	private final ObjectMapper objectMapper = new ObjectMapper();

	private String validRequestBody() {
		return """
				{
				    "code": "ABC-123",
				    "description": "Cupom de desconto",
				    "discountValue": 0.8,
				    "expirationDate": "2026-11-04T17:14:45.180Z",
				    "published": false
				}
				""";
	}
	
	private String validRequestBodyWithouPublished() {
		return """
				{
				    "code": "ABC-123",
				    "description": "Cupom de desconto",
				    "discountValue": 0.8,
				    "expirationDate": "2026-11-04T17:14:45.180Z"
				}
				""";
	}

	@Test
	void testCreateCouponAndReturn201() throws Exception {
		mockMvc.perform(post("/coupon").contentType(MediaType.APPLICATION_JSON).content(validRequestBody()))
				.andExpect(status().isCreated()).andExpect(jsonPath("$.code").value("ABC123"))
				.andExpect(jsonPath("$.status").value("ACTIVE")).andExpect(jsonPath("$.redeemed").value(false));
	}
	
	@Test
	void testCreateCouponWithoutPublishedAndReturn201() throws Exception {
		mockMvc.perform(post("/coupon").contentType(MediaType.APPLICATION_JSON).content(validRequestBodyWithouPublished()))
				.andExpect(status().isCreated()).andExpect(jsonPath("$.code").value("ABC123"))
				.andExpect(jsonPath("$.status").value("ACTIVE")).andExpect(jsonPath("$.redeemed").value(false));
	}

	@Test
	void testReturn400WhenInvalidData() throws Exception {
		String invalidBody = """
				{
				    "code": "AB",
				    "description": "Cupom de desconto",
				    "discountValue": 0.3,
				    "expirationDate": "2026-11-04T17:14:45.180Z"
				}
				""";

		mockMvc.perform(post("/coupon").contentType(MediaType.APPLICATION_JSON).content(invalidBody))
				.andExpect(status().isBadRequest());
	}

	@Test
	void testGetCouponAndReturn200() throws Exception {
		MvcResult result = mockMvc
				.perform(post("/coupon").contentType(MediaType.APPLICATION_JSON).content(validRequestBody()))
				.andReturn();

		String id = extractId(result);

		mockMvc.perform(get("/coupon/" + id)).andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value("ACTIVE"));
	}

	@Test
	void testDeleteCouponAndReturn204() throws Exception {
		MvcResult result = mockMvc
				.perform(post("/coupon").contentType(MediaType.APPLICATION_JSON).content(validRequestBody()))
				.andReturn();

		String id = extractId(result);

		mockMvc.perform(delete("/coupon/" + id)).andExpect(status().isNoContent());
	}

	@Test
	void testReturn404WhenDeletingNonExistentCoupon() throws Exception {
		mockMvc.perform(delete("/coupon/id-inexistente")).andExpect(status().isNotFound());
	}

	@Test
	void testReturn409WhenDeletingAlreadyDeletedCoupon() throws Exception {
		MvcResult result = mockMvc
				.perform(post("/coupon").contentType(MediaType.APPLICATION_JSON).content(validRequestBody()))
				.andReturn();

		String id = extractId(result);

		mockMvc.perform(delete("/coupon/" + id)).andExpect(status().isNoContent());
		mockMvc.perform(delete("/coupon/" + id)).andExpect(status().isConflict());
	}

	private String extractId(MvcResult result) throws Exception {
		String json = result.getResponse().getContentAsString();
		CouponResponse response = objectMapper.readValue(json, CouponResponse.class);
		return response.id();
	}
}