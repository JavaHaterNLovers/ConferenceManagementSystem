package vendor.database;

import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import domain.IHasId;
import vendor.IRepository;

public class DatabaseRepository<T extends IHasId> extends Observable implements IRepository<T>
{
    /**
     * Managerul bazei de date.
     */
    protected DatabaseManager manager;

    /**
     * Clasa pentru care este repository.
     */
    protected Class<T> type;

    /**
     * Numele tabelului.
     */
    protected String table;

    /**
     * Numele coloanei care este cheie primara.
     */
    protected String idCol;

    /**
     * Coloanele entitatii.
     */
    protected FieldColumn[] cols;

    /**
     * Creeaza un nou obiect.
     * Clasele care extind acest repository vor trebui sa aiba exact aceste argumente in constructorul lor, putand sa adauge pe langa ele altele.
     * Obiecte de acest tip sunt construite de factory-ul EntityManager.
     *
     * @param manager
     * @param type
     * @param table
     * @param idCol
     * @param cols
     */
    public DatabaseRepository(DatabaseManager manager, Class<T> type, String table, String idCol, FieldColumn... cols) {
        super();
        this.manager = manager;
        this.type = type;
        this.table = table;
        this.idCol = idCol;
        this.cols = cols;
    }

    @Override
    public boolean add(T elem) {
        String query = "insert into " + table + " values(default,";

        for (int i = 1;i < cols.length;i++) {
            query += "?,";
        }

        query = query.substring(0, query.length() - 1) + ")";

        try {
            PreparedStatement ps = manager.getConnection().prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            for (int i = 1;i < cols.length;i++) {
                FieldColumn col = cols[i];
                String field = col.getField();
                Class<?> type = col.getType();
                String method = "get" + field.substring(0, 1).toUpperCase() + field.substring(1);

                this.setQueryParameter(i, ps, type, this.type.getMethod(method).invoke(elem));
            }

            ps.execute();

            ResultSet set = ps.getGeneratedKeys();

            if (set.next()) {
                elem.setId(set.getInt(1));
            }

            this.setChanged();
            this.notifyObservers(elem);

            return true;
        } catch (SQLException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Seteaza parametrii unui query in functie de tipul obiectului.
     */
    protected void setQueryParameter(int i, PreparedStatement ps, Class<?> type, Object result) throws SQLException {
        if (type.equals(Integer.TYPE) || type.isAssignableFrom(Integer.class)) {
            ps.setInt(i, (int) result);
        } else if (type.equals(Float.TYPE) || type.isAssignableFrom(Float.class)) {
            ps.setFloat(i, (float) result);
        } else if (type.equals(Double.TYPE) || type.isAssignableFrom(Double.class)) {
            ps.setDouble(i, (double) result);
        } else if (type.isAssignableFrom(String.class)) {
            ps.setString(i, (String) result);
        } else if (type.isAssignableFrom(Timestamp.class)) {
            ps.setTimestamp(i, (Timestamp) result);
        }
    }

    @Override
    public T get(int id) {
        String query = "select * from " + table + " where " + idCol + "= ?";
        try {
            PreparedStatement ps = manager.getConnection().prepareStatement(query);
            ps.setInt(1, id);
            ResultSet results = ps.executeQuery();

            if (results.next()) {
                return this.createInstance(results);
            }
        } catch (SQLException | InstantiationException | IllegalAccessException
            | IllegalArgumentException | InvocationTargetException
            | NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Creeaza o noua instanta de obiect din rezultatele unui query folosiind anotatiile @Column care
     * exista pe campurile din clasa obiectului.
     * Clasa trebuie sa contina set-eri pentru fielduri sub forma camelCase.
     *
     * @param results
     * @return
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws SecurityException
     * @throws InstantiationException
     * @throws SQLException
     */
    protected T createInstance(ResultSet results) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, InstantiationException, SQLException {
        T instance = type.newInstance();

        for (int i = 0;i < cols.length;i++) {
            FieldColumn col = cols[i];
            String field = col.getField();
            String method = "set" + field.substring(0, 1).toUpperCase() + field.substring(1);
            Object result = getResult(results, i + 1, col.getType());
            type.getMethod(method, col.getType()).invoke(instance, result);
        }

        return instance;
    }

    /**
     * Returneaza resultatul unei coloane din query in functie de tipul campului.
     *
     * @param results
     * @param col
     * @param type
     * @return
     * @throws SQLException
     */
    protected Object getResult(ResultSet results, int col, Class<?> type) throws SQLException {
        if (type.equals(Integer.TYPE) || type.isAssignableFrom(Integer.class)) {
            return results.getInt(col);
        } else if (type.equals(Float.TYPE) || type.isAssignableFrom(Float.class)) {
            return results.getFloat(col);
        } else if (type.equals(Double.TYPE) || type.isAssignableFrom(Double.class)) {
            return results.getDouble(col);
        } else if (type.isAssignableFrom(String.class)) {
            return results.getString(col);
        } else if (type.isAssignableFrom(Timestamp.class)) {
            return results.getTimestamp(col);
        }

        return null;
    }

    @Override
    public boolean update(int id, T newElem) {
        String query = "update " + table + " set ";

        for (int i = 1;i < cols.length;i++) {
            query += cols[i].getColumn() + " = ?,";
        }

        query = query.substring(0, query.length() - 1) + " where " + idCol + " = ?";

        try {
            PreparedStatement ps = this.manager.getConnection().prepareStatement(query);
            int i;
            for (i = 1;i < cols.length;i++) {
                FieldColumn col = cols[i];
                String field = col.getField();
                Class<?> type = col.getType();
                String method = "get" + field.substring(0, 1).toUpperCase() + field.substring(1);

                Object result = this.type.getMethod(method).invoke(newElem);
                this.setQueryParameter(i, ps, type, result);
            }

            ps.setInt(i, id);
            ps.execute();
            newElem.setId(id);

            this.setChanged();
            this.notifyObservers(newElem);

            return true;
        } catch (SQLException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
        }


        return false;
    }

    @Override
    public T delete(int id) {
        T elem = this.get(id);

        if (elem == null) {
            return null;
        }

        String query = "delete from " + table + " where " + idCol + " = ?";

        PreparedStatement ps;
        try {
            ps = this.manager.getConnection().prepareStatement(query);
            ps.setInt(1, id);

            ps.execute();
            this.setChanged();
            this.notifyObservers(elem);

            return elem;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public int size() {
        String query = "select count(*) from  " + table;
        try {
            ResultSet result = this.manager.getConnection().prepareStatement(query).executeQuery();

            if (result.next()) {
                return result.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public List<T> all() {
        String query = "select * from " + table;
        try {
            ArrayList<T> list = new ArrayList<>();
            PreparedStatement ps = manager.getConnection().prepareStatement(query);
            ResultSet results = ps.executeQuery();

            while (results.next()) {
                list.add(this.createInstance(results));
            }

            return list;
        } catch (SQLException | InstantiationException | IllegalAccessException
            | IllegalArgumentException | InvocationTargetException
            | NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Returneaza un numar de elemente incepand de la pozitia data.
     *
     * @param start
     * @param limit
     * @return
     */
    public List<T> all(int start, int limit)
    {
        String query = "select * from " + table + " order by " + idCol + " offset " + start + " rows fetch next " + limit + " rows only";
        try {
            ArrayList<T> list = new ArrayList<>();
            PreparedStatement ps = manager.getConnection().prepareStatement(query);
            ResultSet results = ps.executeQuery();

            while (results.next()) {
                list.add(this.createInstance(results));
            }

            return list;
        } catch (SQLException | InstantiationException | IllegalAccessException
            | IllegalArgumentException | InvocationTargetException
            | NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
        }

        return null;
    }
}
