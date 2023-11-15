package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();

        userService.createUsersTable();

        userService.saveUser("Alex", "Alex", (byte) 10);
        userService.saveUser("Ben", "Ben", (byte) 20);
        userService.saveUser("Cris", "Cris", (byte) 30);
        userService.saveUser("Den", "Den", (byte) 40);

        userService.getAllUsers();

        //userService.cleanUsersTable();

        //userService.dropUsersTable();
    }
}
