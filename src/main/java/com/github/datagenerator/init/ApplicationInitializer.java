package com.github.datagenerator.init;

import com.github.datagenerator.service.DataGeneratorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
public class ApplicationInitializer implements ApplicationRunner {
    private final Logger LOGGER = LoggerFactory.getLogger(ApplicationInitializer.class);

    @Autowired
    DataGeneratorService dataGeneratorService;

    @Value("${datagen.schema.path}")
    private String schemaPath;
    @Value("${datagen.out.path}")
    private String outputPath;
    @Value("${datagen.out.count}")
    private Integer outCount;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        LOGGER.info("============Starting the application");

        List<String> generatedData = new ArrayList<>();

        StopWatch processWatch = new StopWatch();
        processWatch.start();
        List<CompletableFuture<String>> futures = new ArrayList<>();


        for (int i=0;i<outCount;i++){
            futures.add(dataGeneratorService.generate(schemaPath));
        }
        for (int i=0;i<outCount;i++){
            generatedData.add(futures.get(i).get());
        }
        processWatch.stop();
        LOGGER.info("Took {} seconds",processWatch.getTotalTimeSeconds());

        LOGGER.info("============Done generating data");
        LOGGER.info("DATA=====\n{}",generatedData);
    }
}
