package eva.evangelion.view;


import eva.evangelion.activegame.Gamestate;
import eva.evangelion.gameboard.Battlefield;
import eva.evangelion.units.Types.EvangelionType;
import eva.evangelion.util.EvaSaveUtil;
import eva.evangelion.view.evainterface.EvaButton;
import eva.evangelion.view.evainterface.EvaMenuSubScene;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainMenu {

    private AnchorPane mainPane;
    private Scene mainScene;
    private Stage mainStage;
    private static final int WIDTH = 700;
    private static final int HEIGHT= 700;
    private List<EvaButton> MainMenuButtons;
    private  List<EvaButton> WeaponsMenuButtons;


    private EvaMenuSubScene UnitsSubScene;
    private EvaMenuSubScene EvangelionsSubScene;
    private EvaMenuSubScene EvangelionsListSubScene;
    private EvaMenuSubScene AngelsSubScene;
    private EvaMenuSubScene AngelsListSubScene;
    private EvaMenuSubScene OtherUnitsSubScene;
    private EvaMenuSubScene OtherUnitsListSubScene;
    private EvaMenuSubScene WeaponsSubScene;
    private EvaMenuSubScene BattlefieldsSubScene;
    private EvaMenuSubScene GameSubScene;
    private EvaMenuSubScene CustomTilesSubScene;
    private EvaMenuSubScene CustomTilesListSubScene;
    private EvaMenuSubScene ActualBattlefieldsSubScene;

    private EvaMenuSubScene amongus;

    private List<EvaMenuSubScene> sceneToHide;

    public MainMenu() throws IOException {
        MainMenuButtons = new ArrayList<>();
        sceneToHide = new ArrayList<>();

        mainPane = new AnchorPane();
        mainScene = new Scene(mainPane, WIDTH, HEIGHT );
        mainStage = new Stage();
        mainStage.setScene(mainScene);

        createSubScenes();
        createButtons();


        createBackground();
        // createLogo();


    }


    private void createSubScenes() {
        createUnitsSubScene();
        createBattlefieldsSubScene();
        createWeaponSubScene();
        createGameSubScene();


        mainPane.getChildren().add(WeaponsSubScene);


    }


    private void createUnitsSubScene() {
        List<EvaButton> unitsMenuButtons = new ArrayList<>();
        List<EvaButton> evangelionsMenuButtons = new ArrayList<>();
        List<EvaButton> angelsMenuButtons = new ArrayList<>();
        List<EvaButton> otherUnitsMenuButtons = new ArrayList<>();

        UnitsSubScene = new EvaMenuSubScene(1);
        EvangelionsSubScene = new EvaMenuSubScene(2);
        AngelsSubScene = new EvaMenuSubScene(2);
        OtherUnitsSubScene = new EvaMenuSubScene(2);

        mainPane.getChildren().add(UnitsSubScene);
        mainPane.getChildren().add(EvangelionsSubScene);
        mainPane.getChildren().add(AngelsSubScene);
        mainPane.getChildren().add(OtherUnitsSubScene);

        createSceneButton("Evangelions", unitsMenuButtons, EvangelionsSubScene);
        createNaLButtonsEva(evangelionsMenuButtons, EvangelionsListSubScene);
        createSceneButton("Angels", unitsMenuButtons, AngelsSubScene);
        createNaLButtonsEva(angelsMenuButtons, AngelsListSubScene);
        createSceneButton("Other", unitsMenuButtons, OtherUnitsSubScene);
        createNaLButtonsEva(otherUnitsMenuButtons, OtherUnitsListSubScene);

        SetUpMenuList(UnitsSubScene.getPane(), unitsMenuButtons, 5, 0);
        SetUpMenuList(EvangelionsSubScene.getPane(), evangelionsMenuButtons, 5, 0);
        SetUpMenuList(AngelsSubScene.getPane(), angelsMenuButtons, 5, 0);
        SetUpMenuList(OtherUnitsSubScene.getPane(), otherUnitsMenuButtons, 5, 0);

    }

    private void createBattlefieldsSubScene() {
        List<EvaButton> battlefieldsMenuButtons = new ArrayList<>();
        List<EvaButton> customTilesMenuButtons = new ArrayList<>();
        List<EvaButton> actualBattlefieldsMenuButtons = new ArrayList<>();


        BattlefieldsSubScene = new EvaMenuSubScene(1);
        CustomTilesSubScene = new EvaMenuSubScene(2);
        ActualBattlefieldsSubScene = new EvaMenuSubScene(2);


        mainPane.getChildren().add(BattlefieldsSubScene);
        mainPane.getChildren().add(CustomTilesSubScene);
        mainPane.getChildren().add(ActualBattlefieldsSubScene);


        createSceneButton("Battlefields", battlefieldsMenuButtons, ActualBattlefieldsSubScene);
        createNaLButtonsBattlefield(actualBattlefieldsMenuButtons, ActualBattlefieldsSubScene);
        createSceneButton("CustomTiles", battlefieldsMenuButtons, CustomTilesSubScene);
        createNaLButtonsEva(customTilesMenuButtons, CustomTilesListSubScene);



        SetUpMenuList(BattlefieldsSubScene.getPane(), battlefieldsMenuButtons, 5, 0);
        SetUpMenuList(CustomTilesSubScene.getPane(), customTilesMenuButtons, 5, 0);
        SetUpMenuList(ActualBattlefieldsSubScene.getPane(), actualBattlefieldsMenuButtons, 5, 0);


    }

    private void createWeaponSubScene() {
        List<EvaButton> WeaponMenu = new ArrayList<>();


        WeaponsSubScene = new EvaMenuSubScene(1);

        createNewWeaponButton("Create New", WeaponMenu);


        SetUpMenuList(WeaponsSubScene.getPane(), WeaponMenu, 5, 0);


    }

    private void createGameSubScene() {
        List<EvaButton> gameMenuButtons = new ArrayList<>();

        GameSubScene = new EvaMenuSubScene(1);
        mainPane.getChildren().add(GameSubScene);

        TextField PlayerEmount = new TextField();
        PlayerEmount.setLayoutX(5);
        PlayerEmount.setLayoutY(5);
        PlayerEmount.setText("3");

        TextField PlayerName = new TextField();
        PlayerName.setLayoutX(5);
        PlayerName.setLayoutY(235);
        PlayerName.setText("NAME");

        GameSubScene.getPane().getChildren().add(PlayerEmount);
        GameSubScene.getPane().getChildren().add(PlayerName);

        createNewGameButton("NewGame", gameMenuButtons, PlayerEmount);
        createConnectButton("Connect", gameMenuButtons, PlayerName);



        SetUpMenuList(GameSubScene.getPane(), gameMenuButtons, 5, 40);
    }












    private void createBackground() {
        Image backgroundImage = new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("/eva/background.png")), 256, 256, false, false);
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
        mainPane.setBackground(new Background(background));
    }





    public Stage getMainStage() {
        return mainStage;
    }

    public void createButtons(){
       createSceneButton("Units", MainMenuButtons, UnitsSubScene);
       createSceneButton("Weapons", MainMenuButtons, WeaponsSubScene);
       createSceneButton("Battlefields", MainMenuButtons, BattlefieldsSubScene);
       createSceneButton("Game", MainMenuButtons, GameSubScene);



        EvaButton ExitButton = new EvaButton("Exit");



        MainMenuButtons.add(ExitButton);




        SetUpMenuList(mainPane, MainMenuButtons, 100, 100);


    }


    private void createNaLButtonsEva(List<EvaButton> menu, EvaMenuSubScene subscene) {
       createNewEvaButton("Create New", menu);
       subscene = new EvaMenuSubScene(3);
       mainPane.getChildren().add(subscene);
       createSceneButton("View Old", menu, subscene);
    }
    private void createNaLButtonsBattlefield(List<EvaButton> menu, EvaMenuSubScene subscene) {
        createNewBattlefieldButton("Create New", menu);
        subscene = new EvaMenuSubScene(3);
        mainPane.getChildren().add(subscene);
        createSceneButton("View Old", menu, subscene);
    }



    private void createSceneButton(String name, List<EvaButton> menu, EvaMenuSubScene subscene) {
        EvaButton button = new EvaButton(name);
        menu.add(button);
        button.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                showSubScene(subscene);
            }
        });
    }

    private void createNormalButton(String name, List<EvaButton> menu) {
        EvaButton button = new EvaButton(name);
        menu.add(button);
     //   button.setOnAction(new EventHandler<ActionEvent>() {

       //     @Override
        //    public void handle(ActionEvent event) {
       //         showSubScene(subscene);
     //       }
     //   });
    }

    private void createNewEvaButton(String name, List<EvaButton> menu) {
        EvaButton button = new EvaButton(name);
        menu.add(button);
           button.setOnAction(new EventHandler<ActionEvent>() {

             @Override
            public void handle(ActionEvent event) {
                 try {
                 EvangelionCreator EvaManager = new EvangelionCreator();

                     EvaManager.createNewMaker(mainStage, new EvangelionType());
                 } catch (IOException e) {
                     throw new RuntimeException(e);
                 }
             }
           });
    }


    private void createNewBattlefieldButton(String name, List<EvaButton> menu) {
        EvaButton button = new EvaButton(name);
        menu.add(button);
        button.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                try {
                BattlefieldCreator EvaManager = new BattlefieldCreator();

                    EvaManager.createNewMaker(mainStage, new Battlefield(30, 30));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void createNewWeaponButton(String name, List<EvaButton> menu) {
        EvaButton button = new EvaButton(name);
        menu.add(button);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    WeaponCreator EvaManager = new WeaponCreator();
                    EvaManager.createNewMaker(mainStage, null);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }



    private void createNewGameButton(String name, List<EvaButton> menu, TextField field) {
        EvaButton button = new EvaButton(name);
        menu.add(button);
        button.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                try {
                GameCreation EvaManager = new GameCreation();

                  //  if (Integer.getInteger(field.getText()) != null) {
                        EvaManager.createNewMaker(mainStage, Integer.parseInt(field.getText()));
                 //   }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }




    private void createConnectButton(String name, List<EvaButton> menu, TextField field) {
        EvaButton button = new EvaButton(name);
        menu.add(button);
        button.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                try {
                   GameInterface EvaManager = new GameInterface();
                   String s = EvaSaveUtil.getSaveGamePath();
                   Gamestate state = EvaSaveUtil.ReadGameState(s.replace("\\", "/")+"currentgame.dat");
                   EvaManager.createNewMaker(mainStage, state.Field, state, true, field.getText());
                } catch (IOException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }








    private void SetUpMenuList(AnchorPane pane,  List<EvaButton> list, float startx, float starty) {
        float x = startx;
        float y = starty;
        for (EvaButton button : list) {
            button.setPosition(x, y);
            pane.getChildren().add(button);
            y+=100;
        }


    }


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



}
