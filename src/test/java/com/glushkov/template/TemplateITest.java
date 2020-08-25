package com.glushkov.template;

import com.glushkov.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class TemplateITest {

    @Test
    @DisplayName("Returns page with users table")
    void processAllUsersPageTest() throws IOException {
        //prepare
        Configuration configuration = new Configuration("/templates");
        Template template = configuration.getTemplate("/page.html");
        String expectedPage;

        try (InputStream inputStreamFromFile = new BufferedInputStream(getClass().getResourceAsStream("/expected-pages" + "/expectedTablePage.html"))) {
            expectedPage = new String(inputStreamFromFile.readAllBytes());
        }

        User firstUser = new User();
        firstUser.setId(1);
        firstUser.setFirstName("Alex");
        firstUser.setSecondName("Developer");
        firstUser.setSalary(3000.0);
        firstUser.setDateOfBirth(LocalDate.of(1993, 7, 22));

        User secondUser = new User();
        secondUser.setId(2);
        secondUser.setFirstName("Roma");
        secondUser.setSecondName("Developer");
        secondUser.setSalary(3000.0);
        secondUser.setDateOfBirth(LocalDate.of(1991, 7, 22));

        List<User> usersList = new ArrayList<>();
        usersList.add(firstUser);
        usersList.add(secondUser);


        Map<String, Object> usersMap = new HashMap<>();
        usersMap.put("users", usersList);

        //when
        String actualPage = template.process(usersMap);

        //then
        assertEquals(expectedPage, actualPage);
    }

    @Test
    @DisplayName("Returns page form for user registration")
    void processAddUserPageTest() throws IOException {
        //prepare
        Configuration configuration = new Configuration("/templates");
        Template template = configuration.getTemplate("/form.html");
        String expectedPage;

        try (InputStream inputStreamFromFile = new BufferedInputStream(getClass().getResourceAsStream("/templates" + "/form.html"))) {
            expectedPage = new String(inputStreamFromFile.readAllBytes());
        }

        Map<String, Object> usersMap = new HashMap<>();

        //when
        String actualPage = template.process(usersMap);

        //then
        assertEquals(expectedPage, actualPage);
    }

    @Test
    @DisplayName("Returns a page with a message stating that no users were found")
    void processSearchUserResultPageTest() throws IOException {
        //prepare
        Configuration configuration = new Configuration("/templates");
        Template template = configuration.getTemplate("/page.html");
        String expectedPage;

        try (InputStream inputStreamFromFile = new BufferedInputStream(getClass().getResourceAsStream("/expected-pages" + "/expectedPageNoUsersFound.html"))) {
            expectedPage = new String(inputStreamFromFile.readAllBytes());
        }

        Map<String, Object> usersMap = new HashMap<>();
        usersMap.put("message", "Sorry, no users were found for your request: Alex");
        //when
        String actualPage = template.process(usersMap);

        //then
        assertEquals(expectedPage, actualPage);
    }

    @Test
    @DisplayName("Returns a page with exception message and code")
    void processErrorPageTest() throws IOException {
        //prepare
        Configuration configuration = new Configuration("/templates");
        Template template = configuration.getTemplate("/error.html");
        String expectedPage;

        try (InputStream inputStreamFromFile = new BufferedInputStream(getClass().getResourceAsStream("/expected-pages" + "/expectedErrorPage.html"))) {
            expectedPage = new String(inputStreamFromFile.readAllBytes());
        }

        Map<String, Object> usersMap = new HashMap<>();
        usersMap.put("message", "java.io.FileNotFoundException");
        usersMap.put("code", 404);

        //when
        String actualPage = template.process(usersMap);

        //then
        assertEquals(expectedPage, actualPage);
    }

    @Test
    @DisplayName("Returns a page with fields filled in with user data")
    void processEditUserPageTest() throws IOException {
        //prepare
        Configuration configuration = new Configuration("/templates");
        Template template = configuration.getTemplate("/edit.html");
        String expectedPage;

        try (InputStream inputStreamFromFile = new BufferedInputStream(getClass().getResourceAsStream("/expected-pages" + "/expectedEditPage.html"))) {
            expectedPage = new String(inputStreamFromFile.readAllBytes());
        }

        Map<String, Object> userMap = new HashMap<>();
        userMap.put("id", 1);
        userMap.put("firstName", "Alex");
        userMap.put("secondName", "Developer");
        userMap.put("salary", 3000.0);
        userMap.put("dateOfBirth", LocalDate.of(1993, 6, 22));

        //when
        String actualPage = template.process(userMap);

        //then
        assertEquals(expectedPage, actualPage);
    }
}