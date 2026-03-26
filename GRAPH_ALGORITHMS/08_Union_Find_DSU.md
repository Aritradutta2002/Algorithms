# ╔══════════════════════════════════════════════════════════════╗
# ║           08 — UNION-FIND / DSU                             ║
# ║       Complete Theory + In-Depth Java Implementation        ║
# ╚══════════════════════════════════════════════════════════════╝

---

## 🧭 What You Will Learn in This File

```text
1. What DSU is and what problem it solves
2. Naive parent-tree representation
3. Path compression
4. Union by rank and union by size
5. Why DSU is almost O(1) in practice
6. Counting connected components
7. Detecting cycles in undirected graphs
8. Dynamic connectivity queries
9. Complete Java implementations
10. Common mistakes and interview patterns
```

---

## 📌 1. What Is Union-Find?

**Union-Find**, also called **Disjoint Set Union (DSU)**, is a data structure used to maintain a collection of disjoint groups.

It supports two core operations:

- `find(x)` -> returns the representative of the set containing `x`
- `union(a, b)` -> merges the sets containing `a` and `b`

### Intuition

Imagine each connected component as a group:

```text
{0} {1} {2} {3} {4}
```

After unions:

```text
union(0,1) => {0,1} {2} {3} {4}
union(1,2) => {0,1,2} {3} {4}
union(3,4) => {0,1,2} {3,4}
```

Then:

```text
find(0) == find(2)  => same group
find(0) != find(4)  => different groups
```

---

## 📌 2. Why DSU Matters

DSU is extremely useful when:

- edges arrive one by one
- we need repeated connectivity queries
- graph is given as edge list
- we want very fast merging of groups

It appears in:

- connected components
- cycle detection
- Kruskal's algorithm
- redundant connection problems
- grouping and merging tasks

---

## 📌 3. Naive Tree Representation

DSU usually stores each set as a rooted tree.

### Parent array

```text
parent[x] = parent of node x
```

If:

```text
parent[x] == x
```

then `x` is the root of its set.

### Example

```text
parent = [0, 0, 1, 3, 3]
```

Means:

```text
0 is root of {0,1,2}
3 is root of {3,4}
```

Because:

```text
2 -> 1 -> 0
4 -> 3
```

---

## 📌 4. The Problem with Naive DSU

Without optimization, the trees can become very tall:

```text
0 <- 1 <- 2 <- 3 <- 4 <- 5
```

Then:

```text
find(5)
```

must walk through many parent links, which can become `O(N)`.

We fix this with two major optimizations.

---

## 📌 5. Path Compression

### Idea

When we call `find(x)`, we make every node on the path point directly to the root.

### Before

```text
5 -> 4 -> 3 -> 2 -> 1 -> 0
```

### After `find(5)`

```text
5 -> 0
4 -> 0
3 -> 0
2 -> 0
1 -> 0
```

The tree becomes much flatter.

### Standard code

```java
int find(int x) {
    if (parent[x] != x) {
        parent[x] = find(parent[x]);
    }
    return parent[x];
}
```

This is one of the most important DSU lines in all interview prep.

---

## 📌 6. Union by Rank

### Idea

Attach the shallower tree under the deeper tree.

`rank[root]` is an upper bound on tree height.

This prevents trees from becoming unnecessarily tall.

### Rule

```text
If rank[rootA] < rank[rootB], attach A under B
If rank[rootA] > rank[rootB], attach B under A
If equal, choose one and increase its rank
```

---

## 📌 7. Union by Size

Instead of height, we can track:

```text
size[root] = number of elements in that component
```

Then always attach the smaller component under the larger component.

This is often even more practical because:

- it keeps trees shallow
- it also gives you component size information for free

---

## 📌 8. Why DSU Is "Almost O(1)"

With:

- path compression
- union by rank or size

the amortized complexity becomes:

```text
O(alpha(N))
```

where `alpha` is the inverse Ackermann function.

For all practical input sizes, this behaves like constant time.

So we often casually say DSU operations are:

```text
almost O(1)
```

---

## 📌 9. DSU for Connected Components

Initially:

```text
components = n
```

Every successful union merges two different components, so:

```text
components--
```

At the end:

```text
components = number of connected components
```

This is a very important pattern.

---

## 📌 10. DSU for Cycle Detection in Undirected Graphs

Process edges one by one:

- if two endpoints are already in the same set, that edge creates a cycle
- otherwise union them

### Example

```text
Edges:
(0,1), (1,2), (2,0)

After first two unions, 0,1,2 are already connected.
So edge (2,0) closes a cycle.
```

This is the cleanest DSU graph application.

---

## 📌 11. Dynamic Connectivity

Suppose queries arrive like:

```text
union(1, 4)
connected(1, 7)?
union(4, 7)
connected(1, 7)?
```

