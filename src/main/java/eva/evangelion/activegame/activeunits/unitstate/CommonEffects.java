package eva.evangelion.activegame.activeunits.unitstate;

import eva.evangelion.activegame.activeunits.Weapon;

public final class CommonEffects {


    public static StateEffect DamagePenaltyWeaponUse(int damagepen, Weapon weapon){
        StateEffect DPW = new StateEffect("Penalty_Damage"+weapon.Name+damagepen);
        DPW.Stackable = true;
        DPW.AttackStrengthDelta = damagepen;
        DPW.RangedStrengthDelta = damagepen;
        DPW.WeaponSpecific = weapon;
        DPW.Condition = StateEffect.ExpirationCondition.WEAPON_USE;
        return DPW;
    }


    public static StateEffect NextGuardPenalty(int penalty) {
    StateEffect Penalty = new StateEffect("Penalty_Guard"+penalty);
    Penalty.ReflexesDelta = penalty;
    Penalty.Condition = StateEffect.ExpirationCondition.GUARD;
    return Penalty;
    }

    public static StateEffect Cover() {
        StateEffect Penalty = new StateEffect("Cover");
        Penalty.ArmorDelta = 1;
        Penalty.DeleteOnMovement = true;
        Penalty.Condition = StateEffect.ExpirationCondition.START;
        return Penalty;
    }
    public static StateEffect LayeredField() {
        StateEffect Penalty = new StateEffect("LayeredField");
        Penalty.ArmorDelta = 3;
        Penalty.Condition = StateEffect.ExpirationCondition.POTENTIAL;
        return Penalty;
    }

    public static StateEffect NextAttackPenalty(int penalty) {
        StateEffect Penalty = new StateEffect("Penalty_Attack"+penalty);
        Penalty.AccuracyDelta = penalty;
        Penalty.Condition = StateEffect.ExpirationCondition.ATTACK;
        return Penalty;
    }
    public static StateEffect NextAttackPenaltyStackable(int penalty) {
        StateEffect Penalty = new StateEffect("Penalty_Attack_Stack"+penalty);
        Penalty.AccuracyDelta = penalty;
        Penalty.Stackable = true;
        Penalty.Condition = StateEffect.ExpirationCondition.ATTACK;
        return Penalty;
    }

    public static StateEffect AimEffect() {
        StateEffect Penalty = new StateEffect("Aim");
        Penalty.AccuracyDelta = 10;
        Penalty.DeleteOnMovement = true;
        Penalty.Condition = StateEffect.ExpirationCondition.ATTACK;
        return Penalty;
    }

    public static StateEffect DefendEffect() {
        StateEffect Penalty = new StateEffect("Defend");
        Penalty.ReflexesDelta = 10;
        Penalty.Condition = StateEffect.ExpirationCondition.START;
        return Penalty;
    }
    public static StateEffect RunBonus() {
        StateEffect Penalty = new StateEffect("RunBonus");
        Penalty.ReflexesDelta = 15;
        Penalty.Condition = StateEffect.ExpirationCondition.START;
        return Penalty;
    }
    public static StateEffect RunDefend(int x) {
        StateEffect Penalty = new StateEffect("RunDefend");
        Penalty.ReflexesDelta = x;
        Penalty.Condition = StateEffect.ExpirationCondition.START;
        return Penalty;
    }

    public static StateEffect DamagePenaltyRound(int damagepen){
        StateEffect DPW = new StateEffect("Penalty_Damage"+damagepen);
        DPW.Stackable = true;
        DPW.Expiration = 2;
        DPW.RangedStrengthDelta = damagepen;
        DPW.AttackStrengthDelta = damagepen;
        DPW.Condition = StateEffect.ExpirationCondition.ROUND;
        return DPW;
    }

    public static StateEffect Prone(){
        StateEffect prone = new StateEffect("Prone");
        prone.Condition = StateEffect.ExpirationCondition.STAND;
        prone.Description = "You are on the ground. You move at half your Speed and take a -20 penalty to Reflexes, but Ranged Attacks " +
                "are at a -20 penalty to hit you.";
        prone.ReflexesDelta = -20;
        prone.SpeedChange = 1;
        return prone;
    }
    public static StateEffect Immobilized(){
        StateEffect imob = new StateEffect("Immobilized");
        imob.Condition = StateEffect.ExpirationCondition.NONE;
        imob.Description = "You cannot use any Movement Action and suffer a -20 Penalty to Reflexes.";
        imob.ReflexesDelta = -20;
        imob.SpeedChange = 2;
        return imob;
    }

