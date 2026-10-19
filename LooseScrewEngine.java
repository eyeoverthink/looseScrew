import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;
import java.io.*;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.*;

/**
 * looseScrew // EyeOverthink Media & REA Engineering Backend
 * Architecture: LogicCircuit + LazarusEngine + FraymusPortal + UniversalAbsorber + AkashicRecord
 */
public class LooseScrewEngine {

    // THE MEMORY: Structured Temporal Archive
    public static class AkashicRecord {
        private final List<String> fossils = new CopyOnWriteArrayList<>();

        public void preserveFossil(String category, String payload) {
            String entry = String.format("{\"timestamp\":\"%s\",\"category\":\"%s\",\"payload\":\"%s\"}",
                    Instant.now().toString(), category, payload.replace("\"", "\\\""));
            fossils.add(entry);
            System.out.println("[AKASHIC_RECORD] Preserved Fossil #" + fossils.size() + " -> " + category);
        }

        public String toJson() {
            return "[" + String.join(",", fossils) + "]";
        }
    }

    // THE BODY: Background Evolution & Thermal Core Thread
    public static class LazarusEngine implements Runnable {
        private volatile double thermalCoreCelsius = 36.48;
        private final AkashicRecord memory;

        public LazarusEngine(AkashicRecord memory) {
            this.memory = memory;
        }

        public void triggerThermalInjection(double delta) {
            this.thermalCoreCelsius += delta;
            memory.preserveFossil("THERMAL_INJECTION", "Core mutated to " + String.format("%.2fC", thermalCoreCelsius));
        }

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Thread.sleep(15000);
                    // Phi-harmonic stabilization drift
                    thermalCoreCelsius = 36.48 + (Math.sin(System.currentTimeMillis() * 0.001) * 0.1618);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        int port = 8080;
        AkashicRecord akashic = new AkashicRecord();
        akashic.preserveFossil("BOOT", "looseScrew Media & REA Engine Initialized for EyeOverthink");

        LazarusEngine lazarus = new LazarusEngine(akashic);
        Thread.ofVirtual().start(lazarus);

        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

        // Fraymus Portal Root: Serves index.html
        server.createContext("/", (HttpExchange exchange) -> {
            Path indexPath = Path.of("index.html");
            byte[] response = Files.exists(indexPath)
                    ? Files.readAllBytes(indexPath)
                    : "<h1>Place index.html in working directory</h1>".getBytes();
            exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
            exchange.sendResponseHeaders(200, response.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response);
            }
        });

        // Akashic Fossil Telemetry Endpoint
        server.createContext("/api/akashic", (HttpExchange exchange) -> {
            byte[] json = akashic.toJson().getBytes();
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, json.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(json);
            }
        });

        server.setExecutor(Executors.newVirtualThreadPerTaskExecutor());
        server.start();
        System.out.println("==========================================================");
        System.out.println(" looseScrew Studio Online -> http://localhost:" + port);
        System.out.println(" Brand Host: EyeOverthink Avatar [ACTIVE]");
        System.out.println(" Channels: [MUSIC] [VIDEO] [CODE & REA_LAB]");
        System.out.println("==========================================================");
    }
}