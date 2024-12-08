import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        String infile = "input.txt";
        int count = 0;
        List<String> lines = Files.readAllLines(Paths.get(infile));

        int R = lines.size();
        int C = lines.get(0).length();
        for (int r = 0; r < R; r++) {
            for (int c = 0; c < C; c++) {
                if (r + 2 < R && c + 2 < C && lines.get(r).charAt(c) == 'M' && lines.get(r + 1).charAt(c + 1) == 'A' && lines.get(r + 2).charAt(c + 2) == 'S' && lines.get(r + 2).charAt(c) == 'M' && lines.get(r).charAt(c + 2) == 'S') {
                    count += 1;
                }
                if (r + 2 < R && c + 2 < C && lines.get(r).charAt(c) == 'M' && lines.get(r + 1).charAt(c + 1) == 'A' && lines.get(r + 2).charAt(c + 2) == 'S' && lines.get(r + 2).charAt(c) == 'S' && lines.get(r).charAt(c + 2) == 'M') {
                    count += 1;
                }
                if (r + 2 < R && c + 2 < C && lines.get(r).charAt(c) == 'S' && lines.get(r + 1).charAt(c + 1) == 'A' && lines.get(r + 2).charAt(c + 2) == 'M' && lines.get(r + 2).charAt(c) == 'M' && lines.get(r).charAt(c + 2) == 'S') {
                    count += 1;
                }
                if (r + 2 < R && c + 2 < C && lines.get(r).charAt(c) == 'S' && lines.get(r + 1).charAt(c + 1) == 'A' && lines.get(r + 2).charAt(c + 2) == 'M' && lines.get(r + 2).charAt(c) == 'S' && lines.get(r).charAt(c + 2) == 'M') {
                    count += 1;
                }
            }
        }

        System.out.println(count);
    }
}
