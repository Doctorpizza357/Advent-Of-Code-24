import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<Long> stones = readInput("input.txt");
        for (int i = 0; i < 25; i++) {
            System.out.println(stones);
            stones = blink(stones);
        }
        System.out.println(stones.size());
    }

    private static List<Long> readInput(String filename) {
        List<Long> stones = new ArrayList<>();
        try {
            String line = new BufferedReader(new FileReader(filename)).readLine();
            for (String number : line.split(" ")) {
                stones.add(Long.parseLong(number));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stones;
    }

    private static List<Long> blink(List<Long> stones) {
        List<Long> newStones = new ArrayList<>();
        for (long stone : stones) {
            if (stone == 0) {
                newStones.add(1L);
            } else if (String.valueOf(Math.abs(stone)).length() % 2 == 0) {
                String stoneStr = String.valueOf(Math.abs(stone));
                int mid = stoneStr.length() / 2;
                newStones.add(Long.parseLong(stoneStr.substring(0, mid)));
                newStones.add(Long.parseLong(stoneStr.substring(mid)));
            } else {
                newStones.add(stone * 2024);
            }
        }
        return newStones;
    }
}