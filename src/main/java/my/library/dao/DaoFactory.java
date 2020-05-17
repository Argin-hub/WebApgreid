package my.library.dao;

import my.library.jdbc.ConnectionPool;
import my.library.jdbc.exception.ResourcesException;

import java.sql.Connection;
import java.sql.SQLException;

public class DaoFactory implements AutoCloseable {

    private final ConnectionPool connectionPool;

    private Connection connection;

    public DaoFactory() {
        connectionPool = ConnectionPool.getInstance();
        try {
            connection = connectionPool.getConnection();
        } catch (ResourcesException e) {
            e.printStackTrace();
        }
    }

    public MySqlPerson getPersonDao() {
        MySqlPerson mySqlPerson = new MySqlPerson();
        mySqlPerson.setConnection(connection);
        return mySqlPerson;
    }

    public MySqlUser getUserDao() {
        MySqlUser mySqlUser = new MySqlUser();
        mySqlUser.setConnection(connection);
        return mySqlUser;
    }

    public MySqlUserRole getUserRoleDao() {
        MySqlUserRole mySqlUserRole = new MySqlUserRole();
        mySqlUserRole.setConnection(connection);
        return mySqlUserRole;
    }

    public MySqlAuthor getAuthorDao() {
        MySqlAuthor mySqlAuthor = new MySqlAuthor();
        mySqlAuthor.setConnection(connection);
        return mySqlAuthor;
    }

    public MySqlGenre getGenreDao() {
        MySqlGenre mySqlGenre = new MySqlGenre();
        mySqlGenre.setConnection(connection);
        return mySqlGenre;
    }

    public MySqlBook getBookDao() {
        MySqlBook mySqlBook = new MySqlBook();
        mySqlBook.setConnection(connection);
        return mySqlBook;
    }

    public void returnConnect() {
        connectionPool.returnConnection(connection);
    }

    public void startTransaction() throws Exception {
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new Exception("Cannot starting date transaction", e);
        }
    }

    public void commitTransaction() throws Exception {
        try {
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            throw new Exception("Cannot committing date transaction", e);
        }
    }


    public void rollbackTransaction() throws Exception {
        try {
            connection.rollback();
        } catch (SQLException e) {
            throw new Exception("Cannot rollback date transaction", e);
        }
    }

    @Override
    public void close() {
        returnConnect();
    }
}
