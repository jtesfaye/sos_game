package com.github.jtesfaye.sosgame.Gemini;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GeminiRequest {

    private static final ObjectMapper mapper = new ObjectMapper();
    private static final JsonSchemaGenerator schemaGen = new JsonSchemaGenerator(mapper);

    public static final String URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent";
    public static final String API_KEY = System.getenv("SOS_KEY_TWO");

    public static String generateRequest(String prompt, Class<?> schema) throws JsonProcessingException {
        Map<String, Object> base = new HashMap<>();
        Map<String, Object> generationConfig;

        base.put("contents", List.of(Map.of("parts", List.of(Map.of("text", prompt)))));
        generationConfig = setGenerationConfig(schema);

        base.put("generationConfig", generationConfig);

        ObjectMapper mapper = new ObjectMapper();

        return mapper.writeValueAsString(base);
    }

    private static Map<String, Object> setGenerationConfig(Class<?> schema) throws JsonProcessingException {
        Map<String, Object> gen = new HashMap<>();

        gen.put("responseMimeType", "application/json");
        gen.put("responseJsonSchema", extractProperty(schemaGen.generateSchema(schema)));

        return gen;
    }

    private static Object extractProperty(JsonSchema schema) {
        try {
            String j = mapper.writeValueAsString(schema);
            JsonNode root = mapper.readTree(j);
            ((ObjectNode) root).remove("id");
            JsonNode property = root.get("properties");

            ObjectNode extracted = mapper.createObjectNode();
            extracted.put("type", "object");
            extracted.set("properties", property);
            return extracted;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }
}
