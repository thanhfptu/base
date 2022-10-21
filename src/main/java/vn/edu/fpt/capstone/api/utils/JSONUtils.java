package vn.edu.fpt.capstone.api.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.Objects;
import java.util.TimeZone;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JSONUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(JSONUtils.class);

    private static final ObjectMapper MAPPER = JsonMapper.builder()
            .enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT)
            .enable(DeserializationFeature.FAIL_ON_READING_DUP_TREE_KEY)
            .enable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE)
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .defaultTimeZone(TimeZone.getTimeZone(DateUtils.TIMEZONE))
            .build();

    public static <T> String toJSON(T data) {
        try {
            return Objects.isNull(data) ? null : MAPPER.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            LOGGER.error(e.getMessage());
            return null;
        }
    }

    public static <T> T parse(String str, Class<T> tClass) {
        try {
            return StringUtils.hasText(str) ? MAPPER.readValue(str, tClass) : null;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return null;
        }
    }

}
