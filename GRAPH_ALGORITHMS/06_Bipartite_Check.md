# ╔══════════════════════════════════════════════════════════════╗
# ║           06 — BIPARTITE CHECK                              ║
# ║       Complete Theory + In-Depth Java Implementation        ║
# ╚══════════════════════════════════════════════════════════════╝

---

## 🧭 What You Will Learn in This File

```text
1. What a bipartite graph is
2. The 2-coloring viewpoint
3. The odd-cycle theorem
4. BFS and DFS solutions
5. Why trees are always bipartite
6. Why odd cycles break bipartite graphs
7. How to return the two partitions
8. Common edge cases and mistakes
9. Complete Java implementations
10. Interview patterns and LeetCode mapping
```

---

## 📌 1. What Is a Bipartite Graph?

A graph is **bipartite** if its vertices can be split into **two disjoint sets** such that every edge goes from one set to the other.

That means:

- no edge connects two vertices inside the first set
- no edge connects two vertices inside the second set

### Visual intuition

```text
Bipartite graph:

Left set        Right set
   0  -------->    1
   2  -------->    3
   0  -------->    3
   2  -------->    1
```

If you can color the graph using only **two colors** so that adjacent nodes have different colors, the graph is bipartite.

So these statements are equivalent:

- graph is bipartite
- graph is 2-colorable
- graph has no odd cycle

---

## 📌 2. Why Bipartite Graphs Matter

Bipartite graphs appear in:

- matching problems
- scheduling with alternating constraints
- graph coloring basics
- team partitioning
- "possible bipartition" interview problems

Examples:

- students and clubs
- workers and tasks
- users and groups
- men and women in matching problems

---

## 📌 3. The Core Theorem

> A graph is bipartite **if and only if** it contains **no odd-length cycle**.

This theorem is one of the most important facts in graph theory interviews.

### Why odd cycles break bipartite structure

Suppose we alternate colors:

```text
Red, Blue, Red, Blue, ...
```

In an odd cycle:

```text
0 -- 1 -- 2 -- 0
```

Try coloring:

```text
0 = Red
1 = Blue
2 = Red
```

But then edge `2 -- 0` connects `Red -- Red`, which is forbidden.

So odd cycles make bipartite coloring impossible.

### Why even cycles are okay

```text
0 -- 1 -- 2 -- 3 -- 0
```

Color:

```text
0 = Red
1 = Blue
2 = Red
3 = Blue
```

Every edge connects opposite colors.

---

## 📌 4. Trees Are Always Bipartite

A tree has no cycle at all, so in particular it has no odd cycle.

Therefore:

> Every tree is bipartite.

In fact, if you root a tree anywhere:

- even depth nodes can be one color
- odd depth nodes can be the other color

This is why many tree partition problems secretly rely on bipartite structure.

---

## 📌 5. Core Algorithm: 2-Coloring

We try to assign each node one of two colors:

```text
0  = uncolored
1  = first color
-1 = second color
```

Rule:

- if node is color `1`, every neighbor must be `-1`
- if node is color `-1`, every neighbor must be `1`

If we ever find:

```text
color[node] == color[neighbor]
```

then the graph is not bipartite.

---

## 📌 6. Bipartite Check Using BFS

### Why BFS works nicely

BFS explores level by level.
In an undirected graph, nodes at alternate levels should get alternate colors.

### Pseudocode

```text
isBipartite(graph):
  color[] = 0

  for each node u:
    if color[u] == 0:
      color[u] = 1
      BFS from u

      for each edge (x, y) discovered:
        if color[y] == 0:
          color[y] = -color[x]
        else if color[y] == color[x]:
          return false

  return true
```

### Complexity

```text
Time:  O(V + E)
Space: O(V)
```

---

## 📌 7. Bipartite Check Using DFS

DFS works the same way:

- color the current node
- recursively color neighbors with opposite color
- if a conflict appears, return false

This is equally correct and equally common.

### BFS vs DFS

| Topic | BFS | DFS |
|------|------|------|
| Style | Iterative | Recursive |
| Risk of stack overflow | No | Possible on very deep graphs |
| Interview acceptance | Excellent | Excellent |

---

## 📌 8. Disconnected Graphs

This is a very common source of bugs.

The graph may have multiple components:

```text
Component 1: bipartite
Component 2: not bipartite
```

If you only start from node `0`, you may miss the bad component.

So the outer loop over all nodes is mandatory.

---

## 📌 9. Special Cases

### Self-loop

```text
0 -- 0
```

This is immediately not bipartite, because a node would need to have the opposite color of itself.

### Isolated node

A single isolated node is trivially bipartite.

### Even cycle

Always bipartite.

### Odd cycle

Never bipartite.

---

## 📌 10. Returning the Two Partitions

Sometimes the problem does not just ask:

```text
Is it bipartite?
```

It asks for the actual groups.

Once coloring succeeds:

- all nodes with color `1` belong to group A
- all nodes with color `-1` belong to group B

Example:

```text
color = [1, -1, 1, -1, 1]

Group A = [0, 2, 4]
Group B = [1, 3]
```

---

## 📌 11. Common Mistakes

### Mistake 1: Forgetting disconnected components

Always process all nodes.

