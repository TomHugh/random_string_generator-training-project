package com.tsliwinski.random_string_generator.service;

import com.tsliwinski.random_string_generator.entity.Job;
import com.tsliwinski.random_string_generator.repository.JobRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


import java.util.List;
import java.util.Optional;

@SpringBootTest
public class JobServiceTest {
    @Mock
    private JobRepository jobRepository;

    @InjectMocks
    private JobService jobService;


    @Test
    public void testGetJobs() {
        Job job1 = new Job("asdf", 2, 4, 10);
        Job job2 = new Job("qwerty", 3, 5, 20);
        List<Job> jobList = List.of(job1, job2);

        Mockito.when(jobRepository.findAll()).thenReturn(jobList);
        assertEquals(jobList, jobService.getJobs());
    }

    @Test
    public void testGetNumberOfRunningJobs() {
        Mockito.when(jobRepository.countByResult(null)).thenReturn(0);
        assertEquals(0, jobService.getNumberOfRunningJobs());
    }

    @Test
    public void testGetJob() {
        Optional<Job> job1 = Optional.of(new Job("asdf", 2, 4, 10));

        Mockito.when(jobRepository.findById(1L)).thenReturn(job1);
        assertEquals(job1, jobService.getJob(1L));
    }

    @Test
    public void testCreate() {
        Job job = new Job("asdf", 2, 4, 10);
        job.setId(1L);
        jobService.create(job);
        ArgumentCaptor<Job> jobArgumentCaptor = ArgumentCaptor.forClass(Job.class);
        Mockito.verify(jobRepository).save(jobArgumentCaptor.capture());
        Job createdJob = jobArgumentCaptor.getValue();

        assertEquals(1L, createdJob.getId());
        assertEquals("asdf", createdJob.getCharset());
        assertEquals(2, createdJob.getMin());
        assertEquals(4, createdJob.getMax());
        assertEquals(10, createdJob.getQuantity());
    }
}