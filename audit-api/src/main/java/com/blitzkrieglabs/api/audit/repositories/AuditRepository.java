package com.blitzkrieglabs.api.audit.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blitzkrieglabs.api.audit.domains.InquiryView;
import com.blitzkrieglabs.commons.audit.domains.Audit;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuditRepository extends JpaRepository<Audit, Long> {

  

}
