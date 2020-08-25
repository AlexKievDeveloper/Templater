package com.glushkov.template;

import com.glushkov.entity.User;

import java.util.List;
import java.util.Map;

public class Template {

    private final String pageTemplate;

    public Template(String pageTemplate) {
        this.pageTemplate = pageTemplate;
    }

    public String process(Map<String, Object> pageVariables) {

        String tableRowTemplate = pageTemplate.contains("Table users") ? pageTemplate.substring(pageTemplate.indexOf("<tr class=\"row\">\n" +
                "            <td class=\"col-lg-1\">UserId</td>"), pageTemplate.indexOf("</tbody>")) : "";

        if (pageVariables.get("users") != null) {

            List<User> usersList = (List<User>) pageVariables.get("users");
            StringBuilder stringBuilder = new StringBuilder();

            for (User user : usersList) {
                String row = tableRowTemplate.replace("UserId", String.valueOf(user.getId()))
                        .replace("FirstName", user.getFirstName())
                        .replace("SecondName", user.getSecondName())
                        .replace("Salary", String.valueOf(user.getSalary()))
                        .replace("DateOfBirth", String.valueOf(user.getDateOfBirth()));
                stringBuilder.append(row);
            }

            return pageTemplate.replace(tableRowTemplate, stringBuilder.toString());
        }

        switch (pageVariables.size()) {
            case 0:
                return pageTemplate;

            case 1:
                return pageTemplate.replace(tableRowTemplate, "<tr class=\"row\"><td class=\"col-lg-12\"><h3>"
                        .concat(String.valueOf(pageVariables.get("message"))).concat("</h3></td></tr>"));

            case 2:
                return pageTemplate.replace("code", String.valueOf(pageVariables.get("code")))
                        .replace("message", String.valueOf(pageVariables.get("message")));

            case 5:
                return pageTemplate.replace("UserId", String.valueOf(pageVariables.get("id")))
                        .replace("FirstName", String.valueOf(pageVariables.get("firstName")))
                        .replace("SecondName", String.valueOf(pageVariables.get("secondName")))
                        .replace("Salary", String.valueOf(pageVariables.get("salary").toString()))
                        .replace("DateOfBirth", String.valueOf(pageVariables.get("dateOfBirth")));
        }

        throw new RuntimeException("Page variables don`t contain suitable values");
    }

    public String getPageTemplate() {
        return pageTemplate;
    }
}
