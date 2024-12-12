import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        List<String> data = Files.readAllLines(Paths.get("input.txt"));
        Map<Point, Set<Point>> neighbors = new HashMap<>();

        for (int y = 0; y < data.size(); y++) {
            String line = data.get(y);
            for (int x = 0; x < line.length(); x++) {
                Point pos = new Point(x, y);
                Set<Point> matchingNeighbors = findNeighbors(data, pos);
                neighbors.put(pos, matchingNeighbors);
            }
        }

        int p2 = 0;
        Set<Point> seen = new HashSet<>();
        List<Set<Point>> regions = new ArrayList<>();

        for (Map.Entry<Point, Set<Point>> entry : neighbors.entrySet()) {
            Point pos = entry.getKey();
            if (seen.contains(pos)) {
                continue;
            }
            Set<Point> region = dfs(new HashSet<>(), neighbors, pos);
            int area = region.size();
            int perimeter = region.stream().mapToInt(p -> 4 - neighbors.get(p).size()).sum();
            p2 += area * (perimeter - countCommonSides(region));
            seen.addAll(region);
            regions.add(region);
        }

        System.out.println(p2);
    }

    private static boolean inBounds(List<String> map, Point pos) {
        int x = pos.x;
        int y = pos.y;
        return 0 <= x && x < map.get(0).length() && 0 <= y && y < map.size();
    }

    private static Set<Point> findNeighbors(List<String> map, Point pos) {
        int x = pos.x;
        int y = pos.y;
        Set<Point> neighbors = new HashSet<>();
        for (Point neighbor : Arrays.asList(
                new Point(x + 1, y), new Point(x - 1, y),
                new Point(x, y + 1), new Point(x, y - 1)
        )) {
            if (!inBounds(map, neighbor)) {
                continue;
            }
            if (map.get(neighbor.y).charAt(neighbor.x) == map.get(y).charAt(x)) {
                neighbors.add(neighbor);
            }
        }
        return neighbors;
    }

    private static Set<Point> dfs(Set<Point> visited, Map<Point, Set<Point>> neighbors, Point pos) {
        if (!visited.contains(pos)) {
            visited.add(pos);
            for (Point neighbor : neighbors.get(pos)) {
                dfs(visited, neighbors, neighbor);
            }
        }
        return visited;
    }

    private static int countCommonSides(Set<Point> region) {
        int ct = 0;
        for (Point p : region) {
            int x = p.x;
            int y = p.y;
            if (region.contains(new Point(x - 1, y))) {
                for (int y2 : Arrays.asList(y - 1, y + 1)) {
                    if (!region.contains(new Point(x, y2)) && !region.contains(new Point(x - 1, y2))) {
                        ct++;
                    }
                }
            }
            if (region.contains(new Point(x, y - 1))) {
                for (int x2 : Arrays.asList(x - 1, x + 1)) {
                    if (!region.contains(new Point(x2, y)) && !region.contains(new Point(x2, y - 1))) {
                        ct++;
                    }
                }
            }
        }
        return ct;
    }

    private static class Point {
        int x;
        int y;

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
}