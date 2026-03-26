# ╔══════════════════════════════════════════════════════════════╗
# ║           07 — TOPOLOGICAL SORT                             ║
# ║       Complete Theory + In-Depth Java Implementation        ║
# ╚══════════════════════════════════════════════════════════════╝

---

## 🧭 What You Will Learn in This File

```text
1. What topological sorting really means
2. Why it only works on DAGs
3. Kahn's algorithm (BFS + in-degree)
4. DFS finish-time / post-order method
5. How cycle detection is connected to topo sort
6. Why topological order is usually not unique
7. How to detect whether the order is unique
8. Lexicographically smallest topological order
9. Course Schedule style problems
10. Complete Java implementations
11. Common mistakes and interview patterns
```

---

## 📌 1. What Is Topological Sort?

A **topological order** of a directed graph is a linear ordering of vertices such that for every directed edge:

```text
u -> v
```

vertex `u` appears **before** vertex `v` in the ordering.

### Core interpretation

If `u -> v`, then:

- `u` must happen first
- `v` depends on `u`

So topological sort is the natural ordering for:

- prerequisites
- dependency graphs
- build systems
- task scheduling

---

## 📌 2. Topological Sort Exists Only for DAGs

Topological sorting is defined only for a **Directed Acyclic Graph (DAG)**.

Why?

Because if a cycle exists:

```text
0 -> 1 -> 2 -> 0
```

then the ordering requirements become impossible:

- `0` before `1`
- `1` before `2`
- `2` before `0`

This creates a contradiction.

So:

> A directed graph has a topological order **if and only if** it is acyclic.

---

## 📌 3. Real-World Intuition

### Course scheduling

```text
Math -> Algorithms -> Machine Learning
```

You cannot take Machine Learning before Algorithms.

### Build systems

```text
Parser.java -> Compiler.java -> App.java
```

A dependent module must be built after the modules it depends on.

### Spreadsheet formulas

If cell `B1` uses `A1`, then `A1` must be evaluated first.

---

## 📌 4. Kahn's Algorithm (BFS + In-Degree)

This is the cleanest and most interview-friendly topological sort.

### Key idea

A node with **in-degree 0** has no prerequisites, so it is ready to be placed next in the order.

### Algorithm

1. Compute in-degree of every node
2. Put all nodes with in-degree `0` into a queue
3. Repeatedly pop from queue
4. Add popped node to result
5. Remove its outgoing edges by decrementing neighbors' in-degree
6. If any neighbor becomes `0`, enqueue it

### Pseudocode

```text
kahn(graph):
  compute inDegree[]
  queue = all nodes with inDegree = 0
  result = []

  while queue not empty:
    u = queue.pop()
    add u to result

    for v in graph[u]:
      inDegree[v]--
      if inDegree[v] == 0:
        queue.push(v)

  if result size != V:
    cycle exists
  else:
    result is a valid topological ordering
```

---

## 📌 5. Why Kahn's Algorithm Works

At every step, we choose a node with no remaining prerequisites.

That is always safe because:

- no unresolved dependency points into it
- placing it now cannot violate any edge rule

When we remove that node, we conceptually remove all outgoing edges.
This may unlock more nodes.

This "unlocking" idea is exactly why topological sort is so useful for dependency problems.

---

## 📌 6. Kahn's Algorithm Trace

```text
Edges:
5 -> 0
5 -> 2
4 -> 0
4 -> 1
2 -> 3
3 -> 1
```

### Initial in-degrees

```text
0: 2
1: 2
2: 1
3: 1
4: 0
5: 0
```

### Start queue

```text
[4, 5]
```

Possible execution:

```text
pop 4 -> result [4]
  reduce indegree of 0, 1

pop 5 -> result [4, 5]
  reduce indegree of 0 and 2
  now 0 and 2 become zero

queue [0, 2]
pop 0 -> result [4, 5, 0]
pop 2 -> result [4, 5, 0, 2]
  reduce indegree of 3 to zero

pop 3 -> result [4, 5, 0, 2, 3]
  reduce indegree of 1 to zero

pop 1 -> result [4, 5, 0, 2, 3, 1]
```

Valid topological order found.

---

## 📌 7. DFS Finish-Time Method

Topological sort can also be built using DFS.

### Core idea

In DFS, a node should be placed in the order **after** all its outgoing neighbors have been processed.

That means:

- recurse first
- push node after finishing all descendants

Then reverse the finishing order.

### Why this works

If there is an edge:

```text
u -> v
```

