package com.tsliwinski.random_string_generator.controller;

import com.tsliwinski.random_string_generator.entity.Job;
import com.tsliwinski.random_string_generator.service.JobService;
import com.tsliwinski.random_string_generator.service.ResultService;
import com.tsliwinski.random_string_generator.service.utility.JobValidator;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AppController.class)
public class AppControllerTest {
    @MockBean
    private JobService jobService;

    @MockBean
    private ResultService resultService;

    @MockBean
    private JobValidator jobValidator;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetJobs() throws Exception {
        Job job1 = new Job("asdf", 2, 4, 10);
        Job job2 = new Job("qwerty", 3, 5, 20);
        List<Job> jobList = List.of(job1, job2);

        Mockito.when(jobService.getJobs()).thenReturn(jobList);

        mockMvc.perform(get("/jobs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(jobList.size()))
                .andDo(print());
    }

    @Test
    public void testGetNumberOfRunningJobs() throws Exception {
        Mockito.when(jobService.getNumberOfRunningJobs()).thenReturn(0);

        mockMvc.perform(get("/jobs/running"))
                .andExpect(status().isOk())
                .andExpect(content().string("0"));
    }

    @Test
    public void testGetJob() throws Exception {
        Job job1 = new Job("asdf", 2, 4, 10);
        long id = 1;
        job1.setId(id);

        Mockito.when(jobService.getJob(id)).thenReturn(Optional.of(job1));
        Mockito.when(jobService.getJob(2)).thenReturn(Optional.ofNullable(null));


        mockMvc.perform(get("/jobs/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.charset").value(job1.getCharset()))
                .andExpect(jsonPath("$.min").value(job1.getMin()))
                .andExpect(jsonPath("$.max").value(job1.getMax()))
                .andExpect(jsonPath("$.quantity").value(job1.getQuantity()))
                .andDo(print());

        mockMvc.perform(get("/jobs/2"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Job doesn't exists"));

    }

    @Test
    public void testCreateJob() throws Exception {

        mockMvc.perform(post("/jobs?charset=asdf&min=2&max=4&quantity=10"))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    public void testGetResults() throws Exception {

        mockMvc.perform(get("/results"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/zip"));
    }

    @Test
    public void testGetResult() throws Exception {
        File file = new File("results/result_of_job_123456789.txt");
        file.createNewFile();

        mockMvc.perform(get("/results/123456789"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/zip"));

        file.delete();
    }

}