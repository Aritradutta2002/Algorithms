# ╔══════════════════════════════════════════════════════════════╗
# ║           04 — CYCLE DETECTION                              ║
# ║       Complete Theory + In-Depth Java Implementation        ║
# ╚══════════════════════════════════════════════════════════════╝

---

## 🧭 What You Will Learn in This File

```text
1. What a cycle really means
2. Why cycle detection matters in interviews and systems
3. Cycle detection in undirected graphs
4. Cycle detection in directed graphs
5. Why "visited[]" alone is not enough in directed graphs
6. DFS, BFS, 3-color DFS, Kahn's algorithm, and DSU approaches
7. How to reconstruct an actual cycle path
8. Time and space complexity of every method
9. Common mistakes and edge cases
10. Complete Java implementations
11. LeetCode patterns and interview hints
```

---

## 📌 1. What Is a Cycle?

A **cycle** is a path that starts and ends at the same vertex.

The exact interpretation depends on the graph type:

- In an **undirected graph**, a cycle means you can start at a node, move through edges, and come back to the same node without reusing edges in the trivial back-and-forth sense.
- In a **directed graph**, edge direction matters, so every step in the cycle must follow the arrow direction.

### Simple examples

```text
Undirected cycle:

    0
   / \
  1---2

Cycle: 0 -> 1 -> 2 -> 0
```

```text
Directed cycle:

0 -> 1 -> 2
^         |
|---------|

Cycle: 0 -> 1 -> 2 -> 0
```

```text
Acyclic graph:

0 -> 1 -> 2 -> 3

No cycle exists.
```

---

## 📌 2. Why Cycle Detection Matters

Cycle detection is not just a textbook topic. It appears everywhere:

- **Course prerequisites**: a cycle means impossible scheduling
- **Build systems**: circular dependencies break builds
- **Deadlock modeling**: wait-for graphs with cycles indicate deadlock risk
- **Network design**: in undirected graphs, cycle edges may be redundant
- **Graph validation**: many problems require the input to be a tree or a DAG

> 💡 Shortcut:
> - "Undirected + detect loop" often means **DFS/BFS with parent** or **DSU**
> - "Directed + prerequisites/order" often means **3-color DFS** or **Kahn's algorithm**

---

## 📌 3. Cycle Detection in Undirected Graphs

In an undirected graph, the most important idea is:

> If during traversal you see a visited neighbor that is **not your parent**, then a cycle exists.

Why do we need the parent check?

Because every undirected edge appears in both directions.

```text
Graph:
0 -- 1

If DFS starts at 0:
  0 sees 1
  1 sees 0

But 1 seeing 0 does NOT mean cycle.
0 is just the node it came from.
```

### Example

```text
    0
   / \
  1---2

DFS from 0:
  Visit 0
  Go to 1 (parent = 0)
  From 1 go to 2 (parent = 1)
  From 2 see neighbor 0, which is visited and is NOT parent
  => cycle found
```

---

### 3.1 Undirected Cycle Detection Using DFS + Parent

### Core intuition

DFS explores along a path. If a node reaches an already visited node that is not the edge it just came from, then that path has looped back.

### Pseudocode

```text
hasCycleUndirected(graph):
  visited[] = false

  for each vertex u:
    if not visited[u]:
      if dfs(u, parent = -1):
        return true

  return false

dfs(node, parent):
  visited[node] = true

  for each neighbor in graph[node]:
    if not visited[neighbor]:
      if dfs(neighbor, node):
        return true
    else if neighbor != parent:
      return true

  return false
```

### Why this works

For undirected graphs, every back edge to a previously visited non-parent node closes a loop.

### Complexity

```text
Time:  O(V + E)
Space: O(V)    for visited + recursion stack
```

---

### 3.2 Undirected Cycle Detection Using BFS + Parent

The idea is exactly the same, but we traverse level by level instead of depth first.

Each queue entry stores:

```text
(currentNode, parentNode)
```

### When BFS is useful

- You want an iterative approach
- You want to avoid recursion depth issues
- You want logic parallel to BFS-based component traversal

### Complexity

