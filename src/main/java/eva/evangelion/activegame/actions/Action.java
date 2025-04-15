package eva.evangelion.activegame.actions;

import java.io.Serializable;

public class Action implements Serializable {
    public final String Actor;

    public Action(String actor){
        Actor = actor;
    }

}
