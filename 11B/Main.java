import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Map<Long, Long> stones = readInput("input.txt");
        for (int i = 0; i < 75; i++) {
            System.out.println(stones);
            blink(stones);
        }
        long totalStones = stones.values().stream().mapToLong(Long::longValue).sum();
        System.out.println(totalStones);
    }

    private static Map<Long, Long> readInput(String filename) {
        Map<Long, Long> stones = new HashMap<>();
        try {
            String line = new BufferedReader(new FileReader(filename)).readLine();
            for (String number : line.split(" ")) {
                long num = Long.parseLong(number);
                stones.put(num, stones.getOrDefault(num, 0L) + 1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stones;
    }

    private static void blink(Map<Long, Long> stones) {
        Map<Long, Long> newStones = new HashMap<>();
        for (Map.Entry<Long, Long> entry : stones.entrySet()) {
            long stone = entry.getKey();
            long count = entry.getValue();

            if (stone == 0) {
                newStones.put(1L, newStones.getOrDefault(1L, 0L) + count);
            } else {
                long absStone = Math.abs(stone);
                int length = (int) Math.log10(absStone) + 1;
                if (length % 2 == 0) {
                    long divisor = (long) Math.pow(10, length / 2);
                    long leftStone = absStone / divisor;
                    long rightStone = absStone % divisor;
                    newStones.put(leftStone, newStones.getOrDefault(leftStone, 0L) + count);
                    newStones.put(rightStone, newStones.getOrDefault(rightStone, 0L) + count);
                } else {
                    long newStone = stone * 2024;
                    newStones.put(newStone, newStones.getOrDefault(newStone, 0L) + count);
                }
            }
        }
        stones.clear();
        stones.putAll(newStones);
    }
}
