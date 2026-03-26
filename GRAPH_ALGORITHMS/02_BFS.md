
# ╔══════════════════════════════════════════════════════════════╗
# ║           02 — BREADTH-FIRST SEARCH (BFS)                   ║
# ║       Complete Theory + In-Depth Java Implementation        ║
# ╚══════════════════════════════════════════════════════════════╝

---

## 🧭 What You Will Learn in This File

```
1. What is BFS? (Core Intuition)
2. Why BFS Guarantees Shortest Path
3. The BFS Algorithm (Step-by-Step)
4. Detailed Trace with State Tables
5. BFS on Graphs (Complete Java)
6. BFS on Grids (4-dir & 8-dir)
7. Multi-Source BFS
8. Level-by-Level BFS (3 Methods)
9. BFS Shortest Path + Path Reconstruction
10. BFS vs DFS Comparison
11. Common Mistakes
12. LeetCode Problems + Hints
```

---

## 📌 1. What is BFS?

BFS explores vertices in **order of their distance** (number of hops) from the source.
It visits all nodes at distance 1 first, then distance 2, then distance 3, etc.

Think of it like **ripples on water** — the wave expands outward uniformly.

```
Graph:
        0
       / \
      1   2
     / \   \
    3   4   5

BFS from 0:
  Level 0: [0]
  Level 1: [1, 2]
  Level 2: [3, 4, 5]

Visit order: 0 → 1 → 2 → 3 → 4 → 5
```

**Data Structure Used:** Queue (FIFO — First In, First Out)

> 💡 **Why Queue?** FIFO ensures we process nodes in the order they were discovered.
> Nodes discovered earlier (closer to source) are processed before nodes discovered later (farther).

---

## 📌 2. Why BFS Guarantees Shortest Path

> 🔑 **The Fundamental Theorem of BFS:**
> In an **unweighted** graph, the FIRST time BFS reaches a node, it has found the **shortest path** to it.

**Proof by contradiction:**
```
Suppose BFS finds path P from s to v with length d.
Suppose there exists a shorter path P' with length d' < d.

P' must go through some node u at distance d'-1 from s.
But BFS processes nodes in non-decreasing order of distance.
So u would have been processed before v.
When u was processed, BFS would have discovered v at distance d'-1+1 = d'.
But d' < d — contradiction! BFS already found v at distance d.

Therefore, BFS always finds the shortest path. ✓
```

**This only works for unweighted graphs.** For weighted graphs, use Dijkstra's algorithm.

---

## 📌 3. The BFS Algorithm

### Pseudocode
```
BFS(graph, start):
  1. Create visited[] array, set all to false
  2. Create dist[] array, set all to -1 (or ∞)
  3. Mark visited[start] = true, dist[start] = 0
  4. Enqueue start
  5. While queue is NOT empty:
     a. Dequeue node u
     b. Process u (print, count, etc.)
     c. For each neighbor v of u:
        - If NOT visited[v]:
            visited[v] = true
            dist[v] = dist[u] + 1
            Enqueue v
  6. Return dist[]
```

> ⚠️ **CRITICAL RULE:** Mark visited **WHEN ENQUEUING**, not when dequeuing!
>
> If you mark visited when dequeuing, the same node can be enqueued multiple times,
> causing O(V²) time instead of O(V+E).

---

## 📌 4. Detailed Trace with State Tables

### Graph
```
Edges: 0—1, 0—2, 1—3, 1—4, 2—5, 2—6

        0
       / \
      1   2
     / \ / \
    3  4 5  6
```

### BFS from node 0

| Step | Dequeue | Queue After | Visited | dist[] |
|------|---------|-------------|---------|--------|
| Init | —       | [0]         | {0}     | [0,-1,-1,-1,-1,-1,-1] |
| 1    | 0       | [1,2]       | {0,1,2} | [0,1,1,-1,-1,-1,-1] |
| 2    | 1       | [2,3,4]     | {0,1,2,3,4} | [0,1,1,2,2,-1,-1] |
| 3    | 2       | [3,4,5,6]   | {0,1,2,3,4,5,6} | [0,1,1,2,2,2,2] |
| 4    | 3       | [4,5,6]     | same    | same |
| 5    | 4       | [5,6]       | same    | same |
| 6    | 5       | [6]         | same    | same |
| 7    | 6       | []          | same    | same |

