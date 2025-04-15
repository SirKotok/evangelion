package eva.evangelion.units.Upgrades;

import eva.evangelion.activegame.activeunits.Weapon;

public class ATPower extends Upgrade{

    public ATPower(String name) {
    super(name, false, false, false, true, 0,
            0, 0, 0, 0,0, 0, 0,
            0, 0, 0, 0, 0, 0,
           0, 0);
    }
   public Weapon ATWeapon;
   public int StaminaCost;

}
