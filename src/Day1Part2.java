import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

public class Day1Part2 {
    public static void main(String[] args) throws Exception {
        URL url = Day1Part1.class.getResource("day1part1input.txt");
        File file = new File(URLDecoder.decode(url.getPath(), "UTF-8"));
        List<Integer> measurements = new ArrayList<Integer>();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                measurements.add(Integer.parseInt(line));
            }
        }

        Integer[] measurementsArr = new Integer[measurements.size()];
        measurementsArr = measurements.toArray(measurementsArr);

        Integer previousWindowSum = null;
        Integer totalIncreases = 0;

        for (int i = 0; i <= measurementsArr.length - 3; i++) {
            Integer nextWindowSum = measurementsArr[i] + measurementsArr[i + 1] + measurementsArr[i + 2];
            if (previousWindowSum != null && nextWindowSum > previousWindowSum) {
                totalIncreases++;
            }
            previousWindowSum = nextWindowSum;
        }

        System.out.println(totalIncreases);
    }
}
