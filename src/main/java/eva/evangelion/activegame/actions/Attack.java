package eva.evangelion.activegame.actions;

import eva.evangelion.activegame.activeunits.Weapon;
import eva.evangelion.activegame.activeunits.unitstate.StateEffect;

import java.lang.annotation.Target;
import java.util.List;

import static eva.evangelion.activegame.actions.Attack.TossDirection.NONE;

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
    public int toss;
    public enum TossDirection {
        UP, UPRIGHT, UPLEFT, RIGHT, LEFT, DOWN, DOWNLEFT, DOWNRIGHT, NONE
    }
    public TossDirection direction;

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
        this.toss = 0;
        this.direction = NONE;
    }

    public Attack(String attacker, String defender, int AttackTest, int TargetNumber, int damage, int attackerX, int attackerY,
                  int defenderX, int defenderY, int penetration, Weapon weapon, List<StateEffect> onHitEffect, int tossdistance, TossDirection direction) {
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
        this.toss = tossdistance;
        this.direction = direction;
    }
    public static TossDirection figureoutDirection(int x1, int y1, int x2, int y2) {
        int dx = x2 - x1;
        int dy = y2 - y1;

        if (dx == 0) {
            return dy > 0 ? TossDirection.UP : TossDirection.DOWN;
        } else if (dy == 0) {
            return dx > 0 ? TossDirection.RIGHT : TossDirection.LEFT;
        } else if (dx > 0) {
            return dy > 0 ? TossDirection.UPRIGHT : TossDirection.DOWNRIGHT;
        } else {
            return dy > 0 ? TossDirection.UPLEFT : TossDirection.DOWNLEFT;
        }
    }
    public TossDirection getDirection() {
        return direction;
    }
    public int getTossDistance() {
        return toss;
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
