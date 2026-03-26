# Algorithm Complexity Cheatsheet

Quick reference for time and space complexity of all algorithms in this directory.

## Sorting Algorithms

| Algorithm      | Best       | Average    | Worst      | Space    | Stable |
| -------------- | ---------- | ---------- | ---------- | -------- | ------ |
| Bubble Sort    | O(n)       | O(n²)      | O(n²)      | O(1)     | ✅     |
| Selection Sort | O(n²)      | O(n²)      | O(n²)      | O(1)     | ❌     |
| Insertion Sort | O(n)       | O(n²)      | O(n²)      | O(1)     | ✅     |
| Merge Sort     | O(n log n) | O(n log n) | O(n log n) | O(n)     | ✅     |
| Quick Sort     | O(n log n) | O(n log n) | O(n²)      | O(log n) | ❌     |
| Heap Sort      | O(n log n) | O(n log n) | O(n log n) | O(1)     | ❌     |
| Counting Sort  | O(n + k)   | O(n + k)   | O(n + k)   | O(k)     | ✅     |

## Searching Algorithms

| Algorithm     | Best | Average      | Worst     | Space | Prerequisites        |
| ------------- | ---- | ------------ | --------- | ----- | -------------------- |
| Linear Search | O(1) | O(n)         | O(n)      | O(1)  | None                 |
| Binary Search | O(1) | O(log n)     | O(log n)  | O(1)  | Sorted               |
| Jump Search   | O(1) | O(√n)        | O(√n)     | O(1)  | Sorted               |
| Interpolation | O(1) | O(log log n) | O(n)      | O(1)  | Uniform distribution |
| Exponential   | O(1) | O(log n)     | O(log n)  | O(1)  | Sorted               |
| Ternary       | O(1) | O(log₃ n)    | O(log₃ n) | O(1)  | Unimodal             |

## Dynamic Programming

| Problem       | Time       | Space | Pattern            |
| ------------- | ---------- | ----- | ------------------ |
| Fibonacci     | O(n)       | O(1)  | Linear DP          |
| 0/1 Knapsack  | O(n×W)     | O(W)  | 2D DP              |
| LCS           | O(m×n)     | O(n)  | 2D DP              |
| LIS           | O(n log n) | O(n)  | Binary Search + DP |
| Matrix Chain  | O(n³)      | O(n²) | Interval DP        |
| Edit Distance | O(m×n)     | O(n)  | 2D DP              |

## Graph Algorithms

### Traversal

| Algorithm | Time     | Space | Use Case                     |
| --------- | -------- | ----- | ---------------------------- |
| DFS       | O(V + E) | O(V)  | Pathfinding, Cycle detection |
| BFS       | O(V + E) | O(V)  | Shortest path (unweighted)   |

### Shortest Path

| Algorithm      | Time           | Space | Features                          |
| -------------- | -------------- | ----- | --------------------------------- |
| Dijkstra       | O((V+E) log V) | O(V)  | Non-negative weights              |
| Bellman-Ford   | O(V×E)         | O(V)  | Negative weights, cycle detection |
| Floyd-Warshall | O(V³)          | O(V²) | All pairs shortest path           |

### Minimum Spanning Tree

| Algorithm | Time           | Space | Approach                     |
| --------- | -------------- | ----- | ---------------------------- |
| Kruskal   | O(E log E)     | O(V)  | Edge-based, Union-Find       |
| Prim      | O((V+E) log V) | O(V)  | Vertex-based, Priority Queue |

### Others

| Algorithm       | Time     | Space | Purpose                       |
| --------------- | -------- | ----- | ----------------------------- |
| Cycle Detection | O(V + E) | O(V)  | Directed & Undirected         |
| Tarjan's SCC    | O(V + E) | O(V)  | Strongly Connected Components |
| Kosaraju's SCC  | O(V + E) | O(V)  | Strongly Connected Components |

## Greedy Algorithms

| Algorithm          | Time           | Space | Type       |
| ------------------ | -------------- | ----- | ---------- |
| Activity Selection | O(n log n)     | O(n)  | Scheduling |
| Kruskal's MST      | O(E log E)     | O(V)  | Graph      |
| Prim's MST         | O((V+E) log V) | O(V)  | Graph      |

## Backtracking

| Problem  | Time       | Space | Description             |
| -------- | ---------- | ----- | ----------------------- |
| N-Queens | O(N!)      | O(N²) | Constraint satisfaction |
| Sudoku   | O(9^(n×n)) | O(1)  | Puzzle solving          |

## Mathematical Algorithms

| Algorithm              | Time            | Space | Purpose                 |
| ---------------------- | --------------- | ----- | ----------------------- |
| Euclidean GCD          | O(log min(a,b)) | O(1)  | Greatest Common Divisor |
| Sieve of Eratosthenes  | O(n log log n)  | O(n)  | Prime generation        |
| Modular Exponentiation | O(log n)        | O(1)  | Fast power with modulo  |

## Tree Algorithms

| Algorithm           | Time     | Space | Type               |
| ------------------- | -------- | ----- | ------------------ |
| Inorder Traversal   | O(n)     | O(h)  | DFS                |
| Preorder Traversal  | O(n)     | O(h)  | DFS                |
| Postorder Traversal | O(n)     | O(h)  | DFS                |
| BST Insert          | O(h)     | O(1)  | Binary Search Tree |
| BST Delete          | O(h)     | O(1)  | Binary Search Tree |
| BST Search          | O(h)     | O(1)  | Binary Search Tree |
| LCA                 | O(n)     | O(h)  | Binary Tree        |
| AVL Insert          | O(log n) | O(1)  | Self-balancing     |

**Note:**

- V = number of vertices
- E = number of edges
- n = size of input
- h = height of tree
- k = range of values
- W = knapsack capacity

---

## Interview Tips

### Recognize Patterns

- **Two Pointers:** Sorted arrays, palindromes
- **Sliding Window:** Substring problems, subarrays
- **Binary Search:** Sorted data, search space reduction
- **DFS:** Connected components, paths
- **BFS:** Shortest path, level-order
- **DP:** Overlapping subproblems, optimal substructure
- **Greedy:** Local optimal → Global optimal

### Common Optimizations

- **Memoization:** Cache recursive results
- **Tabulation:** Bottom-up DP
- **Space Optimization:** Use 1D array instead of 2D
- **Early Termination:** Stop when answer found
- **Binary Search:** Reduce O(n) to O(log n)

### Time-Space Tradeoffs

- Hash maps: O(1) lookup vs O(n) space
- Recursion vs Iteration: Cleaner code vs stack space
- Sorting: O(n log n) time for O(log n) binary search
