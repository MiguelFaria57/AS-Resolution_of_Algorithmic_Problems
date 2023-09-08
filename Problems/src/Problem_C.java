import java.util.*;
import java.io.*;

public class Problem_C {
    public static int[][] matrix;
    public static int[] visited;
    public static int[] degree;
    public static int best;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        while(true) {
            try {
                int n = in.nextInt();
                int m = in.nextInt();
                int k = in.nextInt();

                if (n<=12 && m<=40 && k<=n) {
                    matrix = new int[n][n];
                    visited = new int[n];
                    degree = new int[n];
                    best = -1;

                    int i;
                    for (i=0; i<m; i++) {
                        ArrayList<Integer> aux = in.lineTokens();
                        matrix[aux.get(0)-1][aux.get(1)-1] = aux.get(2);
                        matrix[aux.get(1)-1][aux.get(0)-1] = aux.get(2);
                    }

                    visited[0] = 1;
                    tree(n, k, 1, 0);

                    if (best >= 0)
                        out.println(best);
                    else
                        out.println("NO WAY!");
                }
            } catch (RuntimeException e) {
                break;
            }
        }

        out.close();
    }

    public static void tree(int n, int k, int v, int cost) {
        if ((best==-1 || best>cost) && v==n) {
            best = cost;
            return;
        }
        if (best!=-1 && best<cost)
            return;

        int i, j;
        for (i=0; i<n; i++) {
            if (visited[i] == 0) {
                for (j=0; j<n; j++) {
                    if (visited[j] == 1) {
                        if (matrix[i][j]!=0 && degree[j]<k) {
                            degree[i]++;
                            degree[j]++;
                            visited[i] = 1;
                            tree(n, k, v+1, cost+matrix[i][j]);
                            visited[i] = 0;
                            degree[i]--;
                            degree[j]--;
                        }
                    }
                }
            }
        }
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
