package ua.springapp.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ua.springapp.dao.row_mapper.BookMapper;
import ua.springapp.dao.row_mapper.PersonBookMapper;
import ua.springapp.models.Book;
import ua.springapp.models.Person;

import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> getBooks() {
        return jdbcTemplate.query("select * from book", new BookMapper());
    }

    public Book getBook(int id) {
        return jdbcTemplate.queryForObject("select * from book where book_id = ?", new BookMapper(), id);
    }

    public void updateBook(int id, Book book) {
        jdbcTemplate.update("UPDATE Book SET  name = ?, author = ?, year_of_publication = ?  WHERE book_id = ? ",
                book.getName(), book.getAuthor(), book.getYearOfPublication(), id );
    }

    public void addBook(Book book) {
        jdbcTemplate.update("INSERT INTO Book(name, author, year_of_publication) VALUES(?,?,?) ",
                book.getName(), book.getAuthor(), book.getYearOfPublication());
    }

    public void deleteBook(int id) {
        jdbcTemplate.update("DELETE FROM Book WHERE book_id = ?", id);
    }

    public void assignBookToUser(int user_id, int book_id) {
        jdbcTemplate.update("UPDATE Book SET person_id = ? WHERE book_id = ?", user_id, book_id);
    }

    public Optional<Person> personThatAssignBook(int book_id){
        return jdbcTemplate.query("SELECT p.full_name FROM Person p Join Book b on p.person_id = b.person_id where b.book_id = ?",
                new PersonBookMapper(), book_id).stream().findFirst();
    }

    public void releaseBook(int book_id) {
        jdbcTemplate.update("UPDATE Book SET person_id = null WHERE book_id = ?", book_id);
    }
}
