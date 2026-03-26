
# ╔══════════════════════════════════════════════════════════════╗
# ║           03 — DEPTH-FIRST SEARCH (DFS)                     ║
# ║       Complete Theory + In-Depth Java Implementation        ║
# ╚══════════════════════════════════════════════════════════════╝

---

## 🧭 What You Will Learn in This File

```
1. What is DFS? (Core Intuition)
2. Recursive vs Iterative DFS
3. DFS Discovery & Finish Times
4. Edge Classification (Tree/Back/Forward/Cross)
5. DFS Tree and DFS Forest
6. Pre-order, In-order, Post-order in DFS
7. DFS for All Paths (Backtracking)
8. DFS on Grids
9. Complete Java Implementation
10. DFS Applications Summary
11. Common Mistakes
12. LeetCode Problems + Hints
```

---

## 📌 1. What is DFS?

DFS explores as **FAR as possible** down each branch before **backtracking**.

Think of it like exploring a maze — you go as deep as you can, and only turn back when you hit a dead end.

```
Graph:
        0
       / \
      1   2
     / \   \
    3   4   5

DFS from 0:
  Visit 0 → go to 1 → go to 3 (dead end, backtrack)
         → go to 4 (dead end, backtrack)
         → backtrack to 0 → go to 2 → go to 5 (dead end)

Visit order: 0 → 1 → 3 → 4 → 2 → 5
```

**Data Structure Used:** Stack (LIFO) — either the call stack (recursive) or an explicit stack (iterative).

> 💡 **BFS vs DFS intuition:**
> - BFS = explore level by level (breadth first) → uses Queue
> - DFS = explore branch by branch (depth first) → uses Stack

---

## 📌 2. Recursive vs Iterative DFS

### Recursive DFS (Most Common)
```
DFS(graph, node, visited):
  1. Mark node as visited
  2. Process node
  3. For each neighbor v of node:
     - If NOT visited[v]: recursively call DFS(graph, v, visited)
```

The **call stack** acts as the implicit stack.

### Iterative DFS (Using Explicit Stack)
```
DFS_Iterative(graph, start):
  1. Push start onto stack
  2. While stack is NOT empty:
     a. Pop node
     b. If NOT visited[node]:
        - Mark visited[node] = true
        - Process node
        - Push all unvisited neighbors onto stack
```

> ⚠️ **Important difference:**
> - Recursive DFS: marks visited **before** recursing (when entering)
> - Iterative DFS: marks visited **when popping** (not when pushing)
>
> This means iterative DFS may push the same node multiple times,
> but the visited check prevents processing it multiple times.

---

## 📌 3. DFS Discovery & Finish Times

One of the most powerful features of DFS is **timing** — recording when each node is first visited (discovery time) and when DFS is completely done with it (finish time).

```
disc[v] = time when DFS first visits v (entering)
fin[v]  = time when DFS is done with v and ALL its descendants (leaving)
```

### Trace Example

```
Graph (directed): 0→1→3, 0→2, 1→4

Timer starts at 1.

Call DFS(0):
  disc[0] = 1
  Call DFS(1):
    disc[1] = 2
    Call DFS(3):
      disc[3] = 3
      (no unvisited neighbors)
      fin[3] = 4
    Call DFS(4):
      disc[4] = 5
      (no unvisited neighbors)
      fin[4] = 6
    fin[1] = 7
  Call DFS(2):
    disc[2] = 8
    (no unvisited neighbors)
    fin[2] = 9
  fin[0] = 10
```

| Node | disc | fin |
|------|------|-----|
| 0    | 1    | 10  |
| 1    | 2    | 7   |
| 2    | 8    | 9   |
| 3    | 3    | 4   |
| 4    | 5    | 6   |

**Finish order:** 3, 4, 1, 2, 0

> 🔑 **Key Insight:** Reverse finish order = **Topological Order**!
> This is the basis of DFS-based topological sort.

---

## 📌 4. Edge Classification in DFS

When DFS processes edge (u, v), the edge falls into one of 4 categories:

```
Color scheme:
  WHITE (0) = not yet visited
  GRAY  (1) = currently being processed (on the call stack)
  BLACK (2) = fully processed (done)
```

