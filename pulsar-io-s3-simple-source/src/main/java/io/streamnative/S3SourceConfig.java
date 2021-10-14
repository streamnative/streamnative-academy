package io.streamnative;

import com.amazonaws.util.StringUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.Serializable;
import java.util.Map;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.pulsar.io.core.annotations.FieldDoc;

@Data
@Accessors
public class S3SourceConfig implements Serializable {
    @FieldDoc(
            required = true,
            defaultValue = "",
            help = "AWS Bucket"
    )
    private String awsBuckets;

    @FieldDoc(
            required = true,
            defaultValue = "",
            help = "AWS Region"
    )
    private String awsRegion;


    public static S3SourceConfig load(Map<String, Object> config) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new ObjectMapper().writeValueAsString(config), S3SourceConfig.class);
    }

    public void validate() throws IllegalAccessException {
        if (StringUtils.isNullOrEmpty(awsBuckets)) {
            throw new IllegalAccessException("AWS Bucket not specified.");
        }

        if (StringUtils.isNullOrEmpty(awsRegion)) {
            throw new IllegalAccessException("AWS Region not specified.");
        }
    }
}
