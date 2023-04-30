package com.promed.admission.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.DateTimeFormat;

import com.promed.admission.valid.constraint.ValidBirthDate;
import com.promed.admission.valid.constraint.ValidCategory;
import com.promed.admission.valid.constraint.ValidGender;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Document("admission")
public class AdmissionModel implements Serializable {

	private static final long serialVersionUID = 483832665576489347L;

	@Id
	private String id;
	@NotEmpty(message = "Name must not be empty")
	@Field(name = "name")
	private String name;
	@Field(name = "birthDate")

	

	@NotNull(message = "BirthDate must not be empty")
	@ValidBirthDate
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate birthDate;
	
	@Field(name = "gender")
	@NotEmpty(message = "Sex must not be empty")
	@ValidGender
	private String gender;
	
	@Field(name = "category")
	@NotEmpty(message = "Category must not be empty")
	@ValidCategory
	private String category;

	@Field(name = "source")
	private String source;

	@CreatedDate
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	//@Column(name = "createdDate", updatable = false)
	private LocalDateTime   createdDate;
	@LastModifiedDate
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private LocalDateTime lastModifiedDate;

}
