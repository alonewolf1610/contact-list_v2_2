package com.example.contactlist;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("contacts")
public class Contact {
    @Id
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
}