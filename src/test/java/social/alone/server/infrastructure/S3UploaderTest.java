package social.alone.server.infrastructure;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class S3UploaderTest {

    @Autowired
    S3Uploader s3Uploader;

    @Test
    @Ignore
    public void test() throws IOException {
        String result = s3Uploader.upload("https://blog.ordinarysimple.com/img/og.jpeg");

        System.out.println(result);
    }

}