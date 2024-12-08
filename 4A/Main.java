import java.util.Scanner;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(new File("input.txt"));
        String input = scanner.useDelimiter("\\A").next().replaceAll("\\s+", "");
        int length = input.length();
        int rows = (int) Math.sqrt(length);
        int cols = rows;

        if (rows * rows != length) {
            System.out.println("Input length is not a perfect square!");
            return;
        }

        String[][] grid = new String[rows][cols];
        int index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = String.valueOf(input.charAt(index++));
            }
        }

        int count = 0;

        // Check horizontally
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j <= cols - 4; j++) {
                count += checkPattern(grid, i, j, 0, 1);
            }
        }

        // Check vertically
        for (int i = 0; i <= rows - 4; i++) {
            for (int j = 0; j < cols; j++) {
                count += checkPattern(grid, i, j, 1, 0);
            }
        }

        // Check diagonally (top-left to bottom-right)
        for (int i = 0; i <= rows - 4; i++) {
            for (int j = 0; j <= cols - 4; j++) {
                count += checkPattern(grid, i, j, 1, 1);
            }
        }

        // Check diagonally (top-right to bottom-left)
        for (int i = 0; i <= rows - 4; i++) {
            for (int j = 3; j < cols; j++) {
                count += checkPattern(grid, i, j, 1, -1);
            }
        }

        System.out.println(count);
    }

    private static int checkPattern(String[][] grid, int row, int col, int rowInc, int colInc) {
        String[] pattern1 = {"X", "M", "A", "S"};
        String[] pattern2 = {"S", "A", "M", "X"};
        boolean match1 = true, match2 = true;

        for (int i = 0; i < 4; i++) {
            if (!grid[row + i * rowInc][col + i * colInc].equalsIgnoreCase(pattern1[i])) {
                match1 = false;
            }
            if (!grid[row + i * rowInc][col + i * colInc].equalsIgnoreCase(pattern2[i])) {
                match2 = false;
            }
        }
        
        return (match1 ? 1 : 0) + (match2 ? 1 : 0);
    }
}
