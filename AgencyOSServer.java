import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class AgencyOSServer {
    private static final int PORT = 8080;
    private static String activeBrain = "FRAYMUS v18";
    private static final Map<String, String> toolStatus = new ConcurrentHashMap<>();

    static {
        toolStatus.put("canva", "connected");
        toolStatus.put("gmail", "connected");
        toolStatus.put("gcal", "connected");
        toolStatus.put("github", "connected");
        toolStatus.put("notion", "reconnect");
        toolStatus.put("gsheets", "connected");
        toolStatus.put("gdrive", "connected");
        toolStatus.put("gdocs", "connected");
    }

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
        server.createContext("/", exchange -> {
            Path index = Paths.get("apps/ai-workshop-fraymus-os.html");
            if (!Files.exists(index)) index = Paths.get("index.html");
            if (Files.exists(index)) {
                byte[] bytes = Files.readAllBytes(index);
                exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
                exchange.sendResponseHeaders(200, bytes.length);
                try (OutputStream os = exchange.getResponseBody()) { os.write(bytes); }
            } else {
                sendJson(exchange, 404, "{\"error\":\"Place index.html in the working directory.\"}");
            }
        });
        server.createContext("/api/status", exchange -> {
            addCors(exchange);
            String json = String.format(
                "{\"status\":\"ONLINE\",\"brain\":\"%s\",\"phi75Seal\":4721424167835376.00,\"notes\":538,\"connections\":1947,\"connectedTools\":%d}",
                activeBrain,
                toolStatus.values().stream().filter(s -> s.equals("connected")).count()
            );
            sendJson(exchange, 200, json);
        });
        server.createContext("/api/brain", exchange -> {
            addCors(exchange);
            if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
                String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8).trim();
                if (!body.isEmpty()) activeBrain = body.replaceAll("[\"{}]", "");
            }
            sendJson(exchange, 200, "{\"activeBrain\":\"" + activeBrain + "\"}");
        });
        server.setExecutor(null);
        server.start();
        System.out.println("[AI WORKSHOP OS & F.R.A.Y.M.U.S.] J.A.R.V.I.S. Kernel online at http://localhost:" + PORT);
    }

    private static void addCors(HttpExchange exchange) {
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");
    }

    private static void sendJson(HttpExchange exchange, int code, String payload) throws IOException {
        byte[] bytes = payload.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
        exchange.sendResponseHeaders(code, bytes.length);
        try (OutputStream os = exchange.getResponseBody()) { os.write(bytes); }
    }
}
