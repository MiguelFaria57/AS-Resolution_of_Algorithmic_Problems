import java.util.*;
import java.io.*;

public class Problem_B {
    public static int[][] tab = new int [400][400];

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int total = 0;
        int num = in.nextInt();
        if (num >= 1 && num <= 30) {
            for (int l = 0; l < num; l++) {
                ArrayList<Integer> pos_mov = in.lineTokens();
                if (pos_mov.get(2) >= 0 && pos_mov.get(2) <= 7)
                    total += chess(pos_mov.get(0)+200, pos_mov.get(1)+200, pos_mov.get(2));
            }
        }
        out.println(total);

        out.close();
    }

    public static int chess(int x, int y, int i) {
        if (i < 0)
            return 0;

        int c = 0;
        if (tab[x][y] == 0) {
            c = 1;
            tab[x][y] = 1;
        }


        return c + chess(x-1, y+2, i-1)
                 + chess(x+1, y+2, i-1)
                 + chess(x-1, y-2, i-1)
                 + chess(x+1, y-2, i-1)
                 + chess(x+2, y-1, i-1)
                 + chess(x-2, y-1, i-1)
                 + chess(x+2, y+1, i-1)
                 + chess(x-2, y+1, i-1);
    }

    static class InputReader {
        public BufferedReader reader;
        public StringTokenizer tokenizer;

        public InputReader(InputStream stream) {
            reader = new BufferedReader(new InputStreamReader(stream));
            tokenizer = null;
        }

        public String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            return tokenizer.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }

        public ArrayList<Integer> lineTokens() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            ArrayList<Integer> i = new ArrayList<>();
            while (tokenizer.hasMoreTokens())
                i.add(Integer.parseInt(tokenizer.nextToken()));

            return i;
        }
    }
}
