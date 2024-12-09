import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        List<String> mapData = readFile("input.txt");
        String[] mapArray = mapData.toArray(new String[0]);
        
        int uniqueAntinodesPart2 = countUniqueAntinodesPart2(mapArray);
        System.out.println(uniqueAntinodesPart2);
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

    public static int countUniqueAntinodesPart2(String[] mapData) {
        int width = mapData[0].length();
        int height = mapData.length;

        Map<Character, List<int[]>> antennas = parseMap(mapData);
        Set<String> antinodes = findAntinodesPart2(antennas, width, height);

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

    private static Set<String> findAntinodesPart2(Map<Character, List<int[]>> antennas, int width, int height) {
        Set<String> antinodes = new HashSet<>();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                for (Map.Entry<Character, List<int[]>> entry : antennas.entrySet()) {
                    List<int[]> positions = entry.getValue();
                    for (int[] pos1 : positions) {
                        for (int[] pos2 : positions) {
                            if (!Arrays.equals(pos1, pos2)) {
                                int dx1 = x - pos1[0];
                                int dy1 = y - pos1[1];
                                int dx2 = x - pos2[0];
                                int dy2 = y - pos2[1];

                                if (dx1 * dy2 == dy1 * dx2) {
                                    antinodes.add(x + "," + y);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }

        return antinodes;
    }
}
