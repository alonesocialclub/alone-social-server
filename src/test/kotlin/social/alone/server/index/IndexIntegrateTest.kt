package social.alone.server.index

import org.hamcrest.Matchers.containsString
import org.junit.Test
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import social.alone.server.BaseIntegrateTest

class IndexIntegrateTest : BaseIntegrateTest() {

    @Test
    @Throws(Exception::class)
    fun indexTest() {
        this.mockMvc
                .perform(get("/"))
                .andExpect(status().isOk)
                .andExpect(content().string(containsString("Hello")))
    }

    @Test
    @Throws(Exception::class)
    fun apiIndexTest() {
        val perform = this.mockMvc
                .perform(get("/api"))

        perform
                .andDo(print())
                .andDo(document("index"))
                .andExpect(status().isOk)

    }

}
