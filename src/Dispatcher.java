import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.Executor;

public class Dispatcher {
    private final int port;
    private final Executor executor;
    private final DbAdapter dbAdapter;

    public Dispatcher(int port, Executor executor, DbAdapter dbAdapter) {
        this.port = port;
        this.executor = executor;
        this.dbAdapter = dbAdapter;
    }

    public void start() throws IOException {
        try (var serverSocket = new ServerSocket(port)) {
            //noinspection InfiniteLoopStatement
            while (true) {
                var socket = serverSocket.accept();
                executor.execute(() -> {
                    try {
                        new Worker(socket, dbAdapter).handle();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        }
    }
}

