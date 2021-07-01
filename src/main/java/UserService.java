import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class UserService {
    private final UserDB db = new UserDB("users.json");
    private final static String OK = "Ok!";

    public List<User> getAll() {
        return db.read();
    }

    public User getUser(int id) throws UserServiceException {
        List<User> users = db.read().stream()
                .filter(u -> u.getId() == id)
                .collect(Collectors.toList());

        if (users.isEmpty())
            throw new UserServiceException(UserServiceErrors.NOT_FOUND.getMessage());

        return users.get(0);
    }

    public String post(String firstName, String lastName, String email) throws UserServiceException {
        List<User> users = this.getAll();

        long duplicates = users.stream()
                .filter(u -> u.getEmail().equals(email))
                .count();

        if (duplicates > 0)
            throw new UserServiceException(UserServiceErrors.EMAIL_EXISTS.getMessage());

        int maxId = users.stream()
                .mapToInt(User::getId)
                .max()
                .orElse(0);

        List<User> updatedUsers = new ArrayList<>(users);
        User user = new User(++maxId, firstName, lastName, email);

        updatedUsers.add(user);
        db.write(updatedUsers);
        return OK;
    }

    public String put(int id, String key, String value) throws UserServiceException {
        if (!Arrays.asList(User.EDITABLE_FIELDS).contains(key))
            throw new UserServiceException(UserServiceErrors.READONLY_FIELD.getMessage());

        User user = getUser(id);

        List<User> updatedList = this.getAll().stream()
                .map((u) -> u.getId() == id
                        ? updateUser(user, key, value)
                        : u)
                .collect(Collectors.toList());

        db.write(updatedList);
        return OK;
    }

    private static User updateUser(User user, String key, String value) {
        switch (key) {
            case User.FIRST_NAME:
                user.setFirstName(value);
                user.setFullName(value + " " + user.getLastName());
                break;

            case User.LAST_NAME:
                user.setLastName(value);
                user.setFullName(user.getFirstName() + " " + value);
                break;

            case User.EMAIL:
                user.setEmail(value);
        }

        return user;
    }

    public String delete(int id) throws UserServiceException {
        getUser(id);

        List<User> updatedList = this.getAll().stream()
                .filter(u -> u.getId() != id)
                .collect(Collectors.toList());

        db.write(updatedList);
        return OK;
    }
}
