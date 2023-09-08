import java.util.*;
import java.io.*;


public class Problem_A {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        while(true) {
            try {
                int n = in.nextInt();
                int[] pessoas = new int[n];
                for (int l = 0; l < n; l++)
                    pessoas[l] = in.nextInt();
                in.nextInt();

                boolean a = checkGame(pessoas);
                if (a)
                    out.println("Fair");
                else
                    out.println("Rigged");


            } catch (RuntimeException e) {
                break;
            }
        }

        out.close();
    }

    public static boolean checkGame(int[] p) {
        int i, j, k;
        int n = p.length;
        Arrays.sort(p);
        for (i=0; i<n-2; i++) {
            j=i+1;
            k=n-1;

            while (j<k) {
                if (p[i]+p[j]+p[k] == 0) {
                    return true;
                }
                else if (p[i]+p[j]+p[k] > 0)
                    k -= 1;
                else
                    j += 1;
            }
        }
        return false;
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
    }
}
