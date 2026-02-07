package eva.evangelion.gameboard;

import eva.evangelion.activegame.activeunits.BaseUnit;
import eva.evangelion.activegame.activeunits.Evangelion;
import eva.evangelion.activegame.activeunits.Weapon;
import eva.evangelion.util.EvaCalculationUtil;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameBoard {

    public GridPane Board;
    public BorderPane Container;
    public int boardwidth = 20;
    public int boardheight = 30;

    public ArrayList<Sector> sectors = new ArrayList<>();

    public GameBoard(GridPane Board,  BorderPane container, int width, int height){
        this.Board = Board;
        this.Container = container;
        boardwidth = width;
        boardheight = height;
        makeBoard(this.Board);
    }
    public GameBoard(GridPane Board){
        this.Board = Board;
     //   this.Container = container;
        makeBoard(this.Board);
    }

    public void setPosition(double x, double y){
        Container.setLayoutX(x);
        Container.setLayoutY(y);
    }



    public void makeBoard(GridPane battleboard){
        for(int i=0; i<boardwidth; i++){
            for(int j=0; j<boardheight; j++){
                Sector sector = new Sector(i,j);
                sector.setPrefHeight(20);
                sector.setPrefWidth(20);
                sector.setBorder(new Border(new BorderStroke(Color.BLACK,
                        BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                battleboard.add(sector, i, j, 1, 1);
                sectors.add(sector);
            }
        }
    }


    public boolean OnBoard(int x, int y) {
        return x >= 0 && x < boardwidth && y >= 0 && y < boardheight;
    }
    public void UpdateBoardColors(){
        for (Sector sector : sectors) {
            ChangeColor(sector);
        }
    }
    public void fakeColors(BaseUnit unit){
        for (Sector sector : sectors) {
            if (!sector.type.EnemyCanSee) {
                if (sector.type.Creator.equals("GM")) {
                    if (!unit.getPlayerName().equals("GM")) {
                        FakeColor(sector);
                    }
                } else {
                    if (unit.getPlayerName().equals("GM")) {
                        FakeColor(sector);
                    }
                }
            }

        }
    }

    private void FakeColor(Sector sector) {
        if (sector.getType().originaltype != null) sector.setBackground(new Background(new BackgroundFill(sector.getType().originaltype.getColor(), CornerRadii.EMPTY, Insets.EMPTY)));
    }

    public void ShowPossibleMovementPositions(BaseUnit Eva, String s) {
        switch (s) {
            case "Maneuver" -> {
                for (Sector sector : sectors) {
                    if (isPossibleReachLocation(sector, Eva, 1) && sector.type.CanMoveTo) {
                        setYellowBorder(sector);
                    } else {
                        ChangeColor(sector);
                    }
                }
            }
            case "Reposition" -> {
                for (Sector sector : sectors) {
                    if (isPossibleReachLocation(sector, Eva, 3) && sector.type.CanMoveTo) {
                        setYellowBorder(sector);
                    } else {
                        ChangeColor(sector);
                    }
                }
            }
            default -> {
                for (Sector sector : sectors) {
                    if (isPossibleMovementLocation(sector, Eva) && (!s.equals("Take Cover") || CoverCheck(sector))  && sector.type.CanMoveTo) {
                        setYellowBorder(sector);
                    } else if (isPossibleDoubleLocation(sector, Eva) && (!s.equals("Take Cover") || CoverCheck(sector))  && sector.type.CanMoveTo) {
                        setOrangeBorder(sector);
                    } else {
                        ChangeColor(sector);
                    }
                }
            }
        }
    }

    // Helper method to set yellow border with default background
    private void setYellowBorder(Sector sector) {
        if (sector.getType() != null) {
            // Set the background to the type's color with a yellow border
            sector.setBackground(new Background(new BackgroundFill(
                    sector.getType().getColor(),
                    CornerRadii.EMPTY,
                    Insets.EMPTY
            )));

            // Add a yellow border
            sector.setBorder(new Border(new BorderStroke(
                    Color.YELLOW,
                    BorderStrokeStyle.SOLID,
                    CornerRadii.EMPTY,
                    new BorderWidths(1.2) // Border thickness
            )));
        }
    }

    // Helper method to set orange border with default background
    private void setOrangeBorder(Sector sector) {
        if (sector.getType() != null) {
            // Set the background to the type's color with an orange border
            sector.setBackground(new Background(new BackgroundFill(
                    sector.getType().getColor(),
                    CornerRadii.EMPTY,
                    Insets.EMPTY
            )));

            // Add an orange border
            sector.setBorder(new Border(new BorderStroke(
                    Color.ORANGE,
                    BorderStrokeStyle.SOLID,
                    CornerRadii.EMPTY,
                    new BorderWidths(1.2) // Border thickness
            )));
        }
    }

    public void ChangeColor(Sector sector) {
        if (sector.getType() != null) {
            // Reset to default: background only, no border
            sector.setBackground(new Background(new BackgroundFill(
                    sector.getType().getColor(),
                    CornerRadii.EMPTY,
                    Insets.EMPTY
            )));
            sector.setBorder(new Border(new BorderStroke(
                    Color.BLACK,
                    BorderStrokeStyle.SOLID,
                    CornerRadii.EMPTY,
                    new BorderWidths(0.5) // Border thickness
            ))); // Remove any border
        }
    }

    public void ShowSupportStructures() {
        for (Sector sector : sectors) {
            if (SupportCheck(sector)) {
                sector.setBackground(new Background(new BackgroundFill(Color.BLUEVIOLET, CornerRadii.EMPTY, Insets.EMPTY)));
            } else ChangeColor(sector);
        }
    }
    public void fogSectors(BaseUnit unit, int FogNumber) {
        if (FogNumber < 0) return;
        if (unit instanceof Evangelion evangelion) {
        for (Sector sector : sectors) {
            if (isFogged(sector, evangelion, FogNumber)) {
                sector.setBackground(new Background(new BackgroundFill(Color.DARKGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
                sector.setBorder(new Border(new BorderStroke(
                        Color.BLACK,
                        BorderStrokeStyle.SOLID,
                        CornerRadii.EMPTY,
                        new BorderWidths(0.5) // Border thickness
                )));
            }
        }
        }
    }


    public boolean isFogged(Sector sector, Evangelion eva, int FogNumber) {
        if (eva == null) return false;

        // First check: is it in range?
        boolean FoggedByDefault = !EvaCalculationUtil.isInRange(eva, sector, FogNumber);
        if (FoggedByDefault) return true;

        int x0 = eva.getX();
        int y0 = eva.getY();
        int x1 = sector.x;
        int y1 = sector.y;

        // Try direct line of sight first
        if (hasDirectLineOfSight(x0, y0, x1, y1)) {
            return false; // Visible
        }

        // If direct line is blocked, try corner peeking
        // Check if target is adjacent to an empty space that we can see
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx == 0 && dy == 0) continue;

                int adjX = x1 + dx;
                int adjY = y1 + dy;

                // Check if adjacent cell exists and is passable
                Sector adjacent = this.getSector(adjX, adjY);
                if (adjacent != null && adjacent.type.CanPassThrough) {
                    if (hasDirectLineOfSight(x0, y0, adjX, adjY)) {
                        return false; // Can see around corner
                    }
                }
            }
        }

        return true; // Fogged
    }

    private boolean hasDirectLineOfSight(int x0, int y0, int x1, int y1) {
        int dx = Math.abs(x1 - x0);
        int dy = Math.abs(y1 - y0);
        int sx = (x0 < x1) ? 1 : -1;
        int sy = (y0 < y1) ? 1 : -1;
        int err = dx - dy;

        int x = x0;
        int y = y0;

        while (true) {
            // Skip checking the starting cell
            if (!(x == x0 && y == y0)) {
                Sector currentSector = this.getSector(x, y);
                if (currentSector != null && !currentSector.type.CanPassThrough) {
                    return false; // Blocked by wall
                }
            }

            if (x == x1 && y == y1) break;

            int e2 = 2 * err;
            if (e2 > -dy) {
                err -= dy;
                x += sx;
            }
            if (e2 < dx) {
                err += dx;
                y += sy;
            }
        }

        return true; // Clear line of sight
    }

    public boolean CoverCheck(Sector sector) {
        if (sector.getType().CanCoverOn) return true;
        for (Sector sector1 : sectors) {
           if (sector1.type.CanCoverAdjacentTo && EvaCalculationUtil.isAdjecent(sector1, sector)) return true;
        }
        return false;
    }

    public boolean SupportCheck(Sector sector) {
        return sector.getType().SupportStructure;
    }

    public List<Sector> DrawSquare(Sector sector, int size, Color color) {
        int startx = sector.x-size;
        int starty = sector.y-size;
        int endx = sector.x+size;
        int endy = sector.y+size;
        List<Sector> affected = new ArrayList<>();
        for (Sector sector1 : sectors) {
            if (sector1.x >= startx && sector1.x <= endx && sector1.y >= starty && sector1.y <= endy) {
                sector1.setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));
                affected.add(sector1); }
        }
        return affected;
    }
    public List<Sector> DrawSquare(int x, int y, int size, Color color) {
        int startx = x-size;
        int starty = y-size;
        int endx = x+size;
        int endy = y+size;
        List<Sector> affected = new ArrayList<>();
        for (Sector sector1 : sectors) {
            if (sector1.x >= startx && sector1.x <= endx && sector1.y >= starty && sector1.y <= endy) {
                sector1.setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));
                affected.add(sector1); }
        }
        return affected;
    }


    public List<Sector> DrawLine(int startx, int starty, int endx, int endy, int stepx, int stepy, Color color) {
        int sx = startx;
        int sy = starty;
        int dx = Math.abs(endx - startx);
        int dy = Math.abs(endy - starty);
        int steps = Math.max(dx, dy);
        List<Sector> affected = new ArrayList<>();
        for (int i = 0; i <= steps; i++) {
            Sector sector1 = getSector(sx, sy);
            if (sector1 != null && i != 0) {
                sector1.setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));
                affected.add(sector1); }
            if (sx != endx) sx += stepx;
            if (sy != endy) sy += stepy;
        }
        return affected;
    }

    public Sector getSector(int x, int y) {
        for (Sector sector1 : sectors) {
            if (sector1.x == x && sector1.y == y) return sector1;
        }
        return null;
    }

    public void ShowPossibleAttackRange(BaseUnit Eva, Weapon weapon){
        int a;
        int b;

        a = weapon.Ranged ? Eva.getMinRange(weapon)-1 : Eva.getMinRange(weapon);
        b = Eva.getMaxRange(weapon);



        if (weapon.getLine()) {
            for (Sector sector : sectors) {
                if (isPossibleLineReachLocation(sector, Eva, a)) {
                    sector.setBackground(new Background(new BackgroundFill(Color.ORANGE, CornerRadii.EMPTY, Insets.EMPTY)));
                } else if (isPossibleLineDoubleReachLocation(sector, Eva, a, b)) {
                    sector.setBackground(new Background(new BackgroundFill(Color.ORANGERED, CornerRadii.EMPTY, Insets.EMPTY)));
                } else ChangeColor(sector);
            }
        } else for (Sector sector : sectors) {
            if (isPossibleReachLocation(sector, Eva, a)) {
                sector.setBackground(new Background(new BackgroundFill(Color.ORANGE, CornerRadii.EMPTY, Insets.EMPTY)));
            } else if (isPossibleDoubleReachLocation(sector, Eva, a, b)) {
                sector.setBackground(new Background(new BackgroundFill(Color.ORANGERED, CornerRadii.EMPTY, Insets.EMPTY)));
            } else ChangeColor(sector);
        }
    }


    public boolean isPossibleMovementLocation(Sector sector, BaseUnit Unit) {
        int Speed = Unit.getSpeed();
        return Unit.getStamina() > 0 && isPossibleReachLocation(sector, Unit, Speed);

    }


    public Sector getRandomSectorInRange(int CenterX, int CenterY, int range) {
        int boardWidth = boardwidth;
        int boardHeight = boardheight;

        // Calculate valid X range (clamped to board boundaries)
        int minX = Math.max(0, CenterX - range);
        int maxX = Math.min(boardWidth - 1, CenterX + range);

        // Calculate valid Y range (clamped to board boundaries)
        int minY = Math.max(0, CenterY - range);
        int maxY = Math.min(boardHeight - 1, CenterY + range);

        Random random = new Random();

        // Generate random coordinates within the valid range
        int x = random.nextInt(maxX - minX + 1) + minX;
        int y = random.nextInt(maxY - minY + 1) + minY;

        // Return the corresponding Sector from the gameboard
        return getSector(x, y); // See note below
    }

    public boolean isPossibleReachLocation(Sector sector, BaseUnit Unit, int a) {
        int EvaX = Unit.getX();
        int EvaY = Unit.getY();
        a = a+1;
        int EvaXMax = EvaX+a;
        int EvaXMin = EvaX-a;
        int EvaYMax = EvaY+a;
        int EvaYMin = EvaY-a;
        return  sector.x < EvaXMax && sector.x > EvaXMin && sector.y < EvaYMax && sector.y > EvaYMin;
    }


    public boolean isPossibleLineReachLocation(Sector sector, BaseUnit Unit, int a) {
        int EvaX = Unit.getX();
        int EvaY = Unit.getY();
        int x = sector.x;
        int y = sector.y;

        // Check if sector lies on a straight line (horizontal, vertical, or diagonal)
        boolean isStraightLine = (x == EvaX) ||            // Vertical line
                (y == EvaY) ||             // Horizontal line
                (Math.abs(x - EvaX) == Math.abs(y - EvaY));  // Diagonal

        // Combine with original reach check
        return isStraightLine && isPossibleReachLocation(sector, Unit, a);
    }

    public boolean isPossibleLineDoubleReachLocation(Sector sector, BaseUnit Unit, int a, int b) {
        int EvaX = Unit.getX();
        int EvaY = Unit.getY();
        int x = sector.x;
        int y = sector.y;

        // Check if sector lies on a straight line (horizontal, vertical, or diagonal)
        boolean isStraightLine = (x == EvaX) ||            // Vertical line
                (y == EvaY) ||             // Horizontal line
                (Math.abs(x - EvaX) == Math.abs(y - EvaY));  // Diagonal

        // Combine with original reach check
        return isStraightLine && isPossibleDoubleReachLocation(sector, Unit, a, b);
    }


    public boolean isPossibleDoubleReachLocation(Sector sector, BaseUnit Eva, int a, int b) {
        b = b+1;
        if (a == b) return false;
        int EvaX = Eva.getX();
        int EvaY = Eva.getY();
        int EvaXMax = EvaX+b;
        int EvaXMin = EvaX-b;
        int EvaYMax = EvaY+b;
        int EvaYMin = EvaY-b;
        return sector.x < EvaXMax && sector.x > EvaXMin && sector.y < EvaYMax && sector.y > EvaYMin && !isPossibleReachLocation(sector, Eva, a);
    }

    public boolean isPossibleDoubleLocation(Sector sector, BaseUnit Eva) {
        int EvaX = Eva.getX();
        int EvaY = Eva.getY();
        int Speed = 2*(Eva.getSpeed())+1;
        int EvaXMax = EvaX+Speed;
        int EvaXMin = EvaX-Speed;
        int EvaYMax = EvaY+Speed;
        int EvaYMin = EvaY-Speed;
        return Eva.getStamina() > 1 && sector.x < EvaXMax && sector.x > EvaXMin && sector.y < EvaYMax && sector.y > EvaYMin && !isPossibleMovementLocation(sector, Eva);
    }




    public void setType(Sector sector, SectorType type){
        sector.setType(type);
        ChangeColor(sector);
    }




}