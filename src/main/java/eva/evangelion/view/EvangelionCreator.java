package eva.evangelion.view;


import eva.evangelion.units.Types.EvangelionType;
import eva.evangelion.units.Upgrades.Upgrade;
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

public class EvangelionCreator {




    public List<Upgrade> UpgradeList;
    private String filepath = EvaSaveUtil.getFilepath();
    private String savepath = EvaSaveUtil.getSavepath()+"\\Evangelions\\";
    public EvangelionType Eva;

    private AnchorPane creatorPane;
    private Scene creatorScene;
    private Stage creatorStage;
    private static final int WIDTH = 700;
    private static final int HEIGHT= 700;
    private Stage menuStage;
    private List<EvaMenuSubScene> sceneToHide;

    private List<EvaButton> MainMenuButtons;
    private List<EvaButton> UpgradeButtons;
    private EvaMenuSubScene UpgradesSubScene;
    private DisplaySubSpace EvangelionDisplay;

    EvaLabel AccuracyLabel = new EvaLabel("");
    EvaLabel AttackStrengthLabel = new EvaLabel("");
    EvaLabel ToughnessLabel = new EvaLabel("");
    EvaLabel ArmorLabel = new EvaLabel("");
    EvaLabel ReflexesLabel = new EvaLabel("");
    EvaLabel SpeedLabel = new EvaLabel("");
    EvaLabel RequisitionLabel = new EvaLabel("");
    EvaLabel UpgradesAvaliableLabel = new EvaLabel("");

    List<EvaLabel> statLables;
    TextField EvaName;


    public EvangelionCreator() throws IOException {
        initializeStage();
    }



    public void createNewMaker(Stage menuStage, EvangelionType evangelionType) throws IOException {
        statLables = new ArrayList<>();



        statLables.add(AccuracyLabel);
        statLables.add(AttackStrengthLabel);
        statLables.add(ToughnessLabel);
        statLables.add(ArmorLabel);
        statLables.add(ReflexesLabel);
        statLables.add(SpeedLabel);
        statLables.add(RequisitionLabel);
        statLables.add(UpgradesAvaliableLabel);

        MainMenuButtons = new ArrayList<>();
        sceneToHide = new ArrayList<>();
        UpgradeList = new ArrayList<>();

        File folder = new File(filepath+"/Upgrades");
        File[] listOfFiles = folder.listFiles();
        for (int m = 0; m < listOfFiles.length; m++) {
            if (listOfFiles[m].isFile()) {
                UpgradeList.add(EvaSaveUtil.ReadUpgrade(listOfFiles[m].getAbsolutePath()));
            }
        }

        this.Eva = evangelionType;

        UpdateLables();

        this.menuStage = menuStage;
        this.menuStage.hide();
        creatorStage.show();

        createUpgradesSubSpace();
        createDisplaySubSpace();


        createButtons();
        createBackground();


    }

    private void createDisplaySubSpace() {
        EvangelionDisplay = new DisplaySubSpace();
        creatorPane.getChildren().add(EvangelionDisplay);
        EvangelionDisplay.moveSubScene();
        EvaLabel EvaStatsLable = new EvaLabel("Evangelion Stats:");
        EvaStatsLable.setLayoutX(0);
        EvaStatsLable.setLayoutY(0);
        int i = 40;

        for (EvaLabel lable : statLables) {
            lable.setLayoutX(0);
            lable.setLayoutY(i);
            i+=20;
            EvangelionDisplay.getPane().getChildren().add(lable);
        }

        EvaName = new TextField();
        EvaName.setLayoutX(130);
        EvaName.setLayoutY(34);

        if (this.Eva.Name == null) {
            File folder = new File(filepath+"/Evangelions");
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
            String s = "Evangelion Unit "+k;
            k++;
            if (!ListOfNames.contains(s+".txt")) {
                t = false;
                this.Eva.Name = s;
            }
            }
        }
        EvaName.setText(this.Eva.Name);

