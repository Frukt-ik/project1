package ua.springapp.dao.row_mapper;

import org.springframework.jdbc.core.RowMapper;
import ua.springapp.models.Person;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonMapper implements RowMapper<Person> {

    @Override
    public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
        Person person = new Person();
        person.setId(rs.getInt("person_id"));
        person.setFullName(rs.getString("full_name"));
        person.setYearOfBorn(rs.getInt("year_of_born"));
        return person;
    }
}