```text
Time:  O(V + E)
Space: O(V)
```

---

### 3.3 Important Edge Cases in Undirected Graphs

#### Self-loop

```text
0 -- 0
```

This is immediately a cycle.

#### Parallel edges / multi-edges

If there are two separate edges between the same two vertices:

```text
u === v
```

then a cycle may exist even though simple parent logic can be misleading in some graph models.

In interview problems, graphs are usually **simple graphs**, so normal parent tracking is enough.

#### Disconnected graphs

You must run DFS/BFS from every unvisited node, otherwise you may miss a cycle in another component.

---

## 📌 4. Cycle Detection in Directed Graphs

Directed graphs are more subtle.

In a directed graph, seeing a visited node does **not** automatically mean a cycle.

### Example where visited node does NOT imply cycle

```text
0 -> 1
 \   |
  \  v
   ->2

Edges: 0->1, 1->2, 0->2
```

Suppose DFS visits:

```text
0 -> 1 -> 2
```

Then while returning to 0, it sees edge `0 -> 2`.
Node `2` is already visited, but there is **no cycle**.

So in directed graphs, we need stronger information than just `visited[]`.

---

## 📌 5. Why "Visited" Alone Fails in Directed Graphs

We must distinguish three states:

```text
WHITE = not visited yet
GRAY  = currently in recursion stack / current DFS path
BLACK = fully processed and exited
```

### Critical rule

> In a directed graph, a cycle exists if DFS reaches a **GRAY** node.

Why?

Because a GRAY node is still active in the current DFS path. Reaching it again means we have returned to an ancestor, which forms a directed cycle.

---

## 📌 6. Directed Cycle Detection Using 3-Color DFS

### State meaning

| Color | Meaning |
|------|---------|
| `0` | unvisited |
| `1` | currently being explored |
| `2` | completely processed |

### Pseudocode

```text
hasCycleDirected(graph):
  color[] = 0 for all nodes

  for each vertex u:
    if color[u] == 0:
      if dfs(u):
        return true

  return false

dfs(node):
  color[node] = GRAY

  for each neighbor:
    if color[neighbor] == GRAY:
      return true
    if color[neighbor] == WHITE and dfs(neighbor):
      return true

  color[node] = BLACK
  return false
```

### Why this works

- Tree edges go to WHITE nodes
- Back edges go to GRAY nodes
- Back edge in directed DFS means cycle
- BLACK nodes are already proven safe for the current DFS

### Complexity

```text
Time:  O(V + E)
Space: O(V)
```

---

## 📌 7. Directed Cycle Detection Using Recursion Stack Array

This is logically equivalent to 3-color DFS.

Instead of colors, keep:

- `visited[node]`
- `inStack[node]`

Cycle rule:

```text
If an edge goes to a node with inStack[node] = true, cycle exists.
```

This version is very common in interviews because it is easy to explain.

---

## 📌 8. Directed Cycle Detection Using Kahn's Algorithm

Kahn's algorithm is normally taught for **topological sorting**, but it also detects cycles.

### Key idea

In a DAG, there is always at least one node with in-degree `0`.

If we repeatedly remove all in-degree-0 nodes and process the graph:

- If all vertices get processed, graph is acyclic
- If some vertices remain forever, they are trapped in a cycle

### Why this works

A cycle means every node in the cycle has at least one incoming edge from within the cycle, so none of them can become initial zero in-degree nodes until the cycle is broken, which never happens.

### Complexity

```text
Time:  O(V + E)
Space: O(V)
```

> 💡 Kahn's algorithm is especially useful when the problem talks about:
> - prerequisites
> - ordering
> - whether all tasks can be completed

---

## 📌 9. Cycle Detection Using Union-Find (DSU)

This method is for **undirected graphs only**.

### Core idea

Process edges one by one:

- If `u` and `v` already belong to the same component, adding edge `(u, v)` creates a cycle
- Otherwise, union their components

### Example

```text
Edges:
(0,1), (1,2), (2,0)

After (0,1): 0 and 1 connected
After (1,2): 0,1,2 connected
Before adding (2,0): 2 and 0 already in same set
=> cycle found
```

