package engine;

import datastructures.CustomQueue;
import datastructures.CustomStack;
import datastructures.CustomLinkedList;

public class GraphMap {
    private int rows, cols;
    private int[][] grid; // 0 = Walkable, 1 = Wall/Obstacle

    public GraphMap(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.grid = new int[rows][cols];
    }

    public void toggleWall(int r, int c) {
        if (r >= 0 && r < rows && c >= 0 && c < cols) {
            grid[r][c] = (grid[r][c] == 0) ? 1 : 0;
        }
    }

    public int getCell(int r, int c) {
        return grid[r][c];
    }

    public int getRows() { return rows; }
    public int getCols() { return cols; }

    // Represent grid coordinate as a single Node ID
    public int toNodeId(int r, int c) {
        return r * cols + c;
    }

    public int[] toCoord(int nodeId) {
        return new int[]{nodeId / cols, nodeId % cols};
    }

    // --- Breadth-First Search (BFS) using CustomQueue ---
    public CustomLinkedList<Integer> findPathBFS(int startR, int startC, int targetR, int targetC) {
        int startNode = toNodeId(startR, startC);
        int targetNode = toNodeId(targetR, targetC);
        int totalNodes = rows * cols;

        boolean[] visited = new boolean[totalNodes];
        int[] parent = new int[totalNodes];
        for (int i = 0; i < totalNodes; i++) parent[i] = -1;

        CustomQueue<Integer> queue = new CustomQueue<>();
        queue.enqueue(startNode);
        visited[startNode] = true;

        boolean pathFound = false;

        // Direction vectors: Up, Down, Left, Right
        int[] dR = {-1, 1, 0, 0};
        int[] dC = {0, 0, -1, 1};

        while (!queue.isEmpty()) {
            int current = queue.dequeue();
            if (current == targetNode) {
                pathFound = true;
                break;
            }

            int[] currCoord = toCoord(current);
            int r = currCoord[0];
            int c = currCoord[1];

            for (int i = 0; i < 4; i++) {
                int newR = r + dR[i];
                int newC = c + dC[i];

                if (newR >= 0 && newR < rows && newC >= 0 && newC < cols && grid[newR][newC] == 0) {
                    int neighborId = toNodeId(newR, newC);
                    if (!visited[neighborId]) {
                        visited[neighborId] = true;
                        parent[neighborId] = current;
                        queue.enqueue(neighborId);
                    }
                }
            }
        }

        return reconstructPath(parent, startNode, targetNode, pathFound);
    }

    // --- Depth-First Search (DFS) using CustomStack ---
    public CustomLinkedList<Integer> findPathDFS(int startR, int startC, int targetR, int targetC) {
        int startNode = toNodeId(startR, startC);
        int targetNode = toNodeId(targetR, targetC);
        int totalNodes = rows * cols;

        boolean[] visited = new boolean[totalNodes];
        int[] parent = new int[totalNodes];
        for (int i = 0; i < totalNodes; i++) parent[i] = -1;

        CustomStack<Integer> stack = new CustomStack<>();
        stack.push(startNode);

        boolean pathFound = false;

        int[] dR = {-1, 1, 0, 0};
        int[] dC = {0, 0, -1, 1};

        while (!stack.isEmpty()) {
            int current = stack.pop();

            if (!visited[current]) {
                visited[current] = true;

                if (current == targetNode) {
                    pathFound = true;
                    break;
                }

                int[] currCoord = toCoord(current);
                int r = currCoord[0];
                int c = currCoord[1];

                for (int i = 0; i < 4; i++) {
                    int newR = r + dR[i];
                    int newC = c + dC[i];

                    if (newR >= 0 && newR < rows && newC >= 0 && newC < cols && grid[newR][newC] == 0) {
                        int neighborId = toNodeId(newR, newC);
                        if (!visited[neighborId]) {
                            parent[neighborId] = current;
                            stack.push(neighborId);
                        }
                    }
                }
            }
        }

        return reconstructPath(parent, startNode, targetNode, pathFound);
    }

    // Reconstruct path from target back to start
    private CustomLinkedList<Integer> reconstructPath(int[] parent, int startNode, int targetNode, boolean pathFound) {
        CustomLinkedList<Integer> path = new CustomLinkedList<>();
        if (!pathFound) return path; // Return empty list if no path exists

        int current = targetNode;
        while (current != -1) {
            path.add(current);
            current = parent[current];
        }
        return path;
    }
}