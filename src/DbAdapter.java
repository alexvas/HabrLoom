/**
 * Заглушка Адаптера для общения с Базой данных (БД).
 */
public class DbAdapter {
    /**
     * Получаем данные из БД (например, по протоколу JDBC).
     *
     * @param input - входной параметр для подстановки в SQL-запрос.
     * @return ответ БД.
     */
    public Output fetch(Input input) {
        throw new UnsupportedOperationException("not implemented");
    }
}
