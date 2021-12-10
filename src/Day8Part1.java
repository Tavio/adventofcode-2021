import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day8Part1 {
    static class Notes {
        public List<String> signals;
        public List<String> digits;

        public Notes(List<String> signals, List<String> digits) {
            this.signals = signals;
            this.digits = digits;
        }
    }

    public static void main(String[] args) throws Exception {
        List<Notes> notes = parseInput();
        int numSimpleDigits = 0;
        for (Notes note : notes) {
            for (String digits : note.digits) {
                if (digits.length() == 2 || digits.length() == 4 || digits.length() == 3 || digits.length() == 7) {
                    numSimpleDigits++;
                }
            }
        }
        System.out.println(numSimpleDigits);
    }

    private static List<Notes> parseInput() throws Exception {
        List<Notes> notes = new ArrayList<Notes>();
        URL url = Day7Part1.class.getResource("day8part1input.txt");
        File file = new File(URLDecoder.decode(url.getPath(), "UTF-8"));
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parsedLine = line.split("\\|");
                List<String> signals = Arrays.asList(parsedLine[0].trim().split(" ")).stream()
                        .map(signal -> signal.trim()).collect(Collectors.toList());
                List<String> digits = Arrays.asList(parsedLine[1].trim().split(" ")).stream()
                        .map(signal -> signal.trim()).collect(Collectors.toList());
                notes.add(new Notes(signals, digits));
            }
            ;
        }
        return notes;
    }
}
