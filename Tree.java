import processing.core.PImage;

import java.util.List;

public class Tree extends Plant{

    public Tree(String id, Point position, double actionPeriod, double animationPeriod, int health, List<PImage> images) {
        super(id, position, actionPeriod, animationPeriod, health, images);
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler){
        if (!transform(world, scheduler, imageStore)) {
            scheduler.scheduleEvent(this, createActivityAction(world, imageStore), this.getActionPeriod());
        }
    }

    public boolean transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        if (this.getHealth() <= 0)
            super.transform(world, scheduler, imageStore);
        return false;
    }
}
