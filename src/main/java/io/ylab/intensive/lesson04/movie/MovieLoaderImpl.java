package io.ylab.intensive.lesson04.movie;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;
import javax.sql.DataSource;

public class MovieLoaderImpl implements MovieLoader {
  private DataSource dataSource;

  public MovieLoaderImpl(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Override
  public void loadData(File file) {
    try (Scanner scanner = new Scanner(new FileInputStream(file))) {
      scanner.nextLine();
      scanner.nextLine();

      while (scanner.hasNextLine()) {
        String[] line = scanner.nextLine().split(";");

        Movie movie = getMovie(line);

        saveMovie(movie);
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  private Movie getMovie(String[] line) {
    Movie movie = new Movie();

    movie.setYear((!line[0].isEmpty() ? Integer.parseInt(line[0]) : null));
    movie.setLength((!line[1].isEmpty() ? Integer.parseInt(line[1]) : null));
    movie.setTitle((!line[2].isEmpty() ? line[2] : null));

    movie.setSubject((!line[3].isEmpty() ? line[3] : null));
    movie.setActors((!line[4].isEmpty() ? line[4] : null));
    movie.setActress((!line[5].isEmpty() ? line[5] : null));

    movie.setDirector((!line[6].isEmpty() ? line[6] : null));
    movie.setPopularity((!line[7].isEmpty() ? Integer.parseInt(line[7]) : null));
    movie.setAwards((!line[8].isEmpty() ? Boolean.valueOf(line[8]) : null));

    return movie;
  }

  private void saveMovie(Movie movie) {
    String insertQuery = "INSERT INTO movie " +
            "(year, length, title, subject, actors, actress, director, popularity, awards) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    try (Connection connection = dataSource.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

      setIntOrNull(preparedStatement, movie.getYear(), 1);

      setIntOrNull(preparedStatement, movie.getLength(), 2);

      setStringOrNull(preparedStatement, movie.getTitle(), 3);

      setStringOrNull(preparedStatement, movie.getSubject(), 4);

      setStringOrNull(preparedStatement, movie.getActors(), 5);

      setStringOrNull(preparedStatement, movie.getActress(), 6);

      setStringOrNull(preparedStatement, movie.getDirector(), 7);

      setIntOrNull(preparedStatement, movie.getPopularity(), 8);

      setBoolOrNull(preparedStatement, movie.getAwards(), 9);

      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  private void setIntOrNull(PreparedStatement preparedStatement, Integer field, int i) throws SQLException {
    if (field != null) {
      preparedStatement.setInt(i, field);
    }
    else {
      preparedStatement.setNull(i, Types.INTEGER);
    }
  }

  private void setStringOrNull(PreparedStatement preparedStatement, String field, int i) throws SQLException {
    if (field != null) {
      preparedStatement.setString(i, field);
    }
    else {
      preparedStatement.setNull(i, Types.VARCHAR);
    }
  }

  private void setBoolOrNull(PreparedStatement preparedStatement, Boolean field, int i) throws SQLException {
    if (field != null) {
      preparedStatement.setBoolean(i, field);
    }
    else {
      preparedStatement.setNull(i, Types.BOOLEAN);
    }
  }
}
