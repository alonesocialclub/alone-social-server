package com.freestudy.api.index;


import com.freestudy.api.RestDocsConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Import(RestDocsConfiguration.class)
@ActiveProfiles("test")
public class IndexControllerTest {


  @Autowired
  private MockMvc mockMvc;

  @Test
  public void indexTest() throws Exception {
    this.mockMvc
            .perform(get("/"))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("Hello")));
  }

  @Test
  public void apiIndexTest() throws Exception {
    this.mockMvc
            .perform((get("/api")))
            .andExpect(status().isOk())
            .andExpect(jsonPath("_links.events").exists());
  }

}
