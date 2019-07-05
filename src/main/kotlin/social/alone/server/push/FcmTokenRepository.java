package social.alone.server.push;

import org.springframework.data.jpa.repository.JpaRepository;
import social.alone.server.event.Event;

public interface FcmTokenRepository extends JpaRepository<FcmToken, Long> {
}
