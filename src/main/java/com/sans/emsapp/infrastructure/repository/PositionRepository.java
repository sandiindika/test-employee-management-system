package com.sans.emsapp.infrastructure.repository;

import com.sans.emsapp.domain.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PositionRepository extends JpaRepository<Position, String> {
}