| Edge Type    | When (u,v) is processed, v is... | disc[u] vs disc[v] |
|--------------|-----------------------------------|--------------------|
| **Tree**     | WHITE (unvisited)                 | disc[u] < disc[v]  |
| **Back**     | GRAY (in progress)                | disc[u] > disc[v]  |
| **Forward**  | BLACK (finished)                  | disc[u] < disc[v]  |
| **Cross**    | BLACK (finished)                  | disc[u] > disc[v]  |

### Visual Example
```
Directed graph: 0→1, 0→3, 1→2, 3→1, 2→3

DFS from 0:
  0 (GRAY) → 1 (GRAY) → 2 (GRAY) → 3 (GRAY)
    3→1: 1 is GRAY → BACK EDGE (cycle!)
  3 (BLACK) → 2 (BLACK) → 1 (BLACK)
  0→3: 3 is BLACK, disc[0]<disc[3] → FORWARD EDGE
```

### Why Edge Classification Matters

```
Back edge in DIRECTED graph → CYCLE EXISTS
Back edge in UNDIRECTED graph → also cycle (but need parent check)

For undirected graphs: only Tree and Back edges exist.
For directed graphs: all 4 types can exist.
```

---

## 📌 5. DFS Tree and DFS Forest

DFS builds a **DFS tree** (or **DFS forest** for disconnected graphs).

```
Original graph:          DFS Tree (from node 0):
  0 — 1 — 3               0
  |   |                   |
  2   4                   1
                         / \
                        3   4
                        |
                        2 (via 0-2 edge, but 2 is child of 0 in tree)

Actually:
  DFS(0): visit 0, go to 1
  DFS(1): visit 1, go to 3
  DFS(3): visit 3, no unvisited neighbors, return
  Back to 1: go to 4
  DFS(4): visit 4, return
  Back to 0: go to 2
  DFS(2): visit 2, return

DFS Tree edges: 0→1, 1→3, 1→4, 0→2
```

**Properties of DFS Tree:**
- Every non-tree edge in an undirected graph is a **back edge**
- Every back edge creates exactly one cycle
- The DFS tree has exactly V-1 edges (for connected graph)

---

## 📌 6. Pre-order, In-order, Post-order

These concepts from binary trees generalize to graphs:

```
Pre-order  = process node BEFORE visiting children
             → Standard DFS print order
             → Used for: copying a tree, prefix expression

Post-order = process node AFTER visiting ALL children
             → Used for: topological sort, computing subtree sizes
             → Reverse post-order = Topological order!

In-order   = only meaningful for binary trees (left, root, right)
```

```java
// Pre-order DFS
void dfsPreOrder(int node, boolean[] visited) {
    visited[node] = true;
    process(node);  // ← BEFORE children
    for (int neighbor : graph.get(node))
        if (!visited[neighbor]) dfsPreOrder(neighbor, visited);
}

// Post-order DFS
void dfsPostOrder(int node, boolean[] visited) {
    visited[node] = true;
    for (int neighbor : graph.get(node))
        if (!visited[neighbor]) dfsPostOrder(neighbor, visited);
    process(node);  // ← AFTER all children
}
```

---

## 📌 7. Complete Java Implementation

