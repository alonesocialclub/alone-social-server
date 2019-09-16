package social.alone.server

import org.junit.Ignore
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.transaction.annotation.Transactional
import social.alone.server.interest.Interest
import social.alone.server.interest.InterestRepository

@RunWith(SpringRunner::class)
@DataJpaTest
@ActiveProfiles("test")
@Ignore
@Transactional
class BaseRepositoryTest {

    @Autowired
    lateinit protected var interestRepository: InterestRepository

    fun buildInterest(value: String): Interest {
        return interestRepository.save(
                Interest(value)
        )
    }

}
