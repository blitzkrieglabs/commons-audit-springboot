package com.blitzkrieglabs.commons.audit.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blitzkrieglabs.commons.audit.domains.Stateful;

@Repository
public interface HistoricalRepository<T extends Stateful, ID> extends JpaRepository<T, ID>  {
}