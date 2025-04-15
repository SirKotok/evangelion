package eva.evangelion.util;

import eva.evangelion.activegame.Gamestate;
import eva.evangelion.activegame.activeunits.Weapon;
import eva.evangelion.activegame.activeunits.unitstate.StateEffect;
import eva.evangelion.activegame.activeunits.unitstate.WingLoadout;
import eva.evangelion.gameboard.Battlefield;
import eva.evangelion.gameboard.SectorType;
import eva.evangelion.units.Types.AngelType;
import eva.evangelion.units.Types.EvangelionType;
import eva.evangelion.units.Upgrades.ATPower;
import eva.evangelion.units.Upgrades.Upgrade;
import javafx.scene.paint.Color;
import kotlin.Triple;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public final class EvaSaveUtil {

    public static String filepath = "C:/Users/2/Desktop/EvangelionJava";
    public static String savepath = "C:\\Users\\2\\Desktop\\EvangelionJava";
    public static String savegamepath = "C:\\Users\\2\\Desktop\\EvangelionJava\\EvaBox\\Dropbox\\";
    public static String getFilepath() throws IOException {
        return getSavepath().replace("\\", "/");
    }

    public static String getSavepath()  {
        try {
        Path path = Paths.get("FilepathConfig.txt");
        Scanner scan = new Scanner(path);
        String s = scan.nextLine();
        scan.close();
        return s;
        }
        catch (RuntimeException | IOException ignored) {

        }
        return savepath;
    }

    public static String getSaveGamePath() {
        try {
        Path path = Paths.get("EvaDropboxConfig.txt");
        Scanner scan = new Scanner(path);
        String s = scan.nextLine();
        scan.close();
        return s;
        }
        catch (RuntimeException | IOException ignored) {
        }
        return savegamepath;
    }

    //  public void SaveUpgrade(Upgrade upgrade, String Path) throws IOException {
  //      String name = upgrade.Name;
  //      FileWriter write = new FileWriter(Path+name);
  //      PrintWriter linewriter = new PrintWriter(write);
  //  }

    public static EvangelionType ReadEvangelionType(String filepath, List<String> UpgradeNames) throws IOException {
        EvangelionType Eva = new EvangelionType();
        Path path = Paths.get(filepath);
        Scanner scan = new Scanner(path);
        Eva.Name = scan.nextLine();
        while (scan.hasNextLine()) {
            String s = scan.nextLine();
            if (s.equals("Color")) {
                float r = Float.parseFloat(scan.nextLine());
                float g = Float.parseFloat(scan.nextLine());
                float b = Float.parseFloat(scan.nextLine());
                Eva.EvaColor = new Color(r, g, b, 1);
            }
            if (UpgradeNames.contains(s)) {
                Upgrade upgrade = ReadStringUpgrade(s);
                Eva.ChosenUpgrades.add(upgrade);
            }
        }
        Eva.CurrentUpgrades.addAll(Eva.ChosenUpgrades);
        Eva.CalculateNext();
        Eva.CalculateDisplay();
        scan.close();
        return Eva;
    }

    public static void SaveEvangelion(String filepath, EvangelionType Eva) throws IOException {
        File savefile = new File(filepath+Eva.Name+".txt");
        if (savefile.exists()) savefile.delete();
        savefile.createNewFile();
        FileWriter write = new FileWriter(savefile);
        PrintWriter linewriter = new PrintWriter(write);
        linewriter.println(Eva.Name);
        linewriter.println("Color");
        linewriter.println(Eva.EvaColor.getRed());
        linewriter.println(Eva.EvaColor.getBlue());
        linewriter.println(Eva.EvaColor.getGreen());
        for (Upgrade upg : Eva.CurrentUpgrades) {
            linewriter.println(upg.Name);
        }
        linewriter.close();
    }

    public static Battlefield ReadBattlefield(String filepath) throws IOException {
        Path path = Paths.get(filepath);
        Scanner scan = new Scanner(path);
        scan.nextLine();
        Battlefield field = new Battlefield(Integer.parseInt(scan.nextLine()), Integer.parseInt(scan.nextLine()));
        while (scan.hasNextLine()) {
            String s = scan.nextLine();
            int x = Integer.parseInt(scan.nextLine());
            int y = Integer.parseInt(scan.nextLine());
            Triple<Integer, Integer, String> triple = new Triple<>(x, y, s);
            field.SpecialTiles.add(triple);
        }
        scan.close();
        return field;
    }
    public static void SaveBattlefield(String filepath, String name, Battlefield battlefield) throws IOException {
        File savefile = new File(filepath+name+".txt");
        if (savefile.exists()) savefile.delete();
        savefile.createNewFile();
        FileWriter write = new FileWriter(savefile);
        PrintWriter linewriter = new PrintWriter(write);
        linewriter.println(name);
        linewriter.println(battlefield.sizeX);
        linewriter.println(battlefield.sizeY);
        for (Triple<Integer, Integer, String> triple : battlefield.SpecialTiles) {
            linewriter.println(triple.getThird());
            linewriter.println(triple.getFirst());
            linewriter.println(triple.getSecond());
        }
        linewriter.close();
    }

    public static void SaveWeapon(String filepath, Weapon weapon) throws IOException {
        File savefile = new File(filepath+weapon.Name+".txt");
        if (savefile.exists()) savefile.delete();
        savefile.createNewFile();
        FileWriter write = new FileWriter(savefile);
        PrintWriter linewriter = new PrintWriter(write);
        linewriter.println(weapon.Name);
        linewriter.println(weapon.profile);
        linewriter.println(weapon.Technology.name());
        for (Weapon.Customisation c : weapon.CurrentCustomisations) {
            linewriter.println(c.name());
            if (c.equals(Weapon.Customisation.DOUBLE_EDGED)) {
                linewriter.println(weapon.Technology2.name());
            }
            if (c.equals(Weapon.Customisation.BAYONET)) {
                linewriter.println(weapon.getSubWeapon().profile);
                linewriter.println(weapon.getSubWeapon().Technology.name());
            }
            if (c.equals(Weapon.Customisation.ENHANCED_BAYONET)) {
                linewriter.println(weapon.getSubWeapon().profile);
                linewriter.println(weapon.getSubWeapon().Technology.name());
            }
        }
        linewriter.close();
    }
    public static Weapon ReadStringWeapon(String name) throws IOException {
        if (name.equals("Ammo")) return Weapon.Ammo();
        if (name.equals("Free") || name.equals("None")) return Weapon.Free();
        return ReadWeapon(getFilepath()+"/Weapons/"+name+".txt");
    }
    public static Weapon ReadWeapon(String filepath) throws IOException {
        Path path = Paths.get(filepath);
        Scanner scan = new Scanner(path);
        String Name = scan.nextLine();
        String profile = scan.nextLine();
        Weapon weapon = ReadWeaponProfile(EvaSaveUtil.getFilepath() + "/WeaponProfiles/"+profile+".txt");
        weapon.Name = Name;
        weapon.Technology = Weapon.Tech.valueOf(scan.nextLine());
        while (scan.hasNextLine()) {
            String s = scan.nextLine();
            weapon.CurrentCustomisations.add(Weapon.Customisation.valueOf(s));
            if (s.equals(Weapon.Customisation.DOUBLE_EDGED.name())) {
                weapon.Technology2 = Weapon.Tech.valueOf(scan.nextLine());
            }
            if (s.equals(Weapon.Customisation.ENHANCED_BAYONET.name()) || s.equals(Weapon.Customisation.BAYONET.name())) {
                weapon.addSubWeapon(ReadWeaponProfile(EvaSaveUtil.getFilepath() + "/WeaponProfiles/"+scan.nextLine()+".txt"));
                weapon.getSubWeapon().Technology = Weapon.Tech.valueOf(scan.nextLine());
            }
        }
        scan.close();
        return weapon;
    }


    public static Weapon ReadWeaponProfile(String filepath) throws IOException {
        Path path = Paths.get(filepath);
        Scanner scan = new Scanner(path);
        String Name = scan.nextLine();
        String Profile = Name;
        int handsint = Integer.parseInt(scan.nextLine());
        Weapon.Hand hand = Weapon.Hand.NONE;
        switch (handsint) {
            case 1 -> hand = Weapon.Hand.ONE_HANDED;
            case 2 -> hand = Weapon.Hand.TWO_HANDED;
        }
        int DiceAmount = Integer.parseInt(scan.nextLine());
        int DicePower = Integer.parseInt(scan.nextLine());
        int Power = Integer.parseInt(scan.nextLine());
        int Cost = Integer.parseInt(scan.nextLine());

        boolean Ranged = Boolean.parseBoolean(scan.nextLine());

        Weapon weapon = new Weapon(Name, hand, DiceAmount, DicePower, Power, Ranged);

        weapon.Cost+=Cost;
        weapon.profile = Profile;
        if (Ranged) {
            weapon.MinRange = Integer.parseInt(scan.nextLine());
            weapon.MaxRange = Integer.parseInt(scan.nextLine());
            weapon.AmmoCapacity = Integer.parseInt(scan.nextLine());
        }


        while (scan.hasNextLine()) {
          String Property = scan.nextLine();
          switch (Property){
              case "Area" -> {weapon.Area+=1+Integer.parseInt(scan.nextLine());}
              case "ArmorPiercing" -> {weapon.makeArmorPiercing();}
              case "Defensive" -> {weapon.Defensive+=Integer.parseInt(scan.nextLine());}
              case "Penetration" -> {
                  int x = Integer.parseInt(scan.nextLine());
                  weapon.Penetration+=x;
              }
              case "Line" -> {weapon.Line = true;}
              case "Grapple" -> {weapon.makeGrapple();}
              case "Intrinsic" -> {weapon.makeIntrinsic();}
              case "Precise" -> {weapon.makePrecise();}
              case "Proven" -> {weapon.makeProven();}
              case "Reach" -> {weapon.makeReach();}
              case "CQB" -> {weapon.makeCQB();}
              case "Small" -> {weapon.makeSmall();}
              case "Spray" -> {weapon.makeSpray();}
              case "Throwing" -> {weapon.makeThrowing();}
              case "Swift" -> {weapon.makeSwift();}
          }
        }



        scan.close();
        return weapon;

    }



    public static Upgrade ReadStringUpgrade(String name) throws IOException {
        return ReadUpgrade(getFilepath()+"/Upgrades/"+name+".txt");
    }
    public static EvangelionType ReadStringEvangelionType(String name, List<String> upgrades) throws IOException {
        return ReadEvangelionType(getFilepath()+"/Evangelions/"+name+".txt", upgrades);
    }
    public static Upgrade ReadUpgrade(String filepath) throws IOException {
        Path path = Paths.get(filepath);
        Scanner scan = new Scanner(path);
        String Name = scan.nextLine();
        boolean Passive = Boolean.parseBoolean(scan.nextLine());
        if (!Passive) {
            ATPower power = new ATPower(Name);
            scan.nextLine();
            power.ATWeapon = ReadWeaponProfile(EvaSaveUtil.getFilepath() + "/AdditionalWeapons/"+scan.nextLine()+".txt");
            power.ATWeapon.setATPower(true);
            power.StaminaCost = Integer.parseInt(scan.nextLine());
            return power;
        }


        boolean PredicatedPassive = Boolean.parseBoolean(scan.nextLine());
        boolean WeaponPassive = Boolean.parseBoolean(scan.nextLine());
        boolean Active = Boolean.parseBoolean(scan.nextLine());
       // String Upgrades = Boolean.valueOf(scan.nextLine());

        int AccuracyDelta = Integer.parseInt(scan.nextLine());
        int AttackStrengthDelta = Integer.parseInt(scan.nextLine());
        int ToughnessDelta = Integer.parseInt(scan.nextLine());
        int ArmorDelta = Integer.parseInt(scan.nextLine());
        int ReflexesDelta = Integer.parseInt(scan.nextLine());
        int SpeedDelta = Integer.parseInt(scan.nextLine());
        int RequisitionDelta = Integer.parseInt(scan.nextLine());
        int UpgradesAvaliableDelta = Integer.parseInt(scan.nextLine());



        int AccuracyDeltaPredicated = Integer.parseInt(scan.nextLine());
        int  AttackStrengthDeltaPredicated = Integer.parseInt(scan.nextLine());
        int ToughnessDeltaPredicated = Integer.parseInt(scan.nextLine());
        int ArmorDeltaPredicated = Integer.parseInt(scan.nextLine());
        int ReflexesDeltaPredicated = Integer.parseInt(scan.nextLine());
        int SpeedDeltaPredicated = Integer.parseInt(scan.nextLine());
        int RequisitionDeltaPredicated = Integer.parseInt(scan.nextLine());
        int  UpgradesAvaliableDeltaPredicated = Integer.parseInt(scan.nextLine());
        Upgrade upg =  new Upgrade(Name, Passive, PredicatedPassive, WeaponPassive, Active,
                AccuracyDelta, AttackStrengthDelta, ToughnessDelta, ArmorDelta, ReflexesDelta, SpeedDelta, RequisitionDelta,
                UpgradesAvaliableDelta, AccuracyDeltaPredicated, AttackStrengthDeltaPredicated, ToughnessDeltaPredicated,
                ArmorDeltaPredicated, ReflexesDeltaPredicated, SpeedDeltaPredicated, RequisitionDeltaPredicated, UpgradesAvaliableDeltaPredicated);
        while (scan.hasNextLine()) {
            if (WeaponPassive) {
                while (scan.hasNextLine()) {
                        String Property = scan.nextLine();
                        switch (Property){
                            case "Area" -> {upg.PotentialArea+=1+Integer.parseInt(scan.nextLine());}
                            case "ArmorPiercing" -> {upg.makePotentialArmorPiercing();}
                            case "Defensive" -> {
                                upg.PotentialDefensive+=Integer.parseInt(scan.nextLine());
                            }
                            case "Penetration" -> {
                                int x = Integer.parseInt(scan.nextLine());
                                upg.PotentialPenetration+=x;
                                }
                            case "Grapple" -> {upg.makePotentialGrapple();}
                            case "Intrinsic" -> {upg.makePotentialIntrinsic();}
                            case "Precise" -> {upg.makePotentialPrecise();}
                            case "Proven" -> {upg.makePotentialProven();}
                            case "Reach" -> {upg.makePotentialReach();}
                            case "CQB" -> {upg.makePotentialCQB();}
                            case "Small" -> {upg.makePotentialSmall();}
                            case "Spray" -> {upg.makePotentialSpray();}
                            case "Throwing" -> {upg.makePotentialThrowing();}
                            case "Swift" -> {upg.makePotentialSwift();}
                    }
                }
            } else {
            String s = scan.nextLine();
            if (s.equals("Wing")) {
                WingLoadout Wing = new WingLoadout();
                String ss = scan.nextLine().toUpperCase().replace(" ", "_");
                boolean weapon = Boolean.parseBoolean(scan.nextLine());
                if (weapon) Wing.setItem(ReadWeaponProfile(EvaSaveUtil.getFilepath() + "/AdditionalWeapons/"+ss+".txt"));
                Wing.loadout = WingLoadout.Loadout.valueOf(ss.toUpperCase());
                upg.loadout = Wing;
            } else
            if (s.equals("SpreadPattern")) {
                StateEffect effect = new StateEffect(scan.nextLine());
                effect.AccuracyDelta = Integer.parseInt(scan.nextLine());
                effect.AttackStrengthDelta = Integer.parseInt(scan.nextLine());
                effect.RangedStrengthDelta = Integer.parseInt(scan.nextLine());
                effect.MaxToughnessDelta = Integer.parseInt(scan.nextLine());
                effect.ArmorDelta = Integer.parseInt(scan.nextLine());
                effect.ReflexesDelta = Integer.parseInt(scan.nextLine());
                effect.SpeedDelta = Integer.parseInt(scan.nextLine());
                effect.PenetrationDelta = Integer.parseInt(scan.nextLine());
                switch (scan.nextLine()) {
                    case "guard" -> {effect.Condition = StateEffect.ExpirationCondition.GUARD;}
                    case "attack" -> {effect.Condition = StateEffect.ExpirationCondition.ATTACK;}
                    case "start" -> {effect.Condition = StateEffect.ExpirationCondition.START;}
                    case "stand" -> {effect.Condition = StateEffect.ExpirationCondition.STAND;}
                    case "potential" -> {effect.Condition = StateEffect.ExpirationCondition.POTENTIAL;}
                    case "interval" -> {effect.Condition = StateEffect.ExpirationCondition.INTERVAL;}
                    case "turn" -> {effect.Condition = StateEffect.ExpirationCondition.TURN;}
                    case "round" -> {effect.Condition = StateEffect.ExpirationCondition.ROUND;}
                }
                while (scan.hasNextLine()) {
                effect.ProhibitedActions.add(scan.nextLine());
                }
                upg.SpreadPatternStateEffect = effect;
            }
        }
        }
        scan.close();
        return  upg;
    }


    public static void SaveGameState(String filepath, Gamestate state) throws IOException {
        ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filepath));
        outputStream.writeObject(state);
        outputStream.close();
    }

    public static Gamestate ReadGameState(String filepath) throws IOException, ClassNotFoundException {

        int retries = 10;
        long delay = 200;

        for (int i = 0; i < retries; i++) {
            try (ObjectInputStream inStream = new ObjectInputStream(new FileInputStream(filepath))) {
                Gamestate state = (Gamestate) inStream.readObject();
                return state;
            } catch (FileNotFoundException e) {
                if (i == retries - 1) throw e;
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    throw new IOException("Interrupted while retrying", ie);
                }
            }
        }
        throw new FileNotFoundException("Failed to access file after " + retries + " attempts");
    }

    public static Gamestate ReadGameStateTry(String filepath) throws IOException, ClassNotFoundException {
        ObjectInputStream inStream = new ObjectInputStream(new FileInputStream(filepath));
        Gamestate state = (Gamestate) inStream.readObject();
        inStream.close();
        return state;
    }


    public static SectorType ReadSectorType(String filepath) throws IOException {
        Path path = Paths.get(filepath);
        Scanner scan = new Scanner(path);
        String Name = scan.nextLine();
        Color color = Color.web(scan.nextLine());
        boolean CanMoveTo = Boolean.parseBoolean(scan.nextLine());
        boolean CanCoverOn = Boolean.parseBoolean(scan.nextLine());
        boolean CanCoverAdj = Boolean.parseBoolean(scan.nextLine());

        int DamageOn = Integer.parseInt(scan.nextLine());
        int DamageOnAdj = Integer.parseInt(scan.nextLine());
        int DamageOnMove = Integer.parseInt(scan.nextLine());
        int DamageOnMoveAdj = Integer.parseInt(scan.nextLine());
        boolean Support = Boolean.parseBoolean(scan.nextLine());
        int Health1 = Integer.parseInt(scan.nextLine());
        int Health2 = Integer.parseInt(scan.nextLine());
        boolean Dogma = Boolean.parseBoolean(scan.nextLine());
        boolean canPassThrough = Boolean.parseBoolean(scan.nextLine());
        scan.close();
        return new SectorType(Name, color, CanMoveTo, CanCoverOn, CanCoverAdj,
                DamageOn, DamageOnAdj, DamageOnMove, DamageOnMoveAdj,
                Support, Health1, Health2, Dogma, canPassThrough);

    }






    public static AngelType ReadAngelType(String filepath, List<String> UpgradeNames) throws IOException {
        AngelType Angel = new AngelType();
        Path path = Paths.get(filepath);
        Scanner scan = new Scanner(path);
        Angel.Name = scan.nextLine();
     //   List<String> UpgradeNames = new ArrayList<>();
    //    for (Upgrade upg : AvaliableUpgadeList) {
    //        UpgradeNames.add(upg.Name);
    //    }
   //     while (scan.hasNextLine()) {
   //         String s = scan.nextLine();
        //    if (UpgradeNames.contains(s)) {
      //          Angel.ChosenUpgrades.add(AvaliableUpgadeList.get(UpgradeNames.indexOf(s)));
        //    }
     //   }
        Angel.CurrentUpgrades.addAll(Angel.ChosenUpgrades);
        Angel.CalculateNext();
        Angel.CalculateDisplay();
        scan.close();
        return Angel;
    }

    public static void SaveAngel(String filepath, AngelType Angel) throws IOException {
        File savefile = new File(filepath+Angel.Name+".txt");
        if (savefile.exists()) savefile.delete();
        savefile.createNewFile();
        FileWriter write = new FileWriter(savefile);
        PrintWriter linewriter = new PrintWriter(write);
        linewriter.println(Angel.Name);
        for (Upgrade upg : Angel.CurrentUpgrades) {
            linewriter.println(upg.Name);
        }
        linewriter.close();
    }








}
