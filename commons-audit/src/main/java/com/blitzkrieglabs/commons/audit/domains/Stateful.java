package com.blitzkrieglabs.commons.audit.domains;

import java.time.LocalDateTime;
import java.util.UUID;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/***
 * NOTE: upon inheritance, kindly use @Table annotation to explicitly define table name, to be captured by audit
 ***/


@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class Stateful {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="public_id")
	private String publicId;
	
	@Column(name="reference_id")
	private String referenceId; //to be used to track the most recent id used to create/update/delete the entity
	
	@Column(name="previous_id")
	private String previousId; //previous reference id prior to update.. populated via trigger. 
	
	@Column(name="timestamp")
	private LocalDateTime timestamp; //last inserted/last updated
	
	
	@Column(name="state")
	private String state;

	@PrePersist
	@PreUpdate
	void preInsert() {
	   if (this.timestamp == null)
	       this.timestamp = LocalDateTime.now();
	}
}
