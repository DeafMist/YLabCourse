package io.ylab.intensive.lesson05.eventsourcing.db;

import io.ylab.intensive.lesson05.eventsourcing.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class DbClient {
    private DataSource dataSource;

    public DbClient(@Autowired DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void savePerson(Person person) {
        final String insertQuery = "INSERT INTO person (first_name, last_name, middle_name, person_id) VALUES (?, ?, ?, ?)";
        final String updateQuery = "UPDATE person SET first_name=?, last_name=?, middle_name=? WHERE person_id=?";
        final String selectQuery = "SELECT * FROM person WHERE person_id=?";

        try (java.sql.Connection connection = dataSource.getConnection();
             PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
             PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
             PreparedStatement selectStatement = connection.prepareStatement(selectQuery)) {
            selectStatement.setLong(1, person.getId());

            ResultSet selectSet = selectStatement.executeQuery();

            if (selectSet.next()) {
                sendStatement(person, updateStatement, "обновление");
            }
            else {
                sendStatement(person, insertStatement, "добавление");
            }

            selectSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void sendStatement(Person person, PreparedStatement updateStatement, String message) throws SQLException {
        updateStatement.setString(1, person.getName());
        updateStatement.setString(2, person.getLastName());
        updateStatement.setString(3, person.getMiddleName());
        updateStatement.setLong(4, person.getId());

        updateStatement.executeUpdate();
        System.out.println("Произошло " + message + " данных человека с id = " + person.getId());
    }

    public void deletePerson(long personId) {
        try (java.sql.Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM person WHERE person_id=?")) {
            statement.setLong(1, personId);

            int res = statement.executeUpdate();
            if (res == 0) {
                System.out.println("Была попытка удаления человека с номером id = " + personId +
                        ", но данные о данном человеке были не найдены!");
            }
            else {
                System.out.println("Удаление человека с id = " + personId + " прошло успешно!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
