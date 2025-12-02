package com.github.jtesfaye.sosgame.Gemini;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GeminiResponseSchema {

    @Getter
    @Setter
    private List<Candidate> candidates;

    @Getter
    @Setter
    private String modelVersion;

    public static class Candidate {

        @Getter
        @Setter
        private Content content;

        @Getter
        @Setter
        private String finishReason;

        @Getter
        @Setter
        private int index;

    }

    public static class Content {

        @Getter
        @Setter
        private List<Part> parts;

        @Getter
        @Setter
        private String role;

    }

    public static class Part {

        @Getter
        @Setter
        private String text;

    }
}
