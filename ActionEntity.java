public interface ActionEntity extends Entity{
    void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore);
}
