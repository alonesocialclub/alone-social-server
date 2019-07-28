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
        String fileUrl = "https://platform-lookaside.fbsbx.com/platform/profilepic/?asid=2256853227925892&height=250&width=250&ext=1566891315&hash=AeSIdtyadPIWL4_z";
        String result = s3Uploader.upload("test.jpeg", fileUrl);
        System.out.println(result);
    }

}