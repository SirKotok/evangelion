package eva.evangelion.view;

import eva.evangelion.activegame.Gamestate;
import eva.evangelion.activegame.activeunits.unitstate.AngelState;
import eva.evangelion.activegame.activeunits.unitstate.EvangelionState;
import eva.evangelion.gameboard.Battlefield;
import eva.evangelion.util.EvaSaveUtil;
import eva.evangelion.view.evainterface.EvaButton;
import eva.evangelion.view.evainterface.EvaMenuSubScene;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameCreation {

    private AnchorPane creatorPane;
    private Scene creatorScene;
    private Stage creatorStage;
    private Battlefield field = new Battlefield(50, 30);

    private Stage menuStage;

    public List<String> EvaTypeList;
    public List<String> BattleFieldTypeList = new ArrayList<>();
    private String filepath = EvaSaveUtil.getFilepath();

    private List<String> PlayerList;
    private List<Integer> DoomList;
    private List<Integer> FateList;
    private List<String> PlayerChoiceList;
    public GameCreation() throws IOException {
        initializeStage();
    }
    private static final int WIDTH = 700;
    private static final int HEIGHT= 700;

    private void initializeStage() {
       creatorPane = new AnchorPane();
        creatorScene = new Scene(creatorPane, WIDTH, HEIGHT);
        creatorStage = new Stage();
        creatorStage.setScene(creatorScene);
    }







    public void createNewMaker(Stage menuStage, int Pnumber) throws IOException {
        sceneToHide = new ArrayList<>();
        PlayerList = new ArrayList<>();
        DoomList = new ArrayList<>();
        FateList = new ArrayList<>();
        PlayerChoiceList = new ArrayList<>();



        EvaTypeList = new ArrayList<>();

        File folder2 = new File(filepath+"/Evangelions");
        File[] listOfFiles2 = folder2.listFiles();
        for (int m = 0; m < listOfFiles2.length; m++) {
            if (listOfFiles2[m].isFile()) {
                EvaTypeList.add(listOfFiles2[m].getName().replace(".txt", ""));
            }
        }

        folder2 = new File(filepath+"/Battlefields");
        listOfFiles2 = folder2.listFiles();
        for (int m = 0; m < listOfFiles2.length; m++) {
            if (listOfFiles2[m].isFile()) {
                BattleFieldTypeList.add(listOfFiles2[m].getName().replace(".txt", ""));
            }
        }

        for (int i = 0; i<Pnumber; i++) {
            PlayerList.add(String.valueOf(i));
            DoomList.add(0);
            FateList.add(0);
            PlayerChoiceList.add(EvaTypeList.get(0));
        }


        List<EvaButton> PlayersButtons = new ArrayList<>();

        createNewGameButton("StartGame", PlayersButtons, Pnumber);
        EvaMenuSubScene Battlescene = createBattlefieldSubScene();
        createSceneButton("Battlefield", PlayersButtons, Battlescene, 60);

        for (int i = 0; i < Pnumber; i++) {
            createSceneButton(String.valueOf(i+1), PlayersButtons, createEvaTypesSubScene(i), 30);
        }




        SetUpMenuList(creatorPane, PlayersButtons, 40, 40);


        this.menuStage = menuStage;
        this.menuStage.hide();
        creatorStage.show();
        //   createBackground();

    }




    private void createNewGameButton(String name, List<EvaButton> menu, int Pnumber) {
        EvaButton button = new EvaButton(name);
        menu.add(button);
        button.setPrefHeight(60);
        button.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                try {
                GameInterface EvaManager = new GameInterface();

                   Gamestate state = new Gamestate();

                   for (int i = 0; i < Pnumber; i++) {
                       EvangelionState evaState = new EvangelionState(PlayerList.get(i), PlayerChoiceList.get(i));
                       evaState.Doom = DoomList.get(i);
                       evaState.Fate = FateList.get(i);
                       state.EvaList.add(evaState);
                   }

                    AngelState angel = new AngelState("Angel11");
                    state.AngelList.add(angel);

                    EvaManager.createNewMaker(creatorStage, field, state, false, "GM");
                } catch (IOException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }


    private void createSceneButton(String name, List<EvaButton> menu, EvaMenuSubScene subscene, int y) {
        EvaButton button = new EvaButton(name);
        menu.add(button);
        button.setPrefHeight(y);
        button.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                showSubScene(subscene);
            }
        });
    }
    private List<EvaMenuSubScene> sceneToHide;
    private void showSubScene(EvaMenuSubScene subScene) {
        List<EvaMenuSubScene> newlist = new ArrayList<>();
        newlist.addAll(sceneToHide);
        if (!sceneToHide.isEmpty()) {
            for (EvaMenuSubScene prevScene : sceneToHide) {
                if (prevScene.getLevel() >= subScene.getLevel()) {
                    prevScene.moveSubScene();
                    newlist.remove(prevScene);
                }
            }
        }
        newlist.add(subScene);
        subScene.moveSubScene();
        sceneToHide = newlist;
    }


    private void createChooseTypeButton(String name, List<EvaButton> menu, int PlayerNumber) {
        EvaButton button = new EvaButton(name);
        menu.add(button);
        button.setPrefHeight(30);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                PlayerChoiceList.set(PlayerNumber, name);
            }
        });
    }
    private void createChooseBattlefieldButton(String name, List<EvaButton> menu) {
        EvaButton button = new EvaButton(name);
        menu.add(button);
        button.setPrefHeight(30);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                  field = EvaSaveUtil.ReadBattlefield(filepath+"/Battlefields/"+name+".txt");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
    private void createSetNameButton(String name, TextField namefield, TextField doomfield, TextField fatefield, List<EvaButton> menu, int PlayerNumber) {
        EvaButton button = new EvaButton(name);
        menu.add(button);
        button.setPrefHeight(30);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                PlayerList.set(PlayerNumber, namefield.getText());
                DoomList.set(PlayerNumber, Integer.parseInt(doomfield.getText()));
                FateList.set(PlayerNumber, Integer.parseInt(fatefield.getText()));
            }
        });
    }


    private EvaMenuSubScene createEvaTypesSubScene(int PlayerNumber) {
        List<EvaButton> EvangelionsMenuButtons = new ArrayList<>();
        EvaMenuSubScene EvangelionsSubScene = new EvaMenuSubScene(4, 50, 0);
        creatorPane.getChildren().add(EvangelionsSubScene);

        TextField PlayerName = new TextField();
        TextField Doom = new TextField();
        TextField Fate = new TextField();
        EvangelionsSubScene.getPane().getChildren().add(PlayerName);
        EvangelionsSubScene.getPane().getChildren().add(Doom);
        EvangelionsSubScene.getPane().getChildren().add(Fate);
        PlayerName.setLayoutX(5);
        PlayerName.setLayoutY(5);
        Doom.setLayoutY(35);
        Doom.setLayoutX(5);
        Fate.setLayoutY(65);
        Fate.setLayoutX(5);
        createSetNameButton("apply name", PlayerName, Doom, Fate, EvangelionsMenuButtons, PlayerNumber);
        for (String eva : EvaTypeList) {
            createChooseTypeButton(eva, EvangelionsMenuButtons, PlayerNumber);
        }


        SetUpMenuList(EvangelionsSubScene.getPane(), EvangelionsMenuButtons, 5, 120);
        return EvangelionsSubScene;

    }
    private EvaMenuSubScene createBattlefieldSubScene() {
        List<EvaButton> Battlefieldbuttons = new ArrayList<>();
        EvaMenuSubScene BattlefieldSubScene = new EvaMenuSubScene(4, 50, 0);
        creatorPane.getChildren().add(BattlefieldSubScene);
        for (String name : BattleFieldTypeList) {
            createChooseBattlefieldButton(name, Battlefieldbuttons);
        }
        SetUpMenuList(BattlefieldSubScene.getPane(), Battlefieldbuttons, 5, 40);
        return BattlefieldSubScene;

    }





    private void SetUpMenuList(AnchorPane pane,  List<EvaButton> list, float startx, float starty) {
        float x = startx;
        float y = starty;
        for (EvaButton button : list) {
            button.setPosition(x, y);
            pane.getChildren().add(button);
            y+=button.getPrefHeight()+10;
        }
    }









}
