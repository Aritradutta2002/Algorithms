# ╔══════════════════════════════════════════════════════════════╗
# ║           05 — CONNECTED COMPONENTS                         ║
# ║       Complete Theory + In-Depth Java Implementation        ║
# ╚══════════════════════════════════════════════════════════════╝

---

## 🧭 What You Will Learn in This File

```text
1. What a connected component is
2. Why components are maximal sets
3. Counting components with DFS and BFS
4. Labeling each node with a component id
5. Listing actual component members
6. Largest component size
7. Connected components on grids (Number of Islands pattern)
8. Weak connectivity vs strongly connected components
9. DSU approach for dynamic connectivity
10. Complete Java implementations
11. Common mistakes and interview patterns
```

---

## 📌 1. What Is a Connected Component?

In an **undirected graph**, a connected component is a **maximal set of vertices** such that every pair of vertices in the set is connected by some path.

### Example

```text
Graph:

0 -- 1 -- 2      3 -- 4      5

Components:
{0, 1, 2}
{3, 4}
{5}
```

So the graph has **3 connected components**.

### What does "maximal" mean?

It means:

- all nodes inside the set can reach each other
- no extra node can be added without breaking that property

For example, `{0,1}` is connected, but it is not maximal because node `2` also belongs with them.

---

## 📌 2. Why Connected Components Matter

Connected components are foundational because they answer:

- how many separate groups exist?
- which nodes belong together?
- is the whole graph connected?
- what is the size of each region?

This concept appears in:

- social networks
- network clustering
- island counting in grids
- image segmentation
- grouping problems in DSU

> 💡 Very common translation:
> "Count islands / provinces / friend circles / groups" usually means **count connected components**.

---

## 📌 3. Core Idea

If you start DFS or BFS from a node, the traversal visits **all and only** the nodes in that node's component.

Therefore:

1. Loop through all vertices
2. Whenever you find an unvisited node, start a new traversal
3. That traversal covers exactly one full component
4. Increase the component count

### Why it works

Traversal cannot jump to another component because no path exists.
And it must reach every node in the current component because all are connected.

---

## 📌 4. Counting Components with DFS

### Pseudocode

```text
countComponents(graph):
  visited[] = false
  count = 0

  for each node u:
    if not visited[u]:
      dfs(u)
      count++

  return count
```

### Example trace

```text
Graph:
0 -- 1 -- 2      3 -- 4      5

Start at 0:
  DFS visits 0,1,2
  count = 1

Next unvisited is 3:
  DFS visits 3,4
  count = 2

Next unvisited is 5:
  DFS visits 5
  count = 3
```

### Complexity

```text
Time:  O(V + E)
Space: O(V)
```

Every node is visited once, and every edge is examined once or twice depending on representation.

---

## 📌 5. Counting Components with BFS

BFS works the same way:

- start from each unvisited node
- BFS marks its whole component
- each new BFS corresponds to one new component

### When BFS is nice

- you want iterative traversal
- you want level-order behavior
- you want to avoid recursion depth issues

The complexity remains:

```text
Time:  O(V + E)
Space: O(V)
```

---

## 📌 6. Labeling Every Node with a Component ID

Sometimes counting is not enough.

You may want:

- which component node `x` belongs to
- whether two nodes are in the same component
- the members of each component

Then instead of just `visited[]`, assign:

```text
componentId[node] = id
```

Example:

```text
Graph:
0 -- 1 -- 2      3 -- 4      5

componentId:
node:        0 1 2 3 4 5
componentId: 0 0 0 1 1 2
```

This means:

- nodes `0,1,2` belong to component `0`
- nodes `3,4` belong to component `1`
- node `5` belongs to component `2`

---

## 📌 7. Listing Actual Components

We can also store the actual groups:

```text
[
  [0, 1, 2],
  [3, 4],
  [5]
]
```

This is useful in:

- graph clustering tasks
- printing groups
- debugging
- component-wise processing

---

## 📌 8. Largest Component Size

A common follow-up problem is:

> What is the size of the largest connected component?

Approach:

- each DFS/BFS returns the number of nodes visited in that component
- track the maximum

### Example

```text
Components:
{0,1,2} size = 3
{3,4}   size = 2
{5}     size = 1

Largest size = 3
```

---

## 📌 9. Connected Components on a Grid

This is one of the most important graph patterns.

A grid can be treated as a graph:

- each cell is a node
- edges connect neighboring cells

### Number of Islands pattern

```text
1 1 0 0
1 0 0 1
0 0 1 1
0 0 0 1
```

Connected groups of `1`s are components.

Depending on the problem, adjacency may be:

- 4-directional: up, down, left, right
- 8-directional: includes diagonals too

### Key insight

Grid DFS/BFS is still just component traversal.

---