then DFS ensures `v` is finished before `u` is pushed, so reversing finish order places `u` before `v`.

### Important warning

Plain DFS post-order only gives a valid topological order if the graph is a DAG.
To detect cycles, combine DFS with a 3-color state array.

---

## 📌 8. Kahn vs DFS

| Topic | Kahn | DFS |
|------|------|------|
| Style | BFS + in-degree | DFS + finish order |
| Cycle detection | Natural via size check | Need recursion-stack / color logic |
| Ease in interviews | Usually easiest | Good if you know DFS deeply |
| Extra structure needed | in-degree array | recursion / stack |

> 💡 Recommendation:
> Learn **Kahn first**, then learn DFS-based topo sort as the theoretical companion.

---

## 📌 9. Why Topological Order Is Not Unique

A DAG can have multiple valid orders.

Example:

```text
0 -> 2
1 -> 2
```

Valid orders:

```text
[0, 1, 2]
[1, 0, 2]
```

Both satisfy the rule that `0` and `1` come before `2`.

### When is the order unique?

In Kahn's algorithm, if the queue ever contains more than one node, then there are multiple choices, so uniqueness is not guaranteed.

If at every step there is exactly one available node, the order is unique.

---

## 📌 10. Lexicographically Smallest Topological Order

Sometimes a problem wants the smallest valid order.

Then instead of a normal queue, use a:

```text
PriorityQueue
```

So whenever multiple zero in-degree nodes are available, we always choose the smallest one.

This is a very useful contest and interview variant.

---

## 📌 11. Cycle Detection Through Topological Sort

Kahn's algorithm doubles as cycle detection:

```text
If processed node count < total nodes, a cycle exists.
```

This is the exact idea behind:

- Course Schedule
- dependency validation
- build graph checking

---

## 📌 12. Common Mistakes

### Mistake 1: Trying topological sort on an undirected graph

Topological sorting is for **directed** graphs.

### Mistake 2: Forgetting to detect cycles

If the graph has a cycle, there is no valid topological order.

### Mistake 3: Thinking the order is unique

Most DAGs have multiple valid topological orders.

### Mistake 4: Mixing edge direction in prerequisite problems

If course `A` depends on `B`, the graph should be:

```text
B -> A
```

not `A -> B`.

---

## 📌 13. Complete Java Implementation

