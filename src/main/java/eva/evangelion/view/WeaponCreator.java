package eva.evangelion.view;


import eva.evangelion.activegame.Gamestate;
import eva.evangelion.activegame.activeunits.Weapon;
import eva.evangelion.util.EvaSaveUtil;
import eva.evangelion.view.evainterface.DisplaySubSpace;
import eva.evangelion.view.evainterface.EvaButton;
import eva.evangelion.view.evainterface.EvaLabel;
import eva.evangelion.view.evainterface.EvaMenuSubScene;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

public class WeaponCreator {

    public List<Weapon> WeaponProfileList = new ArrayList<>();
    private String filepath = EvaSaveUtil.getFilepath();
    private String savepath = EvaSaveUtil.getSavepath()+"\\Weapons\\";
    public Weapon weapon = EvaSaveUtil.ReadWeaponProfile(filepath+"/WeaponProfiles/Knife.txt");
    public String Player;
    private AnchorPane creatorPane;
    private Scene creatorScene;
    private Stage creatorStage;
    private static final int WIDTH = 900;
    private static final int HEIGHT= 700;
    private Stage menuStage;
    private List<EvaMenuSubScene> sceneToHide;
    private List<EvaButton> MainMenuButtons;

    private DisplaySubSpace WeaponDisplay;

    EvaLabel Profile = new EvaLabel("");
    EvaLabel Attack = new EvaLabel("");
    EvaLabel Hands = new EvaLabel("");
    EvaLabel Technology = new EvaLabel("");
    EvaLabel Customisations = new EvaLabel("");
    EvaLabel Properties = new EvaLabel("");
    EvaLabel Cost = new EvaLabel("");
    EvaLabel PenetrationDefensive = new EvaLabel("");

    EvaLabel Range = new EvaLabel("");
    EvaLabel Ammo = new EvaLabel("");
    EvaLabel SubWeapon = new EvaLabel("");
    List<EvaLabel> statLables;
    TextField WeaponName;

    EvaLabel CanSave = new EvaLabel("Choose Technology");
    public WeaponCreator() throws IOException {
        initializeStage();
    }



    public void createNewMaker(Stage menuStage, String middlegame) throws IOException {
        statLables = new ArrayList<>();


        statLables.add(Profile);
        statLables.add(Attack);
        statLables.add(Hands);
        statLables.add(Range);
        statLables.add(Ammo);
        statLables.add(Technology);
        statLables.add(Cost);
        statLables.add(Customisations);
        statLables.add(Properties);
        statLables.add(PenetrationDefensive);
        statLables.add(SubWeapon);

        MainMenuButtons = new ArrayList<>();
        sceneToHide = new ArrayList<>();
     //   UpgradeList = new ArrayList<>();

        File folder = new File(filepath+"/WeaponProfiles");
        File[] listOfFiles = folder.listFiles();
        for (int m = 0; m < listOfFiles.length; m++) {
            if (listOfFiles[m].isFile()) {
                WeaponProfileList.add(EvaSaveUtil.ReadWeaponProfile(listOfFiles[m].getAbsolutePath()));
            }
        }




        this.menuStage = menuStage;
        this.menuStage.hide();
        creatorStage.show();
        creatorPane.getChildren().add(CanSave);


        createDisplaySubSpace();


        createButtons();
     //   createBackground();
        UpdateLables();

        if (middlegame != null) {
            Player = middlegame;
            EvaButton connect = createConnectButton("To Game");
            connect.setPosition(10, 10);
            creatorPane.getChildren().add(connect);
        }





    }

    private EvaButton createConnectButton(String name) {
        EvaButton button = new EvaButton(name);
        button.setPrefHeight(30);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    GameInterface EvaManager = new GameInterface();
                    String s = EvaSaveUtil.getSaveGamePath();
                    Gamestate state = EvaSaveUtil.ReadGameState(s.replace("\\", "/")+"currentgame.dat");
                    EvaManager.createNewMaker(creatorStage, state.Field, state, true, Player);
                } catch (IOException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        return button;
    }


