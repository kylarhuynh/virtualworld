import processing.core.PImage;

import java.util.List;

public class Obstacle implements AnimatedEntity{

    private String id;
    private Point position;
    private List<PImage> images;
    private int imageIndex;
    private double animationPeriod;

    public Obstacle(String id, Point position, double animationPeriod, List<PImage> images){
        this.id = id;
        this.position = position;
        this.images = images;
        this.animationPeriod = animationPeriod;
    }

    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this, createAnimationAction(0), getAnimationPeriod());
    }

    public double getAnimationPeriod() {
        return this.animationPeriod;
    }

    public void nextImage() {
        this.imageIndex = this.imageIndex + 1;
    }

    public Action createAnimationAction(int repeatCount) {
        return new Animation(this, repeatCount);
    }

    public PImage getCurrentImage() {
        return this.images.get(this.imageIndex % this.images.size());
    }

    public String log() {
        return this.id.isEmpty() ? null :
                String.format("%s %d %d %d", this.id, this.position.getX(), this.position.getY(), this.imageIndex);
    }

    public Point getPosition() {
        return this.position;
    }

    public String getid() {
        return this.id;
    }

    public void setPosition(Point pos){
        this.position = pos;
    }
}
