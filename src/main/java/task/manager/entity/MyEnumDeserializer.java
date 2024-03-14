package task.manager.entity;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.stream.Stream;

public class MyEnumDeserializer extends JsonDeserializer {
    @Override
    public Object deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        String nodeText = node.asText();
        return Stream.of(TaskCategory.values())
                .filter(enumValue -> enumValue.name().equals(nodeText))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Enum value" + nodeText + " is not recognized"));

    }
}
