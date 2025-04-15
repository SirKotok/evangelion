package eva.evangelion.activegame.activeunits.unitstate;

import eva.evangelion.activegame.activeunits.Weapon;
import eva.evangelion.util.EvaSaveUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EvangelionState extends BaseState {

    public int AttackStrengthDelta = 0;

    public List<WingLoadout> Wings = new ArrayList<>();
    // 0 = Unarmed Attack
    // 1 = Right hand
    // 2 = Left Hand

    public EvangelionState(String playerName, String typeString) {
        super(playerName, typeString);
        Weapon UnarmedAttack = Weapon.getUnarmedAttack();
        Weapons.add(UnarmedAttack);
    }


    public Weapon getRightHandWeapon(){
        if (Weapons.size() > 1)
        return Weapons.get(1); else return null;
    }

    public Weapon getThirdHandWeapon(){
        if (Weapons.size() > 3)
            return Weapons.get(3); else return null;
    }
    public Weapon getLeftHandWeapon(){
        if (Weapons.size() > 2)
        return Weapons.get(2); else return null;
    }
    public void setRightHandWeapon(Weapon weapon){
        if (getRightHandWeapon() != null) {
        Weapons.add(1, weapon);
        Weapons.remove(2); } else Weapons.add(1, weapon);
    }
    public void setLeftHandWeapon(Weapon weapon){
        if (getLeftHandWeapon() != null) {
        Weapons.add(2, weapon);
        Weapons.remove(3);} else Weapons.add(2, weapon);
    }

    public void setThirdHandWeapon(Weapon weapon){
        if (getThirdHandWeapon() != null) {
            Weapons.add(3, weapon);
            Weapons.remove(4);} else Weapons.add(3, weapon);
    }

    @Override
    public void ApplyStateEffects(Weapon weapon){
        for (StateEffect Effect : StateEffects) {
            if (Effect.WeaponSpecific == null || Effect.WeaponSpecific == weapon) {
            SpeedChange = Math.max(SpeedChange, Effect.SpeedChange);
            ArmorDelta += Effect.ArmorDelta;
            SpeedDelta += Effect.SpeedDelta;
            MaxRangeDelta += Effect.RangeDelta;
            MaxToughnessDelta +=Effect.MaxToughnessDelta;
            ReflexesDelta += Effect.ReflexesDelta;
            AccuracyDelta += Effect.AccuracyDelta;
            PenetrationDelta += Effect.PenetrationDelta;
            AttackStrengthDelta += Effect.AttackStrengthDelta;
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
        MaxRangeDelta = 0;
        SpeedDelta = 0;
        PenetrationDelta = 0;
        MaxToughnessDelta = 0;
        ReflexesDelta = 0;
        AccuracyDelta = 0;
        AttackStrengthDelta = 0;
        ProhibitedActions = new ArrayList<>();
        SpeedChange = 0;
        ProcessStateEffects();
        ApplyStateEffects(weapon);
    }




}



