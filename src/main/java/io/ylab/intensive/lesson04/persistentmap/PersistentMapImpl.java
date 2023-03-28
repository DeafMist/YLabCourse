package io.ylab.intensive.lesson04.persistentmap;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

/**
 * Класс, методы которого надо реализовать 
 */
public class PersistentMapImpl implements PersistentMap {
  
  private DataSource dataSource;

  private String mapName = "defaultName";

  public PersistentMapImpl(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Override
  public void init(String name) {
    this.mapName = name;
  }

  @Override
  public boolean containsKey(String key) throws SQLException {
    boolean flag = false;

    try (Connection connection = dataSource.getConnection();
         PreparedStatement statement = connection.prepareStatement("SELECT * FROM persistent_map WHERE map_name=? AND KEY=?")) {
      statement.setString(1, this.mapName);
      statement.setString(2, key);


      ResultSet resultSet = statement.executeQuery();

      if (resultSet.next()) {
        flag = true;
      }

      resultSet.close();
    }

    return flag;
  }

  @Override
  public List<String> getKeys() throws SQLException {
    List<String> keys = new ArrayList<>();

    try (Connection connection = dataSource.getConnection();
         PreparedStatement statement = connection.prepareStatement("SELECT KEY FROM persistent_map WHERE map_name=?")) {
      statement.setString(1, this.mapName);

      ResultSet resultSet = statement.executeQuery();

      while (resultSet.next()) {
        keys.add(resultSet.getString(1));
      }

      resultSet.close();
    }

    return keys;
  }

  @Override
  public String get(String key) throws SQLException {
    String value = null;

    try (Connection connection = dataSource.getConnection();
         PreparedStatement statement = connection.prepareStatement("SELECT value FROM persistent_map WHERE map_name=? AND KEY=?")) {
      statement.setString(1, this.mapName);
      statement.setString(2, key);

      ResultSet resultSet = statement.executeQuery();

      if (resultSet.next()) {
        value = resultSet.getString(1);
      }
    }

    return value;
  }

  @Override
  public void remove(String key) throws SQLException {
    try (Connection connection = dataSource.getConnection();
         PreparedStatement statement = connection.prepareStatement("DELETE FROM persistent_map WHERE map_name=? AND KEY=?")) {
      statement.setString(1, this.mapName);
      statement.setString(2, key);

      statement.executeUpdate();
    }
  }

  @Override
  public void put(String key, String value) throws SQLException {
    try (Connection connection = dataSource.getConnection();
         PreparedStatement statement = connection.prepareStatement(
                 "INSERT INTO persistent_map (map_name, KEY, value) " +
                 "VALUES (?, ?, ?)")) {
      remove(key);

      statement.setString(1, this.mapName);
      statement.setString(2, key);
      statement.setString(3, value);

      statement.executeUpdate();
    }
  }

  @Override
  public void clear() throws SQLException {
    try (Connection connection = dataSource.getConnection();
         PreparedStatement statement = connection.prepareStatement("DELETE FROM persistent_map WHERE map_name=?")) {
      statement.setString(1, this.mapName);

      statement.executeUpdate();
    }
  }
}
