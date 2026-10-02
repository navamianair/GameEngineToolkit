package engine;

import datastructures.CustomLinkedList;

import javax.swing.*;
import java.awt.*;

public class GameWindow extends JPanel implements Runnable {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    private CustomLinkedList<Entity> entities;
    private QuadTree quadTree;
    private Thread gameThread;
    private boolean running = true;

    // Telemetry stats for evaluator display
    private long executionTimeNs = 0;
    private int totalChecks = 0;

    public GameWindow() {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.BLACK);

        entities = new CustomLinkedList<>();
        quadTree = new QuadTree(0, 0, 0, WIDTH, HEIGHT);

        // Spawn 30 random moving objects on screen
        for (int i = 0; i < 30; i++) {
            double x = Math.random() * (WIDTH - 20);
            double y = Math.random() * (HEIGHT - 20);
            double dx = (Math.random() * 4) - 2;
            double dy = (Math.random() * 4) - 2;
            entities.add(new Entity(i, x, y, 15, 15, dx, dy));
        }

        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        while (running) {
            updateGame();
            repaint(); // Triggers paintComponent to redraw graphics

            try {
                Thread.sleep(16); // ~60 FPS
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateGame() {
        long startTime = System.nanoTime();
        totalChecks = 0;

        // 1. Move all entities
        for (int i = 0; i < entities.size(); i++) {
            Entity e = entities.get(i);
            e.isColliding = false; // Reset collision state
            e.update(WIDTH, HEIGHT);
        }

        // 2. Rebuild QuadTree
        quadTree.clear();
        for (int i = 0; i < entities.size(); i++) {
            quadTree.insert(entities.get(i));
        }

        // 3. Perform QuadTree spatial collision checks
        for (int i = 0; i < entities.size(); i++) {
            Entity e1 = entities.get(i);
            CustomLinkedList<Entity> candidates = new CustomLinkedList<>();
            quadTree.retrieve(candidates, e1);

            for (int j = 0; j < candidates.size(); j++) {
                Entity e2 = candidates.get(j);
                if (e1 != e2) {
                    totalChecks++;
                    if (e1.intersects(e2)) {
                        e1.isColliding = true;
                        e2.isColliding = true;
                    }
                }
            }
        }

        executionTimeNs = System.nanoTime() - startTime;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw Quadtree spatial boundary divisions
        quadTree.draw(g);

        // Draw entities (Green = Normal, Red = Colliding)
        for (int i = 0; i < entities.size(); i++) {
            Entity e = entities.get(i);
            if (e.isColliding) {
                g.setColor(Color.RED);
            } else {
                g.setColor(Color.GREEN);
            }
            g.fillRect((int)e.x, (int)e.y, (int)e.width, (int)e.height);
        }

        // On-screen telemetry panel
        g.setColor(Color.WHITE);
        g.drawString("Entities: " + entities.size(), 10, 20);
        g.drawString("Quadtree Spatial Checks: " + totalChecks, 10, 35);
        g.drawString("Execution Time: " + String.format("%.3f", executionTimeNs / 1_000_000.0) + " ms", 10, 50);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Interactive Game Engine Toolkit - Quadtree Visualizer");
        GameWindow gameWindow = new GameWindow();

        frame.add(gameWindow);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}