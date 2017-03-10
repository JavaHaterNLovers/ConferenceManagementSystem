package repo;

import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import domain.User;
import domain.User.UserRole;
import vendor.database.DatabaseManager;
import vendor.database.DatabaseRepository;
import vendor.database.FieldColumn;

public class UserRepository extends DatabaseRepository<User>
{
    /**
     * Creeaza un nou repository cu un manager, clasa userului, numele tabelului, coloana id si coloanele entitatii.
     *
     * @param manager
     * @param type
     * @param table
     * @param idCol
     * @param cols
     */
    public UserRepository(DatabaseManager manager, Class<User> type, String table, String idCol, FieldColumn[] cols) {
        super(manager, type, table, idCol, cols);
    }

    /**
     * Returneaza un user dupa numele de utilizator.
     *
     * @param username
     * @return
     */
    public User getByUsername(String username) {
        String query = "select * from " + table + " where ";

        for (FieldColumn col : cols) {
            if (col.getField() == "username") {
                query += col.getColumn() + " = ?";
                break;
            }
        }

        try {
            PreparedStatement ps = this.manager.getConnection().prepareStatement(query);
            ps.setString(1, username);

            ResultSet result = ps.executeQuery();

            if (result.next()) {
                return this.createInstance(result);
            }
        } catch (SQLException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | InstantiationException e) {
        }

        return null;
    }

    @Override
    protected void setQueryParameter(int i, PreparedStatement ps, Class<?> type, Object result) throws SQLException {
        if (type.isAssignableFrom(UserRole.class)) {
            ps.setString(i, result.toString().toLowerCase());
        } else {
            super.setQueryParameter(i, ps, type, result);
        }
    }

    @Override
    protected Object getResult(ResultSet results, int col, Class<?> type) throws SQLException {
        if (type.isAssignableFrom(UserRole.class)) {
            String res = results.getString(col);
            for (UserRole role : UserRole.values()) {
                if (role.toString().toLowerCase().equals(res)) {
                    return role;
                }
            }
        }

        return super.getResult(results, col, type);
    }
}
