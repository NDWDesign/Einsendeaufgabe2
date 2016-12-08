package common.Game;

import java.util.Set;
import java.util.Vector;

/**
 * Abstrakte Beschreibung des Spielbretts.
 *
 * Created by Andreas Ebling on 07.12.2016.
 */
public abstract class Board {

    protected Vector<Integer> dimensions;

    protected Worm[] worms;

    protected Set<Vector<Float>> noms;

}
