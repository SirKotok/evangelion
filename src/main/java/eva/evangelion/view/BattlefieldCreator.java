package eva.evangelion.view;


import eva.evangelion.gameboard.Battlefield;
import eva.evangelion.gameboard.GameBoard;
import eva.evangelion.gameboard.Sector;
import eva.evangelion.gameboard.SectorType;
import eva.evangelion.util.EvaSaveUtil;
import eva.evangelion.view.evainterface.EvaButton;
import eva.evangelion.view.evainterface.EvaLabel;
import eva.evangelion.view.evainterface.SliderBorerPane;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.jar.Attributes;

import javafx.geometry.Orientation;
import javafx.scene.control.Slider;
import javafx.scene.shape.Rectangle;
import kotlin.Triple;


public class BattlefieldCreator {
    private Battlefield Battlefield;
    private GameBoard Board;
    private AnchorPane creatorPane;
    private Scene creatorScene;
    private Stage creatorStage;
    private static final int WIDTH = 1100;
    private static final int HEIGHT= 900;

    private TextField WidthText = new TextField("20");
    private TextField HeightText = new TextField("30");
    private TextField NameText = new TextField("Name");
    EvaLabel CurrentSectorLabel = new EvaLabel("Current Sector: Blank");
    private SectorType Blank = SectorType.Blank;
    private SectorType CurrentSectorType;
    private Stage menuStage;
    private Pane viewport = new Pane();
    private List<EvaButton> SectorTypesButtons;
    private String filepath = EvaSaveUtil.getFilepath();
    public List<SectorType> SectorTypesList;
    public BattlefieldCreator() throws IOException {
        initializeStage();
    }



    public void createNewMaker(Stage menuStage, Battlefield field) throws IOException {

        SectorTypesButtons = new ArrayList<>();
        this.Battlefield = field;

        SectorTypesList = new ArrayList<>();
        SectorTypesList.add(Blank);

        CurrentSectorLabel.SetPosition(10, 5);
        creatorPane.getChildren().add(CurrentSectorLabel);

        creatorPane.getChildren().add(HeightText);
        creatorPane.getChildren().add(WidthText);
        creatorPane.getChildren().add(NameText);
        int y = 10;
        int x = 160;
        WidthText.setPrefSize(50, 30);
        WidthText.setLayoutX(x);
        WidthText.setLayoutY(y);
        HeightText.setPrefSize(50, 30);
        HeightText.setLayoutX(x+60);
        HeightText.setLayoutY(y);


        NameText.setPrefSize(50, 30);
        NameText.setLayoutX(x+260);
        NameText.setLayoutY(y);



        File folder = new File(filepath+"/SectorTypes");
        File[] listOfFiles = folder.listFiles();
        for (int m = 0; m < listOfFiles.length; m++) {
            if (listOfFiles[m].isFile()) {
                SectorTypesList.add(EvaSaveUtil.ReadSectorType(listOfFiles[m].getAbsolutePath()));
            }
        }

        for (SectorType sectorType : SectorTypesList) {
            createSectorSelectorButton(sectorType.Name, SectorTypesButtons, sectorType);
        }

        SetUpMenuList(creatorPane, SectorTypesButtons, 40, 40);


        createGameBoard(20, 30);

        EvaButton save = createSaveButton("Save");
        save.setPosition(x+310, y);
        EvaButton change = createChangeSizeButton("Size");
        change.setPosition(x+120, y);
        creatorPane.getChildren().add(change);
        creatorPane.getChildren().add(save);
        this.menuStage = menuStage;
        this.menuStage.hide();
        creatorStage.show();
     //   createBackground();

    }

