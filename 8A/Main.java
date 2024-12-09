import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws IOException {
        List<String> mapData = readFile("input.txt");

        String[] mapArray = mapData.toArray(new String[0]);

        int uniqueAntinodes = countUniqueAntinodes(mapArray);
        System.out.println(uniqueAntinodes);

    }

    public static List<String> readFile(String fileName) throws FileNotFoundException {
        List<String> lines = new ArrayList<>();
        Scanner scanner = new Scanner(new File(fileName));

        while (scanner.hasNextLine()) {
            lines.add(scanner.nextLine());
        }

        scanner.close();
        return lines;
    }

    public static int countUniqueAntinodes(String[] mapData) {
        int width = mapData[0].length();
        int height = mapData.length;

        Map<Character, List<int[]>> antennas = parseMap(mapData);
        Set<String> antinodes = findAntinodes(antennas, width, height);

        return antinodes.size();
    }

    private static Map<Character, List<int[]>> parseMap(String[] mapData) {
        Map<Character, List<int[]>> antennas = new HashMap<>();

        for (int y = 0; y < mapData.length; y++) {
            for (int x = 0; x < mapData[y].length(); x++) {
                char ch = mapData[y].charAt(x);
                if (ch != '.') {
                    antennas.computeIfAbsent(ch, k -> new ArrayList<>()).add(new int[] { x, y });
                }
            }
        }

        return antennas;
    }

    private static Set<String> findAntinodes(Map<Character, List<int[]>> antennas, int width, int height) {
        Set<String> antinodes = new HashSet<>();

        for (Map.Entry<Character, List<int[]>> entry : antennas.entrySet()) {
            List<int[]> positions = entry.getValue();
            for (int i = 0; i < positions.size(); i++) {
                int[] pos1 = positions.get(i);
                for (int j = 0; j < positions.size(); j++) {
                    if (i != j) {
                        int[] pos2 = positions.get(j);
                        int dx = pos2[0] - pos1[0];
                        int dy = pos2[1] - pos1[1];

                        int[] antinode1 = { pos1[0] - dx, pos1[1] - dy };
                        int[] antinode2 = { pos2[0] + dx, pos2[1] + dy };

                        if (isWithinBounds(antinode1, width, height)) {
                            antinodes.add(antinode1[0] + "," + antinode1[1]);
                        }
                        if (isWithinBounds(antinode2, width, height)) {
                            antinodes.add(antinode2[0] + "," + antinode2[1]);
                        }
                    }
                }
            }
        }

        return antinodes;
    }

    private static boolean isWithinBounds(int[] pos, int width, int height) {
        return pos[0] >= 0 && pos[0] < width && pos[1] >= 0 && pos[1] < height;
    }
}
