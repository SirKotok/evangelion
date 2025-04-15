package eva.evangelion.activegame.activeunits.unitstate;

import eva.evangelion.activegame.activeunits.Weapon;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class StateEffect implements Serializable {

    public StateEffect(String name) {
        Name = name;
    }

    public enum ExpirationCondition {
        TURN,
        START,
        GUARD,
        ATTACK,
        INTERVAL,
        ROUND,
        WEAPON_USE,
        STAND,
        POTENTIAL,
        NONE
    }

    public boolean drop_right = false;
    public boolean destroyed_right = false;
    public boolean destroyed_left = false;
    public boolean drop_left = false;
    public List<String> ProhibitedActions = new ArrayList<>();

    public final String Name;
    public String Description;
    public ExpirationCondition Condition = ExpirationCondition.NONE;
    public int Expiration = 1;
    public int SpeedChange = 0;
    public boolean Stackable = false;
    public boolean DeleteOnMovement = false;
    public Weapon WeaponSpecific = null;

    public int AccuracyDelta = 0;
    public int ArmorDelta = 0;
    public int ReflexesDelta = 0;
    public int SpeedDelta = 0;
    public int MaxToughnessDelta = 0;
    public int PenetrationDelta = 0;

    public int AttackStrengthDelta = 0;
    public int RangeDelta = 0;
    public int RangedStrengthDelta = 0;

    public List<StateEffect> AdditionalEffects = new ArrayList<>();

}
