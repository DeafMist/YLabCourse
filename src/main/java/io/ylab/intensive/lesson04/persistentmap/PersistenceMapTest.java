package io.ylab.intensive.lesson04.persistentmap;

import io.ylab.intensive.lesson04.DbUtil;

import javax.sql.DataSource;
import java.sql.SQLException;

public class PersistenceMapTest {
  public static void main(String[] args) throws SQLException {
    DataSource dataSource = initDb();
    PersistentMap persistentMap = new PersistentMapImpl(dataSource);

    // Написать код демонстрации работы
    persistentMap.init("map1");

    System.out.println(persistentMap.containsKey("0")); // false
    persistentMap.put("1", "1");
    persistentMap.put("3", "3");

    persistentMap.init("map2");

    System.out.println(persistentMap.get("1")); // null
    persistentMap.put("1", "1");

    persistentMap.init("map1");
    persistentMap.clear();
    System.out.println(persistentMap.containsKey("3")); // false

    persistentMap.init("map2");
    System.out.println(persistentMap.getKeys()); // 1

    persistentMap.put("2", "2");
    persistentMap.put("3", "3");
    System.out.println(persistentMap.getKeys()); // 1 2 3
  }
  
  public static DataSource initDb() throws SQLException {
    String createMapTable = "" 
                                + "drop table if exists persistent_map; " 
                                + "CREATE TABLE if not exists persistent_map (\n"
                                + "   map_name varchar,\n"
                                + "   KEY varchar,\n"
                                + "   value varchar\n"
                                + ");";
    DataSource dataSource = DbUtil.buildDataSource();
    DbUtil.applyDdl(createMapTable, dataSource);
    return dataSource;
  }
}
