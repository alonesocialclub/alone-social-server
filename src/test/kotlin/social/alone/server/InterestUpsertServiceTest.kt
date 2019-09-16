package social.alone.server

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

import org.springframework.transaction.annotation.Transactional
import java.util.HashSet
import java.util.stream.Collectors

import org.assertj.core.api.Assertions.assertThat
import social.alone.server.interest.Interest
import social.alone.server.interest.InterestDto
import social.alone.server.interest.InterestRepository
import social.alone.server.interest.InterestUpsertService


@RunWith(SpringRunner::class)
@SpringBootTest
@Transactional
class InterestUpsertServiceTest {

    @Autowired
    lateinit var interestUpsertService: InterestUpsertService

    @Autowired
    lateinit var interestRepository: InterestRepository

    @Test
    @Throws(Exception::class)
    fun saveAllTest() {

        // given
        val interests = HashSet<Interest>()
        interests.add(Interest("과학2"))
        interests.add(Interest("스타트업2"))
        interests.add(Interest("통계1"))
        interestRepository.saveAll(interests)


        val valuesToBeSaved = listOf(
                InterestDto("과학2"),
                InterestDto("과학1")
        )


        // when
        val results = interestUpsertService.saveAll(valuesToBeSaved)

        assertThat(results.map{ it.value}.toSet()).containsAll(valuesToBeSaved.toSet().map { it.value })

    }

}