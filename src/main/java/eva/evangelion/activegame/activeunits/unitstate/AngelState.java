package eva.evangelion.activegame.activeunits.unitstate;

import eva.evangelion.activegame.activeunits.Weapon;

import java.util.ArrayList;
import java.util.List;

public class AngelState extends BaseState{
    public int x;
    public int y;

    public int MeleeStrengthDelta = 0;
    public int RangedStrengthDelta = 0;


    public AngelState(String typeString) {
        super("GM", typeString);
        Weapons = new ArrayList<>();
        Weapon AngelRanged = Weapon.getChazaquielRangedAttack();
        Weapon AngelMelee = Weapon.getAngelMeleeAttack();
        Weapons.add(AngelMelee);
        Weapons.add(AngelRanged);
    }


    @Override
    public void ApplyStateEffects(Weapon weapon){
        for (StateEffect Effect : StateEffects) {
            if (Effect.WeaponSpecific == null || Effect.WeaponSpecific == weapon) {
            SpeedChange = Math.max(SpeedChange, Effect.SpeedChange);
            ArmorDelta += Effect.ArmorDelta;
            SpeedDelta += Effect.SpeedDelta;
            MaxToughnessDelta +=Effect.MaxToughnessDelta;
            ReflexesDelta += Effect.ReflexesDelta;
            AccuracyDelta += Effect.AccuracyDelta;
            RangedStrengthDelta += Effect.RangedStrengthDelta;
            MeleeStrengthDelta += Effect.AttackStrengthDelta;
            PenetrationDelta += Effect.PenetrationDelta;
            for (String Prohibited : Effect.ProhibitedActions) {
                if (!ProhibitedActions.contains(Prohibited)) {
                    ProhibitedActions.add(Prohibited);
                }
            }
        }
        }
    }
    @Override
    public void CalculateDelta(Weapon weapon){
        ArmorDelta = 0;
        SpeedDelta = 0;
        MaxToughnessDelta = 0;
        ReflexesDelta = 0;
        AccuracyDelta = 0;
        RangedStrengthDelta = 0;
        MeleeStrengthDelta = 0;
        PenetrationDelta = 0;
        ProhibitedActions = new ArrayList<>();
        SpeedChange = 0;
        ProcessStateEffects();
        ApplyStateEffects(weapon);
    }

}
