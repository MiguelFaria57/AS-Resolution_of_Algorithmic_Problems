import java.util.*;
import java.io.*;


public class Problem_I {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        while(true) {
            try {
                int numPlaces = in.nextInt();
                int finish = in.nextInt();
                int[][] links = new int[numPlaces][numPlaces];

                int i, j;
                for (i=0; i<numPlaces; i++) {
                    ArrayList<Integer> line = in.lineTokens();

                    for (j=0; j<numPlaces; j++) {
                        links[line.get(0)-1][j] = line.get(j+1);
                    }
                }

                out.println(getMinimumCost(1, finish, numPlaces, links));

            } catch (RuntimeException e) {
                break;
            }
        }

        out.close();
    }

    public static int getMinimumCost(int start, int finish, int numPlaces, int[][] links) {
        ArrayList<Integer> queue = new ArrayList<>();
        queue.add(start-1);

        int[] dist = new int[numPlaces];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[start-1] = 0;

        while (queue.size() != 0) {
            int currentV = queue.get(0);
            for (int j=0; j<links[currentV].length; j++) {
                int d = links[currentV][j];
                if (j!=currentV && d!=-1) {
                    if (dist[j] > d + dist[currentV]) {
                        queue.add(j);
                        dist[j] = d + dist[currentV];
                    }
                }
            }
            queue.remove(0);
        }

        return dist[finish-1];
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
