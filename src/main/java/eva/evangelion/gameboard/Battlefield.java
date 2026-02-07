package eva.evangelion.gameboard;

import javafx.scene.layout.GridPane;
import kotlin.Triple;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Battlefield implements Serializable {

    public int sizeX;
    public int sizeY;
    public List<Triple<Integer, Integer, SectorType>> SpecialTiles = new ArrayList<>();
    public Battlefield(int Xsize, int Ysize){
        sizeX = Xsize;
        sizeY = Ysize;
    }
}
