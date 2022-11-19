package kz.narxoz.demokaspi.listeners;

public class EmailNotificationListener implements EventListener {
//    private String email;
//
//    public EmailNotificationListener(String email) {
//        this.email = email;
//    }

    @Override
    public void update(String operation_type, int sum) {
        System.out.println("Translated " + sum + ": Someone translated to you" + sum + "tg");
    }
}
