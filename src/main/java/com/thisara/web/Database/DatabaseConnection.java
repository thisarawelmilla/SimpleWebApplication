package com.thisara.web.Database;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDataSource;
import org.apache.commons.pool.impl.GenericObjectPool;


public class DatabaseConnection {
    static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/Person";

    static final String USER = "root";
    static final String PASS_WORD = "";

    private static GenericObjectPool genericPool;

    public DataSource initialPool() throws Exception {
        Class.forName(DB_DRIVER);

        genericPool = new GenericObjectPool();
        genericPool.setMaxActive(4);

        ConnectionFactory con = new DriverManagerConnectionFactory(DB_URL, USER, PASS_WORD);

        PoolableConnectionFactory pcf = new PoolableConnectionFactory(con, genericPool, null, null, false, true);
        return new PoolingDataSource(genericPool);
    }

    public static void addToDb(Person person) {
        Connection connection = null;
        DatabaseConnection DataCon = new DatabaseConnection();


        try {
            DataSource dataSource = DataCon.initialPool();
            connection = dataSource.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Person.person (id ,firstName) VALUES (? , ? )");
            preparedStatement.setInt(1, person.getId());
            preparedStatement.setString(2, person.getFirstName());
            preparedStatement.executeUpdate();

        } catch (Exception sqlException) {
            sqlException.printStackTrace();

       } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception sqlException) {
                sqlException.printStackTrace();

            }
        }
    }
}