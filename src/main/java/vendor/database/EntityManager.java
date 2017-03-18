package vendor.database;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import domain.IHasId;

public class EntityManager
{
    private DatabaseManager manager;
    private HashMap<String, DatabaseRepository<? extends IHasId>> repos;

    /**
     * Creeaza un nou obiect folosind managerul de baze de date.
     *
     * @param manager
     */
    public EntityManager(DatabaseManager manager) {
        this.manager = manager;
        this.repos = new HashMap<>();
    }

    /**
     * Returneaza un repository dupa numele clasei la care apartine.
     * Repository-ul poate sa aiba si alte argumente pe langa cele celui implicit DatabaseRepository.
     */
    public <T extends IHasId> DatabaseRepository<T> getRepository(String clsName) {
        return this.getRepository(clsName, new Object[]{});
    }

    @SuppressWarnings("unchecked")
    /**
     * Returneaza un repository dupa numele clasei la care apartine.
     * Repository-ul poate sa aiba si alte argumente pe langa cele celui implicit DatabaseRepository.
     */
    public <T extends IHasId> DatabaseRepository<T> getRepository(String clsName, Object... args) {
        try {
            return this.getRepository((Class<T>) Class.forName(clsName), args);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Returneaza un repository dupa clasa la care apartine. Clasa trebuie sa fie anotata cu @Entity.
     * Repository-ul poate sa aiba si alte argumente pe langa cele celui implicit DatabaseRepository.
     *
     * @param cls
     * @param args
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T extends IHasId> DatabaseRepository<T> getRepository(Class<T> cls, Object... args) {
        if (!cls.isAnnotationPresent(Entity.class)) {
            throw new RuntimeException("Entity annotation not present for class");
        }
        if (this.repos.containsKey(cls.getName())) {
            return (DatabaseRepository<T>) this.repos.get(cls.getName());
        }

        Entity ann = cls.getAnnotation(Entity.class);
        String table = ann.table();
        String repo = ann.repository();

        if (repo.isEmpty()) {
            return this.getBasicRepository(cls, table);
        } else {
            try {
                Class<T> clsrepo = (Class<T>) Class.forName(repo);
                if (!DatabaseRepository.class.isAssignableFrom(clsrepo)) {
                    throw new ClassNotFoundException();
                }

                return this.getRepositoryFromConstructor(clsrepo, this.getRepositoryArguments(cls, table, args));
            } catch (ClassNotFoundException | SecurityException | IllegalArgumentException e) {
                throw new RuntimeException("Class " + repo + " is not a valid DatabaseRepository class.");
            }
        }
    }

    /**
     * Returneaza un nou DatabaseRepository pentru entitatea data.
     *
     * @param cls
     * @param table
     * @return
     */
    private <T extends IHasId> DatabaseRepository<T> getBasicRepository(Class<T> cls, String table) {
        ArrayList<FieldColumn> cols = new ArrayList<>();
        String idCol = this.getColumns(cls, cols);

        DatabaseRepository<T> dbrepo = new DatabaseRepository<>(manager, cls, table, idCol, cols.toArray(new FieldColumn[]{}));

        this.repos.put(cls.getName(), dbrepo);

        return dbrepo;
    }

    /**
     * Returneaza argumentele pentru repository.
     *
     * @param cls
     * @param table
     * @param args
     * @return
     */
    private <T extends IHasId> Object[] getRepositoryArguments(Class<T> cls, String table, Object... args) {
        ArrayList<FieldColumn> cols = new ArrayList<>();
        String idCol = this.getColumns(cls, cols);

        ArrayList<Object> arguments = new ArrayList<>();
        arguments.add(manager);
        arguments.add(cls);
        arguments.add(table);
        arguments.add(idCol);
        arguments.add(cols.toArray(new FieldColumn[]{}));
        arguments.addAll(Arrays.asList(args));

        return arguments.toArray();
    }

    /**
     * Returneaza repository-ul folosind constructorul.
     *
     * @param cls
     * @param args
     * @return
     */
    @SuppressWarnings("unchecked")
    private <T extends IHasId> DatabaseRepository<T> getRepositoryFromConstructor(Class<T> cls, Object[] args) {
        DatabaseRepository<T> dbrepo = null;
        for (Constructor<?> constr : cls.getConstructors()) {
            try {
                dbrepo = (DatabaseRepository<T>) constr.newInstance(args);
            } catch (Exception ex) {
                ex.printStackTrace();
                dbrepo = null;
            }
        }

        if (dbrepo == null) {
            throw new RuntimeException("Could not create repository for class " + cls.getName());
        }

        this.repos.put(cls.getName(), dbrepo);

        return dbrepo;
    }

    /**
     * Afla toate coloanele unei entitati sub forma unui array de obiecte FieldColumn.
     * Returneaza numele coloanei care este cheie primara.
     *
     * @param cls
     * @param cols
     * @return
     */
    private <T> String getColumns(Class<T> cls, ArrayList<FieldColumn> cols) {
        String idCol = null;

        for (Field field : cls.getDeclaredFields()) {
            if (field.isAnnotationPresent(Column.class)) {
                Column col = field.getAnnotation(Column.class);
                if (col.key()) {
                    idCol = col.value();
                }
                cols.add(new FieldColumn(field.getName(), col.value(), field.getType()));
            } else if (field.isAnnotationPresent(ManyToOne.class)) {
                ManyToOne col = field.getAnnotation(ManyToOne.class);

                cols.add(new FieldColumn(field.getName(), col.value(), field.getType()));
            }
        }

        return idCol;
    }
}
