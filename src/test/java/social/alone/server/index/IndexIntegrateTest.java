package social.alone.server.index;

import social.alone.server.BaseIntegrateTest;
import org.junit.Test;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class IndexIntegrateTest extends BaseIntegrateTest {

  @Test
  public void indexTest() throws Exception {
    this.mockMvc
            .perform(get("/"))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("Hello")));
  }

  @Test
  public void apiIndexTest() throws Exception {
    var perform = this.mockMvc
            .perform((get("/api")));

    perform
            .andDo(print())
            .andDo(document("index"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("_links.events").exists());
  }

}