```java
import java.util.*;

public class TopologicalSort {

    // ============================================================
    // KAHN'S ALGORITHM
    // Returns empty list if cycle exists
    // ============================================================
    public static List<Integer> kahnSort(List<List<Integer>> graph) {
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

        List<Integer> order = new ArrayList<>();
        while (!queue.isEmpty()) {
            int node = queue.poll();
            order.add(node);

            for (int neighbor : graph.get(node)) {
                inDegree[neighbor]--;
                if (inDegree[neighbor] == 0) {
                    queue.offer(neighbor);
                }
            }
        }

        if (order.size() != n) {
            return new ArrayList<>();
        }
        return order;
    }

    // ============================================================
    // LEXICOGRAPHICALLY SMALLEST TOPOLOGICAL ORDER
    // ============================================================
    public static List<Integer> kahnSmallestOrder(List<List<Integer>> graph) {
        int n = graph.size();
        int[] inDegree = new int[n];

        for (int u = 0; u < n; u++) {
            for (int v : graph.get(u)) {
                inDegree[v]++;
            }
        }

        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for (int node = 0; node < n; node++) {
            if (inDegree[node] == 0) {
                pq.offer(node);
            }
        }

        List<Integer> order = new ArrayList<>();
        while (!pq.isEmpty()) {
            int node = pq.poll();
            order.add(node);

            for (int neighbor : graph.get(node)) {
                inDegree[neighbor]--;
                if (inDegree[neighbor] == 0) {
                    pq.offer(neighbor);
                }
            }
        }

        if (order.size() != n) {
            return new ArrayList<>();
        }
        return order;
    }

    // ============================================================
    // CHECK IF TOPOLOGICAL ORDER IS UNIQUE
    // ============================================================
    public static boolean hasUniqueTopologicalOrder(List<List<Integer>> graph) {
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

        int visited = 0;
        while (!queue.isEmpty()) {
            if (queue.size() > 1) {
                return false;
            }

            int node = queue.poll();
            visited++;

            for (int neighbor : graph.get(node)) {
                inDegree[neighbor]--;
                if (inDegree[neighbor] == 0) {
                    queue.offer(neighbor);
                }
            }
        }

        return visited == n;
    }

    // ============================================================
    // DFS-BASED TOPOLOGICAL SORT WITH CYCLE DETECTION
    // Returns empty list if cycle exists
    // ============================================================
    public static List<Integer> dfsTopologicalSort(List<List<Integer>> graph) {
        int n = graph.size();
        int[] state = new int[n];
        Deque<Integer> stack = new ArrayDeque<>();

        for (int node = 0; node < n; node++) {
            if (state[node] == 0 && !dfs(graph, node, state, stack)) {
                return new ArrayList<>();
            }
        }

        List<Integer> order = new ArrayList<>();
        while (!stack.isEmpty()) {
            order.add(stack.pop());
        }
        return order;
    }

    private static boolean dfs(List<List<Integer>> graph, int node, int[] state, Deque<Integer> stack) {
        state[node] = 1;

        for (int neighbor : graph.get(node)) {
            if (state[neighbor] == 1) {
                return false;
            }
            if (state[neighbor] == 0 && !dfs(graph, neighbor, state, stack)) {
                return false;
            }
        }

        state[node] = 2;
        stack.push(node);
        return true;
    }

    // ============================================================
    // COURSE SCHEDULE: CAN FINISH?
    // prerequisites[i] = [course, prerequisite]
    // ============================================================
    public static boolean canFinish(int numCourses, int[][] prerequisites) {
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < numCourses; i++) {
            graph.add(new ArrayList<>());
        }

        for (int[] p : prerequisites) {
            int course = p[0];
            int prerequisite = p[1];
            graph.get(prerequisite).add(course);
        }

        return kahnSort(graph).size() == numCourses;
    }

    // ============================================================
    // COURSE SCHEDULE II: RETURN AN ORDER
    // ============================================================
    public static int[] findOrder(int numCourses, int[][] prerequisites) {
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < numCourses; i++) {
            graph.add(new ArrayList<>());
        }

        for (int[] p : prerequisites) {
            int course = p[0];
            int prerequisite = p[1];
            graph.get(prerequisite).add(course);
        }

        List<Integer> order = kahnSort(graph);
        if (order.size() != numCourses) {
            return new int[0];
        }

        int[] result = new int[numCourses];
        for (int i = 0; i < numCourses; i++) {
            result[i] = order.get(i);
        }
        return result;
    }

    // ============================================================
    // HELPER TO BUILD A DIRECTED GRAPH
    // ============================================================
    public static List<List<Integer>> buildGraph(int n, int[][] edges) {
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        for (int[] edge : edges) {
            graph.get(edge[0]).add(edge[1]);
        }
        return graph;
    }

    public static void main(String[] args) {
        int[][] edges = {
            {5, 0}, {5, 2}, {4, 0}, {4, 1}, {2, 3}, {3, 1}
        };

        List<List<Integer>> graph = buildGraph(6, edges);

        System.out.println("Kahn order: " + kahnSort(graph));
        System.out.println("Smallest Kahn order: " + kahnSmallestOrder(graph));
        System.out.println("DFS order: " + dfsTopologicalSort(graph));
        System.out.println("Unique order? " + hasUniqueTopologicalOrder(graph));

        int[][] prerequisites = {
            {1, 0}, {2, 0}, {3, 1}, {3, 2}
        };
        System.out.println("Can finish courses? " + canFinish(4, prerequisites));
        System.out.println("One course order: " + Arrays.toString(findOrder(4, prerequisites)));
    }
}
```

---

## 📌 14. Interview Pattern Map

### Pattern A: Course Schedule

Directed cycle detection + topological sort.

### Pattern B: Build Dependencies

Dependency ordering problem.

### Pattern C: Alien Dictionary

Construct directed edges from character precedence, then topologically sort.

### Pattern D: Find one valid order / smallest valid order

Normal queue gives any valid order.
Priority queue gives the smallest available choice each step.

---

## 📌 15. LeetCode Problems to Practice

```text
- 207. Course Schedule
- 210. Course Schedule II
- 269. Alien Dictionary
- 444. Sequence Reconstruction
- 1203. Sort Items by Groups Respecting Dependencies
```

---

## 📌 16. Final Takeaways

```text
Topological sort = linear ordering of a DAG

Kahn's algorithm:
  process zero in-degree nodes
  if processed count < V, cycle exists

DFS method:
  reverse finish order
  needs cycle detection logic for correctness
```

> 🔥 If you understand this topic well, dependency graph problems become much easier to recognize and solve.
