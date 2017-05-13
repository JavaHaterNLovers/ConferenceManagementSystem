package service;

import domain.Edition;
import repo.BaseRepository;

/**
 * Created by NicoF on 4/4/2017.
 */
public class ConferenceService extends BaseDomainService<Edition, BaseRepository<Edition>>
{
    public ConferenceService(BaseRepository<Edition> repo) {
        super(repo);
    }

    public Edition add(Edition edition) {
        this.save(edition);

        return edition;
    }
}
