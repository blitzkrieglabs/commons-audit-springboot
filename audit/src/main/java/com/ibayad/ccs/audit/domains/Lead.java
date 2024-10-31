package com.ibayad.ccs.audit.domains;


import java.sql.Date;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(SnakeCaseStrategy.class)
@Table(name = "leads", uniqueConstraints = { @UniqueConstraint(columnNames = "id") })
public class Lead {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Size(max = 100)
	private String firstname;

	@Size(max = 100)
	private String middlename;

	@Size(max = 100)
	private String lastname;

	@Size(max = 10)
	private String suffix;

	@Column(columnDefinition = "varchar(12)")
	private String gender;
	
	@JsonFormat(pattern = "MM/dd/yyyy")
	private LocalDate dateofbirth;
	
	@Size(max = 40)
	private String bloodtype;
	
	@Column(columnDefinition = "varchar(40)")
	private String civilstatus;

	@Size(max = 100)
	private String occupation;

	@Size(max = 100)
	private String industryType;

	@Size(max = 100)
	private String specificOccupation;

	@Size(max = 100)
	private String educationalAttainment;

	@Size(max = 25)
	private String latitude;

	@Size(max = 25)
	private String longitude;

	@Size(max = 100)
	private String barangay;

	@Size(max = 40)
	private String houseNumber;

	@Size(max = 100)
	private String street;

	@Size(max = 40)
	private String purok;

	@Size(max = 40)
	private String zone;

	private String hash;

}
