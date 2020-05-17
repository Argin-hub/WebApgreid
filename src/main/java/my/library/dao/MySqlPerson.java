package my.library.dao;

import my.library.entity.Person;
import my.library.entity.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySqlPerson extends BaseDao<Person> {

    private static final String PERSON = "person";
    private static final String ID_PERSON = "id_person";
    private static final String FIRST_NAME = "first_name";
    private static final String LAST_NAME = "last_name";
    private static final String MIDDLE_NAME = "middle_name";
    private static final String PHONE = "phone";
    private static final String BIRTHDAY = "birthday";

    private static final String USER = "user";
    private static final String ID_USER = "id_user";

    private static final String FIND_BY_ID = Sql.create().select().allFrom().var(PERSON).whereQs(ID_PERSON).build();
    private static final String INSERT = Sql.create().insert().var(PERSON).values(ID_PERSON, 5).build();
    private static final String DELETE = Sql.create().delete().var(PERSON).whereQs(ID_PERSON).build();

    private static final String FIND_BY_USER = Sql.create().select().varS(PERSON, ID_PERSON).c().varS(PERSON, FIRST_NAME).c().varS(PERSON, LAST_NAME).c().varS(PERSON, MIDDLE_NAME).c().varS(PERSON, PHONE).c().varS(PERSON, BIRTHDAY).from().var(PERSON).join(USER).varS(USER, ID_PERSON).eq().varS(PERSON, ID_PERSON).whereQs(USER, ID_USER).build();

    @Override
    public Person insert(Person item) throws Exception {
        try {
            try (PreparedStatement statement = getConnection().prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS)) {
                statementPerson(statement, item).executeUpdate();
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

    @Override
    public Person findById(int id) throws Exception {
        Person person = null;
        try {
            try (PreparedStatement statement = getConnection().prepareStatement(FIND_BY_ID)) {
                statement.setInt(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        person = itemPerson(person, resultSet);
                    }
                }
            }
        } catch (SQLException e) {
            throw new Exception("can't find by id " + this.getClass().getSimpleName(), e);
        }
        return person;
    }

    @Override
    public void update(Person item) {
        //TODO: work here
    }

    @Override
    public void delete(Person item) throws Exception {
        try {
            try (PreparedStatement statement = getConnection().prepareStatement(DELETE)) {
                statement.setInt(1, item.getId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new Exception("can't delete  " + this.getClass().getSimpleName() + "/" + item, e);
        }
    }

    public Person findByUser(User user) throws Exception {
        Person person = null;
        try {
            try (PreparedStatement statement = getConnection().prepareStatement(FIND_BY_USER)) {
                statement.setInt(1, user.getId());
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        person = itemPerson(person, resultSet);

                    }
                }
            }
        } catch (SQLException e) {
            throw new Exception("can't find by user " + this.getClass().getSimpleName(), e);
        }
        return person;
    }

    private PreparedStatement statementPerson(PreparedStatement statement, Person item) throws SQLException {
        statement.setString(1, item.getFirstName());
        statement.setString(2, item.getLastName());
        statement.setString(3, item.getMiddleName());
        statement.setString(4, item.getPhone());
        statement.setDate(5, item.getBirthday());
        return statement;
    }

    private Person itemPerson(Person person, ResultSet resultSet) throws SQLException {
        person = new Person();
        person.setId(resultSet.getInt(1));
        person.setFirstName(resultSet.getString(2));
        person.setLastName(resultSet.getString(3));
        person.setMiddleName(resultSet.getString(4));
        person.setPhone(resultSet.getString(5));
        person.setBirthday(resultSet.getDate(6));
        return person;
    }
}
