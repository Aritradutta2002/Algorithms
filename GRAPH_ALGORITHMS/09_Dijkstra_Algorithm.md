# ╔══════════════════════════════════════════════════════════════╗
# ║           09 — DIJKSTRA'S ALGORITHM                         ║
# ║       Complete Theory + In-Depth Java Implementation        ║
# ╚══════════════════════════════════════════════════════════════╝

---

## 🧭 What You Will Learn in This File

```text
1. What shortest path means in weighted graphs
2. Why Dijkstra requires non-negative weights
3. Greedy idea behind the algorithm
4. Priority queue / min-heap implementation
5. The stale-entry trick in Java
6. Path reconstruction with parent array
7. Dijkstra for all nodes vs single destination
8. Adjacency list vs adjacency matrix complexity
9. When not to use Dijkstra
10. Complete Java implementations
11. Common mistakes and interview patterns
```

---

## 📌 1. What Problem Does Dijkstra Solve?

Dijkstra's algorithm finds the **shortest path distances from one source node to all other nodes** in a weighted graph with **non-negative edge weights**.

### Example

```text
0 --4--> 1
|        |
2        5
v        v
2 --1--> 3
```

Possible shortest distances from source `0`:

```text
dist[0] = 0
dist[1] = 4
dist[2] = 2
dist[3] = 3   via 0 -> 2 -> 3
```

This is the weighted version of what BFS does for unweighted graphs.

---

## 📌 2. Why BFS Is Not Enough

In an unweighted graph, each edge costs the same, so the fewest edges means the shortest path.

In a weighted graph, edge counts alone are not enough.

### Example

```text
0 -> 1 (100)
0 -> 2 (1)
2 -> 1 (1)
```

Path with fewer edges:

```text
0 -> 1   cost 100
```

Path with more edges:

```text
0 -> 2 -> 1   cost 2
```

So we need an algorithm based on total weight, not just hop count.

---

## 📌 3. Core Greedy Idea

Dijkstra repeatedly chooses the not-yet-finalized node with the smallest current known distance.

Why is that safe?

Because all edge weights are non-negative.

If the current best node has distance `d`, then any alternative route through an unprocessed node would add at least `0` more, so it cannot create a smaller path than `d`.

This is the key greedy invariant.

---

## 📌 4. Why Non-Negative Weights Are Required

If negative edges exist, a node that looked "final" might later get improved.

That breaks Dijkstra's greedy logic.

### Example

```text
0 -> 1 (5)
0 -> 2 (10)
2 -> 1 (-10)
```

At first, node `1` looks reachable with cost `5`.
But later:

```text
0 -> 2 -> 1 = 10 + (-10) = 0
```

So the earlier "final" distance was not actually final.

> ⚠️ For graphs with negative weights, use **Bellman-Ford**, not Dijkstra.

---

## 📌 5. Relaxation: The Heart of Shortest Path Algorithms

For every edge:

```text
u -> v with weight w
```

we try to improve `dist[v]` using:

```text
dist[u] + w
```

This update is called **relaxation**.

### Relaxation rule

```text
if dist[u] + w < dist[v]:
    dist[v] = dist[u] + w
```

Dijkstra is basically:

- pick the current best node
- relax all outgoing edges
- repeat

---

## 📌 6. Priority Queue / Min-Heap View

We need to quickly pick the node with the smallest known distance.

So we use a **min-heap**.

In Java:

```java
PriorityQueue<long[]> pq = new PriorityQueue<>(Comparator.comparingLong(a -> a[1]));
```

Each heap entry stores:

```text
[node, distance]
```

---

## 📌 7. The "Stale Entry" Trick in Java

Java's `PriorityQueue` does not support efficient decrease-key.

So when we find a better distance for a node:

- we push a new pair into the heap
- the old worse pair remains there

This creates duplicate entries.

### How we handle that

When we pop:

```java
if (currentDistance != dist[node]) continue;
```

or:

```java
if (currentDistance > dist[node]) continue;
```

Then we ignore outdated heap entries.

This is called the **stale entry check**, and it is extremely important in real Java implementations.

---

