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

    @Value("${datagen.schema.path}")
    private String schemaPath;
    @Value("${datagen.out.path}")
    private String outputPath;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        LOGGER.info("============Starting the application");

        StopWatch processWatch = new StopWatch();
        processWatch.start();

        DataGeneratorService dataGeneratorService = new DataGeneratorService(schemaPath,null);

        List<String> data = dataGeneratorService.process();

        processWatch.stop();
        LOGGER.info("Took {} seconds",processWatch.getTotalTimeSeconds());

        LOGGER.info("============Done generating data");
        LOGGER.info("DATA=====\n{}",data);
    }
}
