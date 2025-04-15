package eva.evangelion.activegame.activeunits;


import eva.evangelion.activegame.actions.Attack;
import eva.evangelion.activegame.activeunits.unitstate.BaseState;
import eva.evangelion.activegame.activeunits.unitstate.ChazaqielSummonState;
import eva.evangelion.activegame.activeunits.unitstate.EvangelionState;
import eva.evangelion.activegame.activeunits.unitstate.StateEffect;
import eva.evangelion.units.Types.AngelType;
import eva.evangelion.units.Types.EvangelionType;
import javafx.scene.shape.Circle;
import kotlin.Pair;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.random.RandomGenerator;

public class ChazaqielSummon extends BaseUnit {

    public ChazaqielSummonState state;
    public BaseUnit CopyUnit;

    public ChazaqielSummon(BaseUnit unit, ChazaqielSummonState state) {
        UnitCircle = new Circle(10);
        this.state = state;
        this.CopyUnit = unit;
        UnitCircle.setFill(unit.UnitCircle.getFill());
    }

    public String getPlayerName(){return state.PlayerName;}
    public String getCopyName(){return state.TypeString;}
    @Override
    public boolean isTurnDone() {
        return true;
    }

    public int getX(){return state.x;}
    public int getY(){return state.y;}
    public void setX(int x){state.x = x;}
    public void setY(int y){state.y = y;}

    public void setStamina(int stamina){this.state.Stamina = stamina;}
    public int getStamina() {
        return this.state.Stamina;
    }
    public int getSpeed() {
        return CopyUnit.getSpeed();
    }
    @Override
    public int getAccuracy() {
        return CopyUnit.getAccuracy();
    }
    @Override
    public int getArmor() {
        return CopyUnit.getArmor();
    }
    @Override
    public int getReflexes() {
        return CopyUnit.getReflexes();
    }
    public List<StateEffect> getEffects(){
        return state.StateEffects;
    }
    public void RoundTickEffect(){
        state.RoundTickEffects();
    }
    public void WeaponSpecificTickEffect(Weapon weapon){
        state.WeaponSpecificTickEffect(weapon);
    }
    public void AttackTickEffect(){
        state.AttackTickEffect();
    }
    public void GuardTickEffect(){
        state.GuardTickEffect();
    }
    public void ConditionTickEffect(StateEffect.ExpirationCondition condition){
        state.ConditionTickEffect(condition);
    }
    public List<Weapon> GetWeaponList() {
        return state.Weapons;
    }
    public int getUnitStrength(Weapon w) {
        return CopyUnit.getUnitStrength(w);
    }
    public int getToughness(){
        return CopyUnit.getToughness();
    }
    public int getMaxToughness(){
        return CopyUnit.getMaxToughness();
    }

    public boolean UsedGuard(){return true;}
    public boolean UsedAttack(){return false;}
    public Weapon NextWeapon(Weapon weapon) {
        if (weapon == null) return state.Weapons.get(0);
        if (weapon.hasSubWeapon()) return weapon.getSubWeapon();
        Weapon weapon1;
        if (weapon.isSubWeapon()) weapon1 = weapon.getParentWeapon();
        else weapon1 = weapon;
        if (state.Weapons.contains(weapon1)) {
            if (state.Weapons.indexOf(weapon1)+1 == state.Weapons.size()) {
                return state.Weapons.get(0);
            } else return state.Weapons.get(state.Weapons.indexOf(weapon1)+1);
        } return state.Weapons.get(0);
    }

}
