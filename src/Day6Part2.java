import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Day6Part2 {
    public static void main(String[] args) throws Exception {
        List<Integer> fishes = parseFish();

        int numDays = 256;
        long[] fishToCreate = new long[numDays + 1];
        long totalFish = (long) fishes.size();

        for (Integer fish : fishes) {
            int nextDay = fish + 1;
            while (nextDay <= numDays) {
                fishToCreate[nextDay]++;
                nextDay += 7;
            }
        }

        for (int day = 0; day <= numDays; day++) {
            if (fishToCreate[day] > 0) {
                int nextDay = day + 9;
                while (nextDay <= numDays) {
                    fishToCreate[nextDay] += fishToCreate[day];
                    nextDay += 7;
                }
            }
        }

        for (int i = 0; i < fishToCreate.length; i++) {
            totalFish += fishToCreate[i];
        }

        System.out.println(totalFish);
    }

    private static List<Integer> parseFish() throws Exception {
        URL url = Day6Part2.class.getResource("day6part1input.txt");
        File file = new File(URLDecoder.decode(url.getPath(), "UTF-8"));
        List<Integer> fishes = new LinkedList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            fishes = Arrays.stream(br.readLine().split(","))
                    .map(n -> Integer.parseInt(n)).collect(Collectors.toList());
        }
        return fishes;
    }
}
