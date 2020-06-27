package com.example.demo.init;

import com.example.demo.component.ListValueGenerator;
import com.example.demo.component.ObjectDataGenerator;
import com.example.demo.service.DataGeneratorService;
import com.example.demo.utils.YamlUtility;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class ApplicationInitializer implements ApplicationRunner {
    Logger LOG = LoggerFactory.getLogger(ApplicationInitializer.class);

    @Autowired
    DataGeneratorService dataGeneratorService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        LOG.info("============Starting the application");

        //java -jar app.jar --data-gen.schema=file.yml --data-gen.out=string/file --data-gen.filename=name --data-gen.n=10?

        String schemaFile =args.getOptionValues("data-gen.schema").get(0);
        String outFile = args.getOptionValues("data-gen.out").get(0);
        int n = Integer.parseInt(args.getOptionValues("data-gen.n").get(0));

        System.out.println(schemaFile);


        List<String> generatedData = new ArrayList<>();

        long startTime = System.currentTimeMillis();
        for (int i=0;i<n;i++){
            generatedData.add(dataGeneratorService.generate());
        }
        LOG.info("Took {} seconds",(System.currentTimeMillis()-startTime)/1000.0);

//todo:
//        check if its a json? or an array or a value?
//          check for key data --> its object
//        else check for key list --> its an array of objects/literals
//        else if we have key for

//        LOG.info("yaml {}",new Yaml().dump(out));
//        LOG.info("xml {}",new);
        LOG.info("============Done generating data");
        LOG.info("DATA=====\n{}",generatedData);
    }
}
