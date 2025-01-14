package ua.springapp.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ua.springapp.dao.row_mapper.PersonMapper;
import ua.springapp.models.Book;
import ua.springapp.models.Person;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;


@Component
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> getPeople(){
        return jdbcTemplate.query("SELECT * FROM person",
                new PersonMapper());
    }

    public Person getPerson(int id){
        return jdbcTemplate.queryForObject("SELECT * FROM person WHERE person_id = ?",
                new PersonMapper(), id);
    }

    public void updatePerson(int id, Person person){
        jdbcTemplate.update("UPDATE Person SET full_name = ?, year_of_born = ? WHERE person_id = ?",
                person.getFullName(), person.getYearOfBorn(), id);
    }

    public void addPerson(Person person){
        jdbcTemplate.update("INSERT INTO Person(full_name, year_of_born) VALUES(?,?) ",
                person.getFullName(), person.getYearOfBorn());
    }

    public void deletePerson(int person_id){
        jdbcTemplate.update("DELETE FROM Person WHERE person_id = ?",
                person_id);
    }

//    public List<Book> booksThatAssignPerson(int person_id){
//        return jdbcTemplate.query("SELECT b.name, b.book_id FROM Book b Join Person p on b.person_id = p.person_id " +
//                "where p.person_id = ?", new RowMapper<Book>() {
//            @Override
//            public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
//                Book book = new Book();
//                book.setId(rs.getInt("book_id"));
//                book.setName(rs.getString("name"));
//                return book;
//            }
//        }, person_id);
//    }

    public List<Book> booksThatAssignPerson(int person_id){
        return jdbcTemplate.query("SELECT b.name FROM Book b Join Person p on b.person_id = p.person_id " +
                "where p.person_id = ?", new RowMapper<Book>() {
            @Override
            public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
                Book book = new Book();
                book.setName(rs.getString("name"));
                return book;
            }
        }, person_id);
    }

    public Optional<Person> checkUniqueOfFullName(String fullName, int person_id){
        return jdbcTemplate.query("SELECT * FROM Person WHERE full_name = ? AND person_id != ?",
                new PersonMapper(), fullName, person_id).stream().findFirst();
    }

}
