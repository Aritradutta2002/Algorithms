
# ╔══════════════════════════════════════════════════════════════╗
# ║           01 — GRAPH FOUNDATIONS                            ║
# ║       Complete Theory + In-Depth Java Implementation        ║
# ╚══════════════════════════════════════════════════════════════╝

---

## 🧭 What You Will Learn in This File

```
1. What is a Graph? (Formal Definition)
2. Complete Classification of Graphs
3. Key Terminology (Degree, Path, Cycle, Walk, Trail)
4. Graph Representations (Matrix, List, Edge List)
5. Mathematical Properties (Handshaking Lemma, Trees, Euler)
6. Building Graphs from LeetCode Input
7. Complete Java Implementation (All Representations)
8. Complexity Comparison Table
9. LeetCode Problems + Hints
10. Interview Tips
```

---

## 📌 1. What is a Graph?

A **graph** is a mathematical structure used to model **pairwise relationships** between objects.

### Formal Definition
```
G = (V, E)

where:
  V = a finite, non-empty set of VERTICES (also called nodes)
  E = a set of EDGES, where each edge connects two vertices

Example:
  V = {0, 1, 2, 3}
  E = {(0,1), (1,2), (2,3), (3,0)}

  0 ——— 1
  |     |
  3 ——— 2
```

> 💡 **Key Insight:** Trees, Linked Lists, and Grids are ALL special cases of graphs.
> - A **tree** = connected, acyclic undirected graph
> - A **linked list** = graph where each node has at most 1 outgoing edge
> - A **grid** = graph where each cell connects to its 4 (or 8) neighbors

---

## 📌 2. Complete Classification of Graphs

### 2.1 Directed vs Undirected

#### Undirected Graph
Edges have **NO direction**. Edge (u, v) = edge (v, u). Relationship is mutual.

```
    0 ——— 1
    |     |
    2 ——— 3

Adjacency: 0↔1, 0↔2, 1↔3, 2↔3
```

**Real-world examples:** Road networks (two-way roads), Facebook friendships, electrical circuits.

#### Directed Graph (Digraph)
Edges have **direction**. Edge (u → v) does NOT imply (v → u).

```
    0 ──► 1
    ▲     │
    │     ▼
    3 ◄── 2

Edges: 0→1, 1→2, 2→3, 3→0  (a directed cycle!)
```

**Real-world examples:** Twitter follows, web hyperlinks, task dependencies, call graphs.

---

### 2.2 Weighted vs Unweighted

#### Unweighted Graph
All edges are equal (implicitly weight = 1). Used for counting hops, reachability.

#### Weighted Graph
Each edge carries a **numerical weight** (cost, distance, capacity, probability).

```
    0 ──5── 1
    |       |
    3       2
    |       |
    2 ──1── 3

Shortest path 0→3: 0→2→3 (cost=4) NOT 0→1→3 (cost=7)
```

**Real-world examples:** GPS distances, network bandwidth, flight costs, probability paths.

---

### 2.3 Cyclic vs Acyclic

#### Cyclic Graph
Contains at least one **cycle** — a path from a vertex back to itself.

```
    0 → 1 → 2
            ↓
            3 → 0   ← cycle: 0→1→2→3→0
```

#### Acyclic Graph
**No cycles** exist anywhere in the graph.

#### DAG (Directed Acyclic Graph) ⭐ EXTREMELY IMPORTANT
Directed + No cycles. The backbone of topological sort, build systems, dependency resolution.

```
    5 ──► 0 ◄── 4
    │           │
    ▼           ▼
    2 ──► 3 ──► 1

No path leads back to any previously visited node.
```

> 💡 **Why DAGs matter:** Every time you see "dependencies", "prerequisites", "ordering" → think DAG + Topological Sort.

---

### 2.4 Connected vs Disconnected

#### Connected (Undirected)
There is a **path between EVERY pair** of vertices.

#### Disconnected
Some pairs have no path. The graph has multiple **connected components**.

```
Component 1:   Component 2:   Component 3:
  0 — 1 — 2     3 — 4            5

Total components = 3
```

#### Strongly Connected (Directed)
Every vertex can reach **every other vertex** (in both directions via directed paths).

