package vendor;

public class Controller implements IHasContainer
{
    /**
     * Instanta containeru-lui.
     */
    private static Container container;

    /**
     * Instanta configurarii.
     */
    private static Config config;

    public Controller() {
        container = IHasContainer.super.getContainer();
        config = Config.getConfig();
    }

    @Override
    public Object get(String id) {
        return container.get(id);
    }

    @Override
    public Container getContainer() {
        return container;
    }

    /**
     * Returneaza un parametru din configurare.
     *
     * @param param
     * @return
     */
    public String getParameter(String param) {
        return config.getParameter(param);
    }
}
