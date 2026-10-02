package engine;

import javax.swing.*;
import java.awt.*;

public class MainApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Interactive Game Engine Toolkit - DS Lab Project");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JTabbedPane tabbedPane = new JTabbedPane();

            // Tab 1: QuadTree Spatial Collision
            GameWindow collisionWindow = new GameWindow();
            tabbedPane.addTab("1. Spatial Quadtree Collision Engine", collisionWindow);

            // Tab 2: Graph Pathfinding (BFS / DFS)
            PathfindingPanel pathfindingPanel = new PathfindingPanel();

            JPanel pathTabContainer = new JPanel(new BorderLayout());
            JPanel controlPanel = new JPanel();

            JButton btnBFS = new JButton("Use BFS (Shortest Path)");
            JButton btnDFS = new JButton("Use DFS (Maze Exploration)");

            btnBFS.addActionListener(e -> pathfindingPanel.setUseBFS(true));
            btnDFS.addActionListener(e -> pathfindingPanel.setUseBFS(false));

            controlPanel.add(btnBFS);
            controlPanel.add(btnDFS);

            pathTabContainer.add(controlPanel, BorderLayout.NORTH);
            pathTabContainer.add(pathfindingPanel, BorderLayout.CENTER);

            tabbedPane.addTab("2. Graph Pathfinding Engine (BFS / DFS)", pathTabContainer);

            frame.add(tabbedPane);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}