import java.net.URL;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Day12 {

    static class Cave {
        public Set<Cave> neighbours;
        public String name;

        public Cave(String name) {
            this.name = name;
            this.neighbours = new HashSet<>();
        }

        public boolean isBig() {
            return Character.isUpperCase(name.charAt(0));
        }

        public boolean isSmall() {
            return !isBig();
        }

        public boolean isEnd() {
            return "end".equals(this.name);
        }

        public boolean isStart() {
            return "start".equals(this.name);
        }

        @Override
        public String toString() {
            return this.name;
        }

        public String getName() {
            return name;
        }
    }

    public static void main(String[] args) throws Exception {
        Map<String, Cave> caveDict = parseInput();
        Cave start = caveDict.get("start");
        // Part 1:
        List<List<Cave>> paths = findPaths(new ArrayList<>(Arrays.asList(start)), start);
        System.out.println(paths.size());
        // Part 2:
        List<List<Cave>> paths2 = findPathsPart2(new ArrayList<>(Arrays.asList(start)), start, false, new HashMap<>());
        System.out.println(paths2.size());
    }

    private static List<List<Cave>> findPaths(List<Cave> currentPath, Cave currentCave) {
        List<List<Cave>> result = new ArrayList<>();
        if (currentCave.isEnd()) {
            result.add(new ArrayList<>(currentPath));
        }

        for (Cave next : currentCave.neighbours) {
            if (next.isBig() || (!currentPath.contains(next))) {
                List<Cave> nextPath = new ArrayList<>(currentPath);
                nextPath.add(next);
                result.addAll(findPaths(nextPath, next));
            }
        }

        return result;
    }

    private static List<List<Cave>> findPathsPart2(List<Cave> currentPath, Cave currentCave, boolean hasRepeated,
            Map<String, List<List<Cave>>> cache) {
        // cache key = unique caves in current path + current cave + has repeated
        String cacheKey = currentPath.stream()
                .map(Cave::getName).collect(Collectors.toSet()).stream()
                .collect(Collectors.joining("")) + currentCave.name + String.valueOf(hasRepeated);

        if (cache.containsKey(cacheKey)) {
            return cache.get(cacheKey);
        }

        List<List<Cave>> result = new ArrayList<>();
        if (currentCave.isEnd()) {
            result.add(new ArrayList<>(currentPath));
        }

        for (Cave next : currentCave.neighbours) {
            if (next.isBig() || (!currentPath.contains(next))) {
                List<Cave> nextPath = new ArrayList<>(currentPath);
                nextPath.add(next);
                result.addAll(findPathsPart2(nextPath, next, hasRepeated, cache));
            } else if (next.isSmall() && currentPath.contains(next) && !hasRepeated && !next.isStart()
                    && !next.isEnd()) {
                List<Cave> nextPath = new ArrayList<>(currentPath);
                nextPath.add(next);
                result.addAll(findPathsPart2(nextPath, next, true, cache));

            }
        }

        cache.put(cacheKey, result);

        return result;
    }

    private static Map<String, Cave> parseInput() throws Exception {
        Map<String, Cave> caveDict = new HashMap<>();
        URL url = Day12.class.getResource("day12input.txt");
        List<String> lines = Files.lines(Paths.get(URLDecoder.decode(url.getPath(), "UTF-8")))
                .collect(Collectors.toList());
        for (String line : lines) {
            String[] splitLine = line.split("-");
            Cave from = caveDict.getOrDefault(splitLine[0], new Cave(splitLine[0]));
            Cave to = caveDict.getOrDefault(splitLine[1], new Cave(splitLine[1]));
            from.neighbours.add(to);
            to.neighbours.add(from);
            caveDict.put(from.name, from);
            caveDict.put(to.name, to);
        }
        return caveDict;
    }
}
