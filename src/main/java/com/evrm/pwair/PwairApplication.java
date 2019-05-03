package com.evrm.pwair;

import com.evrm.pwair.test.Test;
import com.evrm.pwair.test.TestRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class PwairApplication implements CommandLineRunner {

    @Autowired
    private TestRepository testRepository;

    public static void main(String[] args) {
        SpringApplication.run(PwairApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Test test= Test.builder()
                .id(5555L)
                .name("나에욥")
                .build();

        testRepository.addTest(test);

        log.info("args = {}", testRepository.findAll());
    }
}