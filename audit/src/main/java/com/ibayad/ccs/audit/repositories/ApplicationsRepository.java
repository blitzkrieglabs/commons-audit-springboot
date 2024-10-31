package com.ibayad.ccs.audit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ibayad.ccs.audit.domains.Lead;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationsRepository extends JpaRepository<Lead, Long> {

    Optional<Lead> findById(Long id);

    List<Lead> findAll();

    Optional<Lead> findByHash(String hash);

}
