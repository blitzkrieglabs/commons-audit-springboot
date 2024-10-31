package com.blitzkrieglabs.api.audit.controllers;



import java.time.LocalDate;
import java.util.List;
import java.util.Map;



import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;

import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blitzkrieglabs.api.audit.dto.EntityHistory;
import com.blitzkrieglabs.api.audit.services.AuditService;
import com.blitzkrieglabs.commons.audit.annotations.Auditable;
import com.blitzkrieglabs.commons.audit.domains.Stateful;
import com.blitzkrieglabs.commons.audit.dto.ApplicationResponse;
import com.fasterxml.jackson.databind.ObjectMapper;





@Validated
@RestController
@CrossOrigin(origins ="*") //TODO: TECH DEBT: DISABLE CORS!
@RequestMapping("/v1/history")
public class HttpAudits {
	
	
	@Autowired
	AuditService auditService;
	
	
	
	@GetMapping("/{entity}")
	@SuppressWarnings("unchecked")
	public ResponseEntity<EntityHistory<Stateful>> getEntityHistory(@PathVariable("entity") String entity,@RequestParam("public_id") String publicId, @RequestParam("from") LocalDate from,@RequestParam("to") LocalDate to){
		ApplicationResponse res = auditService.getEntityHistory(entity, publicId, from, to);
		return ResponseEntity.status(res.getStatus()).body((EntityHistory<Stateful>)res.getData());	
	}
	

}