### Complexity

```text
Time:  O(E * alpha(V))   ~ almost linear
Space: O(V)
```

This is excellent when the graph is given as an **edge list** and you do not need traversal order.

---

## 📌 10. How to Reconstruct an Actual Cycle

Detecting whether a cycle exists is one task.
Sometimes the problem asks for the **cycle itself**.

### Directed graph approach

While doing DFS:

- keep a `parent[]` array
- when you find an edge to a GRAY ancestor `x`
- current node `u` plus ancestors from `u` back to `x` form the cycle

### Example

```text
0 -> 1 -> 2 -> 3
     ^         |
     |---------|

When DFS is at 3 and sees edge 3 -> 1:
  cycle = 1 -> 2 -> 3 -> 1
```

This is a very useful advanced pattern for contest problems and graph debugging.

---

## 📌 11. Comparison Table

| Method | Graph Type | Detects Cycle? | Returns Order? | Good For |
|------|------|------|------|------|
| DFS + parent | Undirected | Yes | No | Most standard |
| BFS + parent | Undirected | Yes | No | Iterative solution |
| 3-color DFS | Directed | Yes | No | Most standard directed check |
| visited + inStack | Directed | Yes | No | Interview-friendly |
| Kahn's algorithm | Directed | Yes | Yes, if acyclic | DAG / prerequisites |
| DSU | Undirected | Yes | No | Edge-list processing |

---

## 📌 12. Common Mistakes

### Mistake 1: Using only `visited[]` for directed graphs

That gives false positives and false negatives.

You need:

- recursion stack
- or 3-color DFS
- or Kahn's algorithm

### Mistake 2: Forgetting disconnected components

Always loop through all vertices.

### Mistake 3: Forgetting the parent check in undirected DFS/BFS

Without parent tracking, every undirected edge looks like a cycle.

### Mistake 4: Using DSU for directed cycle detection

DSU does **not** correctly solve general directed cycle detection.

### Mistake 5: Thinking every visited node in DFS means back edge

Only a node in the **current recursion stack** indicates a directed cycle.

---

## 📌 13. Complete Java Implementation

