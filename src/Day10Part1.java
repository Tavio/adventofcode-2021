import java.net.URL;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Collectors;

public class Day10Part1 {

    public static void main(String[] args) throws Exception {
        List<String> lines = parseInput();
        Map<Character, Integer> scores = getScores();
        int totalScore = 0;
        for (String line : lines) {
            Character corruptedCharacter = findCorruptedCharacter(line);
            if (corruptedCharacter != null) {
                totalScore += scores.get(corruptedCharacter);
            }
        }
        System.out.println(totalScore);
    }

    private static Character findCorruptedCharacter(String line) {
        Stack<Character> stack = new Stack<Character>();
        for (int i = 0; i < line.length(); i++) {
            Character c = line.charAt(i);
            Character prev;
            switch (c) {
                case '(':
                case '[':
                case '{':
                case '<':
                    stack.push(c);
                    break;
                case ')':
                    if (stack.isEmpty() || (prev = stack.pop()) != '(') {
                        return c;
                    }
                    break;
                case ']':
                    if (stack.isEmpty() || (prev = stack.pop()) != '[') {
                        return c;
                    }
                    break;
                case '}':
                    if (stack.isEmpty() || (prev = stack.pop()) != '{') {
                        return c;
                    }
                    break;
                case '>':
                    if (stack.isEmpty() || (prev = stack.pop()) != '<') {
                        return c;
                    }
                    break;

            }
        }
        return null;
    }

    private static Map<Character, Integer> getScores() {
        Map<Character, Integer> scores = new HashMap<>();
        scores.put(')', 3);
        scores.put(']', 57);
        scores.put('}', 1197);
        scores.put('>', 25137);
        return scores;
    }

    private static List<String> parseInput() throws Exception {
        URL url = Day10Part1.class.getResource("day10input.txt");
        return Files.lines(Paths.get(URLDecoder.decode(url.getPath(), "UTF-8"))).collect(Collectors.toList());
    }
}
