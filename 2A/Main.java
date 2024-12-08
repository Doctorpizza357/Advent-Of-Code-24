import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        int safeCount = 0;

        try (BufferedReader br = new BufferedReader(new FileReader("input.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] numbers = line.split(" ");
                boolean valid = true;
                boolean increasing = true;
                boolean decreasing = true;

                for (int i = 1; i < numbers.length; i++) {
                    int a = Integer.parseInt(numbers[i - 1]);
                    int b = Integer.parseInt(numbers[i]);

                    if (Math.abs(a - b) < 1 || Math.abs(a - b) > 3) {
                        valid = false;
                        break;
                    }

                    if (a >= b) {
                        increasing = false;
                    }
                    if (a <= b) {
                        decreasing = false;
                    }
                }

                if (valid && (increasing || decreasing)) {
                    safeCount++;
                }
            }
        }

        System.out.println(safeCount);
    }
}
