import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        ArrayList<ArrayList<Integer>> reports = new ArrayList<>();

        try (Scanner scanner = new Scanner(new File("input.txt"))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] numbers = line.split(" ");
                ArrayList<Integer> report = new ArrayList<>();
                for (String number : numbers) {
                    report.add(Integer.parseInt(number));
                }
                reports.add(report);
            }
        }

        int safeCount = getSafetyCount(reports);
        System.out.println(safeCount);
    }

    private static int getSafetyCount(ArrayList<ArrayList<Integer>> list) {
        int finalCount = 0;

        for (ArrayList<Integer> list1 : list) {
            boolean safe = false;
            int removeIndex = -1;

            while (!safe) {
                ArrayList<Integer> list2 = new ArrayList<>(list1);
                if (removeIndex != -1) {
                    list2.remove(removeIndex);
                }
                safe = checkList(list2);
                removeIndex++;

                if (removeIndex > list1.size() - 1) {
                    break;
                }
            }

            if (safe) {
                finalCount++;
            }
        }

        return finalCount;
    }

    private static boolean checkList(List<Integer> list) {
        int prevOp = 0;
        for (int i = 0; i < list.size() - 1; i++) {
            int difference = list.get(i) - list.get(i + 1);
            if (isSafe(difference, prevOp)) {
                return false;
            } else {
                if (prevOp == 0) {
                    prevOp = difference;
                }
            }
        }

        return true;
    }

    private static boolean isSafe(int difference, int prevOp) {
        return difference > 0 && prevOp < 0 || difference < 0 && prevOp > 0 || difference > 3 || difference < -3
                || difference == 0;
    }
}
