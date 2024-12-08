import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        int sum = 0;
        List<Integer> leftList = new ArrayList<>();
        List<Integer> rightList = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new FileReader("input.txt"));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split("\\s+");
            leftList.add(Integer.parseInt(parts[0]));
            rightList.add(Integer.parseInt(parts[1]));
        }
        reader.close();

        Collections.sort(leftList);
        Collections.sort(rightList);

        for (int i = 0; i < leftList.size(); i++) {
            // System.out.println(leftList.get(i) + " " + rightList.get(i));
            sum += Math.abs(leftList.get(i) - rightList.get(i));
        }
        System.out.println(sum);
    }
}