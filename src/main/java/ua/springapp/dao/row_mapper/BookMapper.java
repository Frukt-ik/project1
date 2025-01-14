package ua.springapp.dao.row_mapper;

import org.springframework.jdbc.core.RowMapper;
import ua.springapp.models.Book;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookMapper implements RowMapper<Book> {

    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
        Book book = new Book();
        book.setId(rs.getInt("book_id"));
        book.setPersonId(rs.getObject("person_id") != null ? rs.getInt("person_id") : null);
        book.setName(rs.getString("name"));
        book.setAuthor(rs.getString("author"));
        book.setYearOfPublication(rs.getInt("year_of_publication"));
        return book;
    }
}
