package com.tsliwinski.random_string_generator.service;

import com.tsliwinski.random_string_generator.entity.Job;
import com.tsliwinski.random_string_generator.repository.JobRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class JobService {
    private final JobRepository jobRepository;

    public List<Job> getJobs() {
        return jobRepository.findAll();
    }

    public int getNumberOfRunningJobs() {
        return jobRepository.countByResult(null);
    }

    public Optional<Job> getJob(long id) {
        return jobRepository.findById(id);
    }

    public Job create(String charset, int min, int max, int quantity) {
        return jobRepository.save(new Job(charset, min, max, quantity));
    }

}
