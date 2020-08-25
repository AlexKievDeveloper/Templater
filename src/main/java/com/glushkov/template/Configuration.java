package com.glushkov.template;

import java.io.IOException;
import java.io.InputStream;

public class Configuration {

    private final String basePackagePath;
    public Configuration(String basePackagePath) {
        this.basePackagePath = basePackagePath;
    }

    public Template getTemplate(String filename) {
        try {
            InputStream inputStreamFromFile = getClass().getResourceAsStream(basePackagePath.concat(filename));
            String pageTemplate = new String(inputStreamFromFile.readAllBytes());

            return new Template(pageTemplate);
        } catch (IOException e) {
            throw new RuntimeException("Error while reading page. Path to page: ".concat(filename), e);
        }
    }
}