## 📌 10. Directed Graph Note: Weak vs Strong Connectivity

For directed graphs, the term "connected component" needs care.

### Weakly connected

If you ignore edge directions and the graph becomes connected, nodes are in the same **weakly connected component**.

### Strongly connected

Two nodes are in the same **strongly connected component (SCC)** if each can reach the other following directed edges.

```text
0 -> 1 -> 2
^         |
|---------|

{0,1,2} is a strongly connected component
```

> ⚠️ SCCs are a deeper topic solved by Kosaraju / Tarjan, so they are different from ordinary connected components in undirected graphs.

---

## 📌 11. DSU / Union-Find View of Components

Union-Find is another way to maintain components.

### Idea

Initially:

```text
{0} {1} {2} {3} ...
```

For every edge `(u, v)`:

- union the sets containing `u` and `v`

At the end:

- the number of disjoint sets = number of connected components

### When DSU is especially useful

- graph given as edge list
- edges arrive dynamically
- repeated connectivity queries

### Complexity

```text
Time:  O(E * alpha(V))
Space: O(V)
```

---

## 📌 12. Common Mistakes

### Mistake 1: Starting DFS/BFS from only one node

That only explores one component, not the whole graph.

### Mistake 2: Forgetting isolated vertices

A node with no edges is still a component of size `1`.

### Mistake 3: Confusing connected components with SCCs

Undirected connected components and directed SCCs are not the same thing.

### Mistake 4: Reusing `visited[]` incorrectly across multiple traversals

The same `visited[]` should be shared across the whole outer loop.

---

## 📌 13. Complete Java Implementation

