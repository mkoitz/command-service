package net.mjr.commandservice.repository;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import net.mjr.commandservice.dto.CommandResponseDTO;

public class CommandResourceTest extends AbstractResourceTest {

   private static final String SERVICE_BASE_URL = "/api/v1/commands/";

   @Override
   @Before
   public void setUp() {
      super.setUp();
   }

   @Test
   public void executeCommandIsOk() throws Exception {

      final String commandKey = "echo";

      MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(CommandResourceTest.SERVICE_BASE_URL + commandKey)
         .contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();
      
      int status = mvcResult.getResponse().getStatus();
      assertEquals(200, status);
      String content = mvcResult.getResponse().getContentAsString();
      CommandResponseDTO response = super.mapFromJson(content, CommandResponseDTO.class);
      assertEquals("hello world!", response.getMessages().get(0));
      assertEquals("0", response.getStatus());
      
   }

   @Test
   public void executeCommandIsNotFound() throws Exception {

      final String commandKey = "not_found";

      MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(CommandResourceTest.SERVICE_BASE_URL + commandKey)
         .contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();
      
      int status = mvcResult.getResponse().getStatus();
      assertEquals(404, status);
      String content = mvcResult.getResponse().getContentAsString();
      assertEquals("", content);
   }
}