#### Weakly Connected (Directed)
Connected if you **ignore edge directions** (treat all edges as undirected).

```
Strongly Connected:        Weakly Connected (not strongly):
  0 → 1                      0 → 1
  ↑   ↓                      ↑
  3 ← 2                      2
(0→1→2→3→0 cycle)          (can't get from 1 back to 0)
```

---

### 2.5 Dense vs Sparse

| Type   | Edge Count  | Typical Use Case     | Best Representation |
|--------|-------------|----------------------|---------------------|
| Sparse | E ≈ O(V)    | Road networks, trees | Adjacency List      |
| Dense  | E ≈ O(V²)   | Social networks      | Adjacency Matrix    |

> 💡 **Rule of thumb:** If E << V², use adjacency list. If E ≈ V², matrix is fine.

---

### 2.6 Simple vs Multi-graph vs Pseudo-graph

| Type         | Self-loops? | Multiple edges between same pair? |
|--------------|-------------|-----------------------------------|
| Simple Graph | No          | No                                |
| Multi-graph  | No          | Yes                               |
| Pseudo-graph | Yes         | Yes                               |

**LeetCode problems almost always use simple graphs.**

---

### 2.7 Complete Graph (Kₙ)

Every vertex is connected to every other vertex.

```
K₄ (Complete graph with 4 vertices):

    0
   /|\
  / | \
 1——+——2
  \ | /
   \|/
    3

Edges = n(n-1)/2 = 4×3/2 = 6
```

---

## 📌 3. Key Terminology

### 3.1 Degree

**Undirected graph:**
```
degree(v) = number of edges incident to v = number of neighbors
```

**Directed graph:**
```
in-degree(v)  = number of edges COMING INTO v
out-degree(v) = number of edges GOING OUT OF v
total-degree(v) = in-degree + out-degree
```

**Example:**
```
    0 ──► 1 ──► 3
    │     ▲
    ▼     │
    2 ────┘

in-degree:  [0, 2, 1, 1]
out-degree: [2, 1, 1, 0]
```

### 3.2 The Handshaking Lemma ⭐

```
For any undirected graph:
  Σ degree(v) = 2 × |E|

Consequence: Every graph has an EVEN number of odd-degree vertices.

For directed graphs:
  Σ in-degree(v) = Σ out-degree(v) = |E|
```

**Why it matters in interviews:**
- If you know the number of edges, you know the sum of degrees.
- Helps verify graph construction.
- Used to prove properties about Eulerian paths.

---

### 3.3 Walk, Trail, Path — The Differences

```
Walk:  vertices AND edges can repeat.   0→1→2→1→3
Trail: edges cannot repeat.             0→1→2→3→1  (edge 1-3 used once)
Path:  vertices cannot repeat.          0→1→2→3    (simplest, most common)
```

**In competitive programming and LeetCode, "path" almost always means simple path.**

---

### 3.4 Cycle

A **path** where the start vertex = end vertex, with at least one edge.

```
Simple cycle: only start = end is repeated.
  0 → 1 → 2 → 0   (length 3, odd cycle)
  0 → 1 → 2 → 3 → 0  (length 4, even cycle)
```

> 💡 **Odd cycle = NOT bipartite.** This is a fundamental theorem.

---

### 3.5 Spanning Tree

A **subgraph** that:
- Includes ALL vertices
- Is connected
- Has NO cycles
- Has exactly **V - 1 edges**

```
Original graph:          One spanning tree:
  0 — 1 — 2               0 — 1 — 2
  |   |   |               |
  3 — 4 — 5               3 — 4 — 5

(6 vertices, 7 edges)    (6 vertices, 5 edges = V-1)
```

**Minimum Spanning Tree (MST):** Spanning tree with minimum total edge weight. (Kruskal's / Prim's)

---

### 3.6 Eulerian vs Hamiltonian

#### Eulerian Path
Visits every **EDGE** exactly once.

