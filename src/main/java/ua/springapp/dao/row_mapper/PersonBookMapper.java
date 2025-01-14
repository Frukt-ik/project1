package ua.springapp.dao.row_mapper;

import org.springframework.jdbc.core.RowMapper;
import ua.springapp.models.Person;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonBookMapper implements RowMapper<Person> {
    @Override
    public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
        Person person = new Person();
        person.setFullName(rs.getString("full_name"));
        return person;
    }
}
