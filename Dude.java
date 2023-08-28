import processing.core.PImage;

import java.util.List;
import java.util.Optional;

/**
 * An entity that exists in the world. See EntityKind for the
 * different kinds of entities that exist.
 */
public abstract class Dude extends FairyDudeEntity{

    private int resourceLimit;

    public Dude(String id, Point position, List<PImage> images, int resourceLimit, double actionPeriod, double animationPeriod){
        super(id, position, images, actionPeriod, animationPeriod);
        this.resourceLimit = resourceLimit;
    }

    public int getResourceLimit(){
        return this.resourceLimit;
    }

    public abstract void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler);

//    public abstract boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler);

    public boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler) {
        Point nextPos = nextPosition(world, target.getPosition());

        if (!super.getPosition().equals(nextPos)) {
            world.moveEntity(scheduler, this, nextPos);
        }
        return false;
    }

    public Point nextPosition(WorldModel world, Point destPos) {
        PathingStrategy strategy = new AStarPathingStrategy();
        List<Point> path = strategy.computePath(
                this.getPosition(),
                destPos,
                point -> world.withinBounds(point) && (!world.isOccupied(point) || world.getOccupancyCell(point).getClass() == Stump.class),
                (Functions::adjacent),
                PathingStrategy.CARDINAL_NEIGHBORS);

        if (path.size() > 0)
            return path.get(0);
        else
            return this.getPosition();
//        int horiz = Integer.signum(destPos.x - super.getPosition().x);
//        Point newPos = new Point(super.getPosition().x + horiz, super.getPosition().y);
//
//        if (horiz == 0 || world.isOccupied(newPos) && world.getOccupancyCell(newPos) instanceof ActionEntity || world.getOccupancyCell(newPos) instanceof House) {
//            int vert = Integer.signum(destPos.y - super.getPosition().y);
//            newPos = new Point(super.getPosition().x, super.getPosition().y + vert);
//
//            if (vert == 0 || world.isOccupied(newPos) && world.getOccupancyCell(newPos) instanceof ActionEntity || world.getOccupancyCell(newPos) instanceof House) {
//                newPos = super.getPosition();
//            }
//        }
//
//        return newPos;
    }


}
