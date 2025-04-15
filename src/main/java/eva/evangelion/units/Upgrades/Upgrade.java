package eva.evangelion.units.Upgrades;

import eva.evangelion.activegame.activeunits.Weapon;
import eva.evangelion.activegame.activeunits.unitstate.StateEffect;
import eva.evangelion.activegame.activeunits.unitstate.WingLoadout;

import java.util.ArrayList;
import java.util.List;

public class Upgrade {
    public final String Name;
    public final boolean Passive;
    public final boolean PredicatedPassive;
    public final boolean WeaponPassive;
    public final boolean Active;
    // public final List<String> Upgrades;
    public final int AccuracyDelta;

    public final int AttackStrengthDelta;

    public final int ToughnessDelta;

    public final int ArmorDelta;

    public final int ReflexesDelta;

    public final int SpeedDelta;
    public List<Weapon.Property> PotentialProperties = new ArrayList<>();
    public int PotentialDefensive = 0;
    public int PotentialArea = -1;
    public int PotentialPenetration = 0;


    public final int RequisitionDelta;

    public final int UpgradesAvaliableDelta;

    public final int AccuracyDeltaPredicated;

    public final int AttackStrengthDeltaPredicated;

    public final int ToughnessDeltaPredicated;

    public final int ArmorDeltaPredicated;

    public final int ReflexesDeltaPredicated;

    public final int SpeedDeltaPredicated;

    public final int RequisitionDeltaPredicated;

    public final int UpgradesAvaliableDeltaPredicated;
    public StateEffect SpreadPatternStateEffect;

    public WingLoadout loadout;

    public Upgrade(String name,  boolean passive, boolean predicatedPassive, boolean weaponPassive, boolean active, int accuracyDelta, int attackStrengthDelta, int toughnessDelta, int armorDelta, int reflexesDelta, int speedDelta, int requisitionDelta, int upgradesAvaliableDelta, int accuracyDeltaPredicated, int attackStrengthDeltaPredicated, int toughnessDeltaPredicated, int armorDeltaPredicated, int reflexesDeltaPredicated, int speedDeltaPredicated, int requisitionDeltaPredicated, int upgradesAvaliableDeltaPredicated){
        Name = name;
        Passive = passive;
        PredicatedPassive = predicatedPassive;
        WeaponPassive = weaponPassive;
        Active = active;
      //  Upgrades = upgrades;

        AccuracyDelta = accuracyDelta;
        AttackStrengthDelta = attackStrengthDelta;
        ToughnessDelta = toughnessDelta;
        ArmorDelta = armorDelta;
        ReflexesDelta = reflexesDelta;
        SpeedDelta = speedDelta;
        RequisitionDelta = requisitionDelta;

        UpgradesAvaliableDelta = upgradesAvaliableDelta;
        AccuracyDeltaPredicated = accuracyDeltaPredicated;
        AttackStrengthDeltaPredicated = attackStrengthDeltaPredicated;
        ToughnessDeltaPredicated = toughnessDeltaPredicated;
        ArmorDeltaPredicated = armorDeltaPredicated;
        ReflexesDeltaPredicated = reflexesDeltaPredicated;
        SpeedDeltaPredicated = speedDeltaPredicated;
        RequisitionDeltaPredicated = requisitionDeltaPredicated;
        UpgradesAvaliableDeltaPredicated = upgradesAvaliableDeltaPredicated;
    }

    public void makePotentialGrapple() {
        PotentialProperties.add(Weapon.Property.GRAPPLE);
    }

    public void removePotentialGrapple() {
        PotentialProperties.remove(Weapon.Property.GRAPPLE);
    }

    public void makePotentialIntrinsic() {
        PotentialProperties.add(Weapon.Property.INTRINSIC);
    }

    public void removePotentialIntrinsic() {
        PotentialProperties.remove(Weapon.Property.INTRINSIC);
    }

    public void makePotentialPrecise() {
        PotentialProperties.add(Weapon.Property.PRECISE);
    }

    public void removePotentialPrecise() {
        PotentialProperties.remove(Weapon.Property.PRECISE);
    }

    public void makePotentialProven() {
        PotentialProperties.add(Weapon.Property.PROVEN);
    }

    public void removePotentialProven() {
        PotentialProperties.remove(Weapon.Property.PROVEN);
    }

    public void makePotentialReach() {
        PotentialProperties.add(Weapon.Property.REACH);
    }

    public void removePotentialReach() {
        PotentialProperties.remove(Weapon.Property.REACH);
    }

    public void makePotentialCQB() {
        PotentialProperties.add(Weapon.Property.CQB);
    }

    public void removePotentialCQB() {
        PotentialProperties.remove(Weapon.Property.CQB);
    }

    public void makePotentialSwift() {
        PotentialProperties.add(Weapon.Property.SWIFT);
    }

    public void removePotentialSwift() {
        PotentialProperties.remove(Weapon.Property.SWIFT);
    }

    public void makePotentialSmall() {
        PotentialProperties.add(Weapon.Property.SMALL);
    }

    public void makePotentialArmorPiercing() {
        PotentialProperties.add(Weapon.Property.ARMORPIERCING);
    }
    public void removePotentialArmorPiercing() {
        PotentialProperties.remove(Weapon.Property.ARMORPIERCING);
    }
    public void removePotentialSmall() {
        PotentialProperties.remove(Weapon.Property.SMALL);
    }

    public void makePotentialSpray() {
        PotentialProperties.add(Weapon.Property.SPRAY);
    }

    public void removePotentialSpray() {
        PotentialProperties.remove(Weapon.Property.SPRAY);
    }

    public void makePotentialThrowing() {
        PotentialProperties.add(Weapon.Property.THROWING);
    }

    public void removePotentialThrowing() {
        PotentialProperties.remove(Weapon.Property.THROWING);
    }




}
