import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.net.URLDecoder;

public class Day1Part1 {
    public static void main(String[] args) throws Exception {
        URL url = Day1Part1.class.getResource("day1part1input.txt");
        File file = new File(URLDecoder.decode(url.getPath(), "UTF-8"));

        Integer previousMeasurement = null;
        Integer totalIncreases = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                Integer nextMeasurement = Integer.parseInt(line);
                if (previousMeasurement != null && nextMeasurement > previousMeasurement) {
                    totalIncreases++;
                }
                previousMeasurement = nextMeasurement;
            }
        }

        System.out.println(totalIncreases);
    }
}