DFS/BFS would be too expensive if repeated many times.

DSU handles this naturally.

This is why DSU is so powerful for online edge processing.

---

## 📌 12. Common Mistakes

### Mistake 1: Calling union without find

Always union the roots, not raw nodes.

### Mistake 2: Forgetting path compression

Then performance can degrade badly.

### Mistake 3: Using DSU for general directed graph cycle detection

DSU works cleanly for undirected cycle detection, not for arbitrary directed cycle detection.

### Mistake 4: Forgetting 1-indexed vs 0-indexed nodes

Many problems use nodes `1..n`, so allocate `n + 1`.

---

## 📌 13. Complete Java Implementation

```java
import java.util.*;

public class UnionFindDSU {

    static class DSU {
        private final int[] parent;
        private final int[] size;
        private int components;

        public DSU(int n) {
            parent = new int[n];
            size = new int[n];
            components = n;

            for (int i = 0; i < n; i++) {
                parent[i] = i;
                size[i] = 1;
            }
        }

        public int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]);
            }
            return parent[x];
        }

        public boolean union(int a, int b) {
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

        public boolean connected(int a, int b) {
            return find(a) == find(b);
        }

        public int componentSize(int x) {
            return size[find(x)];
        }

        public int components() {
            return components;
        }
    }

    // ============================================================
    // COUNT CONNECTED COMPONENTS
    // ============================================================
    public static int countComponents(int n, int[][] edges) {
        DSU dsu = new DSU(n);

        for (int[] edge : edges) {
            dsu.union(edge[0], edge[1]);
        }

        return dsu.components();
    }

    // ============================================================
    // DETECT CYCLE IN UNDIRECTED GRAPH
    // ============================================================
    public static boolean hasCycle(int n, int[][] edges) {
        DSU dsu = new DSU(n);

        for (int[] edge : edges) {
            if (!dsu.union(edge[0], edge[1])) {
                return true;
            }
        }
        return false;
    }

    // ============================================================
    // REDUNDANT CONNECTION
    // nodes are 1-indexed in this classic problem
    // ============================================================
    public static int[] findRedundantConnection(int[][] edges) {
        int n = edges.length;
        DSU dsu = new DSU(n + 1);

        for (int[] edge : edges) {
            if (!dsu.union(edge[0], edge[1])) {
                return edge;
            }
        }
        return new int[0];
    }

    // ============================================================
    // NETWORK CONNECTIVITY STYLE PROBLEM
    // Returns minimum extra cable moves needed, or -1 if impossible
    // ============================================================
    public static int makeConnected(int n, int[][] connections) {
        if (connections.length < n - 1) {
            return -1;
        }

        DSU dsu = new DSU(n);
        for (int[] edge : connections) {
            dsu.union(edge[0], edge[1]);
        }

        return dsu.components() - 1;
    }

    public static void main(String[] args) {
        int[][] edges = {
            {0, 1}, {1, 2}, {3, 4}
        };

        System.out.println("Connected components: " + countComponents(6, edges));
        System.out.println("Has cycle? " + hasCycle(6, edges));

        int[][] cycleEdges = {
            {1, 2}, {1, 3}, {2, 3}
        };
        System.out.println("Redundant edge: " + Arrays.toString(findRedundantConnection(cycleEdges)));

        DSU dsu = new DSU(6);
        dsu.union(0, 1);
        dsu.union(1, 2);
        dsu.union(4, 5);

        System.out.println("0 connected to 2? " + dsu.connected(0, 2));
        System.out.println("2 connected to 5? " + dsu.connected(2, 5));
        System.out.println("Size of component containing 1: " + dsu.componentSize(1));
        System.out.println("Current components: " + dsu.components());
    }
}
```

---

## 📌 14. Interview Pattern Map

### Pattern A: Count connected components from edge list

Use DSU directly.

### Pattern B: Redundant connection

First edge whose endpoints already share the same root is the answer.

### Pattern C: Dynamic connectivity queries

DSU is much better than rerunning DFS every time.

### Pattern D: Kruskal's algorithm

DSU is the backbone of MST construction because it quickly tells whether adding an edge would create a cycle.

---

## 📌 15. LeetCode Problems to Practice

```text
- 323. Number of Connected Components in an Undirected Graph
- 684. Redundant Connection
- 1319. Number of Operations to Make Network Connected
- 947. Most Stones Removed with Same Row or Column
- 990. Satisfiability of Equality Equations
- 1061. Lexicographically Smallest Equivalent String
```

---

## 📌 16. Final Takeaways

```text
DSU maintains disjoint groups efficiently.

find(x):
  returns representative root

union(a, b):
  merges groups if different

Path compression + union by size/rank:
  makes DSU extremely fast in practice
```

> 🔥 DSU is one of the most reusable data structures in graph and grouping problems.
