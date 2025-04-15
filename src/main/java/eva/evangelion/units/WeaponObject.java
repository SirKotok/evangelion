package eva.evangelion.units;

import eva.evangelion.activegame.activeunits.BaseUnit;
import eva.evangelion.activegame.activeunits.Weapon;
import eva.evangelion.activegame.activeunits.unitstate.StateEffect;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.List;

public class WeaponObject extends MapObject {

    public Weapon weapon;

    public WeaponObject(Weapon weapon) {
        this.weapon = weapon;
        ItemCircle = new Circle(2);
        ItemCircle.setFill(Color.BLACK);
    }


    public void setPosition(int x, int y) {
        setX(x);
        setY(y);
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public List<StateEffect> getWeaponEffects() {
        return weapon.WeaponEffects;
    }

    public void setWeaponEffects(List<StateEffect> weaponEffects) {
        weapon.WeaponEffects = weaponEffects;
    }
    public void setWeaponEffectsFromUnit(BaseUnit unit) {
        List<StateEffect> effects = unit.getEffects();
        List<StateEffect> weaponef = new ArrayList<>();
        for (StateEffect effect : effects) {
            if (effect.WeaponSpecific != null && effect.WeaponSpecific.equals(getWeapon())) {
                weaponef.add(effect);
            }
        }
        unit.WeaponSpecificTickEffect(getWeapon());
        setWeaponEffects(weaponef);
        unit.removeWeapon(getWeapon());
     }

    public int getX() {
        X = weapon.x;
        return weapon.x;
    }
    public int getY() {
        Y = weapon.y;
        return weapon.y;
    }
    public void setX(int x) {
        X = x;
        weapon.x = x;
    }
    public void setY(int y) {
        Y = y;
        weapon.y = y;
    }



}
