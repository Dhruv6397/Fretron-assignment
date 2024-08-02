import java.util.*;

class Graph {
    int V; 
    List<List<Integer>> adj; 

    Graph(int V) {
        this.V = V;
        adj = new ArrayList<>(V);
        for (int i = 0; i < V; i++) {
            adj.add(new ArrayList<>());
        }
    }

    void addEdge(int u, int v) {
        adj.get(u).add(v);
        adj.get(v).add(u); 
    }

   
    int[] graphColoring() {
        int[] result = new int[V];
        Arrays.fill(result, -1);

        result[0] = 0; 

        boolean[] available = new boolean[V]; 

       
        for (int u = 1; u < V; u++) {
            Arrays.fill(available, true);

          
            for (int i : adj.get(u)) {
                if (result[i] != -1) {
                    available[result[i]] = false;
                }
            }

           
            int color;
            for (color = 0; color < V; color++) {
                if (available[color]) {
                    break;
                }
            }

            result[u] = color; 
        }
        return result;
    }

    
    List<Integer> findPath(int start, int end, int[] colors) {
        boolean[] visited = new boolean[V];
        int[] parent = new int[V];
        Arrays.fill(parent, -1);

        Queue<Integer> queue = new LinkedList<>();
        queue.add(start);
        visited[start] = true;

        while (!queue.isEmpty()) {
            int u = queue.poll();
            if (u == end) break;

            for (int v : adj.get(u)) {
                if (!visited[v] && colors[u] != colors[v]) { 
                    visited[v] = true;
                    parent[v] = u;
                    queue.add(v);
                }
            }
        }

        
        List<Integer> path = new ArrayList<>();
        for (int v = end; v != -1; v = parent[v]) {
            path.add(v);
        }
        Collections.reverse(path);
        return path;
    }
}


public class NoAccidentPlease {
    public static void main(String[] args) {
       
        int V = 6;
        Graph graph = new Graph(V);

        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(0, 4);
        graph.addEdge(4, 5);
        graph.addEdge(5, 3);

        int[] colors = graph.graphColoring();

        List<Integer> path1 = graph.findPath(0, 3, colors);
        List<Integer> path2 = graph.findPath(0, 5, colors);

        System.out.println("Path 1: " + path1);
        System.out.println("Path 2: " + path2);
    }
}
