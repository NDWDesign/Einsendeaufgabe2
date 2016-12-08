package common.Game;

import java.util.LinkedList;
import java.util.Vector;

/**
 * Beschreibung der Spielfigur.
 *
 * Created by Andreas Ebling on 07.12.2016.
 */
public class Worm {

    private String name;

    private Vector<Float> direction;

    private LinkedList<Vector<Float>> segments;


    public Worm(String name, float startPosX, float startPosY, int elements) {
        this.name = name;

        Vector<Float> direction = new Vector<>();
        direction.add(1f);
        direction.add(0f);

        Vector<Float> startPos = new Vector<>();
        startPos.add(startPosX);
        startPos.add(startPosY);

        LinkedList<Vector<Float>> segments = new LinkedList<>();
        for (int i = 0; i < elements; i++) {
            segments.addLast(startPos);
        }
    }

    public String getName() {
        return name;
    }

    public Vector<Float> getDirection() {
        return direction;
    }

    public void setDirection(int x, int y) {

        Vector<Float> z = new Vector<>();
        float zx = x / (x + y);
        float zy = y / (x + y);
        z.add(zx);
        z.add(zy);

        direction = z;
    }

    public LinkedList<Vector<Float>> getSegments() {
        return segments;
    }

    public Vector<Float> getHead() {
        return segments.getFirst();
    }

    public void addSegment(Vector<Float> tail) {
        segments.addLast(tail);
    }

    public void move() {
        float newX = direction.get(0) + getHead().get(0);
        float newY = direction.get(1) + getHead().get(1);

        Vector<Float> newZ = new Vector<>();
        newZ.add(newX);
        newZ.add(newY);

        segments.addFirst(newZ);
        segments.removeLast();
    }
}
