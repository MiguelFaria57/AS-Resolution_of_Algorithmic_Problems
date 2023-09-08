import java.util.*;
import java.io.*;

public class Problem_J {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        while(true) {
            try {
                int i;

                int numNodes = in.nextInt();
                Node[] nodes = new Node[numNodes];
                double[][] connections = new double[numNodes][numNodes];

                for (i=0; i<numNodes; i++) {
                    nodes[i] = new Node(in.nextInt(), in.nextInt());
                }

                int numConnections = in.nextInt();
                for (i=0; i<numConnections; i++) {
                    int aux1 = in.nextInt();
                    int aux2 = in.nextInt();
                    double d = euclidianDistance(nodes[aux1], nodes[aux2]);

                    connections[aux1][aux2] = d;
                    connections[aux2][aux1] = d;
                }



            } catch (RuntimeException e) {
                break;
            }
        }

        out.close();
    }

    public static double euclidianDistance(Node n1, Node n2) {
        return Math.sqrt(Math.pow(Math.abs(n1.x-n2.x), 2) + Math.pow(Math.abs(n1.y-n2.y), 2));
    }

    static class Node {
        int x;
        int y;
        int parent;

        public Node(int x, int y) {
            this.x = x;
            this.y = y;
            this.parent = -1;
        }

        public void setParent(int parent) {
            this.parent = parent;
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
    }
}
