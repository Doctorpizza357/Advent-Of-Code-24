import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(new File("input.txt"));
        List<String> lines = new ArrayList<>();

        while (scanner.hasNextLine()) {
            lines.add(scanner.nextLine());
        }
        scanner.close();

        int rows = lines.size();
        int cols = lines.get(0).length();
        char[][] map = new char[rows][cols];
        int startX = -1, startY = -1;
        char direction = '^';

        for (int i = 0; i < rows; i++) {
            String line = lines.get(i);
            for (int j = 0; j < cols; j++) {
                map[i][j] = line.charAt(j);
                if (line.charAt(j) == '^' || line.charAt(j) == '>' || line.charAt(j) == 'v' || line.charAt(j) == '<') {
                    startX = i;
                    startY = j;
                    direction = line.charAt(j);
                }
            }
        }

        if (startX == -1 || startY == -1 || direction == ' ') {
            throw new IllegalArgumentException("Invalid initial position or direction.");
        }

        int[][] directionVectors = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}}; // Directions for '^', '>', 'v', '<'
        int directionIndex = "^>v<".indexOf(direction);
        Set<String> visitedPositions = new HashSet<>();
        int x = startX, y = startY;

        visitedPositions.add(x + "," + y);
        while (true) {
            int newX = x + directionVectors[directionIndex][0];
            int newY = y + directionVectors[directionIndex][1];

            if (newX < 0 || newX >= rows || newY < 0 || newY >= cols || map[newX][newY] == '#') {
                directionIndex = (directionIndex + 1) % 4; // Turn right 90 degrees
            } else {
                x = newX;
                y = newY;
                visitedPositions.add(x + "," + y);
            }

            // Break if the guard leaves the map
            if (newX < 0 || newX >= rows || newY < 0 || newY >= cols) {
                break;
            }
        }


        int countLoopPositions = 0;
        for (int o_r = 0; o_r < rows; o_r++) {
            for (int o_c = 0; o_c < cols; o_c++) {
                if (o_r == startX && o_c == startY) {
                    continue;
                }

                Set<String> seen = new HashSet<>();
                Set<String> seenRC = new HashSet<>();
                x = startX;
                y = startY;
                directionIndex = "^>v<".indexOf(direction);

                while (true) {
                    if (seen.contains(x + "," + y + "," + directionIndex)) {
                        countLoopPositions++;
                        break;
                    }

                    seen.add(x + "," + y + "," + directionIndex);
                    seenRC.add(x + "," + y);
                    int newX = x + directionVectors[directionIndex][0];
                    int newY = y + directionVectors[directionIndex][1];

                    if (newX < 0 || newX >= rows || newY < 0 || newY >= cols) {
                        break;
                    }

                    if (map[newX][newY] == '#' || (newX == o_r && newY == o_c)) {
                        directionIndex = (directionIndex + 1) % 4; // Turn right 90 degrees
                    } else {
                        x = newX;
                        y = newY;
                    }
                }
            }
        }

        System.out.println(countLoopPositions);
    }
}