```java
import java.util.*;

/**
 * Complete DFS Implementation
 * Covers: Recursive, Iterative, Timing, Edge Classification,
 *         All Paths (Backtracking), Grid DFS, Flood Fill
 */
public class DFS {

    // ═══════════════════════════════════════════════════════════
    //  RECURSIVE DFS — Basic Traversal
    // ═══════════════════════════════════════════════════════════
    static void dfsRecursive(List<List<Integer>> graph, int start) {
        boolean[] visited = new boolean[graph.size()];
        System.out.print("DFS (Recursive): ");
        dfsHelper(graph, start, visited);
        System.out.println();
    }

    static void dfsHelper(List<List<Integer>> graph, int node, boolean[] visited) {
        visited[node] = true;
        System.out.print(node + " ");
        for (int neighbor : graph.get(node))
            if (!visited[neighbor])
                dfsHelper(graph, neighbor, visited);
    }

    // ═══════════════════════════════════════════════════════════
    //  ITERATIVE DFS — Using Explicit Stack
    // ═══════════════════════════════════════════════════════════
    static void dfsIterative(List<List<Integer>> graph, int start) {
        int n = graph.size();
        boolean[] visited = new boolean[n];
        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(start);

        System.out.print("DFS (Iterative): ");
        while (!stack.isEmpty()) {
            int node = stack.pop();
            if (visited[node]) continue; // skip if already processed
            visited[node] = true;
            System.out.print(node + " ");

            // Push neighbors in REVERSE order to maintain same order as recursive
            List<Integer> neighbors = graph.get(node);
            for (int i = neighbors.size() - 1; i >= 0; i--)
                if (!visited[neighbors.get(i)])
                    stack.push(neighbors.get(i));
        }
        System.out.println();
    }

    // ═══════════════════════════════════════════════════════════
    //  DFS WITH TIMING (Discovery & Finish Times)
    // ═══════════════════════════════════════════════════════════
    static int timer = 0;

    static void dfsWithTiming(List<List<Integer>> graph, int n) {
        int[] disc = new int[n];
        int[] fin = new int[n];
        boolean[] visited = new boolean[n];
        timer = 1;

        for (int i = 0; i < n; i++)
            if (!visited[i])
                dfsTimer(graph, i, visited, disc, fin);

        System.out.println("Node | Discovery | Finish | Interval");
        System.out.println("-----|-----------|--------|----------");
        for (int i = 0; i < n; i++)
            System.out.printf("  %d  |     %2d    |   %2d   | [%d, %d]%n",
                i, disc[i], fin[i], disc[i], fin[i]);
    }

    static void dfsTimer(List<List<Integer>> graph, int node,
                          boolean[] visited, int[] disc, int[] fin) {
        visited[node] = true;
        disc[node] = timer++;
        for (int neighbor : graph.get(node))
            if (!visited[neighbor])
                dfsTimer(graph, neighbor, visited, disc, fin);
        fin[node] = timer++;
    }

    // ═══════════════════════════════════════════════════════════
    //  DFS EDGE CLASSIFICATION (3-Color)
    // ═══════════════════════════════════════════════════════════
    static final int WHITE = 0, GRAY = 1, BLACK = 2;

    static void classifyEdges(List<List<Integer>> graph, int n) {
        int[] color = new int[n];
        int[] disc = new int[n];
        timer = 0;
        System.out.println("Edge Classification:");
        for (int i = 0; i < n; i++)
            if (color[i] == WHITE)
                dfsClassify(graph, i, color, disc);
    }

    static void dfsClassify(List<List<Integer>> graph, int node, int[] color, int[] disc) {
        color[node] = GRAY;
        disc[node] = timer++;
        for (int neighbor : graph.get(node)) {
            String edgeType;
            if (color[neighbor] == WHITE) {
                edgeType = "TREE";
                dfsClassify(graph, neighbor, color, disc);
            } else if (color[neighbor] == GRAY) {
                edgeType = "BACK (cycle!)";
            } else { // BLACK
                edgeType = disc[node] < disc[neighbor] ? "FORWARD" : "CROSS";
            }
            System.out.println("  Edge " + node + " → " + neighbor + ": " + edgeType);
        }
        color[node] = BLACK;
    }

    // ═══════════════════════════════════════════════════════════
    //  PATH DETECTION — Does path exist from src to dst?
    // ═══════════════════════════════════════════════════════════
    static boolean hasPath(List<List<Integer>> graph, int src, int dst) {
        boolean[] visited = new boolean[graph.size()];
        return dfsPath(graph, src, dst, visited);
    }

    static boolean dfsPath(List<List<Integer>> graph, int node, int dst, boolean[] visited) {
        if (node == dst) return true;
        visited[node] = true;
        for (int neighbor : graph.get(node))
            if (!visited[neighbor] && dfsPath(graph, neighbor, dst, visited))
                return true;
        return false;
    }

    // ═══════════════════════════════════════════════════════════
    //  ALL PATHS — Find every path from src to dst (Backtracking)
    // ═══════════════════════════════════════════════════════════
    static List<List<Integer>> allPaths(List<List<Integer>> graph, int src, int dst) {
        List<List<Integer>> result = new ArrayList<>();
        boolean[] visited = new boolean[graph.size()];
        List<Integer> path = new ArrayList<>();
        path.add(src);
        visited[src] = true;
        findAllPaths(graph, src, dst, visited, path, result);
        return result;
    }

    static void findAllPaths(List<List<Integer>> graph, int node, int dst,
                              boolean[] visited, List<Integer> path,
                              List<List<Integer>> result) {
        if (node == dst) {
            result.add(new ArrayList<>(path)); // found a complete path
            return;
        }
        for (int neighbor : graph.get(node)) {
            if (!visited[neighbor]) {
                // CHOOSE
                visited[neighbor] = true;
                path.add(neighbor);

                // EXPLORE
                findAllPaths(graph, neighbor, dst, visited, path, result);

                // UN-CHOOSE (backtrack)
                path.remove(path.size() - 1);
                visited[neighbor] = false;
            }
        }
    }

    // ═══════════════════════════════════════════════════════════
    //  DFS POST-ORDER — Used for Topological Sort
    // ═══════════════════════════════════════════════════════════
    static int[] dfsPostOrder(int n, List<List<Integer>> graph) {
        boolean[] visited = new boolean[n];
        Deque<Integer> stack = new ArrayDeque<>();
        for (int i = 0; i < n; i++)
            if (!visited[i])
                dfsPostOrderHelper(graph, i, visited, stack);

        int[] order = new int[n];
        int i = 0;
        while (!stack.isEmpty()) order[i++] = stack.pop();
        return order; // this is topological order!
    }

    static void dfsPostOrderHelper(List<List<Integer>> graph, int node,
                                    boolean[] visited, Deque<Integer> stack) {
        visited[node] = true;
        for (int neighbor : graph.get(node))
            if (!visited[neighbor])
                dfsPostOrderHelper(graph, neighbor, visited, stack);
        stack.push(node); // push AFTER all children → post-order
    }

    // ═══════════════════════════════════════════════════════════
    //  DFS FOR DISCONNECTED GRAPHS — Count components
    // ═══════════════════════════════════════════════════════════
    static int countComponents(List<List<Integer>> graph) {
        int n = graph.size();
        boolean[] visited = new boolean[n];
        int count = 0;
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                count++;
                dfsHelper(graph, i, visited);
            }
        }
        return count;
    }

    // ═══════════════════════════════════════════════════════════
    //  FLOOD FILL (LC 733) — DFS on Grid
    // ═══════════════════════════════════════════════════════════
    static int[][] floodFill(int[][] image, int sr, int sc, int newColor) {
        int oldColor = image[sr][sc];
        if (oldColor == newColor) return image; // avoid infinite loop
        dfsFloodFill(image, sr, sc, oldColor, newColor);
        return image;
    }

    static void dfsFloodFill(int[][] image, int r, int c, int oldColor, int newColor) {
        // Boundary check
        if (r < 0 || r >= image.length || c < 0 || c >= image[0].length) return;
        // Color check
        if (image[r][c] != oldColor) return;

        image[r][c] = newColor; // paint this cell

        // Explore all 4 directions
        dfsFloodFill(image, r + 1, c, oldColor, newColor);
        dfsFloodFill(image, r - 1, c, oldColor, newColor);
        dfsFloodFill(image, r, c + 1, oldColor, newColor);
        dfsFloodFill(image, r, c - 1, oldColor, newColor);
    }

    // ═══════════════════════════════════════════════════════════
    //  SURROUNDED REGIONS (LC 130) — DFS from border
    // ═══════════════════════════════════════════════════════════
    static void solveSurroundedRegions(char[][] board) {
        int rows = board.length, cols = board[0].length;

        // Step 1: DFS from all border 'O' cells, mark them as safe ('S')
        for (int r = 0; r < rows; r++) {
            if (board[r][0] == 'O') dfsMark(board, r, 0);
            if (board[r][cols-1] == 'O') dfsMark(board, r, cols-1);
        }
        for (int c = 0; c < cols; c++) {
            if (board[0][c] == 'O') dfsMark(board, 0, c);
            if (board[rows-1][c] == 'O') dfsMark(board, rows-1, c);
        }

        // Step 2: Flip remaining 'O' to 'X', restore 'S' to 'O'
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++)
                if (board[r][c] == 'O') board[r][c] = 'X';
                else if (board[r][c] == 'S') board[r][c] = 'O';
    }

    static void dfsMark(char[][] board, int r, int c) {
        if (r < 0 || r >= board.length || c < 0 || c >= board[0].length) return;
        if (board[r][c] != 'O') return;
        board[r][c] = 'S'; // mark as safe
        dfsMark(board, r+1, c);
        dfsMark(board, r-1, c);
        dfsMark(board, r, c+1);
        dfsMark(board, r, c-1);
    }

    // ═══════════════════════════════════════════════════════════
    //  KEYS AND ROOMS (LC 841) — DFS reachability
    // ═══════════════════════════════════════════════════════════
    static boolean canVisitAllRooms(List<List<Integer>> rooms) {
        int n = rooms.size();
        boolean[] visited = new boolean[n];
        dfsRooms(rooms, 0, visited);
        for (boolean v : visited) if (!v) return false;
        return true;
    }

    static void dfsRooms(List<List<Integer>> rooms, int room, boolean[] visited) {
        visited[room] = true;
        for (int key : rooms.get(room))
            if (!visited[key])
                dfsRooms(rooms, key, visited);
    }

    // ═══════════════════════════════════════════════════════════
    //  PACIFIC ATLANTIC WATER FLOW (LC 417) — DFS from borders
    // ═══════════════════════════════════════════════════════════
    static List<List<Integer>> pacificAtlantic(int[][] heights) {
        int rows = heights.length, cols = heights[0].length;
        boolean[][] pacific = new boolean[rows][cols];
        boolean[][] atlantic = new boolean[rows][cols];

        // DFS from Pacific border (top row + left col)
        for (int r = 0; r < rows; r++) dfsPacific(heights, r, 0, pacific, heights[r][0]);
        for (int c = 0; c < cols; c++) dfsPacific(heights, 0, c, pacific, heights[0][c]);

        // DFS from Atlantic border (bottom row + right col)
        for (int r = 0; r < rows; r++) dfsPacific(heights, r, cols-1, atlantic, heights[r][cols-1]);
        for (int c = 0; c < cols; c++) dfsPacific(heights, rows-1, c, atlantic, heights[rows-1][c]);

        List<List<Integer>> result = new ArrayList<>();
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++)
                if (pacific[r][c] && atlantic[r][c])
                    result.add(Arrays.asList(r, c));
        return result;
    }

    static int[][] DIRS = {{0,1},{0,-1},{1,0},{-1,0}};

    static void dfsPacific(int[][] heights, int r, int c, boolean[][] visited, int prevHeight) {
        if (r < 0 || r >= heights.length || c < 0 || c >= heights[0].length) return;
        if (visited[r][c]) return;
        if (heights[r][c] < prevHeight) return; // water flows downhill, we go uphill
        visited[r][c] = true;
        for (int[] d : DIRS)
            dfsPacific(heights, r+d[0], c+d[1], visited, heights[r][c]);
    }

    // ═══════════════════════════════════════════════════════════
    //  MAIN DEMO
    // ═══════════════════════════════════════════════════════════
    public static void main(String[] args) {
        // Build undirected graph: 0—1, 0—2, 1—3, 1—4, 2—5
        int n = 6;
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) graph.add(new ArrayList<>());
        int[][] edges = {{0,1},{0,2},{1,3},{1,4},{2,5}};
        for (int[] e : edges) {
            graph.get(e[0]).add(e[1]);
            graph.get(e[1]).add(e[0]);
        }

        System.out.println("╔══════════════════════════════════╗");
        System.out.println("║         DFS DEMO                 ║");
        System.out.println("╚══════════════════════════════════╝\n");

        System.out.println("═══ Recursive DFS ═══");
        dfsRecursive(graph, 0);

        System.out.println("\n═══ Iterative DFS ═══");
        dfsIterative(graph, 0);

        System.out.println("\n═══ DFS Timing ═══");
        dfsWithTiming(graph, n);

        System.out.println("\n═══ Path Detection ═══");
        System.out.println("Path 0→5 exists: " + hasPath(graph, 0, 5));
        System.out.println("Path 3→5 exists: " + hasPath(graph, 3, 5));

        System.out.println("\n═══ All Paths (Directed Graph) ═══");
        List<List<Integer>> dg = new ArrayList<>();
        for (int i = 0; i < 6; i++) dg.add(new ArrayList<>());
        dg.get(0).add(1); dg.get(0).add(2);
        dg.get(1).add(3); dg.get(3).add(5);
        dg.get(2).add(4); dg.get(4).add(5);
        System.out.println("All paths 0→5: " + allPaths(dg, 0, 5));

        System.out.println("\n═══ Post-Order (Topological Sort) ═══");
        List<List<Integer>> dag = new ArrayList<>();
        for (int i = 0; i < 6; i++) dag.add(new ArrayList<>());
        dag.get(5).add(0); dag.get(5).add(2);
        dag.get(4).add(0); dag.get(4).add(1);
        dag.get(2).add(3); dag.get(3).add(1);
        System.out.println("Topological order: " + Arrays.toString(dfsPostOrder(6, dag)));

        System.out.println("\n═══ Flood Fill ═══");
        int[][] image = {{1,1,1},{1,1,0},{1,0,1}};
        System.out.println("Before: " + Arrays.deepToString(image));
        System.out.println("After:  " + Arrays.deepToString(floodFill(image, 1, 1, 2)));

        System.out.println("\n═══ Edge Classification (Directed) ═══");
        List<List<Integer>> dg2 = new ArrayList<>();
        for (int i = 0; i < 4; i++) dg2.add(new ArrayList<>());
        dg2.get(0).add(1); dg2.get(1).add(2); dg2.get(2).add(0); // cycle: 0→1→2→0
        dg2.get(0).add(3);
        classifyEdges(dg2, 4);
    }
}
```

