package repo;

import domain.Topic;

public class TopicRepository extends BaseRepository<Topic>
{
    
    public TopicRepository() {
        super(Topic.class);        
    }

}
