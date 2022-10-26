package com.tsliwinski.random_string_generator.repository;

import com.tsliwinski.random_string_generator.entity.Result;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResultRepository extends JpaRepository<Result, Long> {
}