---

## 📌 8. DFS on Grids

```java
import java.util.*;

public class GridDFS {

    static final int[][] DIRS = {{0,1},{0,-1},{1,0},{-1,0}};

    // ─────────────────────────────────────────────────────────
    //  MAX AREA OF ISLAND (LC 695)
    // ─────────────────────────────────────────────────────────
    static int maxAreaOfIsland(int[][] grid) {
        int rows = grid.length, cols = grid[0].length;
        boolean[][] visited = new boolean[rows][cols];
        int maxArea = 0;

        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++)
                if (grid[r][c] == 1 && !visited[r][c])
                    maxArea = Math.max(maxArea, dfsArea(grid, r, c, visited));

        return maxArea;
    }

    static int dfsArea(int[][] grid, int r, int c, boolean[][] visited) {
        if (r < 0 || r >= grid.length || c < 0 || c >= grid[0].length) return 0;
        if (visited[r][c] || grid[r][c] == 0) return 0;
        visited[r][c] = true;
        int area = 1;
        for (int[] d : DIRS)
            area += dfsArea(grid, r + d[0], c + d[1], visited);
        return area;
    }

    // ─────────────────────────────────────────────────────────
    //  NUMBER OF ENCLAVES (LC 1020)
    //  Count land cells that cannot reach the border
    // ─────────────────────────────────────────────────────────
    static int numEnclaves(int[][] grid) {
        int rows = grid.length, cols = grid[0].length;

        // Mark all land connected to border as visited
        for (int r = 0; r < rows; r++) {
            if (grid[r][0] == 1) dfsMarkLand(grid, r, 0);
            if (grid[r][cols-1] == 1) dfsMarkLand(grid, r, cols-1);
        }
        for (int c = 0; c < cols; c++) {
            if (grid[0][c] == 1) dfsMarkLand(grid, 0, c);
            if (grid[rows-1][c] == 1) dfsMarkLand(grid, rows-1, c);
        }

        // Count remaining land cells (enclaves)
        int count = 0;
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++)
                if (grid[r][c] == 1) count++;
        return count;
    }

    static void dfsMarkLand(int[][] grid, int r, int c) {
        if (r < 0 || r >= grid.length || c < 0 || c >= grid[0].length) return;
        if (grid[r][c] != 1) return;
        grid[r][c] = 2; // mark as visited
        for (int[] d : DIRS) dfsMarkLand(grid, r + d[0], c + d[1]);
    }

    // ─────────────────────────────────────────────────────────
    //  MAKING A LARGE ISLAND (LC 827)
    //  Color each island, find max island after flipping one 0
    // ─────────────────────────────────────────────────────────
    static int largestIsland(int[][] grid) {
        int n = grid.length;
        int[] islandSize = new int[n * n + 2]; // islandSize[id] = size of island with that id
        int id = 2; // start from 2 (0 and 1 are used by grid values)

        // Step 1: Color each island with unique id, record sizes
        for (int r = 0; r < n; r++)
            for (int c = 0; c < n; c++)
                if (grid[r][c] == 1) {
                    islandSize[id] = dfsColor(grid, r, c, id);
                    id++;
                }

        // Step 2: Try flipping each 0 cell
        int maxSize = 0;
        for (int i = 0; i < islandSize.length; i++) maxSize = Math.max(maxSize, islandSize[i]);

        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                if (grid[r][c] == 0) {
                    Set<Integer> seen = new HashSet<>();
                    int size = 1;
                    for (int[] d : DIRS) {
                        int nr = r + d[0], nc = c + d[1];
                        if (nr >= 0 && nr < n && nc >= 0 && nc < n && grid[nr][nc] > 1) {
                            int islandId = grid[nr][nc];
                            if (seen.add(islandId)) size += islandSize[islandId];
                        }
                    }
                    maxSize = Math.max(maxSize, size);
                }
            }
        }
        return maxSize;
    }

    static int dfsColor(int[][] grid, int r, int c, int color) {
        if (r < 0 || r >= grid.length || c < 0 || c >= grid[0].length) return 0;
        if (grid[r][c] != 1) return 0;
        grid[r][c] = color;
        int size = 1;
        for (int[] d : DIRS) size += dfsColor(grid, r + d[0], c + d[1], color);
        return size;
    }
}
```

