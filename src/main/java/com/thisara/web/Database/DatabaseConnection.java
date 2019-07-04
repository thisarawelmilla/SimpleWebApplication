package com.thisara.web.Database;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDataSource;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


public class DatabaseConnection {
    private static Logger logger = LogManager.getRootLogger();
    static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/Person";

    static final String USER = "root";
    static final String PASS_WORD = "";

    private static GenericObjectPool genericPool;
    private static final  DatabaseConnection DataCon = new DatabaseConnection();



    private DatabaseConnection(){


    }

    private DataSource getPool() throws Exception {
        Class.forName(DB_DRIVER);

        genericPool = new GenericObjectPool();
        genericPool.setMaxActive(4);

        ConnectionFactory con = new DriverManagerConnectionFactory(DB_URL, USER, PASS_WORD);

        PoolableConnectionFactory pcf = new PoolableConnectionFactory(con, genericPool, null, null, false, true);
        return new PoolingDataSource(genericPool);
    }




    public static void addToDb(Person person) throws Exception {
        DataSource dataSource = DataCon.getPool();
        Connection connection = dataSource.getConnection();

        try {

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
                logger.error("An error has occurred might");

            }
        }
    }
}