    private void createDisplaySubSpace() {
        WeaponDisplay = new DisplaySubSpace(300, 20, 400, 400, 400);
        creatorPane.getChildren().add(WeaponDisplay);
        WeaponDisplay.moveSubScene();
        EvaLabel EvaStatsLable = new EvaLabel("Weapon Stats:");
        EvaStatsLable.setLayoutX(0);
        EvaStatsLable.setLayoutY(0);
        int i = 50;

        for (EvaLabel lable : statLables) {
            lable.setLayoutX(0);
            lable.setLayoutY(i);
            i+=20;
            WeaponDisplay.getPane().getChildren().add(lable);
        }

        WeaponName = new TextField();
        WeaponName.setLayoutX(0);
        WeaponName.setLayoutY(20);


        File folder = new File(filepath+"/Weapons");
        File[] listOfFiles = folder.listFiles();
            List<String> ListOfNames = new ArrayList<>();
            for (int m = 0; m < listOfFiles.length; m++) {
                if (listOfFiles[m].isFile()) {
                    ListOfNames.add(listOfFiles[m].getName());
                }
            }
            boolean t = true;
            int k = 0;
            while (t) {
            String s = weapon.Name+k;
            k++;
            if (!ListOfNames.contains(s+".txt")) {
                t = false;
                this.weapon.Name = s;
            }
            }

        WeaponName.setText(this.weapon.Name);

        WeaponDisplay.getPane().getChildren().add(EvaStatsLable);
        WeaponDisplay.getPane().getChildren().add(WeaponName);

    }






    private void UpdateLables(){
        Profile.setText(weapon.profile);
        Technology.setText("Technology:  "+ weapon.Technology.name().toLowerCase()+ (!weapon.isDoubleEdged() ? "" : ("/"+ weapon.Technology2.name().toLowerCase())));
        String customisations = "Customisations: ";
        for (Weapon.Customisation custom : weapon.CurrentCustomisations) {
            customisations+=custom.toString().toLowerCase().replace("_"," ")+"; ";
        }
        Customisations.setText(customisations);

        String properties = "Properties: ";
        for (Weapon.Property prprt : weapon.CurrentProperties) {
            properties+=prprt.toString().toLowerCase().replace("_"," ")+"; ";
        }
        Properties.setText(properties);

        Hands.setText("Size: "+weapon.Hands.toString().toLowerCase().replace("_"," "));
        String pendef = "";
        if (weapon.getPenetration() != 0) pendef+="Penetration: "+weapon.getPenetration()+" ";
        if (weapon.getDefensive() != 0) pendef+="Defensive"+weapon.getDefensive();
        PenetrationDefensive.setText(pendef);

        if (PenetrationDefensive.equals("")) {
            SubWeapon.SetPosition(PenetrationDefensive.getLayoutX(), PenetrationDefensive.getLayoutY());
        } else SubWeapon.SetPosition(PenetrationDefensive.getLayoutX(), PenetrationDefensive.getLayoutY()+20);

        Cost.setText("Cost:  "+ weapon.getCost());
        Attack.setText("Attack:  "+ weapon.getDiceNumber()+"d"+weapon.getDiceStrength()+((weapon.getPower() != 0) ? "+"+weapon.getPower() : "")
                +((weapon.Technology.equals(Weapon.Tech.POLYTHERMIC) || weapon.Technology2.equals(Weapon.Tech.POLYTHERMIC)) ?
                ("/"+(weapon.OverHeatModifier() ? "4d3"+((weapon.getPower()+1 != 0) ? ("+"+(weapon.getPower()+1)) : "")
                        : "3d3"+((weapon.getPower() != 0) ? ("+"+weapon.getPower()) : "")))
                : ""));
        Range.setText(((weapon.Ranged ? "Ranged " : "Melee ")+weapon.getMinRange()+" - "+weapon.getMaxRange()));
        Ammo.setText("Ammo:  "+ weapon.getAmmoCapacity());
        if (weapon.hasSubWeapon()) {
            if (weapon.isEnhancedBayonet()) {
            SubWeapon.setText("Has: "+weapon.getSubWeapon().Technology.name().toLowerCase()+" "+weapon.getSubWeapon().Name); }
            else SubWeapon.setText("Has: "+weapon.getSubWeapon().Name);
        } else SubWeapon.setText("");
        if (weapon.Ranged) {
            techbuttons.get(1).setText("GAUSS");
            techbuttons.get(2).setText("N2SHELL");
            techbuttons.get(3).setText("MASER");
            techbuttons.get(4).setText("POSITRON");
        } else {
            techbuttons.get(1).setText("CHAIN");
            techbuttons.get(2).setText("PROGRESSIVE");
            techbuttons.get(3).setText("POLYTHERMIC");
            techbuttons.get(4).setText("SUPERCONDUCTIVE");
        }

        if (weapon.Technology.equals(Weapon.Tech.NONE) || (weapon.isDoubleEdged() && weapon.Technology2.equals(Weapon.Tech.NONE))) {
            CanSave.setText("Choose Technology");
        } else CanSave.setText("Can Save");


    }


