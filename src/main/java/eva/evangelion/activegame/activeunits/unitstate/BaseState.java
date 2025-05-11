package eva.evangelion.activegame.activeunits.unitstate;

import eva.evangelion.activegame.activeunits.Weapon;

import java.io.Serializable;
import java.util.*;

public class BaseState  implements Serializable {
    public int x;
    public int y;
    public int SpeedChange;
    public int PenetrationDelta = 0;
    public int woundlevel = 0;
    public List<Weapon> Weapons = new ArrayList<>();
    public int Stamina = 2;
    public int MaxRangeDelta = 0;

    public boolean TurnOrIntervalDone;
    public int ATP = 1;
    public int toughness = -1;
    public int AccuracyDelta = 0;
    public int ArmorDelta = 0;
    public int ReflexesDelta = 0;
    public int Requisition = -1;
    public int SpeedDelta = 0;

    public int Fate = 5;
    public int Doom = 5;
    public int MaxToughnessDelta = 0;
    public boolean UsedGuard = false;
    public boolean UsedRedundantOrgans = false;
    public boolean UsedAttackAction = false;
    public boolean UsedAngelicCore = false;
    public boolean UsedTacticalAction = false;
    public final String PlayerName;
    public final String TypeString;
    public List<StateEffect> StateEffects = new ArrayList<>();
    public List<String> ProhibitedActions = new ArrayList<>();

    public BaseState(String playerName, String typeString) {
        PlayerName = playerName;
        TypeString = typeString;
    }

    public void ApplyStateEffects(Weapon weapon){
    }
    public void CalculateDelta(Weapon weapon){
    }

    public void RoundTickEffects(){
        ProcessStateEffects();
        List<StateEffect> TickedEffects = new ArrayList<>();
        for (StateEffect Effect : StateEffects) {
            if (Effect.Condition.equals(StateEffect.ExpirationCondition.ROUND)) {
                Effect.Expiration--;
            }
            if (Effect.Expiration > 0) TickedEffects.add(Effect);
        }
        StateEffects = TickedEffects;
    }



    public void RemovePotentialEffects(){
        ProcessStateEffects();
        List<StateEffect> TickedEffects = new ArrayList<>();
        for (StateEffect Effect : StateEffects) {
            if (Effect.Condition.equals(StateEffect.ExpirationCondition.POTENTIAL)) {
                Effect.Expiration = 0;
            }
            if (Effect.Expiration > 0) TickedEffects.add(Effect);
        }
        StateEffects = TickedEffects;
    }

    public void RemoveOnMoveEffects(){
        ProcessStateEffects();
        List<StateEffect> TickedEffects = new ArrayList<>();
        for (StateEffect Effect : StateEffects) {
            if (!Effect.DeleteOnMovement) TickedEffects.add(Effect);
        }
        StateEffects = TickedEffects;
    }


    public void WeaponSpecificTickEffect(Weapon weapon){
        ProcessStateEffects();
        List<StateEffect> TickedEffects = new ArrayList<>();
        for (StateEffect Effect : StateEffects) {
            if (Effect.Condition.equals(StateEffect.ExpirationCondition.WEAPON_USE) || Effect.Condition.equals(StateEffect.ExpirationCondition.POTENTIAL)) {
                if (Effect.WeaponSpecific == weapon) Effect.Expiration--;
            }
            if (Effect.Expiration > 0) TickedEffects.add(Effect);
        }
        StateEffects = TickedEffects;
    }

    public void AttackTickEffect(){
        ProcessStateEffects();
        List<StateEffect> TickedEffects = new ArrayList<>();
        for (StateEffect Effect : StateEffects) {
            if (Effect.Condition.equals(StateEffect.ExpirationCondition.ATTACK)) {
               Effect.Expiration--;
            }
            if (Effect.Expiration > 0) TickedEffects.add(Effect);
        }
        StateEffects = TickedEffects;
    }
    public void GuardTickEffect(){
        ProcessStateEffects();
        List<StateEffect> TickedEffects = new ArrayList<>();
        for (StateEffect Effect : StateEffects) {
            if (Effect.Condition.equals(StateEffect.ExpirationCondition.GUARD)) {
                Effect.Expiration--;
            }
            if (Effect.Expiration > 0) TickedEffects.add(Effect);
        }
        StateEffects = TickedEffects;
    }


    public void ConditionTickEffect(StateEffect.ExpirationCondition condition){
        ProcessStateEffects();
        List<StateEffect> TickedEffects = new ArrayList<>();
        for (StateEffect Effect : StateEffects) {
            if (Effect.Condition.equals(condition)) {
                Effect.Expiration--;
            }
            if (Effect.Expiration > 0) TickedEffects.add(Effect);
        }
        StateEffects = TickedEffects;
    }


    public void ProcessStateEffects() {
        // Step 1: Unpack all nested AdditionalEffects
        Queue<StateEffect> processingQueue = new LinkedList<>(this.StateEffects);
        List<StateEffect> unpackedEffects = new ArrayList<>();

        while (!processingQueue.isEmpty()) {
            StateEffect effect = processingQueue.poll();
            unpackedEffects.add(effect);

            // Add all additional effects to processing queue and clear them
            while (!effect.AdditionalEffects.isEmpty()) {
                StateEffect additional = effect.AdditionalEffects.remove(0);
                processingQueue.add(additional);
            }
        }

        // Step 2: Process duplicates and Bruised/Wounded conversion
        Map<String, StateEffect> effectMap = new HashMap<>();
        int bruisedCount = 0;
        boolean medical = false;
        int reflexesbonus = 0;

        // First pass to count bruises and collect other unique effects
        for (StateEffect effect : unpackedEffects) {
            if ("Medical0".equals(effect.Name) || "Medical5".equals(effect.Name) || "Medical8".equals(effect.Name)) {
                medical = true;
            }
            else if ("Bruised".equals(effect.Name)) {
                bruisedCount++;
            } else if ("RunBonus".equals((effect.Name)) || "Defend".equals((effect.Name)) || "RunDefend".equals((effect.Name))) {
               reflexesbonus = Math.max(reflexesbonus, effect.ReflexesDelta);
            }
            else {
                if (effect.Stackable) effectMap.put(effect.Name, effect);
                else effectMap.putIfAbsent(effect.Name, effect);
            }
        }

        // Handle Bruised -> Wounded conversion
        if (bruisedCount == 1 && !effectMap.containsKey("Wounded")) {
            effectMap.putIfAbsent("Bruised", CommonEffects.Bruised());
        }
        if (bruisedCount >= 2 && !effectMap.containsKey("Wounded")) {
            effectMap.put("Wounded", CommonEffects.Wounded());
        }

        // remote medical
        if (medical) {
            if (effectMap.containsKey("Bruised"))  effectMap.putIfAbsent("Medical5", CommonEffects.RemoteMedicalBruised());
            else if (effectMap.containsKey("Wounded"))  effectMap.putIfAbsent("Medical8", CommonEffects.RemoteMedicalWounded());
            else effectMap.putIfAbsent("Medical0", CommonEffects.RemoteMedical());
        }

        if (reflexesbonus > 0) {
            effectMap.putIfAbsent("RunDefend", CommonEffects.RunDefend(reflexesbonus));
        }

        // Second pass to build final list
        List<StateEffect> finalEffects = new ArrayList<>(effectMap.values());

        // Add Wounded if converted from Bruised
        if (bruisedCount >= 2 && !finalEffects.contains(CommonEffects.Wounded())) {
            finalEffects.add(CommonEffects.Wounded());
        }

        // Update state effects
        this.StateEffects = finalEffects;
    }


}
