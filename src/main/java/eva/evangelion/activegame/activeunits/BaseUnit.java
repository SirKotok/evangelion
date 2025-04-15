package eva.evangelion.activegame.activeunits;

import eva.evangelion.activegame.actions.Attack;
import eva.evangelion.activegame.activeunits.unitstate.StateEffect;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.List;

public class BaseUnit {
    public Circle UnitCircle;
    public boolean UsedTactical(){return false;}
    public void SetUsedTactical(boolean usedtactical){
    }
    public int getUnitStrength(Weapon w) {
        return 0;
    }
    public void hurt(Attack attack){

    }
    public boolean hasUpgrade(String S) {
        return false;
    }
    public void removeWeapon(Weapon w) {

    }

    public boolean canDrop(Weapon weapon){
        if (weapon.ParentWeapon != null) return false;
        return !weapon.isIntrinsic();
    }

    public void RemovePotentialEffects(){

    }
    public void RemoveOnMoveEffects(){

    }
    public int getMaxRange(Weapon weapon) {
     return weapon.getMaxRange();
    }
    public int getMinRange(Weapon weapon) {
    return weapon.getMinRange();
    }
    public List<StateEffect> getEffects(){
        return null;
    }
    public List<String> getNameEffects(){
        List<String> eff = new ArrayList<>();
        for (StateEffect effect : getEffects()) {
            eff.add(effect.Name);
        }
        return eff;
    }

    public List<String> getProhibitedActions(){
        return null;
    }


    public void dealDamage(int strain){
    }
    public void AddEffect(StateEffect effect) {
    }
    public void setATP(int atp) {
    }

    public void addDoom(int Doom) {
    }
    public void addFate(int Fate) {
    }
    public void useATP() {
        setATP(0);
    }
    public boolean UsedGuard(){return false;}
    public boolean UsedAttack(){return false;}
    public void SetUsedGuard(boolean usedguard){

    }
    public void SetUsedAttack(boolean usedattack){

    }
    public boolean isTurnDone(){
        return false;
    }

    public Weapon NextWeapon(Weapon weapon) {
        return null;
    }
    public List<Weapon> GetWeaponList() {
        return new ArrayList<>();
    }
    public void SetTurnDone(boolean turndone){

    }
    public Weapon NeedsDoubleEdgeCheck() {
        for (Weapon weapon : GetWeaponList()) {
            if (weapon.requiresDoubleEdgeCheck()) {
                return weapon;
            }
        }
        return null;
    }
    public int getX(){return 0;}
    public int getY(){return 0;}
    public int getStamina(){return 0;}
    public int getSpeed(){return 0;}
    public int getWoundLevel(){return 0;}
    public int getAccuracy(){return 0;}
    public int getReflexes(){return 0;}
    public int getArmor(){return 0;}
    public int getMaxToughness(){return 0;}
    public int getToughness(){return 0;}
    public void setX(int x){}
    public void setY(int y){}
    public void setStamina(int stamina){}
    public void IncreaseWoundLevel(){

    }
    public void RoundTickEffect(){

    }
    public void WeaponSpecificTickEffect(Weapon weapon){
    }
    public void AttackTickEffect(){

    }
    public void GuardTickEffect(){

    }

    public void ConditionTickEffect(StateEffect.ExpirationCondition condition){

    }
    public int getATP(){
        return 0;
    }

    public String getPlayerName(){return "";}

    public void UpdateCalc() {
    }
    public void UpdateCalcWeapon(Weapon weapon) {
    }
    public int getPenetration() {
        return 0;
    }
}
