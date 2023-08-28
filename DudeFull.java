import processing.core.PImage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * An entity that exists in the world. See EntityKind for the
 * different kinds of entities that exist.
 */
public final class DudeFull extends Dude{

    public DudeFull(String id, Point position, List<PImage> images, int resourceLimit, double actionPeriod, double animationPeriod){
        super(id, position, images, resourceLimit, actionPeriod, animationPeriod);
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<Entity> fullTarget = world.findNearest(super.getPosition(), new ArrayList<>(List.of(House.class)));

        if (fullTarget.isPresent() && moveTo(world, fullTarget.get(), scheduler)) {
            transformFull(world, scheduler, imageStore);
        } else {
            scheduler.scheduleEvent(this, createActivityAction(world, imageStore), super.getActionPeriod());
        }
    }

//    public boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler) {
//        if (Functions.adjacent(super.getPosition(), target.getPosition())) {
//            return true;
//        } else {
//            Point nextPos = super.nextPosition(world, target.getPosition());
//
//            if (!super.getPosition().equals(nextPos)) {
//                world.moveEntity(scheduler, this, nextPos);
//            }
//            return false;
//        }
//    }

    public boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler) {
        if (Functions.adjacent(super.getPosition(), target.getPosition())) {
            return true;
        } else {
            super.moveTo(world, target, scheduler);
        }
        return false;
    }

    public void transformFull(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        DudeNotFull dude = EntityFactory.createDudeNotFull(super.getid(), super.getPosition(), super.getActionPeriod(), super.getAnimationPeriod(), super.getResourceLimit(), super.getImages());

        world.removeEntity(scheduler, this);

        world.addEntity(dude);
        dude.scheduleActions(scheduler, world, imageStore);
    }
}
