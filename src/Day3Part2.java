import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.ListIterator;

public class Day3Part2 {

    private static int getMostCommonAt(ArrayList<int[]> candidates, int position) {
        int ones = 0;
        for (int[] candidate : candidates) {
            if (candidate[position] == 1) {
                ones++;
            }
        }
        if (ones >= candidates.size() - ones) {
            return 1;
        }
        return 0;
    }

    private static int getLeastCommonAt(ArrayList<int[]> candidates, int position) {
        int ones = 0;
        for (int[] candidate : candidates) {
            if (candidate[position] == 1) {
                ones++;
            }
        }
        if (ones < candidates.size() - ones) {
            return 1;
        }
        return 0;
    }

    public static void main(String[] args) throws Exception {
        URL url = Day3Part1.class.getResource("day3part1input.txt");
        File file = new File(URLDecoder.decode(url.getPath(), "UTF-8"));
        ArrayList<int[]> diagnostic = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] digits = line.split("");
                int[] digitsInt = new int[digits.length];
                for (int i = 0; i < digits.length; i++) {
                    digitsInt[i] = Integer.parseInt(digits[i]);
                }
                diagnostic.add(digitsInt);
            }
        }

        ArrayList<int[]> oxygenGeneratorCandidates = new ArrayList<>(diagnostic);
        ArrayList<int[]> co2ScrubberCandidates = new ArrayList<>(diagnostic);

        for (int bit = 0; bit < diagnostic.get(0).length; bit++) {
            if (oxygenGeneratorCandidates.size() == 1 && co2ScrubberCandidates.size() == 1) {
                break;
            }

            if (oxygenGeneratorCandidates.size() > 1) {
                int mostCommon = getMostCommonAt(oxygenGeneratorCandidates, bit);
                ListIterator<int[]> iter = oxygenGeneratorCandidates.listIterator();
                while (iter.hasNext()) {
                    if (iter.next()[bit] != mostCommon) {
                        iter.remove();
                    }
                }
            }

            if (co2ScrubberCandidates.size() > 1) {
                int leastCommon = getLeastCommonAt(co2ScrubberCandidates, bit);
                ListIterator<int[]> iter = co2ScrubberCandidates.listIterator();
                while (iter.hasNext()) {
                    if (iter.next()[bit] != leastCommon) {
                        iter.remove();
                    }
                }
            }
        }

        int oxygenGenerator = 0;
        int co2Scrubber = 0;

        for (int i = 0; i < diagnostic.get(0).length; i++) {
            oxygenGenerator += oxygenGeneratorCandidates.get(0)[i] << (diagnostic.get(0).length - i - 1);
            co2Scrubber += co2ScrubberCandidates.get(0)[i] << (diagnostic.get(0).length - i - 1);
        }

        System.out.println(oxygenGenerator * co2Scrubber);

    }
}