```java
import java.util.*;

public class ConnectedComponents {

    // ============================================================
    // COUNT COMPONENTS USING DFS
    // ============================================================
    public static int countComponentsDFS(List<List<Integer>> graph) {
        int n = graph.size();
        boolean[] visited = new boolean[n];
        int count = 0;

        for (int node = 0; node < n; node++) {
            if (!visited[node]) {
                dfs(graph, node, visited);
                count++;
            }
        }
        return count;
    }

    private static void dfs(List<List<Integer>> graph, int node, boolean[] visited) {
        visited[node] = true;
        for (int neighbor : graph.get(node)) {
            if (!visited[neighbor]) {
                dfs(graph, neighbor, visited);
            }
        }
    }

    // ============================================================
    // COUNT COMPONENTS USING BFS
    // ============================================================
    public static int countComponentsBFS(List<List<Integer>> graph) {
        int n = graph.size();
        boolean[] visited = new boolean[n];
        int count = 0;

        for (int start = 0; start < n; start++) {
            if (visited[start]) {
                continue;
            }

            count++;
            Queue<Integer> queue = new ArrayDeque<>();
            queue.offer(start);
            visited[start] = true;

            while (!queue.isEmpty()) {
                int node = queue.poll();
                for (int neighbor : graph.get(node)) {
                    if (!visited[neighbor]) {
                        visited[neighbor] = true;
                        queue.offer(neighbor);
                    }
                }
            }
        }

        return count;
    }

    // ============================================================
    // LABEL EACH NODE WITH ITS COMPONENT ID
    // ============================================================
    public static int[] componentIds(List<List<Integer>> graph) {
        int n = graph.size();
        int[] componentId = new int[n];
        Arrays.fill(componentId, -1);

        int id = 0;
        for (int node = 0; node < n; node++) {
            if (componentId[node] == -1) {
                dfsLabel(graph, node, id, componentId);
                id++;
            }
        }
        return componentId;
    }

    private static void dfsLabel(List<List<Integer>> graph, int node, int id, int[] componentId) {
        componentId[node] = id;
        for (int neighbor : graph.get(node)) {
            if (componentId[neighbor] == -1) {
                dfsLabel(graph, neighbor, id, componentId);
            }
        }
    }

    // ============================================================
    // RETURN ACTUAL LIST OF COMPONENTS
    // ============================================================
    public static List<List<Integer>> getComponents(List<List<Integer>> graph) {
        int n = graph.size();
        boolean[] visited = new boolean[n];
        List<List<Integer>> components = new ArrayList<>();

        for (int node = 0; node < n; node++) {
            if (!visited[node]) {
                List<Integer> current = new ArrayList<>();
                dfsCollect(graph, node, visited, current);
                components.add(current);
            }
        }
        return components;
    }

    private static void dfsCollect(List<List<Integer>> graph, int node,
                                   boolean[] visited, List<Integer> current) {
        visited[node] = true;
        current.add(node);

        for (int neighbor : graph.get(node)) {
            if (!visited[neighbor]) {
                dfsCollect(graph, neighbor, visited, current);
            }
        }
    }

    // ============================================================
    // LARGEST COMPONENT SIZE
    // ============================================================
    public static int largestComponentSize(List<List<Integer>> graph) {
        int n = graph.size();
        boolean[] visited = new boolean[n];
        int best = 0;

        for (int node = 0; node < n; node++) {
            if (!visited[node]) {
                int size = dfsSize(graph, node, visited);
                best = Math.max(best, size);
            }
        }
        return best;
    }

    private static int dfsSize(List<List<Integer>> graph, int node, boolean[] visited) {
        visited[node] = true;
        int size = 1;

        for (int neighbor : graph.get(node)) {
            if (!visited[neighbor]) {
                size += dfsSize(graph, neighbor, visited);
            }
        }
        return size;
    }

    // ============================================================
    // GRID VERSION: NUMBER OF ISLANDS
    // '1' = land, '0' = water
    // ============================================================
    public static int numIslands(char[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;
        boolean[][] visited = new boolean[rows][cols];
        int islands = 0;

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (grid[r][c] == '1' && !visited[r][c]) {
                    floodFill(grid, r, c, visited);
                    islands++;
                }
            }
        }
        return islands;
    }

    private static void floodFill(char[][] grid, int r, int c, boolean[][] visited) {
        int rows = grid.length;
        int cols = grid[0].length;

        if (r < 0 || r >= rows || c < 0 || c >= cols) {
            return;
        }
        if (grid[r][c] != '1' || visited[r][c]) {
            return;
        }

        visited[r][c] = true;

        floodFill(grid, r + 1, c, visited);
        floodFill(grid, r - 1, c, visited);
        floodFill(grid, r, c + 1, visited);
        floodFill(grid, r, c - 1, visited);
    }

    // ============================================================
    // DSU / UNION-FIND VERSION
    // ============================================================
    public static int countComponentsDSU(int n, int[][] edges) {
        DSU dsu = new DSU(n);

        for (int[] edge : edges) {
            dsu.union(edge[0], edge[1]);
        }
        return dsu.components;
    }

    private static class DSU {
        int[] parent;
        int[] size;
        int components;

        DSU(int n) {
            parent = new int[n];
            size = new int[n];
            components = n;

            for (int i = 0; i < n; i++) {
                parent[i] = i;
                size[i] = 1;
            }
        }

        int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]);
            }
            return parent[x];
        }

        boolean union(int a, int b) {
            int rootA = find(a);
            int rootB = find(b);

            if (rootA == rootB) {
                return false;
            }

            if (size[rootA] < size[rootB]) {
                int temp = rootA;
                rootA = rootB;
                rootB = temp;
            }

            parent[rootB] = rootA;
            size[rootA] += size[rootB];
            components--;
            return true;
        }
    }

    // ============================================================
    // HELPER TO BUILD AN UNDIRECTED GRAPH
    // ============================================================
    public static List<List<Integer>> buildGraph(int n, int[][] edges) {
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            graph.get(u).add(v);
            graph.get(v).add(u);
        }
        return graph;
    }

    public static void main(String[] args) {
        int[][] edges = {
            {0, 1}, {1, 2}, {3, 4}
        };

        List<List<Integer>> graph = buildGraph(6, edges);

        System.out.println("DFS component count: " + countComponentsDFS(graph));
        System.out.println("BFS component count: " + countComponentsBFS(graph));
        System.out.println("Largest component size: " + largestComponentSize(graph));
        System.out.println("Component IDs: " + Arrays.toString(componentIds(graph)));
        System.out.println("Actual components: " + getComponents(graph));
        System.out.println("DSU component count: " + countComponentsDSU(6, edges));

        char[][] grid = {
            {'1', '1', '0', '0'},
            {'1', '0', '0', '1'},
            {'0', '0', '1', '1'},
            {'0', '0', '0', '1'}
        };

        System.out.println("Number of islands: " + numIslands(grid));
    }
}
```

---

## 📌 14. Interview Pattern Map

### Pattern A: Number of Provinces

Each province is a connected component.

### Pattern B: Number of Islands

Grid connected components.

### Pattern C: Largest Region / Largest Island

Component size problem.

### Pattern D: Are two nodes in the same group?

Use:

- `componentId[u] == componentId[v]`
- or DSU `find(u) == find(v)`

---

## 📌 15. LeetCode Problems to Practice

```text
- 200. Number of Islands
- 323. Number of Connected Components in an Undirected Graph
- 547. Number of Provinces
- 695. Max Area of Island
- 1319. Number of Operations to Make Network Connected
- 1905. Count Sub Islands
```

---

## 📌 16. Final Takeaways

```text
One traversal from one unvisited node = exactly one connected component.

Outer loop over all nodes is mandatory.

DFS / BFS:
  best when graph structure is already built

DSU:
  best when edges arrive dynamically or graph is given as edge list
```

> 🔥 Master this file and graph grouping problems become much easier.
