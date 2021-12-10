import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.net.URLDecoder;

public class Day2Part1 {
    public static void main(String[] args) throws Exception {
        URL url = Day2Part1.class.getResource("day2part1input.txt");
        File file = new File(URLDecoder.decode(url.getPath(), "UTF-8"));

        int horizontalPosition = 0;
        int depth = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] command = line.split(" ");
                String direction = command[0];
                Integer distance = Integer.parseInt(command[1]);
                switch (direction) {
                    case "forward":
                        horizontalPosition += distance;
                        break;
                    case "down":
                        depth += distance;
                        break;
                    case "up":
                        depth -= distance;
                        break;
                }
            }
        }

        System.out.println(horizontalPosition * depth);

    }
}
