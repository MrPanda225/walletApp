package com.walletApp.backend.repository;

import com.walletApp.backend.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<Status, Integer> {
}
