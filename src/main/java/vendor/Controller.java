package vendor;

import org.springframework.context.ApplicationContext;

import main.Main;

public class Controller
{
    private static ApplicationContext container;

    public Controller() {
        container = Main.getContainer();
    }

    public Object get(String id) {
        return container.getBean(id);
    }

    public ApplicationContext getContainer() {
        return container;
    }
}