    private EvaMenuSubScene createWeaponProfilesScene() {
        List<EvaButton> CustomisationsButtons = new ArrayList<>();
        EvaMenuSubScene WeaponProfileScene = new EvaMenuSubScene(1, true, 160, 1000);
        creatorPane.getChildren().add(WeaponProfileScene);
        for (Weapon w : WeaponProfileList) {
        createProfileButton(w.Name, CustomisationsButtons, w);
        }
        SetUpMenuList(WeaponProfileScene.getPane(), CustomisationsButtons, 5, 0);
        return WeaponProfileScene;
    }


    private void createProfileButton(String name, List<EvaButton> menu, Weapon prfl) {
       EvaButton button = new EvaButton(name);
       menu.add(button);
       button.setPrefHeight(30);
       button.setPrefWidth(150);
       button.setOnAction(new EventHandler<ActionEvent>() {
             @Override
          public void handle(ActionEvent event) {
                    weapon = prfl;
                    UpdateLables();
              }
           });
   }

    List<EvaButton> techbuttons = new ArrayList<>();
    private EvaMenuSubScene createTechnologySubSpace() {
        List<EvaButton> CustomisationsButtons = new ArrayList<>();
        EvaMenuSubScene TechScene = new EvaMenuSubScene(1);
        creatorPane.getChildren().add(TechScene);

        createTechnologyButton("NONE", CustomisationsButtons);
        createTechnologyButton("CHAIN", CustomisationsButtons);
        createTechnologyButton("PROGRESSIVE", CustomisationsButtons);
        createTechnologyButton("POLYTHERMIC", CustomisationsButtons);
        createTechnologyButton("SUPERCONDUCTIVE", CustomisationsButtons);

        SetUpMenuList(TechScene.getPane(), CustomisationsButtons, 5, 0);
        return TechScene;
    }

    private EvaMenuSubScene createSubTechnologySubSpace() {
        List<EvaButton> CustomisationsButtons = new ArrayList<>();
        EvaMenuSubScene TechScene = new EvaMenuSubScene(2);
        creatorPane.getChildren().add(TechScene);
        createSubTechnologyButton("NONE", CustomisationsButtons);
        createSubTechnologyButton("CHAIN", CustomisationsButtons);
        createSubTechnologyButton("PROGRESSIVE", CustomisationsButtons);
        createSubTechnologyButton("POLYTHERMIC", CustomisationsButtons);
        createSubTechnologyButton("SUPERCONDUCTIVE", CustomisationsButtons);
        SetUpMenuList(TechScene.getPane(), CustomisationsButtons, 5, 0);
        return TechScene;
    }


