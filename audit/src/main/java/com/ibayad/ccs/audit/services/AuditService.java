package com.ibayad.ccs.audit.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.blitzkrieglabs.commons.audit.dto.ApplicationResponse;
import com.ibayad.ccs.audit.domains.InquiryView;
import com.ibayad.ccs.audit.domains.Lead;
import com.ibayad.ccs.audit.repositories.ApplicationsRepository;
import com.ibayad.ccs.audit.repositories.AuditRepository;

import ch.qos.logback.core.joran.util.beans.BeanUtil;
import jakarta.validation.Valid;


@Validated
@Service
public class AuditService {
	
	
	@Autowired
	AuditRepository auditRepo;
	
	

	public ApplicationResponse getEntityHistory(String entityName, String publicId, LocalDate from, LocalDate to) {
		ApplicationResponse res = ApplicationResponse.builder()
				 .status(HttpStatus.BAD_REQUEST)
				 .build();
		
		return res;
	}
	
	
	public ApplicationResponse getAuditLog(LocalDate from , LocalDate to) {
		ApplicationResponse res = ApplicationResponse.builder()
				 .status(HttpStatus.BAD_REQUEST)
				 .build();
		
		return res;
	}
	
	public ApplicationResponse getActivityByInitiator(String intiator, LocalDate from , LocalDate to) {
		ApplicationResponse res = ApplicationResponse.builder()
				 .status(HttpStatus.BAD_REQUEST)
				 .build();
		
		return res;
	}
	
	


}