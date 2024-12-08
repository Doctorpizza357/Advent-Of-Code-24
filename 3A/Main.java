import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.File;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(new File("input.txt"));
        int total = 0;
        Pattern pattern = Pattern.compile("mul\\((\\d{1,3}),(\\d{1,3})\\)");
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            Matcher matcher = pattern.matcher(line);
            while (matcher.find()) {
                int num1 = Integer.parseInt(matcher.group(1));
                int num2 = Integer.parseInt(matcher.group(2));
                total += num1 * num2;
            }
        }
        System.out.println(total);
    }
}