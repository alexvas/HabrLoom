import java.io.IOException;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String... args) throws InterruptedException {
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
            System.err.println("uncaught exception:" + e.getMessage());
            e.printStackTrace(System.err);
        });

        var portToListen = 8080;
        var executor = Executors.newFixedThreadPool(100);
        var dbAdapter = new DbAdapter();
        var dispatcher = new Dispatcher(portToListen, executor, dbAdapter);

        var t = new Thread(() -> {
            try {
                dispatcher.start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        t.start();
        t.join();
    }
}