**Final distances from 0:** `[0, 1, 1, 2, 2, 2, 2]`

---

## 📌 5. Time & Space Complexity

```
Time:  O(V + E)
  - Every vertex is enqueued and dequeued exactly ONCE → O(V)
  - Every edge is examined exactly ONCE (or twice for undirected) → O(E)
  - Total: O(V + E)

Space: O(V)
  - visited[] array: O(V)
  - dist[] array: O(V)
  - Queue: at most O(V) elements at any time
  - Total: O(V)
```

**Why O(V) for the queue?**
In the worst case (star graph), all V-1 neighbors of the center are enqueued at once.
But each node is enqueued at most once, so total queue operations = O(V).

---

## 📌 6. BFS on a Graph — Complete Java

```java
import java.util.*;

public class BFS {

    // ─────────────────────────────────────────────────────────
    //  BASIC BFS TRAVERSAL
    // ─────────────────────────────────────────────────────────
    static void bfsTraversal(List<List<Integer>> graph, int start) {
        int n = graph.size();
        boolean[] visited = new boolean[n];
        Queue<Integer> queue = new LinkedList<>();

        // Mark visited BEFORE enqueuing (critical!)
        visited[start] = true;
        queue.offer(start);

        System.out.print("BFS order: ");
        while (!queue.isEmpty()) {
            int node = queue.poll();
            System.out.print(node + " ");

            for (int neighbor : graph.get(node)) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;  // mark here, not after dequeue
                    queue.offer(neighbor);
                }
            }
        }
        System.out.println();
    }

    // ─────────────────────────────────────────────────────────
    //  BFS SHORTEST DISTANCES FROM SOURCE
    // ─────────────────────────────────────────────────────────
    static int[] bfsDistances(List<List<Integer>> graph, int source) {
        int n = graph.size();
        int[] dist = new int[n];
        Arrays.fill(dist, -1);  // -1 means unreachable
        dist[source] = 0;

        Queue<Integer> queue = new LinkedList<>();
        queue.offer(source);

        while (!queue.isEmpty()) {
            int node = queue.poll();
            for (int neighbor : graph.get(node)) {
                if (dist[neighbor] == -1) {
                    dist[neighbor] = dist[node] + 1;
                    queue.offer(neighbor);
                }
            }
        }
        return dist;
    }

    // ─────────────────────────────────────────────────────────
    //  BFS SHORTEST PATH (single source to single destination)
    // ─────────────────────────────────────────────────────────
    static int shortestPath(List<List<Integer>> graph, int src, int dst) {
        if (src == dst) return 0;
        int n = graph.size();
        int[] dist = new int[n];
        Arrays.fill(dist, -1);
        dist[src] = 0;

        Queue<Integer> queue = new LinkedList<>();
        queue.offer(src);

        while (!queue.isEmpty()) {
            int node = queue.poll();
            for (int neighbor : graph.get(node)) {
                if (dist[neighbor] == -1) {
                    dist[neighbor] = dist[node] + 1;
                    if (neighbor == dst) return dist[neighbor]; // early exit
                    queue.offer(neighbor);
                }
            }
        }
        return -1; // unreachable
    }

    // ─────────────────────────────────────────────────────────
    //  BFS WITH PATH RECONSTRUCTION
    // ─────────────────────────────────────────────────────────
    static List<Integer> bfsWithPath(List<List<Integer>> graph, int src, int dst) {
        int n = graph.size();
        int[] parent = new int[n];
        Arrays.fill(parent, -1);
        boolean[] visited = new boolean[n];

        Queue<Integer> queue = new LinkedList<>();
        visited[src] = true;
        queue.offer(src);

        while (!queue.isEmpty()) {
            int node = queue.poll();
            if (node == dst) break;
            for (int neighbor : graph.get(node)) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    parent[neighbor] = node;  // record how we got here
                    queue.offer(neighbor);
                }
            }
        }

        // Reconstruct path by following parent pointers backward
        if (!visited[dst]) return Collections.emptyList(); // no path

        List<Integer> path = new ArrayList<>();
        for (int cur = dst; cur != -1; cur = parent[cur])
            path.add(cur);
        Collections.reverse(path);
        return path;
    }

    // ─────────────────────────────────────────────────────────
    //  BFS LEVEL-BY-LEVEL (Method 1: size-based)
    // ─────────────────────────────────────────────────────────
    static void bfsLevelOrder(List<List<Integer>> graph, int start) {
        boolean[] visited = new boolean[graph.size()];
        Queue<Integer> queue = new LinkedList<>();
        visited[start] = true;
        queue.offer(start);
        int level = 0;

        while (!queue.isEmpty()) {
            int size = queue.size(); // number of nodes at current level
            System.out.print("Level " + level + ": ");
            for (int i = 0; i < size; i++) {
                int node = queue.poll();
                System.out.print(node + " ");
                for (int neighbor : graph.get(node))
                    if (!visited[neighbor]) {
                        visited[neighbor] = true;
                        queue.offer(neighbor);
                    }
            }
            System.out.println();
            level++;
        }
    }

    // ─────────────────────────────────────────────────────────
    //  MULTI-SOURCE BFS
    //  All sources start at distance 0 simultaneously
    // ─────────────────────────────────────────────────────────
    static int[] multiSourceBFS(int n, List<List<Integer>> graph, List<Integer> sources) {
        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        Queue<Integer> queue = new LinkedList<>();

        // Add ALL sources to queue at distance 0
        for (int src : sources) {
            dist[src] = 0;
            queue.offer(src);
        }

        while (!queue.isEmpty()) {
            int node = queue.poll();
            for (int neighbor : graph.get(node)) {
                if (dist[neighbor] == Integer.MAX_VALUE) {
                    dist[neighbor] = dist[node] + 1;
                    queue.offer(neighbor);
                }
            }
        }
        return dist;
    }

    // ─────────────────────────────────────────────────────────
    //  BFS FOR DISCONNECTED GRAPHS
    //  Visits ALL components
    // ─────────────────────────────────────────────────────────
    static int bfsAllComponents(List<List<Integer>> graph) {
        int n = graph.size();
        boolean[] visited = new boolean[n];
        int components = 0;

        for (int start = 0; start < n; start++) {
            if (!visited[start]) {
                components++;
                Queue<Integer> queue = new LinkedList<>();
                visited[start] = true;
                queue.offer(start);
                while (!queue.isEmpty()) {
                    int node = queue.poll();
                    for (int neighbor : graph.get(node))
                        if (!visited[neighbor]) {
                            visited[neighbor] = true;
                            queue.offer(neighbor);
                        }
                }
            }
        }
        return components;
    }

    // ─────────────────────────────────────────────────────────
    //  MAIN DEMO
    // ─────────────────────────────────────────────────────────
    public static void main(String[] args) {
        // Build graph: 0—1, 0—2, 1—3, 1—4, 2—5, 2—6
        int n = 7;
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) graph.add(new ArrayList<>());
        int[][] edges = {{0,1},{0,2},{1,3},{1,4},{2,5},{2,6}};
        for (int[] e : edges) {
            graph.get(e[0]).add(e[1]);
            graph.get(e[1]).add(e[0]);
        }

        System.out.println("═══ Basic BFS Traversal ═══");
        bfsTraversal(graph, 0);

        System.out.println("\n═══ Shortest Distances from 0 ═══");
        System.out.println(Arrays.toString(bfsDistances(graph, 0)));

        System.out.println("\n═══ Shortest Path 0 → 6 ═══");
        System.out.println("Distance: " + shortestPath(graph, 0, 6));
        System.out.println("Path: " + bfsWithPath(graph, 0, 6));

        System.out.println("\n═══ Level-by-Level BFS ═══");
        bfsLevelOrder(graph, 0);

        System.out.println("\n═══ Multi-Source BFS from {0, 6} ═══");
        System.out.println(Arrays.toString(multiSourceBFS(n, graph, Arrays.asList(0, 6))));

        System.out.println("\n═══ Connected Components ═══");
        // Add disconnected component
        List<List<Integer>> g2 = new ArrayList<>();
        for (int i = 0; i < 9; i++) g2.add(new ArrayList<>());
        g2.get(0).add(1); g2.get(1).add(0);
        g2.get(2).add(3); g2.get(3).add(2);
        // nodes 4,5,6,7,8 are isolated
        System.out.println("Components: " + bfsAllComponents(g2));
    }
}
```

