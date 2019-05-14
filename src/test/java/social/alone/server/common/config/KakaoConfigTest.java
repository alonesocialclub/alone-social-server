package social.alone.server.common.config;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
public class KakaoConfigTest {

    @Autowired
    private KakaoConfig config;

    @Test
    public void test() {
        assertThat(config.getApiKey()).isNotEmpty();
        assertThat(config.getSearchKeywordUrl()).isNotEmpty();
    }

}