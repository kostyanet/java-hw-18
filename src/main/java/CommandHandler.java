import java.util.List;

public class CommandHandler {
    private final UserService service = new UserService();

    public boolean handle(String input) {
        String[] parts = input.split(" ");
        String command = parts[0].toLowerCase();

        try {

            switch (command) {
                case "exit":
                    return true;

                case "list":
                    listAll();
                    break;

                case "get":
                    list(parts);
                    break;

                case "post":
                    post(parts);
                    break;

                case "put":
                    put(parts);
                    break;

                case "delete":
                    delete(parts);
                    break;

                default:
                    throw new IllegalStateException("Unsupported command: " + parts[0]);
            }

        } catch (UserServiceException e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    private void listAll() {
        List<User> users = service.getAll();

        if (users.size() == 0)
            System.out.println("No users yet.");
        else
            users.forEach(System.out::println);
    }

    private void list(String[] args) throws UserServiceException {
        int id = Integer.parseInt(args[1]);

        System.out.println(service.getUser(id));
    }

    private void post(String[] args) throws UserServiceException {
        String msg = service.post(args[1], args[2], args[3]);
        System.out.println(msg);
    }

    private void put(String[] args) throws UserServiceException {
        int id = Integer.parseInt(args[1]);
        String msg = service.put(id, args[2], args[3]);
        System.out.println(msg);
    }

    private void delete(String[] args) throws UserServiceException {
        int id = Integer.parseInt(args[1]);
        String msg = service.delete(id);
        System.out.println(msg);
    }
}
