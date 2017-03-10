package vendor.database;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Column
{
    /**
     * Numele coloanei.
     *
     * @return
     */
    public String value();

    /**
     * Daca coloana este cheie primara sau nu.
     *
     * @return
     */
    public boolean key() default false;

    /**
     * Daca coloana poate avea valori nule.
     * @return
     */
    public boolean nullable() default false;

    /**
     * Tipul coloanei.Daca este gol, tipul va fi dat in functie de tipul variabilei.
     * @return
     */
    public String type() default "";
}