```java
import java.util.*;

public class CycleDetection {

    // ============================================================
    // UNDIRECTED GRAPH: DFS + PARENT
    // ============================================================
    public static boolean hasCycleUndirectedDFS(List<List<Integer>> graph) {
        int n = graph.size();
        boolean[] visited = new boolean[n];

        for (int node = 0; node < n; node++) {
            if (!visited[node] && dfsUndirected(graph, node, -1, visited)) {
                return true;
            }
        }
        return false;
    }

    private static boolean dfsUndirected(List<List<Integer>> graph, int node, int parent, boolean[] visited) {
        visited[node] = true;

        for (int neighbor : graph.get(node)) {
            if (!visited[neighbor]) {
                if (dfsUndirected(graph, neighbor, node, visited)) {
                    return true;
                }
            } else if (neighbor != parent) {
                return true;
            }
        }
        return false;
    }

    // ============================================================
    // UNDIRECTED GRAPH: BFS + PARENT
    // ============================================================
    public static boolean hasCycleUndirectedBFS(List<List<Integer>> graph) {
        int n = graph.size();
        boolean[] visited = new boolean[n];

        for (int start = 0; start < n; start++) {
            if (visited[start]) {
                continue;
            }

            Queue<int[]> queue = new ArrayDeque<>();
            queue.offer(new int[]{start, -1});
            visited[start] = true;

            while (!queue.isEmpty()) {
                int[] current = queue.poll();
                int node = current[0];
                int parent = current[1];

                for (int neighbor : graph.get(node)) {
                    if (!visited[neighbor]) {
                        visited[neighbor] = true;
                        queue.offer(new int[]{neighbor, node});
                    } else if (neighbor != parent) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // ============================================================
    // DIRECTED GRAPH: 3-COLOR DFS
    // 0 = WHITE, 1 = GRAY, 2 = BLACK
    // ============================================================
    public static boolean hasCycleDirectedColor(List<List<Integer>> graph) {
        int n = graph.size();
        int[] color = new int[n];

        for (int node = 0; node < n; node++) {
            if (color[node] == 0 && dfsDirectedColor(graph, node, color)) {
                return true;
            }
        }
        return false;
    }

    private static boolean dfsDirectedColor(List<List<Integer>> graph, int node, int[] color) {
        color[node] = 1;

        for (int neighbor : graph.get(node)) {
            if (color[neighbor] == 1) {
                return true;
            }
            if (color[neighbor] == 0 && dfsDirectedColor(graph, neighbor, color)) {
                return true;
            }
        }

        color[node] = 2;
        return false;
    }

    // ============================================================
    // DIRECTED GRAPH: VISITED + RECURSION STACK
    // ============================================================
    public static boolean hasCycleDirectedStack(List<List<Integer>> graph) {
        int n = graph.size();
        boolean[] visited = new boolean[n];
        boolean[] inStack = new boolean[n];

        for (int node = 0; node < n; node++) {
            if (!visited[node] && dfsDirectedStack(graph, node, visited, inStack)) {
                return true;
            }
        }
        return false;
    }

    private static boolean dfsDirectedStack(List<List<Integer>> graph, int node,
                                            boolean[] visited, boolean[] inStack) {
        visited[node] = true;
        inStack[node] = true;

        for (int neighbor : graph.get(node)) {
            if (!visited[neighbor]) {
                if (dfsDirectedStack(graph, neighbor, visited, inStack)) {
                    return true;
                }
            } else if (inStack[neighbor]) {
                return true;
            }
        }

        inStack[node] = false;
        return false;
    }

    // ============================================================
    // DIRECTED GRAPH: KAHN'S ALGORITHM
    // If processed nodes < total nodes, a cycle exists.
    // ============================================================
    public static boolean hasCycleDirectedKahn(List<List<Integer>> graph) {
        int n = graph.size();
        int[] inDegree = new int[n];

        for (int u = 0; u < n; u++) {
            for (int v : graph.get(u)) {
                inDegree[v]++;
            }
        }

        Queue<Integer> queue = new ArrayDeque<>();
        for (int node = 0; node < n; node++) {
            if (inDegree[node] == 0) {
                queue.offer(node);
            }
        }

        int processed = 0;
        while (!queue.isEmpty()) {
            int node = queue.poll();
            processed++;

            for (int neighbor : graph.get(node)) {
                inDegree[neighbor]--;
                if (inDegree[neighbor] == 0) {
                    queue.offer(neighbor);
                }
            }
        }

        return processed != n;
    }

    // ============================================================
    // DIRECTED GRAPH: RETURN AN ACTUAL CYCLE
    // Returns empty list if no cycle exists.
    // Example output: [1, 2, 3, 1]
    // ============================================================
    public static List<Integer> findDirectedCycle(List<List<Integer>> graph) {
        int n = graph.size();
        int[] state = new int[n];
        int[] parent = new int[n];
        Arrays.fill(parent, -1);

        int[] cycleStart = new int[]{-1};
        int[] cycleEnd = new int[]{-1};

        for (int node = 0; node < n; node++) {
            if (state[node] == 0 && dfsFindCycle(graph, node, state, parent, cycleStart, cycleEnd)) {
                return buildDirectedCycle(parent, cycleStart[0], cycleEnd[0]);
            }
        }

        return new ArrayList<>();
    }

    private static boolean dfsFindCycle(List<List<Integer>> graph, int node, int[] state, int[] parent,
                                        int[] cycleStart, int[] cycleEnd) {
        state[node] = 1;

        for (int neighbor : graph.get(node)) {
            if (state[neighbor] == 0) {
                parent[neighbor] = node;
                if (dfsFindCycle(graph, neighbor, state, parent, cycleStart, cycleEnd)) {
                    return true;
                }
            } else if (state[neighbor] == 1) {
                cycleStart[0] = neighbor;
                cycleEnd[0] = node;
                return true;
            }
        }

        state[node] = 2;
        return false;
    }

    private static List<Integer> buildDirectedCycle(int[] parent, int cycleStart, int cycleEnd) {
        List<Integer> cycle = new ArrayList<>();

        for (int node = cycleEnd; node != cycleStart; node = parent[node]) {
            cycle.add(node);
        }
        cycle.add(cycleStart);
        Collections.reverse(cycle);
        cycle.add(cycleStart);

        return cycle;
    }

    // ============================================================
    // UNDIRECTED GRAPH: DSU / UNION-FIND
    // Graph must be given as edge list.
    // ============================================================
    public static boolean hasCycleUndirectedDSU(int n, int[][] edges) {
        DSU dsu = new DSU(n);

        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];

            if (!dsu.union(u, v)) {
                return true;
            }
        }
        return false;
    }

    private static class DSU {
        int[] parent;
        int[] rank;

        DSU(int n) {
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
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

            if (rank[rootA] < rank[rootB]) {
                parent[rootA] = rootB;
            } else if (rank[rootA] > rank[rootB]) {
                parent[rootB] = rootA;
            } else {
                parent[rootB] = rootA;
                rank[rootA]++;
            }
            return true;
        }
    }

    // ============================================================
    // HELPERS
    // ============================================================
    public static List<List<Integer>> buildGraph(int n, int[][] edges, boolean directed) {
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            graph.get(u).add(v);
            if (!directed) {
                graph.get(v).add(u);
            }
        }
        return graph;
    }

    public static void main(String[] args) {
        int[][] undirectedEdges = {
            {0, 1}, {1, 2}, {2, 0}, {3, 4}
        };
        List<List<Integer>> undirected = buildGraph(5, undirectedEdges, false);

        System.out.println("Undirected DFS cycle? " + hasCycleUndirectedDFS(undirected));
        System.out.println("Undirected BFS cycle? " + hasCycleUndirectedBFS(undirected));
        System.out.println("Undirected DSU cycle? " + hasCycleUndirectedDSU(5, undirectedEdges));

        int[][] directedEdges = {
            {0, 1}, {1, 2}, {2, 3}, {3, 1}, {4, 5}
        };
        List<List<Integer>> directed = buildGraph(6, directedEdges, true);

        System.out.println("Directed 3-color cycle? " + hasCycleDirectedColor(directed));
        System.out.println("Directed stack cycle? " + hasCycleDirectedStack(directed));
        System.out.println("Directed Kahn cycle? " + hasCycleDirectedKahn(directed));
        System.out.println("One directed cycle: " + findDirectedCycle(directed));
    }
}
```

