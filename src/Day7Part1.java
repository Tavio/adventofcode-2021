import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Arrays;

public class Day7Part1 {
    public static void main(String[] args) throws Exception {
        int[] positions = parseInput();
        Arrays.sort(positions);
        int bestPosition = positions[positions.length / 2];
        int totalFuel = 0;
        for (int i = 0; i < positions.length; i++) {
            totalFuel += Math.abs(positions[i] - bestPosition);
        }
        System.out.println(totalFuel);
    }

    private static int[] parseInput() throws Exception {
        URL url = Day7Part1.class.getResource("day7part1input.txt");
        File file = new File(URLDecoder.decode(url.getPath(), "UTF-8"));
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            return Arrays.stream(br.readLine().split(",")).mapToInt(n -> Integer.parseInt(n)).toArray();
        }
    }
}
