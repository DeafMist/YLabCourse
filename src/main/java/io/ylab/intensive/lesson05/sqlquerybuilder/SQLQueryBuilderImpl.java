package io.ylab.intensive.lesson05.sqlquerybuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class SQLQueryBuilderImpl implements SQLQueryBuilder {
    private DataSource dataSource;

    public SQLQueryBuilderImpl(@Autowired DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Note: also we can do it with the help of select query
     * SELECT column_name FROM information_schema.columns WHERE table_name=?
    */
    @Override
    public String queryForTable(String tableName) throws SQLException {
        if (!getTables().contains(tableName)) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        try (Connection connection = dataSource.getConnection();
             ResultSet resultSet = connection.getMetaData().getColumns(null, null, tableName, null)) {
            while (resultSet.next()) {
                sb.append(resultSet.getString("column_name")).append(", ");
            }
        }

        return "SELECT " + sb.substring(0, sb.length() - 2).toString() + " FROM " + tableName;
    }

    /**
     * Note: this task will be done with
     * SELECT * FROM information_schema.tables
     */
    @Override
    public List<String> getTables() throws SQLException {
        List<String> tables = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             ResultSet resultSet = connection.getMetaData().getTables(null, null, null, new String[] {"TABLE"})) {
            while (resultSet.next()) {
                tables.add(resultSet.getString("table_name"));
            }
        }
        return tables;
    }
}
