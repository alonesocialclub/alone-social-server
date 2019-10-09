package social.alone.server.post.controller

import org.junit.Test
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.security.test.context.support.WithUserDetails
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import social.alone.server.BaseIntegrateTest
import social.alone.server.image.Image
import social.alone.server.image.ImageRequest
import social.alone.server.post.dto.PostCreateRequest

class PostControllerTest : BaseIntegrateTest() {

    @Test
    @WithUserDetails(
            value = BaseIntegrateTest.CREATED_USER_EMAIL,
            userDetailsServiceBeanName = "customUserDetailService")
    @Throws(Exception::class)
    fun createPost_happy() {
        // Givens
        val image: Image = this.createImage()
        val postCreateRequest = PostCreateRequest("한산한 가빈 카페", ImageRequest(image.id!!))


        // When
        val perform = mockMvc
                .perform(
                        MockMvcRequestBuilders.post("/v1/posts/")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .content(objectMapper.writeValueAsString(postCreateRequest))
                )

        // Then
        perform
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("id").isString)
                .andExpect(MockMvcResultMatchers.jsonPath("text").isString)
                .andExpect(MockMvcResultMatchers.jsonPath("createdAt").isString)
                .andExpect(MockMvcResultMatchers.jsonPath("updatedAt").isString)
                .andExpect(MockMvcResultMatchers.jsonPath("image.id").isString)
                .andExpect(MockMvcResultMatchers.jsonPath("author.profile.name").isString)
                .andDo(MockMvcRestDocumentation.document("post-create"))
    }


}
