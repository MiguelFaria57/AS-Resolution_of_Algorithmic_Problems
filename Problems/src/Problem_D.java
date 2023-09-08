import java.util.*;
import java.io.*;


public class Problem_D {
    public static int[][] P;
    public static int[][] DP;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int testCases = in.nextInt();
        int i, j, l;
        for (i=0; i<testCases; i++) {
            int rows = in.nextInt();

            P = new int[rows][rows];
            DP = new int[rows][rows];

            for (j=0; j<rows; j++) {
                ArrayList<Integer> numbers = in.lineTokens();

                for (l=0; l< numbers.size(); l++) {
                    P[j][l] = numbers.get(l);
                    DP[j][l] = -1;
                }
            }

            int highestScore = 0;

            for (j=0; j<rows; j++) {
                int s = getScore(rows, rows-1, j);

                if (s > highestScore)
                    highestScore = s;
            }

            /*for (int z=0; z<DP.length; z++) {
                for (int x=0; x<DP[0].length; x++) {
                    System.out.print(DP[z][x] + " ");
                }
                System.out.println();
            }*/

            out.println(highestScore);
        }

        out.close();
    }

    public static int getScore(int rows, int i, int j) {
        if (i == -1) {
            i = rows-1;
        }
        if (j == -1) {
            j = rows-1;
        }

        if (DP[i][j] != -1) {
            return DP[i][j];
        }
        if (j>i) {
            DP[i][j] = 0;
            return DP[i][j];
        }
        if (i==0 && j==0) {
            DP[i][j] = P[i][j];
            return DP[i][j];
        }

        int aux1 = getScore(rows,i-1, j);
        int aux2 = getScore(rows,i-1, j-1);

        if (aux1 >= aux2)
            DP[i][j] = aux1 + P[i][j];
        else
            DP[i][j] = aux2 + P[i][j];

        return DP[i][j];
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
