package eva.evangelion.activegame.actions;

import eva.evangelion.activegame.activeunits.BaseUnit;

public class Movement extends Action {
    public final String UnitActor;
    public final int StartX;
    public final int StartY;
    public final int EndX;
    public final int EndY;
    public final int Cost;
    public Movement(String actor, int startX, int startY, int endX, int endY, int cost) {
        super(actor);
        UnitActor = actor;
        StartX = startX;
        StartY = startY;
        EndX = endX;
        EndY = endY;
        Cost = cost;
    }


}
