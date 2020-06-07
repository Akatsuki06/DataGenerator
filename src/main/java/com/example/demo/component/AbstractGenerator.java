package com.example.demo.component;

import java.util.List;
import java.util.Map;

public interface AbstractGenerator {

    Map<String,Object> generate(Map<String,Object> props);
    Object generateValue(Map<String,Object> props);
    List<Object> generateListValue(Map<String,Object> props);

}