---

## 📌 9. DFS Applications Summary

| Application                    | DFS Technique                          | Key Idea                              |
|--------------------------------|----------------------------------------|---------------------------------------|
| **Path detection**             | Basic DFS + visited                    | If dst is visited, path exists        |
| **All paths**                  | DFS + backtracking                     | Undo visited when backtracking        |
| **Cycle detection (undirected)**| DFS + parent tracking                 | Back edge to non-parent = cycle       |
| **Cycle detection (directed)** | 3-color DFS                            | Back edge to GRAY node = cycle        |
| **Topological sort**           | DFS post-order                         | Reverse finish order = topo order     |
| **Connected components**       | DFS from each unvisited node           | Count DFS calls                       |
| **Strongly connected components** | Kosaraju's (2 DFS passes)           | DFS on original + transposed graph    |
| **Bridges & articulation points** | DFS + low-link values              | Tarjan's algorithm                    |
| **Flood fill**                 | DFS on grid                            | Paint connected region                |
| **Island problems**            | DFS on grid                            | Count/measure connected components    |

---

## 📌 10. Time & Space Complexity

```
Time:  O(V + E)
  - Every vertex visited exactly once → O(V)
  - Every edge examined exactly once (or twice for undirected) → O(E)
  - Total: O(V + E)

Space: O(V)
  - visited[] array: O(V)
  - Call stack depth: O(V) in worst case (linear chain)
  - Total: O(V)

⚠️ IMPORTANT: Recursive DFS can cause StackOverflowError for V > ~10,000
   on a linear chain (each call adds one frame to the call stack).
   
   Solutions:
   1. Use iterative DFS with explicit stack
   2. Increase JVM stack size: java -Xss64m MyClass
   3. Use BFS instead (no stack overflow risk)
```

