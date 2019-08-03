package social.alone.server.interest

import org.springframework.lang.NonNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional
class InterestUpsertService (val interestRepository: InterestRepository){

    fun saveAll(@NonNull interests: List<InterestDto>): HashSet<Interest> {
        val values = interests.map { it.value }

        val results = HashSet(interestRepository.findAllByValueIn(values))

        if (results.size == values.size) {
            return results
        }

        val existsValues = results.map { it.value }

        val itemsToBeSaved = values
                .filter { value -> !existsValues.contains(value) }
                .map( { Interest(it!!)})

        results.addAll(interestRepository.saveAll(itemsToBeSaved))

        return results

    }
}
