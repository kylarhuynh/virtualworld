import processing.core.PImage;

import java.util.List;

public class SaplingTest extends Plant{
    private final String TREE_KEY = "tree";

    private int healthLimit;

    public SaplingTest(String id, Point position, double actionPeriod, double animationPeriod, int health, List<PImage> images, int healthLimit){
        super(id, position, actionPeriod, animationPeriod, health, images);
        this.healthLimit = healthLimit;
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        super.setHealth(this.getHealth()+1);
        if (!transformSapling(world, scheduler, imageStore)) {
            scheduler.scheduleEvent(this, createActivityAction(world, imageStore), this.getActionPeriod());
        }
    }

    public boolean transformSapling(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        if (this.getHealth() <= 0)
            super.transform(world, scheduler, imageStore);
        else if (this.getHealth() >= this.healthLimit) {
            Tree tree = EntityFactory.createTree(TREE_KEY + "_" + super.getid(), super.getPosition(), Functions.getNumFromRange(1.400, 1.000), Functions.getNumFromRange(0.600, 0.050), Functions.getIntFromRange(3, 1), imageStore.getImageList(TREE_KEY));

            world.removeEntity(scheduler, this);

            world.addEntity(tree);
            tree.scheduleActions(scheduler, world, imageStore);

            return true;
        }

        return false;
    }
}
