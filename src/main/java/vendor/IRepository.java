package vendor;

import java.util.List;

import domain.IHasId;

public interface IRepository<T extends IHasId>
{
    /**
     * Adauga un nou element si returneaza daca a fost adaugat sau nu.
     *
     * @param elem
     *   Elementul de adaugat.
     * @return
     *   Fals daca exista un element cu acelasi id.
     */
	boolean add(T elem);

    /**
     * Returneaza elementul cu id-ul respectiv sau NULL daca nu este gasit.
     *
     * @param id
     *   Id-ul elementului.
     * @return
     *   Elementul la pozitia respectiva.
     */
	T get(int id);

    /**
     * Updateaza elementul cu id-ul respectiv si returneaza daca s-a putut updata sau nu.
     * Id-ul noului element va fi acelasi ca si id-ul vechiului element.
     *
     * @param id
     *   Id-ul elementului.
     * @param elem
     *   Noul element.
     * @return
     *   Fals daca elementul cu acel id nu exista.
     */
	boolean update(int id, T newElem);

    /**
     * Sterge un element dupa id.
     *
     * @param id
     *   Id-ul elementului.
     * @return
     *   Elementul sters asu NULL daca nu exista.
     */
	T delete(int id);

    /**
     * Returneaza numarul de elemente.
     *
     * @return
     *   Numarul de elemente.
     */
	public int size();

	/**
	 * Returneaza toate elementele din repozitory.
	 *
	 * @return
	 *   O lista cu toate elementele.
	 */
	public List<T> all();
}
