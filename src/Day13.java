import java.net.URL;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day13 {

    static abstract class Fold {
        protected int line;

        public int getLine() {
            return line;
        }

        public abstract int[][] doFold(int[][] paper);
    }

    static class HorizontalFold extends Fold {
        public HorizontalFold(int line) {
            this.line = line;
        }

        @Override
        public int[][] doFold(int[][] paper) {
            int[][] newPaper = new int[paper.length][paper[0].length - 1 - line];
            for (int i = 0; i < paper.length; i++) {
                for (int j = line + 1; j < paper[0].length; j++) {
                    int newX = line - (j - line);
                    newPaper[i][newX] = paper[i][j] == 1 || paper[i][newX] == 1 ? 1 : 0;
                }
            }
            return newPaper;
        }
    }

    static class VerticalFold extends Fold {
        public VerticalFold(int line) {
            this.line = line;
        }

        @Override
        public int[][] doFold(int[][] paper) {
            int[][] newPaper = new int[paper.length - 1 - line][paper[0].length];
            for (int i = line + 1; i < paper.length; i++) {
                for (int j = 0; j < paper[0].length; j++) {
                    int newY = line - (i - line);
                    newPaper[newY][j] = paper[i][j] == 1 || paper[newY][j] == 1 ? 1 : 0;
                }
            }
            return newPaper;
        }
    }

    static class Paper {
        int[][] paper;
        List<Fold> folds;

        public Paper(int[][] paper, List<Fold> folds) {
            this.paper = paper;
            this.folds = folds;
        }

        public void fold() {
            for (Fold fold : this.folds) {
                this.paper = fold.doFold(this.paper);
            }
        }

        public void foldFirst() {
            this.paper = this.folds.get(0).doFold(this.paper);
        }

        public int visibleDots() {
            int count = 0;
            for (int i = 0; i < paper.length; i++) {
                for (int j = 0; j < paper[0].length; j++) {
                    count += paper[i][j];
                }
            }
            return count;
        }

        private void print() {
            for (int i = 0; i < paper.length; i++) {
                for (int j = 0; j < paper[0].length; j++) {
                    System.out.print(paper[i][j] + " ");
                }
                System.out.println();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        Paper paper = parseInput();
        // Part 1:
        paper.foldFirst();
        System.out.println(paper.visibleDots());
        // Part 2:
        paper = parseInput();
        paper.fold();
        paper.print();
    }

    private static Paper parseInput() throws Exception {
        List<int[]> dots = new ArrayList<>();
        List<Fold> folds = new ArrayList<>();
        int maxX = -1;
        int maxY = -1;
        URL url = Day13.class.getResource("day13input.txt");
        List<String> lines = Files.lines(Paths.get(URLDecoder.decode(url.getPath(), "UTF-8")))
                .collect(Collectors.toList());
        for (String line : lines) {
            if (line.matches("[^,]+,[^,]+")) {
                String[] splitLine = line.split(",");
                int x = Integer.parseInt(splitLine[0].trim());
                int y = Integer.parseInt(splitLine[1].trim());
                if (x > maxX) {
                    maxX = x;
                }
                if (y > maxY) {
                    maxY = y;
                }

                dots.add(new int[] { x, y });
            } else if (line.matches("^fold along y.*")) {
                String[] splitLine = line.split("=");
                folds.add(new VerticalFold(Integer.parseInt(splitLine[1])));
            } else if (line.matches("^fold along x.*")) {
                String[] splitLine = line.split("=");
                folds.add(new HorizontalFold(Integer.parseInt(splitLine[1])));
            } else {
                continue;
            }
        }
        int[][] paperMatrix = new int[maxY + 1][maxX + 1];
        for (int[] dot : dots) {
            paperMatrix[dot[1]][dot[0]] = 1;
        }
        return new Paper(paperMatrix, folds);
    }

}
