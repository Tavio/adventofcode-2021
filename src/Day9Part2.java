import java.net.URL;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

public class Day9Part2 {

    static class Point {
        public int[] coords;

        public Point(int[] coords) {
            this.coords = coords;
        }

        @Override
        public int hashCode() {
            return coords[0] * 31 + coords[1];
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null)
                return false;
            if (obj == this)
                return true;
            if (!(obj instanceof Point))
                return false;
            Point otherPoint = (Point) obj;
            return this.coords[0] == otherPoint.coords[0] && this.coords[1] == otherPoint.coords[1];
        }
    }

    public static void main(String[] args) throws Exception {
        Integer[][] heightMap = parseInput();
        List<Point> lowPoints = getLowPoints(heightMap);
        List<Integer> basinSizes = getBasinSizes(heightMap, lowPoints);
        Collections.sort(basinSizes);
        System.out.println(basinSizes);
    }

    private static List<Integer> getBasinSizes(Integer[][] heightMap, List<Point> lowPoints) {
        List<Integer> basinSizes = new ArrayList<>();
        for (Point lowPoint : lowPoints) {
            Set<Point> basin = new HashSet<>();
            basin.add(lowPoint);
            basin = getBasin(heightMap, basin, lowPoint);
            int lastBasinSize = basin.size();
            while (true) {
                // Rerun the search until it stops producing new results
                basin = getBasin(heightMap, basin, lowPoint);
                if (basin.size() == lastBasinSize) {
                    break;
                }
                lastBasinSize = basin.size();
            }
            basinSizes.add(lastBasinSize);
        }
        return basinSizes;
    }

    private static Set<Point> getBasin(Integer[][] heightMap, Set<Point> initialBasin, Point lowPoint) {
        boolean visited[][] = new boolean[heightMap.length][heightMap[0].length];
        Set<Point> basin = new HashSet<Point>(initialBasin);
        Queue<Point> queue = new ArrayDeque<Point>();
        queue.offer(lowPoint);
        while (!queue.isEmpty()) {
            Point point = queue.poll();
            addNeighboursToQueue(queue, point, visited, heightMap);
            if (pointBelongsToBasin(point, heightMap, basin)) {
                basin.add(point);
            }
        }
        return basin;
    }

    private static boolean pointBelongsToBasin(Point point, Integer[][] heightMap,
            Set<Point> basin) {
        if (heightMap[point.coords[0]][point.coords[1]] == 9) {
            return false;
        }
        if (basin.contains(point)) {
            return true;
        }
        int i = point.coords[0];
        int j = point.coords[1];
        int pointsLowerInBasin = 0;
        int pointsLowerOutsideBasin = 0;
        if (i - 1 >= 0 && heightMap[i - 1][j] < heightMap[i][j]) {
            if (basin.contains(new Point(new int[] { i - 1, j }))) {
                pointsLowerInBasin++;
            } else {
                pointsLowerOutsideBasin++;
            }
        }
        if (i + 1 < heightMap.length && heightMap[i + 1][j] < heightMap[i][j]) {
            if (basin.contains(new Point(new int[] { i + 1, j }))) {
                pointsLowerInBasin++;
            } else {
                pointsLowerOutsideBasin++;
            }
        }

        if (j - 1 >= 0 && heightMap[i][j - 1] < heightMap[i][j]) {
            if (basin.contains(new Point(new int[] { i, j - 1 }))) {
                pointsLowerInBasin++;
            } else {
                pointsLowerOutsideBasin++;
            }
        }

        if (j + 1 < heightMap[i].length && heightMap[i][j + 1] < heightMap[i][j]) {
            if (basin.contains(new Point(new int[] { i, j + 1 }))) {
                pointsLowerInBasin++;
            } else {
                pointsLowerOutsideBasin++;
            }
        }

        return pointsLowerInBasin >= 1 && pointsLowerOutsideBasin == 0;

    }

    private static void addNeighboursToQueue(Queue<Point> queue, Point point, boolean[][] visited,
            Integer[][] heightMap) {
        int i = point.coords[0];
        int j = point.coords[1];
        List<Point> neighbours = new ArrayList<Point>();
        if (i - 1 >= 0 && heightMap[i - 1][j] > heightMap[i][j] && !visited[i - 1][j]) {
            int[] coords = { i - 1, j };
            neighbours.add(new Point(coords));
            visited[i - 1][j] = true;
        }
        if (i + 1 < heightMap.length && heightMap[i + 1][j] > heightMap[i][j] && !visited[i + 1][j]) {
            int[] coords = { i + 1, j };
            neighbours.add(new Point(coords));
            visited[i + 1][j] = true;
        }
        if (j - 1 >= 0 && heightMap[i][j - 1] > heightMap[i][j] && !visited[i][j - 1]) {
            int[] coords = { i, j - 1 };
            neighbours.add(new Point(coords));
            visited[i][j - 1] = true;
        }
        if (j + 1 < heightMap[i].length && heightMap[i][j + 1] > heightMap[i][j] && !visited[i][j + 1]) {
            int[] coords = { i, j + 1 };
            neighbours.add(new Point(coords));
            visited[i][j + 1] = true;
        }
        for (Point neighbour : neighbours) {
            queue.offer(neighbour);
        }
    }

    private static List<Point> getLowPoints(Integer[][] heightMap) {
        List<Point> lowPoints = new ArrayList<>();
        for (int i = 0; i < heightMap.length; i++) {
            for (int j = 0; j < heightMap[0].length; j++) {
                if (i - 1 >= 0 && heightMap[i - 1][j] <= heightMap[i][j] ||
                        i + 1 < heightMap.length && heightMap[i + 1][j] <= heightMap[i][j] ||
                        j - 1 >= 0 && heightMap[i][j - 1] <= heightMap[i][j] ||
                        j + 1 < heightMap[i].length && heightMap[i][j + 1] <= heightMap[i][j]) {
                    continue;
                }
                int[] coords = { i, j };
                lowPoints.add(new Point(coords));
            }
        }
        return lowPoints;
    }

    private static Integer[][] parseInput() throws Exception {

        List<List<Integer>> heightMap = new ArrayList<>();
        URL url = Day9Part2.class.getResource("day9part1input.txt");
        Files.lines(Paths.get(URLDecoder.decode(url.getPath(), "UTF-8")))
                .forEach(l -> {
                    heightMap.add(Arrays.asList(l.split("")).stream().map(s -> Integer.parseInt(s))
                            .collect(Collectors.toList()));
                });

        return heightMap.stream().map(u -> u.toArray(new Integer[0])).toArray(Integer[][]::new);
    }
}
