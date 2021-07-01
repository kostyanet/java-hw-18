import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Реализовать maven project – UserConsoleCrudApplication
 * <p>
 * Вам необходимо реализовать CRUD (create/read/update/delete) приложение, которое,
 * в качестве взаимодействия с пользователем использует консоль (BufferedReader).
 * Вы должны предоставить весь цикл создания, чтения (всем списком так и по айди),
 * обновления по айди и удаления по айди сущности User (id: string, firstName: string,
 * lastName: string, fullName: string, email: unique string).
 * В качестве источника хранения данных вы должны использовать файл users.json
 */
public class UserConsoleCrudApplication {
    public static void main(String[] args) {
        System.out.println("Commands:");
        System.out.println("'list' - lists the users");
        System.out.println("'get <id>' - get a user by ID");
        System.out.println("'post <firstName> <lastName> <email>' - create a user");
        System.out.println("'put <id> <updatedKey> <newValue>' - update a field in a user record");
        System.out.println("'delete <id>' - delete a user by ID");
        System.out.println("'exit' - quit the app");

        listenConsole();
    }

    public static void listenConsole() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        CommandHandler handler = new CommandHandler();
        boolean shallExit = false;

        while (!shallExit) {
            try {
                String command = reader.readLine();
                shallExit = handler.handle(command);
            } catch (IOException | IllegalStateException e) {
                e.printStackTrace();
            } catch (IndexOutOfBoundsException | NumberFormatException e) {
                System.out.println("Wrong command format!");
            }
        }
    }
}
