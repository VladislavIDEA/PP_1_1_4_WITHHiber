package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;
import java.util.function.Function;

public class UserDaoHibernateImpl implements UserDao {
    User user = new User();
    private final SessionFactory sessionFactory;

    public UserDaoHibernateImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private <T> T executeInTransaction(Function<Session, T> action) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            T result = action.apply(session);
            transaction.commit();
            return result;
        } catch (HibernateException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    @Override
    public void createUsersTable() {
        String sql = String.join(" ",
                "CREATE TABLE IF NOT EXISTS users (",
                "id BIGINT PRIMARY KEY AUTO_INCREMENT,",
                "name VARCHAR(128),",
                "last_name VARCHAR(128),",
                "age TINYINT)");

        executeInTransaction(session -> {
            session.createNativeQuery(sql).executeUpdate();
            return null;
        });
    }

    @Override
    public void dropUsersTable() {
        String sql = String.join(" ",
                "DROP TABLE IF EXISTS users");
        executeInTransaction(session -> {
            session.createNativeQuery(sql).executeUpdate();
            return null;
        });
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        user = new User(name, lastName, age);
        executeInTransaction(session -> {
            session.persist(user);
            return null;
        });
    }

    @Override
    public void removeUserById(long id) {
        user.setId(id);
        executeInTransaction(session -> {
            session.remove(user);
            return null;
        });
    }

    @Override
    public List<User> getAllUsers() {
        return executeInTransaction(session -> {
            return session.createQuery("FROM User", User.class).getResultList();
        });
    }

    @Override
    public void cleanUsersTable() {
        executeInTransaction(session -> {
            session.createQuery("DELETE FROM User").executeUpdate();
            return null;
        });
    }
}