```
Condition (undirected):
  - Exactly 0 vertices with odd degree → Eulerian CIRCUIT (starts and ends at same vertex)
  - Exactly 2 vertices with odd degree → Eulerian PATH (starts at one odd-degree vertex, ends at other)
  - Any other count → No Eulerian path

Example:
  0 — 1 — 2
  |       |
  3 ——————┘
  
  Degrees: 0→2, 1→2, 2→2, 3→2  (all even → Eulerian circuit exists)
```

#### Hamiltonian Path
Visits every **VERTEX** exactly once.

```
⚠️ NP-complete — no known polynomial-time algorithm.
No simple condition like Eulerian.
```

---

## 📌 4. Graph Representations

### 4.1 Adjacency Matrix

A 2D array where `matrix[u][v] = weight` (or 1/0 for unweighted).

```
Graph:  0—1, 0—2, 1—3

     0  1  2  3
  0 [0, 1, 1, 0]
  1 [1, 0, 0, 1]
  2 [1, 0, 0, 0]
  3 [0, 1, 0, 0]
```

**Pros:**
- O(1) edge existence check: `matrix[u][v] != 0`
- Simple to implement
- Good for dense graphs

**Cons:**
- O(V²) space — wasteful for sparse graphs
- O(V) to iterate all neighbors of a vertex

**Use when:** Dense graph, Floyd-Warshall (all-pairs shortest path), small V (≤ 1000).

---

### 4.2 Adjacency List ⭐ (MOST COMMON)

Each vertex stores a list of its neighbors.

```
Graph:  0—1, 0—2, 1—3

0 → [1, 2]
1 → [0, 3]
2 → [0]
3 → [1]
```

**Pros:**
- O(V + E) space — efficient for sparse graphs
- O(degree(v)) to iterate neighbors — fast in practice
- Natural for BFS, DFS, Dijkstra

**Cons:**
- O(degree(v)) edge existence check (not O(1))

**Use when:** Almost always. BFS, DFS, Dijkstra, Topological Sort, etc.

---

### 4.3 Edge List

A flat list of all edges `{u, v, weight}`.

```
Graph:  0—1 (w=5), 0—2 (w=3), 1—3 (w=2)

Edges: [(0,1,5), (0,2,3), (1,3,2)]
```

**Pros:**
- O(E) space
- Easy to sort by weight
- Simple iteration over all edges

**Cons:**
- O(E) to find neighbors of a vertex
- O(E) edge existence check

**Use when:** Kruskal's MST, Bellman-Ford, when you need to sort edges.

---

### 4.4 Representation Comparison Table

| Operation              | Adj. Matrix | Adj. List    | Edge List |
|------------------------|-------------|--------------|-----------|
| **Space**              | O(V²)       | O(V + E)     | O(E)      |
| **Edge check (u,v)**   | O(1)        | O(degree(v)) | O(E)      |
| **Get neighbors(v)**   | O(V)        | O(degree(v)) | O(E)      |
| **Add edge**           | O(1)        | O(1)         | O(1)      |
| **Remove edge**        | O(1)        | O(degree(v)) | O(E)      |
| **Iterate all edges**  | O(V²)       | O(V + E)     | O(E)      |
| **Build from scratch** | O(V²)       | O(V + E)     | O(E)      |

---

## 📌 5. Mathematical Properties

### 5.1 Tree Properties (Memorize These!)

For a graph with V vertices to be a **tree**, it must satisfy ALL of:
```
1. Connected
2. Acyclic
3. Has exactly V - 1 edges

Any two of these three imply the third!
```

**Proof sketch:** A connected graph needs at least V-1 edges. Adding any edge to a tree creates exactly one cycle. So connected + V-1 edges → no cycles.

---

### 5.2 Complete Graph Properties

```
Kₙ (complete graph with n vertices):
  - Undirected edges: n(n-1)/2
  - Directed edges:   n(n-1)
  - Each vertex degree: n-1
  - Sum of degrees: n(n-1)  [= 2 × edges, verifies Handshaking Lemma]
```

---

### 5.3 Bipartite Graph Properties

```
A graph is bipartite ⟺ it contains NO odd-length cycle
A bipartite graph is 2-colorable
A tree is always bipartite (trees have no cycles at all)
```

---

## 📌 6. Building Graphs from LeetCode Input

