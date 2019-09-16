package social.alone.server.interest


import social.alone.server.BaseRepositoryTest
import org.assertj.core.api.Assertions
import org.junit.Test

import java.util.ArrayList
import java.util.HashSet
import java.util.stream.Collectors

import org.assertj.core.api.Assertions.assertThat


class InterestRepositoryTest : BaseRepositoryTest() {


    @Test
    @Throws(Exception::class)
    fun saveTest() {

        // given
        val interest = Interest("스타트업")
        // when
        interestRepository.save(interest)

        // then
        assertThat(interest).isNotNull
        assertThat(interest.users).isEmpty()
    }

    @Test
    @Throws(Exception::class)
    fun findAllByValueInTest() {
        // given
        val interests = ArrayList<Interest>()
        interests.add(buildInterest("sc"))
        interests.add(buildInterest("make the world a better place"))
        interests.add(buildInterest("statistics"))
        interestRepository.saveAll(interests)
        val interestsKeywords = interests.map{it.value}.toSet()

        // when
        val results = interestRepository.findAllByValueIn(interestsKeywords)

        // then
        assertThat(results).containsAll(interests)
    }


}