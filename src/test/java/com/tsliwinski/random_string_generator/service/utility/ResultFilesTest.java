package com.tsliwinski.random_string_generator.service.utility;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;

@SpringBootTest
public class ResultFilesTest {

    @InjectMocks
    private ResultFiles resultFiles;

    @Test
    public void testWrite() {
        Set<String> stringSet = Set.of("asdf", "fdsa");
        resultFiles.write(stringSet, "testResult");

        File file = new File("results/testResult.txt");
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            while (line != null) {
                assertTrue(stringSet.contains(line));
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        file.delete();
    }
}