## 📌 8. Detailed Trace

```text
Edges:
0 -> 1 (4)
0 -> 2 (1)
2 -> 1 (2)
1 -> 3 (1)
2 -> 3 (5)
```

### Start

```text
dist = [0, INF, INF, INF]
pq = [(0,0)]
```

### Pop (0,0)

Relax neighbors:

```text
dist[1] = 4
dist[2] = 1
pq = [(2,1), (1,4)]
```

### Pop (2,1)

Relax:

```text
2 -> 1 (2) => dist[1] = min(4, 1+2) = 3
2 -> 3 (5) => dist[3] = 6
pq = [(1,3), (1,4), (3,6)]
```

### Pop (1,3)

Relax:

```text
1 -> 3 (1) => dist[3] = min(6, 3+1) = 4
pq = [(1,4), (3,6), (3,4)]
```

### Pop (1,4)

This is stale because:

```text
dist[1] is already 3
```

So skip.

### Pop (3,4)

Done.

Final distances:

```text
[0, 3, 1, 4]
```

---

## 📌 9. Path Reconstruction

To return the actual shortest path, keep:

```text
parent[v] = previous node on shortest path to v
```

Whenever you improve `dist[v]`, update `parent[v]`.

Then reconstruct by walking backward from destination to source and reversing the list.

### Example

If:

```text
parent[3] = 1
parent[1] = 2
parent[2] = 0
parent[0] = -1
```

Then shortest path from `0` to `3` is:

```text
0 -> 2 -> 1 -> 3
```

---

## 📌 10. Complexity

### Adjacency list + min-heap

```text
Time:  O((V + E) log V)
Space: O(V + E)
```

This is the standard version for sparse graphs.

### Adjacency matrix + no heap

```text
Time:  O(V^2)
Space: O(V^2)
```

This can be acceptable for dense graphs or small constraints.

---

## 📌 11. When NOT to Use Dijkstra

### Negative edge weights

Use Bellman-Ford.

### Unweighted graph

Use BFS.

### Edge weights only 0 or 1

Use **0-1 BFS**, which is often faster and simpler.

### All-pairs shortest path

Use repeated Dijkstra, Floyd-Warshall, or Johnson's algorithm depending on constraints.

---

## 📌 12. Common Mistakes

### Mistake 1: Using Dijkstra on negative edges

This is the biggest correctness mistake.

### Mistake 2: Forgetting stale-entry check

Then the algorithm may still work slowly or be harder to reason about.

### Mistake 3: Integer overflow

For large weights, use `long` instead of `int`.

### Mistake 4: Building edges in wrong direction

For directed graph problems, be very careful with edge direction.

### Mistake 5: Forgetting unreachable nodes

Their distance remains infinity.

---

## 📌 13. Complete Java Implementation

