package domain;

public interface IHasId
{
    /**
     * Seteaza id-ul obiectului.
     *
     * @param id
     *   Noul id.
     */
    public void setId(int id);

    /**
     * Returneaza id-ul obiectului.
     *
     * @return
     *   Id-ul curent.
     */
    public int getId();
}
