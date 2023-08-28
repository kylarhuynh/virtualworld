import processing.core.PImage;

import java.util.List;

public class SimpleEntity implements Entity{
    private String id;
    private Point position;
    private List<PImage> images;

    public SimpleEntity(String id, Point position, List<PImage> images){
        this.id = id;
        this.position = position;
        this.images = images;
    }

    public void setPosition(Point pos){
        this.position = pos;
    }

    public PImage getCurrentImage() {
        return this.images.get(0);
    }

    public String log() {
        return this.id.isEmpty() ? null :
                String.format("%s %d %d %d", this.id, this.position.getX(), this.position.getY(), 0);
    }

    public Point getPosition() {
        return this.position;
    }
    public String getid() {
        return this.id;
    }
}
