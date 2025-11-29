package com.github.jtesfaye.sosgame.GameIO;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.jtesfaye.sosgame.GameEvent.InputEvent;
import com.github.jtesfaye.sosgame.GameObject.Move;
import com.github.jtesfaye.sosgame.GameObject.Piece;
import com.github.jtesfaye.sosgame.util.Pair;
import com.github.jtesfaye.sosgame.util.utilFunctions;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class LLMInputHandler extends InputHandler {

    private final String API_KEY = System.getenv("SOS_API_KEY");
    private final String URL;

    class RequestSchema {

        @Getter
        String objective = objective =
            """
                Play like its Tic tac toe, but instead of X and O its S and O. \n"
                Refer to the "board" field to see the current state of the game board \n"
                Select a row column pair listed in the validMoves field \n"
                Your response needs to be in JSON format in the form: {row: number, col: number, piece: "S" | "O"}
            """;

        @Getter
        public List<Pair<Integer, Integer>> validMoves;

        @Getter
        public char[][] board;

        public RequestSchema(char[][] b, List<Pair<Integer, Integer>> valid) {
            board = b;
            validMoves = valid;
        }
    };

    public LLMInputHandler(UUID id, String URL) {
        super(id, InputType.NetworkIO);
        this.URL = !URL.isEmpty() ? URL : "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent";
    }

    public Move makeRequest(Piece[][] board) {

        char[][] charBoard = utilFunctions.boardToCharArr(board);
        RequestSchema reqSchema = new RequestSchema(charBoard, utilFunctions.getOpenPositions(board));

        ObjectMapper mapper = new ObjectMapper();

        try {

            String requestJSON = mapper.writeValueAsString(reqSchema);

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest req = HttpRequest.newBuilder()
                .uri(new URI(URL + "/" + API_KEY))
                .header("accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestJSON))
                .build();

            HttpResponse<InputStream> resp = client.send(req, HttpResponse.BodyHandlers.ofInputStream());

            if (resp.statusCode() == 200) {
                InputStream body = resp.body();
                Move move = mapper.readValue(body, Move.class);
                return move;

            } else {
                return new Move(0,0, Piece.sPiece);
            }

        } catch (InterruptedException | URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void getInput(Piece[][] board) {

       Move move = makeRequest(board);
       consumer.accept(new InputEvent(move, playerId));

    }
}
