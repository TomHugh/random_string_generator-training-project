package com.tsliwinski.random_string_generator.service.utility;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Path;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

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
                }
            });
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public StreamingResponseBody downloadResultsZip(HttpServletResponse response) {
        StreamingResponseBody stream = out -> {
            final File directory = new File("results");
            final ZipOutputStream zipOut = new ZipOutputStream(response.getOutputStream());

            if (directory.exists() && directory.isDirectory()) {
                try {
                    for (final File file : directory.listFiles()) {
                        final InputStream inputStream = new FileInputStream(file);
                        final ZipEntry zipEntry = new ZipEntry(file.getName());
                        zipOut.putNextEntry(zipEntry);
                        byte[] bytes = new byte[1024];
                        int length;
                        while ((length = inputStream.read(bytes)) >= 0) {
                            zipOut.write(bytes, 0, length);
                        }
                        inputStream.close();
                    }
                    zipOut.close();
                } catch (final IOException e) {
                    throw new IOException();
                }
            }
        };
        return stream;
    }

    public StreamingResponseBody downloadFileZip(HttpServletResponse response, String filename) {
        StreamingResponseBody stream = out -> {

            final File file = new File("results/" + filename);
            final ZipOutputStream zipOut = new ZipOutputStream(response.getOutputStream());

            if (file.exists()) {
                try {
                    final InputStream inputStream = new FileInputStream(file);
                    final ZipEntry zipEntry = new ZipEntry(file.getName());
                    zipOut.putNextEntry(zipEntry);
                    byte[] bytes = new byte[1024];
                    int length;
                    while ((length = inputStream.read(bytes)) >= 0) {
                        zipOut.write(bytes, 0, length);
                    }
                    inputStream.close();
                    zipOut.close();
                } catch (final IOException e) {
                    throw new IOException();
                }
            }
        };
        return stream;
    }
}
