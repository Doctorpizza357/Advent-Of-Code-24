import java.io.*;
import java.util.*;

public class Main {
    private static final int WIDTH = 101;
    private static final int HEIGHT = 103;
    private static final int TIME = 100;

    public static void main(String[] args) throws IOException {
        List<Robot> robots = readInput("input.txt");

        Map<Point, Integer> grid = new HashMap<>();
        for (Robot robot : robots) {
            int x = (robot.px + robot.vx * TIME) % WIDTH;
            int y = (robot.py + robot.vy * TIME) % HEIGHT;
            if (x < 0) x += WIDTH;
            if (y < 0) y += HEIGHT;

            Point point = new Point(x, y);
            grid.put(point, grid.getOrDefault(point, 0) + 1);
        }

        int quadrant1 = 0;
        int quadrant2 = 0;
        int quadrant3 = 0;
        int quadrant4 = 0;
        int midX = WIDTH / 2;
        int midY = HEIGHT / 2;

        for (Map.Entry<Point, Integer> entry : grid.entrySet()) {
            Point p = entry.getKey();
            int count = entry.getValue();

            if (p.x == midX || p.y == midY) continue;

            if (p.x > midX && p.y > midY) {
                quadrant1 += count;
            } else if (p.x > midX && p.y < midY) {
                quadrant2 += count;
            } else if (p.x < midX && p.y < midY) {
                quadrant3 += count;
            } else if (p.x < midX && p.y > midY) {
                quadrant4 += count;
            }
        }

        int safetyFactor = quadrant1 * quadrant2 * quadrant3 * quadrant4;
        System.out.println(safetyFactor);
    }

    private static List<Robot> readInput(String filename) throws IOException {
        List<Robot> robots = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                String[] pos = parts[0].substring(2).split(",");
                String[] vel = parts[1].substring(2).split(",");
                int px = Integer.parseInt(pos[0]);
                int py = Integer.parseInt(pos[1]);
                int vx = Integer.parseInt(vel[0]);
                int vy = Integer.parseInt(vel[1]);
                robots.add(new Robot(px, py, vx, vy));
            }
        }
        return robots;
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

class Point {
    int x, y;

    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return x == point.x && y == point.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
