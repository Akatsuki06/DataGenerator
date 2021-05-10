package io.github.datagenerator.utils;

import org.springframework.util.ResourceUtils;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class YamlUtility {
    public static final Map<String,Object> readYamlFile(String filePath) throws FileNotFoundException {
            Yaml yaml = new Yaml();
            Path path = Paths.get(filePath);
            InputStream in = new FileInputStream(path.toFile());
            Map<String, Object> obj = yaml.load(in);
            return obj;
    }
    public static final Map<String,Object> readYamlFile(Path path) throws FileNotFoundException {
        Yaml yaml = new Yaml();
        InputStream in = new FileInputStream(path.toFile());
        Map<String, Object> obj = yaml.load(in);
        return obj;
    }
}
