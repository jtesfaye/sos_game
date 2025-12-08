package com.github.jtesfaye.sosgame.GameIO;
import com.github.jtesfaye.sosgame.Gemini.GeminiRequest;
import com.github.jtesfaye.sosgame.Computer.MediumGameStrategy;
import com.github.jtesfaye.sosgame.GameEvent.InputEvent;
import com.github.jtesfaye.sosgame.GameObject.Move;
import com.github.jtesfaye.sosgame.GameObject.Piece;
import com.github.jtesfaye.sosgame.Gemini.GeminiResponseSchema;
import com.github.jtesfaye.sosgame.util.utilFunctions;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.UUID;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class LLMInputHandler extends InputHandler {

    private final MediumGameStrategy fallback = new MediumGameStrategy();
    private final ObjectMapper mapper = new ObjectMapper();

    public LLMInputHandler(UUID id) {
        super(id, InputType.NetworkIO);
    }

    public Move makeRequest(Piece[][] board) {

        char[][] charBoard = utilFunctions.boardToCharArr(board);

        String prompt = """
                Play like its Tic tac toe, but instead of X and O its S and O.
                To score, form an "SOS" on the board and it can be horizontal, vertical or diagonal.
                Play with the intent to win, whether it involves blocking the player from scoring, or scoring for yourself
                Refer to the "board" field to see the current state of the game board
                Select a row column pair listed in the validMoves field
                Your response needs to be in JSON format in the form: {row: number, col: number, piece: "sPiece" | "oPiece"}

                Board:
                %s

                Valid moves
                %s
                """.formatted(
                    Arrays.deepToString(charBoard),
                    utilFunctions.getOpenPositions(board).toString());

        try {

            String requestJSON = GeminiRequest.generateRequest(prompt, Move.class);

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest req = HttpRequest.newBuilder()
                .uri(new URI(GeminiRequest.URL))
                .header("x-goog-api-key", GeminiRequest.API_KEY)
                .header("accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestJSON))
                .build();

                HttpResponse<InputStream> resp = client.send(req, HttpResponse.BodyHandlers.ofInputStream());

                if (resp.statusCode() == 200) {
                    InputStream body = resp.body();
                    GeminiResponseSchema gresp = mapper.readValue(body, GeminiResponseSchema.class);
                    String text = gresp.getCandidates().get(0).getContent().getParts().get(0).getText();
                    return mapper.readValue(text, Move.class);

                } else {
                    return fallback.makeMove(board);
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
