import java.util.Scanner;

public class SingletonScanner {
    private static SingletonScanner instance;
    private final Scanner scanner;

    private SingletonScanner() {
        scanner = new Scanner(System.in);
    }

    public static SingletonScanner getInstance() {
        if (instance == null) {
            instance = new SingletonScanner();
        }

        return instance;
    }

    public Scanner getScanner() {
        return scanner;
    }

    public void closeScanner() {
        scanner.close();
    }
}
