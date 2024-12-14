import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        List<Machine> machines = readInput("input.txt");
        long totalTokens = 0;

        for (Machine machine : machines) {
            totalTokens += calculateTokens(machine);
        }
        
        System.out.println(totalTokens);
    }

    private static List<Machine> readInput(String filename) throws IOException {
        List<Machine> machines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("Button A:")) {
                    int ax = Integer.parseInt(line.split("X\\+")[1].split(",")[0].trim());
                    int ay = Integer.parseInt(line.split("Y\\+")[1].trim());
                    line = br.readLine();
                    int bx = Integer.parseInt(line.split("X\\+")[1].split(",")[0].trim());
                    int by = Integer.parseInt(line.split("Y\\+")[1].trim());
                    line = br.readLine();
                    int px = Integer.parseInt(line.split("X=")[1].split(",")[0].trim());
                    int py = Integer.parseInt(line.split("Y=")[1].trim());
                    machines.add(new Machine(ax, ay, bx, by, px, py));
                }
            }
        }
        return machines;
    }

    private static long calculateTokens(Machine m) {
        // Using the mathematical formula:
        // ax*a + bx*b = px
        // ay*a + by*b = py
        // Solve for a and b using cross multiplication
        
        long numeratorB = (m.px * m.ay - m.py * m.ax);
        long denominatorB = (m.ay * m.bx - m.by * m.ax);
        
        if (denominatorB == 0) return 0;
        
        long b = numeratorB / denominatorB;
        
        long numeratorA = (m.px * m.by - m.py * m.bx);
        long denominatorA = (m.by * m.ax - m.bx * m.ay);
        
        if (denominatorA == 0) return 0;
        
        long a = numeratorA / denominatorA;
        
        // Verify solution
        if (m.ax * a + m.bx * b == m.px && m.ay * a + m.by * b == m.py && a >= 0 && b >= 0) {
            return 3 * a + b;
        }
        return 0;
    }
}

class Machine {
    int ax, ay, bx, by, px, py;

    Machine(int ax, int ay, int bx, int by, int px, int py) {
        this.ax = ax;
        this.ay = ay;
        this.bx = bx;
        this.by = by;
        this.px = px;
        this.py = py;
    }
}
