package kz.narxoz.demokaspi.authorization;

import kz.narxoz.demokaspi.authorization.Server;

/**
 * Конкретный элемент цепи обрабатывает запрос по-своему.
 */
public class UserExistsMiddleware extends Middleware {
    private Server server;

    public UserExistsMiddleware(Server server) {
        this.server = server;
    }

    public boolean check(String phone_number, String password) {
        if (!server.hasPhone_number(phone_number)) {
            System.out.println("This email is not registered!");
            return false;
        }
        if (!server.isValidPassword(phone_number, password)) {
            System.out.println("Wrong password!");
            return false;
        }
        return checkNext(phone_number, password);
    }
}
