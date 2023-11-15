package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        Util util = new Util();
        try (Statement statement = util.getConnection().createStatement()) {
            statement.execute(
                    "create table users (ID bigint primary key auto_increment, NAME varchar(20), LASTNAME varchar(20), AGE tinyint)");
            System.out.println("таблица успешно создана...");
        } catch (SQLException e) {
            System.out.println("не удалось создать таблицу...");
        } finally {
            util.closeConnection(util.getConnection());
        }
    }

    public void dropUsersTable() {
        Util util = new Util();
        try (Statement statement = util.getConnection().createStatement()) {
            statement.executeUpdate("drop table users");
            System.out.println("таблица успешно удалена...");
        } catch (SQLException e) {
            System.out.println("не удалось удалить таблицу...");
        } finally {
            util.closeConnection(util.getConnection());
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        Util util = new Util();
        Connection connection = util.getConnection();
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
        } finally {
            util.closeConnection(connection);
        }
    }

    public void removeUserById(long id) {
        Util util = new Util();
        try (PreparedStatement preparedStatement
                     = util.getConnection().prepareStatement("delete from users where ID = ?")) {
            preparedStatement.setLong(1, id);
            System.out.println("запись успешно удалена...");
        } catch (SQLException e) {
            System.out.println("не удалось удалить запись...");
        } finally {
            util.closeConnection(util.getConnection());
        }
    }

    public List<User> getAllUsers() {
        Util util = new Util();
        List<User> usersList = new ArrayList<>();
        try (Statement statement = util.getConnection().createStatement()) {
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
        } finally {
            util.closeConnection(util.getConnection());
        }
        return usersList;
    }

    public void cleanUsersTable() {
        Util util = new Util();
        try (Statement statement = util.getConnection().createStatement()) {
            statement.executeUpdate("truncate table users");
            System.out.println("таблица успешно очищена...");
        } catch (Exception e) {
            System.out.println("не удалось очистить таблицу...");
        } finally {
            util.closeConnection(util.getConnection());
        }
    }
}