    private void createTechnologyButton(String name1, List<EvaButton> menu) {
        EvaButton button = new EvaButton(name1);
        menu.add(button);
        button.setPrefHeight(30);
        techbuttons.add(button);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String name = button.getText();
                if (weapon.isDoubleEdged()) {
                    if ((weapon.Technology.equals(Weapon.Tech.CHAIN) || weapon.Technology.equals(Weapon.Tech.PROGRESSIVE))
                            && (name.equals("POLYTHERMIC") || name.equals("SUPERCONDUCTIVE"))) {
                        weapon.Technology2 = Weapon.Tech.valueOf(name);
                    } else
                    if ((weapon.Technology.equals(Weapon.Tech.POLYTHERMIC) || weapon.Technology.equals(Weapon.Tech.SUPERCONDUCTIVE))
                            && (name.equals("CHAIN") || name.equals("PROGRESSIVE"))) {
                        weapon.Technology2 = Weapon.Tech.valueOf(name);
                    } else weapon.Technology = Weapon.Tech.valueOf(name);
                } else weapon.Technology = Weapon.Tech.valueOf(name);
                UpdateLables();
            }
        });
    }

    private void createSubTechnologyButton(String name, List<EvaButton> menu) {
        EvaButton button = new EvaButton(name);
        menu.add(button);
        button.setPrefHeight(30);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!weapon.hasSubWeapon()) return;
                weapon.applyTechToSubWeapon(Weapon.Tech.valueOf(name));
                if (name.equals("NONE")) {
                    weapon.removeBayonet();
                    weapon.removeEnhancedBayonet();
                    weapon.makeBayonet();
                } else {
                    weapon.removeEnhancedBayonet();
                    weapon.removeBayonet();
                    weapon.makeEnhancedBayonet();
                }
                UpdateLables();
            }
        });
    }

    private EvaMenuSubScene createCustomisationSubSpace() {
        List<EvaButton> CustomisationsButtons = new ArrayList<>();
        EvaMenuSubScene TechScene = new EvaMenuSubScene(1);
        creatorPane.getChildren().add(TechScene);

        createCustomisationButton("AntiArmor", CustomisationsButtons);
        createCustomisationButton("Balanced", CustomisationsButtons);
        createCustomisationButton("Double Edged", CustomisationsButtons);
        createCustomisationButton("Explosive", CustomisationsButtons);
        createCustomisationButton("ExtraAmmo", CustomisationsButtons);
        createCustomisationButton("Reinforced", CustomisationsButtons);
        createCustomisationButton("Throwing", CustomisationsButtons);
        createBayonetteSceneButton("Bayonet", CustomisationsButtons, createSubTechnologySubSpace());
        createCustomisationButton("TelescopicSight", CustomisationsButtons);
        createCustomisationButton("AutoLoader", CustomisationsButtons);

        SetUpMenuList(TechScene.getPane(), CustomisationsButtons, 5, 0);
        return TechScene;
    }

    private void createCustomisationButton(String name, List<EvaButton> menu) {
        EvaButton button = new EvaButton(name);
        menu.add(button);
        button.setPrefHeight(30);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                switch (name) {
                    case "AntiArmor" -> {
                        if (weapon.isAntiArmor()) {
                            weapon.removeAntiArmor();
                        } else {
                            weapon.makeAntiArmor();
                        }
                    }
                    case "Balanced" -> {
                        if (weapon.isBalanced()) {
                            weapon.removeBalanced();
                        } else {
                            weapon.makeBalanced();
                        }
                    }
                    case "Double Edged" -> {
                        if (weapon.isDoubleEdged()) {
                            weapon.Technology2 = Weapon.Tech.NONE;
                            weapon.removeDoubleEdged();
                        } else {
                            weapon.makeDoubleEdged();
                        }
                    }
                    case "Explosive" -> {
                        if (weapon.isExplosive()) {
                            weapon.removeExplosive();
                        } else {
                            weapon.makeExplosive();
                        }
                    }
                    case "ExtraAmmo" -> {
                        if (weapon.isExtraAmmo()) {
                            weapon.removeExtraAmmo();
                        } else {
                            weapon.makeExtraAmmo();
                        }
                    }
                    case "Reinforced" -> {
                        if (weapon.isReinforced()) {
                            weapon.removeReinforced();
                        } else {
                            weapon.makeReinforced();
                        }
                    }
                    case "Throwing" -> {
                        if (weapon.isThrowingCustomisation()) {
                            weapon.removeCustomisationThrowing();
                        } else {
                            weapon.makeCustomisationThrowing();
                        }
                    }
                    case "TelescopicSight" -> {
                        if (weapon.isTelescopicSight()) {
                            weapon.removeTelescopicSight();
                        } else {
                            weapon.makeTelescopicSight();
                        }
                    }
                    case "AutoLoader" -> {
                        if (weapon.isAutoLoader()) {
                            weapon.removeAutoLoader();
                        } else {
                            weapon.makeAutoLoader();
                        }
                    }
                }
                UpdateLables();
            }
        });
    }


   private void UpdateEvaName() {
        weapon.Name = WeaponName.getText();
   }
    private EvaButton createSaveButton(String name, List<EvaButton> menu){
        EvaButton button = new EvaButton(name);
        menu.add(button);
        button.setPrefHeight(30);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (CanSave.getText().equals("Can Save")) {
                try {
                    UpdateEvaName();
                    EvaSaveUtil.SaveWeapon(savepath, weapon);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            }
        });
        return button;
    }




    private void createButtons(){
        createSceneButton("Profiles", MainMenuButtons, createWeaponProfilesScene());
        createSceneButton("Technology", MainMenuButtons, createTechnologySubSpace());
        createSceneButton("Customisations", MainMenuButtons, createCustomisationSubSpace());
        EvaButton savebutton = createSaveButton("Save Weapon", MainMenuButtons);



        SetUpMenuList(creatorPane, MainMenuButtons, 100, 100);
        CanSave.SetPosition(savebutton.getLayoutX(), savebutton.getLayoutY()+40);
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







    private void createBackground() {
        Image backgroundImage = new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("/eva/background.png")), 256, 256, false, false);
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
        creatorPane.setBackground(new Background(background));
    }

  //  public void createNewMaker(Stage menuStage) {
   //     this.createNewMaker(menuStage, new Evangelion());
  //  }


    private void initializeStage() {
        creatorPane = new AnchorPane();
        creatorScene = new Scene(creatorPane, WIDTH, HEIGHT);
        creatorStage = new Stage();
        creatorStage.setScene(creatorScene);
    }



    private void createSceneButton(String name, List<EvaButton> menu, EvaMenuSubScene subscene) {
        EvaButton button = new EvaButton(name);
        menu.add(button);
        button.setPrefHeight(30);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showSubScene(subscene);
            }
        });
    }

    private void createBayonetteSceneButton(String name, List<EvaButton> menu, EvaMenuSubScene subscene) {
        EvaButton button = new EvaButton(name);
        menu.add(button);
        button.setPrefHeight(30);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!weapon.Ranged) return;
                if (weapon.isBayonet() || weapon.isEnhancedBayonet()) {
                    weapon.subWeapon = null;
                    weapon.removeBayonet();
                    weapon.removeEnhancedBayonet();
                    UpdateLables();
                    hideSubScene(subscene);
                } else {
                    try {
                        weapon.addSubWeapon(EvaSaveUtil.ReadWeaponProfile(EvaSaveUtil.getFilepath() + "/WeaponProfiles/Knife.txt"));
                        weapon.getSubWeapon().Name = "Bayonet Knife";
                        weapon.makeBayonet();
                        UpdateLables();
                        showSubScene(subscene);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
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

    private void hideSubScene(EvaMenuSubScene subScene) {
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
        sceneToHide = newlist;
    }


}
