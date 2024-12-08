import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        int SimilarityScore = 0;
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

        for (int num : leftList) {
            int frequency = Collections.frequency(rightList, num);
            SimilarityScore += num * frequency;
        }
        System.out.println(SimilarityScore);
    }
}