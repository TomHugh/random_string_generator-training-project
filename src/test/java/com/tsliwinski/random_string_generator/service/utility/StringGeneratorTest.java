package com.tsliwinski.random_string_generator.service.utility;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class StringGeneratorTest {

    @InjectMocks
    private StringGenerator stringGenerator;

    @Test
    public void testGenerate() {
        String randomStr = stringGenerator.generate("asdf", 2,3);
        assertTrue(randomStr.length() >= 2 && randomStr.length() <=3);
        assertTrue(randomStr.matches("[asdf][asdf]") || randomStr.matches("[asdf][asdf][asdf]"));
    }

    @Test
    public void testGenerateSet() {
        Set<String> stringSet = stringGenerator.generateSet("asdf", 2,3, 10);
        assertTrue(stringSet.size() == 10);
        for(String string : stringSet)
            assertTrue(string.matches("[asdf][asdf]") || string.matches("[asdf][asdf][asdf]"));

    }
}
