package game;

import java.awt.*;

public class Bullet {
    
    private double x, y;
    private double targetX, targetY;
    private double speed = 8.0;
    private int damage;
    private boolean active;
    private Enemy target;
    
    public Bullet(int startX, int startY, Enemy target, int damage) {
        this.x = startX;
        this.y = startY;
        this.target = target;
        this.damage = damage;
        this.active = true;
    }
    
    public void update() {
        if (!active) return;
        
        // Hedef öldüyse mermıyı yok et
        if (!target.isAlive() || target.hasReachedEnd()) {
            active = false;
            return;
        }
        
        // Hedefe doğru hareket
        double dx = target.getX() - x;
        double dy = target.getY() - y;
        double distance = Math.sqrt(dx * dx + dy * dy);
        
        if (distance < speed) {
            // Hedefe ulaştı, hasar ver
            target.takeDamage(damage);
            active = false;
        } else {
            // Hareket et
            x += (dx / distance) * speed;
            y += (dy / distance) * speed;
        }
    }
    
    public void draw(Graphics g) {
        if (!active) return;
        
        g.setColor(Color.YELLOW);
        g.fillOval((int) x - 5, (int) y - 5, 10, 10);
        
        g.setColor(Color.ORANGE);
        g.drawOval((int) x - 5, (int) y - 5, 10, 10);
    }
    
    public boolean isActive() {
        return active;
    }
}