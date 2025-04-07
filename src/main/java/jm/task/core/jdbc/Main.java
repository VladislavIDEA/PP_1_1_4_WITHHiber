package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;
import org.hibernate.SessionFactory;

import java.sql.Connection;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getConnection;
import static jm.task.core.jdbc.util.Util.getSessionFactory;

public class Main {
    public static void main(String[] args) {
        SessionFactory sessionFactory = getSessionFactory();
        UserDao userDao = new UserDaoHibernateImpl(sessionFactory);
        UserService userService = new UserServiceImpl(userDao);
        userService.createUsersTable();
        userService.saveUser("Владислав", "Илясов", (byte) 25);
        userService.saveUser("Петр", "Петров", (byte) 30);
        userService.saveUser("Мария", "Сидорова", (byte) 28);
        userService.saveUser("Анна", "Никифорова", (byte) 22);
        List<User> users = userService.getAllUsers();
        for (User user : users) {
            System.out.println(user);
        }
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
