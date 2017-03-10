package vendor.database;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(FIELD)
public @interface ManyToOne
{
    /**
     * Numele coloanei.
     *
     * @return
     */
    public String value();

    /**
     * Numele tabelului catre care exista referinta.
     *
     * @return
     */
    public String table();

    /**
     * Numele coloanei din tabelul de referinta care este cheie primara.
     *
     * @return
     */
    public String column();
}
