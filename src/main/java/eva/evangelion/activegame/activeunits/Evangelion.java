package eva.evangelion.activegame.activeunits;

import eva.evangelion.activegame.actions.Attack;
import eva.evangelion.activegame.activeunits.unitstate.EvangelionState;
import eva.evangelion.activegame.activeunits.unitstate.StateEffect;
import eva.evangelion.activegame.activeunits.unitstate.WingLoadout;
import eva.evangelion.units.Types.EvangelionType;
import eva.evangelion.util.EvaCalculationUtil;
import eva.evangelion.util.EvaSaveUtil;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Evangelion extends BaseUnit {

    public EvangelionState state;
    public final EvangelionType type;

    public void setATP(int atp) {
        state.ATP = atp;
    }
    public void useATP() {
        setATP(0);
    }
    public int getATP(){
        return state.ATP;
    }

    public Evangelion(EvangelionState evastate, EvangelionType type) {
        this.state = evastate;
        this.type = type;
        type.CalculateDisplay();
        if (state.Wings.isEmpty()) state.Wings.addAll(type.Loadouts);
        SetWings();
        if (this.state.toughness == -1) this.state.toughness = getMaxToughness();
        if (this.state.Requisition == -1) this.state.Requisition = type.RequisitionDisplay;
        UnitCircle = new Circle(10);
        UnitCircle.setFill(type.EvaColor);

     //   StateEffect debug = new StateEffect("debugtest");
    //    debug.ProhibitedActions.add("Aim");
     //   debug.AdditionalEffects.add(CommonEffects.Prone());
    //    AddEffect(debug);

    }
    public int getMaxRange(Weapon weapon) {
        return weapon.getMaxRange()+state.MaxRangeDelta;
    }
    public int getMinRange(Weapon weapon) {
        return weapon.getMinRange();
    }

    public void SetWings() {
        if (!state.Wings.isEmpty()) {
            state.Wings.get(0).location = "Right";
            state.Wings.get(1).location = "Left";
        }
        for (WingLoadout wing : state.Wings) {
            if (wing.hasWeapon()) wing.getItem().Reload();
        }
    }

    public WingLoadout getRightWing() {
        return state.Wings.get(0);
    }
    public WingLoadout getLeftWing() {
        return state.Wings.get(1);
    }

    public void addDoom(int Doom) {
        state.Doom+=Doom;
    }
    public void addFate(int Fate) {
        state.Fate+=Fate;
    }
    public int getPenetration() {
        return state.PenetrationDelta;
    }
    @Override
    public void hurt(Attack attack) {
        int damage = attack.Damage;
        Weapon weapon = attack.Weapon;
        boolean range = !EvaCalculationUtil.isInRange(attack.AttackerX, attack.AttackerY, attack.DefenderX, attack.DefenderY, 2);
        if (!weapon.isArmorPiercing()) {
        int penetration = attack.Penetration;
        if ((weapon.isPrecise() || (weapon.isTelescopicSight() && range))) {
            penetration+=(int) Math.min(Math.ceil(attack.getDOS()*0.5f), 2);
        }
        int armor = getArmor();
        damage -= Math.max((armor-penetration), 0); }
        this.state.toughness = Math.min(state.toughness, getMaxToughness());
        this.state.toughness-=Math.max(0, damage);
    }

    public void removeWeapon(Weapon w) {
        if (w.equals(state.getRightHandWeapon())) state.setRightHandWeapon(Weapon.Free());
        else if (w.equals(state.getLeftHandWeapon())) state.setLeftHandWeapon(Weapon.Free());
        else state.Weapons.remove(w);
    }

    @Override
    public boolean hasUpgrade(String S) {
        return type.CurrentUpgradeNames.contains(S);
    }

    public void AddEffect(StateEffect effect) {
        state.StateEffects.add(effect);
        UpdateCalc();
    }
    public List<StateEffect> getEffects(){
        return state.StateEffects;
    }
    public List<String> getProhibitedActions(){
        return state.ProhibitedActions;
    }

    public List<String> getNameEffects(){
        List<String> eff = new ArrayList<>();
        for (StateEffect effect : getEffects()) {
            eff.add(effect.Name);
        }
        return eff;
    }
    public void IncreaseWoundLevel(){
        state.woundlevel++;
        state.toughness = this.getMaxToughness();
    }
    public Weapon NeedsDoubleEdgeCheck() {
        for (Weapon weapon : GetWeaponList()) {
            if (weapon.requiresDoubleEdgeCheck()) {
                return weapon;
            }
        }
        return null;
    }
    public void RemovePotentialEffects(){
        state.RemovePotentialEffects();
    }

    public void RemoveOnMoveEffects(){
        state.RemoveOnMoveEffects();
    }

    public void UpdateCalc() {
        UpdateCalcWeapon(null);
    }
    public void UpdateCalcWeapon(Weapon weapon) {
        state.CalculateDelta(weapon);
    }
    public int getAttackStrength(){
        return type.AttackStrengthDisplay+state.AttackStrengthDelta;
    }
    public List<Weapon> GetWeaponList() {
        return state.Weapons;
    }


    public boolean isTurnDone(){
        return state.TurnOrIntervalDone;
    }
    public void SetTurnDone(boolean turndone){
        state.TurnOrIntervalDone = turndone;
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
    public int getUnitStrength(Weapon w) {
        return state.AttackStrengthDelta+type.AttackStrengthDisplay;
    }
    public int getToughness(){
        this.state.toughness = Math.min(state.toughness, getMaxToughness());
        return state.toughness;
    }
    @Override
    public void dealDamage(int strain) {
        this.state.toughness = Math.min(state.toughness, getMaxToughness());
        this.state.toughness-= strain;
    }

    public boolean WeaponInSiegeFrame(Weapon weapon) {
        if (getRightHandItem().equals(weapon) && getRightWing().isSiegeFrame()) return true;
        if (getLeftHandItem().equals(weapon) && getLeftWing().isSiegeFrame()) return true;
        return false;
    }

    public boolean canDrop(Weapon weapon){
        if (WeaponInSiegeFrame(weapon)) return false;
        if (weapon.ParentWeapon != null) return false;
        return !weapon.isIntrinsic();
    }

    @Override
    public int getMaxToughness() {
        return state.MaxToughnessDelta+type.ToughnessDisplay;
    }

    public Weapon NextWeapon(Weapon weapon) {
        if (weapon == null) {
            return state.Weapons.isEmpty() ? null : state.Weapons.get(0);
        }
        if (weapon.hasSubWeapon()) {
            return weapon.getSubWeapon();
        }

        Weapon weapon1 = weapon.isSubWeapon() ? weapon.getParentWeapon() : weapon;

        List<Weapon> checkWeapons = new ArrayList<>(state.Weapons);
        for (WingLoadout wing : state.Wings) {
            if (wing.isAttack() && wing.hasWeapon()) {
                checkWeapons.add(wing.getItem());
            }
        }

        if (checkWeapons.isEmpty()) {
            return null; // Handle empty case appropriately
        }

        int currentIndex = checkWeapons.indexOf(weapon1);
        int startIndex = (currentIndex != -1) ? currentIndex : 0;
        int x = 1;
        Weapon w2 = null;
        while (x <= checkWeapons.size()) {
            int nextIndex = (startIndex + x) % checkWeapons.size();
            w2 = checkWeapons.get(nextIndex);
            if (!w2.isFree()) {
                break;
            }
            x++;
        }

        return (x <= checkWeapons.size()) ? w2 : checkWeapons.get(0);
    }

    /*

        if (weapon == null) {
            return state.Weapons.get(0);
        }
        if (weapon.hasSubWeapon()) {
            return weapon.getSubWeapon();
        }
        Weapon weapon1;
        if (weapon.isSubWeapon()) {
            weapon1 = weapon.getParentWeapon();
        }
        else weapon1 = weapon;
        Weapon w2 = Weapon.Free();
        int x = 1;
        List<Weapon> CheckWeapons = new ArrayList<>(state.Weapons);
        for (WingLoadout wing : state.Wings) {
            if (wing.isAttack() && wing.hasWeapon()) CheckWeapons.add(wing.getItem());
        }
        while (w2.isFree()) {
            if (CheckWeapons.contains(weapon1)) {
                if (CheckWeapons.indexOf(weapon1) + x == CheckWeapons.size()) {
                    w2 =  CheckWeapons.get(0);

                } else {
                    w2 =  CheckWeapons.get(CheckWeapons.indexOf(weapon1) + x);
                }
            } else {
                w2 = CheckWeapons.get(0);
            }
            x++;
        }
        return w2;


     */

    public WingLoadout NextWingForSwap(WingLoadout wing) {
        List<WingLoadout> wings = state.Wings;
        if (wings.isEmpty()) {
            return null;
        }
        int startIndex = 0;
        if (wing != null) {
            int index = wings.indexOf(wing);
            if (index != -1) {
                startIndex = (index + 1) % wings.size();
            }
        }
        for (int i = 0; i < wings.size(); i++) {
            int currentIndex = (startIndex + i) % wings.size();
            WingLoadout candidate = wings.get(currentIndex);
            if (candidate.canStoreWeapon() && !candidate.isAttack()) {
                return candidate;
            }
        }
        return null;
    }




    public Weapon getRightHandItem(){
        return state.getRightHandWeapon();
    }
    public Weapon getThirdHandItem(){
        return state.getThirdHandWeapon();
    }
    public Weapon getHandItem(String hand){
        if (hand.equals("Right") || hand.equals("Right Hand") || hand.equals("Right Arm"))
        return getRightHandItem();
        else return getLeftHandItem();
    }

    public boolean isSetUp(){
        boolean right = getRightHandItem() != null;
        boolean left = getLeftHandItem() != null;
        boolean third = getThirdHandItem() != null || !this.hasUpgrade("Extraneous Limbs");
        boolean wings = true;
        for (WingLoadout wingLoadout : state.Wings) {
            if (wingLoadout.canStoreWeapon() && wingLoadout.getItem() == null) {
                wings = false;
                break;
            }
        }
        return right && left && third && wings;
    }
    public String getSetUpLocationString(){
        boolean right = getRightHandItem() == null;
        boolean left = getLeftHandItem() == null;
        if (right) return "Set Right Hand Item ";
        if (left) return "Set Left Hand Item ";
        if (this.hasUpgrade("Extraneous Limbs") && getThirdHandItem() == null) return "Set Third Hand Item ";
        for (WingLoadout wingLoadout : state.Wings) {
            if (wingLoadout.canStoreWeapon() && wingLoadout.getItem() == null) {
                return "Set "+wingLoadout.loadout.toString().toLowerCase()+" ("+ wingLoadout.getLocation()+") Item";
            }
        }
        return "";
    }


    public String getSetUpLocationShortString(){
        boolean right = getRightHandItem() == null;
        boolean left = getLeftHandItem() == null;
        if (right) return "Right";
        if (left) return "Left";
        if (this.hasUpgrade("Extraneous Limbs") && getThirdHandItem() == null) return "Third";
        for (WingLoadout wingLoadout : state.Wings) {
            if (wingLoadout.canStoreWeapon() && wingLoadout.getItem() == null) {
                return wingLoadout.loadout.toString().toLowerCase();
            }
        }
        return "";
    }

    public Weapon AmmoChooser() {
        boolean right = getRightHandItem().isAmmo();
        boolean left = getLeftHandItem().isAmmo();
        if (right) return getRightHandItem();
        if (left) return getLeftHandItem();
        for (WingLoadout wingLoadout : state.Wings) {
            if (wingLoadout.getItem() != null && wingLoadout.getItem().isAmmo()) {
                return wingLoadout.getItem();
            }
        }
        return null;
    }
    public void SetUpPutItemToLocation(String s) throws IOException {
        String s1 = getSetUpLocationString();
        Weapon item = EvaSaveUtil.ReadStringWeapon(s);
        item.Reload();
        int Cost = item.getCost();
        if (s1.equals("Set Right Hand Item ")) {
            state.Requisition-=Cost;
            state.setRightHandWeapon(item); return;
        }
        if (s1.equals("Set Left Hand Item ")) {
            state.Requisition-=Cost;
            state.setLeftHandWeapon(item); return;
        }
        if (s1.equals("Set Third Hand Item ")) {
            state.Requisition-=Cost;
            state.setThirdHandWeapon(item); return;
        }
        for (WingLoadout wingLoadout : state.Wings) {
            if (wingLoadout.canStoreWeapon() && wingLoadout.getItem() == null) {
                if (wingLoadout.isStorage()) {
                    Cost = Math.max(0, Cost-1);
                }
                state.Requisition-=Cost;
                wingLoadout.setItem(item);
                return;
            }
        }
    }


    public Weapon getLeftHandItem(){
        return state.getLeftHandWeapon();
    }

    public boolean hasFreeHand(){
      return state.getLeftHandWeapon() == null || state.getRightHandWeapon() == null || state.getLeftHandWeapon().isFree() || state.getRightHandWeapon().isFree();
    }


    public int getWoundLevel() {return this.state.woundlevel;}
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
    @Override
    public int getSpeed() {
        switch (state.SpeedChange) {
        case 1 -> {return (int)Math.ceil((state.SpeedDelta+type.SpeedDisplay)*0.5f);}
        case 2 -> {return 0;}
        }
        return state.SpeedDelta+type.SpeedDisplay;
    }
    @Override
    public int getAccuracy() {
        return state.AccuracyDelta+type.AccuracyDisplay;
    }
    @Override
    public int getArmor() {
        return state.ArmorDelta+type.ArmorDisplay;
    }
    @Override
    public int getReflexes() {
        int Defensive = 0;
        for (Weapon weapon : state.Weapons) {
            Defensive = Math.max(weapon.getDefensive(), Defensive);
        }
        return Math.min(state.ReflexesDelta+type.ReflexesDisplay+Defensive, 65)+(getNameEffects().contains("FateDefence") ? 100 : 0);
    }

    public int getRequisition() {
        return state.Requisition;
    }
    public void setRequisition(int requisition) {
        state.Requisition = requisition;
    }
    @Override
    public String getPlayerName() {
        return state.PlayerName;
    }
    public boolean UsedTactical(){return state.UsedTacticalAction;}
    public boolean UsedGuard(){return state.UsedGuard;}
    public boolean UsedAttack(){return state.UsedAttackAction;}
    public boolean UsedAngelicCore(){return state.UsedAngelicCore;}
    public void SetUsedGuard(boolean usedguard){
        state.UsedGuard = usedguard;
    }
    public void SetUsedAngelicCore(boolean angeliccore){
        state.UsedAngelicCore = angeliccore;
    }
    public void SetUsedAttack(boolean usedattack){
        state.UsedAttackAction = usedattack;
    }
    public void SetUsedTactical(boolean usedtactical){
        state.UsedTacticalAction = usedtactical;
    }
    public boolean UsedRedOrgans(){return state.UsedRedundantOrgans;}
    public void SetRedOrgans(boolean usedRO){
        state.UsedRedundantOrgans = usedRO;
    }

    public void setX(int x){this.state.x = x;}
    public void setY(int y){this.state.y = y;}
    public void setStamina(int stamina){this.state.Stamina = stamina;}
}
