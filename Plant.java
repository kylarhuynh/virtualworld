import processing.core.PImage;

import java.util.List;

public abstract class Plant implements AnimatedEntity, ActivityEntity{

    private String id;
    private Point position;
    private List<PImage> images;
    private int imageIndex;
    private double actionPeriod;
    private double animationPeriod;
    private int health;
    private final String STUMP_KEY = "stump";

    public Plant(String id, Point position, double actionPeriod, double animationPeriod, int health, List<PImage> images){
        this.id = id;
        this.position = position;
        this.actionPeriod = actionPeriod;
        this.animationPeriod = animationPeriod;
        this.health = health;
        this.images = images;
    }

    public double getActionPeriod(){
        return this.actionPeriod;
    }

    public int getHealth(){
        return this.health;
    }

    public void setHealth(int x){
        this.health = x;
    }

    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this, createActivityAction(world, imageStore), this.actionPeriod);
        scheduler.scheduleEvent(this, createAnimationAction(0), getAnimationPeriod());
    }

    public Action createActivityAction(WorldModel world, ImageStore imageStore) {
        return new Activity(this, world, imageStore);
    }

    public abstract void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler);

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

    public void setPosition(Point pos){
        this.position = pos;
    }

    public String getid() {
        return this.id;
    }

    public boolean transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
            Stump stump = EntityFactory.createStump(STUMP_KEY + "_" + this.id, this.position, imageStore.getImageList(STUMP_KEY));

            world.removeEntity(scheduler, this);

            world.addEntity(stump);

            return true;
    }
}
