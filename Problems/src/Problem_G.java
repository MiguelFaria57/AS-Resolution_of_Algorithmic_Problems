import java.util.*;
import java.io.*;

public class Problem_G {
    public static int N;
    public static int[][] E;
    public static int[] visited;
    public static int best;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        while(true) {
            try {
                N = in.nextInt();
                int m = in.nextInt();

                E = new int[N][N];
                visited = new int[N];
                best = 0;

                int i;
                for (i=0; i<m; i++) {
                    int m1 = in.nextInt();
                    int m2 = in.nextInt();

                    E[m1][m2] = 1;
                    E[m2][m1] = 1;
                }

                usm(0, 0);

                out.println(best);

            } catch (RuntimeException e) {
                break;
            }
        }


        out.close();
    }

    public static void usm(int n, int a) {
        int b = a + ub(n);

        if (b <= best)
            return;
        if (a > best)
            best = a;
        if (n == N)
            return;

        if (visited[n] == 0) {
            visited[n] = 1;
            int j;
            for (j=0; j<N; j++) {
                if (E[n][j] == 1)
                    visited[j]++;
            }

            usm(n+1, a+1);

            for (j=0; j<N; j++) {
                if (E[n][j] == 1)
                    visited[j]--;
            }

            visited[n] = 0;
        }

        usm(n+1, a);
    }

    public static int ub(int n) {
        int c = 0;
        for (int i=n; i<N; i++) {
            if (visited[i] == 0)
                c++;
        }
        return c;
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
