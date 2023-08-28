import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AStarPathingStrategy implements PathingStrategy{
    public List<Point> computePath(Point start, Point end, Predicate<Point> canPassThrough, BiPredicate<Point, Point> withinReach, Function<Point, Stream<Point>> potentialNeighbors) {
        List<Point> path = new LinkedList<>();
        PriorityQueue<Node> open = new PriorityQueue<>(Comparator.comparing(Node::getF));

        // use hashmaps too quickly check if in open or closed
        HashMap<Point, Node> closed = new HashMap<>();
        HashMap<Point, Node> openHash = new HashMap<>();

        open.add(new Node(0, H(start, end), F(0, H(start, end)), start, null));

        while (open.size() > 0){
            Node current = open.poll();

            // check if current is next to end
            if (withinReach.test(current.getLoc(), end)) {
                writePath(path, current);
                return path;
            } else {

                // list of neighboring points
                List<Point> neighbors = potentialNeighbors.apply(current.getLoc())
                        .filter(canPassThrough)
                        .filter(point -> !closed.containsKey(point))
                        .collect(Collectors.toList());

                for (Point pos : neighbors) {
                    int g = current.getG() + 1;
                    int h = H(pos, end);
                    Node n = new Node(g, h, F(g, h), pos, current);
                    if (openHash.containsKey(pos)) {
                        if (g < openHash.get(pos).getG()) {
                            open.remove(n);
                            open.add(n);
                            openHash.replace(pos, n);
                        }
                    } else {
                        open.add(n);
                        openHash.put(pos, n);
                    }
                }

                closed.put(current.getLoc(), current);
            }
        }
        return path;
    }

    // write path recursively
    public void writePath(List<Point> path, Node n){
        if (n.getPrior() == null){
            return;
        }
        path.add(0, n.getLoc());
        writePath(path, n.getPrior());
    }

    // calculate f value
    public int F(int g, int h){
        return g + h;
    }

    // calculate h value
    public int H(Point current, Point end){
        return Math.abs((current.getX() - end.getX()) + (current.getY() - end.getY()));
    }
}