        EvangelionDisplay.getPane().getChildren().add(EvaStatsLable);
        EvangelionDisplay.getPane().getChildren().add(EvaName);

    }






    private void UpdateLables(){
        if (Eva.AccuracyDisplay != Eva.AccuracyNext)
        AccuracyLabel.setText("Accuracy:  "+Eva.AccuracyDisplay+"   -->   "+Eva.AccuracyNext);
        else AccuracyLabel.setText("Accuracy:  "+Eva.AccuracyDisplay);

        if (Eva.AttackStrengthDisplay != Eva.AttackStrengthNext)
            AttackStrengthLabel.setText("Attack Strength (S):  "+Eva.AttackStrengthDisplay+"   -->   "+Eva.AttackStrengthNext);
        else AttackStrengthLabel.setText("Attack Strength (S):  "+Eva.AttackStrengthDisplay);

        if (Eva.ToughnessDisplay != Eva.ToughnessNext)
            ToughnessLabel.setText("Toughness:  "+Eva.ToughnessDisplay+"   -->   "+Eva.ToughnessNext);
        else ToughnessLabel.setText("Toughness:  "+Eva.ToughnessDisplay);

        if (Eva.ArmorDisplay != Eva.ArmorNext)
            ArmorLabel.setText("Armor:  "+Eva.ArmorDisplay+"   -->   "+Eva.ArmorNext);
        else ArmorLabel.setText("Armor:  "+Eva.ArmorDisplay);

        if (Eva.ReflexesDisplay != Eva.ReflexesNext)
            ReflexesLabel.setText("Reflexes:  "+Eva.ReflexesDisplay+"   -->   "+Eva.ReflexesNext);
        else ReflexesLabel.setText("Reflexes:  "+Eva.ReflexesDisplay);

        if (Eva.RequisitionDisplay != Eva.RequisitionNext)
            RequisitionLabel.setText("Requisition:  "+Eva.RequisitionDisplay+"   -->   "+Eva.RequisitionNext);
        else RequisitionLabel.setText("Requisition:  "+Eva.RequisitionDisplay);

        if (Eva.SpeedDisplay != Eva.SpeedNext)
            SpeedLabel.setText("Speed:  "+Eva.SpeedDisplay+"   -->   "+Eva.SpeedNext);
        else SpeedLabel.setText("Speed:  "+Eva.SpeedDisplay);

        if (Eva.UpgradesAvaliableDisplay != Eva.UpgradesAvaliableNext)
            UpgradesAvaliableLabel.setText("Upgrades Avaliable:  "+Eva.UpgradesAvaliableDisplay+"   -->   "+Eva.UpgradesAvaliableNext);
        else UpgradesAvaliableLabel.setText("Upgrades Avaliable:  "+Eva.UpgradesAvaliableDisplay);

    }




    private void createUpgradesSubSpace() {
        UpgradeButtons = new ArrayList<>();
        UpgradesSubScene = new EvaMenuSubScene(1);

        creatorPane.getChildren().add(UpgradesSubScene);
        for (Upgrade upg : UpgradeList) {
        createUpgradeButton(upg.Name, UpgradeButtons, upg, UpgradesSubScene);
        }
        SetUpMenuList(UpgradesSubScene.getPane(), UpgradeButtons, 5, 0);
    }


    private void createUpgradeButton(String name, List<EvaButton> menu, Upgrade upgrade, EvaMenuSubScene subScene) {
       EvaButton button = new EvaButton(name);
       menu.add(button);
           button.setOnAction(new EventHandler<ActionEvent>() {

             @Override
          public void handle(ActionEvent event) {
                   if (Eva.ChosenUpgrades.contains(upgrade)) {
                       Eva.ChosenUpgrades.remove(upgrade);
                   } else {
                       Eva.ChosenUpgrades.add(upgrade);
                   }
                    Eva.CalculateNext();
                    UpdateLables();
              }
           });
   }

   private void UpdateEvaName() {
        Eva.Name = EvaName.getText();
   }
    private void createSaveButton(String name, List<EvaButton> menu){
        EvaButton button = new EvaButton(name);
        menu.add(button);
        button.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                try {
                    UpdateEvaName();
                    EvaSaveUtil.SaveEvangelion(savepath, Eva);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }


    private void createApplyUpgradesButton(String name, List<EvaButton> menu){
        EvaButton button = new EvaButton(name);
        menu.add(button);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Eva.CurrentUpgrades.clear();
                Eva.CurrentUpgrades.addAll(Eva.ChosenUpgrades);
                Eva.CalculateDisplay();
                Eva.CalculateNext();
                UpdateEvaName();
                UpdateLables();
            }
        });
    }



    private void createButtons(){
        createSceneButton("Upgrades", MainMenuButtons, UpgradesSubScene);
        createSaveButton("Save Eva", MainMenuButtons);
        createApplyUpgradesButton("Apply Upgrades", MainMenuButtons);

        SetUpMenuList(creatorPane, MainMenuButtons, 100, 100);
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
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showSubScene(subscene);
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




}
