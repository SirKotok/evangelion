package eva.evangelion.activegame;

import eva.evangelion.activegame.actions.Action;
import eva.evangelion.activegame.actions.Attack;
import eva.evangelion.activegame.activeunits.ChazaqielSummon;
import eva.evangelion.activegame.activeunits.Weapon;
import eva.evangelion.activegame.activeunits.unitstate.AngelState;
import eva.evangelion.activegame.activeunits.unitstate.ChazaqielSummonState;
import eva.evangelion.activegame.activeunits.unitstate.EvangelionState;
import eva.evangelion.gameboard.Battlefield;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Gamestate implements Serializable {

    public List<EvangelionState> EvaList;
    public List<AngelState> AngelList;
    public List<ChazaqielSummonState> SummonList;
    public List<Weapon> Weapons;
    public String Phase;
    public int Round;
    public int NervRespources = 8;
    public int Fog = -1;
    public String Player;
    public Action Action;
    public Action BackupAction;
    public String NextPlayer;
    public Battlefield Field;

    public List<String> GameQueueList;
    public List<Attack> AttackQueueList;

    public Gamestate(){
       EvaList = new ArrayList<>();
       AngelList = new ArrayList<>();
       GameQueueList = new ArrayList<>();
       AttackQueueList = new ArrayList<>();
       Round = 1;
       Phase = "";
       Player = "GM";
       NextPlayer = "GM";
       Field = new Battlefield(30, 30);
    }



}
