package io.ylab.intensive.lesson04.filesort;

import java.io.*;
import java.nio.file.Files;
import java.sql.*;
import java.util.Scanner;
import javax.sql.DataSource;

public class FileSortImpl implements FileSorter {
  private DataSource dataSource;

  public FileSortImpl(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Override
  public File sort(File data) {
    saveNumbers(data);

    return saveSortedNumbersToFile();
  }

  private File saveSortedNumbersToFile() {
    File sortedFile = new File("src/main/java/io/ylab/intensive/lesson04/filesort/sortedFile.txt");

    try (Connection connection = dataSource.getConnection();
         Statement statement = connection.createStatement())  {
      Files.deleteIfExists(sortedFile.toPath());

      ResultSet resultSet = statement.executeQuery("SELECT val FROM numbers ORDER BY val DESC");

      PrintWriter pw = new PrintWriter(sortedFile);

      while (resultSet.next()) {
        pw.println(resultSet.getLong(1));
      }

      pw.flush();
      pw.close();
      resultSet.close();
    } catch (SQLException | IOException e) {
      e.printStackTrace();
    }

    return sortedFile;
  }

  private void saveNumbers(File data) {
    final int BATCH_SIZE = 1000;

    try (Connection connection = dataSource.getConnection()) {
      connection.setAutoCommit(false);

      try (Scanner scanner = new Scanner(new FileInputStream(data));
           PreparedStatement statement = connection.prepareStatement("INSERT INTO numbers (val) VALUES (?)")) {
        while (scanner.hasNextLong()) {
          int i = 1;
          while (scanner.hasNextLong() && i <= BATCH_SIZE) {
            long number = scanner.nextLong();
            statement.setLong(1, number);
            statement.addBatch();
            i++;
          }
          try {
            statement.executeBatch();
            connection.commit();
          } catch (BatchUpdateException e) {
            e.printStackTrace();
          }
        }
      }
      catch (FileNotFoundException e) {
        e.printStackTrace();
      }
    }
    catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
