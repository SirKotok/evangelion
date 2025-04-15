package eva.evangelion.units.Types;

import eva.evangelion.activegame.activeunits.unitstate.StateEffect;
import eva.evangelion.activegame.activeunits.unitstate.WingLoadout;
import eva.evangelion.units.Upgrades.ATPower;
import eva.evangelion.units.Upgrades.Upgrade;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class EvangelionType {
    // Accuracy 75
    // Attack Strength 4
    // Toughness 15
    // Armor 3
    // Reflexes 25
    // Speed 3

    // Requisition 3
    // Upgrades Avaliable 3

    public List<WingLoadout> Loadouts;
    public Color EvaColor;
    public final int BaseAccuracy = 75;
    public final int BaseAttackStrength = 4;
    public final int BaseToughness = 15;
    public final int BaseArmor = 3;
    public final int BaseReflexes = 25;
    public final int BaseSpeed = 3;
    public final int BaseRequisition = 3;
    public final int BaseUpgradesAvaliable = 3;
    public final int BaseRangedRange = 0;
    public final int BaseMeleeRange = 0;

    public int AccuracyDisplay;
    public int AccuracyNext;
    public int AttackStrengthDisplay;
    public int AttackStrengthNext;
    public int ToughnessDisplay;
    public int ToughnessNext;
    public int ArmorDisplay;
    public int ArmorNext;
    public int ReflexesDisplay;
    public int ReflexesNext;
    public int SpeedDisplay;
    public int SpeedNext;
    public int RequisitionDisplay;
    public int RequisitionNext;
    public int UpgradesAvaliableDisplay;
    public int UpgradesAvaliableNext;
    public String Name;

    public List<Upgrade> CurrentUpgrades;
    public List<ATPower> CurrentATPowers = new ArrayList<>();
    public List<Upgrade> CurrentWeaponUpgrades = new ArrayList<>();
    public List<String> CurrentUpgradeNames;
    public List<Upgrade> ChosenUpgrades;
    public List<StateEffect> SpreadPatterns = new ArrayList<>();

    public EvangelionType(){
        CurrentUpgrades = new ArrayList<>();
        CurrentUpgradeNames = new ArrayList<>();
        ChosenUpgrades = new ArrayList<>();

        setDisplaytoBase();
        setNexttoBase();

    }


    public void setNexttoBase(){
        AccuracyNext = BaseAccuracy;
        AttackStrengthNext = BaseAttackStrength;
        ToughnessNext = BaseToughness;
        ArmorNext = BaseArmor;
        ReflexesNext = BaseReflexes;
        SpeedNext = BaseSpeed;
        RequisitionNext = BaseRequisition;
        UpgradesAvaliableNext = BaseUpgradesAvaliable;
    }
    public void setDisplaytoBase(){
        AccuracyDisplay = BaseAccuracy;
        AttackStrengthDisplay = BaseAttackStrength;
        ToughnessDisplay = BaseToughness;
        ArmorDisplay = BaseArmor;
        ReflexesDisplay = BaseReflexes;
        SpeedDisplay = BaseSpeed;
        RequisitionDisplay = BaseRequisition;
        UpgradesAvaliableDisplay = BaseUpgradesAvaliable;
    }


    public void ApplyUpgradeToNext(Upgrade upgrade){
        AccuracyNext += upgrade.AccuracyDelta;
        AttackStrengthNext += upgrade.AttackStrengthDelta;
        ToughnessNext += upgrade.ToughnessDelta;
        ArmorNext += upgrade.ArmorDelta;
        ReflexesNext += upgrade.ReflexesDelta;
        SpeedNext += upgrade.SpeedDelta;
        RequisitionNext += upgrade.RequisitionDelta;
        UpgradesAvaliableNext += upgrade.UpgradesAvaliableDelta;
    }

    public void ApplyUpgradeToDisplay(Upgrade upgrade){
        AccuracyDisplay+=upgrade.AccuracyDelta;
        AttackStrengthDisplay += upgrade.AttackStrengthDelta;
        ToughnessDisplay += upgrade.ToughnessDelta;
        ArmorDisplay += upgrade.ArmorDelta;
        ReflexesDisplay += upgrade.ReflexesDelta;
        SpeedDisplay += upgrade.SpeedDelta;
        RequisitionDisplay += upgrade.RequisitionDelta;
        UpgradesAvaliableDisplay += upgrade.UpgradesAvaliableDelta;
    }


    public void CalculateNext(){
        setNexttoBase();
        List<Upgrade> upgradeList = new ArrayList<>();
        upgradeList.addAll(ChosenUpgrades);
        if (upgradeList != null) {
            for (Upgrade upgrade : upgradeList) {
                ApplyUpgradeToNext(upgrade);
            }
        }
    }
    public void CalculateDisplay(){
        setDisplaytoBase();
        List<Upgrade> upgradeList = new ArrayList<>();
        List<Upgrade> weaponupgradeList = new ArrayList<>();
        List<ATPower> ATPowers = new ArrayList<>();
        upgradeList.addAll(CurrentUpgrades);
        CurrentUpgradeNames = new ArrayList<>();
        SpreadPatterns = new ArrayList<>();
        Loadouts = new ArrayList<>();
        for (Upgrade upgrade : upgradeList) {
            CurrentUpgradeNames.add(upgrade.Name);
            if (upgrade instanceof ATPower) ATPowers.add((ATPower) upgrade);
            if (upgrade.WeaponPassive) weaponupgradeList.add(upgrade);
            ApplyUpgradeToDisplay(upgrade);
            if (upgrade.loadout != null) {
                if (Loadouts.contains(upgrade.loadout)) return;
                Loadouts.add(upgrade.loadout);
            }
        }
        CurrentATPowers = ATPowers;
        CurrentWeaponUpgrades = weaponupgradeList;
    }






}
