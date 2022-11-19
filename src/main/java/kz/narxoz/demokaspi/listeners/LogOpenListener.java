package kz.narxoz.demokaspi.listeners;

import java.io.File;

public class LogOpenListener implements EventListener {
    private File log;

    public LogOpenListener(String fileName) {
        this.log = new File(fileName);
    }

    @Override
    public void update(String operation_type, int sum) {
        System.out.println("Sum:" + sum + "operation_type:" + operation_type);
    }
}
