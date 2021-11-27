package com.xiaohe66.common.util.jackson;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

/**
 * @author xiaohe
 * @since 2021.11.27 22:27
 */
public class JacksonLongJsonDeserializer extends JsonDeserializer<Long> {

    @Override
    public Long deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {

        String text = jsonParser.getText();

        try {
            return Long.parseLong(text);

        } catch (NumberFormatException e) {
            throw new JsonParseException(jsonParser, "cannot convert [" + text + "] to Long", e);
        }
    }
}
