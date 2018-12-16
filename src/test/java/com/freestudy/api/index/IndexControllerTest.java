package com.freestudy.api.index;

import com.freestudy.api.BaseControllerTest;
import org.junit.Test;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class IndexControllerTest extends BaseControllerTest {

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
