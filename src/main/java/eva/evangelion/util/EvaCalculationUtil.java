package eva.evangelion.util;

import eva.evangelion.activegame.activeunits.BaseUnit;
import eva.evangelion.gameboard.Sector;

public final class EvaCalculationUtil {
    public static boolean isAdjecent(BaseUnit unit1, Sector sector) {
        return isAdjecent(unit1.getX(), unit1.getY(), sector.x, sector.y);
    }

    public static boolean isAdjecent(Sector sector1, Sector sector) {
        return isAdjecent(sector1.x, sector1.y, sector.x, sector.y);
    }
    public static boolean isAdjecent(BaseUnit unit1, BaseUnit unit2) {
        return isAdjecent(unit1.getX(), unit1.getY(), unit2.getX(), unit2.getY());
    }
    public static boolean isAdjecent(int x1, int y1, int x2, int y2) {
        return isInRange(x1, y1, x2, y2, 1);
    }
    public static boolean isInRange(BaseUnit unit1, BaseUnit unit2, int range) {
        return isInRange(unit1, unit2.getX(), unit2.getY(), range);
    }
    public static boolean isInRange(BaseUnit unit, Sector sector, int range) {
        return isInRange(unit, sector.x, sector.y, range);
    }
    public static boolean isInRange(BaseUnit unit, int centerx, int centery, int radius) {
        return isBetweenInclusive(unit, centerx-radius, centery-radius, centerx+radius, centery+radius);
    }
    public static boolean isInRange(int x, int y, int centerx, int centery, int radius) {
        return isBetweenInclusive(x, y, centerx-radius, centery-radius, centerx+radius, centery+radius);
    }
    public static boolean isBetweenInclusive(BaseUnit unit, int startx, int starty, int endx, int endy) {
        return isBetweenInclusive(unit.getX(), unit.getY(), startx, starty, endx, endy);
    }
    public static boolean isBetweenInclusive(int x, int y, int startx, int starty, int endx, int endy) {
        return  (x >= startx && x <= endx && y >=starty && y <= endy);
    }
}
