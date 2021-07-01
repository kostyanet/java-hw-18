import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserDB {
    private final String path;
    ObjectMapper mapper = new ObjectMapper();

    public UserDB(String path) {
        this.path = path;
    }

    public List<User> read() {
        List<User> users = new ArrayList<>();

        try {
            users = Arrays.asList(mapper.readValue(Paths.get(path).toFile(), User[].class));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return users;
    }

    public void write(List<User> users) {
        Gson gson = new Gson();
        String usersString = gson.toJson(users);

        try {
            FileWriter file = new FileWriter(path);
            file.write(usersString);
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