    public static StateEffect Bruised(){
        StateEffect bruised = new StateEffect("Bruised");
        bruised.ReflexesDelta = -10;
        bruised.AccuracyDelta = -10;
        return bruised;
    }
    public static StateEffect Wounded(){
        StateEffect wounded = new StateEffect("Wounded");
        wounded.ReflexesDelta = -15;
        wounded.AccuracyDelta = -15;
        return wounded;
    }

    public static StateEffect RemoteMedical(){
        StateEffect RM = new StateEffect("Medical0");
        return RM;
    }


    public static StateEffect LimitCut(){
        StateEffect LimitCut = new StateEffect("Limit Cut");
        LimitCut.ReflexesDelta = +20;
        LimitCut.Condition = StateEffect.ExpirationCondition.GUARD;
        return LimitCut;

    }

    public static StateEffect RemoteMedicalBruised(){
        StateEffect RM5 = new StateEffect("Medical5");
        RM5.ReflexesDelta = +5;
        RM5.AccuracyDelta = +5;
        return RM5;
    }

    public static StateEffect RemoteMedicalWounded(){
        StateEffect RM3 = new StateEffect("Medical8");
        RM3.ReflexesDelta = +8;
        RM3.AccuracyDelta = +8;
        return RM3;
    }


    public static StateEffect EWMinorHead(){
        StateEffect wound = new StateEffect("Minor Head Wound");
        wound.Description = "The Eva’s optics burst open in a spray of blood and viscera. You cannot use the Aim Action.";
        wound.ProhibitedActions.add("Aim");
        wound.Condition = StateEffect.ExpirationCondition.NONE;


        return wound;
    }


    public static StateEffect NoEffect(){
        StateEffect wound = new StateEffect("No Effect");
        wound.Description = "Nothing Happens";
        wound.Condition = StateEffect.ExpirationCondition.NONE;
        return wound;
    }

    public static StateEffect EWSevereHead(){
        StateEffect wound = new StateEffect("Severe Head Wound");
        wound.Description = "The Eva’s cranium is breached, scrambling the nerve feedback. You may no longer use AT Powers (you may still use Spread Patterns).";
        wound.ProhibitedActions.add("ATPower");
        wound.Condition = StateEffect.ExpirationCondition.NONE;


        return wound;
    }

    public static StateEffect EWDestroyedHead(){
        StateEffect wound = new StateEffect("Destroyed Head");
        wound.Description = "The Eva’s head is pulverized and you become disoriented and nauseous. You can no longer spend ATP.";
        wound.ProhibitedActions.add("ATPExpend");
        wound.Condition = StateEffect.ExpirationCondition.NONE;
        return wound;
    }

    public static StateEffect EWMinorBody(){
        StateEffect wound = new StateEffect("Minor Body Wound");
        wound.Description = "Forty layers of plating are shredded or melted by the attack. -2 Armor.";
        wound.ArmorDelta = -2;
        wound.Condition = StateEffect.ExpirationCondition.NONE;
        return wound;
    }

    public static StateEffect EWSevereBody(){
        StateEffect wound = new StateEffect("Severe Body Wound");
        wound.Description = """
                            The safety systems on the Entry Plug fail, causing the plug to shudder
                            violently. You cannot Eject this battle and become Bruised.
                            """;
        wound.ProhibitedActions.add("Eject");
        wound.AdditionalEffects.add(Bruised());
        wound.Condition = StateEffect.ExpirationCondition.NONE;
        return wound;
    }

    public static StateEffect EWDestroyedBody(){
        StateEffect wound = new StateEffect("Destroyed Body");
        wound.Description =
                 """
                 Armor and tissue is carved away in a bloody eruption of gore,
                 causing the Eva to hemorrhage continuously.
                 -2 to your Maximum Toughness and you are Bruised.
                 """;
        wound.MaxToughnessDelta = -2;
        wound.AdditionalEffects.add(Bruised());
        wound.Condition = StateEffect.ExpirationCondition.NONE;
        return wound;
    }

    // Arms Wounds
    public static StateEffect EWMinorArmsR(){
        StateEffect wound = new StateEffect("Minor Right Arm Wound");
        wound.Description = """
                Dozens of tendons and muscles are severed all at once
                and the Eva’s arm goes limp. You drop any
                weapon you are holding with this arm and it can no longer be used to
                wield 2-handed weapons or Grab.""";
        wound.ProhibitedActions.add("GrabR");
        wound.ProhibitedActions.add("TwoHandR");
        wound.drop_right = true;
        wound.Condition = StateEffect.ExpirationCondition.NONE;
        return wound;
    }

