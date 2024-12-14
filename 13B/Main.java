import java.io.*;
import java.util.*;

public class Main {
    static final long P2_OFFSET = 10000000000000L;

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("input.txt"));
        String line;
        long p2 = 0;

        while ((line = reader.readLine()) != null) {
            if (line.trim().isEmpty()) continue;

            String aLine = line;
            String bLine = reader.readLine();
            String prizeLine = reader.readLine();

            int ax = Integer.parseInt(aLine.split("X\\+")[1].split(",")[0].trim());
            int ay = Integer.parseInt(aLine.split("Y\\+")[1].trim());
            int bx = Integer.parseInt(bLine.split("X\\+")[1].split(",")[0].trim());
            int by = Integer.parseInt(bLine.split("Y\\+")[1].trim());
            long px = Long.parseLong(prizeLine.split("X=")[1].split(",")[0].trim()) + P2_OFFSET;
            long py = Long.parseLong(prizeLine.split("Y=")[1].trim()) + P2_OFFSET;

            long denomX = (long)ay * bx - (long)by * ax;
            long denomY = (long)by * ax - (long)bx * ay;
            
            if (denomX != 0 && denomY != 0) {
                long b = (px * ay - py * ax) / denomX;
                long a = (px * by - py * bx) / denomY;
                
                if (a >= 0 && b >= 0 && 
                    ax * a + bx * b == px && 
                    ay * a + by * b == py) {
                    p2 += 3 * a + b;
                }
            }
        }

        System.out.println(p2);
    }
}
