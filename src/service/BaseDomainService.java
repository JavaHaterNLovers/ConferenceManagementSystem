package service;

import java.util.List;
import java.util.Observable;

import javax.validation.ValidationException;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;

import repo.BaseRepository;

public abstract class BaseDomainService<T, R extends BaseRepository<T>> extends Observable
{
    @Autowired
    Validator validator;

    protected R repo;

    public BaseDomainService(R repo) {
        this.repo = repo;
    }

    public void save(T obj) throws ValidationException {
        validator.validate(obj);
        repo.save(obj);
        this.update(obj);
    }

    public void save(T obj, Class<?>... groups) throws ValidationException {
        validator.validate(obj, groups);
        repo.save(obj);
        this.update(obj);
    }

    /**
     * Sterge obiectul dat.
     *
     * @param obj
     */
    public void delete(T obj) {
        repo.delete(obj);
        this.update(obj);
    }

    /**
     * Returneaza toate obiectele.
     *
     * @return
     */
    public List<T> all() {
        return repo.all();
    }

    /**
     * Returneaza numarul de elemente.
     *
     * @return
     */
    public long size() {
        return repo.size();
    }

    /**
     * Updateaza toti observatori de schimbari
     */
    protected void update(Object obj) {
        this.setChanged();
        this.notifyObservers(obj);
    }
}
