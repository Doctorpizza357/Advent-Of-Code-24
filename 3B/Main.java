import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.File;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(new File("input.txt"));
        int total = 0;
        boolean mulEnabled = true;
        Pattern patternMul = Pattern.compile("mul\\((\\d{1,3}),(\\d{1,3})\\)");
        // Pattern patternDo = Pattern.compile("do\\(\\)");
        // Pattern patternDont = Pattern.compile("don't\\(\\)");
        
        StringBuilder inputString = new StringBuilder();
        while (scanner.hasNextLine()) {
            inputString.append(scanner.nextLine()).append("\n");
        }
        String input = inputString.toString();

        for (int i = 0; i < input.length(); i++) {
            String substring = input.substring(i);
            if (substring.startsWith("do()")) {
                mulEnabled = true;
            }
            if (substring.startsWith("don't()")) {
                mulEnabled = false;
            }
            Matcher matcherMul = patternMul.matcher(substring);
            if (matcherMul.find() && matcherMul.start() == 0) {
                int num1 = Integer.parseInt(matcherMul.group(1));
                int num2 = Integer.parseInt(matcherMul.group(2));
                if (mulEnabled) {
                    total += num1 * num2;
                }
                i += matcherMul.end() - 1;
            }
        }
        System.out.println("Total: " + total);
    }
}
