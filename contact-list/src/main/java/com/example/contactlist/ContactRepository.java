package com.example.contactlist;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class ContactRepository {
    private final JdbcTemplate jdbcTemplate;

    public ContactRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Contact> findAll() {
        String sql = "SELECT * FROM contacts";
        return jdbcTemplate.query(sql, new ContactRowMapper());
    }

    public void save(Contact contact) {
        String sql = "INSERT INTO contacts (firstName, lastName, email, phone) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, contact.getFirstName(), contact.getLastName(), contact.getEmail(), contact.getPhone());
    }

    public Optional<Contact> findById(Long id) {
        String sql = "SELECT * FROM contacts WHERE id = ?";
        List<Contact> contacts = jdbcTemplate.query(sql, new ContactRowMapper(), id);
        return contacts.isEmpty() ? Optional.empty() : Optional.of(contacts.get(0));
    }


    public void update(Contact contact) {
        String sql = "UPDATE contacts SET firstName = ?, lastName = ?, email = ?, phone = ? WHERE id = ?";
        jdbcTemplate.update(sql, contact.getFirstName(), contact.getLastName(), contact.getEmail(), contact.getPhone(), contact.getId());
    }

    public void delete(Long id) {
        String sql = "DELETE FROM contacts WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    private static class ContactRowMapper implements RowMapper<Contact> {
        @Override
        public Contact mapRow(ResultSet rs, int rowNum) throws SQLException {
            Contact contact = new Contact();
            contact.setId(rs.getLong("id"));
            contact.setFirstName(rs.getString("firstName"));
            contact.setLastName(rs.getString("lastName"));
            contact.setEmail(rs.getString("email"));
            contact.setPhone(rs.getString("phone"));
            return contact;
        }
    }
}