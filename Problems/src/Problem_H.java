import java.util.*;
import java.io.*;

public class Problem_H {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        while(true) {
            try {
                int n = in.nextInt();
                int m = in.nextInt();

                ArrayList<ArrayList<Integer>> connections = new ArrayList<>();
                for (int a=0; a<n; a++) {
                    ArrayList<Integer> aux = new ArrayList<>();
                    connections.add(aux);
                }

                for (int i=0; i<m; i++) {
                    int temp1 = in.nextInt();
                    int temp2 = in.nextInt();

                    connections.get(temp1-1).add(temp2-1);
                    connections.get(temp2-1).add(temp1-1);
                }

                boolean a = checkCorruption(connections, n);
                if (a)
                    out.println("NOT SURE");
                else
                    out.println("NO");

            } catch (RuntimeException e) {
                break;
            }
        }

        out.close();
    }

    public static boolean checkCorruption(ArrayList<ArrayList<Integer>> connections, int n) {
        ArrayList<Integer> queue = new ArrayList<>();
        int[] colors = new int[n];
        for(int i = 0; i < n; i++)
            colors[i] = -1;

        colors[0] = 1;
        queue.add(0);
        while (queue.size() != 0) {
            int t = queue.remove(0);
            for (int c: connections.get(t)) {
                if (colors[c] == -1) {
                    colors[c] = 1 - colors[t];
                    queue.add(c);
                }
                else if (colors[c] == colors[t]) {
                    return false;
                }
            }
        }
        return true;
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