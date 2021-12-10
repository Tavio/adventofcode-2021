import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;

public class Day3Part1 {
    public static void main(String[] args) throws Exception {
        URL url = Day3Part1.class.getResource("day3part1input.txt");
        File file = new File(URLDecoder.decode(url.getPath(), "UTF-8"));
        ArrayList<String[]> diagnostic = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] digits = line.split("");
                diagnostic.add(digits);
            }
        }

        int[] gammaArr = new int[diagnostic.get(0).length];
        int[] epsilonArr = new int[diagnostic.get(0).length];
        Arrays.fill(gammaArr, 0);

        for (String[] digits : diagnostic) {
            for (int i = 0; i < digits.length; i++) {
                if ("1".equals(digits[i])) {
                    gammaArr[i]++;
                }
            }
        }

        for (int i = 0; i < gammaArr.length; i++) {
            boolean oneMostCommon = gammaArr[i] > diagnostic.size() / 2;
            gammaArr[i] = oneMostCommon ? 1 : 0;
            epsilonArr[i] = oneMostCommon ? 0 : 1;
        }

        int gamma = 0;
        int epsilon = 0;

        for (int i = 0; i < gammaArr.length; i++) {
            gamma += gammaArr[i] << (gammaArr.length - i - 1);
            epsilon += epsilonArr[i] << (epsilonArr.length - i - 1);
        }

        System.out.println(gamma * epsilon);
    }
}
