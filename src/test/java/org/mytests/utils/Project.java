package org.mytests.utils;

import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.*;

import static java.util.Collections.singletonList;

@Data
@Accessors(chain = true)
public class Project<T> {

    String code;
    String prefix;
    String name;
    String description;
    String domain;
    String framework;
    String spScale;
    String language;
    boolean verification;
    boolean invitation;
    double spPrice;
    List<String> selectLocations;
    List<String> rejectLocations;
    List<String> autoSkills;
    List<String> testSkills;
    String onBoardingContent;
    List<String> owners;
    List<String> managers;
    Map<String, List<String>> core;
    T testCaseSources;


    public static Project<Manual> defaultProject() {
        Project<Manual> project = new Project<>();
        String rand = RandomStringUtils.randomAlphabetic(3).toUpperCase();
        project.setCode("T" + rand).setPrefix("T" + rand)
                .setName("Name(T" + rand + ")")
                .setDescription("Desc T" + rand)
                .setDomain("other")
                .setFramework("other")
                .setSpScale("1,2,4,8,16")
                .setLanguage("other")
                .setVerification(true)
                .setInvitation(true)
                .setSpPrice(1.2d)
                .setSelectLocations(singletonList("Germany"))
                .setRejectLocations(Collections.emptyList())
                .setAutoSkills(singletonList("Automated Testing in .NET"))
                .setTestSkills(singletonList("Accessibility Testing"))
                .setOnBoardingContent("RTE Onboarding description")
                .setOwners(singletonList(User.getUser("admin").getUsername()))
                .setManagers(singletonList(User.getUser("admin2").getUsername()))
                .setCore(new HashMap<String, List<String>>() {{
                    put(User.getUser("automator").getUsername(), Arrays.asList("automator", "qualifier", "reviewer", "verifier"));
                }});
        project.setTestCaseSources(Manual.getDefault());

        return project;
    }
    @Data
    @Accessors(chain = true)
    public abstract static class TcSources {
        String type;
        String projectId;
        String syncableField;
        Map<String, String> auth;
    }

    @Data
    @Accessors(chain = true)
    public static class Manual extends TcSources {
        String sourceType;
        String upload;

        public static Manual getDefault() {
            Manual manual = new Manual();
            manual.setType("Manual").setSyncableField("custom_automate_space");
            manual.setSourceType("Test Rail")
                    .setUpload("src/test/resources/cases.csv");
            return manual;
        }
    }

}
