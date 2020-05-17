package my.library.service;

import my.library.dao.DaoFactory;
import my.library.dao.MySqlPerson;
import my.library.dao.MySqlUser;
import my.library.dao.MySqlUserRole;
import my.library.entity.Person;
import my.library.entity.User;
import my.library.entity.UserRole;
import my.library.util.SqlDate;

import java.util.List;

public class UserService {

    private final String USER_ROLE = "user";


    public void registerUser(User user) throws Exception {
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                MySqlPerson mySqlPerson = daoFactory.getPersonDao();
                MySqlUser mySqlUser = daoFactory.getUserDao();
                MySqlUserRole mySqlUserRole = daoFactory.getUserRoleDao();
                UserRole userRole = mySqlUserRole.findRoleByName(USER_ROLE);

                daoFactory.startTransaction();
                mySqlPerson.insert(user.getPerson());
                user.setUserRole(userRole);
                user.setRegisterDate(SqlDate.currentDateAndTime());
                mySqlUser.insert(user);
                daoFactory.commitTransaction();

            } catch (Exception e) {
                try {
                    daoFactory.rollbackTransaction();
                } catch (Exception e1) {
                    throw new Exception("can't rollback transaction", e);
                }
                throw new Exception("can't register user", e);
            }
        }
    }

    public User findByLogin(String login) throws Exception {
        try (DaoFactory daoFactory = new DaoFactory()) {
            User user;
            try {
                MySqlUser mySqlUser = daoFactory.getUserDao();
                user = mySqlUser.getUser(login);
                fillUser(user);
                return user;
            } catch (Exception e) {
                throw new Exception("can't find user by login", e);
            }
        }
    }


    public User findUserById(int id) throws Exception {
        try (DaoFactory daoFactory = new DaoFactory()) {
            User user;
            try {
                MySqlUser mySqlUser = daoFactory.getUserDao();
                user = mySqlUser.findById(id);
                fillUser(user);
                return user;
            } catch (Exception e) {
                throw new Exception("can't find user by id", e);
            }
        }
    }


    public User findByLoginPassword(String login, String password) throws Exception {
        try (DaoFactory daoFactory = new DaoFactory()) {
            User user;
            try {
                MySqlUser mySqlUser = daoFactory.getUserDao(); //MySqlUser
                user = mySqlUser.getUser(login, password);
                fillUser(user);
                return user;
            } catch (Exception e) {
                throw new Exception("can't find by login and password user", e);
            }
        }
    }

    public boolean isLoginAvailable(String login) throws Exception {
        return findByLogin(login) == null;
    }


    public int userCount() throws Exception {
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                MySqlUser mySqlUser = daoFactory.getUserDao();
                return mySqlUser.getUserCount();
            } catch (Exception e) {
                throw new Exception("can't get count user", e);
            }
        }
    }


    public List<User> getListUsers(int start, int end) throws Exception {
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                MySqlUser mySqlUser = daoFactory.getUserDao();
                List<User> list = mySqlUser.getLimitUsers(start, end);
                for (User user : list) {
                    fillUser(user);
                }
                return list;
            } catch (Exception e) {
                throw new Exception("can't get list of user ", e);
            }
        }
    }


    private void fillUser(User user) throws Exception {
        try {
            if (user != null) {
                try (DaoFactory daoFactory = new DaoFactory()) {

                    MySqlPerson mySqlPerson = daoFactory.getPersonDao();
                    MySqlUserRole mySqlUserRole = daoFactory.getUserRoleDao();

                    Person person = mySqlPerson.findByUser(user);
                    user.setPerson(person);
                    user.setUserRole(mySqlUserRole.findByUser(user));
                }
            }
        } catch (Exception e) {
            throw new Exception("Can't fill user ", e);
        }
    }

    public void deleteUser(User user) throws Exception {
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                MySqlPerson mySqlPerson = daoFactory.getPersonDao();
                MySqlUser mySqlUser = daoFactory.getUserDao();
                Person person = mySqlPerson.findByUser(user);

                daoFactory.startTransaction();
                mySqlUser.delete(user);
                mySqlPerson.delete(person);
                daoFactory.commitTransaction();

            } catch (Exception e) {
                try {
                    daoFactory.rollbackTransaction();
                } catch (Exception e1) {
                    throw new Exception("can't rollback transaction", e);
                }
                throw new Exception("can't delete user", e);
            }
        }
    }

}
