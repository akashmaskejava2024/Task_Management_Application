package com.nt.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDTO {

	private int id;
	private String name;
	private LocalDate dob;
	private String email;
	private long phone;
	private String username;
	private String password;

}
