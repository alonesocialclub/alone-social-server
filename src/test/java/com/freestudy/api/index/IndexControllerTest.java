package com.freestudy.api.index;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
public class IndexControllerTest {

  @Autowired
  private IndexController controller;

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void contexLoadsTest() throws Exception {
    assertThat(controller).isNotNull();
  }

  @Test
  public void getTest() throws Exception {
    this.mockMvc
            .perform(get("/"))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("Hello")));
  }

}
