package eva.evangelion.gameboard;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import java.io.Serializable;


public class SectorType extends StackPane implements Serializable {

    public final String Name;
    public final double R;
    public final double G;
    public final double B;
    public final boolean CanMoveTo;
    public final boolean CanCoverOn;
    public final boolean CanCoverAdjacentTo;
    public final int DealsDamageOnMovement;
    public int DealsDamageOnPushInto;
    public final int DealsDamageOnMovementAdjacentTo;
    public final int DealsDamageOnStanding;
    public final int DealsDamageOnStandingAdjacentTo;

    public Color getColor() {
        return Color.color(R, G, B);
    }

    public boolean Replacable = true;

    public final boolean SupportStructure;
    public final int HealthStamina;
    public final int HealthInterval;
    public final boolean TerminalDogma;
    public final boolean CanPassThrough;

    public boolean EnemyCanSee = true;
    public String OneTimePerRound = "";
    public String Creator;
    public SectorType originaltype;
    public int clear = 0;
    public boolean clearonstep = false;


    public static SectorType getMine(String creator, String oneTimePerRound, SectorType original, int damage, int pushindamage) {
       SectorType mine = new SectorType("Mine", Color.RED, true, false, false, damage,
                0,0,0,
                false, 0, 0, false, true);
        mine.clear = 2;
        mine.Replacable = false;
        mine.DealsDamageOnPushInto = pushindamage;
        mine.clearonstep = true;
        mine.EnemyCanSee = false;
        mine.Creator = creator;
        mine.OneTimePerRound = oneTimePerRound;
        mine.originaltype = original;
        return mine;
    }



    public SectorType(String name, Color Color, boolean canMoveTo,
                      boolean canCoverOn, boolean canCoverAdjacentTo,
                      int dealsDamageOnMovement, int dealsDamageOnMovementAdjacentTo,
                      int dealsDamageOnStanding, int dealsDamageOnStandingAdjacentTo,
                      boolean supportStructure, int healthStamina, int healthInterval,
                      boolean terminalDogma, boolean canPassThrough){
        Name = name;

        R = Color.getRed();
        G = Color.getGreen();
        B = Color.getBlue();
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
    public static SectorType Wall = new SectorType("Wall", Color.BLACK, false, false,
            false, 0, 0, 0,
            0, false, 0, 0, false, false);
    public static SectorType Destroyed = new SectorType("Destroyed", Color.WHITESMOKE, true, false,
            false, 0, 0, 0,
            0, false, 0, 0, false, true);
    public static SectorType Acid = new SectorType("Acid", Color.LIMEGREEN, true, false,
            false, 0, 0, 3,
            0, false, 0, 0, false, true);

}