---

## 📌 11. Common Mistakes in DFS

```
❌ Mistake 1: Forgetting to mark visited → infinite loop in cyclic graphs
   Fix: Always mark visited[node] = true at the START of DFS

❌ Mistake 2: Not backtracking visited[] when finding all paths
   Fix: visited[neighbor] = false AFTER the recursive call returns

❌ Mistake 3: Using DFS for shortest path
   Fix: DFS does NOT find shortest paths. Use BFS for unweighted, Dijkstra for weighted.

❌ Mistake 4: Stack overflow with recursive DFS on large graphs
   Fix: Use iterative DFS or increase JVM stack size

❌ Mistake 5: Not handling disconnected graphs
   Fix: Outer loop over all vertices: for (int i = 0; i < n; i++) if (!visited[i]) dfs(i)

❌ Mistake 6: Confusing directed and undirected cycle detection
   Fix: Undirected = parent check; Directed = inStack/gray check

❌ Mistake 7: Iterative DFS marking visited when pushing (not popping)
   Fix: In iterative DFS, mark visited when POPPING (or check before pushing)
```

---

## 📌 12. LeetCode Problems

| #       | Problem                          | Difficulty | Key DFS Technique              |
|---------|----------------------------------|------------|--------------------------------|
| LC 733  | Flood Fill                       | Easy       | Basic grid DFS                 |
| LC 200  | Number of Islands                | Medium     | DFS component counting         |
| LC 695  | Max Area of Island               | Medium     | DFS + area counting            |
| LC 130  | Surrounded Regions               | Medium     | DFS from border                |
| LC 417  | Pacific Atlantic Water Flow      | Medium     | DFS from border, two passes    |
| LC 797  | All Paths from Source to Target  | Medium     | DFS backtracking               |
| LC 1020 | Number of Enclaves               | Medium     | DFS from border                |
| LC 841  | Keys and Rooms                   | Medium     | DFS reachability               |
| LC 1254 | Number of Closed Islands         | Medium     | DFS from border first          |
| LC 827  | Making a Large Island            | Hard       | DFS coloring + merge           |
| LC 329  | Longest Increasing Path in Matrix| Hard       | DFS + memoization              |

