package com.nt.dto;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.bytebuddy.asm.Advice.Local;


@Data
@NoArgsConstructor
@AllArgsConstructor

public class TaskResponseDTO {

	private int id;
	private String name;
	private String desc;
	private LocalDate dueDate;
	private boolean isDone;
	private int tasklistId;
	
}
