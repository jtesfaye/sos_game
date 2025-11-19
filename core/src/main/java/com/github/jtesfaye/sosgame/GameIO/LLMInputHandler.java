package com.github.jtesfaye.sosgame.GameIO;

import com.github.jtesfaye.sosgame.GameObject.Piece;
import com.github.jtesfaye.sosgame.util.utilFunctions;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.UUID;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class LLMInputHandler extends InputHandler {

    private final String API_KEY = System.getenv("SOS_API_KEY");
    private final String URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent";

    class GameRules {

        public String objective = "";
        public String validMoves = "";

        public GameRules(String obj, String valid) {
            this.objective = obj;
            this.validMoves = valid;
        }
    }

    class ResponseSchema {

        public int row;
        public int col;
        public char piece;

        public ResponseSchema(int r, int c, char p) {
            row = r;
            col = c;
            piece = p;
        }
    };

    class RequestSchema {

        public GameRules rules;
        public char[][] board;
        String instruction;

        public RequestSchema(GameRules r, char[][] b, String i) {
            rules = r;
            board = b;
            instruction = i;
        }
    };

    public LLMInputHandler(UUID id) {
        super(id, InputType.NetworkIO);
    }

    @Override
    public void getInput(Piece[][] board) {

        GameRules rules = new GameRules("", "");
        char[][] charBoard = utilFunctions.boardToCharArr(board);
        RequestSchema reqSchema = new RequestSchema(rules, charBoard, "");

        ObjectMapper mapper = new ObjectMapper();

        try {
            String requestJSON = mapper.writeValueAsString(reqSchema);

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest req = HttpRequest.newBuilder()
                .uri(new URI(URL + "/" + API_KEY))
                .header("accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestJSON))
                .build();

            HttpResponse<String> resp = client.send(req, HttpResponse.BodyHandlers.ofString());

        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }


        return;
    }
}
