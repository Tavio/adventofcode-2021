import java.net.URL;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day11Part2 {

    static class Octopus {
        public int energy;
        public boolean hasFlashed;
        public int x;
        public int y;

        public Octopus(int energy, boolean hasFlashed) {
            this.energy = energy;
            this.hasFlashed = hasFlashed;
        }

        @Override
        public String toString() {
            return String.valueOf(energy);
        }
    }

    public static void main(String[] args) throws Exception {
        Octopus[][] octopuses = parseInput();
        octopuses = padOctopuses(octopuses);
        for (int i = 1; i <= 1000; i++) {
            nextStep(octopuses);
            if (allFlashed(octopuses)) {
                System.out.println(i);
                return;
            }
        }
    }

    private static boolean allFlashed(Octopus[][] octopuses) {
        for (int i = 1; i < octopuses.length - 1; i++) {
            for (int j = 1; j < octopuses[0].length - 1; j++) {
                if (octopuses[i][j].energy != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    private static int nextStep(Octopus[][] octopuses) {
        int numFlashes = 0;
        for (int i = 1; i < octopuses.length - 1; i++) {
            for (int j = 1; j < octopuses[0].length - 1; j++) {
                octopuses[i][j].hasFlashed = false;
                octopuses[i][j].energy++;
            }
        }

        boolean needToFlash = true;
        while (needToFlash) {
            needToFlash = false;
            for (int i = 1; i < octopuses.length - 1; i++) {
                for (int j = 1; j < octopuses[0].length - 1; j++) {
                    Octopus octopus = octopuses[i][j];
                    if (octopus.energy > 9 && !octopus.hasFlashed) {
                        flashOctopus(octopus, octopuses);
                        needToFlash = true;
                        numFlashes++;
                    }
                }
            }
        }
        return numFlashes;
    }

    private static void flashOctopus(Octopus octopus, Octopus[][] octopuses) {
        octopuses[octopus.y][octopus.x].hasFlashed = true;
        octopuses[octopus.y][octopus.x].energy = 0;
        increaseNeighbours(octopus, octopuses);
    }

    private static void increaseNeighbours(Octopus octopus, Octopus[][] octopuses) {
        Octopus neighbour;
        int i = octopus.y;
        int j = octopus.x;
        if ((neighbour = octopuses[i - 1][j]) != null && !neighbour.hasFlashed) {
            neighbour.energy++;
        }
        if ((neighbour = octopuses[i - 1][j + 1]) != null && !neighbour.hasFlashed) {
            neighbour.energy++;
        }
        if ((neighbour = octopuses[i][j + 1]) != null && !neighbour.hasFlashed) {
            neighbour.energy++;
        }
        if ((neighbour = octopuses[i + 1][j + 1]) != null && !neighbour.hasFlashed) {
            neighbour.energy++;
        }
        if ((neighbour = octopuses[i + 1][j]) != null && !neighbour.hasFlashed) {
            neighbour.energy++;
        }
        if ((neighbour = octopuses[i + 1][j - 1]) != null && !neighbour.hasFlashed) {
            neighbour.energy++;
        }
        if ((neighbour = octopuses[i][j - 1]) != null && !neighbour.hasFlashed) {
            neighbour.energy++;
        }
        if ((neighbour = octopuses[i - 1][j - 1]) != null && !neighbour.hasFlashed) {
            neighbour.energy++;
        }
    }

    private static Octopus[][] padOctopuses(Octopus[][] octopuses) {
        Octopus[][] paddedOctopuses = new Octopus[octopuses.length + 2][octopuses[0].length + 2];
        for (int i = 0; i < paddedOctopuses.length; i++) {
            paddedOctopuses[i][0] = null;
            paddedOctopuses[i][paddedOctopuses[0].length - 1] = null;
        }
        for (int j = 0; j < paddedOctopuses[0].length; j++) {
            paddedOctopuses[0][j] = null;
            paddedOctopuses[paddedOctopuses.length - 1][j] = null;
        }
        for (int i = 1; i < paddedOctopuses.length - 1; i++) {
            for (int j = 1; j < paddedOctopuses[0].length - 1; j++) {
                paddedOctopuses[i][j] = octopuses[i - 1][j - 1];
                paddedOctopuses[i][j].x = j;
                paddedOctopuses[i][j].y = i;
            }
        }
        return paddedOctopuses;
    }

    private static Octopus[][] parseInput() throws Exception {
        List<List<Octopus>> octopuses = new ArrayList<>();
        URL url = Day11Part2.class.getResource("day11input.txt");
        List<String> lines = Files.lines(Paths.get(URLDecoder.decode(url.getPath(), "UTF-8")))
                .collect(Collectors.toList());
        for (String line : lines) {
            octopuses.add(
                    Arrays.asList(line.split("")).stream().map(s -> new Octopus(Integer.parseInt(s), false))
                            .collect(Collectors.toList()));
        }
        return octopuses.stream().map(u -> u.toArray(new Octopus[0])).toArray(Octopus[][]::new);
    }

    private static void printOctopuses(Octopus[][] octopuses) {
        for (int i = 1; i < octopuses.length - 1; i++) {
            for (int j = 1; j < octopuses[0].length - 1; j++) {
                System.out.print(octopuses[i][j]);
            }
            System.out.println();
        }
    }

}