    boolean debug = false;
    private EvaButton createChangeSizeButton(String name) {
        EvaButton button = new EvaButton(name);
        button.setPrefHeight(30);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                fromBoardtoBattlefield();
                try {
                    debug = true;

                    // Remove old game board and container
                    viewport.getChildren().remove(Board.Board);
                    creatorPane.getChildren().remove(Board.Container);

                    // Recreate game board with new dimensions
                    int width = Integer.parseInt(WidthText.getText());
                    int height = Integer.parseInt(HeightText.getText());
                    createGameBoard(width, height);

                    // Update battlefield sectors
                    fromBattlefieldToBoardSectors();
                } catch (NumberFormatException ignore) {
                    // Handle invalid input gracefully
                }
            }
        });
        return button;
    }

    private void fromBoardtoBattlefield() {
        Battlefield.sizeX = Board.boardwidth;
        Battlefield.sizeY = Board.boardheight;
        if (Board.sectors == null) return;
        List<Triple<Integer, Integer, String>> newlist = new ArrayList<>();
        for (Sector sector : Board.sectors) {
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
           Sector sector = Board.getSector(triple.getFirst(), triple.getSecond());
           if (sector != null) for (SectorType sectorType : SectorTypesList) {
                if (triple.getThird().equals(sectorType.Name)) sector.setType(sectorType);
            }
        }
        Board.UpdateBoardColors();
    }


    /*

     */
    private void createGameBoard(int x, int y) {
        // Create the GridPane for the game board
        GridPane gridPane = new GridPane();

        // Create the scrollContainer (BorderPane) for the game board
        BorderPane scrollContainer = new BorderPane();

        // Initialize the GameBoard with both GridPane and BorderPane (scrollContainer)
        Board = new GameBoard(gridPane, scrollContainer, x, y);

        // Create viewport container with fixed size
        viewport.setPrefSize(Math.min(x * 20, 600), Math.min(y * 20, 800));
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
        creatorPane.getChildren().add(scrollContainer);

        // Existing sector setup
        for (Sector sector : Board.sectors) {
            sector.setType(Blank);
        }
        Board.UpdateBoardColors();
        Board.Board.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                EventTarget target = mouseEvent.getTarget();
                if (target instanceof Sector) {
                    Sector sector = (Sector) target;
                    Board.setType(sector, CurrentSectorType);
                }
            }
        });

        // Set up sliders
        setUpSliders();
    }


    private void setUpSliders() {
        Slider hSlider = (Slider) Board.Container.getBottom(); // Get horizontal slider
        Slider vSlider = (Slider) Board.Container.getRight();  // Get vertical slider

        double boardPixelWidth = Board.boardwidth * 20;
        double boardPixelHeight = Board.boardheight * 20;

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
        Board.Board.layoutXProperty().bind(hSlider.valueProperty().multiply(-1));
        Board.Board.layoutYProperty().bind(vSlider.valueProperty());

        // Clip viewport to prevent overflow
        Rectangle clip = new Rectangle(viewport.getPrefWidth(), viewport.getPrefHeight());
        clip.widthProperty().bind(viewport.widthProperty());
        clip.heightProperty().bind(viewport.heightProperty());
        viewport.setClip(clip);
    }


    private void createBackground() {
        Image backgroundImage = new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("/eva/background.png")), 256, 256, false, false);
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
        creatorPane.setBackground(new Background(background));
    }


    private void UpdateLabel(){
        CurrentSectorLabel.setText("Current Sector: "+CurrentSectorType.Name);
    }


    private void createSectorSelectorButton(String name, List<EvaButton> menu, SectorType Sector) {
       EvaButton button = new EvaButton(name);
       button.setPrefHeight(30);
       menu.add(button);
           button.setOnAction(new EventHandler<ActionEvent>() {
             @Override
          public void handle(ActionEvent event) {
                if (CurrentSectorType != Sector) CurrentSectorType = Sector;
                UpdateLabel();
              }
           });
   }


    private EvaButton createSaveButton(String name){
        EvaButton button = new EvaButton(name);
        button.setPrefHeight(30);
        button.setPrefWidth(50);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
               try {
                    fromBoardtoBattlefield();
                    EvaSaveUtil.SaveBattlefield(EvaSaveUtil.getSavepath()+"\\Battlefields\\", NameText.getText(), Battlefield);
                } catch (IOException e) {
                   throw new RuntimeException(e);
                }
            }
        });
        return button;
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





  //  public void createNewMaker(Stage menuStage) {
   //     this.createNewMaker(menuStage, new Evangelion());
  //  }


    private void initializeStage() {
        creatorPane = new AnchorPane();
        creatorScene = new Scene(creatorPane, WIDTH, HEIGHT);
        creatorStage = new Stage();
        creatorStage.setScene(creatorScene);
    }







}
