package eva.evangelion.gameboard;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;


public class SectorType extends StackPane {

    public final String Name;
    public final Color color;
    public final boolean CanMoveTo;
    public final boolean CanCoverOn;
    public final boolean CanCoverAdjacentTo;
    public final int DealsDamageOnMovement;
    public final int DealsDamageOnMovementAdjacentTo;
    public final int DealsDamageOnStanding;
    public final int DealsDamageOnStandingAdjacentTo;

    public final boolean SupportStructure;
    public final int HealthStamina;
    public final int HealthInterval;
    public final boolean TerminalDogma;
    public final boolean CanPassThrough;
    public SectorType(String name, Color Color, boolean canMoveTo,
                      boolean canCoverOn, boolean canCoverAdjacentTo,
                      int dealsDamageOnMovement, int dealsDamageOnMovementAdjacentTo,
                      int dealsDamageOnStanding, int dealsDamageOnStandingAdjacentTo,
                      boolean supportStructure, int healthStamina, int healthInterval,
                      boolean terminalDogma, boolean canPassThrough){
        Name = name;

        color = Color;
        CanMoveTo = canMoveTo;
        CanCoverOn = canCoverOn;
        CanCoverAdjacentTo = canCoverAdjacentTo;
        DealsDamageOnMovement = dealsDamageOnMovement;
        DealsDamageOnMovementAdjacentTo = dealsDamageOnMovementAdjacentTo;

        DealsDamageOnStanding = dealsDamageOnStanding;
        DealsDamageOnStandingAdjacentTo = dealsDamageOnStandingAdjacentTo;
        SupportStructure = supportStructure;
        HealthStamina = healthStamina;
        HealthInterval = healthInterval;
        TerminalDogma = terminalDogma;
        CanPassThrough = canPassThrough;
    }

    public static SectorType Blank = new SectorType("Blank", Color.WHITE, true, false,
            false, 0, 0, 0,
            0, false, 0, 0, false, true);
    public static SectorType Destroyed = new SectorType("Destroyed", Color.GRAY, true, false,
            false, 0, 0, 0,
            0, false, 0, 0, false, true);


}




