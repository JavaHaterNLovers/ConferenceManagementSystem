package service;

import repo.BaseRepository;

public class BaseDomainService<T>
{
    private BaseRepository<T> repo;

    public BaseDomainService(BaseRepository<T> repo) {
        this.repo = repo;
    }
}
