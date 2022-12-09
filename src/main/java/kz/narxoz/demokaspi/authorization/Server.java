//package kz.narxoz.demokaspi.authorization;
//
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * Класс сервера.
// */
//public class Server {
//    private Map<String, String> users = new HashMap<>();
//    private Middleware middleware;
//
//    /**
//     * Клиент подаёт готовую цепочку в сервер. Это увеличивает гибкость и
//     * упрощает тестирование класса сервера.
//     */
//    public void setMiddleware(Middleware middleware) {
//        this.middleware = middleware;
//    }
//
//    /**
//     * Сервер получает email и пароль от клиента и запускает проверку
//     * авторизации у цепочки.
//     */
//    public boolean logIn(String phone_number, String password) {
//        if (middleware.check(phone_number, password)) {
//            System.out.println("Authorization have been successful!");
//
//            // Здесь должен быть какой-то полезный код, работающий для
//            // авторизированных пользователей.
//            return true;
//        }
//        return false;
//    }
//    public void register(String phone_number, String password) {
//        users.put(phone_number, password); // записывает данные в мап, мап отдает в контроллер
//    }
//
//    public boolean hasPhone_number(String phone_number) {
//        return users.containsKey(phone_number);
//    }
//
//    public boolean isValidPassword(String phone_number, String password) {
//        return users.get(phone_number).equals(password);
//    }
//}
