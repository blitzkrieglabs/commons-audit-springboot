package com.blitzkrieglabs.commons.audit.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blitzkrieglabs.commons.audit.domains.Audit;


@Repository
public interface AuditableRepository extends JpaRepository<Audit, Long> {
}