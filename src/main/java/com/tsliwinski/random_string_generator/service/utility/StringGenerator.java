package com.tsliwinski.random_string_generator.service.utility;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class StringGenerator {
    private int getRandomInt(int bound) {
        return ThreadLocalRandom.current().nextInt(bound);
    }

    private char getRandomCharFromString(String str) {
        return str.charAt(getRandomInt(str.length()));
    }

    public String generate(String charset, int minLength, int maxLength) {
        int length = getRandomInt(maxLength-minLength+1) + minLength;
        if(length == 1) return "";
        StringBuffer stringBuffer = new StringBuffer(length);
        for(int i=0; i<length; i++) {
            stringBuffer.append(getRandomCharFromString(charset));
        }
        return stringBuffer.toString();
    }

    public Set<String> generateSet(String charset, int minLength, int maxLength, int quantity) {
        Set<String> stringSet = new HashSet<>();
        int ctr = 0;
        while(ctr < quantity) {
            if(stringSet.add(generate(charset, minLength, maxLength))) ctr++;
        }
        return stringSet;
    }


}
