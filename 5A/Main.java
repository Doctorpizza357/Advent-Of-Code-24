import java.io.File;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // Read input
        Scanner scanner = new Scanner(new File("input.txt"));
        List<String> rules = new ArrayList<>();
        List<List<Integer>> updates = new ArrayList<>();

        boolean readingRules = true;

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) {
                readingRules = false;
                continue;
            }

            if (readingRules) {
                rules.add(line);
            } else {
                List<Integer> update = new ArrayList<>();
                for (String s : line.split(",")) {
                    update.add(Integer.parseInt(s.trim()));
                }
                updates.add(update);
            }
        }

        Map<Integer, Set<Integer>> graph = new HashMap<>();
        for (String rule : rules) {
            String[] parts = rule.split("\\|");
            int x = Integer.parseInt(parts[0].trim());
            int y = Integer.parseInt(parts[1].trim());

            graph.putIfAbsent(x, new HashSet<>());
            graph.get(x).add(y);
        }

        int sumOfMiddlePages = 0;

        for (List<Integer> update : updates) {
            Map<Integer, Integer> positionMap = new HashMap<>();
            for (int i = 0; i < update.size(); i++) {
                positionMap.put(update.get(i), i);
            }

            boolean isValid = true;
            for (int i = 0; i < update.size() && isValid; i++) {
                int page = update.get(i);
                if (graph.containsKey(page)) {
                    for (int mustComeAfter : graph.get(page)) {
                        if (positionMap.containsKey(mustComeAfter) && positionMap.get(mustComeAfter) < i) {
                            isValid = false;
                            break;
                        }
                    }
                }
            }

            if (isValid) {
                int middleIndex = update.size() / 2;
                sumOfMiddlePages += update.get(middleIndex);
            }
        }

        System.out.println(sumOfMiddlePages);
    }
}
