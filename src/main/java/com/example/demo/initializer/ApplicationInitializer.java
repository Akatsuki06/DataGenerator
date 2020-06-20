package com.example.demo.initializer;

import com.example.demo.component.ObjectDataGenerator;
import com.example.demo.utils.YamlUtility;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

@Component
public class ApplicationInitializer implements ApplicationRunner {
    Logger LOG = LoggerFactory.getLogger(ApplicationInitializer.class);

    @Autowired
    ObjectDataGenerator objectDataGenerator;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        LOG.info("============Starting the application");


        Path path = Paths.get(ClassLoader.getSystemResource("json/config.yml").toURI());


//        check if its a json? or an array or a value?
//          check for key data --> its object
//        else check for key list --> its an array of objects/literals
//        else if we have key for
        Map<String,Object> ymlData = YamlUtility.readYamlFile(path);
        Map<String,Object> data = (Map<String,Object>)ymlData.get("data");

        Map<String,Object> out = objectDataGenerator.generate(data);


        //this out can be converted to xml, json,...
        LOG.info("json {}",new ObjectMapper().writeValueAsString(out));
        LOG.info("yaml {}",new Yaml().dump(out));
//        LOG.info("xml {}",new);
        LOG.info("============Done generating data");
    }
}
