public interface AnimatedEntity extends ActionEntity {
    double getAnimationPeriod();
    void nextImage();
    Action createAnimationAction(int repeatCount);
}
