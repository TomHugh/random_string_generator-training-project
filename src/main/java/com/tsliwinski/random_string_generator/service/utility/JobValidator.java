package com.tsliwinski.random_string_generator.service.utility;

import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class JobValidator {
    public String uniqueChars(String charset) {
        if(charset.length() == 0) return "Charset must have at least 1 character";
        if(charset.length() == 1) return null;

        char[] chars = charset.toCharArray();
        Arrays.sort(chars);

        for(int i=0; i<chars.length-1; i++) {
            if(chars[i] != chars[i+1])
                continue;
            else
                return "Charset contains duplicate characters";
        }
        return null;
    }

    public String minMax(int min, int max) {
        if(min <= 0) return "Min can't be zero or less";
        else return min <= max ? null : "Max can't be less than min";
    }

    public String quantity(String charset, int min, int max, int quantity) {
        int possibleQuantity = 0;
        for(int i=min; i<=max; i++) {
            possibleQuantity += Math.pow(charset.length(), i);
        }
        return quantity <= possibleQuantity ? null : "There is only " + possibleQuantity + " possible strings to generate";
    }

    public String isValid(String charset, int min, int max, int quantity) {
        if (uniqueChars(charset) != null) return uniqueChars(charset);
        if (minMax(min, max) != null) return minMax(min, max);
        if (quantity(charset, min, max, quantity) != null) return quantity(charset, min, max, quantity);
        return null;
    }
}
