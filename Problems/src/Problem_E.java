import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Problem_E {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        while(true) {
            try {
                int N = in.nextInt();

                if (N>=1 && N<=100) {
                    int[] t = new int[N];
                    int aux = 0;

                    int temp;
                    for (int i=0; i<N; i++) {
                        temp = in.nextInt();
                        if (temp>0 && temp<200) {
                            t[i] = temp;
                            aux += temp;
                        }
                    }

                    boolean[][] DP = new boolean[N+1][Math.floorDiv(aux, 2)+1];

                    int l = pizzas(DP, t);
                    out.println(Math.abs((aux - l) - l));
                }
            } catch (RuntimeException e) {
                break;
            }
        }

        out.close();
    }

    public static int pizzas(boolean[][] DP, int[] t) {
        int i, j;
        for (i = 0; i < DP.length; i++) {
            DP[i][0] = true;
        }
        for (j = 1; j < DP[0].length; j++) {
            DP[0][j] = false;
        }
        for (i = 1; i < DP.length; i++) {
            for (j = 1; j < DP[0].length; j++) {
                if (t[i - 1] > j)
                    DP[i][j] = DP[i - 1][j];
                else
                    DP[i][j] = DP[i - 1][j] || DP[i - 1][j - t[i - 1]];
            }
        }

        /*for (i=0; i<DP.length; i++) {
            for (j = 0; j<DP[i].length; j++) {
                System.out.print(DP[i][j] + " ");
            }
            System.out.println();
        }*/

        int l = -1;
        for (i = DP[DP.length - 1].length - 1; i >= 0; i--) {
            if (DP[DP.length - 1][i]) {
                l = i;
                break;
            }
        }

        return l;
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
