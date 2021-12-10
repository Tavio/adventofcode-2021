import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Day8Part2 {
    static class Notes {
        public List<String> signals;
        public List<String> digits;

        public Notes(List<String> signals, List<String> digits) {
            this.signals = signals;
            this.digits = digits;
        }
    }

    public static void main(String[] args) throws Exception {
        Map<Set<Character>, Integer> validDigitsMap = buildValidDigitsMap();
        List<Notes> notes = parseInput();
        int totalSum = 0;
        for (Notes note : notes) {
            Map<Character, Set<Character>> possibleMappings = createPossibleMappings(note);
            int sum = calculateSum(validDigitsMap, note, possibleMappings);
            System.out.println("Sum so far: " + sum);
            totalSum += sum;
        }
        System.out.println(totalSum);
    }

    private static int calculateSum(Map<Set<Character>, Integer> validDigitsMap, Notes note,
            Map<Character, Set<Character>> possibleMappings) {
        for (Character c1 : possibleMappings.get('a')) {
            Set<Character> taken = new HashSet<>(Arrays.asList(c1));
            for (Character c2 : possibleMappings.get('b')) {
                if (taken.contains(c2)) {
                    continue;
                }
                taken.add(c2);
                for (Character c3 : possibleMappings.get('c')) {
                    if (taken.contains(c3)) {
                        continue;
                    }
                    taken.add(c3);
                    for (Character c4 : possibleMappings.get('d')) {
                        if (taken.contains(c4)) {
                            continue;
                        }
                        taken.add(c4);
                        for (Character c5 : possibleMappings.get('e')) {
                            if (taken.contains(c5)) {
                                continue;
                            }
                            taken.add(c5);
                            for (Character c6 : possibleMappings.get('f')) {
                                if (taken.contains(c6)) {
                                    continue;
                                }
                                taken.add(c6);
                                for (Character c7 : possibleMappings.get('g')) {
                                    if (taken.contains(c7)) {
                                        continue;
                                    }
                                    taken.add(c7);
                                    Map<Character, Character> translation = Map.of(
                                            'a', c1,
                                            'b', c2,
                                            'c', c3,
                                            'd', c4,
                                            'e', c5,
                                            'f', c6,
                                            'g', c7);
                                    String sum = "";
                                    boolean valid = true;
                                    for (String digit : note.digits) {
                                        Set<Character> translatedDigit = digit.chars()
                                                .mapToObj(c -> translation.get((char) c))
                                                .collect(Collectors.toSet());
                                        if (!validDigitsMap.containsKey(translatedDigit)) {
                                            valid = false;
                                            break;
                                        }
                                        sum += validDigitsMap.get(translatedDigit);
                                    }
                                    if (valid) {
                                        return Integer.parseInt(sum);
                                    }
                                    taken.remove(c7);
                                }
                                taken.remove(c6);
                            }
                            taken.remove(c5);
                        }
                        taken.remove(c4);
                    }
                    taken.remove(c3);
                }
                taken.remove(c2);
            }
            taken.remove(c1);
        }
        throw new RuntimeException("!!");
    }

    private static Map<Character, Set<Character>> createPossibleMappings(Notes note) {
        Map<Character, Set<Character>> possibleMappings = new HashMap<>();
        Set<Character> len2Signal = note.signals.stream().filter(s -> s.length() == 2)
                .flatMap(s -> Arrays.asList(s.split("")).stream()).map(s -> s.charAt(0))
                .collect(Collectors.toSet());
        Set<Character> len3Signal = note.signals.stream().filter(s -> s.length() == 3)
                .flatMap(s -> Arrays.asList(s.split("")).stream()).map(s -> s.charAt(0))
                .collect(Collectors.toSet());
        Set<Character> len4Signal = note.signals.stream().filter(s -> s.length() == 4)
                .flatMap(s -> Arrays.asList(s.split("")).stream()).map(s -> s.charAt(0))
                .collect(Collectors.toSet());
        Set<Character> len7Signal = note.signals.stream().filter(s -> s.length() == 7)
                .flatMap(s -> Arrays.asList(s.split("")).stream()).map(s -> s.charAt(0))
                .collect(Collectors.toSet());
        Set<Character> len3SignalSubbed = new HashSet(len3Signal);
        Set<Character> len4SignalSubbed = new HashSet(len4Signal);
        Set<Character> len7SignalSubbed = new HashSet(len7Signal);
        len3SignalSubbed.removeAll(len2Signal);
        len4SignalSubbed.removeAll(len3Signal);
        len7SignalSubbed.removeAll(len4Signal);
        len7SignalSubbed.removeAll(len3SignalSubbed);
        for (Character c : len2Signal) {
            possibleMappings.put(c, new HashSet<>(Arrays.asList('c', 'f')));
        }
        for (Character c : len3SignalSubbed) {
            // should contain only one element
            possibleMappings.put(c, new HashSet<>(Arrays.asList('a')));
        }
        for (Character c : len4SignalSubbed) {
            possibleMappings.put(c, new HashSet<>(Arrays.asList('b', 'd')));
        }
        for (Character c : len7SignalSubbed) {
            possibleMappings.put(c, new HashSet<>(Arrays.asList('e', 'g')));
        }
        return possibleMappings;
    }

    private static Map<Set<Character>, Integer> buildValidDigitsMap() {
        Map<Set<Character>, Integer> validDigitsMap = new HashMap<>();
        validDigitsMap.put(new HashSet<>(Arrays.asList('a', 'b', 'c', 'e', 'f', 'g')), 0);
        validDigitsMap.put(new HashSet<>(Arrays.asList('c', 'f')), 1);
        validDigitsMap.put(new HashSet<>(Arrays.asList('a', 'c', 'd', 'e', 'g')), 2);
        validDigitsMap.put(new HashSet<>(Arrays.asList('a', 'c', 'd', 'f', 'g')), 3);
        validDigitsMap.put(new HashSet<>(Arrays.asList('b', 'c', 'd', 'f')), 4);
        validDigitsMap.put(new HashSet<>(Arrays.asList('a', 'b', 'd', 'f', 'g')), 5);
        validDigitsMap.put(new HashSet<>(Arrays.asList('a', 'b', 'd', 'e', 'f', 'g')), 6);
        validDigitsMap.put(new HashSet<>(Arrays.asList('a', 'c', 'f')), 7);
        validDigitsMap.put(new HashSet<>(Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g')), 8);
        validDigitsMap.put(new HashSet<>(Arrays.asList('a', 'b', 'c', 'd', 'f', 'g')), 9);
        return validDigitsMap;
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
