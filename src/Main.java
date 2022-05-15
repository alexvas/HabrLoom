import java.io.IOException;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String... args) throws InterruptedException {
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
            System.err.println("uncaught exception:" + e.getMessage());
            e.printStackTrace(System.err);
        });

        // порт, который слушает сервер
        var portToListen = 8080;

        // Экзекутор задаёт стратегию управления программными потоками
        // для обработки запросов клиента. Каждый запрос
        // обрабатывается в своём потоке.
        var executor = Executors.newFixedThreadPool(100);
        // для перехода ^^^ на Loom:
        // var executor = Executors.newVirtualThreadPerTaskExecutor()

        // адаптер для общения с БД
        var dbAdapter = new DbAdapter();

        // распределяем входящие запросы клиента по обработчикам
        var dispatcher = new Dispatcher(portToListen, executor, dbAdapter);

        Runnable target = () -> {
            try {
                dispatcher.start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
        var t = new Thread(target, "tcp server");
        // для перехода ^^^ на Loom:
        // var t = Thread.ofVirtual().name("tcp server").unstarted(target);

        // запускаем диспетчер в отдельном треде
        t.start();

        // здесь запускаем остальной функционал...

        // в завершении дожидаемся окончания работы асинхронных задач
        t.join();
    }
}
