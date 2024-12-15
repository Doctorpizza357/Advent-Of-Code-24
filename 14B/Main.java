import java.io.*;
import java.util.*;

public class Main {
    private static final int WIDTH = 101;
    private static final int HEIGHT = 103;
    private static final int[] DX = {-1, 0, 1, 0};
    private static final int[] DY = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        List<Robot> robots = readInput("input.txt");

        int seconds = findChristmasTreePattern(robots);
        System.out.println(seconds);
    }

    private static List<Robot> readInput(String filename) throws IOException {
        List<Robot> robots = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                int[] values = Arrays.stream(parts)
                        .flatMap(p -> Arrays.stream(p.substring(2).split(",")))
                        .mapToInt(Integer::parseInt)
                        .toArray();
                robots.add(new Robot(values[0], values[1], values[2], values[3]));
            }
        }
        return robots;
    }

    private static int findChristmasTreePattern(List<Robot> robots) {
        for (int t = 1; t < 1000000; t++) {
            char[][] grid = new char[HEIGHT][WIDTH];
            for (char[] row : grid) Arrays.fill(row, '.');

            for (Robot robot : robots) {
                int px = (robot.px + robot.vx * t) % WIDTH;
                int py = (robot.py + robot.vy * t) % HEIGHT;
                if (px < 0) px += WIDTH;
                if (py < 0) py += HEIGHT;
                grid[py][px] = '#';
            }

            int components = countComponents(grid);
            if (components <= 200) {
                System.out.println("Grid after " + t + " seconds:");
                for (char[] row : grid) {
                    System.out.println(new String(row));
                }
                return t;
            }
        }
        return -1;
    }

    private static int countComponents(char[][] grid) {
        int components = 0;
        boolean[][] seen = new boolean[HEIGHT][WIDTH];
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                if (grid[y][x] == '#' && !seen[y][x]) {
                    components++;
                    Queue<int[]> queue = new LinkedList<>();
                    queue.add(new int[]{x, y});
                    while (!queue.isEmpty()) {
                        int[] point = queue.poll();
                        int px = point[0], py = point[1];
                        if (seen[py][px]) continue;
                        seen[py][px] = true;
                        for (int d = 0; d < 4; d++) {
                            int nx = (px + DX[d] + WIDTH) % WIDTH;
                            int ny = (py + DY[d] + HEIGHT) % HEIGHT;
                            if (grid[ny][nx] == '#' && !seen[ny][nx]) {
                                queue.add(new int[]{nx, ny});
                            }
                        }
                    }
                }
            }
        }
        return components;
    }
}

class Robot {
    int px, py, vx, vy;

    Robot(int px, int py, int vx, int vy) {
        this.px = px;
        this.py = py;
        this.vx = vx;
        this.vy = vy;
    }
}
