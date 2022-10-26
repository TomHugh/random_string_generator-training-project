package com.tsliwinski.random_string_generator.repository;

import com.tsliwinski.random_string_generator.entity.Job;
import com.tsliwinski.random_string_generator.entity.Result;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Long> {
    int countByResult(Result result);
}
