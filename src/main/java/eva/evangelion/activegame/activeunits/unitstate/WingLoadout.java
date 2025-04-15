package eva.evangelion.activegame.activeunits.unitstate;

import eva.evangelion.activegame.activeunits.Weapon;

import java.io.Serializable;

public class WingLoadout implements Serializable {
    public enum Loadout {
        NONE,
        STORAGE,
        ASSAULT,
        ELECTROLASER,
        SIEGE_FRAME,
        JERMOD
    }
    public Loadout loadout;
    public String location = "";
    public String getLocation() {
        return location;
    }
    public String setLocation() {
        return location;
    }
    public boolean isAttack()   {
        return loadout.equals(Loadout.ELECTROLASER);
    }
    public boolean isStorage(){
        return loadout.equals(Loadout.STORAGE);
    }
    public boolean isAssault(){
        return loadout.equals(Loadout.ASSAULT);
    }
    public boolean isSiegeFrame(){
        return loadout.equals(Loadout.SIEGE_FRAME);
    }
    public boolean canStoreWeapon(){
        return !(loadout.equals(Loadout.NONE) || isSiegeFrame() || loadout.equals(Loadout.JERMOD));
    }
    public boolean ReplaceArm(){
        return isSiegeFrame();
    }
    public Weapon item;
    public Weapon getItem() {
        if (loadout.equals(Loadout.NONE)) return null;
        return item;
    }
    public void setItem(Weapon item) {
        this.item = item;
    }

    public boolean hasWeapon() {
        if (item != null && item.isWeapon()) return true;
        else return false;
    }
    public Weapon getWeapon() {
        if (loadout.equals(Loadout.NONE)) return null;
        if (item != null && item.isWeapon()) return item;
        else return null;
    }


}
