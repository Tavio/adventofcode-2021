import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day4Part1 {

    static class Board {
        int[][] numbers;
        int[][] marked;

        public Board(int[][] numbers) {
            this.numbers = numbers;
            this.marked = new int[5][5];
        }

        public void markNumber(int number) {
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    if (numbers[i][j] == number) {
                        marked[i][j] = 1;
                    }
                }
            }
        }

        public boolean hasWon() {
            for (int i = 0; i < 5; i++) {
                int numMarked = 0;
                for (int j = 0; j < 5; j++) {
                    if (marked[i][j] == 1) {
                        numMarked++;
                    }
                }
                if (numMarked == 5) {
                    return true;
                }
            }

            for (int i = 0; i < 5; i++) {
                int numMarked = 0;
                for (int j = 0; j < 5; j++) {
                    if (marked[j][i] == 1) {
                        numMarked++;
                    }
                }
                if (numMarked == 5) {
                    return true;
                }
            }

            return false;
        }

        public int sumOfUnmarkedNumbers() {
            int sum = 0;
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    if (marked[i][j] == 0) {
                        sum += numbers[i][j];
                    }
                }
            }
            return sum;
        }
    }

    static class WinningBoard {
        public Board board;
        public int number;

        public WinningBoard(Board board, int number) {
            this.board = board;
            this.number = number;
        }
    }

    public static WinningBoard getWinningBoard(List<Board> boards, int[] numbers) {
        for (int number : numbers) {
            for (Board board : boards) {
                board.markNumber(number);
                if (board.hasWon()) {
                    return new WinningBoard(board, number);
                }
            }
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        URL url = Day3Part1.class.getResource("day4part1input.txt");
        File file = new File(URLDecoder.decode(url.getPath(), "UTF-8"));
        int[] numbers = null;
        List<Board> boards = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            numbers = Arrays.stream(br.readLine().split(",")).mapToInt(Integer::parseInt).toArray();
            String line;
            while ((line = br.readLine()) != null) {
                int[][] boardNumbers = new int[5][5];
                boardNumbers[0] = Arrays.stream(br.readLine().trim().split("\\s+")).mapToInt(Integer::parseInt)
                        .toArray();
                boardNumbers[1] = Arrays.stream(br.readLine().trim().split("\\s+")).mapToInt(Integer::parseInt)
                        .toArray();
                boardNumbers[2] = Arrays.stream(br.readLine().trim().split("\\s+")).mapToInt(Integer::parseInt)
                        .toArray();
                boardNumbers[3] = Arrays.stream(br.readLine().trim().split("\\s+")).mapToInt(Integer::parseInt)
                        .toArray();
                boardNumbers[4] = Arrays.stream(br.readLine().trim().split("\\s+")).mapToInt(Integer::parseInt)
                        .toArray();
                boards.add(new Board(boardNumbers));
            }
        }

        WinningBoard winningBoard = getWinningBoard(boards, numbers);
        System.out.println(winningBoard.board.sumOfUnmarkedNumbers() * winningBoard.number);
    }
}
