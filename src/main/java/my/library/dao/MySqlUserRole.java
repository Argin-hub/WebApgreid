package my.library.dao;

import my.library.entity.User;
import my.library.entity.UserRole;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySqlUserRole  extends BaseDao<UserRole> {
    private static final String ROLE = "role";
    private static final String NAME = "name";
    private static final String ID_ROLE = "id_role";

    private static final String USER = "user";
    private static final String ID_USER = "id_user";
    private static final String FIND_BY_USER = Sql.create().select().varS(ROLE, ID_ROLE).c().varS(ROLE, NAME).from().var(ROLE).join(USER).varS(USER, ID_ROLE).eq().varS(ROLE, ID_ROLE).whereQs(USER, ID_USER).build();
    private static final String FIND_BY_NAME_ROLE = Sql.create().select().allFrom().var(ROLE).whereQs(NAME).build();

    public UserRole findByUser(User user) throws Exception {
        UserRole userRole = null;
        try {
            try (PreparedStatement statement = getConnection().prepareStatement(FIND_BY_USER)) {
                statement.setInt(1, user.getId());
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        userRole = itemRole(resultSet);
                    }
                }
            }
        } catch (SQLException e) {
            throw new Exception("can't find by user " + this.getClass().getSimpleName(), e);
        }
        return userRole;
    }

    public UserRole findRoleByName(String nameRole) throws Exception{
        UserRole userRole = null;
        try {
            try (PreparedStatement statement = getConnection().prepareStatement(FIND_BY_NAME_ROLE)) {
                statement.setString(1, nameRole);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        userRole = itemRole(resultSet);
                    }
                }
            }
        } catch (SQLException e) {
            throw new Exception("can't find by role " + this.getClass().getSimpleName(), e);
        }
        return userRole;
    }

    private UserRole itemRole(ResultSet resultSet) throws SQLException {
        UserRole userRole = new UserRole();
        userRole.setId(resultSet.getInt(1));
        userRole.setName(resultSet.getString(2));
        return userRole;
    }

    @Override
    public UserRole insert(UserRole item) throws Exception {
        return null;
    }

    @Override
    public UserRole findById(int id) throws Exception {
        return null;
    }

    @Override
    public void update(UserRole item) throws Exception {

    }

    @Override
    public void delete(UserRole item) throws Exception {

    }
}
