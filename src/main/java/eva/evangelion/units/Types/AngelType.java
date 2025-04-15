package eva.evangelion.units.Types;

import eva.evangelion.activegame.activeunits.unitstate.StateEffect;
import eva.evangelion.units.Upgrades.Upgrade;
import kotlin.Pair;

import java.util.ArrayList;
import java.util.List;

public class AngelType {
    public final int BaseAccuracy = 70; //65
    public final int BaseMeleeStrength = 2; //4
    public final int BaseRangedStrength = 6; //4
    public final int BasePenetration = 1; //1
    public final int BaseToughness = 25; //20
    public final int BaseArmor = 5; //3
    public final int BaseReflexes = 33; //25
    public final int BaseSpeed = 3; //3


    public int AccuracyDisplay;
    public int AccuracyNext;
    public int MeleeStrengthDisplay;
    public int MeleeStrengthNext;

    public int RangedStrengthDisplay;
    public int RangedStrengthNext;

    public int PenetrationDisplay;
    public int PenetrationNext;
    public int ToughnessDisplay;
    public int ToughnessNext;
    public int ArmorDisplay;
    public int ArmorNext;
    public int ReflexesDisplay;
    public int ReflexesNext;
    public int SpeedDisplay;
    public int SpeedNext;

    public String Name;

    public List<Upgrade> CurrentUpgrades;
    public List<Upgrade> ChosenUpgrades;
    public List<Pair<Integer, StateEffect>> WoundEffects;
    public AngelType(){
        CurrentUpgrades = new ArrayList<>();
        ChosenUpgrades = new ArrayList<>();

        setDisplaytoBase();
        setNexttoBase();
    }

    public void setNexttoBase(){
        AccuracyNext = BaseAccuracy;
        MeleeStrengthNext = BaseMeleeStrength;
        ToughnessNext = BaseToughness;
        ArmorNext = BaseArmor;
        ReflexesNext = BaseReflexes;
        SpeedNext = BaseSpeed;
        RangedStrengthNext = BaseRangedStrength;
        PenetrationNext = BasePenetration;
    }
    public void setDisplaytoBase(){
        AccuracyDisplay = BaseAccuracy;
        MeleeStrengthDisplay = BaseMeleeStrength;
        ToughnessDisplay = BaseToughness;
        ArmorDisplay = BaseArmor;
        ReflexesDisplay = BaseReflexes;
        SpeedDisplay = BaseSpeed;
        RangedStrengthDisplay = BaseRangedStrength;
        PenetrationDisplay = BasePenetration;
    }


    public void ApplyUpgradeToNext(Upgrade upgrade){
        AccuracyNext += upgrade.AccuracyDelta;
        MeleeStrengthNext += upgrade.AttackStrengthDelta;
        ToughnessNext += upgrade.ToughnessDelta;
        ArmorNext += upgrade.ArmorDelta;
        ReflexesNext += upgrade.ReflexesDelta;
        SpeedNext += upgrade.SpeedDelta;

    }

    public void ApplyUpgradeToDisplay(Upgrade upgrade){
        AccuracyDisplay+=upgrade.AccuracyDelta;
        MeleeStrengthDisplay += upgrade.AttackStrengthDelta;
        ToughnessDisplay += upgrade.ToughnessDelta;
        ArmorDisplay += upgrade.ArmorDelta;
        ReflexesDisplay += upgrade.ReflexesDelta;
        SpeedDisplay += upgrade.SpeedDelta;

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
        upgradeList.addAll(CurrentUpgrades);
        if (upgradeList != null) {
            for (Upgrade upgrade : upgradeList) {
                ApplyUpgradeToDisplay(upgrade);
            }
        }
    }




}
