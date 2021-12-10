import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Arrays;

public class Day7Part2 {
    public static void main(String[] args) throws Exception {
        int[] positions = parseInput();
        int maxPosition = Arrays.stream(positions).max().getAsInt();
        int[] possiblePositions = new int[maxPosition];

        for (int i = 0; i < possiblePositions.length; i++) {
            int totalFuel = 0;
            for (int p = 0; p < positions.length; p++) {
                int distance = Math.abs(positions[p] - i);
                totalFuel += (distance * (distance + 1)) / 2;
            }
            possiblePositions[i] = totalFuel;
        }
        System.out.println(Arrays.stream(possiblePositions).min().getAsInt());
    }

    private static int[] parseInput() throws Exception {
        URL url = Day7Part1.class.getResource("day7part1input.txt");
        File file = new File(URLDecoder.decode(url.getPath(), "UTF-8"));
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            return Arrays.stream(br.readLine().split(",")).mapToInt(n -> Integer.parseInt(n)).toArray();
        }
    }
}