### Pattern 1: Edge List → Adjacency List (Undirected)
```java
// Input: n = number of nodes, edges = [[u1,v1],[u2,v2],...]
static List<List<Integer>> buildGraph(int n, int[][] edges) {
    List<List<Integer>> graph = new ArrayList<>();
    for (int i = 0; i < n; i++) graph.add(new ArrayList<>());
    for (int[] edge : edges) {
        graph.get(edge[0]).add(edge[1]);
        graph.get(edge[1]).add(edge[0]); // remove this line for directed graph
    }
    return graph;
}
```

### Pattern 2: Edge List → Adjacency List (Directed)
```java
static List<List<Integer>> buildDirectedGraph(int n, int[][] edges) {
    List<List<Integer>> graph = new ArrayList<>();
    for (int i = 0; i < n; i++) graph.add(new ArrayList<>());
    for (int[] edge : edges) {
        graph.get(edge[0]).add(edge[1]); // only one direction
    }
    return graph;
}
```

### Pattern 3: Weighted Graph
```java
// Each edge: [u, v, weight]
static List<List<int[]>> buildWeightedGraph(int n, int[][] edges) {
    List<List<int[]>> graph = new ArrayList<>();
    for (int i = 0; i < n; i++) graph.add(new ArrayList<>());
    for (int[] edge : edges) {
        // edge[0]=u, edge[1]=v, edge[2]=weight
        graph.get(edge[0]).add(new int[]{edge[1], edge[2]});
        graph.get(edge[1]).add(new int[]{edge[0], edge[2]}); // undirected
    }
    return graph;
}
```

### Pattern 4: Grid → Graph (Implicit)
```java
// Grid cells are nodes; adjacent cells are edges
// No explicit graph needed — use directions array
int[][] dirs = {{0,1},{0,-1},{1,0},{-1,0}}; // 4-directional
// int[][] dirs = {{0,1},{0,-1},{1,0},{-1,0},{1,1},{1,-1},{-1,1},{-1,-1}}; // 8-directional

// Neighbor check:
for (int[] d : dirs) {
    int nr = r + d[0], nc = c + d[1];
    if (nr >= 0 && nr < rows && nc >= 0 && nc < cols) {
        // valid neighbor at (nr, nc)
    }
}
```

### Pattern 5: Adjacency Matrix Input (LeetCode style)
```java
// isConnected[i][j] = 1 means edge between i and j
static List<List<Integer>> fromMatrix(int[][] isConnected) {
    int n = isConnected.length;
    List<List<Integer>> graph = new ArrayList<>();
    for (int i = 0; i < n; i++) graph.add(new ArrayList<>());
    for (int i = 0; i < n; i++)
        for (int j = i + 1; j < n; j++)
            if (isConnected[i][j] == 1) {
                graph.get(i).add(j);
                graph.get(j).add(i);
            }
    return graph;
}
```

---

## 📌 7. Complete Java Implementation

