package domain;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name="topics")
public class Topic
{
    @Id
    @Column(unique = true)
    @NotBlank(message="{topic.name}")
    private String name;

    public Topic() {
        this(null);
    }

    /**
     * Creeaza un Topic.
     *
     * @param name
     */
    public Topic(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}