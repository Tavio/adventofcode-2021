import java.net.URL;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day9Part1 {
    public static void main(String[] args) throws Exception {
        Integer[][] heightMap = parseInput();
        int sumRiskLevels = 0;
        for (int i = 0; i < heightMap.length; i++) {
            for (int j = 0; j < heightMap[0].length; j++) {
                if (i - 1 >= 0 && heightMap[i - 1][j] <= heightMap[i][j] ||
                        i + 1 < heightMap.length && heightMap[i + 1][j] <= heightMap[i][j] ||
                        j - 1 >= 0 && heightMap[i][j - 1] <= heightMap[i][j] ||
                        j + 1 < heightMap[i].length && heightMap[i][j + 1] <= heightMap[i][j]) {
                    continue;
                }
                sumRiskLevels += 1 + heightMap[i][j];
            }
        }
        System.out.println(sumRiskLevels);
    }

    private static Integer[][] parseInput() throws Exception {

        List<List<Integer>> heightMap = new ArrayList<>();
        URL url = Day7Part1.class.getResource("day9part1input.txt");
        Files.lines(Paths.get(URLDecoder.decode(url.getPath(), "UTF-8")))
                .forEach(l -> {
                    heightMap.add(Arrays.asList(l.split("")).stream().map(s -> Integer.parseInt(s))
                            .collect(Collectors.toList()));
                });

        return heightMap.stream().map(u -> u.toArray(new Integer[0])).toArray(Integer[][]::new);
    }
}