### 💡 Key Hints

**LC 797 (All Paths from Source to Target):**
```
DAG problem — no need for visited[] since no cycles.
DFS backtracking: add node to path, recurse, remove from path.
Time: O(2^V × V) — exponential in worst case (all paths)
```

**LC 329 (Longest Increasing Path):**
```
DFS + memoization (top-down DP).
memo[r][c] = longest increasing path starting at (r,c).
No visited[] needed — increasing constraint prevents cycles.
```

**LC 130 (Surrounded Regions):**
```
Key insight: 'O' cells connected to the border CANNOT be surrounded.
Step 1: DFS from all border 'O' cells, mark them safe.
Step 2: Flip remaining 'O' to 'X'.
Step 3: Restore safe cells back to 'O'.
```

---

## 📌 13. Interview Tips for DFS

```
✅ State the algorithm:
   "I'll use DFS since we need to explore all paths / detect cycles / find components."

✅ Always handle disconnected graphs:
   Outer loop: for (int i = 0; i < n; i++) if (!visited[i]) dfs(i)

✅ For backtracking (all paths):
   Remember to UNDO the choice after the recursive call.
   visited[node] = false; path.remove(path.size()-1);

✅ For cycle detection in directed graphs:
   Use 3-color (WHITE/GRAY/BLACK) or inStack[] boolean array.
   Simple visited[] is NOT sufficient for directed graphs.

✅ For topological sort via DFS:
   Push to stack AFTER all neighbors are processed (post-order).
   Pop the stack to get topological order.

✅ Mention stack overflow risk for large inputs:
   "For very large graphs, I'd use iterative DFS to avoid stack overflow."

✅ Time complexity: O(V+E) for both recursive and iterative DFS.
```

---

## 🔗 What's Next?

```
01_Graph_Foundations  ✅ Done
02_BFS                ✅ Done
03_DFS                ← YOU ARE HERE
04_Cycle_Detection    ← Detect cycles in directed/undirected (builds on DFS)
05_Connected_Components ← Count and label components
06_Bipartite_Check    ← 2-coloring, odd cycle detection
07_Topological_Sort   ← Ordering in DAGs (uses DFS post-order)
08_Union_Find_DSU     ← Dynamic connectivity
09_Dijkstra_Algorithm ← Shortest path in weighted graphs
```
