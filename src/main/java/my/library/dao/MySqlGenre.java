package my.library.dao;

import my.library.entity.Book;
import my.library.entity.Genre;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySqlGenre extends BaseDao<Genre> {
    public static final String GENRE = "genre";
    public static final String NAME = "name";
    public static final String ID_GENRE = "id_genre";

    private static final String BOOK = "book";
    public static final String ID_BOOK = "id_book";

    private static final String FIND_BY_ID = Sql.create().select().allFrom().var(GENRE).whereQs(ID_GENRE).build();
    private static final String SELECT_ALL = Sql.create().select().allFrom().var(GENRE).build();
    private static final String FIND_BY_BOOK = Sql.create().select().varS(GENRE, ID_GENRE).c().varS(GENRE, NAME).from().var(GENRE).join(BOOK).varS(BOOK, ID_GENRE).eq().varS(GENRE, ID_GENRE).whereQs(BOOK, ID_BOOK).build();

    @Override
    public Genre insert(Genre item) throws Exception {
        return null;
    }

    @Override
    public Genre findById(int id) throws Exception {
        Genre genre = null;
        try {
            try (PreparedStatement statement = getConnection().prepareStatement(FIND_BY_ID)) {
                statement.setInt(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        genre = itemGenre(resultSet);
                    }
                }
            }
        } catch (SQLException e) {
            throw new Exception("can't find by id " + this.getClass().getSimpleName(), e);
        }
        return genre;
    }

    @Override
    public void update(Genre item) throws Exception {

    }

    @Override
    public void delete(Genre item) throws Exception {

    }

    public List<Genre> getAll() throws Exception {
        List<Genre> list = new ArrayList<>();
        Genre genre = null;
        try {
            try (PreparedStatement statement = getConnection().prepareStatement(SELECT_ALL)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        genre = itemGenre(resultSet);
                        list.add(genre);
                    }
                }
            }
        } catch (SQLException e) {
            throw new Exception("can't get all list " + this.getClass().getSimpleName(), e);
        }
        return list;
    }

    public Genre findByBook(Book book) throws Exception {
        Genre genre = null;
        try {
            try (PreparedStatement statement = getConnection().prepareStatement(FIND_BY_BOOK)) {
                statement.setInt(1, book.getId());
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        genre = itemGenre(resultSet);
                    }
                }
            }
        } catch (SQLException e) {
            throw new Exception("can't find by book " + this.getClass().getSimpleName(), e);
        }
        return genre;
    }

    private Genre itemGenre(ResultSet resultSet) throws SQLException {
        Genre genre = new Genre();
        genre.setId(resultSet.getInt(1));
        genre.setName(resultSet.getString(2));
        return genre;
    }
}
