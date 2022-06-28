package org.mytests.utils;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String key;
    private String username;
    private String password;
    private String role;
    private String displayName;


    public static User getUser(String key) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<User> users = objectMapper.readValue(new File("src/test/resources/users.json"),
                    new TypeReference<List<User>>() {});
            return users.stream().filter(u -> u.getKey().equalsIgnoreCase(key))
                    .findFirst().orElseThrow(() -> new IllegalArgumentException("No user for key: " + key));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


}
