package vendor.database;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Set;

import org.reflections.Reflections;

public class DatabaseManager
{
    /**
     * Numele bazei de date.
     */
    private String database;

    /**
     * Conexiunea la baza de date.
     */
    private Connection con;

    /**
     * Initializeaza un nou obiect si conecteaza-te la serverul si baza de date folosind userul si parola.
     *
     * @param server
     * @param database
     * @param user
     * @param pass
     */
    public DatabaseManager(String server, String database, String user, String pass) {
        this.database = database;
        String connectionUrl = "jdbc:mysql://" + server;

        try {
            con = DriverManager.getConnection(connectionUrl, user, pass);

            PreparedStatement ps = con.prepareStatement("select * from INFORMATION_SCHEMA.SCHEMATA where SCHEMA_NAME = ?");
            ps.setString(1, database);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                ps = con.prepareStatement("create database " + database);
                ps.execute();
            }
            ps = con.prepareStatement("use " + database);
            ps.execute();

            createTables();
        } catch (SQLException ex) {
            throw new RuntimeException("An error occured while trying to connect to the database or create the tables.");
        }
    }

    /**
     * Returneaza conexiunea la baza de date.
     *
     * @return
     */
    public Connection getConnection() {
        return this.con;
    }

    /**
     * Creeaza tabelele pentru entitatile din package-ul domain care sunt anotate cu @Entity daca ele nu exista deja.
     * Informatiile pentru coloanele tabelelor provin din anotarile @Column din interiorul claselor.
     *
     * @throws SQLException
     */
    private void createTables() throws SQLException {
        Reflections ref = new Reflections("domain");
        Set<Class<?>> entities = ref.getTypesAnnotatedWith(Entity.class);
        ArrayList<String> queries = new ArrayList<>();

        for (Class<?> cls : entities) {
            Entity an = cls.getAnnotation(Entity.class);
            String table = an.table();

            PreparedStatement ps = con.prepareStatement("select * from information_schema.tables where table_schema = ? and table_name = ?");
            ps.setString(1, this.database);
            ps.setString(2, table);
            ResultSet set = ps.executeQuery();

            if (!set.next()) {
                int nbqueries = queries.size();
                String create = this.getCreateTableQuery(cls, table, queries);

                if (queries.size() == nbqueries) {
                    ps = con.prepareStatement(create);
                    ps.execute();
                }
            }
        }

        for (String query : queries) {
            PreparedStatement ps = con.prepareStatement(query);
            ps.execute();
        }
    }

    /**
     * Returneaza query-ul folosit pentru a creea tabelul pentru acea clasa.Colonele din tabel sunt cele care apar in anotarile @Column din
     * interiorul clasei.
     * Daca clasa contine anotari de tipul @ManyToOne, atunci tabelul va fi creat dupa ce dependintele sale au fost creeate.
     *
     * @param cls
     * @param table
     * @param queries
     * @return
     */
    private String getCreateTableQuery(Class<?> cls, String table, ArrayList<String> queries) {
        boolean hasReference = false;

        String create = "create table " + table + "(";
        for (Field field : cls.getDeclaredFields()) {
            if (field.isAnnotationPresent(Column.class)) {
                Column col = field.getAnnotation(Column.class);
                String type = col.type();
                if (col.type().isEmpty()) {
                    type = this.getColumnType(field);
                }
                create += col.value() + " " + type + " " + (col.key() ? "AUTO_INCREMENT primary key " : "")
                    + (!col.nullable() ? "not null" : "") + ",";
            } else if (field.isAnnotationPresent(ManyToOne.class)) {
                ManyToOne col = field.getAnnotation(ManyToOne.class);
                create += col.value() + " int foreign key references " + col.table() + "(" + col.column() + ") not null,";
                hasReference = true;
            }
        }

        create = create.substring(0, create.length() - 1);
        create += ");";

        if (hasReference) {
            queries.add(create);
        }

        return create;
    }

    /**
     * Returneaza tipul unei coloane din tipul campului.Arunca exceptie daca tipul nu este unul recunoscut.
     *
     * @param field
     * @return
     */
    private String getColumnType(Field field) {
        String type = null;
        Class<?> cls = field.getType();

        if (cls.equals(Integer.TYPE) || cls.isAssignableFrom(Integer.class)) {
            type = "int";
        } else if (cls.equals(Float.TYPE) || cls.isAssignableFrom(Float.class)) {
            type = "float";
        } else if (cls.equals(Double.TYPE) || cls.isAssignableFrom(Double.class)) {
            type = "double";
        } else if (cls.isAssignableFrom(String.class)) {
            type = "varchar(255)";
        }

        if (type == null) {
            throw new RuntimeException("Unsuported type found for Column annotation on field " + field.getName() + " from class " + cls.getName());
        }

        return type;
    }
}
