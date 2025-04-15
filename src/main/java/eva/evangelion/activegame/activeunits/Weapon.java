package eva.evangelion.activegame.activeunits;


import eva.evangelion.activegame.activeunits.unitstate.StateEffect;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Weapon implements Serializable {

    public int x;
    public int y;

    public enum Hand {
        ONE_HANDED,
        TWO_HANDED,
        NONE
    }

    public String Name;
    public String profile;
    public Hand Hands;
    public final int DiceNumber;
    public final int DiceStrength;
    public final boolean Ranged;
    public int Area = -1;
    public int AreaPotential = -1;


    public int Defensive = 0;
    public int Penetration = 0;
    public int DefensivePotential = 0;
    public int PenetrationPotential = 0;
    public int Cost = 0;
    public int MinRange;
    public int MaxRange;
    public int AmmoCapacity = 0;
    public int CurrentAmmo = 0;
    public boolean lineswitch = false;
    public boolean Line = false;
    public List<StateEffect> WeaponEffects = new ArrayList<>();

    public enum Property {
        GRAPPLE, INTRINSIC, PRECISE, PROVEN,
        REACH, CQB, SWIFT, SMALL, SPRAY, THROWING, ARMORPIERCING
    }

    public int getThrowBonus() {
        if (isThrowingProperty() && isThrowingCustomisation()) return 3;
        if (isThrowingProperty() || isThrowingCustomisation()) return 2;
        else return 0;
    }

    public boolean isOverheating = false;

    public int getMaxRange() {
        if (this.Ranged) return MaxRange;
        int x = this.isReach() ? 3 : 1;
        return x;
    }

    public int getMinRange() {
        if (this.Ranged) return MinRange;
        else return 1;
    }

    public enum Customisation {
        ANTI_ARMOR, BALANCED, DOUBLE_EDGED, EXPLOSIVE, EXTRA_AMMO, REINFORCED, THROWING, BAYONET, ENHANCED_BAYONET, TELESCOPIC_SIGHT, AUTO_LOADER
    }

    private boolean needsDoubleEdgeCheck = true;

    public List<Property> CurrentProperties = new ArrayList<>();
    public List<Customisation> CurrentCustomisations = new ArrayList<>();
    public List<Property> PotentialProperties = new ArrayList<>();

    public enum Tech {
        NONE, CHAIN, PROGRESSIVE, POLYTHERMIC, SUPERCONDUCTIVE, GAUSS, N2SHELL, MASER, POSITRON;

        public Tech nextTech() {
            Tech[] values = Tech.values();
            int nextIndex = (this.ordinal() + 1) % values.length;
            return values[nextIndex];
        }

        public Tech nextTechNoNone() {
            Tech[] values = Tech.values();
            int shifted = (this.ordinal() - 1); // Shift to exclude NONE
            int nextShifted = (shifted + 1) % 8; // 8 non-NONE values
            int nextIndex = nextShifted + 1;
            return values[nextIndex];
        }

        private static final Tech[] MELEE_CYCLE = {
                NONE, CHAIN, PROGRESSIVE, POLYTHERMIC, SUPERCONDUCTIVE
        };

        private static final Tech[] MELEE_NO_NONE_CYCLE = {
                CHAIN, PROGRESSIVE, POLYTHERMIC, SUPERCONDUCTIVE
        };

        private static final Tech[] RANGED_CYCLE = {
                NONE, GAUSS, N2SHELL, MASER, POSITRON
        };

        private static final Tech[] RANGED_NO_NONE_CYCLE = {
                GAUSS, N2SHELL, MASER, POSITRON
        };

        // MELEE METHODS
        public Tech nextTechMelee() {
            return nextInCycle(MELEE_CYCLE);
        }

        public Tech nextTechMeleeNoNone() {
            return nextInCycle(MELEE_NO_NONE_CYCLE);
        }

        // RANGED METHODS
        public Tech nextTechRanged() {
            return nextInCycle(RANGED_CYCLE);
        }

        public Tech nextTechRangedNoNone() {
            return nextInCycle(RANGED_NO_NONE_CYCLE);
        }

        // Helper method to handle cycle logic
        private Tech nextInCycle(Tech[] cycle) {
            for (int i = 0; i < cycle.length; i++) {
                if (cycle[i] == this) {
                    return cycle[(i + 1) % cycle.length];
                }
            }
            return cycle[0]; // Default to first element if not found
        }

    }

    public Tech Technology = Tech.NONE;
    public Tech TechnologyBackup = Tech.NONE;
    public Tech Technology2 = Tech.NONE;
    public boolean N2OrMaserUsed = false;
    public int Power;
    public Weapon ParentWeapon;
    public boolean explosivereloadcheck = true;
    public Weapon subWeapon;

    public Weapon(String name, Hand hands, int diceNumber, int diceStrength, int power, boolean ranged) {
        Name = name;
        Hands = hands;
        DiceNumber = diceNumber;
        DiceStrength = diceStrength;
        Power = power;
        Ranged = ranged;
    }

    public Weapon(String name, Hand hands, int diceNumber, int diceStrength, int power, boolean ranged, int minRange, int maxRange) {
        Name = name;
        Hands = hands;
        DiceNumber = diceNumber;
        DiceStrength = diceStrength;
        Power = power;
        Ranged = ranged;
        MinRange = minRange;
        MaxRange = maxRange;
    }

    public String OriginalOwner = "";

    public void addSubWeapon(Weapon weapon) {
        subWeapon = weapon;
        subWeapon.ParentWeapon = this;
    }

    public boolean requiresDoubleEdgeCheck() {
        return isDoubleEdged() && needsDoubleEdgeCheck;
    }

    public void setNeedsDoubleEdgeCheck(boolean check) {
        this.needsDoubleEdgeCheck = check;
    }

    public int getCost() {
        int cost = Cost;
        cost += CurrentCustomisations.size();
        if (CurrentCustomisations.contains(Customisation.ENHANCED_BAYONET)) cost++;
        return Math.max(cost, 0);
    }

    public void swapTechnologies() {
        Tech temp = this.Technology;
        this.Technology = this.Technology2;
        this.Technology2 = temp;
    }

    public boolean temptech = false;

    public void DevineStrengthSwitch() {
        if (TechnologyBackup.equals(Tech.NONE) && !Technology.equals(Tech.NONE) && !temptech) {
            TechnologyBackup = Technology;
        }
        temptech = true;
        if (Ranged) Technology = Technology.nextTechRanged();
        else Technology = Technology.nextTechMelee();
        if (Technology.equals(TechnologyBackup)) {
            temptech = false;
        }
    }

    public void removeTempTech() {
        if (temptech) {
            Technology = TechnologyBackup;
            TechnologyBackup = Tech.NONE;
        }
        temptech = false;
    }


    public int getHands() {
        if (Hands.equals(Hand.ONE_HANDED)) return 1;
        if (Hands.equals(Hand.TWO_HANDED)) return 2;
        else return 0;
    }

    public void Reload() {
        if (!isExplosive() || explosivereloadcheck) {
            CurrentAmmo = getAmmoCapacity();
            explosivereloadcheck = false;
        }
    }

    public Weapon getParentWeapon() {
        return ParentWeapon;
    }

    public boolean isSubWeapon() {
        return getParentWeapon() != null;
    }

    public Weapon getSubWeapon() {
        return subWeapon;
    }

    public boolean hasSubWeapon() {
        return getSubWeapon() != null;
    }

    public boolean canAttackAmmo(int cost) {
        if (isATPower()) return true;
        if (!Ranged) return true;
        return getCurrentAmmo() >= cost;
    }

    public int getAmmoCapacity() {
        int ammo = AmmoCapacity;
        if (isExtraAmmo()) {
            ammo += ammo >= 5 ? 2 : 1;
        }
        if (isExplosive()) ammo = 1;
        return ammo;
    }

    public void resetPotential() {
        PotentialProperties = new ArrayList<>();
        AreaPotential = -1;
        PenetrationPotential = 0;
        DefensivePotential = 0;
    }

    public int getCurrentAmmo() {
        return CurrentAmmo;
    }

    public void DecreaseAmmo(int cost) {
        CurrentAmmo -= cost;
    }

    public boolean hasAmmo() {
        return getCurrentAmmo() > 0;
    }


    public void applyTechToSubWeapon(Tech tech) {
        subWeapon.Technology = tech;
    }

    public boolean getN2orMaserUsed() {
        return N2OrMaserUsed;
    }

    public void switchN2OrMaser() {
        N2OrMaserUsed = !N2OrMaserUsed;
    }

    public int getArea() {
        if (getTrueArea() > -1 && getTrueLine() && lineswitch) return -1;
        return getTrueArea();
    }

    public int getTrueArea() {
        int area = Math.max(Area, AreaPotential);
        if (isN2Shell() && area == -1) area++;
        if (isN2Shell() && getN2orMaserUsed()) area++;
        return area;
    }

    public boolean getLine() {
        if (getTrueArea() > -1 && getTrueLine() && !lineswitch) return false;
        return getTrueLine();
    }

    public boolean getTrueLine() {
        boolean l = Line;
        if (isMaser() && getN2orMaserUsed()) l = true;
        return l;
    }

    public int getPenetration() {
        int penetration = Penetration + PenetrationPotential;
        if (isPositron() || isProgressive()) {
            penetration += 2;
        }
        if (isAntiArmor()) penetration++;
        if (isPolythermic() || isMaser())
            penetration++;
        if (isMaser() && N2OrMaserUsed) penetration++;

        return penetration;
    }

    public int getPower() {
        int power = Power;
        if (Math.max(Area, AreaPotential) > -1 && isN2Shell()) power++;
        if (isOverheating() && OverHeatModifier()) power = Power + 1;
        return power;
    }

    public boolean OverHeatModifier() {
        return DiceNumber * DiceStrength >= 7;
    }

    public int getDiceNumber() {
        int DN = DiceNumber;
        if (isOverheating()) DN = OverHeatModifier() ? 4 : 3;
        return DN;
    }

    public int getDiceStrength() {
        int DS = DiceStrength;
        if (isOverheating()) DS = 3;
        return DS;
    }

    public int getDefensive() {
        int defensive = Math.max(Defensive, DefensivePotential);
        if (isBalanced()) defensive = Math.max(Defensive, 10);
        return defensive;
    }

    public boolean isOverheating() {
        return isOverheating;
    }

    public void Overheat(boolean overheat) {
        isOverheating = overheat;
    }

    public boolean ATPower;

    public boolean isATPower() {
        return ATPower;
    }

    public void setATPower(boolean at) {
        ATPower = at;
    }

    public boolean isConventional() {
        return Technology == Tech.NONE;
    }

    public boolean isChain() {
        return Technology == Tech.CHAIN;
    }

    public boolean isProgressive() {
        return Technology == Tech.PROGRESSIVE;
    }

    public boolean isPolythermic() {
        return Technology == Tech.POLYTHERMIC;
    }

    public boolean isSuperconductive() {
        return Technology == Tech.SUPERCONDUCTIVE;
    }

    public boolean isGauss() {
        return Technology == Tech.GAUSS;
    }

    public boolean isN2Shell() {
        return Technology == Tech.N2SHELL;
    }

    public boolean isMaser() {
        return Technology == Tech.MASER;
    }

    public boolean isPositron() {
        return Technology == Tech.POSITRON;
    }

    public boolean isAntiArmor() {
        return CurrentCustomisations.contains(Customisation.ANTI_ARMOR);
    }

    public boolean isBalanced() {
        return CurrentCustomisations.contains(Customisation.BALANCED);
    }

    public boolean isDoubleEdged() {
        return CurrentCustomisations.contains(Customisation.DOUBLE_EDGED);
    }

    public boolean isExplosive() {
        return CurrentCustomisations.contains(Customisation.EXPLOSIVE);
    }

    public boolean isExtraAmmo() {
        return CurrentCustomisations.contains(Customisation.EXTRA_AMMO);
    }

    public void makeReinforced() {
        if (true) return;
        CurrentCustomisations.add(Customisation.REINFORCED);
    }

    public void removeReinforced() {
        CurrentCustomisations.remove(Customisation.REINFORCED);
    }

    public void makeAntiArmor() {
        CurrentCustomisations.add(Customisation.ANTI_ARMOR);
    }

    public void removeAntiArmor() {
        CurrentCustomisations.remove(Customisation.ANTI_ARMOR);
    }

    public void makeBalanced() {
        if (Ranged) return;
        CurrentCustomisations.add(Customisation.BALANCED);
    }

    public void removeBalanced() {
        CurrentCustomisations.remove(Customisation.BALANCED);
    }

    public void makeDoubleEdged() {
        if (Ranged) return;
        CurrentCustomisations.add(Customisation.DOUBLE_EDGED);
    }

    public void removeDoubleEdged() {
        CurrentCustomisations.remove(Customisation.DOUBLE_EDGED);
    }

    public void makeExplosive() {
        if (Ranged) return;
        CurrentCustomisations.add(Customisation.EXPLOSIVE);
    }

    public void removeExplosive() {
        CurrentCustomisations.remove(Customisation.EXPLOSIVE);
    }

    public void makeExtraAmmo() {
        if (!Ranged) return;
        CurrentCustomisations.add(Customisation.EXTRA_AMMO);
    }

    public void removeExtraAmmo() {
        CurrentCustomisations.remove(Customisation.EXTRA_AMMO);
    }

    public void makeCustomisationThrowing() {
        if (Ranged) return;
        CurrentCustomisations.add(Customisation.THROWING);
    }

    public void removeCustomisationThrowing() {
        CurrentCustomisations.remove(Customisation.THROWING);
    }

    public void makeBayonet() {
        if (!Ranged) return;
        CurrentCustomisations.add(Customisation.BAYONET);
    }

    public void removeBayonet() {
        CurrentCustomisations.remove(Customisation.BAYONET);
    }

    public void makeEnhancedBayonet() {
        if (!Ranged) return;
        CurrentCustomisations.add(Customisation.ENHANCED_BAYONET);
    }

    public void removeEnhancedBayonet() {
        CurrentCustomisations.remove(Customisation.ENHANCED_BAYONET);
    }

    public void makeTelescopicSight() {
        if (!Ranged) return;
        CurrentCustomisations.add(Customisation.TELESCOPIC_SIGHT);
    }

    public void removeTelescopicSight() {
        CurrentCustomisations.remove(Customisation.TELESCOPIC_SIGHT);
    }

    public void makeAutoLoader() {
        if (!Ranged) return;
        CurrentCustomisations.add(Customisation.AUTO_LOADER);
    }

    public void removeAutoLoader() {
        CurrentCustomisations.remove(Customisation.AUTO_LOADER);
    }

    public boolean isReinforced() {
        return CurrentCustomisations.contains(Customisation.REINFORCED);
    }

    public boolean isThrowingCustomisation() {
        return CurrentCustomisations.contains(Customisation.THROWING);
    }

    public boolean isBayonet() {
        return CurrentCustomisations.contains(Customisation.BAYONET);
    }

    public boolean isEnhancedBayonet() {
        return CurrentCustomisations.contains(Customisation.ENHANCED_BAYONET);
    }

    public boolean isTelescopicSight() {
        return CurrentCustomisations.contains(Customisation.TELESCOPIC_SIGHT);
    }

    public boolean isAutoLoader() {
        return CurrentCustomisations.contains(Customisation.AUTO_LOADER);
    }


    public boolean isGrapple() {
        return CurrentProperties.contains(Property.GRAPPLE) || PotentialProperties.contains(Property.GRAPPLE);
    }

    public boolean isIntrinsic() {
        return CurrentProperties.contains(Property.INTRINSIC) || PotentialProperties.contains(Property.INTRINSIC);
    }

    public boolean isPrecise() {
        return CurrentProperties.contains(Property.PRECISE) || PotentialProperties.contains(Property.PRECISE);
    }

    public boolean isProven() {
        return CurrentProperties.contains(Property.PROVEN) || PotentialProperties.contains(Property.PROVEN);
    }

    public boolean isReach() {
        return CurrentProperties.contains(Property.REACH) || PotentialProperties.contains(Property.REACH);
    }

    public boolean isCQB() {
        return CurrentProperties.contains(Property.CQB) || PotentialProperties.contains(Property.CQB);
    }

    public boolean isSwift() {
        return CurrentProperties.contains(Property.SWIFT) || PotentialProperties.contains(Property.SWIFT);
    }

    public boolean isSmall() {
        return CurrentProperties.contains(Property.SMALL) || PotentialProperties.contains(Property.SMALL);
    }

    public boolean isSpray() {
        return CurrentProperties.contains(Property.SPRAY) || PotentialProperties.contains(Property.SPRAY);
    }

    public boolean isThrowingProperty() {
        return CurrentProperties.contains(Property.THROWING) || PotentialProperties.contains(Property.THROWING);
    }

    public boolean isActuallyGrapple() {
        return CurrentProperties.contains(Property.GRAPPLE);
    }

    public boolean isActuallyIntrinsic() {
        return CurrentProperties.contains(Property.INTRINSIC);
    }

    public boolean isActuallyPrecise() {
        return CurrentProperties.contains(Property.PRECISE);
    }

    public boolean isActuallyProven() {
        return CurrentProperties.contains(Property.PROVEN);
    }

    public boolean isActuallyReach() {
        return CurrentProperties.contains(Property.REACH);
    }

    public boolean isActuallyCQB() {
        return CurrentProperties.contains(Property.CQB);
    }

    public boolean isActuallySwift() {
        return CurrentProperties.contains(Property.SWIFT);
    }

    public boolean isActuallySmall() {
        return CurrentProperties.contains(Property.SMALL);
    }

    public boolean isActuallySpray() {
        return CurrentProperties.contains(Property.SPRAY);
    }

    public boolean isActuallyThrowing() {
        return CurrentProperties.contains(Property.THROWING);
    }

    public boolean isActuallyArmorPiercing() {
        return CurrentProperties.contains(Property.ARMORPIERCING);
    }

    public void makePotentialGrapple() {
        PotentialProperties.add(Property.GRAPPLE);
    }

    public void removePotentialGrapple() {
        PotentialProperties.remove(Property.GRAPPLE);
    }

    public void makePotentialIntrinsic() {
        PotentialProperties.add(Property.INTRINSIC);
    }

    public void removePotentialIntrinsic() {
        PotentialProperties.remove(Property.INTRINSIC);
    }

    public void makePotentialPrecise() {
        PotentialProperties.add(Property.PRECISE);
    }

    public void removePotentialPrecise() {
        PotentialProperties.remove(Property.PRECISE);
    }

    public void makePotentialProven() {
        PotentialProperties.add(Property.PROVEN);
    }

    public void removePotentialProven() {
        PotentialProperties.remove(Property.PROVEN);
    }

    public void makePotentialReach() {
        PotentialProperties.add(Property.REACH);
    }

    public void removePotentialReach() {
        PotentialProperties.remove(Property.REACH);
    }

    public void makePotentialCQB() {
        PotentialProperties.add(Property.CQB);
    }

    public void removePotentialCQB() {
        PotentialProperties.remove(Property.CQB);
    }

    public void makePotentialSwift() {
        PotentialProperties.add(Property.SWIFT);
    }

    public void removePotentialSwift() {
        PotentialProperties.remove(Property.SWIFT);
    }

    public void makePotentialSmall() {
        PotentialProperties.add(Property.SMALL);
    }

    public boolean isArmorPiercing() {
        return CurrentProperties.contains(Property.ARMORPIERCING) || PotentialProperties.contains(Property.ARMORPIERCING);
    }

    public void makeArmorPiercing() {
        CurrentProperties.add(Property.ARMORPIERCING);
    }

    public void removeArmorPiercing() {
        CurrentProperties.remove(Property.ARMORPIERCING);
    }

    public void makePotentialArmorPiercing() {
        PotentialProperties.add(Property.ARMORPIERCING);
    }

    public void removePotentialArmorPiercing() {
        PotentialProperties.remove(Property.ARMORPIERCING);
    }

    public void removePotentialSmall() {
        PotentialProperties.remove(Property.SMALL);
    }

    public void makePotentialSpray() {
        PotentialProperties.add(Property.SPRAY);
    }

    public void removePotentialSpray() {
        PotentialProperties.remove(Property.SPRAY);
    }

    public void makePotentialThrowing() {
        PotentialProperties.add(Property.THROWING);
    }

    public void removePotentialThrowing() {
        PotentialProperties.remove(Property.THROWING);
    }

    public void makeGrapple() {
        CurrentProperties.add(Property.GRAPPLE);
    }

    public void removeGrapple() {
        CurrentProperties.remove(Property.GRAPPLE);
    }

    public void makeIntrinsic() {
        CurrentProperties.add(Property.INTRINSIC);
    }

    public void removeIntrinsic() {
        CurrentProperties.remove(Property.INTRINSIC);
    }

    public void makePrecise() {
        CurrentProperties.add(Property.PRECISE);
    }

    public void removePrecise() {
        CurrentProperties.remove(Property.PRECISE);
    }

    public void makeProven() {
        CurrentProperties.add(Property.PROVEN);
    }

    public void removeProven() {
        CurrentProperties.remove(Property.PROVEN);
    }

    public void makeReach() {
        CurrentProperties.add(Property.REACH);
    }

    public void removeReach() {
        CurrentProperties.remove(Property.REACH);
    }

    public void makeCQB() {
        CurrentProperties.add(Property.CQB);
    }

    public void removeCQB() {
        CurrentProperties.remove(Property.CQB);
    }

    public void makeSwift() {
        CurrentProperties.add(Property.SWIFT);
    }

    public void removeSwift() {
        CurrentProperties.remove(Property.SWIFT);
    }

    public void makeSmall() {
        CurrentProperties.add(Property.SMALL);
    }

    public void removeSmall() {
        CurrentProperties.remove(Property.SMALL);
    }

    public void makeSpray() {
        CurrentProperties.add(Property.SPRAY);
    }

    public void removeSpray() {
        CurrentProperties.remove(Property.SPRAY);
    }

    public void makeThrowing() {
        CurrentProperties.add(Property.THROWING);
    }

    public void removeThrowing() {
        CurrentProperties.remove(Property.THROWING);
    }


    public static Weapon Free() {
        Weapon Free = new Weapon("Free", Hand.NONE, 0, 0, 0, false);
        Free.makeIntrinsic();
        return Free;
    }

    public static Weapon Ammo() {
        return new Weapon("Ammo", Hand.NONE, 0, 0, 0, false);
    }

    public boolean isWeapon() {
        return !isAmmo() && !isFree();
    }

    public boolean isAmmo() {
        return Name.equals("Ammo");
    }

    public boolean isFree() {
        return Name.equals("Free");
    }

    public static Weapon getUnarmedAttack() {
        Weapon UnarmedAttack = new Weapon("UnarmedAttack", Weapon.Hand.NONE, 1, 6, 0, false);
        UnarmedAttack.makeIntrinsic();
        UnarmedAttack.makeGrapple();
        UnarmedAttack.makeSwift();
        return UnarmedAttack;
    }

    public static Weapon getChazaquielRangedAttack() {
        Weapon AngelRanged = new Weapon("AngelRanged", Weapon.Hand.NONE, 2, 6, 0, true, 4 ,8); // 2, 5
        AngelRanged.AmmoCapacity = 100;
        AngelRanged.Reload();
        AngelRanged.makeIntrinsic();
        return AngelRanged;
    }
    public static Weapon getAngelRangedAttack() {
        Weapon AngelRanged = new Weapon("AngelRanged", Weapon.Hand.NONE, 2, 6, 0, true, 2 ,5);
        AngelRanged.AmmoCapacity = 100;
        AngelRanged.Reload();
        AngelRanged.makeIntrinsic();
        return AngelRanged;
    }
    public static Weapon getAngelMeleeAttack() {
        Weapon AngelMelee = new Weapon("AngelMelee", Weapon.Hand.NONE, 2, 6, 0, false);
        AngelMelee.makeIntrinsic();
        return AngelMelee;
    }

}