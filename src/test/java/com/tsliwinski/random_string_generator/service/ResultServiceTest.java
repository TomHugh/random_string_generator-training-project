package com.tsliwinski.random_string_generator.service;

import com.tsliwinski.random_string_generator.entity.Job;
import com.tsliwinski.random_string_generator.entity.Result;
import com.tsliwinski.random_string_generator.repository.ResultRepository;
import com.tsliwinski.random_string_generator.service.utility.ResultFiles;
import com.tsliwinski.random_string_generator.service.utility.StringGenerator;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ResultServiceTest {
    @Mock
    private ResultRepository resultRepository;

    @Mock
    private StringGenerator stringGenerator;

    @Mock
    private ResultFiles resultFiles;

    @InjectMocks
    private ResultService resultService;


    @Test
    public void testCreate() {
        Result result = new Result(1L, "result_of_job_123456789.txt");
        resultService.create(result);
        ArgumentCaptor<Result> resultArgumentCaptor = ArgumentCaptor.forClass(Result.class);
        Mockito.verify(resultRepository).save(resultArgumentCaptor.capture());
        Result createdResult = resultArgumentCaptor.getValue();

        assertEquals(1, createdResult.getId());
        assertEquals("result_of_job_123456789.txt", createdResult.getFilename());
    }

    @Test
    public void testProcessJob() {
        Job job = new Job("asdf", 2, 4, 10);
        job.setId(123456789L);
        Set<String> stringSet = Set.of("randomString");
        Result result = new Result();
        Mockito.when(stringGenerator.generateSet(Mockito.anyString(),
                Mockito.anyInt(),
                Mockito.anyInt(),
                Mockito.anyInt())).thenReturn(stringSet);

        try (MockedStatic<Result> mockResult = Mockito.mockStatic(Result.class)) {
            mockResult.when(Result::createNew).thenReturn(result);

            resultService.processJob(job);
            Mockito.verify(resultFiles).write(stringSet, "result_of_job_123456789");
            assertEquals(result, job.getResult());
            assertEquals("result_of_job_123456789", result.getFilename());
        }
    }
}