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
      if (movie.getYear() != null) {
        preparedStatement.setInt(1, movie.getYear());
      } else {
        preparedStatement.setNull(1, Types.INTEGER);
      }

      if (movie.getLength() != null) {
        preparedStatement.setInt(2, movie.getLength());
      } else {
        preparedStatement.setNull(2, Types.INTEGER);
      }

      if (movie.getTitle() != null) {
        preparedStatement.setString(3, movie.getTitle());
      } else {
        preparedStatement.setNull(3, Types.VARCHAR);
      }

      if (movie.getSubject() != null) {
        preparedStatement.setString(4, movie.getSubject());
      } else {
        preparedStatement.setNull(4, Types.VARCHAR);
      }

      if (movie.getActors() != null) {
        preparedStatement.setString(5, movie.getActors());
      } else {
        preparedStatement.setNull(5, Types.VARCHAR);
      }

      if (movie.getActress() != null) {
        preparedStatement.setString(6, movie.getActress());
      } else {
        preparedStatement.setNull(6, Types.VARCHAR);
      }

      if (movie.getDirector() != null) {
        preparedStatement.setString(7, movie.getDirector());
      } else {
        preparedStatement.setNull(7, Types.VARCHAR);
      }

      if (movie.getPopularity() != null) {
        preparedStatement.setInt(8, movie.getPopularity());
      } else {
        preparedStatement.setNull(8, Types.INTEGER);
      }

      if (movie.getAwards() != null) {
        preparedStatement.setBoolean(9, movie.getAwards());
      } else {
        preparedStatement.setNull(9, Types.BOOLEAN);
      }

      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