---

## 📌 14. Interview Pattern Map

### Pattern A: "Is this graph a tree?"

A graph is a tree if:

- it is connected
- and it has no cycle

So typical checks are:

- cycle detection
- plus connectivity

### Pattern B: "Can all courses be completed?"

This is a **directed cycle detection** problem.

Use:

- 3-color DFS
- or Kahn's algorithm

### Pattern C: "Find redundant connection"

This is often:

- undirected
- edge list
- DSU is ideal

### Pattern D: "Return one cycle"

Use DFS with:

- state/color
- parent array

---

## 📌 15. LeetCode Problems to Practice

```text
Easy / Medium
- 141. Linked List Cycle              (same conceptual idea, different structure)
- 684. Redundant Connection           (undirected + DSU)
- 685. Redundant Connection II        (directed variant, trickier)
- 207. Course Schedule                (directed cycle detection)
- 210. Course Schedule II             (cycle + topological order)
- 261. Graph Valid Tree               (connectivity + no cycle)

Good follow-up
- 802. Find Eventual Safe States
- 1559. Detect Cycles in 2D Grid
```

---

## 📌 16. Final Takeaways

```text
Undirected graph:
  visited neighbor that is not parent => cycle

Directed graph:
  edge to GRAY / in-recursion-stack node => cycle

Kahn's algorithm:
  not all nodes processed => cycle

DSU:
  edge joining two nodes already in same set => cycle
```

> 🔥 If you deeply understand this file, you are ready for:
> - connected components
> - bipartite checking
> - topological sort
> - graph validation problems
