package service;

import domain.Edition;
import domain.User;
import repo.BaseRepository;


import javax.validation.ValidationException;
import java.time.LocalDate;
import java.util.Calendar;

/**
 * Created by NicoF on 4/4/2017.
 */
public class ConferenceService extends BaseDomainService<Edition, BaseRepository<Edition>>
{
    public ConferenceService(BaseRepository<Edition> repo) {
        super(repo);
    }

    public Edition add(String name, User author, Calendar beginSubmissions, Calendar endSubmissions,
      Calendar endBidding, Calendar endReview
    ) throws ValidationException {
        Edition conference = new Edition(name, author, beginSubmissions, endSubmissions, endBidding, endReview);
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
