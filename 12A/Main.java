import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) {
        char[][] map = readMapFromFile("input.txt");
        int totalPrice = calculateTotalPrice(map);
        System.out.println(totalPrice);
    }

    private static char[][] readMapFromFile(String filename) {
        List<char[]> mapList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                mapList.add(line.toCharArray());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mapList.toArray(new char[0][]);
    }

    private static int calculateTotalPrice(char[][] map) {
        int rows = map.length;
        int cols = map[0].length;
        boolean[][] visited = new boolean[rows][cols];
        int totalPrice = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (!visited[i][j]) {
                    int[] areaPerimeter = calculateAreaAndPerimeter(map, visited, i, j, map[i][j]);
                    int area = areaPerimeter[0];
                    int perimeter = areaPerimeter[1];
                    totalPrice += area * perimeter;
                }
            }
        }

        return totalPrice;
    }

    private static int[] calculateAreaAndPerimeter(char[][] map, boolean[][] visited, int i, int j, char plantType) {
        int rows = map.length;
        int cols = map[0].length;
        int area = 0;
        int perimeter = 0;
        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{i, j});
        visited[i][j] = true;

        while (!queue.isEmpty()) {
            int[] cell = queue.poll();
            int x = cell[0];
            int y = cell[1];
            area++;

            for (int[] dir : directions) {
                int nx = x + dir[0];
                int ny = y + dir[1];

                if (nx < 0 || ny < 0 || nx >= rows || ny >= cols || map[nx][ny] != plantType) {
                    perimeter++;
                } else if (!visited[nx][ny]) {
                    visited[nx][ny] = true;
                    queue.add(new int[]{nx, ny});
                }
            }
        }

        return new int[]{area, perimeter};
    }
}
