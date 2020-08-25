package com.glushkov.template;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConfigurationITest {

    @Test
    @DisplayName("Returns an object of Template class which contains a page template")
    void getTemplateTest() throws IOException {
        //prepare
        Configuration configuration = new Configuration("/templates");
        String fileName = "/form.html";
        String expectedTemplate;

        try (InputStream inputStreamFromFile = new BufferedInputStream(getClass().getResourceAsStream("/templates" + fileName))) {
            expectedTemplate = new String(inputStreamFromFile.readAllBytes());
        }

        //when
        Template template = configuration.getTemplate(fileName);

        //then
        assertEquals(expectedTemplate, template.getPageTemplate());
    }
}