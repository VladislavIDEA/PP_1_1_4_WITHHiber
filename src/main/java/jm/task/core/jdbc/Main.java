package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Владислав", "Илясов", (byte) 25);
        System.out.println("User с именем Владислав добавлен в базу данных");

        userService.saveUser("Петр", "Петров", (byte) 30);
        System.out.println("User с именем Петр добавлен в базу данных");

        userService.saveUser("Мария", "Сидорова", (byte) 28);
        System.out.println("User с именем Мария добавлен в базу данных");

        userService.saveUser("Анна", "Никифорова", (byte) 22);
        System.out.println("User с именем Анна добавлен в базу данных");

        List<User> users = userService.getAllUsers();
        for (User user : users) {
            System.out.println(user);
        }

        userService.cleanUsersTable();

        userService.dropUsersTable();
    }
}
