package service;

import repo.BaseRepository;

public abstract class BaseDomainService<T, R extends BaseRepository<T>>
{
    protected R repo;

    public BaseDomainService(R repo) {
        this.repo = repo;
    }

    public void save(T obj) {
        repo.save(obj);
    }

    /**
     * Sterge obiectul dat.
     *
     * @param obj
     */
    public void delete(T obj) {
        repo.delete(obj);
    }
}
