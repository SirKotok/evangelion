package eva.evangelion.view;

import eva.evangelion.activegame.Gamestate;
import eva.evangelion.activegame.actions.Attack;
import eva.evangelion.activegame.actions.Movement;
import eva.evangelion.activegame.activeunits.*;
import eva.evangelion.activegame.activeunits.unitstate.*;
import eva.evangelion.gameboard.Battlefield;
import eva.evangelion.gameboard.GameBoard;
import eva.evangelion.gameboard.Sector;
import eva.evangelion.gameboard.SectorType;
import eva.evangelion.units.Types.AngelType;
import eva.evangelion.units.Types.EvangelionType;
import eva.evangelion.units.Upgrades.ATPower;
import eva.evangelion.units.Upgrades.Upgrade;
import eva.evangelion.units.WeaponObject;
import eva.evangelion.util.DirectoryWatcher;
import eva.evangelion.util.EvaCalculationUtil;
import eva.evangelion.util.EvaSaveUtil;
import eva.evangelion.util.EvaWoundDeterminer;
import eva.evangelion.view.evainterface.DisplaySubSpace;
import eva.evangelion.view.evainterface.EvaButton;
import eva.evangelion.view.evainterface.EvaLabel;
import eva.evangelion.view.evainterface.EvaMenuSubScene;
import eva.evangelion.view.shape.Arrow;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import kotlin.Triple;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


import static eva.evangelion.gameboard.SectorType.Blank;
import static eva.evangelion.gameboard.SectorType.Destroyed;

//Mawrak's edits
// Add these imports:
import javafx.scene.control.ScrollPane;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.geometry.Rectangle2D; // For Rectangle2D
import javafx.stage.Screen;        // For Screen class

public class GameInterface {
    private Battlefield Battlefield;
    private GameBoard GameBoard;
    private GridPane UnitsBoard;
    private AnchorPane gamePane;
    private EvaMenuSubScene GMSubScene;
    private Scene gameScene;
    private Stage gameStage;
    private static final int WIDTH = 1300;
    private static final int HEIGHT= 900;
    private  EvaLabel CurrentClickedLabel = new EvaLabel("Current Clicked: ");
    private  EvaLabel TurnOrderLabel = new EvaLabel("Current Turn: GM - Set Turn Order");

    public boolean TurnEnd = true;

    private Movement CurrentMovement;
    private Attack CurrentAttack;
    private Stage menuStage;
    private Weapon CurrentChosenWeapon;
    private EvaButton EndTurnButton;
    private List<EvaButton> SectorTypesButtons;
    private String filepath = EvaSaveUtil.getFilepath();
    private String savegamepath = EvaSaveUtil.getSaveGamePath();
    private String savebackuppath = EvaSaveUtil.getSavepath()+"\\Savebackups\\";
    public List<SectorType> SectorTypesList;
    public List<String> UpgradesList;
    public List<EvangelionType> EvaTypesList;
    public List<AngelType> AngelTypesList;
    public List<String> EvaTypesNamesList;
    public List<String> WeaponsNamesList;
    public List<String> AngelTypesNamesList;
    public Gamestate CurrentState;
    public List<BaseUnit> UnitsList;
    public List<ChazaqielSummon> SummonList = new ArrayList<>();
    public List<Evangelion> EvangelionList;
    public List<Angel> AngelList;
    public BaseUnit ClickedUnit;
    public BaseUnit Target;
    public int CurrentAttackAmmoCost = 0;
    public int CurrentAttackStaminaCost = 0;
    public List<BaseUnit> TargetList;
    public BaseUnit LastClickedUnit;
    public Sector ClickedSector;
	//Mawrak's edits
	public String PreviousAttack = "Basic Attack";
	public Boolean PreserveProgress = false;

    public List<String> Players;
    public List<String> SummonPlayers = new ArrayList<>();

    public String CurrentPlayer = "NONE";

    public List<EvaMenuSubScene> PlayerMenus;
    public List<EvaMenuSubScene> PlayerSetUpMenus;
    private DisplaySubSpace GMScreen;
    private String CurrentAction;
    private String CurrentSubAction;


    public int Round = 1;
    private DisplaySubSpace EvangelionDisplay;
    private EvaLabel AccuracyLabel = new EvaLabel("");
    private EvaLabel AttackStrengthLabel = new EvaLabel("");
    private EvaLabel ToughnessLabel = new EvaLabel("");

    private EvaLabel WoundLevelLable = new EvaLabel("");
    private EvaLabel ArmorLabel = new EvaLabel("");
    private EvaLabel ReflexesLabel = new EvaLabel("");
    private EvaLabel SpeedLabel = new EvaLabel("");
    private EvaLabel RequisitionORPenetrationLabel = new EvaLabel("");
    private  EvaLabel Staminalabel = new EvaLabel("");
    private  EvaLabel EvaStatsLabel = new EvaLabel("Evangelion Stats:");

    private WeaponObject TargetObject;

    private int SizeDelta = 20;
    private EvaButton TechnologyButton;
    private EvaButton AttackMax;
    private  EvaLabel AttackLabel = new EvaLabel("Attack Rolls:");
    private  EvaLabel DefenceLabel = new EvaLabel("Defence:");
    private  TextField HelpName = new TextField();
    private  List<EvaButton> NextTurnButtons;
    private DisplaySubSpace CurrentEvangelionDisplay;
    private DisplaySubSpace CurrentEvangelionDisplay2;
    private EvaButton SwitchEdgeButton;
    private EvaLabel CurrentWeaponLabel = new EvaLabel("");
    private EvaLabel CurrentWeaponProfileLabel = new EvaLabel("");
    private EvaLabel CurrentWeaponRangeLabel = new EvaLabel("");
    private EvaLabel CurrentAmmoLabel = new EvaLabel("");
    private EvaLabel CurrentWeaponTechnologyLabel = new EvaLabel("");
    private EvaLabel CurrentAccuracyLabel = new EvaLabel("");
    private EvaLabel CurrentAttackStrengthLabel = new EvaLabel("");
    private EvaLabel CurrentToughnessLabel = new EvaLabel("");
    private ATPower ATPower;
    private EvaLabel CurrentWoundLevelLabel = new EvaLabel("");
    private EvaLabel CurrentArmorLabel = new EvaLabel("");
    private EvaLabel CurrentATPLabel = new EvaLabel("");
    private EvaLabel CurrentReflexesLabel = new EvaLabel("");
    public List<Sector> BlowUpSectorList = new ArrayList<>();
    private EvaLabel CurrentSpeedLabel = new EvaLabel("");
    private EvaLabel CurrentRequisitionOrPenLabel = new EvaLabel("");
    private EvaLabel CurrentStamina = new EvaLabel("");
    private EvaLabel CurrentFateDoomLabel = new EvaLabel("");
    private EvaLabel CurrentEvaStatsLabel = new EvaLabel("Evangelion Stats:");
    private EvaLabel TechnologyLabel = new EvaLabel("");
    private EvaLabel TechnologySwitchLabel = new EvaLabel("");
    private List<WeaponObject> WeaponsWorld = new ArrayList<>();
    public TextField PlayerName = new TextField();
    private StateEffect NewWound;
    private StateEffect ReducedWound = null;
    private List<EvaLabel> statLabels;
    public int FogNumber = -1;
    private TextField GMEffectName = new TextField("Effect");
    private TextField GMFog = new TextField("-1");
    private TextField GMDamage = new TextField("0");
    private TextField GMStand = new TextField("false");
    private TextField GMCostDoom = new TextField("0");
    private TextField GMEffectAccuracy = new TextField("0");
    private TextField GMEffectReflexes = new TextField("0");
    private TextField GMEffectProhibited = new TextField("");
    private TextField GMEffectArmor = new TextField("0");
    private Weapon.Tech DevineStrength = Weapon.Tech.NONE;
    public java.util.Random RandomGenerator = new java.util.Random();
    private TextField GMEffectAttackStrength = new TextField("0");
    private TextField GMEffectRangedStrength = new TextField("0");
    private TextField GMEffectSpeed = new TextField("0");
    private TextField GMEffectMaxToughness = new TextField("0");
    private TextField GMEffectSpeedChange = new TextField("0");
    private TextField GMEffectPenetration = new TextField("0");
    private TextField GMEffectExpiration = new TextField("1");
    private TextField GMEffectExpirationCondition = new TextField("none");
    private  EvaLabel CurrentOutFitLabel = new EvaLabel("");
    private  EvaLabel CurrentEffectsLabel = new EvaLabel("");
    private  List<EvaLabel> CurrentstatLabels= Arrays.asList(
            CurrentAccuracyLabel,
            CurrentAttackStrengthLabel,
            CurrentToughnessLabel,
            CurrentArmorLabel,
            CurrentReflexesLabel,
            CurrentSpeedLabel,
            CurrentRequisitionOrPenLabel,
            CurrentStamina,
            CurrentATPLabel,
            CurrentWoundLevelLabel,
            CurrentWeaponLabel,
            CurrentWeaponRangeLabel,
            CurrentWeaponProfileLabel,
            CurrentAmmoLabel,
            CurrentFateDoomLabel,
            CurrentWeaponTechnologyLabel
    );

    private  List<EvaLabel> CurrentstatLabels2= Arrays.asList(
            CurrentOutFitLabel,
            CurrentEffectsLabel
    );






    public StateEffect pattern;
    private Pane viewport = new Pane();
    public WingLoadout swapWing;
    public String swapHand;
    public EvaLabel swapLabel = new EvaLabel("");
    private DisplaySubSpace AttackRollsSubScene;
    private DisplaySubSpace DefenceSubScene;
    private DisplaySubSpace DoubleEdgeCheckSubScene;
    private DisplaySubSpace WoundSubScene;
    private DisplaySubSpace HelpSubScene;
    private DisplaySubSpace OnMissDramaSubScene;
    private DisplaySubSpace OnEvaWoundDramaSubScene;
    private DisplaySubSpace OnResupplyDramaSubScene;
    private DisplaySubSpace OnEvaTurnStartDramaSubScene;
    private DisplaySubSpace OnIntervalDramaSubScene;
    private DisplaySubSpace OnEvaRangedAttackDramaSubScene;
    private DisplaySubSpace OnDealingDamageDramaSubScene;
    private DisplaySubSpace OnEvaGuardDramaSubScene;
    private DisplaySubSpace OnEvaHitDramaSubScene;
    private EvaLabel AttackRollLabel;
    private EvaLabel DebugLabel;
    private EvaLabel AttackTestRollLabel;
    private EvaLabel DefenceTestRollLabel;
    private EvaLabel WoundLabel;
    private boolean AttackTestRollCanUse = true;
    private boolean AttackRollCanUse = true;


    public GameInterface() throws IOException {
        initializeStage();
    }

    private Arrow arrow;