### Mistake 2: Using `visited[]` only

You need actual color assignment, not just visited state.

### Mistake 3: Assuming every acyclic graph is the only bipartite kind

Even cycles are also bipartite.

### Mistake 4: Forgetting self-loops

A self-loop makes the graph non-bipartite immediately.

---

## 📌 12. Complete Java Implementation

```java
import java.util.*;

public class BipartiteCheck {

    // ============================================================
    // BFS 2-COLORING
    // color[node] = 0  -> uncolored
    // color[node] = 1  -> first set
    // color[node] = -1 -> second set
    // ============================================================
    public static boolean isBipartiteBFS(List<List<Integer>> graph) {
        int n = graph.size();
        int[] color = new int[n];

        for (int start = 0; start < n; start++) {
            if (color[start] != 0) {
                continue;
            }

            Queue<Integer> queue = new ArrayDeque<>();
            queue.offer(start);
            color[start] = 1;

            while (!queue.isEmpty()) {
                int node = queue.poll();

                for (int neighbor : graph.get(node)) {
                    if (color[neighbor] == 0) {
                        color[neighbor] = -color[node];
                        queue.offer(neighbor);
                    } else if (color[neighbor] == color[node]) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    // ============================================================
    // DFS 2-COLORING
    // ============================================================
    public static boolean isBipartiteDFS(List<List<Integer>> graph) {
        int n = graph.size();
        int[] color = new int[n];

        for (int node = 0; node < n; node++) {
            if (color[node] == 0 && !dfsColor(graph, node, 1, color)) {
                return false;
            }
        }
        return true;
    }

    private static boolean dfsColor(List<List<Integer>> graph, int node, int currentColor, int[] color) {
        color[node] = currentColor;

        for (int neighbor : graph.get(node)) {
            if (color[neighbor] == 0) {
                if (!dfsColor(graph, neighbor, -currentColor, color)) {
                    return false;
                }
            } else if (color[neighbor] == currentColor) {
                return false;
            }
        }

        return true;
    }

    // ============================================================
    // RETURN THE ACTUAL TWO PARTITIONS
    // Returns empty list if graph is not bipartite
    // result.get(0) = first group
    // result.get(1) = second group
    // ============================================================
    public static List<List<Integer>> getPartitions(List<List<Integer>> graph) {
        int n = graph.size();
        int[] color = new int[n];

        for (int start = 0; start < n; start++) {
            if (color[start] != 0) {
                continue;
            }

            Queue<Integer> queue = new ArrayDeque<>();
            queue.offer(start);
            color[start] = 1;

            while (!queue.isEmpty()) {
                int node = queue.poll();

                for (int neighbor : graph.get(node)) {
                    if (color[neighbor] == 0) {
                        color[neighbor] = -color[node];
                        queue.offer(neighbor);
                    } else if (color[neighbor] == color[node]) {
                        return new ArrayList<>();
                    }
                }
            }
        }

        List<Integer> left = new ArrayList<>();
        List<Integer> right = new ArrayList<>();

        for (int node = 0; node < n; node++) {
            if (color[node] == 1) {
                left.add(node);
            } else if (color[node] == -1) {
                right.add(node);
            } else {
                left.add(node);
            }
        }

        List<List<Integer>> result = new ArrayList<>();
        result.add(left);
        result.add(right);
        return result;
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
        int[][] edges1 = {
            {0, 1}, {1, 2}, {2, 3}, {3, 0}
        };
        List<List<Integer>> graph1 = buildGraph(4, edges1);

        System.out.println("Graph 1 bipartite (BFS)? " + isBipartiteBFS(graph1));
        System.out.println("Graph 1 bipartite (DFS)? " + isBipartiteDFS(graph1));
        System.out.println("Graph 1 partitions: " + getPartitions(graph1));

        int[][] edges2 = {
            {0, 1}, {1, 2}, {2, 0}
        };
        List<List<Integer>> graph2 = buildGraph(3, edges2);

        System.out.println("Graph 2 bipartite (BFS)? " + isBipartiteBFS(graph2));
        System.out.println("Graph 2 bipartite (DFS)? " + isBipartiteDFS(graph2));
        System.out.println("Graph 2 partitions: " + getPartitions(graph2));
    }
}
```

---

## 📌 13. Interview Pattern Map

### Pattern A: Possible Bipartition

This is directly a bipartite check.

### Pattern B: Graph Coloring with Two Groups

Use 2-coloring.

### Pattern C: Detect odd cycle indirectly

If bipartite check fails, some odd cycle exists.

### Pattern D: Tree depth parity

Trees are always bipartite, and levels naturally define the partition.

---

## 📌 14. LeetCode Problems to Practice

```text
- 785. Is Graph Bipartite?
- 886. Possible Bipartition
- 886 is especially important because it looks like a partition problem,
  but the real core is graph bipartite checking.
```

---

## 📌 15. Final Takeaways

```text
Bipartite = 2-colorable = no odd cycle

Use BFS or DFS:
  color uncolored nodes
  neighbors must get opposite colors
  same-color adjacent nodes => not bipartite

Always handle disconnected graphs.
```

> 🔥 Once this idea becomes natural, many "divide into two groups" problems become easy to spot.
