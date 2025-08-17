
package com.optifoundry.util;
import org.bukkit.entity.*; import java.util.Locale;
public final class KillFilters {
    private KillFilters() {}
    public static boolean isItem(Entity e){ return e.getType()==EntityType.DROPPED_ITEM || e.getType().name().equalsIgnoreCase("ITEM"); }
    public static boolean isBoat(Entity e){ return e.getType().name().contains("BOAT"); }
    public static boolean isMinecart(Entity e){ return e.getType().name().contains("MINECART"); }
    public static boolean isArmorStand(Entity e){ return e.getType()==EntityType.ARMOR_STAND || e.getType().name().equalsIgnoreCase("ARMORSTAND"); }
    public static boolean isTNTPrimed(Entity e){ return e.getType().name().contains("TNT") && !isMinecart(e); }
    public static boolean isVillager(Entity e){ return e.getType().name().equalsIgnoreCase("VILLAGER"); }
    public static boolean isGolem(Entity e){ return e.getType().name().toUpperCase(Locale.ROOT).contains("GOLEM"); }
    public static boolean isBoss(Entity e){ String n=e.getType().name().toUpperCase(Locale.ROOT);
        return n.contains("ENDER_DRAGON")||n.contains("WITHER")||n.contains("ELDER_GUARDIAN")||n.contains("WARDEN"); }
    public static boolean isMob(Entity e){ if(e instanceof Monster) return true; String n=e.getType().name().toUpperCase(Locale.ROOT);
        return n.contains("ZOMBIE")||n.contains("SKELETON")||n.contains("CREEPER")||n.contains("SPIDER")||n.contains("BLAZE")||n.contains("SLIME")||n.contains("PHANTOM")||n.contains("PILLAGER")||n.contains("VINDICATOR")||n.contains("RAVAGER"); }
    public static boolean isAnimal(Entity e){ if(e instanceof Animals) return true; String n=e.getType().name().toUpperCase(Locale.ROOT);
        return n.contains("COW")||n.contains("SHEEP")||n.contains("PIG")||n.contains("CHICKEN")||n.contains("RABBIT")||n.contains("HORSE")||n.contains("WOLF")||n.contains("OCELOT")||n.contains("CAT")||n.contains("FOX")||n.contains("GOAT"); }
}
