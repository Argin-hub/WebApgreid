package my.library.dao;

import my.library.entity.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MySqlUser extends BaseDao<User> {

    private static final String USER = "user";
    private static final String ID_USER = "id_user";
    private static final String REGISTER_DATE = "register_date";
    private static final String PASSWORD = "password";
    private static final String EMAIL = "email";
    private static final String ID_ROLE = "id_role";

    private static final String PERSON = "person";
    private static final String ID_PERSON = "id_person";
    private static final String FIRST_NAME = "first_name";
    private static final String LAST_NAME = "last_name";

    private static final String FIND_BY_ID = Sql.create().select().allFrom().var(USER).whereQs(ID_USER).build();
    private static final String INSERT = Sql.create().insert().var(USER).values(ID_USER, 5).build();
    private static final String UPDATE = Sql.create().update().var(USER).set().varQs(REGISTER_DATE).c().varQs(PASSWORD).c().varQs(EMAIL).c().varQs(ID_PERSON).c().varQs(ID_ROLE).whereQs(ID_USER).build();
    private static final String DELETE = Sql.create().delete().var(USER).whereQs(ID_USER).build();
    private static final String COUNT_USER = Sql.create().select().count().from().var(USER).build();
    private static final String FIND_BY_LOGIN = Sql.create().select().allFrom().var(USER).whereQs(EMAIL).build();
    private static final String FIND_BY_LOGIN_PASSWORD = Sql.create().select().allFrom().var(USER).whereQs(EMAIL).and().varQs(PASSWORD).build();
    private static final String LIMIT_USER = Sql.create().select().allFrom().var(USER).limit().build();

    @Override
    public User insert(User item) throws Exception {
        try {
            try (PreparedStatement statement = getConnection().prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS)) {
                statementUser(statement, item);
                statement.executeUpdate();
                try (ResultSet resultSet = statement.getGeneratedKeys()) {
                    resultSet.next();
                    item.setId(resultSet.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new Exception("can't insert " + this.getClass().getSimpleName() + "/" + item, e);
        }
        return item;
    }

    public User findById(int id) throws Exception {
        User user = null;
        try {
            try (PreparedStatement statement = getConnection().prepareStatement(FIND_BY_ID)) {
                statement.setInt(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        user = itemUser(resultSet);
                    }
                }
            }
        } catch (SQLException e) {
            throw new Exception("can't find by id " + this.getClass().getSimpleName(), e);
        }
        return user;
    }

    @Override
    public void update(User item) throws Exception {
        try {
            try (PreparedStatement statement = getConnection().prepareStatement(UPDATE)) {
                statementUser(statement, item);
                statement.setInt(6, item.getId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new Exception("can't update  " + this.getClass().getSimpleName() + "/" + item, e);
        }
    }

    @Override
    public void delete(User item) throws Exception {
        try {
            try (PreparedStatement statement = getConnection().prepareStatement(DELETE)) {
                statement.setInt(1, item.getId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new Exception("can't delete user " + this.getClass().getSimpleName() + "/" + item, e);
        }
    }

    public int getUserCount() throws Exception {
        int count = 0;
        try (Statement statement = getConnection().createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(COUNT_USER)) {
                while (resultSet.next()) {
                    count = resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new Exception("can't get count user "+ this.getClass().getSimpleName() , e);
        }
        return count;
    }

    public User getUser(String login) throws Exception {
        User user = null;
        try {
            try (PreparedStatement statement = getConnection().prepareStatement(FIND_BY_LOGIN)) {
                statement.setString(1, login);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        user = itemUser(resultSet);
                    }
                }
            }
        } catch (SQLException e) {
            throw new Exception("can't get by login " + this.getClass().getSimpleName(), e);
        }
        return user;
    }

    public User getUser(String login, String password) throws Exception {
        User user = null;
        try {
            try (PreparedStatement statement = getConnection().prepareStatement(FIND_BY_LOGIN_PASSWORD)) {
                statement.setString(1, login);
                statement.setString(2, password);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        user = itemUser(resultSet);
                    }
                }
            }
        } catch (SQLException e) {
            throw new Exception("can't get by login and pasdword " + this.getClass().getSimpleName(), e);
        }
        return user;
    }

    public List<User> getLimitUsers(int start, int count) throws Exception {
        List<User> list = new ArrayList<>();
        User user = null;
        try {
            try (PreparedStatement statement = getConnection().prepareStatement(LIMIT_USER)) {
                statement.setInt(1, ((start - 1) * count));
                statement.setInt(2, count);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        user = itemUser(resultSet);
                        list.add(user);
                    }
                }
            }
        } catch (SQLException e) {
            throw new Exception("can't get list of user " + this.getClass().getSimpleName(), e);
        }
        return list;
    }

    private PreparedStatement statementUser(PreparedStatement statement, User item) throws SQLException {
        statement.setDate(1, item.getRegisterDate());
        statement.setString(2, item.getPassword());
        statement.setString(3, item.getEmail());
        statement.setInt(4 , item.getPerson().getId());
        statement.setInt(5 , item.getUserRole().getId());
        return statement;
    }

    private User itemUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt(1));
        user.setRegisterDate(resultSet.getDate(2));
        user.setPassword(resultSet.getString(3));
        user.setEmail(resultSet.getString(4));
        return user;
    }

}
