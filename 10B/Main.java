import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        String filename = "input.txt";
        
        List<String> data = readFile(filename);
        
        Map<Complex, Integer> terrain = new HashMap<>();
        for (int r = 0; r < data.size(); r++) {
            for (int c = 0; c < data.get(r).length(); c++) {
                char v = data.get(r).charAt(c);
                if (v != '.') {
                    terrain.put(new Complex(r, c), v - '0');
                }
            }
        }

        List<Complex> tHeads = new ArrayList<>();
        for (Map.Entry<Complex, Integer> entry : terrain.entrySet()) {
            if (entry.getValue() == 0) {
                tHeads.add(entry.getKey());
            }
        }

        List<Integer> scores1 = new ArrayList<>();
        for (Complex tHead : tHeads) {
            scores1.add(explore(new HashMap<>(terrain), tHead, true));
        }

        List<Integer> scores2 = new ArrayList<>();
        for (Complex tHead : tHeads) {
            scores2.add(explore(terrain, tHead, false));
        }

        System.out.println(scores2.stream().mapToInt(Integer::intValue).sum());
    }

    private static List<String> readFile(String filename) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        List<String> lines = new ArrayList<>();
        String line;
        while ((line = br.readLine()) != null) {
            lines.add(line);
        }
        return lines;
    }

    private static int explore(Map<Complex, Integer> terrain, Complex pos, boolean noRepeats) {
        int height = terrain.get(pos);
        if (noRepeats) {
            terrain.remove(pos);
        }
        if (height == 9) {
            return 1;
        } else {
            int trails = 0;
            Complex[] directions = { new Complex(0, -1), new Complex(0, 1), new Complex(-1, 0), new Complex(1, 0) };
            for (Complex direction : directions) {
                Complex newPos = pos.add(direction);
                if (terrain.containsKey(newPos) && terrain.get(newPos) == height + 1) {
                    trails += explore(terrain, newPos, noRepeats);
                }
            }
            return trails;
        }
    }

    static class Complex {
        int r, c;

        Complex(int r, int c) {
            this.r = r;
            this.c = c;
        }

        Complex add(Complex other) {
            return new Complex(this.r + other.r, this.c + other.c);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Complex complex = (Complex) obj;
            return r == complex.r && c == complex.c;
        }

        @Override
        public int hashCode() {
            return Objects.hash(r, c);
        }
    }
}
