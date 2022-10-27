package com.tsliwinski.random_string_generator.controller;

import com.tsliwinski.random_string_generator.entity.Job;
import com.tsliwinski.random_string_generator.service.JobService;
import com.tsliwinski.random_string_generator.service.ResultService;
import com.tsliwinski.random_string_generator.service.utility.JobValidator;
import com.tsliwinski.random_string_generator.service.utility.ResultFiles;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RestController
@AllArgsConstructor
public class AppController {

    private final JobService jobService;
    private final JobValidator jobValidator;
    private final ResultService resultService;
    private final ResultFiles resultFiles;

    @GetMapping("/jobs")
    public List<Job> getJobs() {
        return jobService.getJobs();
    }

    @GetMapping("/jobs/running")
    public int getNumberOfRunningJobs() {
        return jobService.getNumberOfRunningJobs();
    }

    @GetMapping("/jobs/{id}")
    public ResponseEntity getJob(@PathVariable long id) {
        Optional<Job> job = jobService.getJob(id);
        return job.isPresent() ? ResponseEntity.of(job) : new ResponseEntity("Job doesn't exists", HttpStatus.NOT_FOUND);
    }

    @PostMapping("/jobs")
    public ResponseEntity createJob(@RequestParam String charset,
                                 @RequestParam int min,
                                 @RequestParam int max,
                                 @RequestParam int quantity) {

        String msg = jobValidator.isValid(charset, min, max, quantity);
        if(msg == null) {
            Job job = jobService.create(new Job(charset, min, max, quantity));
            resultService.processJob(job);
            return new ResponseEntity("Job created and is processed", HttpStatus.CREATED);
        } else {
            return new ResponseEntity(msg, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping (value = "/results", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StreamingResponseBody> getResults(final HttpServletResponse response) {

        response.setContentType("application/zip");
        response.setHeader(
                "Content-Disposition",
                "attachment;filename=results.zip");

        StreamingResponseBody stream = resultFiles.downloadResultsZip(response);
        return new ResponseEntity(stream, HttpStatus.OK);
    }

    @GetMapping (value = "/results/{jobId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StreamingResponseBody> getResult(@PathVariable long jobId, final HttpServletResponse response) {

        String resultName = "result_of_job_" + jobId;

        response.setContentType("application/zip");
        response.setHeader(
                "Content-Disposition",
                "attachment;filename=" + resultName + ".zip");

        StreamingResponseBody stream = resultFiles.downloadFileZip(response, resultName + ".txt");
        return new ResponseEntity(stream, HttpStatus.OK);
    }
}