package com.example.demo.service;

import com.example.demo.component.ObjectDataGenerator;
import com.example.demo.utils.YamlUtility;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class DataGeneratorService {

    Logger LOG = LoggerFactory.getLogger(DataGeneratorService.class);

    @Value("${data-gen.schema}")
    String schemaPath;

    @Autowired
    ObjectDataGenerator objectDataGenerator;


    public String generate() throws Exception {
        Map<String,Object> ymlData = YamlUtility.readYamlFile(schemaPath);
        Map<String,Object> data = (Map<String,Object>)ymlData.get("data");

        Map<String,Object> out = objectDataGenerator.generate(data);


        //map.values().removeIf(Objects::isNull);
        //this out can be converted to xml, json,...

        String result = new ObjectMapper().writeValueAsString(out);
        return result;
    }




}
