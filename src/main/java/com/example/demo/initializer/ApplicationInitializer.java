package com.example.demo.initializer;

import com.example.demo.component.ObjectDataGenerator;
import com.example.demo.resolver.FakeContextResolver;
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
import java.util.Map;

@Component
public class ApplicationInitializer implements ApplicationRunner {
    Logger LOG = LoggerFactory.getLogger(ApplicationInitializer.class);

    @Autowired
    ObjectDataGenerator objectDataGenerator;
    @Autowired
    FakeContextResolver fakeContextResolver;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        LOG.info("============Starting the application");


        Map<String,Object> data =(Map<String,Object>)getYamlData("classpath:json/config2.yml").get("data");
        Map<String,Object> out = objectDataGenerator.generate(data);

        //this out can be converted to xml, json,...
        LOG.info(new ObjectMapper().writeValueAsString(out));
        LOG.info("============Done generating data");
    }
    Map<String,Object> getYamlData(String path) throws IOException {
        Yaml yaml = new Yaml();

        File file = ResourceUtils.getFile(path);


        InputStream in = new FileInputStream(file);


        Map<String, Object> obj = yaml.load(in);
        System.out.println(obj);
        return obj;
    }
}
