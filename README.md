# Interactive Game Engine Toolkit: Spatial Collision Partitioning and Graph Pathfinding

A lightweight Java desktop application demonstrating spatial collision partitioning and graph pathfinding built strictly from scratch without external collections or libraries.

---

## 📌 Features

### 1. Custom Data Structures (Built from Scratch)
- **`CustomLinkedList`**: Dynamic node-based list used across spatial queries.
- **`CustomStack`**: Singly linked list implementation of LIFO structure used for DFS traversal.
- **`CustomQueue`**: Head/Tail pointer-based FIFO structure used for BFS pathfinding.

### 2. Spatial Collision Engine (Quadtree)
- Dynamic 2D spatial partitioning dividing screen space into sub-quadrants (NW, NE, SW, SE).
- Reduces collision detection checks toward $O(N \log N)$ complexity.
- Live telemetry panel showing real-time execution timing ($ms$) and total spatial collision checks.

### 3. Graph Pathfinding Module
- Models grid tiles using an Adjacency Matrix representation.
- **Breadth-First Search (BFS)**: Computes the shortest unweighted path using `CustomQueue`.
- **Depth-First Search (DFS)**: Performs deep maze exploration using `CustomStack`.
- Interactive GUI panel allowing real-time wall placement and obstacle routing.

---

## 🛠️ Project Structure

```text
GameEngineToolkit/
├── src/
│   ├── datastructures/
│   │   ├── CustomLinkedList.java
│   │   ├── CustomStack.java
│   │   └── CustomQueue.java
│   └── engine/
│       ├── Entity.java
│       ├── QuadTree.java
│       ├── GameWindow.java
│       ├── GraphMap.java
│       ├── PathfindingPanel.java
│       └── MainApp.java
└── README.md