```java
import java.util.*;

/**
 * Complete Graph Representations in Java
 * Covers: Adjacency Matrix, Adjacency List (weighted/unweighted), Edge List
 * All representations support directed and undirected graphs.
 */
public class GraphRepresentations {

    // ═══════════════════════════════════════════════════════════
    //  ADJACENCY MATRIX
    // ═══════════════════════════════════════════════════════════
    static class AdjacencyMatrix {
        int V;
        int[][] matrix;

        AdjacencyMatrix(int v) {
            V = v;
            matrix = new int[v][v];
        }

        // Add directed edge u → v
        void addDirectedEdge(int u, int v) {
            matrix[u][v] = 1;
        }

        // Add directed weighted edge u → v with given weight
        void addDirectedWeightedEdge(int u, int v, int weight) {
            matrix[u][v] = weight;
        }

        // Add undirected edge u — v
        void addUndirectedEdge(int u, int v) {
            matrix[u][v] = 1;
            matrix[v][u] = 1;
        }

        // Add undirected weighted edge
        void addUndirectedWeightedEdge(int u, int v, int weight) {
            matrix[u][v] = weight;
            matrix[v][u] = weight;
        }

        // Remove edge
        void removeEdge(int u, int v) {
            matrix[u][v] = 0;
        }

        // O(1) edge check
        boolean hasEdge(int u, int v) {
            return matrix[u][v] != 0;
        }

        // O(V) neighbor iteration
        List<Integer> getNeighbors(int u) {
            List<Integer> neighbors = new ArrayList<>();
            for (int v = 0; v < V; v++)
                if (matrix[u][v] != 0) neighbors.add(v);
            return neighbors;
        }

        // Compute degree of vertex u (undirected)
        int degree(int u) {
            int deg = 0;
            for (int v = 0; v < V; v++) if (matrix[u][v] != 0) deg++;
            return deg;
        }

        // Compute in-degree (directed)
        int inDegree(int u) {
            int deg = 0;
            for (int v = 0; v < V; v++) if (matrix[v][u] != 0) deg++;
            return deg;
        }

        // Compute out-degree (directed)
        int outDegree(int u) {
            int deg = 0;
            for (int v = 0; v < V; v++) if (matrix[u][v] != 0) deg++;
            return deg;
        }

        // Transpose the graph (reverse all edges)
        AdjacencyMatrix transpose() {
            AdjacencyMatrix t = new AdjacencyMatrix(V);
            for (int u = 0; u < V; u++)
                for (int v = 0; v < V; v++)
                    t.matrix[v][u] = matrix[u][v];
            return t;
        }

        void print() {
            System.out.println("Adjacency Matrix:");
            System.out.print("   ");
            for (int i = 0; i < V; i++) System.out.printf("%3d", i);
            System.out.println();
            for (int i = 0; i < V; i++) {
                System.out.printf("%3d", i);
                for (int j = 0; j < V; j++) System.out.printf("%3d", matrix[i][j]);
                System.out.println();
            }
        }
    }

    // ═══════════════════════════════════════════════════════════
    //  ADJACENCY LIST (Unweighted)
    // ═══════════════════════════════════════════════════════════
    static class AdjacencyList {
        int V;
        List<List<Integer>> adj;

        AdjacencyList(int v) {
            V = v;
            adj = new ArrayList<>();
            for (int i = 0; i < v; i++) adj.add(new ArrayList<>());
        }

        void addDirectedEdge(int u, int v) {
            adj.get(u).add(v);
        }

        void addUndirectedEdge(int u, int v) {
            adj.get(u).add(v);
            adj.get(v).add(u);
        }

        void removeDirectedEdge(int u, int v) {
            adj.get(u).remove(Integer.valueOf(v));
        }

        void removeUndirectedEdge(int u, int v) {
            adj.get(u).remove(Integer.valueOf(v));
            adj.get(v).remove(Integer.valueOf(u));
        }

        // O(degree(u)) edge check
        boolean hasEdge(int u, int v) {
            return adj.get(u).contains(v);
        }

        List<Integer> getNeighbors(int u) {
            return adj.get(u);
        }

        int degree(int u) {
            return adj.get(u).size();
        }

        // Count total edges (undirected: divide by 2)
        int countEdges(boolean directed) {
            int total = 0;
            for (List<Integer> neighbors : adj) total += neighbors.size();
            return directed ? total : total / 2;
        }

        // Transpose (reverse all directed edges)
        AdjacencyList transpose() {
            AdjacencyList t = new AdjacencyList(V);
            for (int u = 0; u < V; u++)
                for (int v : adj.get(u))
                    t.adj.get(v).add(u);
            return t;
        }

        // Convert to adjacency matrix
        int[][] toMatrix() {
            int[][] matrix = new int[V][V];
            for (int u = 0; u < V; u++)
                for (int v : adj.get(u))
                    matrix[u][v] = 1;
            return matrix;
        }

        void print() {
            System.out.println("Adjacency List:");
            for (int i = 0; i < V; i++)
                System.out.println("  " + i + " → " + adj.get(i));
        }
    }

    // ═══════════════════════════════════════════════════════════
    //  WEIGHTED ADJACENCY LIST
    // ═══════════════════════════════════════════════════════════
    static class WeightedAdjacencyList {
        int V;
        List<List<int[]>> adj; // adj[u] = list of {v, weight}

        WeightedAdjacencyList(int v) {
            V = v;
            adj = new ArrayList<>();
            for (int i = 0; i < v; i++) adj.add(new ArrayList<>());
        }

        void addDirectedEdge(int u, int v, int weight) {
            adj.get(u).add(new int[]{v, weight});
        }

        void addUndirectedEdge(int u, int v, int weight) {
            adj.get(u).add(new int[]{v, weight});
            adj.get(v).add(new int[]{u, weight});
        }

        List<int[]> getNeighbors(int u) {
            return adj.get(u);
        }

        // Get weight of edge u→v, returns -1 if not found
        int getWeight(int u, int v) {
            for (int[] edge : adj.get(u))
                if (edge[0] == v) return edge[1];
            return -1;
        }

        void print() {
            System.out.println("Weighted Adjacency List:");
            for (int i = 0; i < V; i++) {
                System.out.print("  " + i + " → ");
                for (int[] edge : adj.get(i))
                    System.out.print("[" + edge[0] + ", w=" + edge[1] + "] ");
                System.out.println();
            }
        }
    }

    // ═══════════════════════════════════════════════════════════
    //  EDGE LIST
    // ═══════════════════════════════════════════════════════════
    static class EdgeList {
        List<int[]> edges; // each edge: {u, v, weight}

        EdgeList() {
            edges = new ArrayList<>();
        }

        void addEdge(int u, int v, int weight) {
            edges.add(new int[]{u, v, weight});
        }

        void addEdge(int u, int v) {
            edges.add(new int[]{u, v, 1});
        }

        // Sort by weight (ascending) — used in Kruskal's MST
        void sortByWeight() {
            edges.sort((a, b) -> a[2] - b[2]);
        }

        // Sort by weight (descending)
        void sortByWeightDesc() {
            edges.sort((a, b) -> b[2] - a[2]);
        }

        // Sort by source vertex
        void sortBySource() {
            edges.sort((a, b) -> a[0] != b[0] ? a[0] - b[0] : a[1] - b[1]);
        }

        // Convert to adjacency list
        List<List<Integer>> toAdjList(int n) {
            List<List<Integer>> graph = new ArrayList<>();
            for (int i = 0; i < n; i++) graph.add(new ArrayList<>());
            for (int[] e : edges) {
                graph.get(e[0]).add(e[1]);
                graph.get(e[1]).add(e[0]); // undirected
            }
            return graph;
        }

        void print() {
            System.out.println("Edge List:");
            for (int[] e : edges)
                System.out.println("  " + e[0] + " ──(" + e[2] + ")──► " + e[1]);
        }
    }

    // ═══════════════════════════════════════════════════════════
    //  UTILITY METHODS
    // ═══════════════════════════════════════════════════════════

    // Compute in-degrees for all vertices (directed graph)
    static int[] computeInDegrees(int n, List<List<Integer>> graph) {
        int[] inDegree = new int[n];
        for (int u = 0; u < n; u++)
            for (int v : graph.get(u))
                inDegree[v]++;
        return inDegree;
    }

    // Compute out-degrees for all vertices
    static int[] computeOutDegrees(int n, List<List<Integer>> graph) {
        int[] outDegree = new int[n];
        for (int u = 0; u < n; u++)
            outDegree[u] = graph.get(u).size();
        return outDegree;
    }

    // Verify Handshaking Lemma: sum of degrees = 2 * edges
    static void verifyHandshakingLemma(int n, List<List<Integer>> graph) {
        int sumDegrees = 0;
        int edges = 0;
        for (int u = 0; u < n; u++) {
            sumDegrees += graph.get(u).size();
            edges += graph.get(u).size();
        }
        edges /= 2; // undirected: each edge counted twice
        System.out.println("Sum of degrees: " + sumDegrees);
        System.out.println("2 × edges: " + (2 * edges));
        System.out.println("Handshaking Lemma holds: " + (sumDegrees == 2 * edges));
    }

    // Check if graph is a valid tree (connected + V-1 edges)
    static boolean isTree(int n, int[][] edges) {
        if (edges.length != n - 1) return false; // must have exactly V-1 edges
        // Check connectivity using BFS
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) graph.add(new ArrayList<>());
        for (int[] e : edges) {
            graph.get(e[0]).add(e[1]);
            graph.get(e[1]).add(e[0]);
        }
        boolean[] visited = new boolean[n];
        Queue<Integer> queue = new LinkedList<>();
        visited[0] = true;
        queue.offer(0);
        int count = 1;
        while (!queue.isEmpty()) {
            int node = queue.poll();
            for (int neighbor : graph.get(node))
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    queue.offer(neighbor);
                    count++;
                }
        }
        return count == n; // all vertices reachable = connected
    }

    // ═══════════════════════════════════════════════════════════
    //  MAIN — DEMO ALL REPRESENTATIONS
    // ═══════════════════════════════════════════════════════════
    public static void main(String[] args) {

        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║     GRAPH REPRESENTATIONS DEMO       ║");
        System.out.println("╚══════════════════════════════════════╝\n");

        // ── Adjacency Matrix ──────────────────────────────────
        System.out.println("═══ ADJACENCY MATRIX ═══");
        AdjacencyMatrix am = new AdjacencyMatrix(5);
        am.addUndirectedEdge(0, 1);
        am.addUndirectedEdge(0, 2);
        am.addUndirectedEdge(1, 3);
        am.addUndirectedEdge(2, 4);
        am.addUndirectedEdge(3, 4);
        am.print();
        System.out.println("Edge 0-1 exists: " + am.hasEdge(0, 1));
        System.out.println("Edge 0-3 exists: " + am.hasEdge(0, 3));
        System.out.println("Degree of 0: " + am.degree(0));
        System.out.println("Neighbors of 0: " + am.getNeighbors(0));

        // ── Adjacency List (Unweighted) ───────────────────────
        System.out.println("\n═══ ADJACENCY LIST (Unweighted) ═══");
        AdjacencyList al = new AdjacencyList(5);
        al.addUndirectedEdge(0, 1);
        al.addUndirectedEdge(0, 2);
        al.addUndirectedEdge(1, 3);
        al.addUndirectedEdge(2, 4);
        al.addUndirectedEdge(3, 4);
        al.print();
        System.out.println("Total edges: " + al.countEdges(false));
        System.out.println("Degree of 0: " + al.degree(0));

        // ── Weighted Adjacency List ───────────────────────────
        System.out.println("\n═══ WEIGHTED ADJACENCY LIST ═══");
        WeightedAdjacencyList wal = new WeightedAdjacencyList(5);
        wal.addDirectedEdge(0, 1, 10);
        wal.addDirectedEdge(0, 2, 3);
        wal.addDirectedEdge(1, 3, 2);
        wal.addDirectedEdge(2, 1, 4);
        wal.addDirectedEdge(2, 3, 8);
        wal.addDirectedEdge(3, 4, 5);
        wal.print();
        System.out.println("Weight of 0→1: " + wal.getWeight(0, 1));
        System.out.println("Weight of 0→3: " + wal.getWeight(0, 3)); // -1 (no direct edge)

        // ── Edge List ─────────────────────────────────────────
        System.out.println("\n═══ EDGE LIST ═══");
        EdgeList el = new EdgeList();
        el.addEdge(0, 1, 10);
        el.addEdge(0, 2, 3);
        el.addEdge(1, 3, 2);
        el.addEdge(2, 3, 8);
        el.addEdge(3, 4, 5);
        System.out.println("Before sorting:");
        el.print();
        el.sortByWeight();
        System.out.println("After sorting by weight:");
        el.print();

        // ── Utility Methods ───────────────────────────────────
        System.out.println("\n═══ UTILITY METHODS ═══");
        List<List<Integer>> g = al.adj;
        verifyHandshakingLemma(5, g);

        System.out.println("\nIs tree (4 nodes, 3 edges, connected)?");
        System.out.println(isTree(4, new int[][]{{0,1},{1,2},{2,3}})); // true
        System.out.println("Is tree (4 nodes, 3 edges, with cycle)?");
        System.out.println(isTree(4, new int[][]{{0,1},{1,2},{2,0}})); // false (only 3 nodes reachable)

        // ── In-degree / Out-degree ────────────────────────────
        System.out.println("\n═══ IN-DEGREE / OUT-DEGREE (Directed) ═══");
        List<List<Integer>> directed = new ArrayList<>();
        for (int i = 0; i < 4; i++) directed.add(new ArrayList<>());
        directed.get(0).add(1); directed.get(0).add(2);
        directed.get(1).add(3); directed.get(2).add(3);
        System.out.println("In-degrees:  " + Arrays.toString(computeInDegrees(4, directed)));
        System.out.println("Out-degrees: " + Arrays.toString(computeOutDegrees(4, directed)));
    }
}
```

