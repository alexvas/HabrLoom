import java.io.IOException;
import java.net.Socket;

/**
 * Обработчик запроса клиента.
 */
class Worker {
    private final Socket socket;
    private final DbAdapter dbAdapter;

    public Worker(Socket socket, DbAdapter dbAdapter) {
        this.socket = socket;
        this.dbAdapter = dbAdapter;
    }

    public void handle() throws IOException {
        try (
                var is = socket.getInputStream();
                var os = socket.getOutputStream()
        ) {
            var input = decodeClientRequest(is.readAllBytes());
            var output = dbAdapter.fetch(input);
            os.write(encodeDbResponse(output));
        }
    }

    private Input decodeClientRequest(byte[] input) {
        throw new UnsupportedOperationException("not implemented");
    }

    private byte[] encodeDbResponse(Output output) {
        throw new UnsupportedOperationException("not implemented");
    }
}