---

## 📌 7. BFS on a Grid

Grids are the most common BFS problem type in LeetCode. Each cell is a node; adjacent cells are edges.

### 4-Directional BFS (Standard)
```java
import java.util.*;

public class GridBFS {

    // Standard 4-directional movement
    static final int[][] DIRS_4 = {{0,1},{0,-1},{1,0},{-1,0}};

    // 8-directional movement (includes diagonals)
    static final int[][] DIRS_8 = {{0,1},{0,-1},{1,0},{-1,0},{1,1},{1,-1},{-1,1},{-1,-1}};

    // ─────────────────────────────────────────────────────────
    //  BASIC GRID BFS — Count reachable cells
    // ─────────────────────────────────────────────────────────
    static int bfsGrid(int[][] grid, int startR, int startC) {
        int rows = grid.length, cols = grid[0].length;
        boolean[][] visited = new boolean[rows][cols];
        Queue<int[]> queue = new LinkedList<>();

        visited[startR][startC] = true;
        queue.offer(new int[]{startR, startC});
        int count = 0;

        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            int r = curr[0], c = curr[1];
            count++;

            for (int[] d : DIRS_4) {
                int nr = r + d[0], nc = c + d[1];
                // Boundary check + condition check + visited check
                if (nr >= 0 && nr < rows && nc >= 0 && nc < cols
                        && !visited[nr][nc] && grid[nr][nc] == 1) {
                    visited[nr][nc] = true;
                    queue.offer(new int[]{nr, nc});
                }
            }
        }
        return count;
    }

    // ─────────────────────────────────────────────────────────
    //  GRID BFS WITH DISTANCE — Shortest path on grid
    // ─────────────────────────────────────────────────────────
    static int shortestPathGrid(int[][] grid, int sr, int sc, int er, int ec) {
        int rows = grid.length, cols = grid[0].length;
        if (grid[sr][sc] == 0 || grid[er][ec] == 0) return -1;

        int[][] dist = new int[rows][cols];
        for (int[] row : dist) Arrays.fill(row, -1);
        dist[sr][sc] = 0;

        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{sr, sc});

        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            int r = curr[0], c = curr[1];

            if (r == er && c == ec) return dist[r][c];

            for (int[] d : DIRS_4) {
                int nr = r + d[0], nc = c + d[1];
                if (nr >= 0 && nr < rows && nc >= 0 && nc < cols
                        && dist[nr][nc] == -1 && grid[nr][nc] == 1) {
                    dist[nr][nc] = dist[r][c] + 1;
                    queue.offer(new int[]{nr, nc});
                }
            }
        }
        return -1; // unreachable
    }

    // ─────────────────────────────────────────────────────────
    //  NUMBER OF ISLANDS (LC 200) — Count connected components
    // ─────────────────────────────────────────────────────────
    static int numberOfIslands(char[][] grid) {
        int rows = grid.length, cols = grid[0].length;
        boolean[][] visited = new boolean[rows][cols];
        int count = 0;

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (grid[r][c] == '1' && !visited[r][c]) {
                    count++;
                    // BFS to mark entire island as visited
                    Queue<int[]> queue = new LinkedList<>();
                    visited[r][c] = true;
                    queue.offer(new int[]{r, c});
                    while (!queue.isEmpty()) {
                        int[] curr = queue.poll();
                        for (int[] d : DIRS_4) {
                            int nr = curr[0] + d[0], nc = curr[1] + d[1];
                            if (nr >= 0 && nr < rows && nc >= 0 && nc < cols
                                    && grid[nr][nc] == '1' && !visited[nr][nc]) {
                                visited[nr][nc] = true;
                                queue.offer(new int[]{nr, nc});
                            }
                        }
                    }
                }
            }
        }
        return count;
    }

    // ─────────────────────────────────────────────────────────
    //  ROTTING ORANGES (LC 994) — Multi-source BFS
    // ─────────────────────────────────────────────────────────
    static int rottingOranges(int[][] grid) {
        int rows = grid.length, cols = grid[0].length;
        Queue<int[]> queue = new LinkedList<>();
        int freshCount = 0;

        // Add all initially rotten oranges as sources
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (grid[r][c] == 2) queue.offer(new int[]{r, c});
                else if (grid[r][c] == 1) freshCount++;
            }
        }

        if (freshCount == 0) return 0; // no fresh oranges

        int minutes = 0;
        while (!queue.isEmpty() && freshCount > 0) {
            int size = queue.size();
            minutes++;
            for (int i = 0; i < size; i++) {
                int[] curr = queue.poll();
                for (int[] d : DIRS_4) {
                    int nr = curr[0] + d[0], nc = curr[1] + d[1];
                    if (nr >= 0 && nr < rows && nc >= 0 && nc < cols && grid[nr][nc] == 1) {
                        grid[nr][nc] = 2; // rot it
                        freshCount--;
                        queue.offer(new int[]{nr, nc});
                    }
                }
            }
        }
        return freshCount == 0 ? minutes : -1;
    }

    // ─────────────────────────────────────────────────────────
    //  WALLS AND GATES (LC 286) — Multi-source BFS from gates
    // ─────────────────────────────────────────────────────────
    static void wallsAndGates(int[][] rooms) {
        int rows = rooms.length, cols = rooms[0].length;
        final int GATE = 0, WALL = -1, EMPTY = Integer.MAX_VALUE;
        Queue<int[]> queue = new LinkedList<>();

        // Add all gates as sources
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++)
                if (rooms[r][c] == GATE) queue.offer(new int[]{r, c});

        // BFS from all gates simultaneously
        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            int r = curr[0], c = curr[1];
            for (int[] d : DIRS_4) {
                int nr = r + d[0], nc = c + d[1];
                if (nr >= 0 && nr < rows && nc >= 0 && nc < cols
                        && rooms[nr][nc] == EMPTY) {
                    rooms[nr][nc] = rooms[r][c] + 1;
                    queue.offer(new int[]{nr, nc});
                }
            }
        }
    }

    // ─────────────────────────────────────────────────────────
    //  SHORTEST PATH IN BINARY MATRIX (LC 1091)
    // ─────────────────────────────────────────────────────────
    static int shortestPathBinaryMatrix(int[][] grid) {
        int n = grid.length;
        if (grid[0][0] == 1 || grid[n-1][n-1] == 1) return -1;
        if (n == 1) return 1;

        Queue<int[]> queue = new LinkedList<>();
        grid[0][0] = 1; // mark visited by setting to 1
        queue.offer(new int[]{0, 0, 1}); // {row, col, distance}

        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            int r = curr[0], c = curr[1], dist = curr[2];

            for (int[] d : DIRS_8) {
                int nr = r + d[0], nc = c + d[1];
                if (nr >= 0 && nr < n && nc >= 0 && nc < n && grid[nr][nc] == 0) {
                    if (nr == n-1 && nc == n-1) return dist + 1;
                    grid[nr][nc] = 1; // mark visited
                    queue.offer(new int[]{nr, nc, dist + 1});
                }
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        System.out.println("═══ Number of Islands ═══");
        char[][] grid = {
            {'1','1','0','0','0'},
            {'1','1','0','0','0'},
            {'0','0','1','0','0'},
            {'0','0','0','1','1'}
        };
        System.out.println("Islands: " + numberOfIslands(grid)); // 3

        System.out.println("\n═══ Rotting Oranges ═══");
        int[][] oranges = {{2,1,1},{1,1,0},{0,1,1}};
        System.out.println("Minutes: " + rottingOranges(oranges)); // 4

        System.out.println("\n═══ Shortest Path in Binary Matrix ═══");
        int[][] binaryGrid = {{0,0,0},{1,1,0},{1,1,0}};
        System.out.println("Path length: " + shortestPathBinaryMatrix(binaryGrid)); // 4
    }
}
```