---

## 📌 8. Complexity Summary

| Representation       | Space   | Build Time | Neighbor Iter  | Edge Check   | Best For              |
|----------------------|---------|------------|----------------|--------------|-----------------------|
| **Adjacency Matrix** | O(V²)   | O(V²)      | O(V)           | O(1)         | Dense graphs, Floyd-W |
| **Adjacency List**   | O(V+E)  | O(V+E)     | O(degree)      | O(degree)    | Almost everything     |
| **Edge List**        | O(E)    | O(E)       | O(E)           | O(E)         | Kruskal's, Bellman-F  |

---

## 📌 9. LeetCode Problems

| #           | Problem                                        | Difficulty | Key Concept                    |
|-------------|------------------------------------------------|------------|--------------------------------|
| LC 1791     | Find Center of Star Graph                      | Easy       | Degree analysis                |
| LC 997      | Find the Town Judge                            | Easy       | In/out-degree                  |
| LC 1615     | Maximal Network Rank                           | Medium     | Degree + adjacency check       |
| LC 2685     | Count the Number of Complete Components        | Medium     | Subgraph property              |
| LC 1557     | Minimum Number of Vertices to Reach All Nodes  | Medium     | In-degree = 0 nodes            |
| LC 1971     | Find if Path Exists in Graph                   | Easy       | Basic graph traversal          |

