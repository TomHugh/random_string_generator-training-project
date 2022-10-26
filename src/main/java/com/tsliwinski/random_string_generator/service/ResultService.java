package com.tsliwinski.random_string_generator.service;

import com.tsliwinski.random_string_generator.entity.Job;
import com.tsliwinski.random_string_generator.entity.Result;
import com.tsliwinski.random_string_generator.repository.ResultRepository;
import com.tsliwinski.random_string_generator.service.utility.StringGenerator;
import com.tsliwinski.random_string_generator.service.utility.ResultFiles;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@AllArgsConstructor
public class ResultService {
    private final ResultRepository resultRepository;
    private final StringGenerator stringGenerator;
    private final ResultFiles resultFiles;

    public void create (Result result) {
        resultRepository.save(result);
    }

    @Transactional
    public void processJob(Job job) {
        Set<String> stringSet = stringGenerator.generateSet(job.getCharset(), job.getMin(), job.getMax(), job.getQuantity());
        String filename = "result_of_job_" + job.getId();
        resultFiles.write(stringSet, filename);
        Result result = Result.createNew();
        job.setResult(result);
        result.setFilename(filename);
        create(result);
    }
}
