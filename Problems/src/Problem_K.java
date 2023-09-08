import java.util.*;
import java.io.*;

public class Problem_K {
    public static boolean endBlock = false;
    public static int[][] block;
    public static int[] dfs;
    public static int[] low;
    public static int[] parent;
    public static int t = 1;
    public static Set<Integer> criticalPlaces;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        while(true) {
            try {
                ArrayList<Integer> line = in.lineTokens();

                if (line.size()==1) {
                    int num_places = line.get(0);

                    if (num_places == 0 && !endBlock) {
                        endBlock = true;
                        calculateCriticalPlaces(0);
                        out.println(criticalPlaces.size());
                        t = 1;
                        criticalPlaces.clear();
                    }
                    else if (num_places != 0) {
                        endBlock = false;
                        block = new int[num_places][num_places];
                        dfs = new int[num_places];
                        low = new int[num_places];
                        parent = new int[num_places];
                        criticalPlaces = new HashSet<>();

                        for (int i=0; i<num_places; i++) {
                            dfs[i] = -1;
                            low[i] = -1;
                            parent[i] = -1;
                        }
                    }
                }
                else {
                    int place = line.get(0);
                    for (int i=1; i<line.size(); i++) {
                        block[place-1][line.get(i)-1] = 1;
                        block[line.get(i)-1][place-1] = 1;
                    }
                }

            } catch (RuntimeException e) {
                break;
            }
        }

        out.close();
    }

    public static void calculateCriticalPlaces(int v) {
        dfs[v] = t;
        low[v] = t;
        t += 1;

        for (int w=0; w<block[v].length; w++) {
            if (block[v][w] == 1) {
                if (dfs[w] == -1) {
                    parent[w] = v;
                    calculateCriticalPlaces(w);
                    low[v] = Math.min(low[v], low[w]);
                    if (dfs[v] == 1 && dfs[w] != 2) {
                        criticalPlaces.add(v);
                    }
                    if (dfs[v] != 1 && low[w] >= dfs[v]) {
                        criticalPlaces.add(v);
                    }
                } else if (parent[v] != w) {
                    low[v] = Math.min(low[v], dfs[w]);
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