---

## 📌 8. Multi-Source BFS — Deep Dive

Multi-source BFS is used when you have **multiple starting points** and want to find the minimum distance from **any** source to each node.

### Key Insight
```
Instead of running BFS from each source separately (O(S × (V+E))),
add ALL sources to the queue at the start with distance 0.
BFS then spreads from all sources simultaneously.
Time: O(V + E) — same as single-source BFS!
```

### When to Use Multi-Source BFS
```
✅ "Minimum distance to nearest X" problems
✅ "Spread from multiple starting points" problems
✅ Rotting Oranges, Walls and Gates, 01 Matrix
✅ Any problem where multiple cells/nodes are "sources"
```

### Template
```java
// Multi-source BFS template
Queue<int[]> queue = new LinkedList<>();
boolean[][] visited = new boolean[rows][cols];

// Step 1: Add ALL sources
for (int r = 0; r < rows; r++)
    for (int c = 0; c < cols; c++)
        if (isSource(grid[r][c])) {
            queue.offer(new int[]{r, c});
            visited[r][c] = true;
            dist[r][c] = 0;
        }

// Step 2: Standard BFS
while (!queue.isEmpty()) {
    int[] curr = queue.poll();
    for (int[] d : DIRS_4) {
        int nr = curr[0] + d[0], nc = curr[1] + d[1];
        if (inBounds(nr, nc) && !visited[nr][nc] && isValid(grid[nr][nc])) {
            visited[nr][nc] = true;
            dist[nr][nc] = dist[curr[0]][curr[1]] + 1;
            queue.offer(new int[]{nr, nc});
        }
    }
}
```

