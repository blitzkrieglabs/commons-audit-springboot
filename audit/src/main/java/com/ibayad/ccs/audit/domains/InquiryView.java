package com.ibayad.ccs.audit.domains;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "vwinquiry")
@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.LowerCaseStrategy.class)
public class InquiryView {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Size(max = 100)
	@Column(name="firstname")
	private String firstName;

	@Size(max = 100)
	@Column(name="middlename")
	private String middleName;

	@Size(max = 100)
	@Column(name="lastname")
	private String lastName;

	@Size(max = 10)
	private String suffix;

	@JsonFormat(pattern = "MM/dd/yyyy")
	@Column(name="birthdate")
	private LocalDate dateOfBirth;

	private String status;

	private String gender;

	@Column(name="referencenumber")
	private String referenceNumber;

	private String hash;

	@Column(name="dateissued")
	private String dateIssued;
	
	private String email;
}
