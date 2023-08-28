public interface ActivityEntity extends ActionEntity{
    Action createActivityAction(WorldModel world, ImageStore imageStore);
    void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler);
}
