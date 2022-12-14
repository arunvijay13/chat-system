package com.sms.project.chatsystem.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "USER")
public class User {

    @NotEmpty
    @Id
    @Column(name = "USERNAME")
    private String username;

    @NotEmpty
    @Email
    @Column(name = "EMAIL")
    private String email;

    @NotEmpty
    @Pattern(regexp = "[0-9]{10}", message = "Invalid phone number")
    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

}