    public static StateEffect EWSevereArmsR(){
        StateEffect wound = new StateEffect("Severe Right Arm Wound");
        wound.Description = "A loud snap can be heard for kilometers around as the Eva’s arm is rendered useless. " +
                "You drop any weapon you are holding with that Arm and can no longer use it to hold items or make Attack Actions.";
        wound.ProhibitedActions.add("AttackR");
        wound.ProhibitedActions.add("ItemR");
        wound.drop_right = true;
        wound.Condition = StateEffect.ExpirationCondition.NONE;
        return wound;
    }

    public static StateEffect EWDestroyedArmsR(){
        StateEffect wound = new StateEffect("Destroyed Right Arm");
        wound.Description = "With a final spurt of viscera, the arm is torn free from the socket and takes any support structures with it. " +
                "The arm, the weapon you are carrying in that arm, and any Wings on this location are all destroyed and can no longer be used.";
        wound.ProhibitedActions.add("AttackR");
        wound.ProhibitedActions.add("ItemR");
        wound.ProhibitedActions.add("GrabR");
        wound.destroyed_right = true;
        wound.ProhibitedActions.add("WingR");
        wound.Condition = StateEffect.ExpirationCondition.NONE;
        return wound;
    }

    public static StateEffect EWMinorArmsL(){
        StateEffect wound = new StateEffect("Minor Left Arm Wound");
        wound.Description = "Dozens of tendons and muscles are severed all at once and the Eva’s arm goes limp. " +
                "You drop any weapon you are holding with this arm and it can no longer be used to wield 2-handed weapons or Grab.";
        wound.ProhibitedActions.add("GrabL");
        wound.ProhibitedActions.add("TwoHandL");
        wound.drop_left = true;
        wound.Condition = StateEffect.ExpirationCondition.NONE;
        return wound;
    }

    public static StateEffect EWSevereArmsL(){
        StateEffect wound = new StateEffect("Severe Left Arm Wound");
        wound.Description = "A loud snap can be heard for kilometers around as the Eva’s arm is rendered useless. " +
                "You drop any weapon you are holding with that Arm and can no longer use it to hold items or make Attack Actions.";
        wound.ProhibitedActions.add("AttackL");
        wound.ProhibitedActions.add("ItemL");
        wound.drop_left = true;
        wound.Condition = StateEffect.ExpirationCondition.NONE;
        return wound;
    }

    public static StateEffect EWDestroyedArmsL(){
        StateEffect wound = new StateEffect("Destroyed Left Arm");
        wound.Description = "With a final spurt of viscera, the arm is torn free from the socket and takes any support structures with it. " +
                "The arm, the weapon you are carrying in that arm, and any Wings on this location are all destroyed and can no longer be used.";
        wound.ProhibitedActions.add("AttackL");
        wound.ProhibitedActions.add("ItemL");
        wound.ProhibitedActions.add("GrabL");
        wound.ProhibitedActions.add("WingL");
        wound.destroyed_left = true;
        wound.Condition = StateEffect.ExpirationCondition.NONE;
        return wound;
    }


    // Legs Wounds
    public static StateEffect EWMinorLegs(){
        StateEffect wound = new StateEffect("Minor Leg Wound");
        wound.Description = "The Eva’s ankles and feet are pulverized, only held together by the armor plating. -10 Reflexes and you cannot use the Defend Action.";
        wound.ReflexesDelta = -10;
        wound.ProhibitedActions.add("Defend");
        wound.Condition = StateEffect.ExpirationCondition.NONE;
        return wound;
    }

    public static StateEffect EWSevereLegs(){
        StateEffect wound = new StateEffect("Severe Leg Wound");
        wound.Description = "The muscle is burned and blown away, exposing bone. You are knocked Prone and cannot Guard for the rest of the Battle.";
        wound.ProhibitedActions.add("Guard");
        wound.AdditionalEffects.add(Prone());
        wound.Condition = StateEffect.ExpirationCondition.NONE;
        return wound;
    }

    public static StateEffect EWDestroyedLegs(){
        StateEffect wound = new StateEffect("Destroyed Legs");
        wound.Description = "At least one leg is mangled beyond use, perhaps even severed completely. You are knocked Prone and cannot Stand.";
        wound.ProhibitedActions.add("Stand");
        wound.AdditionalEffects.add(Prone());
        wound.Condition = StateEffect.ExpirationCondition.NONE;
        return wound;
    }

}