### 💡 Hints

**LC 997 (Town Judge):**
```
The judge has:
  - in-degree  = n-1  (everyone trusts them)
  - out-degree = 0    (trusts nobody)

Solution: Find vertex where inDegree[v] == n-1 && outDegree[v] == 0
Time: O(V + E), Space: O(V)
```

**LC 1557 (Min Vertices to Reach All Nodes):**
```
In a DAG, any node with in-degree = 0 cannot be reached from any other node.
So the answer is: all nodes with in-degree = 0.
```

**LC 1615 (Maximal Network Rank):**
```
For each pair (i, j): rank = degree[i] + degree[j] - (edge(i,j) exists ? 1 : 0)
Answer = max rank over all pairs.
```

---

## 📌 10. Interview Tips

```
✅ Always clarify FIRST:
   1. Directed or undirected?
   2. Weighted or unweighted?
   3. Can there be cycles?
   4. Can the graph be disconnected?
   5. 0-indexed or 1-indexed?
   6. Self-loops or multi-edges?

✅ Default representation: Adjacency List
   - Unless problem needs O(1) edge check → Matrix
   - Unless problem needs sorted edges → Edge List

✅ Handle disconnected graphs:
   - Always loop through ALL vertices, not just from node 0

✅ Know the Handshaking Lemma:
   - Sum of degrees = 2 × edges
   - Helps verify your graph construction

✅ Tree vs Graph:
   - Tree = connected + acyclic + V-1 edges
   - Any two of these three imply the third

✅ Common mistake: Forgetting to handle isolated vertices
   - A vertex with no edges still exists in the graph
   - Always initialize adjacency list for ALL n vertices
```

---

## 🔗 What's Next?

```
01_Graph_Foundations  ← YOU ARE HERE
02_BFS                ← Shortest path in unweighted graphs
03_DFS                ← Deep exploration, backtracking
04_Cycle_Detection    ← Detect cycles in directed/undirected
05_Connected_Components ← Count and label components
06_Bipartite_Check    ← 2-coloring, odd cycle detection
07_Topological_Sort   ← Ordering in DAGs
08_Union_Find_DSU     ← Dynamic connectivity
09_Dijkstra_Algorithm ← Shortest path in weighted graphs
```
