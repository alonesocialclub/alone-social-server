package social.alone.server.infrastructure;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

@Slf4j
@RequiredArgsConstructor
@Component
public class S3Uploader {

    private final AmazonS3Client amazonS3Client;

    public String upload(String fileUrl) throws IOException {
        URL url = new URL(fileUrl);
        InputStream inputStream = new BufferedInputStream(url.openStream());
        byte[] contents = IOUtils.toByteArray(inputStream);
        InputStream stream = new ByteArrayInputStream(contents);
        ObjectMetadata meta = new ObjectMetadata();
        meta.setContentLength(contents.length);
        meta.setContentType("image/jpeg");
        String bucket = "alone-social-static-image";
        amazonS3Client
                .putObject(new PutObjectRequest(bucket , fileUrl, stream, meta)
                .withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3Client.getUrl(bucket, fileUrl).toString();
    }
}
