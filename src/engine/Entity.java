package engine;

public class Entity {
    public int id;
    public double x, y;          // Position coordinates
    public double width, height;  // Dimensions
    public double dx, dy;        // Movement speeds
    public boolean isColliding;  // Highlight flag for visual rendering

    public Entity(int id, double x, double y, double width, double height, double dx, double dy) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.dx = dx;
        this.dy = dy;
        this.isColliding = false;
    }

    // Move entity and bounce off window walls
    public void update(int boundsWidth, int boundsHeight) {
        x += dx;
        y += dy;

        // Bounce horizontally
        if (x < 0 || x + width > boundsWidth) {
            dx = -dx;
        }
        // Bounce vertically
        if (y < 0 || y + height > boundsHeight) {
            dy = -dy;
        }
    }

    // AABB (Axis-Aligned Bounding Box) collision test against another entity
    public boolean intersects(Entity other) {
        return this.x < other.x + other.width &&
                this.x + this.width > other.x &&
                this.y < other.y + other.height &&
                this.y + this.height > other.y;
    }
}