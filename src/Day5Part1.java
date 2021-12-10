import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

public class Day5Part1 {

    static class Coordinates {
        public int x;
        public int y;

        public Coordinates(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    static class Segment {
        public Coordinates startCoordinates;
        public Coordinates endCoordinates;

        public Segment(Coordinates startCoordinates, Coordinates endCoordinates) {
            this.startCoordinates = startCoordinates;
            this.endCoordinates = endCoordinates;
        }

        public boolean isHorizontalOrVertical() {
            return startCoordinates.x == endCoordinates.x || startCoordinates.y == endCoordinates.y;
        }

        public List<Coordinates> getCoordinatesAlongSegment() {
            List<Coordinates> result = new ArrayList<>();
            if (startCoordinates.x == endCoordinates.x) {
                int x = startCoordinates.x;
                int startY;
                int endY;
                if (startCoordinates.y <= endCoordinates.y) {
                    startY = startCoordinates.y;
                    endY = endCoordinates.y;
                } else {
                    startY = endCoordinates.y;
                    endY = startCoordinates.y;
                }
                for (int y = startY; y <= endY; y++) {
                    result.add(new Coordinates(x, y));
                }
            } else {
                int y = startCoordinates.y;
                int startX;
                int endX;
                if (startCoordinates.x <= endCoordinates.x) {
                    startX = startCoordinates.x;
                    endX = endCoordinates.x;
                } else {
                    startX = endCoordinates.x;
                    endX = startCoordinates.x;
                }
                for (int x = startX; x <= endX; x++) {
                    result.add(new Coordinates(x, y));
                }
            }

            return result;
        }
    }

    public static void main(String[] args) throws Exception {
        URL url = Day3Part1.class.getResource("day5part1input.txt");
        File file = new File(URLDecoder.decode(url.getPath(), "UTF-8"));
        List<Segment> segments = new ArrayList<Segment>();
        int maxX = 0;
        int maxY = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] segmentStr = line.split("->");
                String[] startCoordStr = segmentStr[0].split(",");
                int segmentStartX = Integer.parseInt(startCoordStr[0].trim());
                int segmentStartY = Integer.parseInt(startCoordStr[1].trim());
                String[] endCoordStr = segmentStr[1].split(",");
                int segmentEndX = Integer.parseInt(endCoordStr[0].trim());
                int segmentEndY = Integer.parseInt(endCoordStr[1].trim());
                segments.add(new Segment(new Coordinates(segmentStartX, segmentStartY),
                        new Coordinates(segmentEndX, segmentEndY)));
                if (segmentStartX > maxX) {
                    maxX = segmentStartX;
                }
                if (segmentEndX > maxX) {
                    maxX = segmentEndX;
                }
                if (segmentStartY > maxY) {
                    maxY = segmentStartY;
                }
                if (segmentEndX > maxX) {
                    maxY = segmentEndY;
                }
            }
        }

        int[][] oceanFloor = new int[maxY + 1][maxX + 1];
        for (Segment segment : segments) {
            if (!segment.isHorizontalOrVertical()) {
                continue;
            }
            List<Coordinates> coords = segment.getCoordinatesAlongSegment();
            for (Coordinates coord : coords) {
                oceanFloor[coord.y][coord.x]++;
            }
        }

        int hazards = 0;
        for (int i = 0; i < oceanFloor.length; i++) {
            for (int j = 0; j < oceanFloor[0].length; j++) {
                if (oceanFloor[i][j] > 1) {
                    hazards++;
                }
            }
        }

        System.out.println(hazards);

    }
}
