package vn.edu.fpt.capstone.api.deserializers;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class LongDeserializer extends StdDeserializer<Long> {

    public LongDeserializer() {
        this(null);
    }

    public LongDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Long deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        try {
            String content = p.getText();
            return Long.valueOf(content);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

}
