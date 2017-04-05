package service;

import domain.Conference;
import domain.User;
import repo.BaseRepository;


import javax.validation.ValidationException;
import java.time.LocalDate;
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


    /**
     * @param localDate local date
     * @return calendar || null if localDate is null
     * Convert localData object to Calendar
     */
    public Calendar getCalendarFromLocalDate(LocalDate localDate){
        if (localDate == null){
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(java.sql.Date.valueOf(localDate));
        return calendar;
    }
}
