package eva.evangelion.activegame.activeunits;

import eva.evangelion.activegame.actions.Attack;
import eva.evangelion.activegame.activeunits.unitstate.AngelState;
import eva.evangelion.activegame.activeunits.unitstate.StateEffect;
import eva.evangelion.units.Types.AngelType;
import eva.evangelion.util.EvaCalculationUtil;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import kotlin.Pair;

import java.util.ArrayList;
import java.util.List;

public class Angel extends BaseUnit{
    public final AngelType type;
    public AngelState state;


    public Angel(AngelState angelstate, AngelType type) {
        this.state = angelstate;
        this.type = type;
        type.CalculateDisplay();
        this.state.toughness = type.ToughnessDisplay+angelstate.MaxToughnessDelta;
        UnitCircle = new Circle(10);
        UnitCircle.setFill(Color.RED);
    }
    public void removeWeapon(Weapon w) {
        state.Weapons.remove(w);
    }
    @Override
    public void hurt(Attack attack) {
        int damage = attack.Damage;
        Weapon weapon = attack.Weapon;
        if (!weapon.isArmorPiercing()) {
            int penetration = attack.Penetration;
            if (weapon.isPrecise() || (weapon.isTelescopicSight() &&
                    !EvaCalculationUtil.isInRange(attack.AttackerX, attack.AttackerY, attack.DefenderX, attack.DefenderY, 2)) ) {
                penetration+=(int) Math.min(Math.ceil(attack.getDOS()*0.5f), 2);
            }
            int armor = getArmor();
            damage -= Math.max((armor-penetration), 0); }
        this.state.toughness-=Math.max(0, damage);
    }

    public List<Weapon> GetWeaponList() {
        return state.Weapons;
    }
    public void RemovePotentialEffects(){
        state.RemovePotentialEffects();
    }
    public void RemoveOnMoveEffects(){
        state.RemoveOnMoveEffects();
    }
    public List<StateEffect> getEffects(){
        return state.StateEffects;
    }
    public List<String> getNameEffects(){
        List<String> eff = new ArrayList<>();
        for (StateEffect effect : getEffects()) {
            eff.add(effect.Name);
        }
        return eff;
    }

    public List<String> getProhibitedActions(){
        return state.ProhibitedActions;
    }
    public void IncreaseWoundLevel(){
        state.woundlevel++;
   //     for (Pair<Integer, StateEffect> woundeffect : type.WoundEffects) {
    //        if (state.woundlevel == woundeffect.getFirst()) state.StateEffects.add(woundeffect.getSecond());
    //    }
        UpdateCalc();
        state.toughness = this.getMaxToughness();
    }

    public int getUnitStrength(Weapon w) {
        if (w.Ranged) return state.RangedStrengthDelta+type.RangedStrengthDisplay;
        else return state.MeleeStrengthDelta+type.MeleeStrengthDisplay;
    }
    @Override
    public void dealDamage(int strain) {
        this.state.toughness-= strain;
    }

    public void AddEffect(StateEffect effect) {
        state.StateEffects.add(effect);
        UpdateCalc();
    }
    public void setATP(int atp) {
        state.ATP = atp;
    }
    public void useATP() {
        setATP(0);
    }
    public Weapon NeedsDoubleEdgeCheck() {
        for (Weapon weapon : GetWeaponList()) {
            if (weapon.requiresDoubleEdgeCheck()) {
                return weapon;
            }
        }
        return null;
    }
    public void UpdateCalc() {
        UpdateCalcWeapon(null);
    }
    public void UpdateCalcWeapon(Weapon weapon) {
        state.CalculateDelta(weapon);
    }

    public int getWoundLevel() {return this.state.woundlevel;}
    @Override
    public int getArmor() {
        return state.ArmorDelta+type.ArmorDisplay;
    }
    @Override
    public int getMaxToughness() {
        return state.MaxToughnessDelta+type.ToughnessDisplay;
    }
    public int getToughness(){
        return state.toughness;
    }
    @Override
    public int getX() {
        return state.x;
    }
    @Override
    public int getY() {
        return state.y;
    }
    @Override
    public int getStamina() {
        return state.Stamina;
    }
    public int getPenetration() {
        return state.PenetrationDelta+ type.PenetrationDisplay;
    }
    public boolean UsedGuard(){return state.UsedGuard;}
    public boolean UsedAttack(){return state.UsedAttackAction;}
    public boolean UsedTactical(){return state.UsedTacticalAction;}
    public void SetUsedTactical(boolean usedtactical){
        state.UsedTacticalAction = usedtactical;
    }
    public void SetUsedGuard(boolean usedguard){
        state.UsedGuard = usedguard;
    }
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
    public void SetUsedAttack(boolean usedattack){
        state.UsedAttackAction = usedattack;
    }
    @Override
    public int getAccuracy() {
        return state.AccuracyDelta+type.AccuracyDisplay;
    }
    public boolean isTurnDone(){
        return state.TurnOrIntervalDone;
    }
    public void SetTurnDone(boolean turndone){
        state.TurnOrIntervalDone = turndone;
    }
    @Override
    public int getReflexes() {
        return state.ReflexesDelta+type.ReflexesDisplay;
    }
    public void RoundTickEffect(){
        state.RoundTickEffects();
    }
    @Override
    public int getSpeed() {
        return state.SpeedDelta+type.SpeedDisplay;
    }

    @Override
    public String getPlayerName() {
        return state.PlayerName;
    }

    public void setX(int x){this.state.x = x;}
    public void setY(int y){this.state.y = y;}
    public void setStamina(int stamina){this.state.Stamina = stamina;}
    public void AttackTickEffect(){
        state.AttackTickEffect();
    }
    public void GuardTickEffect(){
        state.GuardTickEffect();
    }
    public void ConditionTickEffect(StateEffect.ExpirationCondition condition){
        state.ConditionTickEffect(condition);
    }
    public int getATP(){
        return state.ATP;
    }

}
