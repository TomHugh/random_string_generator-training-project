package com.tsliwinski.random_string_generator.service.utility;

import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Set;

@Component
public class ResultFiles {
    public void write(Set<String> inputSet, String filename) {
        Path filePath = Path.of("results/" + filename + ".txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath.toFile()))) {
            inputSet.forEach(str -> {
                        try {
                            writer.write(str);
                            writer.newLine();
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }});
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
