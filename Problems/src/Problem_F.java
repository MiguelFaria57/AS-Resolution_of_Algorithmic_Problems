import java.util.*;
import java.io.*;

public class Problem_F {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int numTests = in.nextInt();
        int i,j;
        for (i=0; i<numTests; i++) {
            int numObjects = in.nextInt();
            int numMeters = in.nextInt();
            int[] posObjects = new int[numObjects];
            for (j=0; j<numObjects; j++) {
                posObjects[j] = in.nextInt();
            }

            out.println(guards(posObjects, numMeters));
        }

        out.close();
    }

    public static int guards(int[] posObj, int m) {
        Arrays.sort(posObj);

        int[] posGuards = new int[posObj.length];
        int i=0;
        posGuards[i] = posObj[0] + m;
        for (int j=1; j< posObj.length; j++) {
            if (posObj[j] > posGuards[i]+m) {
                posGuards[i+1] = posObj[j]+m;
                i++;
            }
        }

        return i+1;
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