---

## 📌 9. Level-by-Level BFS — 3 Methods

### Method 1: Size-Based (Most Common) ⭐
```java
int level = 0;
while (!queue.isEmpty()) {
    int size = queue.size(); // snapshot of current level size
    for (int i = 0; i < size; i++) {
        int node = queue.poll();
        // process node
        for (int neighbor : graph.get(node))
            if (!visited[neighbor]) {
                visited[neighbor] = true;
                queue.offer(neighbor);
            }
    }
    level++;
}
```

### Method 2: Null Sentinel
```java
queue.offer(start);
queue.offer(null); // null marks end of level
int level = 0;
while (!queue.isEmpty()) {
    Integer node = queue.poll();
    if (node == null) {
        level++;
        if (!queue.isEmpty()) queue.offer(null); // add sentinel for next level
    } else {
        // process node
        for (int neighbor : graph.get(node))
            if (!visited[neighbor]) {
                visited[neighbor] = true;
                queue.offer(neighbor);
            }
    }
}
```

### Method 3: Store Distance in Queue
```java
queue.offer(new int[]{start, 0}); // {node, distance}
while (!queue.isEmpty()) {
    int[] curr = queue.poll();
    int node = curr[0], dist = curr[1];
    // process node at distance dist
    for (int neighbor : graph.get(node))
        if (!visited[neighbor]) {
            visited[neighbor] = true;
            queue.offer(new int[]{neighbor, dist + 1});
        }
}
```

