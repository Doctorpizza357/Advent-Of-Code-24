import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws IOException {
        // Read input map from file
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
        char direction = '^'; // Assuming initial direction is always up '^'

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

        int[][] deltas = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}}; // Directions for '^', '>', 'v', '<'
        int directionIndex = "^>v<".indexOf(direction);
        Set<String> visitedPositions = new HashSet<>();
        int x = startX, y = startY;

        // Simulate the guard's movement
        visitedPositions.add(x + "," + y); // Include the starting position
        while (x >= 0 && x < rows && y >= 0 && y < cols) {
            int newX = x + deltas[directionIndex][0];
            int newY = y + deltas[directionIndex][1];

            if (newX < 0 || newX >= rows || newY < 0 || newY >= cols || map[newX][newY] == '#') {
                directionIndex = (directionIndex + 1) % 4; // Turn right 90 degrees
            } else {
                x = newX;
                y = newY;
                visitedPositions.add(x + "," + y); // Add the new position to the set
            }

            // Break if the guard leaves the map
            if (newX < 0 || newX >= rows || newY < 0 || newY >= cols) {
                break;
            }
        }

        // Output the number of distinct positions visited
        System.out.println("Distinct positions visited: " + visitedPositions.size());
    }
}
