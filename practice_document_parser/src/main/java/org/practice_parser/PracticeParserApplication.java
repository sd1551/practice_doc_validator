package org.practice_parser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"org.practice_parser"})
public class PracticeParserApplication {
    public static void main(String[] args) {
        SpringApplication.run(PracticeParserApplication.class, args);
    }
}