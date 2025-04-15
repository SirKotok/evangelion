package eva.evangelion.gameboard;

import eva.evangelion.activegame.activeunits.unitstate.CommonEffects;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;


public class Sector extends StackPane {

    public int x,y;

    public SectorType type;

    public Sector(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void setType(SectorType type) {
        this.type = type;
    }
    public SectorType getType(){
        if (type == null) return SectorType.Blank;
        return this.type;
    }


}