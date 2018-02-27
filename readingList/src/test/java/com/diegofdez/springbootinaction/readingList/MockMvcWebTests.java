
package com.diegofdez.springbootinaction.readingList;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ReadingListApplication.class)
@WebAppConfiguration
public class MockMvcWebTests {
	@Autowired
	private WebApplicationContext webContext;
	private MockMvc mockMvc;
	
	@Before
	public void setupMockMvc() {
		mockMvc = MockMvcBuilders
				.webAppContextSetup(webContext)
				.apply(SecurityMockMvcConfigurers.springSecurity())
				.build();
	}
	
	@Test
	public void homePage_noAuthentication() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/"))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
			.andExpect(MockMvcResultMatchers.header().string("Location", "http://localhost/login"));
	}
	
	@Test
	@WithUserDetails("diegofdez")
	public void homePage_authenticated_withUserDetails() throws Exception {
		Reader expectedReader = new Reader();
		expectedReader.setUsername("diegofdez");
		expectedReader.setPassword("password");
		expectedReader.setFullname("Diego Fernandez");
		
		mockMvc.perform(MockMvcRequestBuilders.get("/"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.view().name("readingList"))
			.andExpect(MockMvcResultMatchers.model().attribute("reader", Matchers.samePropertyValuesAs(expectedReader)))
			.andExpect(MockMvcResultMatchers.model().attribute("books", Matchers.hasSize(0)));
		
	}
	
	@Test
	@WithMockUser(username="diegofdez", password="password", roles= {"READER"})
	public void homePage_authenticated_withMockUser() throws Exception {
		Reader expectedReader = new Reader();
		expectedReader.setUsername("diegofdez");
		expectedReader.setPassword("password");
		expectedReader.setFullname("Diego Fernandez");
		
		mockMvc.perform(MockMvcRequestBuilders.get("/"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.view().name("readingList"))
			.andExpect(MockMvcResultMatchers.model().attribute("reader", Matchers.hasProperty("username", Matchers.comparesEqualTo("diegofdez"))))
			.andExpect(MockMvcResultMatchers.model().attribute("reader", Matchers.hasProperty("password", Matchers.comparesEqualTo("password"))))
			.andExpect(MockMvcResultMatchers.model().attribute("books", Matchers.hasSize(0)));
		
	}
}