    public void createNewMaker(Stage menuStage, Battlefield field, Gamestate gamestate, boolean InProgress, String Player) throws IOException, ClassNotFoundException {
        EvangelionList = new ArrayList<>();
        AngelList = new ArrayList<>();
        UnitsList = new ArrayList<>();
        Players = new ArrayList<>();
        PlayerMenus = new ArrayList<>();
        CurrentAction = "None";
        CurrentPlayer = Player;
        this.CurrentState = gamestate;

        statLabels = new ArrayList<>();
        statLabels.add(AccuracyLabel);
        statLabels.add(AttackStrengthLabel);
        statLabels.add(ToughnessLabel);
        statLabels.add(ArmorLabel);
        statLabels.add(ReflexesLabel);
        statLabels.add(SpeedLabel);
        statLabels.add(RequisitionORPenetrationLabel);
        statLabels.add(Staminalabel);
        statLabels.add(WoundLevelLable);

        File folder = new File(filepath+"/Upgrades");
        File[] listoffiles = folder.listFiles();
        UpgradesList = new ArrayList<>();
        for (int m = 0; m < listoffiles.length; m++) {
            if (listoffiles[m].isFile()) {
                UpgradesList.add(listoffiles[m].getName().replace(".txt", ""));
            }
        }

        gamePane.getChildren().add(amongusventedscene);

        WeaponsNamesList = new ArrayList<>();
        folder = new File(filepath+"/Weapons");
        listoffiles = folder.listFiles();
        for (int m = 0; m < listoffiles.length; m++) {
            if (listoffiles[m].isFile()) {
                WeaponsNamesList.add(listoffiles[m].getName().replace(".txt", ""));
            }
        }



        AngelTypesNamesList = new ArrayList<>();
        AngelTypesList = new ArrayList<>();
        folder = new File(filepath+"/Angels");
        listoffiles = folder.listFiles();
        for (int m = 0; m < listoffiles.length; m++) {
            if (listoffiles[m].isFile()) {
                AngelTypesList.add(EvaSaveUtil.ReadAngelType(listoffiles[m].getAbsolutePath(), UpgradesList));
                AngelTypesNamesList.add(AngelTypesList.get(m).Name);
            }
        }



        SectorTypesButtons = new ArrayList<>();




        SectorTypesList = new ArrayList<>();
        SectorTypesList.add(Blank);
        SectorTypesList.add(Destroyed);
        CurrentClickedLabel.SetPosition(150, 20);
        gamePane.getChildren().add(CurrentClickedLabel);

        TurnOrderLabel.SetPosition(150, 5);
        gamePane.getChildren().add(TurnOrderLabel);





        folder = new File(filepath+"/SectorTypes");
        listoffiles = folder.listFiles();
        for (int m = 0; m < listoffiles.length; m++) {
            if (listoffiles[m].isFile()) {
                SectorTypesList.add(EvaSaveUtil.ReadSectorType(listoffiles[m].getAbsolutePath()));
            }
        }

        this.Battlefield = field;
        CurrentState.Field = this.Battlefield;

        createGameBoard(Battlefield.sizeX, Battlefield.sizeY);
        PlayerSetUpMenus = new ArrayList<>();
        for (EvangelionState EvaInitial : gamestate.EvaList) {
            Players.add(EvaInitial.PlayerName);
            Evangelion Eva = new Evangelion(
                    EvaInitial,
                    EvaSaveUtil.ReadStringEvangelionType(EvaInitial.TypeString, UpgradesList));
            Eva.type.CalculateDisplay();
            EvangelionList.add(Eva);
            viewport.getChildren().add(Eva.UnitCircle);
            Eva.UnitCircle.setDisable(true);
            PlayerMenus.add(createMovesSubScene(Eva));



            EvaMenuSubScene WeaponRequisition = new EvaMenuSubScene(4, 40, SizeDelta, 700);
            List<EvaButton> WeaponRequisitionMenuButtons = new ArrayList<>();
            createChooseWeaponButton("None", WeaponRequisitionMenuButtons);
            createChooseWeaponButton("Ammo", WeaponRequisitionMenuButtons);
            for (String s : WeaponsNamesList) {
                createChooseWeaponButton(s, WeaponRequisitionMenuButtons);
            }


            PlayerSetUpMenus.add(WeaponRequisition);
            gamePane.getChildren().add(WeaponRequisition);
            SetUpLabeledWeaponList(WeaponRequisition.getPane(), WeaponRequisitionMenuButtons, 0, 0);
        }

        PlayerMenus.add(createAngelSubScene());
        GMSubScene = createGMSubScene();

        for (AngelState Angel : gamestate.AngelList) {
            Angel angel = new Angel(Angel, AngelTypesList.get(AngelTypesNamesList.indexOf(Angel.TypeString)));
            AngelList.add(angel);
            viewport.getChildren().add(angel.UnitCircle);
            angel.UnitCircle.setDisable(true);
            if (!InProgress) angel.SetTurnDone(true);
        //    PlayerMenus.add(createMovesSubScene());
        }

        UnitsList.addAll(EvangelionList);
        UnitsList.addAll(AngelList);




        Update();


        PlayerName.setLayoutX(80);
        PlayerName.setLayoutY(80);
        PlayerName.setPrefWidth(50);
        PlayerName.setText(Player);
        gamePane.getChildren().add(PlayerName);
        createApplyNameButton("name", PlayerName);
        createEndTurnButton();
        startSaveFileWatcher();
        createUpdateTurnButton();
        createDisplaySubSpace();
        createCurrentDisplaySubSpace();
        createRollSubScene();
        createDefenceSubScene();
        createHelpSubScene();
        createWoundSubScene();
        createDoubleEdgeCheckSubScene();

        setUpSliders();

        EvaButton DebugButton = createDebugButton("Debug");
        EvaButton WeaponButton = createNewWeaponButton("New Weapon");
        gamePane.getChildren().add(DebugButton);
        gamePane.getChildren().add(WeaponButton);

        this.menuStage = menuStage;
        this.menuStage.hide();
        gameStage.show();

        EventHandler<WindowEvent> onClosedEvent = new EventHandler<>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                System.exit(0);
            }
        };


        gameStage.setOnCloseRequest(onClosedEvent);
        ReadFromGameState();

        if (!InProgress) {
            //RandomisePosition();
            SetPositionToStart();
            CurrentState.Phase = "SetUp";
            CurrentAction = "SetUp";
        }

        gamePane.getChildren().add(swapLabel);
        swapLabel.setLayoutX(SizeDelta+250);
        swapLabel.setLayoutY(10);
        ResetSwap();
        UpdateSwapLabel();

        EndTurn();
    }

    public void discard(WeaponObject weaponobj){
        weaponobj.setPosition(1000, 1000);
        weaponobj.weapon = null;
        WeaponsWorld.remove(weaponobj);
        viewport.getChildren().remove(weaponobj.ItemCircle);
    }
    public void discard(BaseUnit unit){
        unit.setY(1000);
        unit.setY(1000);
        UnitsList.remove(unit);
        Players.remove(unit.getPlayerName());
        SummonPlayers.remove(unit.getPlayerName());
        if (unit instanceof Evangelion e) {
            EvangelionList.remove(e);
        }
        if (unit instanceof ChazaqielSummon e) {
            SummonList.remove(e);
        }
        if (unit instanceof Angel e) {
            AngelList.remove(e);
        }
        viewport.getChildren().remove(unit.UnitCircle);

    }



    private void UpdateSwapLabel() {
        String s = "";
        if (swapHand == null && swapWing == null) {
            swapLabel.setText(s);
            return;
        }
        if (swapHand != null) s += swapHand;
        s+=" - ";
        if (swapWing != null) s += swapWing.loadout.name().toLowerCase()+" ("+swapWing.location+")";
        swapLabel.setText(s);
    }

    private void ResetSwap() {
       swapWing = null;
       swapHand = null;
    }


    private void SetUpCurrentOutfitLabel(Evangelion eva) {
        StringBuilder s = new StringBuilder("Right Hand - ");
        if (eva.getRightHandItem() != null) s.append(eva.getRightHandItem().Name);
        s.append(System.lineSeparator()).append("Left Hand - ");
        if (eva.getLeftHandItem() != null) s.append(eva.getLeftHandItem().Name);
        if (eva.hasUpgrade("Extraneous Limbs")) {
        s.append(System.lineSeparator()).append("Third Hand - ");
        if (eva.getThirdHandItem() != null) s.append(eva.getThirdHandItem().Name);
        }
        for (WingLoadout wing : eva.state.Wings) {
            s.append(System.lineSeparator());
            s.append(wing.loadout.name().toLowerCase()).append(" (").append(wing.getLocation()).append(")");
            if (wing.canStoreWeapon()) {
                s.append(" - ");}
            if (wing.getItem() != null) s.append(wing.getItem().Name);
        }
        CurrentOutFitLabel.setText(s.toString());
    }

    private void SetUpCurrentEffectsLabel(Evangelion evangelion) {
        StringBuilder s = new StringBuilder("Current Effects:");
        int x = 0;
        for (String s1 : evangelion.getNameEffects()) {
            s.append(" ");
            s.append(s1);
            if (x == 3) {
              s.append(System.lineSeparator());
              x = 0;
            } else x++;
        }
        CurrentEffectsLabel.setText(s.toString());
    }
    private void createDisplaySubSpace() {
        EvangelionDisplay = new DisplaySubSpace(560, 430, 100, 400, SizeDelta);
        gamePane.getChildren().add(EvangelionDisplay);
        EvangelionDisplay.moveSubScene();

        EvaStatsLabel.setLayoutX(0);
        EvaStatsLabel.setLayoutY(0);
        int i = 20;
        for (EvaLabel lable : statLabels) {
            lable.setLayoutX(0);
            lable.setLayoutY(i);
            i+=20;
            EvangelionDisplay.getPane().getChildren().add(lable);
        }
        EvangelionDisplay.getPane().getChildren().add(EvaStatsLabel);


    }

    private void createRollSubScene() {
        AttackRollsSubScene = new DisplaySubSpace(560, 40, 400, 200, SizeDelta);
        gamePane.getChildren().add(AttackRollsSubScene);
        AttackLabel.SetPosition(50,0);

        AttackRollLabel = new EvaLabel("");
        AttackRollLabel.SetPosition(10, 20);
        DebugLabel = new EvaLabel("DEBUG");
        DebugLabel.SetPosition(25, 10);
        AttackRollsSubScene.getPane().getChildren().add(DebugLabel);
        AttackTestRollLabel = new EvaLabel("");
        AttackTestRollLabel.SetPosition(100, 20);
        EvaButton AttackRoll = createAttackRollButton("Roll Attack");
        AttackMax = createAttackRollMaxButton("Max Roll");

        TechnologyButton = createTechnologyButton("Overheat");
        EvaButton AttackTestRoll = createAttackRollTestButton("Attack Test");
        AttackRollsSubScene.getPane().getChildren().add(TechnologyButton);
        AttackRollsSubScene.getPane().getChildren().add(AttackRoll);
        AttackRollsSubScene.getPane().getChildren().add(AttackMax);
        AttackRollsSubScene.getPane().getChildren().add(AttackTestRoll);
        AttackRollsSubScene.getPane().getChildren().add(TechnologyLabel);




        AttackRoll.setPosition(10, 40);
        AttackTestRoll.setPosition(100, 40);
        AttackMax.setPosition(10, 80);
        TechnologyButton.setPosition(10, 120);
        TechnologyLabel.SetPosition(10, 150);

        EvaButton WeaponFluxButton = createWPFluxButton("Weapon Flux");
        AttackRollsSubScene.getPane().getChildren().add(WeaponFluxButton);
        WeaponFluxButton.setPosition(10, 170);

        AttackRollsSubScene.getPane().getChildren().add(AttackLabel);
        AttackRollsSubScene.getPane().getChildren().add(AttackRollLabel);
        AttackRollsSubScene.getPane().getChildren().add(AttackTestRollLabel);
    }


    private void createDoubleEdgeCheckSubScene() {
        DoubleEdgeCheckSubScene = new DisplaySubSpace(560, 40, 400, 200,SizeDelta);
        gamePane.getChildren().add(DoubleEdgeCheckSubScene);
        SwitchEdgeButton = createSwitchEdgeButton("Switch");
        DoubleEdgeCheckSubScene.getPane().getChildren().add(TechnologySwitchLabel);
        DoubleEdgeCheckSubScene.getPane().getChildren().add(SwitchEdgeButton);
        SwitchEdgeButton.setPosition(100, 40);
        SwitchEdgeButton.setPosition(100, 70);
    }

    private void createDefenceSubScene() {
        DefenceSubScene = new DisplaySubSpace(560, 40, 400, 200,SizeDelta);
        gamePane.getChildren().add(DefenceSubScene);
        DefenceLabel.SetPosition(50,0);


        DefenceTestRollLabel = new EvaLabel("");
        DefenceTestRollLabel.SetPosition(100, 20);

        EvaButton Guard = createGuardButton("Guard");
        DefenceSubScene.getPane().getChildren().add(Guard);
        Guard.setPosition(100, 40);

        EvaButton LayeredField = createLayeredFieldButton("Layered Field");
        DefenceSubScene.getPane().getChildren().add(LayeredField);
        LayeredField.setPosition(100, 80);

        EvaButton Fate = createDefenceFateButton("Use Fate");
        DefenceSubScene.getPane().getChildren().add(Fate);
        Fate.setPosition(100, 120);


        HelpName.setLayoutX(10);
        HelpName.setLayoutY(20);
        HelpName.setPrefWidth(50);
        HelpName.setText("");
        DefenceSubScene.getPane().getChildren().add(HelpName);

        EvaButton Help = createAskHelpButton("Ask Help", HelpName);
        DefenceSubScene.getPane().getChildren().add(Help);
        Help.setPosition(10, 40);

        DefenceSubScene.getPane().getChildren().add(DefenceLabel);
        DefenceSubScene.getPane().getChildren().add(DefenceTestRollLabel);
    }

    private void createHelpSubScene() {
        HelpSubScene = new DisplaySubSpace(560, 40, 400, 200, SizeDelta);
        gamePane.getChildren().add(HelpSubScene);
        EvaButton LayeredField = createLayeredFieldHelpButton("Body Guard");
        HelpSubScene.getPane().getChildren().add(LayeredField);
        LayeredField.setPosition(100, 30);
        EvaButton Fate = createEnablerButton("Use Fate");
        HelpSubScene.getPane().getChildren().add(Fate);
        Fate.setPosition(100, 60);
    }
    private EvaButton createLayeredFieldHelpButton(String name){
        EvaButton button = new EvaButton(name);
        button.setPrefWidth(100);
        button.setPrefHeight(30);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                BaseUnit Unit2 = getCurrentUnit();
                BaseUnit Unit = getUnitFromPlayer(((Attack) CurrentState.Action).Defender);
                if (Unit2 != null) {
                    if (Unit2.getATP() > 0) {
                        Unit.AddEffect(CommonEffects.LayeredField());
                        Unit2.setATP(0);
                        UpdateUnitLabels();
                    }
                }
            }
        });
        return button;
    }

    private EvaButton createDefenceFateButton(String name){
        EvaButton button = new EvaButton(name);
        button.setPrefWidth(100);
        button.setPrefHeight(30);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                Evangelion Unit = getCurrentEvangelion();
                if (Unit != null) {
                    if (Unit.state.Fate > 0) {
                        Unit.SetUsedGuard(false);
                        StateEffect bonus = new StateEffect("FateDefence");
                        bonus.Condition = StateEffect.ExpirationCondition.POTENTIAL;
                        Unit.AddEffect(bonus);
                        Unit.state.Fate--;
                        UpdateUnitLabels();
                    }
                }
            }
        });
        return button;
    }

    private EvaButton createEnablerButton(String name){
        EvaButton button = new EvaButton(name);
        button.setPrefWidth(100);
        button.setPrefHeight(30);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                Evangelion Unit2 = getCurrentEvangelion();
                BaseUnit Unit = getUnitFromPlayer(((Attack) CurrentState.Action).Defender);
                if (Unit2 != null) {
                    if (Unit2.state.Fate > 0) {
                        Unit.SetUsedGuard(false);
                        StateEffect bonus = new StateEffect("FateDefence");
                        bonus.Condition = StateEffect.ExpirationCondition.POTENTIAL;
                        Unit.AddEffect(bonus);
                        Unit2.state.Fate--;
                        UpdateUnitLabels();
                    }
                }
            }
        });
        return button;
    }


    private void createWoundSubScene() {
        WoundSubScene = new DisplaySubSpace(560, 40, 400, 200, SizeDelta);
        gamePane.getChildren().add(WoundSubScene);
        WoundLabel = new EvaLabel("");
        WoundLabel.SetPosition(50,0);
        EvaButton RedWoundButton = createRedundantOrgansButton("Reduce Wound");
        RedWoundButton.setPosition(50, 30);
        WoundSubScene.getPane().getChildren().add(RedWoundButton);
        WoundSubScene.getPane().getChildren().add(WoundLabel);
    }

    boolean RedOrgans = false;
    public EvaButton RedOrgansButton;
    private EvaButton createRedundantOrgansButton(String name){
        RedOrgansButton = new EvaButton(name);
        RedOrgansButton.setPrefWidth(80);
        RedOrgansButton.setPrefHeight(30);
        RedOrgansButton.setOnAction(new EventHandler<ActionEvent>() {
             @Override
             public void handle(ActionEvent event){
                BaseUnit unit = getCurrentUnit();
                if (unit instanceof Evangelion eva && !eva.UsedRedOrgans()) {
                    RedOrgans = !RedOrgans;
                    if (RedOrgans) WoundLabel.setText(ReducedWound.Name); else WoundLabel.setText(NewWound.Name);
                }
             }
        });
        return RedOrgansButton;
    }


    private EvaButton createAttackRollButton(String name){
        EvaButton button = new EvaButton(name);
        button.setPrefWidth(80);
        button.setPrefHeight(30);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                if (!AttackRollCanUse) return;
                EndTurnButton.setText("Progress");
                DebugLabel.setText("1");
                BaseUnit Attacker = getCurrentUnit();
                DebugLabel.setText("2");
                int WeaponDN = CurrentChosenWeapon.getDiceNumber();
                int WeaponDS = CurrentChosenWeapon.getDiceStrength();
                int WeaponP = CurrentChosenWeapon.getPower();
                DebugLabel.setText("3");
                if (CurrentChosenWeapon.isGauss() && !AttackTestRollCanUse) {
                    assert Attacker != null;
                    WeaponP += getDOSMax(Integer.parseInt(AttackTestRollLabel.getText()), Attacker.getAccuracy(), 4);
                }
                DebugLabel.setText("4");
                int S = Attacker.getUnitStrength(CurrentChosenWeapon);
                DebugLabel.setText("5");
                int AttackSum = 0;
                for (int k = 0; k < WeaponDN; k++) {
                    DebugLabel.setText("5-"+k);
                    int diceAdd = RandomGenerator.nextInt(WeaponDS) + 1;
                    if (CurrentChosenWeapon.isProven()) {
                        int half = (int)Math.ceil(WeaponDS*0.5f);
                        diceAdd = Math.max(half, diceAdd);
                    }
                    AttackSum += diceAdd;
                }
                DebugLabel.setText("6");
                AttackSum+=WeaponP+S;
                AttackRollLabel.setText(""+AttackSum);
                AttackRollCanUse = false;
                DebugLabel.setText("7");
            }
        });
        return button;
    }


    private EvaButton createAttackRollMaxButton(String name){
        EvaButton button = new EvaButton(name);
        button.setPrefWidth(80);
        button.setPrefHeight(30);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                if (!AttackRollCanUse || !CurrentChosenWeapon.isExplosive() || (CurrentChosenWeapon.isExplosive() && CurrentChosenWeapon.getCurrentAmmo() < 1)) return;
                EndTurnButton.setText("Progress");
                BaseUnit Attacker = getCurrentUnit();
                int WeaponDN = CurrentChosenWeapon.getDiceNumber();
                int WeaponDS = CurrentChosenWeapon.getDiceStrength();
                int WeaponP = CurrentChosenWeapon.getPower();
                if (CurrentChosenWeapon.isGauss() && !AttackTestRollCanUse) {
                    assert Attacker != null;
                    WeaponP += getDOSMax(Integer.parseInt(AttackTestRollLabel.getText()), Attacker.getAccuracy(), 4);
                }
                CurrentChosenWeapon.DecreaseAmmo(1);
                int S = Attacker.getUnitStrength(CurrentChosenWeapon);
                int AttackSum = 0;
                AttackSum+= WeaponDS*WeaponDN;
                AttackSum+=WeaponP+S;
                AttackRollLabel.setText(""+AttackSum);
                AttackRollCanUse = false;
            }
        });
        return button;
    }


    public int getDOSMax(int Test, int TargetNumber, int max){
        if (Test < TargetNumber) {
            int Multiple = TargetNumber - Test;
            int x = (int) Math.floor(Multiple*0.1f);
            return Math.min(x, max);
        }
        return 0;
    }



    private EvaButton createPreUseLineAreaSwitchButton(String name){
        EvaButton button = new EvaButton(name);
        button.setPrefWidth(80);
        button.setPrefHeight(30);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                ATPower = null;
                WeaponCheck();
                Weapon currentWeapon = CurrentChosenWeapon;
                if (currentWeapon.isMaser()) MaserN2Switch();
                else if (currentWeapon.getTrueLine() && currentWeapon.getTrueArea() > -1) {
                    if (((currentWeapon.isN2Shell() && currentWeapon.getLine()) || ((currentWeapon.isMaser()) && currentWeapon.getArea() > -1)) && currentWeapon.N2OrMaserUsed) currentWeapon.switchN2OrMaser();
                    currentWeapon.lineswitch = !currentWeapon.lineswitch;
                    UpdateAttackTestLabels();
                    UpdateCurrentLables();
                    UpdatePlayerView();
                    ResetArrow();
                    setEndTurnButtonBasedOnAmmoAndStamina();
                    ClickedSector = null;
                }
            }
        });
        return button;
    }

    private EvaButton createDevineStrengthButton(String name, List<EvaButton> menu){
        EvaButton button = new EvaButton(name);
        button.setPrefWidth(100);
        menu.add(button);
        button.setPrefHeight(30);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                BaseUnit unit = getCurrentUnit();
                ATPower = null;
                WeaponCheck();
                if (getCurrentUnit() instanceof Evangelion eva && eva.UsedAngelicCore()) return;
                CurrentChosenWeapon.N2OrMaserUsed = false;
                CurrentChosenWeapon.lineswitch = false;
                CurrentChosenWeapon.DevineStrengthSwitch();
                checkPotentialAttackEffect();
                UpdateAttackTestLabels();
                UpdateCurrentLables();
                UpdatePlayerView();
                ResetArrow();
                setEndTurnButtonBasedOnAmmoAndStamina();
                ClickedSector = null;
                }
        });
        return button;
    }

    private EvaButton createPreUseTechnologyButton(String name){
        EvaButton button = new EvaButton(name);
        button.setPrefWidth(80);
        button.setPrefHeight(30);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                ATPower = null;
                WeaponCheck();
                MaserN2Switch();
            }
        });
        return button;
    }

    private void MaserN2Switch() {
        Weapon currentWeapon = CurrentChosenWeapon;
        if (((currentWeapon.isN2Shell() && currentWeapon.getLine()) || ((currentWeapon.isMaser()) && currentWeapon.getArea() > -1)) && currentWeapon.N2OrMaserUsed) currentWeapon.switchN2OrMaser();
        if ((currentWeapon.isN2Shell() && !currentWeapon.getLine()) || currentWeapon.isMaser()) {
            int ammo = getAmmoCost();
            boolean k = currentWeapon.canAttackAmmo(ammo);
            currentWeapon.switchN2OrMaser();
            ammo = getAmmoCost();
            boolean k2 = currentWeapon.canAttackAmmo(ammo);
            boolean changed = k != k2;
            if (currentWeapon.isMaser()) {
                currentWeapon.lineswitch = currentWeapon.getTrueLine();
                ResetArrow();
                setEndTurnButtonBasedOnAmmoAndStamina();
                ClickedSector = null;
            }
            UpdateAttackTestLabels();
            UpdateCurrentLables();
            UpdatePlayerView();
            if (changed) {
                ResetArrow();
                ClickedSector = null;
                setEndTurnButtonBasedOnAmmoAndStamina();
            }
            if (currentWeapon.getArea() > -1) {
                ShowAreaAttack(); }
        }
    }


    private EvaButton createTechnologyButton(String name){
        EvaButton button = new EvaButton(name);
        button.setPrefWidth(80);
        button.setPrefHeight(30);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                if (!AttackRollCanUse) return;
                Weapon currentWeapon = CurrentChosenWeapon;
                if (currentWeapon.isPolythermic()) {
                EndTurnButton.setText("Progress");
                currentWeapon.Overheat(!currentWeapon.isOverheating());
                UpdateAttackTestLabels();
                UpdateCurrentLables();
                }
                if (currentWeapon.isSuperconductive()) {
                    if (TechnologyLabel.getText().equals("Current debuff: -10 to next Attack Test")) {
                        TechnologyLabel.setText("Current debuff: -10 to next Guard Test");
                    } else TechnologyLabel.setText("Current debuff: -10 to next Attack Test");
                }
            }
        });
        return button;
    }

    private EvaButton createWPFluxButton(String name){
        EvaButton button = new EvaButton(name);
        button.setPrefWidth(80);
        button.setPrefHeight(30);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                if (!AttackRollCanUse) return;
                if (getCurrentUnit().getATP() == 0) return;
                if (getCurrentUnit().hasUpgrade("Weapon Flux")) {
                    button.setText("Weapon Flux");
                    getCurrentUnit().setATP(0);
                    StateEffect effect = new StateEffect("WPFlux");
                    effect.AttackStrengthDelta = 2;
                    effect.RangedStrengthDelta = 2;
                    effect.Condition = StateEffect.ExpirationCondition.POTENTIAL;
                    getCurrentUnit().AddEffect(effect);
                } else button.setText("No");
            }
        });
        return button;
    }

    private EvaButton createAttackRollTestButton(String name){
        EvaButton button = new EvaButton(name);
        button.setPrefWidth(80);
        button.setPrefHeight(30);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                if (!AttackTestRollCanUse) return;
                EndTurnButton.setText("Progress");
                int f = RandomGenerator.nextInt(1, 101);
                if (getCurrentUnit().hasUpgrade("Bloodthirst") && ((CurrentAction.equals("AtkOfOp") || CurrentSubAction.equals("Basic Attack")))) {
                    f = Math.min(f, getCurrentUnit().getAccuracy()-1);
                }
                String S = ""+f;
                AttackTestRollLabel.setText(S);
                AttackTestRollCanUse = false;
                if (CurrentChosenWeapon.isGauss()) {
                    if (AttackRollCanUse) UpdateAttackTestLabels();
                    else AttackTestRollLabel.setText(""+(Integer.parseInt(AttackTestRollLabel.getText())+ getDOSMax(f, getCurrentUnit().getAccuracy(), 4)));
                }
            }
        });
        return button;
    }
    private EvaButton createGuardButton(String name){
        EvaButton button = new EvaButton(name);
        button.setPrefWidth(80);
        button.setPrefHeight(30);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                BaseUnit Unit = getCurrentUnit();
                if (Unit != null) {
                    if (Unit.UsedGuard()) return;
                    int f = RandomGenerator.nextInt(1, 101);
                    String S = ""+f;
                    DefenceTestRollLabel.setText(S);
                    Unit.SetUsedGuard(true);
                }
            }
        });
        return button;
    }
    private EvaButton createLayeredFieldButton(String name){
        EvaButton button = new EvaButton(name);
        button.setPrefWidth(80);
        button.setPrefHeight(30);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                BaseUnit Unit = getCurrentUnit();
                if (Unit != null) {
                   if (Unit.getATP() > 0) {
                       if (!Unit.getNameEffects().contains("LayeredField")) {
                           Unit.AddEffect(CommonEffects.LayeredField());
                           Unit.setATP(0);
                       }
                       UpdateCurrentLables();
                   }
                }
            }
        });
        return button;
    }


    private EvaButton createSwitchEdgeButton(String name){
        EvaButton button = new EvaButton(name);
        button.setPrefWidth(120);
        button.setPrefHeight(30);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                BaseUnit unit = getCurrentUnit();
                if (unit.NeedsDoubleEdgeCheck() == null) return;
                Weapon Edge = unit.NeedsDoubleEdgeCheck();
                Edge.swapTechnologies();
                UpdateUnitLabels();
                String S = Edge.Technology2.toString().toLowerCase();
                button.setText("switch to "+S);
                String S1 = Edge.Technology.toString().toLowerCase();
                TechnologySwitchLabel.setText("Current Technology: "+S1);
            }
        });
        return button;
    }

    private EvaButton createAskHelpButton(String name, TextField field){
        EvaButton button = new EvaButton(name);
        button.setPrefWidth(80);
        button.setPrefHeight(30);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                String name = field.getText();
                if (name.equals("GM") || name.equals(CurrentPlayer)) return;
                if (Players.contains(name)) {
                CurrentState.NextPlayer = name;
                CurrentState.Phase = "DefendHelp";
                    try {
                        EndTurn(false);
                    } catch (IOException | ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        return button;
    }



    private void createCurrentDisplaySubSpace() {
        CurrentEvangelionDisplay = new DisplaySubSpace(1, 350, 120, 600);
        gamePane.getChildren().add(CurrentEvangelionDisplay);
        CurrentEvangelionDisplay.moveSubScene();

        CurrentEvaStatsLabel.setLayoutX(0);
        CurrentEvaStatsLabel.setLayoutY(0);
        int i = 20;
        for (EvaLabel lable : CurrentstatLabels) {
            lable.setLayoutX(0);
            lable.setLayoutY(i);
            i+=20;
            CurrentEvangelionDisplay.getPane().getChildren().add(lable);
        }

        i+=330;
        CurrentEvangelionDisplay2 = new DisplaySubSpace(1, i, 800, 200);
        gamePane.getChildren().add(CurrentEvangelionDisplay2);
        CurrentEvangelionDisplay2.moveSubScene();
        i =0 ;
        for (EvaLabel lable : CurrentstatLabels2) {
            lable.setLayoutX(i);
            lable.setLayoutY(0);
            i+=200;
            CurrentEvangelionDisplay2.getPane().getChildren().add(lable);
        }
    }
    private boolean isBlitzOrFA() {
        return CurrentSubAction.equals("Blitz") || CurrentSubAction.equals("Full Auto");
    }
    private boolean isBlitz() {
        return CurrentSubAction.equals("Blitz");
    }
    private boolean isThrow() {
        return CurrentSubAction.equals("Throw");
    }
    private boolean isFA() {
        return CurrentSubAction.equals("Full Auto");
    }
    private void AmmoCostRecalculate() {
        WeaponCheck();
        Weapon weapon = CurrentChosenWeapon;
        int cost = 1;
        if (!weapon.Ranged) cost = 0;
        else {
            if ((weapon.isMaser() || weapon.isN2Shell()) && weapon.getN2orMaserUsed()) cost++;
            if (isBlitzOrFA() && !weapon.isSpray()) cost++;
        }
        if (weapon.isATPower()) cost = 0;
        CurrentAttackAmmoCost = cost;
    }
    private void StaminaCostRecalculate() {
        int cost = 1;
        if (isBlitzOrFA() || CurrentSubAction.equals("Overwatch")) cost = 2;
        if (ATPower != null) cost = ATPower.StaminaCost;
        CurrentAttackStaminaCost = cost;
    }
    private int getAmmoCost() {
        AmmoCostRecalculate();
        return CurrentAttackAmmoCost;
    }
    private int getStaminaCost() {
        StaminaCostRecalculate();
        return CurrentAttackStaminaCost;
    }
    private void UpdateUnitLabels(){
        UpdateCurrentLables();
        if (LastClickedUnit == null) return;
        BaseUnit LastClickedUnit1 = LastClickedUnit;
        if (LastClickedUnit instanceof ChazaqielSummon summon) {
            LastClickedUnit1 = getUnitFromPlayer(summon.getCopyName());
        }
        if (LastClickedUnit1 instanceof Evangelion Eva) {
            EvaStatsLabel.setText(Eva.state.PlayerName+"'s stats:");
            AccuracyLabel.setText("Accuracy:  "+(Eva.getAccuracy()));
            AttackStrengthLabel.setText("Attack (S):  "+(Eva.getAttackStrength()));
            ToughnessLabel.setText("Toughness:  "+Eva.getToughness()+"/"+(Eva.getMaxToughness()));
            ArmorLabel.setText("Armor:  "+(Eva.getArmor()));
            ReflexesLabel.setText("Reflexes:  "+(Eva.getReflexes()));
            SpeedLabel.setText("Speed:  "+(Eva.getSpeed()));

            RequisitionORPenetrationLabel.setText("Requisition:  "+Eva.state.Requisition+"/"+Eva.type.RequisitionDisplay);
            Staminalabel.setText("Stamina:  "+Eva.getStamina()+"/2");
            WoundLevelLable.setText("Wound Level:  "+Eva.getWoundLevel()+"/4");


        return;
        }
        if (LastClickedUnit1 instanceof Angel angel) {
            AngelType AngType = angel.type;
            AngelState AngState = angel.state;
            EvaStatsLabel.setText("Angel's stats:");
            AccuracyLabel.setText("Accuracy:  "+(AngType.AccuracyDisplay+AngState.AccuracyDelta));
            AttackStrengthLabel.setText("M: "+(AngType.MeleeStrengthDisplay+AngState.MeleeStrengthDelta)+ " R: "+(AngType.RangedStrengthDisplay+AngState.RangedStrengthDelta));
            ToughnessLabel.setText("Toughness:  "+AngState.toughness+"/"+(AngType.ToughnessDisplay+AngState.MaxToughnessDelta));
            ArmorLabel.setText("Armor:  "+(AngType.ArmorDisplay+AngState.ArmorDelta));
            ReflexesLabel.setText("Reflexes:  "+(AngType.ReflexesDisplay+AngState.ReflexesDelta));
            SpeedLabel.setText("Speed:  "+(AngType.SpeedDisplay+AngState.SpeedDelta));
            RequisitionORPenetrationLabel.setText("Penetration:  "+(AngType.PenetrationDisplay+AngState.PenetrationDelta));
            Staminalabel.setText("Stamina:  "+AngState.Stamina+"/2");
            WoundLevelLable.setText("Wound Level:  "+AngState.woundlevel+"/4");
        }






    }
    private void UpdateCurrentLables(){
        if (getCurrentUnit() == null) return;

        WeaponCheck();
        Weapon Current = CurrentChosenWeapon;
        if (Current != null) {
        CurrentWeaponLabel.setText("Weapon: "+Current.Name);
        String k = (Current.Ranged ? "Ranged: ": "Melee ")+((getCurrentUnit().getMaxRange(Current) <= 1) ? "" : getCurrentUnit().getMinRange(Current) +"-"+getCurrentUnit().getMaxRange(Current));
        CurrentWeaponRangeLabel.setText(k);
        k = "Roll: " + Current.getDiceNumber() + "d" + Current.getDiceStrength();
        int p = Current.getPower();
        if (p != 0) {
            k = k + (p > 0 ? "+" : "") + p;
        }
        CurrentWeaponProfileLabel.setText("Power: "+k);
        k = Current.getAmmoCapacity() > 0 ? "Ammo: "+Current.getCurrentAmmo()+"/"+Current.getAmmoCapacity() : "";
        CurrentAmmoLabel.setText(k);
        CurrentWeaponTechnologyLabel.setText("Tech: "+Current.Technology.toString().toLowerCase()); }

        if (getCurrentUnit() instanceof ChazaqielSummon summon) {
            BaseUnit unit =  getUnitFromPlayer(summon.getCopyName());
            CurrentAccuracyLabel.setText("Stamina: " + summon.getStamina());
            CurrentAccuracyLabel.setText("Accuracy: "+(unit.getAccuracy()));
            if (unit instanceof Evangelion evangelion) {
                CurrentAttackStrengthLabel.setText("Attack (S): "+(evangelion.getAttackStrength())); }
            CurrentToughnessLabel.setText("Toughness: "+unit.getToughness()+"/"+(unit.getMaxToughness()));
            CurrentArmorLabel.setText("Armor: "+(unit.getArmor()));
            CurrentReflexesLabel.setText("Reflexes: "+(unit.getReflexes()));
            CurrentSpeedLabel.setText("Speed: "+(unit.getSpeed()));
            return;
        }


        if (getCurrentUnit() instanceof Evangelion unit) {
        SetUpCurrentOutfitLabel(unit);
        SetUpCurrentEffectsLabel(unit);
        unit.UpdateCalcWeapon(CurrentChosenWeapon);
        CurrentEvaStatsLabel.setText(unit.state.PlayerName+"'s stats:");
        CurrentAccuracyLabel.setText("Accuracy: "+(unit.getAccuracy()));
        CurrentAttackStrengthLabel.setText("Attack (S): "+(unit.getAttackStrength()));
        CurrentToughnessLabel.setText("Toughness: "+unit.getToughness()+"/"+(unit.getMaxToughness()));
        CurrentArmorLabel.setText("Armor: "+(unit.getArmor()));
        CurrentReflexesLabel.setText("Reflexes: "+(unit.getReflexes()));
        CurrentSpeedLabel.setText("Speed: "+(unit.getSpeed()));
        CurrentRequisitionOrPenLabel.setText("Requisition: "+unit.getRequisition());
        CurrentStamina.setText("Stamina: "+unit.getStamina()+"/2");
        CurrentFateDoomLabel.setText("Fate: "+unit.state.Fate+", Doom: "+unit.state.Doom);
        CurrentATPLabel.setText("ATP: "+unit.getATP());
        CurrentWoundLevelLabel.setText("Wound Level: "+unit.getWoundLevel()+"/4");
        }



        if (getCurrentUnit() instanceof Angel unit) {
            CurrentOutFitLabel.setText("");
            AngelType AngType = unit.type;
            AngelState AngState = unit.state;
            unit.UpdateCalcWeapon(CurrentChosenWeapon);
            CurrentEvaStatsLabel.setText("Angel stats:");
            CurrentAccuracyLabel.setText("Accuracy: "+(unit.getAccuracy()));
            CurrentAttackStrengthLabel.setText("M: "+(AngType.MeleeStrengthDisplay+AngState.MeleeStrengthDelta)+ " R: "+(AngType.RangedStrengthDisplay+AngState.RangedStrengthDelta));
            CurrentToughnessLabel.setText("Toughness: "+unit.getToughness()+"/"+(unit.getMaxToughness()));
            CurrentArmorLabel.setText("Armor: "+(unit.getArmor()));
            CurrentReflexesLabel.setText("Reflexes: "+(unit.getReflexes()));
            CurrentSpeedLabel.setText("Speed: "+(unit.getSpeed()));
            CurrentRequisitionOrPenLabel.setText("Penetration:  "+unit.getPenetration());
            CurrentStamina.setText("Stamina:  "+unit.getStamina()+"/2");
            CurrentATPLabel.setText("ATP: "+unit.getATP());
            CurrentWoundLevelLabel.setText("Wound Level:  "+unit.getWoundLevel()+"/5"); }

    }


    private void Update(){
        UpdatePositions();
        UpdatePlayerView();
        UpdateUnitLabels();
        ResetArrow();
    }

    private void ResetArrow(){
        if (arrow != null) drawArrow(1001,1001,1000,1000);
    }

    private Evangelion getCurrentEvangelion(){
       if (Players.contains(CurrentPlayer)) {
       return EvangelionList.get(Players.indexOf(CurrentPlayer)); } else return null;
    }

    private ChazaqielSummon getCurrentSummon(){
       // System.out.println("Getting Player: ");
        if (SummonPlayers.contains(CurrentPlayer)) {
            ChazaqielSummon summon = SummonList.get(SummonPlayers.indexOf(CurrentPlayer));
         //   System.out.println("CurrentPlayer: "+CurrentPlayer+" Index: "+SummonPlayers.indexOf(CurrentPlayer)+" summon: "+summon.getPlayerName()+" player index: "+SummonPlayers.indexOf(summon.getPlayerName()));
            return summon;
        }
        else return null;
    }


    private BaseUnit getCurrentUnit() {
        if (Players.contains(CurrentPlayer)) return getCurrentEvangelion();
        if (CurrentPlayer.equals("GM")) return AngelList.get(0);
        if (SummonPlayers.contains(CurrentPlayer)) {
            return getCurrentSummon();
        }
        else return null;
    }

    private void WeaponCheck() {
        BaseUnit unit = getCurrentUnit();
        if (unit == null) return;
        List<Weapon> checkWeapons = new ArrayList<>(unit.GetWeaponList());
        if (ATPower != null) checkWeapons.add(ATPower.ATWeapon);
        if (unit instanceof Evangelion evangelion)
            for (WingLoadout wing : evangelion.state.Wings) {
                if (wing.isAttack() && wing.hasWeapon()) {
                    checkWeapons.add(wing.getItem());
                }
            }
        if ((CurrentChosenWeapon == null || WeaponCompareList(CurrentChosenWeapon, checkWeapons) == null)) {
            CurrentChosenWeapon = SwitchToNextWeapon(unit, CurrentChosenWeapon);
        }
        unit.UpdateCalcWeapon(CurrentChosenWeapon);
    }

    private void UpdatePlayerView(){
        BaseUnit unit = getCurrentUnit();
        GameBoard.UpdateBoardColors();
        if (CurrentAction.equals("Move")) {
            if (unit != null) {
            GameBoard.ShowPossibleMovementPositions(unit, CurrentSubAction);
            }
        }
        if (CurrentSubAction != null && CurrentSubAction.equals("Drop") && unit.canDrop(CurrentChosenWeapon)) {
                GameBoard.UpdateBoardColors();
                GameBoard.DrawSquare(unit.getX(), unit.getY(), 1, Color.WHEAT);
        }
        if (CurrentSubAction != null && CurrentSubAction.equals("Pick Up Left")) {
            GameBoard.UpdateBoardColors();
            if (!unit.getProhibitedActions().contains("ItemL"))
            for (WeaponObject item : WeaponsWorld) {
                if (EvaCalculationUtil.isAdjecent(unit.getX(), unit.getY(), item.getX(), item.getY())
                        && !(item.getWeapon().getHands() == 2 && unit.getProhibitedActions().contains("TwoHandL")))
                    GameBoard.DrawSquare(item.getX(), item.getY(), 1, Color.POWDERBLUE);
            }
        }
        if (CurrentSubAction != null && CurrentSubAction.equals("Pick Up Right")) {
            GameBoard.UpdateBoardColors();
            if (!unit.getProhibitedActions().contains("ItemR"))
                for (WeaponObject item : WeaponsWorld) {
                    if (EvaCalculationUtil.isAdjecent(unit.getX(), unit.getY(), item.getX(), item.getY())
                            && !(item.getWeapon().getHands() == 2 && unit.getProhibitedActions().contains("TwoHandR")))
                        GameBoard.DrawSquare(item.getX(), item.getY(), 1, Color.POWDERBLUE);
                }
        }
        if (CurrentSubAction != null && WeaponsNamesList.contains(CurrentSubAction)) {
            GameBoard.ShowSupportStructures();
        }
        if (CurrentAction.equals("Attack"))  {
            if (unit != null) {
            WeaponCheck();
            checkPotentialAttackEffect();
            assert CurrentChosenWeapon != null;
                if (CurrentChosenWeapon.isWeapon()) GameBoard.ShowPossibleAttackRange(unit, CurrentChosenWeapon); else GameBoard.UpdateBoardColors();
            }
        }
        GameBoard.fogSectors(getCurrentUnit(), FogNumber);
        UpdateVisible();
    }

    public boolean isFogged(Sector sector){
        if (FogNumber < 0) return false;
        Evangelion eva = getCurrentEvangelion();
        if (eva == null) return false;
        return !EvaCalculationUtil.isInRange(eva, sector, FogNumber);
    }


    private EvaMenuSubScene amongusventedscene = new EvaMenuSubScene(6, 40, SizeDelta);
    private EvaMenuSubScene createMovesSubScene(Evangelion Eva) {
        List<EvaButton> EvangelionsMenuButtons = new ArrayList<>();
        EvaMenuSubScene ActionsSubScene = new EvaMenuSubScene(4, 40, SizeDelta);
        gamePane.getChildren().add(ActionsSubScene);

        createActionTypeButton("None", EvangelionsMenuButtons, null);

        EvaMenuSubScene OtherActions = new EvaMenuSubScene(5, 40, SizeDelta);
        EvaMenuSubScene AttackActions = new EvaMenuSubScene(5, 40, SizeDelta);
        EvaMenuSubScene MovementActions = new EvaMenuSubScene(5, 40, SizeDelta);


        gamePane.getChildren().add(OtherActions);
        gamePane.getChildren().add(AttackActions);
        gamePane.getChildren().add(MovementActions);


        EvaMenuSubScene SimpleAction = new EvaMenuSubScene(6, 40, SizeDelta);

        EvaMenuSubScene WeaponRequisition = new EvaMenuSubScene(6, 40, SizeDelta);
        EvaMenuSubScene WingInteraction = new EvaMenuSubScene(6, 40, SizeDelta);

        List<EvaButton> WeaponRequisitionMenuButtons = new ArrayList<>();
        for (String s : WeaponsNamesList) {
            createActionSubTypeButton(s, WeaponRequisitionMenuButtons, null, false);
        }



        gamePane.getChildren().add(SimpleAction);
        gamePane.getChildren().add(WeaponRequisition);
        gamePane.getChildren().add(WingInteraction);



        createActionTypeButton("Other", EvangelionsMenuButtons, OtherActions);
        createActionTypeButton("Attack", EvangelionsMenuButtons, AttackActions);
        createActionTypeButton("Move", EvangelionsMenuButtons, MovementActions);


        List<EvaButton> OtherMenuButtons = new ArrayList<>();
        List<EvaButton> AttackMenuButtons = new ArrayList<>();
        List<EvaButton> MoveMenuButtons = new ArrayList<>();




        List<EvaButton> SimpleActionMenu = new ArrayList<>();



        createActionSubTypeButton("Aim", OtherMenuButtons, null, true);
        createActionSubTypeButton("Defend", OtherMenuButtons, null, true);
        createActionSubTypeButton("Drop Item", OtherMenuButtons, null, true);
        createActionSubTypeButton("Discard Item", OtherMenuButtons, null, true);
        createActionSubTypeButton("Request Item", OtherMenuButtons, WeaponRequisition, false);

        if (!Eva.type.CurrentATPowers.isEmpty()) {
        EvaMenuSubScene ATPowersScene = new EvaMenuSubScene(5, 40, SizeDelta);
        gamePane.getChildren().add(ATPowersScene);
        createActionTypeButton("ATPower", EvangelionsMenuButtons, ATPowersScene);
        List<EvaButton> ATPowers = new ArrayList<>();
        for (ATPower ATPower : Eva.type.CurrentATPowers) {
            createATPowerButton(ATPower.Name, ATPowers, null, true, ATPower);
        }
        SetUpMenuList(ATPowersScene.getPane(), ATPowers, 5, 0);
        }

        createActionSubTypeButton("Reload", OtherMenuButtons, null, true);
     //   createActionSubTypeButton("Maintain Grab", OtherMenuButtons);
    //    createActionSubTypeButton("Maintain Power", OtherMenuButtons);


        createActionSubTypeButton("Pick Up Right", SimpleActionMenu, null, false);
        createActionSubTypeButton("Pick Up Left", SimpleActionMenu, null, false);
        createActionSubTypeButton("Hand Switch", SimpleActionMenu, null, false);
        createActionSubTypeButton("Wing Switch", SimpleActionMenu, null, false);

        createActionSubTypeButton("Simple Action", OtherMenuButtons, SimpleAction, false);

        createActionSubTypeButton("Basic Attack", AttackMenuButtons, null, true);
        createActionSubTypeButton("Blitz", AttackMenuButtons, null, true);
        createActionSubTypeButton("Full Auto", AttackMenuButtons, null, true);
        if (Eva.hasUpgrade("Overwatch")) createActionSubTypeButton("Overwatch", AttackMenuButtons, null, true);
        createActionSubTypeButton("Grab", AttackMenuButtons, null, true);
        createActionSubTypeButton("Throw", AttackMenuButtons, null, true);

        createActionSubTypeButton("Run", MoveMenuButtons, null, true);
        if (Eva.hasUpgrade("Reposition")) createActionSubTypeButton("Reposition", MoveMenuButtons, null, true);
        createActionSubTypeButton("Maneuver", MoveMenuButtons, null, true);
        createActionSubTypeButton("Stand", MoveMenuButtons, null, true);
        createActionSubTypeButton("Take Cover", MoveMenuButtons, null, true);




        if (Eva.type.CurrentUpgradeNames.contains("AngelicCore")) createDevineStrengthButton("Angelic Core", EvangelionsMenuButtons);
        createSwitchWeaponButton("SwitchWeapon", EvangelionsMenuButtons);




        EvaButton tech = createPreUseTechnologyButton("N2/Maser");
        EvaButton switchbutton = createPreUseLineAreaSwitchButton("Line/Area");
        EvangelionsMenuButtons.add(tech);
        EvangelionsMenuButtons.add(switchbutton);

        SetUpMenuList(ActionsSubScene.getPane(), EvangelionsMenuButtons, 5, 0);

        SetUpMenuList(OtherActions.getPane(),OtherMenuButtons, 5, 0);
        SetUpMenuList(AttackActions.getPane(), AttackMenuButtons, 5, 0);
        SetUpMenuList(MovementActions.getPane(), MoveMenuButtons, 5, 0);


        SetUpMenuList(WeaponRequisition.getPane(), WeaponRequisitionMenuButtons, 5, 0);
        SetUpMenuList(SimpleAction.getPane(), SimpleActionMenu, 5, 0);


        return ActionsSubScene;
    }


    private EvaMenuSubScene createAngelSubScene() {
        List<EvaButton> AngelMenuButtons = new ArrayList<>();

        EvaMenuSubScene AngelMovesSubScene = new EvaMenuSubScene(4, 40, SizeDelta);

        gamePane.getChildren().add(AngelMovesSubScene);

        createActionTypeButton("None", AngelMenuButtons, null);
        createActionTypeButton("Attack", AngelMenuButtons, null);
        createActionTypeButton("Move", AngelMenuButtons, null);
        createSwitchWeaponButton("SwitchWeapon", AngelMenuButtons);
        createDevineStrengthButton("Devine Strength", AngelMenuButtons);

        createSummonButton("Summon", AngelMenuButtons);
        createSplitButton("Split", AngelMenuButtons);
        createSummonStaminaButton("Stamina", AngelMenuButtons);
        createSwitchSummonButton("Switch", AngelMenuButtons);


        SetUpMenuList(AngelMovesSubScene.getPane(), AngelMenuButtons, 5, 0);


        return AngelMovesSubScene;
    }




    private EvaMenuSubScene createGMSubScene() {

        List<EvaButton> GmMenuButtons = new ArrayList<>();



        EvaMenuSubScene ActionsSubScene = new EvaMenuSubScene(4, 40, SizeDelta);
        EvaMenuSubScene DramaSubScene = new EvaMenuSubScene(5, 40, SizeDelta);
        EvaMenuSubScene NextTurnSubScene = new EvaMenuSubScene(5, 40, SizeDelta);

        gamePane.getChildren().add(ActionsSubScene);

        gamePane.getChildren().add(DramaSubScene);
        gamePane.getChildren().add(NextTurnSubScene);
        createSceneButton("Drama", GmMenuButtons,DramaSubScene);
        GMScreen = createGMScreenScene();




        createSceneButton("NextTurn", GmMenuButtons, NextTurnSubScene);



        NextTurnButtons = new ArrayList<>();
        for (String s : Players) {
            createSetNextTurnButton(s, NextTurnButtons);
        }

      List<EvaButton>  DramaButtons = new ArrayList<>();
        createSetEffectButton("Stigmata", DramaButtons, 2, -10, -10, 0);
        createSetEffectButton("Bruised", DramaButtons, 4, -10, -10, 0);
        createSetEffectButton("Anti-Stigmata", DramaButtons, 0, 10, 10, 0);
        createSetEffectButton("HesitationGuard", DramaButtons, 1, 0, -10, 0);
        createSetEffectButton("HesitationAttack", DramaButtons, 2, -10, 0, 0);
        createSetEffectButton("Unsteady Footing", DramaButtons, 1, 0, 0, -1);
        SetUpMenuList(ActionsSubScene.getPane(), GmMenuButtons, 5, 0);
        SetUpMenuList(NextTurnSubScene.getPane(), NextTurnButtons, 5, 0);
        SetUpMenuList(DramaSubScene.getPane(), DramaButtons, 5, 0);

        return ActionsSubScene;
    }

    private DisplaySubSpace createGMScreenScene() {
        DisplaySubSpace DMScreen = new DisplaySubSpace(860, 430, 500, 500, SizeDelta+100);
        gamePane.getChildren().add(DMScreen);
        List<EvaButton> dmbuttonlist = new ArrayList<>();
        dmbuttonlist.add(createDMButton("None"));
        dmbuttonlist.add(createDMButton("Delete"));
        dmbuttonlist.add(createDMButton("SupportSector"));
        dmbuttonlist.add(createDMButton("Effect"));
        dmbuttonlist.add(createDMButton("ApplyAll"));
        SetUpMenuList(DMScreen.getPane(), dmbuttonlist, 5, 0);
        int x = 100;
        int dx = 50;
        int dx2 = 50;
        int y = 0;
        int dy = 40;
        EvaLabel nameLabel = new EvaLabel("Name: ");
        nameLabel.SetPosition(x, y);
        GMEffectName.setLayoutX(x + dx);
        GMEffectName.setLayoutY(y);
        y += dy;

        EvaLabel accuracyLabel = new EvaLabel("Accuracy: ");
        accuracyLabel.SetPosition(x, y);
        GMEffectAccuracy.setLayoutX(x + dx);
        GMEffectAccuracy.setLayoutY(y);
        y += dy;

        EvaLabel reflexesLabel = new EvaLabel("Reflexes: ");
        reflexesLabel.SetPosition(x, y);
        GMEffectReflexes.setLayoutX(x + dx+5);
        GMEffectReflexes.setLayoutY(y);
        y += dy;

        EvaLabel armorLabel = new EvaLabel("Armor: ");
        armorLabel.SetPosition(x, y);
        GMEffectArmor.setLayoutX(x + dx);
        GMEffectArmor.setLayoutY(y);
        y += dy;

        EvaLabel attackStrengthLabel = new EvaLabel("Attack Strength: ");
        attackStrengthLabel.SetPosition(x, y);
        GMEffectAttackStrength.setLayoutX(x + dx+dx2);
        GMEffectAttackStrength.setLayoutY(y);
        y += dy;

        EvaLabel rangedStrengthLabel = new EvaLabel("Ranged Strength: ");
        rangedStrengthLabel.SetPosition(x, y);
        GMEffectRangedStrength.setLayoutX(x + dx+dx2);
        GMEffectRangedStrength.setLayoutY(y);
        y += dy;

        EvaLabel speedLabel = new EvaLabel("Speed: ");
        speedLabel.SetPosition(x, y);
        GMEffectSpeed.setLayoutX(x + dx);
        GMEffectSpeed.setLayoutY(y);
        y += dy;

        EvaLabel maxToughnessLabel = new EvaLabel("Max Toughness: ");
        maxToughnessLabel.SetPosition(x, y);
        GMEffectMaxToughness.setLayoutX(x + dx+dx2);
        GMEffectMaxToughness.setLayoutY(y);
        y += dy;

        EvaLabel speedChangeLabel = new EvaLabel("Speed Change: ");
        speedChangeLabel.SetPosition(x, y);
        GMEffectSpeedChange.setLayoutX(x + dx+dx2);
        GMEffectSpeedChange.setLayoutY(y);
        y += dy;

        EvaLabel penetrationLabel = new EvaLabel("Penetration: ");
        penetrationLabel.SetPosition(x, y);
        GMEffectPenetration.setLayoutX(x + dx+dx2-25);
        GMEffectPenetration.setLayoutY(y);
        y += dy;

        EvaLabel expirationLabel = new EvaLabel("Expiration: ");
        expirationLabel.SetPosition(x, y);
        GMEffectExpiration.setLayoutX(x + dx+dx2-25);
        GMEffectExpiration.setLayoutY(y);
        y += dy;

        EvaLabel prohibitedLabel = new EvaLabel("Prohibited: ");
        prohibitedLabel.SetPosition(x, y);
        GMEffectProhibited.setLayoutX(x + dx+dx2-25);
        GMEffectProhibited.setLayoutY(y);
        y += dy;

        EvaLabel expirationConditionLabel = new EvaLabel("Expiration Condition: ");
        expirationConditionLabel.SetPosition(x, y);
        GMEffectExpirationCondition.setLayoutX(x + dx+dx2+25);
        GMEffectExpirationCondition.setLayoutY(y);



        EvaLabel rollingLabel = new EvaLabel("Roll");
        EvaLabel d = new EvaLabel("d");
        x = x+dx*5;
        y = 0;
        rollingLabel.SetPosition(x+25, y);
        y+=dy;
        d.SetPosition(x+43, y+2);
        TextField Dice = new TextField("2");
        TextField Dice2 = new TextField("6");
        Dice.setPrefWidth(40);
        Dice.setLayoutX(x);
        Dice.setLayoutY(y);
        Dice2.setPrefWidth(40);
        Dice2.setLayoutX(x+50);
        Dice2.setLayoutY(y);

        EvaButton roll = new EvaButton("roll");
        roll.setPrefHeight(30);
        roll.setPosition(x+25, y+dy);
        roll.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int dice = 0;
                for (int k = 0; k < Integer.parseInt(Dice.getText()); k++) {
                    dice += RandomGenerator.nextInt(1, Integer.parseInt(Dice2.getText())+1);
            }
                rollingLabel.setText(""+dice);
            }
        });

        y+=2*dy;

        EvaLabel fogLabel = new EvaLabel("Fog ");
        fogLabel.SetPosition(x, y);
        GMFog.setLayoutX(x+50);
        GMFog.setLayoutY(y);

        y+=dy;

        EvaLabel damageLabel = new EvaLabel("Damage: ");
        damageLabel.SetPosition(x, y);
        GMDamage.setLayoutX(x+50);
        GMDamage.setLayoutY(y);

        y+=dy;

        EvaLabel standLabel = new EvaLabel("Stand: ");
        standLabel.SetPosition(x, y);
        GMStand.setLayoutX(x+50);
        GMStand.setLayoutY(y);

        y+=dy;

        EvaLabel doomcostlabel = new EvaLabel("Doom Cost: ");
        doomcostlabel.SetPosition(x, y);
        GMCostDoom.setLayoutX(x+58);
        GMCostDoom.setLayoutY(y);

        y+=dy;
        DMScreen.getPane().getChildren().addAll(
                rollingLabel, Dice, Dice2, roll, d,
                nameLabel, GMEffectName, fogLabel, GMFog,
                accuracyLabel, GMEffectAccuracy, standLabel, GMStand, damageLabel, GMDamage,
                reflexesLabel, GMEffectReflexes,
                armorLabel, GMEffectArmor, GMCostDoom,
                attackStrengthLabel, GMEffectAttackStrength,
                rangedStrengthLabel, GMEffectRangedStrength,
                speedLabel, GMEffectSpeed,
                maxToughnessLabel, GMEffectMaxToughness,
                speedChangeLabel, GMEffectSpeedChange,
                penetrationLabel, GMEffectPenetration,
                expirationLabel, GMEffectExpiration, expirationConditionLabel,
                doomcostlabel, GMEffectExpirationCondition,
                GMEffectProhibited, prohibitedLabel
        );



        return DMScreen;
    }


    private EvaButton createDMButton(String name) {
        EvaButton button = new EvaButton(name);
        button.setPrefHeight(30);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!Objects.equals(CurrentAction, name)) {
                    ResetArrow();
                    CurrentAction = "GMApply";
                    CurrentSubAction = name;
                    if (name.equals("ApplyAll")) EndTurnButton.setText("Apply");
                    else EndTurnButton.setText("End Turn");
                    Update();
                }
            }
        });
        return button;
    }

    private EvaButton createSummonButton(String name, List<EvaButton> menu) {
        EvaButton button = new EvaButton(name);
        button.setPrefHeight(30);
        button.setPrefWidth(90);
        menu.add(button);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!Objects.equals(CurrentAction, name)) {
                    ResetArrow();
                    CurrentAction = "Summon";
                    EndTurnButton.setText("Summon");
                    Update();
                }
            }
        });
        return button;
    }
    private EvaButton createSplitButton(String name, List<EvaButton> menu) {
        EvaButton button = new EvaButton(name);
        button.setPrefHeight(30);
        button.setPrefWidth(90);
        menu.add(button);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!Objects.equals(CurrentAction, name)) {
                    ResetArrow();
                    CurrentAction = "Split";
                    EndTurnButton.setText("Split");
                    Update();
                }
            }
        });
        return button;
    }



    private EvaButton createSummonStaminaButton(String name, List<EvaButton> menu) {
        EvaButton button = new EvaButton(name);
        button.setPrefHeight(30);
        button.setPrefWidth(90);
        menu.add(button);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!Objects.equals(CurrentAction, name)) {
                    ResetArrow();
                    if (getCurrentUnit().getStamina() > 0) {
                    getCurrentUnit().setStamina(getCurrentUnit().getStamina() -1 );
                    int size = SummonList.size();
                    for (int i = 0; i < 3+AngelList.get(0).getWoundLevel(); i++) {
                        ChazaqielSummon s = SummonList.get(RandomGenerator.nextInt(0, size));
                        s.setStamina(Math.min(s.getStamina()+1,2));
                    }
                    Update();
                    }
                }
            }
        });
        return button;
    }


    private EvaButton createSwitchSummonButton(String name, List<EvaButton> menu) {
        EvaButton button = new EvaButton(name);
        button.setPrefHeight(30);
        button.setPrefWidth(90);
        menu.add(button);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!Objects.equals(CurrentAction, name)) {
                    ResetArrow();
                    for (ChazaqielSummon summon : SummonList) {
                            if (summon.getStamina() > 0){
                             //   System.out.println("Summon Checked list: "+summon.getPlayerName()+" #"+SummonList.indexOf(summon));
                                String s = summon.getPlayerName();
                                CurrentPlayer = s;
                                PlayerName.setText(s);
                                CurrentState.NextPlayer = s;
                                ResetAction();
                                ResetArrow();
                                ApplyPlayer();
                                try {
                                    EndTurn(false);
                                } catch (IOException | ClassNotFoundException ignored) {
                                }
                                break;
                            }
                        }
                        Update();
                    }
            }
        });
        return button;
    }

    private EvaButton createActionTypeButton(String name, List<EvaButton> menu, EvaMenuSubScene subScene) {
        EvaButton button = new EvaButton(name);
        button.setPrefHeight(30);
        menu.add(button);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ATPower = null;
                WeaponCheck();
                if (!Objects.equals(CurrentAction, name)) {
                ResetArrow();
                ResetSwap();
                UpdateSwapLabel();

                if (CurrentAction.equals("AtkOfOp")) {
                    checkPotentialAttackEffect();
                    UpdatePlayerView();
                    if (!name.equals("Attack")) {
                        EndTurnButton.setText("End Turn");
                    }
                    return;
                }


                CurrentAction = name;
                //Mawrak's edits
                PreviousAttack = "Basic Attack";
                PreserveProgress = false;
                System.out.println("createActionTypeButton pressed");

                if (name.equals("Move")) {
                    CurrentSubAction = "Run";
                }
                if (name.equals("Other")) {
                    CurrentSubAction = "";
                }
                if (name.equals("None")) {
                        CurrentSubAction = "";
                }
                setEndTurnButtonBasedOnAmmoAndStamina();
                if (CurrentAction.equals("Attack")) {
                    checkPotentialAttackEffect();
                    CurrentSubAction = "Basic Attack";
                }
                checkPotentialAttackEffect();
                UpdatePlayerView();
                if (subScene != null) {
                    showSubScene(subScene);
                }
                }
            }
        });
        return button;
    }

    private void createChooseWeaponButton(String name, List<EvaButton> menu) {
        EvaButton button = new EvaButton(name);
        button.setPrefHeight(30);
        menu.add(button);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Evangelion eva = getCurrentEvangelion();
                    if (eva != null) {
                        if (eva.isSetUp())
                        {
                            CurrentAction = "SetUp";
                            EndTurnButton.setText("End Turn");
                            return;
                        }
                    CurrentAction = "SetUp";
                    CurrentSubAction = name;
                    Weapon w = EvaSaveUtil.ReadStringWeapon(name);
                    if (w.getCost() > eva.getRequisition()) EndTurnButton.setText("Too Expensive");
                    else if (eva.getSetUpLocationShortString().equals("Left") && !eva.getLeftWing().isSiegeFrame() && w.getHands() == 2 && !eva.getRightHandItem().isFree()) EndTurnButton.setText("No Hand");
                       else if (eva.getSetUpLocationShortString().equals("Left") && !w.isFree() && eva.getRightHandItem().getHands() == 2 && !eva.getRightWing().isSiegeFrame()) EndTurnButton.setText("No Hand");
                      else if (eva.getSetUpLocationShortString().equals("storage") && !(w.isSmall() || !w.isWeapon())) EndTurnButton.setText("Too Large");
                      else if (eva.getSetUpLocationShortString().equals("assault") && w.isAmmo()) EndTurnButton.setText("No Ammo");
                        else EndTurnButton.setText("Get Weapon");
                    }
                }  catch (IOException e) {
                throw new RuntimeException(e);
            }
            }
        });
    }
    private void createATPowerButton(String name, List<EvaButton> menu, EvaMenuSubScene subScene, boolean secret, ATPower power) {
        EvaButton button = new EvaButton(name);
        button.setPrefHeight(30);
        menu.add(button);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (subScene == null) {
                    if (!Objects.equals(CurrentSubAction, name)) {
                        if (secret) showSubScene(amongusventedscene);
                        CurrentAction = "Attack";
                        CurrentSubAction = "Basic Attack";
                        CurrentChosenWeapon = power.ATWeapon;
                        ATPower = power;
                        Update();
                    }
        }}});
    }



    private void createActionSubTypeButton(String name, List<EvaButton> menu, EvaMenuSubScene subScene, boolean secret) {
        EvaButton button = new EvaButton(name);
        button.setPrefHeight(30);
        menu.add(button);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ATPower = null;
                WeaponCheck();
                if (subScene == null) {
                if (!Objects.equals(CurrentSubAction, name)) {
                    if (secret) showSubScene(amongusventedscene);

                    checkPotentialAttackEffect();
                    UpdatePlayerView();
                    getCurrentUnit().UpdateCalcWeapon(CurrentChosenWeapon);



                    if (name.equals("Cant Use")) return;
                    if (name.equals("Aim") && getCurrentUnit().getNameEffects().contains(CommonEffects.AimEffect().Name)) return;
                    if (name.equals("Defend") && getCurrentUnit().getNameEffects().contains("RunDefend")) return;
                    if (name.equals("Stand") && !getCurrentUnit().getNameEffects().contains(CommonEffects.Prone().Name)) return;
                    if (getCurrentUnit().getProhibitedActions().contains(name)) {
                        button.setText("Cant Use");
                        return;
                    }
                    if (name.equals("Full Auto") && !CurrentChosenWeapon.Ranged) {
                        CurrentSubAction = "Basic Attack";
                        checkPotentialAttackEffect();
                        UpdatePlayerView();
                        return;
                    }
                    if (name.equals("Throw") && CurrentChosenWeapon.Ranged) {
                        CurrentSubAction = "Basic Attack";
                        checkPotentialAttackEffect();
                        UpdatePlayerView();

                        //Mawrak's edits
                        ResetArrow();
                        ClickedSector = null;


                        System.out.println("Throw selected");
                        PreserveProgress = false;
                        setEndTurnButtonBasedOnAmmoAndStamina();
                        return;

                    }

                    CurrentSubAction = name;

                    if (CurrentSubAction.equals("Hand Switch") || CurrentSubAction.equals("Wing Switch")) {
                        if (CurrentSubAction.equals("Hand Switch")) {
                            if (swapHand == null || swapHand.equals("Right Hand")) swapHand = "Left Hand"; else swapHand = "Right Hand";
                            CurrentSubAction = "Swap";
                        }
                        if (CurrentSubAction.equals("Wing Switch")) {
                            swapWing = getCurrentEvangelion().NextWingForSwap(swapWing);
                            CurrentSubAction = "Swap";
                        }
                        if (!getCurrentUnit().UsedTactical() || getCurrentUnit().getStamina() > 0) {
                        if (swapHand != null && swapWing != null) {
                            EndTurnButton.setText("Swap");
                            Evangelion eva = getCurrentEvangelion();
                            List<String> prohibited = eva.getProhibitedActions();
                            if (swapHand.equals("Right Hand") && ((prohibited.contains("TwoHandR") && swapWing.getItem().getHands() == 2) || (prohibited.contains("ItemR"))))   EndTurnButton.setText("No Hand");
                            if (swapHand.equals("Left Hand") && ((prohibited.contains("TwoHandL") && swapWing.getItem().getHands() == 2) || (prohibited.contains("ItemL"))))   EndTurnButton.setText("No Hand");
                        }} else EndTurnButton.setText("No Stamina");
                        UpdateSwapLabel();
                        return;
                    }


					
                    ResetSwap();
                    UpdateSwapLabel();


                    checkPotentialAttackEffect();
                    UpdatePlayerView();
					
					//Mawrak's edits
					
					if (name.equals("Basic Attack") || name.equals("Blitz"))
					{

						if (PreviousAttack != "Basic Attack" && PreviousAttack != "Blitz")
						{
							ResetArrow();
							ClickedSector = null;
							PreserveProgress = false;
						}
						else
						{
							PreserveProgress = true;
						}


                    }
					else
					{
						//Mawrak's edits
						ResetArrow();
						ClickedSector = null;
						PreserveProgress = false;
					}






                    if (name.equals("Drop Item")) CurrentSubAction = "Drop";


                    setEndTurnButtonBasedOnAmmoAndStamina();

                    if (name.equals("Aim") && (getCurrentUnit().getStamina() > 0 || !getCurrentUnit().UsedTactical())) {
                        if (!EndTurnButton.getText().equals("Aim")) {
                        EndTurnButton.setText("Aim");
                        } else {
                            EndTurnButton.setText("End Turn");
                        }
                    }

                    if (name.equals("Reload")) {
                        if (CurrentChosenWeapon.Ranged && CurrentChosenWeapon.getCurrentAmmo() < CurrentChosenWeapon.getAmmoCapacity()) {
                        if (getCurrentUnit().getStamina() < 1) EndTurnButton.setText("No Stamina");
                        else if (((Evangelion)getCurrentUnit()).AmmoChooser() != null) {
                            EndTurnButton.setText("Reload");
                        } else {
                            EndTurnButton.setText("No Ammo");
                        }
                        } else  EndTurnButton.setText("End Turn");
                    }


                    if (name.equals("Defend") && getCurrentUnit().getStamina() > 0) {
                        if (!EndTurnButton.getText().equals("Defend")) {
                            EndTurnButton.setText("Defend");
                        } else {
                            EndTurnButton.setText("End Turn");
                        }
                    }
                    if (name.equals("Stand") && getCurrentUnit().getStamina() > 0) {
                        if (!EndTurnButton.getText().equals("Stand")) {
                            EndTurnButton.setText("Stand");
                        } else {
                            EndTurnButton.setText("End Turn");
                        }
                    }

                    if (name.equals("Discard Item")) {
                        CurrentSubAction = "Discard";
                        if (getCurrentUnit().canDrop(CurrentChosenWeapon)) {
                            EndTurnButton.setText("Discard");
                        } else {
                            EndTurnButton.setText("End Turn");
                        }
                    }


                    checkPotentialAttackEffect();
                    UpdatePlayerView(); }

            }
             else {
                if (!Objects.equals(CurrentSubAction, name)) {
                    checkPotentialAttackEffect();
                    ResetArrow();
                    CurrentSubAction = name;
                    setEndTurnButtonBasedOnAmmoAndStamina();
                    if (CurrentSubAction.equals("Attack")) checkPotentialAttackEffect();
                    UpdatePlayerView();
                    showSubScene(subScene);
                }
            }
			//Mawrak's edits
			PreviousAttack = name;
			if (PreserveProgress && ClickedUnit != null && !getPlayerFromUnit(ClickedUnit).equals(CurrentPlayer))
			{
                    System.out.println("Preserve Progress");
					setEndTurnButtonBasedOnAmmoAndStamina("Progress");
			}
			
            }
        
		
		
		
		
		
		}
		);
    }



    private void createSpreadPatternButton(List<EvaButton> menu, StateEffect effect) {
        EvaButton button = new EvaButton(effect.Name);
        button.setPrefHeight(30);
        menu.add(button);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                EndTurnButton.setText("Apply Pattern");
                pattern = effect;
            }
        });
    }

    private void setEndTurnButtonBasedOnAmmoAndStamina(){
      setEndTurnButtonBasedOnAmmoAndStamina("End Turn");
    }
    private void setEndTurnButtonBasedOnAmmoAndStamina(String S){
        int ammo = getAmmoCost();
        if (CurrentChosenWeapon != null && CurrentAction.equals("Attack")) {
            if (getCurrentUnit() instanceof Evangelion eva){
                if (eva.getProhibitedActions().contains("AttackR") && (CurrentChosenWeapon.ParentWeapon != null && CurrentChosenWeapon.ParentWeapon.equals(eva.getRightHandItem())) || CurrentChosenWeapon.equals(eva.getRightHandItem())) {
                    EndTurnButton.setText("No Hand");
                }
                if (eva.getProhibitedActions().contains("AttackL") && (CurrentChosenWeapon.ParentWeapon != null && CurrentChosenWeapon.ParentWeapon.equals(eva.getLeftHandItem())) || CurrentChosenWeapon.equals(eva.getLeftHandItem())) {
                    EndTurnButton.setText("No Hand");
                }
            }
            if (CurrentChosenWeapon.isATPower() && getCurrentUnit().getATP() == 0) EndTurnButton.setText("No ATP");
            if (!CurrentChosenWeapon.canAttackAmmo(ammo)) EndTurnButton.setText("No Ammo");
            else if (getStaminaCost() > getCurrentUnit().getStamina()) EndTurnButton.setText("No Stamina");
            else EndTurnButton.setText(S);
        } else EndTurnButton.setText(S);
    }
    private int debug = 0;
    private EvaButton createDebugButton(String name) {
        EvaButton button = new EvaButton(name);
        button.setPrefHeight(30);
        button.setPrefWidth(80);
        button.setPosition(10, 40);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                BaseUnit unit = getCurrentUnit();
                if (!WeaponsWorld.isEmpty()) WeaponsWorld.get(0);
                debug++;
            }
        });
        return button;
    }
    private void createSwitchWeaponButton(String name, List<EvaButton> menu) {
        EvaButton button = new EvaButton(name);
        button.setPrefHeight(30);
        menu.add(button);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                BaseUnit unit = getCurrentUnit();
                ATPower = null;
                WeaponCheck();
                CurrentChosenWeapon.removeTempTech();
                CurrentChosenWeapon = SwitchToNextWeapon(unit, CurrentChosenWeapon);
                CurrentChosenWeapon.removeTempTech();
                CurrentChosenWeapon.resetPotential();
                UpdateEffectCalc();
                UpdateAttackTestLabels();
                checkPotentialAttackEffect();
                if (CurrentAction.equals("Other") && CurrentSubAction.equals("Reload")) {
                    CurrentSubAction = "";
                    EndTurnButton.setText("End Turn");
                }
                if (CurrentAction.equals("Attack") || CurrentAction.equals("AtkOfOp") || CurrentAction.equals("Other")) {
                    CurrentSubAction = "Basic Attack";
                    ClickedSector = null;
                    checkPotentialAttackEffect();
                    UpdatePlayerView();
                    ResetArrow();
                    setEndTurnButtonBasedOnAmmoAndStamina();
                }
                checkPotentialAttackEffect();
            }
        });
    }

    private Weapon SwitchToNextWeapon(BaseUnit unit, Weapon weapon) {
        // System.out.println("Activate with Unit " +unit.getPlayerName() + " weapon: " +weapon.Name);
        return unit.NextWeapon(weapon);
    }

    private void createSetEffectButton(String name, List<EvaButton> menu, int cost, int accuracy, int reflexes, int speed) {
        EvaButton button = new EvaButton(name);
        button.setPrefHeight(30);
        menu.add(button);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            GMCostDoom.setText(""+cost);
            GMEffectName.setText(name);
            GMEffectAccuracy.setText(""+accuracy);
            GMEffectReflexes.setText(""+reflexes);
            GMEffectSpeed.setText(""+speed);
        }
        });
    }


    private void createSetNextTurnButton(String name, List<EvaButton> menu) {
        EvaButton button = new EvaButton(name);
        button.setPrefHeight(30);
        menu.add(button);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (CurrentState.Player.equals("GM")) {
                ResetAction();
                BaseUnit unit = getUnitFromPlayer(name);
                if (unit != null && !unit.isTurnDone()) {
                    unit.ConditionTickEffect(StateEffect.ExpirationCondition.START);
                    if (unit.GetWeaponList() != null)
                        for (Weapon w : unit.GetWeaponList()) {
                            w.setNeedsDoubleEdgeCheck(true);
                        }
                    CurrentState.NextPlayer = name;
                    if (CurrentPlayer == null) return;
                    if (CurrentPlayer.equals(CurrentState.Player)) {
                        try {
                            EndTurn();
                        } catch (IOException | ClassNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
            }
        });
    }



    private void createEndTurnButton() {
        EndTurnButton = new EvaButton("Progress");
        EndTurnButton.setPrefHeight(30);
        EndTurnButton.setPrefWidth(110);
        EndTurnButton.setPosition(20, 150);
        gamePane.getChildren().add(EndTurnButton);
        EndTurnButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (CurrentPlayer == null) return;
                if (CurrentPlayer.equals(CurrentState.Player) || CurrentAction.equals("GMApply")) {
                    try {
                        EndTurn();
                        } catch (IOException | ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }

    int amogus = -1;
    private void EndTurn(boolean act) throws IOException, ClassNotFoundException {
        boolean end;
        if (!act) {
            end = true;
        } else end = Act();
        UpdateEffectCalc();
        if (end) {
            if (act) {
            if (getCurrentUnit() != null && !(getCurrentUnit() instanceof ChazaqielSummon)) {
            if (getCurrentUnit().getStamina() > 0 && getCurrentUnit().isTurnDone() && !getCurrentUnit().getProhibitedActions().contains("Defend"))
            {
                getCurrentUnit().AddEffect(CommonEffects.DefendEffect());
                getCurrentUnit().ConditionTickEffect(StateEffect.ExpirationCondition.TURN);
                getCurrentUnit().ConditionTickEffect(StateEffect.ExpirationCondition.INTERVAL);
            }
            } else {
                CurrentPlayer = "GM";
                PlayerName.setText("GM");
                CurrentState.NextPlayer = "GM";
            }
            if (ShouldEndRound()) EndRound();
            }
            SaveToGameState();
            EvaSaveUtil.SaveGameState(savegamepath+"currentgame.dat", CurrentState);
            if (amogus>5) amogus=0; else amogus++;
            EvaSaveUtil.SaveGameState(savebackuppath+"currentgame"+amogus+".dat", CurrentState);
            UpdateTurn();
        }
    }

    private void EndTurn() throws IOException, ClassNotFoundException {
        EndTurn(true);
    }


    public void EndRound(){
        for (BaseUnit unit : UnitsList) {
            if (!(unit instanceof ChazaqielSummon)) {
            unit.setStamina(2);
            }
            unit.SetUsedAttack(false);
            unit.SetUsedTactical(false);
            unit.SetUsedGuard(false);
            unit.RoundTickEffect();
            if (unit instanceof Evangelion) {
                unit.SetTurnDone(false);
                unit.setATP(1);
            }
        }
        Round++;
    }

    public boolean ShouldEndRound(){
        for (BaseUnit Unit : UnitsList) {
           if (!Unit.isTurnDone()) return false;
        }
        return true;
    }

    public Evangelion getRandomEvaInRange(int startX, int startY, int endX, int endY) {
        // Calculate Chebyshev distance as the range
        int range = Math.max(Math.abs(startX - endX), Math.abs(startY - endY));

        int boardWidth = GameBoard.boardwidth;
        int boardHeight = GameBoard.boardheight;

        // Calculate valid coordinate boundaries
        int minX = Math.max(0, startX - range);
        int maxX = Math.min(boardWidth - 1, startX + range);
        int minY = Math.max(0, startY - range);
        int maxY = Math.min(boardHeight - 1, startY + range);

        List<Evangelion> validUnits = new ArrayList<>();

        // Search all sectors within the range rectangle
        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                // Skip start and end coordinates
                if ((x == startX && y == startY) || (x == endX && y == endY)) {
                    continue;
                }

                Sector currentSector = GameBoard.getSector(x, y);
                BaseUnit unit = getUnit(currentSector.x, currentSector.y);

                if (unit instanceof Evangelion eva) {
                    validUnits.add(eva);
                }
            }
        }

        // Return random unit or null if none found
        if (validUnits.isEmpty()) {
            return null;
        } else {
            java.util.Random random = new java.util.Random();
            return validUnits.get(random.nextInt(validUnits.size()));
        }
    }

    public boolean Act() throws IOException {
        if (Players.contains(CurrentPlayer) || CurrentPlayerIsGM() || CurrentAction.equals("GMApply")) {
            if (EndTurnButton.getText().equals("No Ammo") || EndTurnButton.getText().equals("No ATP") || EndTurnButton.getText().equals("Set Up") || EndTurnButton.getText().equals("Too Expensive")  || EndTurnButton.getText().equals("No Stamina")
                    || EndTurnButton.getText().equals("No Line") || EndTurnButton.getText().equals("No Hand") || EndTurnButton.getText().equals("Too Large") || EndTurnButton.getText().equals("No Area")) {
                return false;
            }
        if (CurrentAction.equals("AtkOfOp")) {
            if (EndTurnButton.getText().equals("End Turn")) {
                CurrentState.GameQueueList.remove(0);
                if (!CurrentState.GameQueueList.isEmpty()) {
                    CurrentState.NextPlayer = CurrentState.GameQueueList.get(0);
                } else {
                    CurrentState.NextPlayer = CurrentState.Action.Actor;
                    CurrentState.Phase = "";
                    CurrentState.Action = null;
                    CurrentState.BackupAction = null;
                }
                return true;
            }
            if (EndTurnButton.getText().equals("Progress")) {
                AttackTestRollCanUse = true;
                AttackRollCanUse = true;
                CurrentAction = "ProgressAtkOfOp";
                SetUpActionLabels();
                showSubScene(AttackRollsSubScene);
                EndTurnButton.setText("Cancel Attack");
                return false;
            }
        }
        if (CurrentAction.equals("ProgressAtkOfOp")) {
            if (EndTurnButton.getText().equals("Cancel Attack")) {
                CurrentAction = "AtkOfOp";
                EndTurnButton.setText("End Turn");
                return false;
            }
            if (!AttackRollCanUse && !AttackTestRollCanUse) {
                CurrentState.GameQueueList.remove(0);
                BaseUnit AttackingUnit = getCurrentUnit();
                assert AttackingUnit != null;
                int pen = AttackingUnit.getPenetration();

                int ammo = getAmmoCost();
                CurrentChosenWeapon.DecreaseAmmo(ammo);
                List<StateEffect> OnHitEffect = new ArrayList<>();
                if (CurrentAction.equals("Basic Attack") && AttackingUnit.hasUpgrade("Overwatch")) OnHitEffect.add(CommonEffects.NextAttackPenaltyStackable(-10));
                if (CurrentChosenWeapon.isSuperconductive()) {
                    if (TechnologyLabel.getText().equals("Current debuff: -10 to next Attack Test")) OnHitEffect.add(CommonEffects.NextAttackPenalty(-10));
                    else OnHitEffect.add(CommonEffects.NextGuardPenalty(-10));
                }
                BaseUnit attacktarget = Target;
                Attack NextAttack = new Attack(CurrentPlayer, getPlayerFromUnit(attacktarget), Integer.parseInt(AttackTestRollLabel.getText()), AttackingUnit.getAccuracy(),
                            Integer.parseInt(AttackRollLabel.getText()), AttackingUnit.getX(), AttackingUnit.getY(), attacktarget.getX(), attacktarget.getY(), CurrentChosenWeapon.getPenetration()+pen,
                        CurrentChosenWeapon, OnHitEffect);
                AttackingUnit.WeaponSpecificTickEffect(CurrentChosenWeapon);
                AttackingUnit.AttackTickEffect();



                if (CurrentChosenWeapon.isOverheating()) {
                    CurrentChosenWeapon.Overheat(false);
                    AttackingUnit.AddEffect(AttackingUnit instanceof Evangelion ? CommonEffects.DamagePenaltyWeaponUse(-2, CurrentChosenWeapon) : CommonEffects.DamagePenaltyRound(-2));
                    UpdateCurrentLables();
                }
                CurrentState.BackupAction = CurrentState.Action;
                CurrentState.Action = NextAttack;

                if (attacktarget instanceof ChazaqielSummon) {
                    if (!NextAttack.Missed()) {
                        discard(attacktarget);
                        Target = null;
                        System.out.println("Target dead");
                        CurrentState.Action = CurrentState.BackupAction;
                        CurrentState.BackupAction = null;
                        if (CurrentState.GameQueueList.isEmpty()) {
                            CurrentState.NextPlayer = ((Movement)CurrentState.Action).Actor;
                            CurrentState.Phase = "";
                            CurrentState.Action = null;
                        } else {
                            CurrentState.NextPlayer = CurrentState.GameQueueList.get(0);
                            CurrentState.Phase = "AtkOfOp";
                        }
                        return true;
                    }
                }

                if (NextAttack.Missed()) {
                    if (CurrentPlayerIsGM()) {
                        CurrentState.Phase = "";
                        CurrentState.NextPlayer = "GM";
                        return true;
                    }
                    if (CurrentState.GameQueueList.isEmpty()) {
                        CurrentState.NextPlayer = ((Movement)CurrentState.Action).Actor;
                        CurrentState.Phase = "";
                        CurrentState.Action = null;
                    } else {
                        CurrentState.NextPlayer = CurrentState.GameQueueList.get(0);
                        CurrentState.Phase = "AtkOfOp";
                    }
                    return true;
                } else {
                        CurrentState.NextPlayer = NextAttack.Defender;
                        CurrentState.Phase = "Defend";
                    }
                    return true;
            } else return false;
        }
            if (EndTurnButton.getText().equals("Drop")) {
                Weapon w = CurrentChosenWeapon;
                DropWeapon(getCurrentUnit(), w, ClickedSector.x, ClickedSector.y);
                WeaponCheck();
                CurrentState.NextPlayer  = CurrentPlayer;
                return true;
            }
            if (EndTurnButton.getText().equals("Swap")) {
                Evangelion Unit = getCurrentEvangelion();
                if (Unit == null) return false;
                if (!Unit.hasUpgrade("Sub_Arm System"))
                if (Unit.UsedTactical()) {
                    Unit.setStamina(Unit.getStamina()-1);
                } else {
                    Unit.SetUsedTactical(true);
                }
                Weapon w = Unit.getHandItem(swapHand);
                Weapon w1 = swapWing.getItem();
                if (swapHand.equals("Right Hand")) {
                getCurrentEvangelion().state.setRightHandWeapon(w1); }
                else  {
                    getCurrentEvangelion().state.setLeftHandWeapon(w1);
                }
                swapWing.setItem(w);
                CurrentState.NextPlayer  = CurrentPlayer;
                return true;
            }
            if (EndTurnButton.getText().equals("Pick Up")) {
                WeaponObject obj = TargetObject;
                Evangelion eva = getCurrentEvangelion();
                assert eva != null;
                if (eva.UsedTactical()) {
                    eva.setStamina(eva.getStamina()-1); } else {
                    eva.SetUsedTactical(true);
                }
                String hand = "Left";
                if (CurrentSubAction.equals("Pick Up Right")) {
                     hand = "Right";
                }
                if (!eva.getHandItem(hand).isFree()) {
                    DropWeapon(eva, eva.getHandItem(hand), TargetObject.getX(), TargetObject.getY());
                }
                for (StateEffect effect : obj.getWeaponEffects()) {
                    eva.AddEffect(effect);
                }
                if (hand.equals("Right")) eva.state.setRightHandWeapon(obj.getWeapon());
                if (hand.equals("Left")) eva.state.setLeftHandWeapon(obj.getWeapon());
                discard(obj);

                CurrentState.NextPlayer  = CurrentPlayer;
                return true;
            }
            if (EndTurnButton.getText().equals("Get Weapon")) {
                if (CurrentSubAction != null) {
                    getCurrentEvangelion().SetUpPutItemToLocation(CurrentSubAction);
                    EndTurnButton.setText("Set Up");
                }
                if (getCurrentEvangelion().isSetUp())  {
                    EndTurnButton.setText("End Turn");
                    return false;
                }
                CurrentState.NextPlayer  = CurrentPlayer;
                return true;
            }
            if (EndTurnButton.getText().equals("Summon")) {
                if (getCurrentUnit().getATP() > 0) {
                for (int i = 0; i < 3+getCurrentUnit().getWoundLevel(); i++) {
                    String randomplayer = Players.get(RandomGenerator.nextInt(0, Players.size()));
                    createChazaquielSummon((Evangelion) getUnitFromPlayer(randomplayer), getCurrentUnit(), 10+getCurrentUnit().getWoundLevel()*2);
                }
                getCurrentUnit().setATP(0);
                }
                CurrentState.NextPlayer  = CurrentPlayer;
                return true;
            }
            if (EndTurnButton.getText().equals("Split")) {
                if (getCurrentUnit().getATP() > 0) {
                    createChazaquielSummon(getUnitFromPlayer("GM"), getCurrentUnit(), 10+getCurrentUnit().getWoundLevel()*2);
                    Teleport(getCurrentUnit(), 10+getCurrentUnit().getWoundLevel()*2);
                    getCurrentUnit().setATP(0);
                }
                CurrentState.NextPlayer  = CurrentPlayer;
                return true;
            }
            if (EndTurnButton.getText().equals("Discard")) {
                Weapon w = CurrentChosenWeapon;
                getCurrentUnit().removeWeapon(w);
                if (getUnitFromPlayer(w.OriginalOwner) instanceof Evangelion eva) {
                    eva.setRequisition(eva.getRequisition()+w.getCost());
                }
                WeaponCheck();
                CurrentState.NextPlayer  = CurrentPlayer;
                return true;
            }
            if (EndTurnButton.getText().equals("Apply")) {
                if (!CurrentSubAction.equals("ApplyAll") && ClickedUnit != null) {
                    ClickedUnit.dealDamage(Integer.parseInt(GMDamage.getText()));
                    if (Boolean.parseBoolean(GMStand.getText())) {
                        ClickedUnit.ConditionTickEffect(StateEffect.ExpirationCondition.STAND);
                    }
                    if (ClickedUnit instanceof Evangelion eva) eva.state.Doom-=Integer.parseInt(GMCostDoom.getText());
                }
                if (CurrentSubAction.equals("SupportSector")) {
                    for (SectorType type : SectorTypesList) {
                       if (type.Name.equals("Support")) ClickedSector.setType(type);}
                }
                if (CurrentSubAction.equals("Effect")) {
                    StateEffect effect = new StateEffect(GMEffectName.getText());
                    effect.Condition = StateEffect.ExpirationCondition.valueOf(GMEffectExpirationCondition.getText().toUpperCase());
                    effect.AccuracyDelta = Integer.parseInt(GMEffectAccuracy.getText());
                    effect.ReflexesDelta = Integer.parseInt(GMEffectReflexes.getText());
                    effect.ArmorDelta = Integer.parseInt(GMEffectArmor.getText());
                    effect.AttackStrengthDelta = Integer.parseInt(GMEffectAttackStrength.getText());
                    effect.RangedStrengthDelta = Integer.parseInt(GMEffectRangedStrength.getText());
                    effect.SpeedDelta = Integer.parseInt(GMEffectSpeed.getText());
                    effect.MaxToughnessDelta = Integer.parseInt(GMEffectMaxToughness.getText());
                    effect.SpeedChange = Integer.parseInt(GMEffectSpeedChange.getText());
                    effect.PenetrationDelta = Integer.parseInt(GMEffectPenetration.getText());
                    effect.Expiration = Integer.parseInt(GMEffectExpiration.getText());
                    effect.ProhibitedActions.add(GMEffectProhibited.getText());
                    ClickedUnit.AddEffect(effect);
                }
                if (CurrentSubAction.equals("ApplyAll")) {
                    StateEffect effect = new StateEffect(GMEffectName.getText());
                    effect.Condition = StateEffect.ExpirationCondition.valueOf(GMEffectExpirationCondition.getText().toUpperCase());
                    effect.AccuracyDelta = Integer.parseInt(GMEffectAccuracy.getText());
                    effect.ReflexesDelta = Integer.parseInt(GMEffectReflexes.getText());
                    effect.ArmorDelta = Integer.parseInt(GMEffectArmor.getText());
                    effect.AttackStrengthDelta = Integer.parseInt(GMEffectAttackStrength.getText());
                    effect.RangedStrengthDelta = Integer.parseInt(GMEffectRangedStrength.getText());
                    effect.SpeedDelta = Integer.parseInt(GMEffectSpeed.getText());
                    effect.MaxToughnessDelta = Integer.parseInt(GMEffectMaxToughness.getText());
                    effect.SpeedChange = Integer.parseInt(GMEffectSpeedChange.getText());
                    effect.PenetrationDelta = Integer.parseInt(GMEffectPenetration.getText());
                    effect.Expiration = Integer.parseInt(GMEffectExpiration.getText());
                    effect.ProhibitedActions.add(GMEffectProhibited.getText());
                    for (Evangelion eva : EvangelionList) {
                        eva.AddEffect(effect);
                    }
                }
                if (CurrentSubAction.equals("Delete")) {
                 discard(ClickedUnit);
                }
                UpdateUnitLabels();
                CurrentState.NextPlayer = CurrentState.Player;
                return true;
            }
            if (EndTurnButton.getText().equals("End Turn")) {
            if (CurrentAction.equals("SetUp")) {
                if (CurrentState.GameQueueList.isEmpty()) {
                    CurrentState.GameQueueList.addAll(Players);
                    CurrentState.NextPlayer =  CurrentState.GameQueueList.get(0);
                } else {
                    CurrentState.GameQueueList.remove(0);
                    if (CurrentState.GameQueueList.isEmpty()) {
                        CurrentState.NextPlayer = "GM";
                        CurrentState.Phase = "";
                    } else CurrentState.NextPlayer =  CurrentState.GameQueueList.get(0);
                }
                return true;
            }
            BaseUnit Current = getCurrentUnit();
            if (Current instanceof ChazaqielSummon) Current.setStamina(0);
            Current.SetTurnDone(true);
            if (Current instanceof Evangelion) {
                BaseUnit Angel = getUnitFromPlayer("GM");
                Angel.SetTurnDone(false);
                Angel.setATP(1);
                Angel.ConditionTickEffect(StateEffect.ExpirationCondition.START);
                Angel.SetUsedAttack(false);
                Angel.setStamina(2);
            }
            return true;
        }
            if (EndTurnButton.getText().equals("Aim")) {
                BaseUnit Unit = getCurrentUnit();
                ResetAction();
                if (Unit.UsedTactical()) {
                Unit.setStamina(Unit.getStamina()-1); } else {
                    Unit.SetUsedTactical(true);
                }
                Unit.AddEffect(CommonEffects.AimEffect());
                CurrentState.NextPlayer = CurrentPlayer;
                return true;
            }
            if (EndTurnButton.getText().equals("Reload")) {
                Evangelion Unit = getCurrentEvangelion();
                if (Unit != null) {
                ResetAction();
                Unit.setStamina(Unit.getStamina()-1);
                CurrentChosenWeapon.Reload();
                Weapon w = Unit.AmmoChooser();
                Unit.removeWeapon(w);
                UpdatePlayerView();
                UpdateCurrentLables();
                CurrentState.NextPlayer = CurrentPlayer;
                return true;
            }
            }
            if (EndTurnButton.getText().equals("Item")) {
                Evangelion eva = (Evangelion) getUnitFromPlayer(CurrentPlayer);
                Weapon weapon = EvaSaveUtil.ReadWeapon(filepath+"/Weapons/"+CurrentSubAction+".txt");
                createWeaponObject(weapon, ClickedSector.x, ClickedSector.y);
                eva.setRequisition(eva.getRequisition()-weapon.getCost());
                CurrentState.NextPlayer = CurrentPlayer;
                return true;
            }
            if (EndTurnButton.getText().equals("Defend")) {
                BaseUnit Unit = getCurrentUnit();
                ResetAction();
                Unit.setStamina(Unit.getStamina()-1);
                Unit.AddEffect(CommonEffects.DefendEffect());
                CurrentState.NextPlayer = CurrentPlayer;
                return true;
            }
            if (EndTurnButton.getText().equals("Stand")) {
                BaseUnit Eva = getCurrentUnit();
                ResetAction();
                Eva.setStamina(Eva.getStamina()-1);
                Eva.ConditionTickEffect(StateEffect.ExpirationCondition.STAND);
                CurrentMovement = new Movement(getPlayerFromUnit(Eva), Eva.getX(), Eva.getY(), Eva.getX(), Eva.getY(), 0);
                boolean AOO = false;
                for (BaseUnit unit : UnitsList) {
                    if (!unit.equals(Eva) && EvaCalculationUtil.isAdjecent(CurrentMovement.StartX, CurrentMovement.StartY, unit.getX(), unit.getY())) {
                        CurrentState.GameQueueList.add(getPlayerFromUnit(unit));
                        CurrentState.Phase = "AtkOfOp";
                        CurrentState.Action = CurrentMovement;
                        AOO = true;
                    }
                }
                if (AOO) {
                    CurrentState.NextPlayer = CurrentState.GameQueueList.get(0);
                } else {
                    CurrentState.NextPlayer = CurrentPlayer;
                    ResetAction();
                }

                return true;
            }
            if (EndTurnButton.getText().equals("Tactical Aim")) {
                BaseUnit Unit = getCurrentUnit();
                ResetAction();
                Unit.AddEffect(CommonEffects.AimEffect());
                CurrentState.NextPlayer = CurrentPlayer;
                return true;
            }
            if (EndTurnButton.getText().equals("Cancel Attack")) {
                BaseUnit Unit = getCurrentUnit();
                ResetAction();
                CurrentAction = "Attack";
                EndTurnButton.setText("Progress");
                ApplyPlayer();
                Unit.SetUsedAttack(false);
                if (CurrentChosenWeapon.temptech && Unit instanceof Evangelion eva) eva.SetUsedAngelicCore(false);
                return false;
            }
            if (EndTurnButton.getText().equals("Apply Edge")) {
                BaseUnit Unit = getCurrentUnit();
                ResetAction();
                assert Unit != null;
                Weapon w = Unit.NeedsDoubleEdgeCheck();
                w.setNeedsDoubleEdgeCheck(false);
                ApplyPlayer();
                return false;
            }
            switch(CurrentAction) {
            case "Move" -> {
                DoMove();
                BaseUnit Eva = getCurrentUnit();
                if (CurrentSubAction.equals("Reposition")) {
                    Eva.setATP(0);
                    StateEffect effect = new StateEffect("RepositionBonus");
                    effect.Condition = StateEffect.ExpirationCondition.TURN;
                    effect.ArmorDelta = 1;
                    Eva.AddEffect(effect);
                } else Eva.setStamina(Eva.getStamina()-CurrentMovement.Cost);
                if (CurrentSubAction.equals("Run")
                        && EvaCalculationUtil.isInRange(CurrentMovement.StartX, CurrentMovement.StartY, CurrentMovement.EndX,
                        CurrentMovement.EndY, Math.max(2, (int) Math.ceil(Eva.getSpeed()*0.5f)))) {
                    Eva.AddEffect(CommonEffects.RunBonus());
                }
                if (CurrentSubAction.equals("Take Cover")) {
                    Eva.AddEffect(CommonEffects.Cover());
                }
                boolean AOO = false;
                for (BaseUnit unit : UnitsList) {
                    if (!unit.equals(Eva) && !(unit instanceof ChazaqielSummon) && EvaCalculationUtil.isAdjecent(CurrentMovement.StartX, CurrentMovement.StartY, unit.getX(), unit.getY())
                            && !CurrentSubAction.equals("Maneuver")) {
                        CurrentState.GameQueueList.add(getPlayerFromUnit(unit));
                        CurrentState.Phase = "AtkOfOp";
                        CurrentState.Action = CurrentMovement;
                        AOO = true;
                    }
                }
                if (AOO) {
                    CurrentState.NextPlayer = CurrentState.GameQueueList.get(0);
                } else {
                CurrentState.NextPlayer = CurrentPlayer;
                ResetAction();
                }
                return true;
            }
            case "Wound" -> {
                Attack CurrentAttack = (Attack) CurrentState.Action;
                BaseUnit unit = getCurrentUnit();
                if (unit instanceof Evangelion eva) {
                    int woundlevel = eva.getWoundLevel();
                    int HitLocation = PrepTestNumbers(CurrentAttack.Test);
                    EvaWoundDeterminer.WoundResult result = EvaWoundDeterminer.determineWounds(HitLocation, woundlevel);
                    NewWound = result.newWound;
                    ReducedWound = result.reducedWound;
                    StateEffect Wound = NewWound;
                    if (RedOrgans) {
                        eva.state.StateEffects.add(ReducedWound);
                        Wound = ReducedWound;
                        eva.SetRedOrgans(true);
                    } else {
                        eva.state.StateEffects.add(NewWound);
                    }
                    if (Wound.drop_left) {
                        Weapon W = eva.state.getLeftHandWeapon();
                        if (eva.canDrop(W)) DropWeapon(eva, W);
                    }
                    if (Wound.drop_right) {
                        Weapon W = eva.state.getRightHandWeapon();
                        if (eva.canDrop(W)) DropWeapon(eva, W);
                    }
                    if (Wound.destroyed_left) {
                        Weapon W = eva.state.getLeftHandWeapon();
                        eva.getLeftWing().loadout = WingLoadout.Loadout.NONE;
                        eva.getLeftWing().item = Weapon.Free();
                        getCurrentUnit().removeWeapon(W);
                    }
                    if (Wound.destroyed_right) {
                        Weapon W = eva.state.getRightHandWeapon();
                        eva.getRightWing().loadout = WingLoadout.Loadout.NONE;
                        eva.getRightWing().item = Weapon.Free();
                        getCurrentUnit().removeWeapon(W);
                    }
                    eva.UpdateCalcWeapon(CurrentChosenWeapon);
                }
                assert unit != null;
                unit.IncreaseWoundLevel();
                if (CurrentState.BackupAction != null) {
                    CurrentState.Action = CurrentState.BackupAction;
                    CurrentState.BackupAction = null;
                    if (CurrentState.GameQueueList.isEmpty()) {
                        CurrentState.NextPlayer = ((Movement)CurrentState.Action).Actor;
                        CurrentState.Phase = "";
                        CurrentState.Action = null;
                    } else {
                        CurrentState.NextPlayer = CurrentState.GameQueueList.get(0);
                        CurrentState.Phase = "AtkOfOp";
                    }
                    return true;
                }
                if (CurrentState.GameQueueList.isEmpty()) {
                    CurrentState.Action = null;
                    CurrentState.Phase = "";
                    CurrentState.NextPlayer = CurrentAttack.Attacker;
                } else {
                    CurrentState.Phase = "Defend";
                    CurrentState.Action = CurrentState.AttackQueueList.get(0);
                    CurrentState.NextPlayer = CurrentState.GameQueueList.get(0);
                }
                UpdateUnitLabels();
                ResetAction();
                return true;
            }
            case "Attack" -> {
                BaseUnit Unit = getCurrentUnit();
                if (Unit.UsedAttack()) return false;
                Unit.SetUsedAttack(true);
                if (CurrentChosenWeapon.temptech && Unit instanceof Evangelion eva) eva.SetUsedAngelicCore(true);
                AttackTestRollCanUse = true;
                AttackRollCanUse = true;
                CurrentAction = "ProgressAttack";
                SetUpActionLabels();
                showSubScene(AttackRollsSubScene);
                EndTurnButton.setText("Cancel Attack");
                return false;
            }
            case "DefendHelp" -> {
                Attack CurrentAttack = (Attack) CurrentState.Action;
                CurrentState.NextPlayer = CurrentAttack.Defender;
                CurrentState.Phase = "Defend";
                return true;
            }
            case "Defend" -> {
                Attack CAttack = (Attack) CurrentState.Action;
                System.out.println("START DEFENCE ");
                BaseUnit DefendingUnit = getCurrentUnit();
                System.out.println("Player = "+CurrentState.Player+ " Next: "+CurrentState.NextPlayer+ " Queueu: "+ CurrentState.GameQueueList.get(0));
                System.out.println("Queue sizes, Attack: "+CurrentState.AttackQueueList.size()+" player: "+CurrentState.GameQueueList.size());
                System.out.println("Attack "+ CurrentState.AttackQueueList.get(0).Attacker+" attacked "+CurrentState.AttackQueueList.get(0).Defender);
                for (String player : CurrentState.GameQueueList) {
                    System.out.println(player);
                }
                if (CurrentState.BackupAction == null) {
                CurrentState.GameQueueList.remove(0);
                CurrentState.AttackQueueList.remove(0); }
                System.out.println("Queue sizes, Attack: "+CurrentState.AttackQueueList.size()+" player: "+CurrentState.GameQueueList.size());
                assert DefendingUnit != null;
                boolean passed = false;
                if (DefendingUnit.UsedGuard()) {
                    int DefenceTest = 100;
                    try {
                    DefenceTest = Integer.parseInt(DefenceTestRollLabel.getText()); }
                    catch (NumberFormatException ignore){}
                    int TargetNumber = DefendingUnit.getReflexes();
                    passed = DefenceTest < TargetNumber;
                    DefendingUnit.GuardTickEffect();
                } else if ((CAttack.Weapon.getArea() > -1 || CAttack.Weapon.getLine()) && CAttack.Missed()) {
                    passed = true;
                }
                if (CAttack.Weapon.isChain() && CAttack.getDOS() > 0) {
                    DefendingUnit.dealDamage(Math.min(CAttack.getDOS(), 3));
                }
                if (passed && (CAttack.Weapon.getArea() > -1 || CAttack.Weapon.getLine())) {
                    CAttack = new Attack(CAttack.Attacker, CAttack.Defender, CAttack.Test, CAttack.TargetNumber,
                            (int) Math.ceil(CAttack.Damage*0.5f), CAttack.AttackerX, CAttack.AttackerY, CAttack.DefenderX, CAttack.DefenderY,
                            CAttack.Penetration, CAttack.Weapon, CAttack.OnHitEffect);
                    passed = false;
                }
                if (!passed) {
                   DefendingUnit.hurt(CAttack);
                   DefendingUnit.ConditionTickEffect(StateEffect.ExpirationCondition.POTENTIAL);
                   for (StateEffect effect : CAttack.OnHitEffect) {
                   DefendingUnit.AddEffect(effect);
                   }
                   DefendingUnit.UpdateCalcWeapon(CurrentChosenWeapon);
                }
                if (DefendingUnit.getToughness() <= 0) {
                    CurrentState.Phase = "Wound";
                    CurrentState.NextPlayer = getPlayerFromUnit(DefendingUnit);
                } else {
                if (CurrentState.BackupAction != null) {
                    CurrentState.Action = CurrentState.BackupAction;
                    CurrentState.BackupAction = null;
                    if (CurrentState.GameQueueList.isEmpty()) {
                        CurrentState.NextPlayer = ((Movement)CurrentState.Action).Actor;
                        CurrentState.Phase = "";
                        CurrentState.Action = null;
                    } else {
                        CurrentState.NextPlayer = CurrentState.GameQueueList.get(0);
                        CurrentState.Phase = "AtkOfOp";
                    }
                    return true;
                }
                if (CurrentState.GameQueueList.isEmpty()) {
                    CurrentState.Action = null;
                    CurrentState.Phase = "";
                    CurrentState.NextPlayer = CAttack.Attacker;
                } else {
                    CurrentState.Action = CurrentState.AttackQueueList.get(0);
                    CurrentState.NextPlayer = CurrentState.GameQueueList.get(0);
                }
                }
                return true;
            }
            case "ProgressAttack" -> {
                 if (!AttackRollCanUse && !AttackTestRollCanUse) {
                     BaseUnit AttackingUnit = getCurrentUnit();
                     assert AttackingUnit != null;
                     int pen = AttackingUnit.getPenetration();
                     AttackingUnit.setStamina(AttackingUnit.getStamina()-getStaminaCost());
                     int ammo = getAmmoCost();
                     if (CurrentChosenWeapon.isATPower()) AttackingUnit.setATP(0); else CurrentChosenWeapon.DecreaseAmmo(ammo);
                     if (CurrentChosenWeapon.temptech) AttackingUnit.addDoom(1);
                     List<StateEffect> OnHitEffect = new ArrayList<>();
                     if (CurrentAction.equals("Basic Attack") && AttackingUnit.hasUpgrade("Overwatch")) OnHitEffect.add(CommonEffects.NextAttackPenaltyStackable(-10));
                     if (CurrentChosenWeapon.isSuperconductive()) {
                         if (TechnologyLabel.getText().equals("Current debuff: -10 to next Attack Test")) OnHitEffect.add(CommonEffects.NextAttackPenalty(-10));
                         else OnHitEffect.add(CommonEffects.NextGuardPenalty(-10));
                     }
                     if (Target != null) {
                         System.out.println("Target != null");
                         TargetList = new ArrayList<>();
                         TargetList.add(Target);
                     }
                     List<Attack> AttackQueue = new ArrayList<>();
                     List<BaseUnit> targetlistchanged = new ArrayList<>();
                     for (BaseUnit attacktarget : TargetList) {
                         Attack NextAttack = new Attack(CurrentPlayer, getPlayerFromUnit(attacktarget),
                                 Integer.parseInt(AttackTestRollLabel.getText()), AttackingUnit.getAccuracy(),
                                 Integer.parseInt(AttackRollLabel.getText()), AttackingUnit.getX(),
                                 AttackingUnit.getY(), attacktarget.getX(), attacktarget.getY(),
                                 CurrentChosenWeapon.getPenetration()+pen, CurrentChosenWeapon, OnHitEffect);

                         if (attacktarget instanceof ChazaqielSummon) {
                                    if (Target == null || !NextAttack.Missed()) {
                                        discard(attacktarget);
                                        Target = null;
                                        System.out.println("Target dead");
                                    }
                                }
                                else {
                                AttackQueue.add(NextAttack);
                                targetlistchanged.add(attacktarget);
                                System.out.println("Target defending ");
                                }
                     }
                     TargetList = targetlistchanged;
                     if (Target != null && isThrow() && !((CurrentChosenWeapon.getThrowBonus() == 3) && AttackQueue.get(0).Missed())) {
                       DropWeapon(getCurrentUnit(), CurrentChosenWeapon, Target.getX(), Target.getY());
                     }
                     AttackingUnit.WeaponSpecificTickEffect(CurrentChosenWeapon);
                     AttackingUnit.AttackTickEffect();
                     if (CurrentChosenWeapon.isOverheating()) {
                         CurrentChosenWeapon.Overheat(false);
                         AttackingUnit.AddEffect(AttackingUnit instanceof Evangelion ? CommonEffects.DamagePenaltyWeaponUse(-2, CurrentChosenWeapon) : CommonEffects.DamagePenaltyRound(-2));
                         UpdateCurrentLables();
                     }
                        BlowUpArea();
                        if (!AttackQueue.isEmpty()) {
                        CurrentAttack = AttackQueue.get(0);
                        CurrentState.Action = CurrentAttack;
                        CurrentState.AttackQueueList = AttackQueue;
                        for (BaseUnit unit : TargetList) {
                        CurrentState.GameQueueList.add(getPlayerFromUnit(unit));
                        }
                        System.out.println("Queue sizes, Attack: "+CurrentState.AttackQueueList.size()+" player: "+CurrentState.GameQueueList.size());
                     if (AttackQueue.get(0).Missed() && Target != null) {
                         if (CurrentPlayerIsGM()) {
                             CurrentState.NextPlayer = CurrentPlayer;
                             CurrentState.Phase = "";
                             return true;
                         }
                         if (getCurrentEvangelion() != null && getCurrentEvangelion().state.Doom > 1) {
                         if (getRandomEvaInRange(AttackQueue.get(0).AttackerX, AttackQueue.get(0).AttackerY, AttackQueue.get(0).DefenderX, AttackQueue.get(0).DefenderY) != null) {
                             Evangelion eva = getRandomEvaInRange(AttackQueue.get(0).AttackerX, AttackQueue.get(0).AttackerY, AttackQueue.get(0).DefenderX, AttackQueue.get(0).DefenderY);
                             Attack NextAttack = new Attack(CurrentPlayer, getPlayerFromUnit(eva),
                                     AttackingUnit.getAccuracy()-1, AttackingUnit.getAccuracy(),
                                     Integer.parseInt(AttackRollLabel.getText()), AttackingUnit.getX(),
                                     AttackingUnit.getY(), eva.getX(), eva.getY(),
                                     CurrentChosenWeapon.getPenetration()+pen, CurrentChosenWeapon, OnHitEffect);
                             AttackQueue.remove(0);
                             AttackQueue.add(NextAttack);
                             getCurrentEvangelion().state.Doom--;
                             CurrentState.GameQueueList = new ArrayList<>();
                             CurrentAttack = AttackQueue.get(0);
                             CurrentState.Action = CurrentAttack;
                             CurrentState.AttackQueueList = AttackQueue;
                             CurrentState.GameQueueList.add(CurrentAttack.Defender);
                             CurrentState.NextPlayer = CurrentAttack.Defender;
                             CurrentState.Phase = "Defend";
                             return true;
                         }
                         }
                         CurrentState.NextPlayer = CurrentPlayer;
                         CurrentState.Phase = "";
                         return true;
                     } else {
                     CurrentState.NextPlayer = CurrentAttack.Defender;
                     CurrentState.Phase = "Defend";
                     }
                     return true; } else {
                            System.out.println("END ATTACK");
                            CurrentState.NextPlayer = CurrentPlayer;
                            CurrentState.Phase = "";
                            return true;
                        }
                 } else return false;
            }
        }
        }
        return true;
    }

    public String getPlayerFromUnit(BaseUnit unit){
        if (unit instanceof Evangelion evangelion) {
            return Players.get(EvangelionList.indexOf(evangelion));
        }
        else return "GM";
    }
    public BaseUnit getUnitFromPlayer(String Player){
        if (Players.contains(Player)) {
            for (BaseUnit unit : UnitsList) {
                if (unit.getPlayerName().equals(Player)) return unit;
            }
        }
        if (Player.equals("GM")) return AngelList.get(0);
        if (SummonPlayers.contains(Player)) {
        for (BaseUnit unit : UnitsList) {
            if (unit.getPlayerName().equals(Player)) return unit;
        }
        }
        return null;
    }



    public void SetUpTurn() {
        UpdateEffectCalc();
        ATPower = null;
        WeaponCheck();
        if (CurrentState.Action == null) {
        for (BaseUnit unit : UnitsList) {
            unit.RemovePotentialEffects();
            for (Weapon weapon : unit.GetWeaponList()) {
                weapon.removeTempTech();
            }
        }
        }
        SetUpActionLabels();
        EndTurnButton.setText("End Turn");
        ApplyPlayer();
    }

    public void UpdateEffectCalc(){
        for (BaseUnit unit : UnitsList) {
            if (unit.equals(getCurrentUnit())) unit.UpdateCalcWeapon(CurrentChosenWeapon);
            unit.UpdateCalc();
        }
        UpdateUnitLabels();
    }


    public int PrepTestNumbers(int test){
        if (test>99) return 1;
        String S = Integer.toString(test);
        if (S.length() == 1) return test*10;
        else {
            char A = S.charAt(0);
            char B = S.charAt(1);
            String K = ""+B+""+A;
            return Integer.parseInt(K);
        }
    }

    public void SetUpActionLabels(){
        if (getCurrentUnit() != null) {
            BaseUnit Eva = getCurrentUnit();
            if (CurrentState.Phase.equals("Wound")) {
                RedOrgans = false;
                if (Eva instanceof Evangelion evangelion && !evangelion.UsedRedOrgans()) {
                    RedOrgansButton.setText("Reduce Wound");
                } else RedOrgansButton.setText("Cant Reduce");
                if (Eva instanceof Evangelion evangelion) {
                    int woundlevel = evangelion.getWoundLevel();
                    if (woundlevel > 2) return;
                    int HitLocation = PrepTestNumbers(((Attack) CurrentState.Action).Test);
                    EvaWoundDeterminer.WoundResult result = EvaWoundDeterminer.determineWounds(HitLocation, woundlevel);
                    NewWound = result.newWound;
                    ReducedWound = result.reducedWound;
                    WoundLabel.setText(NewWound.Name);
                    EndTurnButton.setText("Progress");
                } else WoundLabel.setText("Angel Wound");
                return;
            }
            if (CurrentState.Phase.equals("Defend")) {
                HelpName.setText("");
                DefenceTestRollLabel.setText(Eva.UsedGuard() ? "Cant Guard" : "Target Number = "+Eva.getReflexes());
                return;
            }

            UpdateAttackTestLabels();
        }
    }




    private void UpdateAttackTestLabels(){
        BaseUnit Eva = getCurrentUnit();
        WeaponCheck();
        Weapon Current = CurrentChosenWeapon;
        TechnologyLabel.setText("");
        if (Current.isExplosive()) {
            if (Current.getCurrentAmmo() > 0)
            AttackMax.setText("Max Attack");
            else AttackMax.setText("No Ammo");
        } else AttackMax.setText("No Explosive");
        if (Current.isPolythermic()) {
            String S = Current.isOverheating() ? "Disable" : "";
            TechnologyButton.setText(S+"Overheat");
        } else if (Current.isSuperconductive()) {
            TechnologyButton.setText("Switch");
            TechnologyLabel.setText("Current debuff: -10 to next Attack Test");
        } else TechnologyButton.setText("No Special");

        if (AttackRollCanUse) {
        String string = "Roll: " + Current.getDiceNumber() + "d" + Current.getDiceStrength();
        int p = Current.getPower();
        p+= Eva.getUnitStrength(Current);
        if (Current.isGauss() && !AttackTestRollCanUse) {
            p += getDOSMax(Integer.parseInt(AttackTestRollLabel.getText()), Eva.getAccuracy(), 4);
        }
        if (p != 0) {
            string = string + (p > 0 ? "+" : "") + p;
        }
        AttackRollLabel.setText(string);
        }
        if (AttackTestRollCanUse) AttackTestRollLabel.setText("Target Number = "+Eva.getAccuracy());
    }




    private void SaveToGameState(){
        int i = 0;
        if (CurrentPlayerIsGM()) CurrentState.Fog = Integer.parseInt(GMFog.getText());
        CurrentState.Round = Round;
        for (Evangelion eva : EvangelionList) {
            CurrentState.EvaList.set(i, eva.state);
            i++;
        }
        List<Weapon> Weapons = new ArrayList<>();
        for (WeaponObject w : WeaponsWorld) {
            Weapons.add(w.getWeapon());
        }
        CurrentState.Weapons = Weapons;
        List<ChazaqielSummonState> CHSS = new ArrayList<>();
        for (ChazaqielSummon w : SummonList) {
            CHSS.add(w.state);
        }
        CurrentState.SummonList = CHSS;
        i = 0;
        for (Angel angel : AngelList) {
            CurrentState.AngelList.set(i, angel.state);
            i++;
        }
        fromBoardtoBattlefield();
        CurrentState.Field = this.Battlefield;
    }

    private void ReadFromGameState(){
        int i = 0;
        FogNumber = CurrentState.Fog;
        GMFog.setText(""+FogNumber);
        Round = CurrentState.Round;
        for (Evangelion eva : EvangelionList) {
            eva.state = CurrentState.EvaList.get(i);
            i++;
        }
        List<BaseUnit> discardqueue2 = new ArrayList<>();
        discardqueue2.addAll(SummonList);
        for (BaseUnit w : discardqueue2) {
            discard(w);
        }
        List<WeaponObject> discardqueue = new ArrayList<>();
        discardqueue.addAll(WeaponsWorld);
        for (WeaponObject w : discardqueue) {
            discard(w);
        }
        SummonList = new ArrayList<>();
        WeaponsWorld = new ArrayList<>();
        if (CurrentState.SummonList != null) {
        for (ChazaqielSummonState state : CurrentState.SummonList) {
            createChazaquielSummon(state);
        }
        }
        if (CurrentState.Weapons != null) {
            for (Weapon weapon : CurrentState.Weapons) {
                createWeaponObject(weapon, weapon.x, weapon.y);
            }
        }
        i = 0;
        for (Angel angel : AngelList) {
            angel.state = CurrentState.AngelList.get(i);
            i++;
        }
        this.Battlefield = CurrentState.Field;
        fromBattlefieldToBoardSectors();
    }

    public WeaponObject createWeaponObject(Weapon weapon, int x, int y) {
        List<WeaponObject> discardqueue = new ArrayList<>();
        for (WeaponObject o : WeaponsWorld) {
            if (o.getWeapon().equals(weapon)) discardqueue.add(o);
        }
        for (WeaponObject o : discardqueue) {
            discard(o);
        }
        WeaponObject obj = new WeaponObject(weapon);
        obj.setPosition(x, y);
        WeaponsWorld.add(obj);
        viewport.getChildren().add(obj.ItemCircle);
        return obj;
    }

    public ChazaqielSummon createChazaquielSummon(ChazaqielSummonState state) {
        List<ChazaqielSummon> discardqueue = new ArrayList<>();
        for (ChazaqielSummon o : SummonList) {
            if (o.state.equals(state)) discardqueue.add(o);
        }
        for (ChazaqielSummon o : discardqueue) {
            discard(o);
        }
        ChazaqielSummon obj = new ChazaqielSummon(getUnitFromPlayer(state.TypeString), state);
        obj.setX(state.x);
        obj.setY(state.y);
        SummonList.add(obj);
        viewport.getChildren().add(obj.UnitCircle);
        SummonPlayers.add(obj.getPlayerName());
        UnitsList.add(obj);
        obj.UnitCircle.setDisable(true);
        return obj;
    }
    public ChazaqielSummon createChazaquielSummon(Evangelion evangelion, BaseUnit unit, int range) throws IOException {
        Sector sector = GameBoard.getRandomSectorInRange(unit.getX(), unit.getY(), range);
        if (getUnit(sector.x, sector.y) != null) return null;
        else  return createChazaquielSummon(evangelion, sector.x, sector.y);
    }
    public void Teleport(BaseUnit unit, int range)  {
        Sector sector = GameBoard.getRandomSectorInRange(unit.getX(), unit.getY(), range);
        if (getUnit(sector.x, sector.y) != null) return;
        unit.setX(sector.x);
        unit.setY(sector.y);
        Update();
    }
    public ChazaqielSummon createChazaquielSummon(BaseUnit evangelion, BaseUnit unit, int range) throws IOException {
        Sector sector = GameBoard.getRandomSectorInRange(unit.getX(), unit.getY(), range);
        if (getUnit(sector.x, sector.y) != null) return null;
        else  return createChazaquielSummon(evangelion, sector.x, sector.y);
    }

    public ChazaqielSummon createChazaquielSummon(Evangelion evangelion, int x, int y) throws IOException {
        ChazaqielSummon obj = new ChazaqielSummon(evangelion,
                new ChazaqielSummonState("GM"+ RandomGenerator.nextInt(0,100000), evangelion.getPlayerName()));
        SummonPlayers.add(obj.getPlayerName());
        obj.setX(x);
        obj.setY(y);
        obj.setStamina(0);
        List<Weapon> checkWeapons = new ArrayList<>(evangelion.GetWeaponList());
        for (WingLoadout wing : evangelion.state.Wings) {
            if (wing.isAttack() && wing.hasWeapon()) {
                    checkWeapons.add(wing.getItem());
            }
        }
        List<Weapon> w3 = new ArrayList<>();
        for (Weapon w1 : checkWeapons) {
            Weapon w2;
            if (WeaponsNamesList.contains(w1.Name) ||  w1.Name.equals("Free") || w1.Name.equals("Ammo")) w2 = EvaSaveUtil.ReadStringWeapon(w1.Name);
            else w2 = Weapon.getUnarmedAttack();
            w2.Reload();
            w3.add(w2);
        }
        obj.state.Weapons = w3;
        SummonList.add(obj);
        UnitsList.add(obj);
        viewport.getChildren().add(obj.UnitCircle);
        obj.UnitCircle.setDisable(true);

        return obj;
    }

    public ChazaqielSummon createChazaquielSummon(BaseUnit evangelion, int x, int y) throws IOException {
        ChazaqielSummon obj = new ChazaqielSummon(evangelion,
                new ChazaqielSummonState("GM"+ RandomGenerator.nextInt(0,100000), evangelion.getPlayerName()));
        SummonPlayers.add(obj.getPlayerName());
        obj.setX(x);
        obj.setY(y);
        obj.setStamina(0);
        List<Weapon> checkWeapons = new ArrayList<>(evangelion.GetWeaponList());
        List<Weapon> w3 = new ArrayList<>();
        for (Weapon w1 : checkWeapons) {
            Weapon w2;
            if (WeaponsNamesList.contains(w1.Name) ||  w1.Name.equals("Free") || w1.Name.equals("Ammo")) w2 = EvaSaveUtil.ReadStringWeapon(w1.Name);
            else w2 = Weapon.getUnarmedAttack();
            if (w1.Name.equals("AngelRanged")) w2 = Weapon.getAngelRangedAttack();
            if (w1.Name.equals("AngelMelee")) w2 = Weapon.getAngelMeleeAttack();
            w2.Reload();
            w3.add(w2);
        }
        obj.state.Weapons = w3;
        SummonList.add(obj);
        UnitsList.add(obj);
        viewport.getChildren().add(obj.UnitCircle);
        obj.UnitCircle.setDisable(true);

        return obj;
    }

    private void createUpdateTurnButton() throws IOException {

        EvaButton button = new EvaButton("Update State");
        button.setPrefHeight(30);
        button.setPrefWidth(110);
        button.setPosition(20, 190);
        gamePane.getChildren().add(button);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    UpdateTurn();
                } catch (IOException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }

    private void startSaveFileWatcher() throws IOException {
        String savePath = EvaSaveUtil.getSaveGamePath().replace("\\", "/");
        Path directory = Paths.get(savePath);
        String targetFile = "currentgame.dat";

        DirectoryWatcher saveFileWatcher = new DirectoryWatcher(directory, targetFile, () -> {
            try {
                UpdateTurn();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        Thread watcherThread = new Thread(saveFileWatcher);
        watcherThread.setDaemon(true);
        watcherThread.start();
    }


    private void UpdateTurn() throws IOException, ClassNotFoundException {
        String s = EvaSaveUtil.getSaveGamePath();
        CurrentState = EvaSaveUtil.ReadGameState(s.replace("\\", "/")+"currentgame.dat");
        if (CurrentState.GameQueueList != null && !CurrentState.GameQueueList.isEmpty()) {
            CurrentState.Player = CurrentState.GameQueueList.get(0);
            List<String> players = new ArrayList<>();
            players.add(CurrentState.GameQueueList.get(0));
            for (String player : CurrentState.GameQueueList) {
                if (!players.contains(player)) {players.add(player);}
            }
            CurrentState.GameQueueList = players;
        } else
        CurrentState.Player = CurrentState.NextPlayer;
        if (!Players.contains(CurrentState.Player) && !PlayerIsGM(CurrentState.Player)) {
            String S = CurrentState.Player;
            CurrentState.Player = "GM";
            CurrentState.Phase = "";
        }
        CurrentState.NextPlayer = "GM";
        TurnOrderLabel.setText("Player "+CurrentState.Player+" "+CurrentState.Phase+" "+CurrentAction);
        ReadFromGameState();
        SetUpTurn();
        ResetAction();
        Update();
    }





    private void ResetAction() {
        if (CurrentState.Phase.equals("")) CurrentAction = "None";
        else CurrentAction = CurrentState.Phase;
        CurrentSubAction = "";
        CurrentMovement = null;
    }

    private int getXFromBoard(int x){
        return 10+x*20;
    }
    private int getYFromBoard(int y){
        return 10+y*20;
    }

    private void MoveUnitToPosition(BaseUnit Eva){
        int x = Eva.getX();
        int y = Eva.getY();
        Slider hSlider = (Slider) GameBoard.Container.getBottom(); // Get horizontal slider
        Slider vSlider = (Slider) GameBoard.Container.getRight();  // Get vertical slider
        double hScroll = hSlider.getValue();
        double vScroll = vSlider.getValue();
        Eva.UnitCircle.setLayoutX(getXFromBoard(x)-hScroll);
        Eva.UnitCircle.setLayoutY(getYFromBoard(y)+vScroll);
    }


    private void MoveUnitToPosition(WeaponObject weapon){
        List<WeaponObject> objlist = getItemsOnLocation(weapon.getX(), weapon.getY());
        int modx=0;
        int mody=0;
        switch (objlist.indexOf(weapon)) {
            case 1 -> {
                modx = -5;
            }
            case 2 -> {
                modx = -5;
                mody = 5;
            }
            case 3 -> {
                mody = -5;
            }
            case 4 -> {
                modx = -5;
                mody = -5;
            }
            case 5 -> {
                mody = 5;
            }
            case 6 -> {
                modx = 5;
                mody = -5;
            }
            case 7 -> {
                modx = 5;
            }
            case 8 -> {
                modx = 5;
                mody = 5;
            }
            default -> {
            }
        }
        int x = weapon.getX();
        int y = weapon.getY();
        Slider hSlider = (Slider) GameBoard.Container.getBottom(); // Get horizontal slider
        Slider vSlider = (Slider) GameBoard.Container.getRight();  // Get vertical slider
        double hScroll = hSlider.getValue();
        double vScroll = vSlider.getValue();
        weapon.ItemCircle.setLayoutX(getXFromBoard(x)-hScroll+modx);
        weapon.ItemCircle.setLayoutY(getYFromBoard(y)+vScroll+mody);
    }


    private void drawArrow(int sx, int sy, int ex, int ey) {
        arrow.setStartEnd(sx, sy, ex, ey);
        UpdateArrowPosition();
    }
    private void UpdateArrowPosition() {
        if (arrow == null) return;
        Slider hSlider = (Slider) GameBoard.Container.getBottom();
        Slider vSlider = (Slider) GameBoard.Container.getRight();
        double hScroll = hSlider.getValue();
        double vScroll = -vSlider.getValue();
        arrow.UpdateArrow(hScroll, vScroll);
    }


    private void UpdatePositions(){
        UpdateArrowPosition();
        for (BaseUnit unit : UnitsList) {
                MoveUnitToPosition(unit);
        }
        for (WeaponObject weapon : WeaponsWorld) {
            MoveUnitToPosition(weapon);
        }
    }
    private void UpdateVisible(){
        for (BaseUnit unit : UnitsList) {
          if (unit.UnitCircle.isVisible() && isFogged(GameBoard.getSector(unit.getX(), unit.getY()))) {
                  unit.UnitCircle.setVisible(false);
            }
            if (!unit.UnitCircle.isVisible() && !isFogged(GameBoard.getSector(unit.getX(), unit.getY()))) {
                unit.UnitCircle.setVisible(true);
            }
        }
        for (WeaponObject weapon : WeaponsWorld) {
            if (weapon.ItemCircle.isVisible() && isFogged(GameBoard.getSector(weapon.getX(), weapon.getY()))) {
                weapon.ItemCircle.setVisible(false);
            }
            if (!weapon.ItemCircle.isVisible() && !isFogged(GameBoard.getSector(weapon.getX(), weapon.getY()))) {
                weapon.ItemCircle.setVisible(true);
            }
        }
    }


    private List<WeaponObject> getItemsOnLocation(Sector sector){
        List<WeaponObject> list = new ArrayList<>();
        for (WeaponObject obj : WeaponsWorld) {
            if (obj.getX() == sector.x && obj.getY() == sector.y) list.add(obj);
        }
        return list;
    }
    private List<WeaponObject> getItemsOnLocation(int x, int y){
        List<WeaponObject> list = new ArrayList<>();
        for (WeaponObject obj : WeaponsWorld) {
            if (obj.getX() == x && obj.getY() == y) list.add(obj);
        }
        return list;
    }

    private boolean CurrentPlayerIsGM() {
        if (CurrentPlayer.equals("GM") || SummonPlayers.contains(CurrentPlayer)) return true;
        else return false;
    }
    private boolean PlayerIsGM(String Player) {
        if (Player.equals("GM") || SummonPlayers.contains(Player)) return true;
        else return false;
    }
    private void ApplyPlayer(){
        WeaponShow();
        if (CurrentPlayerIsGM()) {
            GMScreen.isHidden = true;
            GMScreen.moveSubScene(); } else {
            GMScreen.isHidden = false;
            GMScreen.moveSubScene();
        }
        if (CurrentState.Phase.equals("SetUp")) {
            CurrentAction = CurrentState.Phase;
            UpdatePlayerView();
            UpdateCurrentLables();
            EndTurnButton.setText("Set Up");
            if (!CurrentPlayerIsGM()) {
                for (String S : Players) {
                    if (CurrentPlayer.equals(S)) {
                        showSubScene(PlayerSetUpMenus.get(Players.indexOf(S)));
                    }
                }
            } else {showSubScene(GMSubScene);}
            return;
        }
        if (CurrentPlayer.equals(CurrentState.Player) && getUnitFromPlayer(CurrentPlayer).NeedsDoubleEdgeCheck() != null) {
            Weapon Edge = getUnitFromPlayer(CurrentPlayer).NeedsDoubleEdgeCheck();
            String S = Edge.Technology2.toString().toLowerCase();
            SwitchEdgeButton.setText("switch to "+S);
            String S1 = Edge.Technology.toString().toLowerCase();
            TechnologySwitchLabel.setText("Current Technology: "+S1);
            showSubScene(DoubleEdgeCheckSubScene);
            SetUpActionLabels();
            EndTurnButton.setText("Apply Edge");
            return;
        }
        if (CurrentPlayer.equals(CurrentState.Player) && !CurrentState.Phase.equals("")) {
            CurrentAction = CurrentState.Phase;
            EndTurnButton.setText("Progress");
            switch (CurrentState.Phase) {
                case "Defend" -> {
                    SetUpActionLabels();
                    WeaponCheck();
                    checkWeaponPotential();
                    showSubScene(DefenceSubScene);
                }
                case "DefendHelp" -> {
                    showSubScene(HelpSubScene);
                }
                case "Wound" -> {
                    showSubScene(WoundSubScene);
                }
                case "WoundDrama" -> {

                }
                case "DramaMiss" -> {
                    //   showSubScene(DramaMissSubScene)
                }
                case "AtkOfOp" -> {
                    EndTurnButton.setText("End Turn");
                    if (CurrentPlayerIsGM()) showSubScene(PlayerMenus.get(PlayerMenus.size()-1));
                    else showSubScene(PlayerMenus.get(Players.indexOf(CurrentPlayer)));
                    UpdatePlayerView();
                }
            }
            return;
        }
            if (CurrentPlayerIsGM()) {
                if (!getUnitFromPlayer("GM").isTurnDone()) {
                showSubScene(PlayerMenus.get(PlayerMenus.size()-1));
            }
            else showSubScene(GMSubScene);
        } else {
            GMScreen.isHidden = false;
            GMScreen.moveSubScene();
            for (String S : Players) {
                if (CurrentPlayer.equals(S)) {
                    showSubScene(PlayerMenus.get(Players.indexOf(S)));
                }
            }
        }

    }



    public void WeaponShow() {
        if (CurrentPlayerIsGM()) {
            Weapon w1 = WeaponCompareList(CurrentChosenWeapon, getUnitFromPlayer(CurrentPlayer).GetWeaponList());
            if (w1 == null) {
                CurrentChosenWeapon = SwitchToNextWeapon(getUnitFromPlayer(CurrentPlayer), null) ;
            } else CurrentChosenWeapon = w1;

        } else {
            for (String S : Players) {
                if (CurrentPlayer.equals(S)) {
                    Weapon w1 = WeaponCompareList(CurrentChosenWeapon, getUnitFromPlayer(CurrentPlayer).GetWeaponList());
                    if (w1 == null) {
                        CurrentChosenWeapon = SwitchToNextWeapon(getUnitFromPlayer(CurrentPlayer), null) ;
                    } else CurrentChosenWeapon = w1;
                }
            }
        }
    }


    public boolean WeaponCompare(Weapon w1, Weapon w2) {
        if (w1 == null || w2 == null) return false;
        return w1.Name.equals(w2.Name) && w1.Technology.equals(w2.Technology) && w1.AmmoCapacity == w2.AmmoCapacity;
    }
    public Weapon WeaponCompareList(Weapon w1, List<Weapon> w2) {
        for (Weapon w3 : w2) {
            if (WeaponCompare(w1, w3) || (w3.hasSubWeapon() && WeaponCompare(w1, w3.getSubWeapon())))
                return w3;
        }
        return null;
    }

    private void RandomisePosition(){
        for (BaseUnit unit : UnitsList) {
            if (unit instanceof Evangelion Eva) {
            Eva.state.x = RandomGenerator.nextInt(0, GameBoard.boardwidth);
            Eva.state.y = RandomGenerator.nextInt(0, GameBoard.boardheight); }
            if (unit instanceof Angel angel) {
                angel.state.x = RandomGenerator.nextInt(0, GameBoard.boardwidth);
                angel.state.y = RandomGenerator.nextInt(0, GameBoard.boardheight);
            }
        }
    }

    private void SetPosition(BaseUnit unit, int x, int y){
            unit.setX(x);
            unit.setY(y);
    }
    private void SetPositionToStart(){
        for (BaseUnit unit : UnitsList) {
            SetPosition(unit, -1, -1);
        }
    }




    private void BlowUpSector(Sector sector) {
        sector.setType(Destroyed);
        for (WeaponObject w: getItemsOnLocation(sector)) {
            discard(w);
        }
    }
    private void BlowUpArea() {
        for (Sector sector : BlowUpSectorList) {
        BlowUpSector(sector);
        }
    }


    private BaseUnit getUnit(int x, int y){
        for (BaseUnit Eva : UnitsList) {
            if (Eva.getX() == x && Eva.getY() == y) return Eva;
        }
        return null;
    }

    private void DropWeapon(BaseUnit unit, Weapon weapon) {
        WeaponObject obj = createWeaponObject(weapon, unit.getX(), unit.getY());
        obj.setWeaponEffectsFromUnit(unit);
    }
    private void DropWeapon(BaseUnit unit, Weapon weapon, int x, int y) {
        WeaponObject obj = createWeaponObject(weapon, x, y);
        obj.setWeaponEffectsFromUnit(unit);
    }

    public WeaponObject NextItem(WeaponObject weapon, List<WeaponObject> list) {
        if (list.isEmpty()) return null;
        if (weapon == null) {
            return list.get(0);
        }
        int x = 1;
        WeaponObject w2;
        if (list.contains(weapon)) {
            if (list.indexOf(weapon) + x == list.size()) {
                w2 =  list.get(0);
            } else {
                w2 = list.get(list.indexOf(weapon) + x);
            }
        } else {
            w2 = list.get(0);
        }
        return w2;
    }

    private void createGameBoard(int x, int y){
        GridPane gridPane = new GridPane();

        // Create the scrollContainer (BorderPane) for the game board
        BorderPane scrollContainer = new BorderPane();

        // Initialize the GameBoard with both GridPane and BorderPane (scrollContainer)
        GameBoard = new GameBoard(gridPane, scrollContainer, x, y);

        // Create viewport container with fixed size
        SizeDelta = Math.min(x * 20, 800);
        viewport.setPrefSize(SizeDelta, Math.min(y * 20, 600));

        viewport.setStyle("-fx-background-color: lightgray;");

        // Add battle board to viewport
        viewport.getChildren().add(gridPane);

        // Create scroll sliders
        Slider hSlider = new Slider();
        Slider vSlider = new Slider();
        hSlider.setOrientation(Orientation.HORIZONTAL);
        vSlider.setOrientation(Orientation.VERTICAL);

        // Set up the scrollContainer
        scrollContainer.setCenter(viewport);
        scrollContainer.setBottom(hSlider);
        scrollContainer.setRight(vSlider);

        scrollContainer.setLayoutX(140);
        scrollContainer.setLayoutY(40);

        // Add the scrollContainer to the creatorPane
        gamePane.getChildren().add(scrollContainer);

        // Existing sector setup
        for (Sector sector : GameBoard.sectors) {
            sector.setType(Blank);
        }

        fromBattlefieldToBoardSectors();
        GameBoard.UpdateBoardColors();
        GameBoard.Board.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                EventTarget target = mouseEvent.getTarget();
                if(target instanceof Sector sector){
                    ClickedSector = sector;
                    if (isFogged(ClickedSector)) {
                        return;
                    }
                    int SectorX = sector.x;
                    int SectorY = sector.y;
                    ClickedUnit = getUnit(SectorX, SectorY);
                    BaseUnit CurrentUnit = getCurrentUnit();
                    TargetObject = NextItem(TargetObject, getItemsOnLocation(ClickedSector));
                    ClickedUpdate();
                    if (CurrentUnit != null){
                        WeaponCheck();
                    if (ClickedUnit != null && ClickedUnit.equals(CurrentUnit))
                    {
                        //Mawrak's edits
                        if (CurrentAction.equals("Attack"))
                        {
                            PreserveProgress = false;
                            setEndTurnButtonBasedOnAmmoAndStamina();
                        }

                        System.out.println("Self target.");
                        Update();
                        return;
                    }
                    if (CurrentState.Phase.equals("SetUp") && CurrentPlayer.equals("GM") && ClickedUnit == null) {
                        for (BaseUnit unit : UnitsList) {
                            if (unit.getX() == -1 && unit.getY() == -1) {
                                SetPosition(unit, sector.x, sector.y);
                                if (UnitsList.indexOf(unit) != UnitsList.size()-1)
                                EndTurnButton.setText("Set Up"); else EndTurnButton.setText("End Turn");
                                Update();
                                break;
                            } else EndTurnButton.setText("End Turn");
                        }
                    } else
                    if (CurrentAction.equals("GMApply")) {
                        if (ClickedUnit != null || CurrentSubAction.equals("ApplyAll") || CurrentSubAction.equals("SupportSector") && ClickedSector != null) {
                        EndTurnButton.setText("Apply");
                        }
                        else EndTurnButton.setText("End Turn");
                    } else if (CurrentAction.equals("Other")) {
                            if (CurrentSubAction.equals("Drop")) {
                                if (EvaCalculationUtil.isAdjecent(getCurrentUnit(), ClickedSector) && getCurrentUnit().canDrop(CurrentChosenWeapon)) {
                                    EndTurnButton.setText("Drop");
                                    if (arrow == null) arrow = new Arrow(viewport);
                                    drawArrow(getXFromBoard(CurrentUnit.getX()), getYFromBoard(CurrentUnit.getY()), getXFromBoard(sector.x), getYFromBoard(sector.y));
                                    arrow.SetColor(Color.BLUE);
                                } else {
                                    ResetArrow();
                                    EndTurnButton.setText("End Turn");
                                }
                            }
                        if (CurrentSubAction.equals("Pick Up Right")) {
                            if (EvaCalculationUtil.isAdjecent(getCurrentUnit(), ClickedSector) && !getItemsOnLocation(ClickedSector).isEmpty()) {
                                if (!getCurrentUnit().UsedTactical() || getCurrentUnit().getStamina() > 0)
                                EndTurnButton.setText("Pick Up"); else  EndTurnButton.setText("No Stamina");
                                Evangelion eva = getCurrentEvangelion();
                                assert eva != null;
                                if (eva.getProhibitedActions().contains("ItemR")) EndTurnButton.setText("No Hand");
                                if (eva.getProhibitedActions().contains("TwoHandR") && TargetObject.getWeapon().getHands() == 2) EndTurnButton.setText("No Hand");
                                if (arrow == null) arrow = new Arrow(viewport);
                                drawArrow(getXFromBoard(CurrentUnit.getX()), getYFromBoard(CurrentUnit.getY()), getXFromBoard(sector.x), getYFromBoard(sector.y));
                                arrow.SetColor(Color.BLUE);
                            } else {
                                ResetArrow();
                                EndTurnButton.setText("End Turn");
                            }
                        }
                        if (CurrentSubAction.equals("Pick Up Left")) {
                            if (EvaCalculationUtil.isAdjecent(getCurrentUnit(), ClickedSector) && !getItemsOnLocation(ClickedSector).isEmpty()) {
                                TargetObject = NextItem(TargetObject, getItemsOnLocation(ClickedSector));
                                if (!getCurrentUnit().UsedTactical() || getCurrentUnit().getStamina() > 0)
                                    EndTurnButton.setText("Pick Up"); else EndTurnButton.setText("No Stamina");
                                Evangelion eva = getCurrentEvangelion();
                                assert eva != null;
                                if (eva.getProhibitedActions().contains("ItemL")) EndTurnButton.setText("No Hand");
                                if (eva.getProhibitedActions().contains("TwoHandL") && TargetObject.getWeapon().getHands() == 2) EndTurnButton.setText("No Hand");
                                if (arrow == null) arrow = new Arrow(viewport);
                                drawArrow(getXFromBoard(CurrentUnit.getX()), getYFromBoard(CurrentUnit.getY()), getXFromBoard(sector.x), getYFromBoard(sector.y));
                                arrow.SetColor(Color.BLUE);
                            } else {
                                ResetArrow();
                                EndTurnButton.setText("End Turn");
                            }
                        }
                            if (WeaponsNamesList.contains(CurrentSubAction)) {
                                try {
                                Weapon weapon = EvaSaveUtil.ReadWeapon(filepath+"/Weapons/"+CurrentSubAction+".txt");
                                if (weapon.getCost() <= ((Evangelion) getCurrentUnit()).getRequisition()) {
                                ResetArrow();
                                if (GameBoard.SupportCheck(ClickedSector)){
                                UpdatePlayerView();
                                EndTurnButton.setText("Item");
                                GameBoard.DrawSquare(ClickedSector, 0, Color.YELLOW);
                            } else {
                            EndTurnButton.setText("End Turn");
                        }
                                } else EndTurnButton.setText("Too Expensive");
                                } catch (IOException ignored) {}
                    }
                    }
                    else if (CurrentAction.equals("Move") && ClickedUnit == null && CurrentUnit.getStamina() > 0 &&
                            ((CurrentSubAction.equals("Take Cover") && GameBoard.CoverCheck(ClickedSector)) || (CurrentSubAction.equals("Maneuver") && GameBoard.isPossibleReachLocation(ClickedSector, CurrentUnit, 1))
                                    || ((!(CurrentSubAction.equals("Maneuver")) && !(CurrentSubAction.equals("Take Cover"))) && (GameBoard.isPossibleMovementLocation(ClickedSector, CurrentUnit)
                                    || GameBoard.isPossibleDoubleLocation(ClickedSector, CurrentUnit))))) {
                        EndTurnButton.setText("Progress");
                        if (arrow == null) arrow = new Arrow(viewport);
                        drawArrow(getXFromBoard(CurrentUnit.getX()), getYFromBoard(CurrentUnit.getY()), getXFromBoard(sector.x), getYFromBoard(sector.y));
                        int cost = GameBoard.isPossibleDoubleLocation(ClickedSector, CurrentUnit) ? 2 : 1;
                        CurrentMovement = new Movement(CurrentUnit.getPlayerName(), CurrentUnit.getX(), CurrentUnit.getY(), SectorX, SectorY, cost);
                        arrow.SetColor(Color.BLACK);
                    }
                    else if (CurrentChosenWeapon != null && CurrentChosenWeapon.isWeapon() && CurrentAction.equals("AtkOfOp")
                    && ClickedUnit != null && (CurrentState.Action instanceof Movement movement && ClickedUnit.getPlayerName().equals(movement.UnitActor)))
                    {
                        checkPotentialAttackEffect();
                        if (CurrentChosenWeapon.getArea() > -1) {
                            EndTurnButton.setText("No Area");
                            return;
                        }
                        if (CurrentChosenWeapon.getLine()) {
                            EndTurnButton.setText("No Line");
                            return;
                        }
                        setEndTurnButtonBasedOnAmmoAndStamina("Progress");
                        TargetList = new ArrayList<>();
                        BlowUpSectorList = new ArrayList<>();
                        if (arrow == null) arrow = new Arrow(viewport);
                        drawArrow(getXFromBoard(CurrentUnit.getX()), getYFromBoard(CurrentUnit.getY()), getXFromBoard(sector.x), getYFromBoard(sector.y));
                        arrow.SetColor(Color.RED);
                        Target = ClickedUnit;
                    }
                    else if (CurrentChosenWeapon != null && CurrentChosenWeapon.isWeapon() && CurrentAction.equals("Attack")
                    && ClickedUnit != null && !(getCurrentUnit().UsedAttack()) && !CurrentChosenWeapon.getLine()
                    && (getCurrentUnit().getStamina() > 0) && CurrentChosenWeapon.getArea() < 0 &&
                    GameBoard.isPossibleReachLocation(ClickedSector, CurrentUnit, CurrentUnit.getMaxRange(CurrentChosenWeapon)))
                    {
                        System.out.println("attack select 1");
                            setEndTurnButtonBasedOnAmmoAndStamina("Progress");
                            checkPotentialAttackEffect();
                            TargetList = new ArrayList<>();
                            BlowUpSectorList = new ArrayList<>();
                            if (arrow == null) arrow = new Arrow(viewport);
                            drawArrow(getXFromBoard(CurrentUnit.getX()), getYFromBoard(CurrentUnit.getY()), getXFromBoard(sector.x), getYFromBoard(sector.y));
                            arrow.SetColor(Color.RED);
                            Target = ClickedUnit;
                        }
                    else if (CurrentChosenWeapon != null && CurrentChosenWeapon.isWeapon() && (getCurrentUnit().getStamina() > 0) && CurrentAction.equals("Attack") && !(getCurrentUnit().UsedAttack()) && CurrentChosenWeapon.getArea() > -1 &&
                    GameBoard.isPossibleReachLocation(ClickedSector, CurrentUnit, CurrentUnit.getMaxRange(CurrentChosenWeapon)))
                        {
                            System.out.println("attack select 2");
                            setEndTurnButtonBasedOnAmmoAndStamina("Progress");
                            checkPotentialAttackEffect();
                            BlowUpSectorList = new ArrayList<>();
                            if (arrow == null) arrow = new Arrow(viewport);
                            drawArrow(getXFromBoard(CurrentUnit.getX()), getYFromBoard(CurrentUnit.getY()), getXFromBoard(sector.x), getYFromBoard(sector.y));
                            arrow.SetColor(Color.RED);
                            UpdatePlayerView();
                           // GameBoard.ShowPossibleAttackRange(CurrentUnit, CurrentChosenWeapon);
                            ShowAreaAttack();
                        }
                    else if (CurrentChosenWeapon != null && CurrentChosenWeapon.isWeapon() && (getCurrentUnit().getStamina() > 0) && CurrentAction.equals("Attack")
                    && !(getCurrentUnit().UsedAttack()) && CurrentChosenWeapon.getLine() &&
                    GameBoard.isPossibleLineReachLocation(ClickedSector, CurrentUnit, CurrentUnit.getMaxRange(CurrentChosenWeapon)))
                    {
                        System.out.println("attack select 3");
                        setEndTurnButtonBasedOnAmmoAndStamina("Progress");
                        checkPotentialAttackEffect();
                        BlowUpSectorList = new ArrayList<>();
                        if (arrow == null) arrow = new Arrow(viewport);
                        int maxReach = CurrentUnit.getMaxRange(CurrentChosenWeapon);
                        int unitX = CurrentUnit.getX();
                        int unitY = CurrentUnit.getY();
                        int dx = sector.x - unitX;
                        int dy = sector.y - unitY;
                        int dxStep = Integer.signum(dx);
                        int dyStep = Integer.signum(dy);
                        int endX = unitX + dxStep * maxReach;
                        int endY = unitY + dyStep * maxReach;
                        while (!GameBoard.OnBoard(endX, endY)) {
                            endX -= dxStep;
                            endY -= dyStep;
                        }
                        drawArrow(getXFromBoard(unitX), getYFromBoard(unitY), getXFromBoard(endX), getYFromBoard(endY));
                        arrow.SetColor(Color.RED);
                        UpdatePlayerView();
                       // GameBoard.ShowPossibleAttackRange(CurrentUnit, CurrentChosenWeapon);
                        ShowLineAttack(unitX, unitY, endX, endY, dxStep, dyStep);
                    }
                    //Mawrak's edits
                    else if (CurrentAction.equals("Attack"))
                        {
                            System.out.println("attack select 4 no target");
                            setEndTurnButtonBasedOnAmmoAndStamina();
                            ResetArrow();
                            ClickedSector = null;
                            PreserveProgress = false;
                        }

                    else if (CurrentAction.equals("ProgressAttack"))
                    {

                    }
                    else Update();

                    }
                }
            }
        });

    }

    private void fromBoardtoBattlefield() {
        Battlefield.sizeX = GameBoard.boardwidth;
        Battlefield.sizeY = GameBoard.boardheight;
        if (GameBoard.sectors == null) return;
        List<Triple<Integer, Integer, String>> newlist = new ArrayList<>();
        for (Sector sector : GameBoard.sectors) {
            if (!sector.getType().Name.equals("Blank")) {
                int x = sector.x;
                int y = sector.y;
                String name = sector.getType().Name;
                Triple<Integer, Integer, String> triple = new Triple<>(x, y , name);
                if (!newlist.contains(triple)) newlist.add(triple);
            }
        }
        Battlefield.SpecialTiles = newlist;
    }
    private void fromBattlefieldToBoardSectors() {
        if (Battlefield.SpecialTiles == null) return;
        for (Triple<Integer, Integer, String> triple : Battlefield.SpecialTiles) {
            Sector sector = GameBoard.getSector(triple.getFirst(), triple.getSecond());
            if (sector != null) for (SectorType sectorType : SectorTypesList) {
                if (triple.getThird().equals(sectorType.Name)) sector.setType(sectorType);
            }
        }
        GameBoard.UpdateBoardColors();
    }
    private void setUpSliders() {
        Slider hSlider = (Slider) GameBoard.Container.getBottom(); // Get horizontal slider
        Slider vSlider = (Slider) GameBoard.Container.getRight();  // Get vertical slider

        double boardPixelWidth = GameBoard.boardwidth * 20;
        double boardPixelHeight = GameBoard.boardheight * 20;

        // Set horizontal slider range
        double hSliderMax = Math.max(0, boardPixelWidth - viewport.getPrefWidth());
        hSlider.setMin(0);
        hSlider.setMax(hSliderMax);

        // Set vertical slider range
        double vSliderMax = Math.max(0, boardPixelHeight - viewport.getPrefHeight());
        vSlider.setMin(-vSliderMax);
        vSlider.setMax(0);

        // Disable sliders if their max value is 0 (no need for scrolling)
        hSlider.setDisable(hSliderMax == 0);
        vSlider.setDisable(vSlider.getMin() == 0);

        // Optionally hide sliders if they are disabled
        hSlider.setVisible(hSliderMax > 0);
        vSlider.setVisible(vSliderMax > 0);

        // Bind grid position to slider values
        GameBoard.Board.layoutXProperty().bind(hSlider.valueProperty().multiply(-1));
        GameBoard.Board.layoutYProperty().bind(vSlider.valueProperty());

        hSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            UpdatePositions();
        });

        vSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            UpdatePositions();
        });

        // Clip viewport to prevent overflow
        Rectangle clip = new Rectangle(viewport.getPrefWidth(), viewport.getPrefHeight());
        clip.widthProperty().bind(viewport.widthProperty());
        clip.heightProperty().bind(viewport.heightProperty());
        viewport.setClip(clip);
    }

    private EvaButton createNewWeaponButton(String name) {
        EvaButton button = new EvaButton(name);
        button.setPrefHeight(30);
        button.setPrefWidth(80);
        button.setPosition(10, 10);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    WeaponCreator EvaManager = new WeaponCreator();
                    EvaManager.createNewMaker(gameStage, CurrentPlayer);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        return button;
    }
    public void checkWeaponPotential(){
        BaseUnit unit = getCurrentUnit();
        WeaponCheck();
        Weapon weapon = CurrentChosenWeapon;
        weapon.resetPotential();
        if (unit == null) return;
        if (unit instanceof Evangelion eva) {
        for (Upgrade upg : eva.type.CurrentWeaponUpgrades) {
            CurrentChosenWeapon.PotentialProperties.addAll(upg.PotentialProperties);
            CurrentChosenWeapon.AreaPotential = Math.max(CurrentChosenWeapon.AreaPotential, upg.PotentialArea);
            CurrentChosenWeapon.DefensivePotential = Math.max(CurrentChosenWeapon.DefensivePotential, upg.PotentialDefensive);
            CurrentChosenWeapon.PenetrationPotential += upg.PotentialDefensive;
        }
        }
    }
    public void checkPotentialAttackEffect(){
        BaseUnit unit = getCurrentUnit();
        if (unit == null || unit instanceof ChazaqielSummon) return;
        unit.RemovePotentialEffects();
        WeaponCheck();
        Weapon weapon = CurrentChosenWeapon;
        checkWeaponPotential();
        if (ATPower != null) return;
        Sector sec = ClickedSector;
        BaseUnit unit1 = ClickedUnit;
        if (sec != null) {
        if (weapon.isReach() && !isThrow() && EvaCalculationUtil.isAdjecent(unit, sec)) {
            StateEffect effect = new StateEffect("Reach Adjecent Penalty");
            effect.AccuracyDelta = -15;
            effect.Condition = StateEffect.ExpirationCondition.POTENTIAL;
            effect.WeaponSpecific = weapon;
            unit.AddEffect(effect);
        }

            if (unit.hasUpgrade("Return Fire")) {
                boolean checkadjecent = false;
                for (Evangelion eva : EvangelionList) {
                    if (EvaCalculationUtil.isAdjecent(eva, ClickedSector) && !eva.equals(unit)) checkadjecent = true;
                }
                if (checkadjecent) {
                StateEffect effect = new StateEffect("Return Fire");
                effect.AttackStrengthDelta = 1;
                effect.RangedStrengthDelta = 1;
                effect.Condition = StateEffect.ExpirationCondition.POTENTIAL;
                unit.AddEffect(effect);
                }
            }




        if (weapon.isCQB() && EvaCalculationUtil.isAdjecent(unit, sec)) {
            StateEffect effect = new StateEffect("CQB Adjecent Bonus");
            effect.PenetrationDelta = 1;
            effect.Condition = StateEffect.ExpirationCondition.POTENTIAL;
            effect.WeaponSpecific = weapon;
            unit.AddEffect(effect);
        }

        if (weapon.Ranged && ((CurrentAction.equals("AtkOfOp") && weapon.getMinRange() > 1) || (!weapon.getLine() && EvaCalculationUtil.isInRange(unit, sec, weapon.getMinRange()-1)))) {
            StateEffect effect = new StateEffect("Ranged Too Close Penalty");
            effect.AccuracyDelta = -30;
            effect.Condition = StateEffect.ExpirationCondition.POTENTIAL;
            effect.WeaponSpecific = weapon;
            unit.AddEffect(effect);
        }



        if (weapon.isTelescopicSight() && !isBlitzOrFA() && weapon.isPrecise() && !EvaCalculationUtil.isInRange(unit, sec, 2)) {
            StateEffect effect = new StateEffect("Telescopic Precise Bonus");
            effect.AttackStrengthDelta = 1;
            effect.RangedStrengthDelta = 1;
            effect.Condition = StateEffect.ExpirationCondition.POTENTIAL;
            effect.WeaponSpecific = weapon;
            unit.AddEffect(effect);
        }

            if (weapon.isAutoLoader() && !weapon.isSpray() && EvaCalculationUtil.isInRange(unit, sec, 2)) {
               weapon.makePotentialSpray(); //FINISH THIS
            }

        }
        if (weapon.getHands() == 2 && unit instanceof Evangelion eva && !eva.hasFreeHand() && !eva.WeaponInSiegeFrame(weapon)) {
            StateEffect effect = new StateEffect("Two Handed Penalty");
            effect.AccuracyDelta = -20;
            effect.Condition = StateEffect.ExpirationCondition.POTENTIAL;
            unit.AddEffect(effect);
        }

        if (weapon.Ranged && unit1 != null && unit1.getNameEffects().contains("Prone")) {
            StateEffect effect = new StateEffect("vsProne");
            effect.AccuracyDelta = -20;
            effect.Condition = StateEffect.ExpirationCondition.POTENTIAL;
            unit.AddEffect(effect);
        }
        if ((weapon.Ranged || isThrow()) && getCurrentUnit() instanceof Evangelion eva && eva.hasUpgrade("Double Retina")) {
            StateEffect effect = new StateEffect("Double Retina");
            effect.RangeDelta = 1;
            effect.Condition = StateEffect.ExpirationCondition.POTENTIAL;
            unit.AddEffect(effect);
        }
        if ((unit.hasUpgrade("Bloodthirst") || unit.hasUpgrade("Feint")) && ((CurrentAction.equals("AtkOfOp") || CurrentSubAction.equals("Basic Attack")))) {
            StateEffect effect = new StateEffect("Bloodthirst");
            effect.AttackStrengthDelta = 1;
            effect.RangedStrengthDelta = 1;
            effect.Condition = StateEffect.ExpirationCondition.POTENTIAL;
            unit.AddEffect(effect);
        }
        if (unit.getNameEffects().contains("RepositionBonus") && isBlitz()) {
            StateEffect effect = new StateEffect("RepositionFinalBonus");
            effect.AttackStrengthDelta = 2;
            effect.RangedStrengthDelta = 2;
            effect.Condition = StateEffect.ExpirationCondition.POTENTIAL;
            unit.AddEffect(effect);
        }

        if (isBlitz()) {
            StateEffect effect = new StateEffect("BlitzBonus");
            int x = 2*(Math.max(weapon.getHands(), 1)) + (weapon.isSwift() ? 2 : 0);
            effect.AttackStrengthDelta = x;
            effect.RangedStrengthDelta = x;
            if (weapon.isActuallySwift() && unit.hasUpgrade("Onslaught")) {
                weapon.PenetrationPotential += 1;
            }
            effect.Condition = StateEffect.ExpirationCondition.POTENTIAL;
            unit.AddEffect(effect);
        }

        if (CurrentSubAction.equals("Overwatch")) {
            StateEffect effect = new StateEffect("Overwatch");
            int n = 1;
            if (weapon.isBalanced()) n = 2;
            effect.AttackStrengthDelta = n;
            effect.RangedStrengthDelta = n;
            effect.Condition = StateEffect.ExpirationCondition.POTENTIAL;
            unit.AddEffect(effect);
        }

        if (isThrow() && !weapon.Ranged) {
            if (weapon.isThrowingProperty() || weapon.isThrowingCustomisation()) {
            StateEffect effect = new StateEffect("ThrowPotential");
            effect.RangeDelta= weapon.getThrowBonus();
            effect.Condition = StateEffect.ExpirationCondition.POTENTIAL;
            unit.AddEffect(effect);
            }
        }



        if (isFA()) {
            weapon.AreaPotential = 1;
            if (weapon.isActuallySwift() && unit.hasUpgrade("Onslaught")) {
                weapon.PenetrationPotential += 1;
            }
        }




        getAmmoCost();
        getStaminaCost();
        setEndTurnButtonBasedOnAmmoAndStamina(EndTurnButton.getText());
        unit.UpdateCalcWeapon(weapon);
        UpdateUnitLabels();
        UpdateAttackTestLabels();
        if (weapon.getArea() > -1) ShowAreaAttack();
    }


    public void ShowAreaAttack(){
        if (CurrentChosenWeapon != null && CurrentChosenWeapon.getArea() >= 0 && ClickedSector != null) {
        int Area = CurrentChosenWeapon.getArea();
        BlowUpSectorList = GameBoard.DrawSquare(ClickedSector, Area, Color.YELLOW);
        Target = null;
        TargetList = new ArrayList<>();
        for (BaseUnit unit : UnitsList) {
            if (EvaCalculationUtil.isInRange(unit, ClickedSector, Area)) {
                TargetList.add(unit);
            }
        }
        }
    }
    public void ShowLineAttack(int startx, int starty, int endx, int endy, int stepx, int stepy){
        if (CurrentChosenWeapon != null && CurrentChosenWeapon.getLine()) {
            BlowUpSectorList =  GameBoard.DrawLine(startx, starty, endx, endy, stepx, stepy, Color.YELLOW);
            Target = null;
            TargetList = new ArrayList<>();
            int sx = startx;
            int sy = starty;
            int dx = Math.abs(endx - startx);
            int dy = Math.abs(endy - starty);
            int steps = Math.max(dx, dy);
            for (int i = 0; i <= steps; i++) {
                if (getUnit(sx, sy) != null && i != 0) TargetList.add(getUnit(sx, sy));
                if (sx != endx) sx += stepx;
                if (sy != endy) sy += stepy;
            }
        }
    }



    public void DoMove(){
        if (CurrentMovement != null ) {
            BaseUnit Mover = getUnitFromPlayer(CurrentMovement.UnitActor);
            Mover.setX(CurrentMovement.EndX);
            Mover.setY(CurrentMovement.EndY);
            Mover.RemoveOnMoveEffects();
        //    System.out.println("Moving "+Mover.getPlayerName()+ " to (" + Mover.getX() + ", " + Mover.getY() + ")");
        }
    }


    private void ClickedUpdate(){
        String s = "Clicked: ";
        if (ClickedUnit != null) {
            BaseUnit display = ClickedUnit;
            if (ClickedUnit instanceof ChazaqielSummon summon) {
                if (CurrentPlayerIsGM()) {
                    s = ""+ summon.getCopyName()+"'s Fake, "+summon.getPlayerName()+" "+summon.getStamina()+" // "; }
                else display = summon.CopyUnit;
            }
            LastClickedUnit = ClickedUnit;
            if (display instanceof Evangelion evangelion) s = "Evangelion = "+ evangelion.state.PlayerName+"'s Evangelion, ";
            if (display instanceof Angel) s = "Chazaqiel ";
        }
        if (TargetObject != null) {
            System.out.println("Clicked item location: "+ClickedSector.x+" "+ClickedSector.y);
            //  System.out.println("Weaponobject - " );
            s+="Item: "+TargetObject.getWeapon().Name+", ";
        }
        if (ClickedSector != null) s+="Sector: "+ClickedSector.getType().Name;



		
        CurrentClickedLabel.setText(s);
        UpdateUnitLabels();
		
		
    }


    private void createBackground() {
        Image backgroundImage = new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("/eva/background.png")), 256, 256, false, false);
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
        gamePane.setBackground(new Background(background));
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

    private void SetUpLabeledWeaponList(AnchorPane pane,  List<EvaButton> list, float startx, float starty) throws IOException {
        float x = startx;
        float y = starty;
        for (EvaButton button : list) {
            String s = button.getText();
            if (!s.equals("None") && !s.equals("Ammo")) {
                Weapon weapon = EvaSaveUtil.ReadStringWeapon(s);
                s += ", Hands: "+weapon.getHands()+", Small: "+weapon.isSmall()+", Cost: "+weapon.getCost();
            }
            button.setPosition(x, y);
            EvaLabel label = new EvaLabel(s);
            label.setLayoutX(x + button.getPrefWidth()+5);
            label.setLayoutY(y);
            pane.getChildren().add(label);
            pane.getChildren().add(button);
            y+=button.getPrefHeight()+10;
        }
    }


    private void initializeStage() {
        gamePane = new AnchorPane();

        //Mawrak's edits

		//ScrollPane scrollPane = new ScrollPane(gamePane); // Wrap in ScrollPane
		//scrollPane.setFitToWidth(true);
		//scrollPane.setFitToHeight(true);

        gameScene = new Scene(gamePane, WIDTH, HEIGHT);
        //gameScene = new Scene(scrollPane, WIDTH, HEIGHT); // this is to enable scrolling, but currently it is completely broken so no
        gameStage = new Stage();
        gameStage.setScene(gameScene);


		// Center the window on the primary screen
        gameStage.centerOnScreen();

    // Ensure the window stays within screen bounds
        gameStage.setOnShown(event -> {
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double x = Math.max(0, Math.min(gameStage.getX(), screenBounds.getMaxX() - gameStage.getWidth()));
        double y = Math.max(0, Math.min(gameStage.getY(), screenBounds.getMaxY() - gameStage.getHeight()));
        gameStage.setX(x);
        gameStage.setY(y);
    });


    }


    private EvaButton createSceneButton(String name, List<EvaButton> menu, EvaMenuSubScene subscene) {
        EvaButton button = new EvaButton(name);
        menu.add(button);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showSubScene(subscene);
            }
        });
        return button;
    }

    private void createApplyNameButton(String name, TextField field) {
        EvaButton button = new EvaButton(name);
        button.setPrefHeight(30);
        button.setPrefWidth(50);
        button.setPosition(80, 110);
        gamePane.getChildren().add(button);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                CurrentPlayer = field.getText();
                ResetAction();
                ResetArrow();
                ApplyPlayer();
                Update();
            }
        });
    }



    private List<EvaMenuSubScene> sceneToHide = new ArrayList<>();
    private void showSubScene(EvaMenuSubScene subScene) {
        List<EvaMenuSubScene> newlist = new ArrayList<>();
        newlist.addAll(sceneToHide);
        if (!sceneToHide.isEmpty()) {
            for (EvaMenuSubScene prevScene : sceneToHide) {
                if (prevScene.getLevel() >= subScene.getLevel() || prevScene instanceof DisplaySubSpace) {
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

