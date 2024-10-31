package com.ibayad.ccs.audit.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blitzkrieglabs.commons.audit.domains.Audit;
import com.ibayad.ccs.audit.domains.InquiryView;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuditRepository extends JpaRepository<Audit, Long> {

  

}
