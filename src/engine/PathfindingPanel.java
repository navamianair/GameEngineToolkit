package engine;

import datastructures.CustomLinkedList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PathfindingPanel extends JPanel {

    private static final int ROWS = 20;
    private static final int COLS = 20;
    private static final int TILE_SIZE = 25;

    private GraphMap graphMap;
    private int startR = 2, startC = 2;
    private int targetR = 17, targetC = 17;
    private CustomLinkedList<Integer> currentPath;

    private boolean useBFS = true; // Toggle between BFS and DFS
    private long searchTimeNs = 0;

    public PathfindingPanel() {
        this.setPreferredSize(new Dimension(COLS * TILE_SIZE + 200, ROWS * TILE_SIZE));
        this.setBackground(Color.DARK_GRAY);

        graphMap = new GraphMap(ROWS, COLS);
        currentPath = new CustomLinkedList<>();

        // Click on grid to toggle wall obstacles
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int col = e.getX() / TILE_SIZE;
                int row = e.getY() / TILE_SIZE;

                if (row >= 0 && row < ROWS && col >= 0 && col < COLS) {
                    if ((row != startR || col != startC) && (row != targetR || col != targetC)) {
                        graphMap.toggleWall(row, col);
                        calculatePath();
                        repaint();
                    }
                }
            }
        });

        calculatePath();
    }

    public void setUseBFS(boolean useBFS) {
        this.useBFS = useBFS;
        calculatePath();
        repaint();
    }

    private void calculatePath() {
        long startTime = System.nanoTime();
        if (useBFS) {
            currentPath = graphMap.findPathBFS(startR, startC, targetR, targetC);
        } else {
            currentPath = graphMap.findPathDFS(startR, startC, targetR, targetC);
        }
        searchTimeNs = System.nanoTime() - startTime;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Render Map Grid
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                if (r == startR && c == startC) {
                    g.setColor(Color.GREEN); // Start Node
                } else if (r == targetR && c == targetC) {
                    g.setColor(Color.RED);   // Target Node
                } else if (graphMap.getCell(r, c) == 1) {
                    g.setColor(Color.BLACK); // Wall / Obstacle
                } else {
                    g.setColor(Color.LIGHT_GRAY);
                }

                g.fillRect(c * TILE_SIZE, r * TILE_SIZE, TILE_SIZE - 1, TILE_SIZE - 1);
            }
        }

        // Highlight Calculated Path in Cyan
        g.setColor(Color.CYAN);
        for (int i = 0; i < currentPath.size(); i++) {
            int nodeId = currentPath.get(i);
            int[] coord = graphMap.toCoord(nodeId);
            int r = coord[0];
            int c = coord[1];

            if ((r != startR || c != startC) && (r != targetR || c != targetC)) {
                g.fillRect(c * TILE_SIZE + 4, r * TILE_SIZE + 4, TILE_SIZE - 8, TILE_SIZE - 8);
            }
        }

        // Sidebar Telemetry Info
        int sidebarX = COLS * TILE_SIZE + 10;
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 14));
        g.drawString("Graph Search Controls", sidebarX, 30);

        g.setFont(new Font("Arial", Font.PLAIN, 12));
        g.drawString("Algorithm: " + (useBFS ? "BFS (Queue)" : "DFS (Stack)"), sidebarX, 60);
        g.drawString("Path Length: " + currentPath.size() + " tiles", sidebarX, 85);
        g.drawString("Execution Time:", sidebarX, 110);
        g.drawString(String.format("%.3f ms", searchTimeNs / 1_000_000.0), sidebarX, 130);

        g.drawString("Instructions:", sidebarX, 170);
        g.drawString("• Click grid to place/remove walls.", sidebarX, 190);
        g.drawString("• Green = Start Node (0,0)", sidebarX, 210);
        g.drawString("• Red = Target Node", sidebarX, 230);
        g.drawString("• Cyan = Calculated Route", sidebarX, 250);
    }
}