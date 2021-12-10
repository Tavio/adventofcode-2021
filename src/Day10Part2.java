import java.net.URL;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Collectors;

public class Day10Part2 {

    public static void main(String[] args) throws Exception {
        List<String> lines = parseInput();
        Map<Character, Integer> scores = getScores();
        List<Long> lineScores = new ArrayList<>();
        for (String line : lines) {
            try {
                List<Character> complements = getLineComplement(line);
                lineScores.add(getComplementsScore(complements, scores));
            } catch (Day10Part2.LineParsingException e) {
            }
        }
        Collections.sort(lineScores);
        System.out.println(lineScores.get(lineScores.size() / 2));
    }

    private static Long getComplementsScore(List<Character> complements, Map<Character, Integer> scores) {
        Long totalScore = 0l;
        for (Character c : complements) {
            totalScore *= 5;
            totalScore += scores.get(c);
        }
        return totalScore;
    }

    private static List<Character> getLineComplement(String line) throws Day10Part2.LineParsingException {
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
                        throw new LineParsingException("Unexpected )");
                    }
                    break;
                case ']':
                    if (stack.isEmpty() || (prev = stack.pop()) != '[') {
                        throw new LineParsingException("Unexpected ]");
                    }
                    break;
                case '}':
                    if (stack.isEmpty() || (prev = stack.pop()) != '{') {
                        throw new LineParsingException("Unexpected }");
                    }
                    break;
                case '>':
                    if (stack.isEmpty() || (prev = stack.pop()) != '<') {
                        throw new LineParsingException("Unexpected >");
                    }
                    break;

            }
        }
        return getStackComplements(stack);
    }

    private static List<Character> getStackComplements(Stack<Character> stack) {
        List<Character> complements = new ArrayList<>();
        Collections.reverse(stack);
        for (Character c : stack) {
            switch (c) {
                case '(':
                    complements.add(')');
                    break;
                case '[':
                    complements.add(']');
                    break;
                case '{':
                    complements.add('}');
                    break;
                case '<':
                    complements.add('>');
                    break;
            }
        }
        return complements;
    }

    private static Map<Character, Integer> getScores() {
        Map<Character, Integer> scores = new HashMap<>();
        scores.put(')', 1);
        scores.put(']', 2);
        scores.put('}', 3);
        scores.put('>', 4);
        return scores;
    }

    private static List<String> parseInput() throws Exception {
        URL url = Day10Part2.class.getResource("day10input.txt");
        return Files.lines(Paths.get(URLDecoder.decode(url.getPath(), "UTF-8"))).collect(Collectors.toList());
    }

    static class LineParsingException extends Exception {
        public LineParsingException(String errorMessage) {
            super(errorMessage);
        }
    }
}
