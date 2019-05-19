package social.alone.server.auth;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@RunWith(SpringRunner.class)
public class FacebookConfigTest {

    @Autowired
    FacebookConfig config;

    @Test
    public void test() {
        assertThat(config.getGraphUrl()).isEqualTo("https://graph.facebook.com/v3.0");
    }

}