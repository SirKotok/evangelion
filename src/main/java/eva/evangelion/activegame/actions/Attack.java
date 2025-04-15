package eva.evangelion.activegame.actions;

import eva.evangelion.activegame.activeunits.Weapon;
import eva.evangelion.activegame.activeunits.unitstate.StateEffect;

import java.lang.annotation.Target;
import java.util.List;

public class Attack extends Action{
    public final String Attacker;
    public final String Defender;
    public final int Damage;
    public final int Penetration;
    public final int Test;
    public final int DefenderX;
    public final int DefenderY;
    public final int AttackerX;
    public final int AttackerY;
    public final int TargetNumber;
    public final Weapon Weapon;
    public final List<StateEffect> OnHitEffect;

    public Attack(String attacker, String defender, int AttackTest, int TargetNumber, int damage, int attackerX, int attackerY,
                  int defenderX, int defenderY, int penetration, Weapon weapon, List<StateEffect> onHitEffect) {
        super(attacker);
        Attacker = attacker;
        Defender = defender;
        DefenderX = defenderX;
        AttackerX = attackerX;
        AttackerY = attackerY;
        Damage = damage;
        DefenderY = defenderY;
        Weapon = weapon;
        Test = AttackTest;
        this.TargetNumber = TargetNumber;
        Penetration = penetration;
        this.OnHitEffect = onHitEffect;
    }

    public int getDOS(){
        if (Test < TargetNumber) {
            int Multiple = TargetNumber - Test;
            return (int) Math.floor(Multiple*0.1f);
        }
        return 0;
    }

    public boolean Missed(){
        return Test > TargetNumber;
    }
    public int getDOF(){
        if (Test > TargetNumber) {
            int Multiple = -TargetNumber + Test;
            return (int) Math.floor(Multiple*0.1f);
        }
        return 0;
    }
}
