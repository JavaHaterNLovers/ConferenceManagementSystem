package util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

public class BaseController
{
    @Autowired
    private ApplicationContext container;

    public Object get(String id) {
        return container.getBean(id);
    }

    public ApplicationContext getContainer() {
        return container;
    }
}
