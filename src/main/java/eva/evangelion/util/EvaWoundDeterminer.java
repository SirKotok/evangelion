package eva.evangelion.util;

import eva.evangelion.activegame.activeunits.unitstate.CommonEffects;
import eva.evangelion.activegame.activeunits.unitstate.StateEffect;

public class EvaWoundDeterminer {
    public static class WoundResult {
        public final StateEffect newWound;
        public final StateEffect reducedWound;

        public WoundResult(StateEffect newWound, StateEffect reducedWound) {
            this.newWound = newWound;
            this.reducedWound = reducedWound;
        }
    }

    public static WoundResult determineWounds(int hitLocation, int woundLevel) {
        String bodyPart = determineBodyPart(hitLocation);
        StateEffect newWound = getWoundEffect(bodyPart, woundLevel);
        StateEffect reducedWound = woundLevel > 0 ? getWoundEffect(bodyPart, woundLevel - 1) : CommonEffects.NoEffect();
        return new WoundResult(newWound, reducedWound);
    }

    private static String determineBodyPart(int hitLocation) {
        if (hitLocation <= 20) return "Head";
        if (hitLocation <= 40) return "ArmsR";
        if (hitLocation <= 60) return "ArmsL";
        if (hitLocation <= 80) return "Body";
        return "Legs";
    }

    private static StateEffect getWoundEffect(String bodyPart, int level) {
        try {
            return switch (bodyPart) {
                case "Head" -> switch (level) {
                    case 0 -> CommonEffects.EWMinorHead();
                    case 1 -> CommonEffects.EWSevereHead();
                    case 2 -> CommonEffects.EWDestroyedHead();
                    default -> CommonEffects.NoEffect();
                };
                case "ArmsR" -> switch (level) {
                    case 0 -> CommonEffects.EWMinorArmsR();
                    case 1 -> CommonEffects.EWSevereArmsR();
                    case 2 -> CommonEffects.EWDestroyedArmsR();
                    default -> CommonEffects.NoEffect();
                };
                case "ArmsL" -> switch (level) {
                    case 0 -> CommonEffects.EWMinorArmsL();
                    case 1 -> CommonEffects.EWSevereArmsL();
                    case 2 -> CommonEffects.EWDestroyedArmsL();
                    default -> CommonEffects.NoEffect();
                };
                case "Body" -> switch (level) {
                    case 0 -> CommonEffects.EWMinorBody();
                    case 1 -> CommonEffects.EWSevereBody();
                    case 2 -> CommonEffects.EWDestroyedBody();
                    default -> CommonEffects.NoEffect();
                };
                case "Legs" -> switch (level) {
                    case 0 -> CommonEffects.EWMinorLegs();
                    case 1 -> CommonEffects.EWSevereLegs();
                    case 2 -> CommonEffects.EWDestroyedLegs();
                    default -> CommonEffects.NoEffect();
                };
                default -> CommonEffects.NoEffect();
            };
        } catch (Exception e) {
            return CommonEffects.NoEffect();
        }
    }
}