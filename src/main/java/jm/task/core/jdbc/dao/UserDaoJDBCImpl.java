package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Connection connection = Util.getConnection();
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.execute(
                    "create table users (ID bigint primary key auto_increment, NAME varchar(20), LASTNAME varchar(20), AGE tinyint)");
            System.out.println("таблица успешно создана...");
        } catch (SQLException e) {
            System.out.println("не удалось создать таблицу...");
        }
    }

    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("drop table users");
            System.out.println("таблица успешно удалена...");
        } catch (SQLException e) {
            System.out.println("не удалось удалить таблицу...");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement preparedStatement
                     = connection.prepareStatement(
                             "insert into users (NAME, LASTNAME, AGE) values (?, ?, ?)")) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);

            preparedStatement.executeUpdate();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            System.out.println("не удалось добавить запись...");
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement preparedStatement
                     = connection.prepareStatement("delete from users where ID = ?")) {
            preparedStatement.setLong(1, id);
            System.out.println("запись успешно удалена...");
        } catch (SQLException e) {
            System.out.println("не удалось удалить запись...");
        }
    }

    public List<User> getAllUsers() {
        List<User> usersList = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("select * from users");
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("ID"));
                user.setName(resultSet.getString("NAME"));
                user.setLastName(resultSet.getString("LASTNAME"));
                user.setAge(resultSet.getByte("AGE"));
                usersList.add(user);
                System.out.println(user.toString());
            }
        } catch (Exception e) {
            System.out.println("не удалось прочитать таблицу...");
        }
        return usersList;
    }

    public void cleanUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("truncate table users");
            System.out.println("таблица успешно очищена...");
        } catch (Exception e) {
            System.out.println("не удалось очистить таблицу...");
        }
    }
}
