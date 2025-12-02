package GameIO;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jtesfaye.sosgame.GameIO.LLMInputHandler;
import com.github.jtesfaye.sosgame.GameObject.Move;
import com.github.jtesfaye.sosgame.GameObject.Piece;
import com.github.jtesfaye.sosgame.Gemini.GeminiResponseSchema;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterAll;
import okhttp3.mockwebserver.MockWebServer;
import com.github.jtesfaye.sosgame.Gemini.GeminiRequest;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.UUID;

public class LLMInputHandlerTest {

    public static MockWebServer mock;
    private LLMInputHandler handler;

    private final Piece[][] board = {
        {Piece.sPiece, Piece.OPEN, Piece.OPEN},
        {Piece.OPEN, Piece.sPiece, Piece.OPEN},
        {Piece.sPiece, Piece.OPEN, Piece.OPEN}
    };

    @BeforeAll
    static void setUp() throws Exception {
        mock = new MockWebServer();
        mock.start();
    }

    @AfterAll
    static void tearDown() throws Exception {
        mock.shutdown();
    }

    @BeforeEach
    void initialize() {
        int port = mock.getPort();
        handler = new LLMInputHandler(UUID.randomUUID());
    }

    @Test
    public void responseGenerationTest() throws JsonProcessingException {
        String resp = GeminiRequest.generateRequest("test", Move.class);
        System.out.println(resp);
    }

    @Test
    public void requestToRealEndPoint() throws InterruptedException, IOException, URISyntaxException {

        String requestJSON = GeminiRequest.generateRequest(
            "This is a test, when selecting a piece please only choose sPiece or oPiece", Move.class);

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest req = HttpRequest.newBuilder()
            .uri(new URI(GeminiRequest.URL))
            .header("x-goog-api-key", GeminiRequest.API_KEY)
            .header("accept", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(requestJSON))
            .build();

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);

        HttpResponse<InputStream> resp = client.send(req, HttpResponse.BodyHandlers.ofInputStream());
        assertEquals(200, resp.statusCode());

        GeminiResponseSchema gresp = mapper.readValue(resp.body(), GeminiResponseSchema.class);

        String text = gresp.getCandidates().get(0).getContent().getParts().get(0).getText();
        Move move = mapper.readValue(text, Move.class);

        assertNotNull(move);
    }
}
