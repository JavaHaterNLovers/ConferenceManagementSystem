package vendor.database;

public class FieldColumn
{
    /**
     * Numele campului din entitate.
     */
    private String field;

    /**
     * Numele coloanei din tabel.
     */
    private String column;

    /**
     * Tipul campului din entitate.
     */
    private Class<?> type;

    /**
     * Creeaza un nou obiect cu numele campului, numele coloanei si tipul campului.
     *
     * @param field
     * @param column
     * @param type
     */
    public FieldColumn(String field, String column, Class<?> type) {
        super();
        this.field = field;
        this.column = column;
        this.type = type;
    }

    /**
     * Returneaza numele campului.
     *
     * @return
     */
    public String getField() {
        return field;
    }

    /**
     * Seteaza numele campului.
     *
     * @param field
     */
    public void setField(String field) {
        this.field = field;
    }

    /**
     * Returneaza numele coloanei.
     *
     * @return
     */
    public String getColumn() {
        return column;
    }

    /**
     * Seteaza numele coloanei.
     *
     * @param column
     */
    public void setColumn(String column) {
        this.column = column;
    }

    /**
     * Returneaza tipul campului.
     *
     * @return
     */
    public Class<?> getType() {
        return type;
    }

    /**
     * Seteaza tipul campului.
     *
     * @param type
     */
    public void setType(Class<?> type) {
        this.type = type;
    }
}