> 💡 **Method 1 is preferred** — clean, no extra space, easy to understand.

---

## 📌 10. BFS on State Graphs

Sometimes the "graph" is implicit — nodes are **states** and edges are **transitions**.

### Word Ladder (LC 127)
```java
// Each word is a node; two words are connected if they differ by exactly 1 letter
static int wordLadder(String beginWord, String endWord, List<String> wordList) {
    Set<String> wordSet = new HashSet<>(wordList);
    if (!wordSet.contains(endWord)) return 0;

    Queue<String> queue = new LinkedList<>();
    queue.offer(beginWord);
    int level = 1;

    while (!queue.isEmpty()) {
        int size = queue.size();
        for (int i = 0; i < size; i++) {
            String word = queue.poll();
            char[] chars = word.toCharArray();
            for (int j = 0; j < chars.length; j++) {
                char original = chars[j];
                for (char c = 'a'; c <= 'z'; c++) {
                    if (c == original) continue;
                    chars[j] = c;
                    String newWord = new String(chars);
                    if (newWord.equals(endWord)) return level + 1;
                    if (wordSet.contains(newWord)) {
                        wordSet.remove(newWord); // mark visited by removing
                        queue.offer(newWord);
                    }
                    chars[j] = original; // restore
                }
            }
        }
        level++;
    }
    return 0;
}
```

