import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

public class Day6Part1 {
    static class LanternFish {
        private int timer;

        public LanternFish(int timer) {
            this.timer = timer;
        }

        public boolean tick() {
            if (timer == 0) {
                timer = 6;
                return true;
            }
            timer--;
            return false;
        }
    }

    public static void main(String[] args) throws Exception {
        URL url = Day6Part1.class.getResource("day6part1input.txt");
        File file = new File(URLDecoder.decode(url.getPath(), "UTF-8"));
        List<LanternFish> fishes = new LinkedList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            fishes = Arrays.stream(br.readLine().split(","))
                    .map(n -> new LanternFish(Integer.parseInt(n))).collect(Collectors.toList());
        }

        for (int i = 1; i <= 80; i++) {
            ListIterator<LanternFish> iter = fishes.listIterator();
            while (iter.hasNext()) {
                LanternFish fish = iter.next();
                boolean newFishCreated = fish.tick();
                if (newFishCreated) {
                    iter.add(new LanternFish(8));
                }
            }
        }

        System.out.println(fishes.size());
    }
}
