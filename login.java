
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class login {

    private static final String FILE_NAME = "mapData.ser";
    private static Map<String, String> hashMap = new HashMap<>();

    public static void main(String[] args) {
        loadMap();
        try {
            String jsonString = readJsonAsString("C:\\Users\\trent\\IdeaProjects\\login\\src\\users.json");
            System.out.println("JSON Data: " + jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scanner sc = new Scanner(System.in);
        System.out.println("Login | Sign-up");
        String choice = sc.nextLine();

        if (choice.equals("Sign-up")) {
            signUp(sc);
        } else if (choice.equals("Login")) {
            login(sc);
        } else {
            System.out.println("Invalid choice.");
        }
    }

    // Method to handle user sign-up
    private static void signUp(Scanner sc) {
        System.out.print("Enter a username: ");
        String newUsername = sc.nextLine();

        System.out.print("Enter a password: ");
        String newPassword = sc.nextLine();

        if (hashMap.containsKey(newUsername)) {
            System.out.println("Username already exists. Please choose a different username.");
        } else {
            String hashedPassword = hashPassword(newPassword);
            hashMap.put(newUsername, hashedPassword);
            saveMap();
            System.out.println("Account Created!");
        }
    }

    // Method to handle user login
    private static void login(Scanner sc) {
        System.out.print("Enter username: ");
        String username = sc.nextLine().trim();

        System.out.print("Enter password: ");
        String password = sc.nextLine().trim();

        String storedHash = hashMap.get(username);
        // Check if the username exists and the password matches
        if (storedHash != null && hashMap.get(username).equals(hashPassword(password))) {
            System.out.println("Login successful");
        } else {
            System.out.println("Invalid username or password");
        }
    }

    private static void saveMap(){
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME));
            oos.writeObject(hashMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void loadMap(){
        File file = new File(FILE_NAME);
        if (file.exists()) {
            try {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
                hashMap = (Map<String, String>) ois.readObject();
            } catch (IOException | ClassNotFoundException e){
                e.printStackTrace();
            }
        }
    }
    private static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = digest.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    public static String readJsonAsString(String filePath) throws IOException {
        Gson gson = new Gson();
        // Parse JSON file into a JsonElement
        JsonElement jsonElement = JsonParser.parseReader(new FileReader(filePath));
        // Convert JsonElement to String
        return gson.toJson(jsonElement);
    }
}