### Open the Lock (LC 752)
```java
// State = 4-digit combination; transitions = turning one wheel ±1
static int openLock(String[] deadends, String target) {
    Set<String> dead = new HashSet<>(Arrays.asList(deadends));
    if (dead.contains("0000")) return -1;
    if (target.equals("0000")) return 0;

    Queue<String> queue = new LinkedList<>();
    Set<String> visited = new HashSet<>();
    queue.offer("0000");
    visited.add("0000");
    int steps = 0;

    while (!queue.isEmpty()) {
        int size = queue.size();
        steps++;
        for (int i = 0; i < size; i++) {
            String curr = queue.poll();
            for (int j = 0; j < 4; j++) {
                for (int delta : new int[]{1, -1}) {
                    char[] arr = curr.toCharArray();
                    arr[j] = (char)((arr[j] - '0' + delta + 10) % 10 + '0');
                    String next = new String(arr);
                    if (next.equals(target)) return steps;
                    if (!visited.contains(next) && !dead.contains(next)) {
                        visited.add(next);
                        queue.offer(next);
                    }
                }
            }
        }
    }
    return -1;
}
```

---

## 📌 11. BFS vs DFS — When to Choose

| Situation                          | Use BFS              | Use DFS              |
|------------------------------------|----------------------|----------------------|
| **Shortest path (unweighted)**     | ✅ YES               | ❌ No                |
| **Minimum steps/hops**             | ✅ YES               | ❌ No                |
| **Level-order traversal**          | ✅ YES               | ❌ No                |
| **Check if path exists**           | Either               | Either               |
| **Cycle detection**                | Either               | DFS preferred        |
| **Topological sort**               | Either (Kahn's)      | ✅ YES               |
| **Finding ALL paths**              | ❌ No                | ✅ YES               |
| **Exhaustive search/backtracking** | ❌ No                | ✅ YES               |
| **Memory (wide/bushy graph)**      | ❌ BAD (huge queue)  | ✅ Better            |
| **Memory (deep/linear graph)**     | ✅ Better            | ❌ BAD (stack overflow) |

> 🔑 **Rule of thumb:**
> - Need **CLOSEST / SHORTEST / MINIMUM** → BFS
> - Need **ALL paths / ordering / exhaustive** → DFS

---

## 📌 12. Common Mistakes in BFS

```
❌ Mistake 1: Marking visited AFTER dequeuing (not before enqueuing)
   → Same node gets enqueued multiple times → O(V²) time
   Fix: visited[neighbor] = true BEFORE queue.offer(neighbor)

❌ Mistake 2: Not handling disconnected graphs
   → Only visits one component
   Fix: Outer loop over all vertices

❌ Mistake 3: Forgetting boundary checks in grid BFS
   → ArrayIndexOutOfBoundsException
   Fix: Always check nr >= 0 && nr < rows && nc >= 0 && nc < cols

❌ Mistake 4: Using DFS when BFS is needed
   → DFS does NOT guarantee shortest path
   Fix: If problem asks for "minimum steps/hops", use BFS

❌ Mistake 5: Integer overflow in distance computation
   → dist[node] + weight overflows if dist[node] = Integer.MAX_VALUE
   Fix: Use (int)1e9 instead of Integer.MAX_VALUE, or check before adding

❌ Mistake 6: Modifying the grid while iterating (in multi-source BFS)
   → Can cause incorrect results
   Fix: Use a separate visited array, or mark before adding to queue
```

---

## 📌 13. LeetCode Problems

| #       | Problem                          | Difficulty | Key BFS Technique              |
|---------|----------------------------------|------------|--------------------------------|
| LC 733  | Flood Fill                       | Easy       | Basic grid BFS                 |
| LC 200  | Number of Islands                | Medium     | Count BFS components           |
| LC 695  | Max Area of Island               | Medium     | BFS + count nodes              |
| LC 994  | Rotting Oranges                  | Medium     | Multi-source BFS + level count |
| LC 286  | Walls and Gates                  | Medium     | Multi-source BFS               |
| LC 417  | Pacific Atlantic Water Flow      | Medium     | Two-source BFS                 |
| LC 130  | Surrounded Regions               | Medium     | BFS from border                |
| LC 127  | Word Ladder                      | Hard       | BFS on state graph             |
| LC 1091 | Shortest Path in Binary Matrix   | Medium     | 8-dir grid BFS                 |
| LC 752  | Open the Lock                    | Medium     | BFS on state graph             |
| LC 909  | Snakes and Ladders               | Medium     | BFS with state mapping         |
| LC 542  | 01 Matrix                        | Medium     | Multi-source BFS               |
| LC 1162 | As Far from Land as Possible     | Medium     | Multi-source BFS               |
| LC 1293 | Shortest Path in Grid with Obstacles | Hard   | BFS with state (r,c,k)         |

### 💡 Key Hints

**LC 542 (01 Matrix):**
```
Multi-source BFS from all 0-cells simultaneously.
Each 1-cell gets the distance to its nearest 0-cell.
Time: O(rows × cols)
```

**LC 417 (Pacific Atlantic):**
```
Instead of BFS from each cell to both oceans (expensive),
do BFS from Pacific border inward, and BFS from Atlantic border inward.
Answer = cells reachable from BOTH.
```

**LC 1293 (Shortest Path with Obstacles):**
```
State = (row, col, obstacles_remaining)
BFS on this 3D state space.
dist[r][c][k] = min steps to reach (r,c) with k obstacles left.
```

---

## 📌 14. Interview Tips for BFS

```
✅ State the algorithm clearly:
   "I'll use BFS since we need the shortest path in this unweighted graph."

✅ Always mark visited BEFORE enqueuing (not after dequeuing).

✅ For grid problems, define dirs array first:
   int[][] dirs = {{0,1},{0,-1},{1,0},{-1,0}};

✅ For "minimum steps" problems — BFS answer is the level count.

✅ For multi-source problems — all sources go in queue at start with distance 0.

✅ For state-space problems — define what constitutes a "state" clearly.

✅ Mention time complexity: O(V+E) for graphs, O(rows×cols) for grids.

✅ Handle edge cases:
   - src == dst → return 0
   - Empty graph → return 0 or -1
   - Disconnected graph → check if dst was reached
```

---

## 🔗 What's Next?

```
01_Graph_Foundations  ✅ Done
02_BFS                ← YOU ARE HERE
03_DFS                ← Deep exploration, backtracking, timing
04_Cycle_Detection    ← Detect cycles in directed/undirected
05_Connected_Components ← Count and label components
06_Bipartite_Check    ← 2-coloring, odd cycle detection
07_Topological_Sort   ← Ordering in DAGs
08_Union_Find_DSU     ← Dynamic connectivity
09_Dijkstra_Algorithm ← Shortest path in weighted graphs
```
