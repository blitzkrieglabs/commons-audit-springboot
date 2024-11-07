package com.blitzkrieglabs.commons.audit.domains;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@Entity
@Table(name = "audit")
public class Audit {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

    @Column(name = "reference_id")
    private String referenceId;

    @Column(name = "session_id")
    private String sessionId;

    @Column(name = "initiator")
    private String initiator;

    @Column(name = "resource")
    private String resource;

    @Column(name = "status")
    private String status;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    @Column(name="entity_name")
    private String entity;
    
    @Column(name="entity_id")
    private String entityId;
    

    
	@PrePersist
	void preInsert() {
	   if (this.timestamp == null){
			this.timestamp = LocalDateTime.now();
	   }	    	
	
	}
}
