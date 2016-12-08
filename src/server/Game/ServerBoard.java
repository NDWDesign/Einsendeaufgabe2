package server.Game;

import common.Game.Board;
import common.Game.Worm;

import java.util.Vector;

/**
 * Serverseitiges Board mit Kollisionsmethoden und Seeder.
 *
 * Created by Andreas Ebling on 07.12.2016.
 */
public class ServerBoard extends Board {

    public boolean collideWalls(Worm worm) {
        boolean collided = false;

        if (worm.getHead().get(0) > (dimensions.get(0) / 2)){
            collided = true;
        } else if (worm.getHead().get(0) < -(dimensions.get(0) / 2)) {
            collided = true;
        } else if (worm.getHead().get(1) > (dimensions.get(1) / 2)) {
            collided = true;
        } else if (worm.getHead().get(1) < -(dimensions.get(1) / 2)) {
            collided = true;
        }
            return collided;
    }

    public void seed() {
        float randX = (float) Math.random() * dimensions.get(0);
        float randY = (float) Math.random() * dimensions.get(1);
        Vector<Float> randZ = new Vector<>();
        randZ.add(randX);
        randZ.add(randY);

        noms.add(randZ);
    }

    public void collideSeed(Worm worm) {
        for (Vector<Float> nom: noms) {
            if (worm.getHead().equals(nom)) {                               // needs work, match loosely
                worm.addSegment(nom);
                noms.remove(nom);
            }
        }
    }

    public boolean collideWorm(Worm worm) {
        boolean collided = false;
        for (Worm target: worms) {
            if (!worm.getName().equals(target.getName())) {
                for (Vector<Float> targetSegment: target.getSegments()) {   // needs work, match loosely
                    if (worm.getHead().equals(targetSegment)) {
                        collided = true;
                    }
                }
            }
        }

        return collided;
    }
}