```java
import java.util.*;

public class DijkstraAlgorithm {

    static class Edge {
        int to;
        int weight;

        Edge(int to, int weight) {
            this.to = to;
            this.weight = weight;
        }
    }

    static class Result {
        long[] dist;
        int[] parent;

        Result(long[] dist, int[] parent) {
            this.dist = dist;
            this.parent = parent;
        }
    }

    // ============================================================
    // STANDARD DIJKSTRA FROM A SINGLE SOURCE
    // ============================================================
    public static long[] dijkstra(List<List<Edge>> graph, int source) {
        return dijkstraWithParent(graph, source).dist;
    }

    // ============================================================
    // DIJKSTRA + PARENT ARRAY FOR PATH RECONSTRUCTION
    // ============================================================
    public static Result dijkstraWithParent(List<List<Edge>> graph, int source) {
        int n = graph.size();
        long[] dist = new long[n];
        int[] parent = new int[n];

        Arrays.fill(dist, Long.MAX_VALUE);
        Arrays.fill(parent, -1);

        dist[source] = 0;

        PriorityQueue<long[]> pq = new PriorityQueue<>(Comparator.comparingLong(a -> a[1]));
        pq.offer(new long[]{source, 0});

        while (!pq.isEmpty()) {
            long[] current = pq.poll();
            int node = (int) current[0];
            long d = current[1];

            if (d != dist[node]) {
                continue;
            }

            for (Edge edge : graph.get(node)) {
                int neighbor = edge.to;
                long newDistance = dist[node] + edge.weight;

                if (newDistance < dist[neighbor]) {
                    dist[neighbor] = newDistance;
                    parent[neighbor] = node;
                    pq.offer(new long[]{neighbor, newDistance});
                }
            }
        }

        return new Result(dist, parent);
    }

    // ============================================================
    // SHORTEST PATH FROM SOURCE TO DESTINATION
    // Returns empty list if destination is unreachable
    // ============================================================
    public static List<Integer> shortestPath(List<List<Edge>> graph, int source, int destination) {
        Result result = dijkstraWithParent(graph, source);

        if (result.dist[destination] == Long.MAX_VALUE) {
            return new ArrayList<>();
        }

        List<Integer> path = new ArrayList<>();
        for (int node = destination; node != -1; node = result.parent[node]) {
            path.add(node);
        }
        Collections.reverse(path);
        return path;
    }

    // ============================================================
    // NETWORK DELAY STYLE PROBLEM
    // Returns the maximum shortest distance from source,
    // or -1 if some node is unreachable
    // ============================================================
    public static int networkDelayTime(int[][] times, int n, int source) {
        List<List<Edge>> graph = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            graph.add(new ArrayList<>());
        }

        for (int[] edge : times) {
            int u = edge[0];
            int v = edge[1];
            int w = edge[2];
            graph.get(u).add(new Edge(v, w));
        }

        long[] dist = dijkstra(graph, source);
        long answer = 0;

        for (int node = 1; node <= n; node++) {
            if (dist[node] == Long.MAX_VALUE) {
                return -1;
            }
            answer = Math.max(answer, dist[node]);
        }

        return (int) answer;
    }

    // ============================================================
    // HELPER TO BUILD A GRAPH
    // directed = true  => add only u -> v
    // directed = false => also add v -> u
    // ============================================================
    public static List<List<Edge>> buildGraph(int n, int[][] edges, boolean directed) {
        List<List<Edge>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            int w = edge[2];

            graph.get(u).add(new Edge(v, w));
            if (!directed) {
                graph.get(v).add(new Edge(u, w));
            }
        }

        return graph;
    }

    public static void main(String[] args) {
        int[][] edges = {
            {0, 1, 4},
            {0, 2, 1},
            {2, 1, 2},
            {1, 3, 1},
            {2, 3, 5}
        };

        List<List<Edge>> graph = buildGraph(4, edges, true);

        long[] dist = dijkstra(graph, 0);
        System.out.println("Distances from 0: " + Arrays.toString(dist));
        System.out.println("Shortest path 0 -> 3: " + shortestPath(graph, 0, 3));

        int[][] times = {
            {2, 1, 1},
            {2, 3, 1},
            {3, 4, 1}
        };
        System.out.println("Network delay time: " + networkDelayTime(times, 4, 2));
    }
}
```

---

## 📌 14. Interview Pattern Map

### Pattern A: Single-source shortest path in weighted graph

Classic Dijkstra.

### Pattern B: Return shortest path, not just distance

Maintain parent array.

### Pattern C: Network Delay Time

Run Dijkstra and take the maximum shortest distance.

### Pattern D: Cheapest route / minimum travel cost

Usually weighted graph shortest path, but always check whether constraints suggest Dijkstra, Bellman-Ford, or Floyd-Warshall.

---

## 📌 15. LeetCode Problems to Practice

```text
- 743. Network Delay Time
- 778. Swim in Rising Water
- 1514. Path with Maximum Probability
- 1631. Path With Minimum Effort
- 1976. Number of Ways to Arrive at Destination
```

---

## 📌 16. Final Takeaways

```text
Dijkstra solves shortest paths from one source
in weighted graphs with non-negative edges.

Core loop:
  extract smallest known distance
  relax outgoing edges
  skip stale heap entries

For actual route:
  maintain parent[]
```

> 🔥 This is one of the most important weighted-graph algorithms in interviews and competitive programming.
