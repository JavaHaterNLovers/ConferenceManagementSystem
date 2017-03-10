package vendor;

public interface IHasContainer
{
    /**
     * Returneaza serviciul din container dupa id.
     *
     * @param id
     *   Id-ul serviciului.
     *
     * @return
     *   Serviciul propriu-zis.
     * @throws Exception
     */
    public Object get(String id) throws Exception;

    /**
     * Returneaza containerul.
     *
     * @return
     *   Containerul propriu-zis.
     */
    default public Container getContainer() {
        return Container.getContainer();
    }
}
