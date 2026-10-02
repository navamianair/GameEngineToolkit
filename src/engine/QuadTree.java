package engine;

import datastructures.CustomLinkedList;
import java.awt.Graphics;
import java.awt.Color;

public class QuadTree {
    private static final int MAX_CAPACITY = 4; // Max entities before splitting
    private static final int MAX_LEVELS = 5;   // Max tree depth

    private int level;
    private double x, y, width, height; // Node bounding box
    private CustomLinkedList<Entity> entities;
    private QuadTree[] nodes; // 0: NW, 1: NE, 2: SW, 3: SE

    public QuadTree(int level, double x, double y, double width, double height) {
        this.level = level;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.entities = new CustomLinkedList<>();
        this.nodes = new QuadTree[4];
    }

    // Clear the tree recursively
    public void clear() {
        entities = new CustomLinkedList<>();
        for (int i = 0; i < nodes.length; i++) {
            if (nodes[i] != null) {
                nodes[i].clear();
                nodes[i] = null;
            }
        }
    }

    // Split node into 4 child quadrants
    private void split() {
        double subWidth = width / 2.0;
        double subHeight = height / 2.0;

        nodes[0] = new QuadTree(level + 1, x, y, subWidth, subHeight);                      // NW
        nodes[1] = new QuadTree(level + 1, x + subWidth, y, subWidth, subHeight);           // NE
        nodes[2] = new QuadTree(level + 1, x, y + subHeight, subWidth, subHeight);           // SW
        nodes[3] = new QuadTree(level + 1, x + subWidth, y + subHeight, subWidth, subHeight);// SE
    }

    // Insert entity into the QuadTree
    public void insert(Entity entity) {
        if (nodes[0] != null) {
            int index = getIndex(entity);
            if (index != -1) {
                nodes[index].insert(entity);
                return;
            }
        }

        entities.add(entity);

        // Subdivide if capacity exceeded
        if (entities.size() > MAX_CAPACITY && level < MAX_LEVELS) {
            if (nodes[0] == null) {
                split();
            }

            int i = 0;
            while (i < entities.size()) {
                Entity current = entities.get(i);
                int index = getIndex(current);
                if (index != -1) {
                    entities.remove(current);
                    nodes[index].insert(current);
                } else {
                    i++;
                }
            }
        }
    }

    // Determine which child quadrant an entity belongs to
    private int getIndex(Entity entity) {
        int index = -1;
        double verticalMidpoint = x + (width / 2.0);
        double horizontalMidpoint = y + (height / 2.0);

        boolean topQuadrant = (entity.y < horizontalMidpoint && entity.y + entity.height < horizontalMidpoint);
        boolean bottomQuadrant = (entity.y > horizontalMidpoint);

        if (entity.x < verticalMidpoint && entity.x + entity.width < verticalMidpoint) {
            if (topQuadrant) index = 0;      // NW
            else if (bottomQuadrant) index = 2; // SW
        } else if (entity.x > verticalMidpoint) {
            if (topQuadrant) index = 1;      // NE
            else if (bottomQuadrant) index = 3; // SE
        }

        return index;
    }

    // Retrieve all entities that could collide with the given entity
    public void retrieve(CustomLinkedList<Entity> returnEntities, Entity entity) {
        int index = getIndex(entity);
        if (index != -1 && nodes[0] != null) {
            nodes[index].retrieve(returnEntities, entity);
        } else if (nodes[0] != null) {
            // Entity overlaps multiple child boundaries; check all subnodes
            for (int i = 0; i < 4; i++) {
                nodes[i].retrieve(returnEntities, entity);
            }
        }

        for (int i = 0; i < entities.size(); i++) {
            returnEntities.add(entities.get(i));
        }
    }

    // Visual rendering of Quadtree boundary lines
    public void draw(Graphics g) {
        g.setColor(Color.GRAY);
        g.drawRect((int)x, (int)y, (int)width, (int)height);

        if (nodes[0] != null) {
            for (int i = 0; i < 4; i++) {
                nodes[i].draw(g);
            }
        }
    }
}