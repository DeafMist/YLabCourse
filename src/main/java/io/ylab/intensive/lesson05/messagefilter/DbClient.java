package io.ylab.intensive.lesson05.messagefilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;

@Component
public class DbClient {
    private String tableName = "bad_word";

    private DataSource dataSource;

    public DbClient(@Autowired DataSource dataSource) {
        this.dataSource = dataSource;

        createTableIfNotExists();

        initTable();
    }

    public boolean findWord(String word) {
        String selectQuery = "SELECT * FROM " + tableName + " WHERE word=?";

        boolean res = false;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(selectQuery)) {
            statement.setString(1, word);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                res = true;
            }

            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return res;
    }

    private void initTable() {
        File file = new File("src/main/java/io/ylab/intensive/lesson05/messagefilter/words.txt");
        String insertQuery = "INSERT INTO " + tableName + " (id, word) VALUES (?, ?)";
        String deleteQuery = "DELETE FROM " + tableName;

        try (Scanner scanner = new Scanner(new FileInputStream(file));
             Connection connection = dataSource.getConnection();
             Statement deleteStatement = connection.createStatement();
             PreparedStatement statement = connection.prepareStatement(insertQuery)) {

            deleteStatement.executeUpdate(deleteQuery);

            long i = 1L;
            while (scanner.hasNextLine()) {
                String word = scanner.nextLine().toLowerCase();
                statement.setString(2, word);
                statement.setLong(1, i++);
                statement.executeUpdate();
            }
        } catch (SQLException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void createTableIfNotExists() {
        String ddl = ""
                + "create table " + tableName + " ("
                + "id bigint primary key, "
                + "word varchar)";

        try (Connection connection = dataSource.getConnection();
             ResultSet resultSet = connection.getMetaData().getTables(null, null, tableName, new String[] {"TABLE"})) {
            if (!resultSet.next()) {
                Statement statement = connection.createStatement();
                statement.execute(ddl);
                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
