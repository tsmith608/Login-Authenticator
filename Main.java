import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String username, password;

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter your username: ");
        username = sc.nextLine();

        System.out.print("Enter your password: ");
        password = sc.nextLine();
        isValidCredentials(username, password);
    }
    public static void isValidCredentials(String username, String password) {
        if (username.equals("robert") & (password.equals("password123"))) {
            System.out.println("Login successful");
        } else System.out.println("Not telling you");
    }
}