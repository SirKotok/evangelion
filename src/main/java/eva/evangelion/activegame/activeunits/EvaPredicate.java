package eva.evangelion.activegame.activeunits;

import java.io.Serializable;
import java.util.List;

public class EvaPredicate implements Serializable {

    public enum Condition {
        SINGLETARGET
    }

    public final List<Condition> Conditions;

    public EvaPredicate(List<Condition> codnitionList){
        Conditions = codnitionList;
    }
}
