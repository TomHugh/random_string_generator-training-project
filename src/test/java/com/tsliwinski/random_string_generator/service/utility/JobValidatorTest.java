package com.tsliwinski.random_string_generator.service.utility;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class JobValidatorTest {

    @InjectMocks
    private JobValidator jobValidator;

    @Test
    public void testUniqueChars() {
        assertEquals("Charset must have at least 1 character", jobValidator.uniqueChars(""));
        assertNull(jobValidator.uniqueChars("1"));
        assertEquals("Charset contains duplicate characters", jobValidator.uniqueChars("qwertyy"));
        assertNull(jobValidator.uniqueChars("qwerty"));
    }

    @Test
    public void testMinMax() {
        assertEquals("Min can't be zero or less", jobValidator.minMax(-1,5));
        assertEquals("Max can't be less than min", jobValidator.minMax(7,5));
        assertNull(jobValidator.minMax(2,5));
    }

    @Test
    public void testQuantity() {
        assertEquals("There is only 36 possible strings to generate", jobValidator.quantity("asd", 2, 3, 100));
        assertNull(jobValidator.quantity("asd", 2, 3, 20));
    }

    @Test
    public void testIsValid() {
        assertNull(jobValidator.quantity("asd", 2, 3, 20));
    }
}
