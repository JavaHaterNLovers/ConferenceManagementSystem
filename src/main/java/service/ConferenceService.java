package service;

import domain.Conference;
import domain.User;
import repo.BaseRepository;


import javax.validation.ValidationException;
import java.util.Calendar;

/**
 * Created by NicoF on 4/4/2017.
 */
public class ConferenceService extends BaseDomainService<Conference, BaseRepository<Conference>>
{
    public ConferenceService(BaseRepository<Conference> repo) {
        super(repo);
    }

    public Conference add(String name, User author, Calendar beginSubmissions, Calendar endSubmissions,
      Calendar endBidding, Calendar endReview
    ) throws ValidationException {
        Conference conference = new Conference(name, author, beginSubmissions, endSubmissions, endBidding, endReview);
        this.save(conference);

        return conference;
    }
}
