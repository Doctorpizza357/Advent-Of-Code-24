import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        // Read input from file
        Scanner scanner = new Scanner(new File("input.txt"));
        List<String> lines = new ArrayList<>();

        while (scanner.hasNextLine()) {
            lines.add(scanner.nextLine());
        }
        scanner.close();

        long totalCalibrationResult = 0;

        for (String line : lines) {
            String[] parts = line.split(": ");
            long testValue = Long.parseLong(parts[0]);
            String[] numbers = parts[1].split(" ");
            if (canFormTestValue(testValue, numbers)) {
                totalCalibrationResult += testValue;
            }
        }

        System.out.println(totalCalibrationResult);
    }

    private static boolean canFormTestValue(long testValue, String[] numbers) {
        long[] nums = new long[numbers.length];
        for (int i = 0; i < numbers.length; i++) {
            nums[i] = Long.parseLong(numbers[i]);
        }
        return evaluate(nums, 0, nums[0], testValue);
    }

    private static boolean evaluate(long[] nums, int pos, long current, long target) {
        if (pos == nums.length - 1) {
            return current == target;
        }
        return evaluate(nums, pos + 1, current + nums[pos + 1], target) ||
               evaluate(nums, pos + 1, current * nums[pos + 1], target) ||
               evaluate(nums, pos + 1, concat(current, nums[pos + 1]), target);
    }

    private static long concat(long a, long b) {
        String concatStr = String.valueOf(a) + String.valueOf(b);
        return Long.parseLong(concatStr);
    }
}
