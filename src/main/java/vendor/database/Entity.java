package vendor.database;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Entity
{
    /**
     * Numele tabelului acestei entitati.
     *
     * @return
     */
    public String table();

    /**
     * Clasa de repository care va fi folosita pentru entitate.
     * Daca este goala, clasa implicita DatabaseRepository va fi folosita.
     *
     * @return
     */
    public String repository() default "";
}
