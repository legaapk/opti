
package com.optifoundry;
import com.optifoundry.bridge.VersionBridge; import com.optifoundry.modules.*; import com.optifoundry.util.KillFilters;
import org.bukkit.*; import org.bukkit.command.*; import org.bukkit.entity.*;
public class OptiFoundryCommand implements CommandExecutor{
  private final OptiFoundry plugin; public OptiFoundryCommand(OptiFoundry plugin){ this.plugin=plugin; }
  @Override public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
    if(!sender.hasPermission("optifoundry.use")){ sender.sendMessage("§cNo permission."); return true; }
    if(args.length>0 && args[0].equalsIgnoreCase("help")){
      sender.sendMessage("§aOptiFoundry help:");
      sender.sendMessage("§7/opti status §f- show bridge, worlds, chunks, entities");
      sender.sendMessage("§7/opti reload §f- reload config.yml");
      sender.sendMessage("§7/opti chunksweep §f- unload idle chunks");
      sender.sendMessage("§7/opti itemsweep §f- TTL sweep for dropped items");
      sender.sendMessage("§7/opti toggle <chunk|items> <on|off> §f- enable/disable module");
      sender.sendMessage("§7/opti kill <type> [radius r] §f- remove entities (players|mobs|animals|items|...)");
      sender.sendMessage("§7/optick ... §f- tick control (query/rate/freeze/unfreeze/step/sprint)");
      return true;
    }
    if(args.length==0 || "status".equalsIgnoreCase(args[0])) return sendStatus(sender);
    if("reload".equalsIgnoreCase(args[0])){ plugin.reloadConfig(); sender.sendMessage("§a[OptiFoundry] Config reloaded."); return true; }
    if("chunksweep".equalsIgnoreCase(args[0])){ ChunkGuardian cg=plugin.chunkGuardian(); if(cg==null){ sender.sendMessage("§eChunkGuardian disabled."); return true; } int n=cg.sweep(); sender.sendMessage("§a[OptiFoundry] Unloaded chunks: §f"+n); return true; }
    if("itemsweep".equalsIgnoreCase(args[0])){ ItemFlow f=plugin.itemFlow(); if(f==null){ sender.sendMessage("§eItemFlow disabled."); return true; } int n=f.sweepTTL(); sender.sendMessage("§a[OptiFoundry] Items removed by TTL: §f"+n); return true; }
    if("toggle".equalsIgnoreCase(args[0])){ if(args.length<3){ sender.sendMessage("§7Usage: /opti toggle <chunk|items> <on|off>"); return true; }
      boolean on=args[2].equalsIgnoreCase("on");
      if(args[1].equalsIgnoreCase("chunk")) plugin.getConfig().set("chunk-guardian.enabled", on);
      else if(args[1].equalsIgnoreCase("items")) plugin.getConfig().set("item-flow.enabled", on);
      else { sender.sendMessage("§cUnknown module. Use: chunk, items"); return true; }
      plugin.saveConfig(); sender.sendMessage("§a[OptiFoundry] Module "+args[1]+" → "+(on? "ON":"OFF")); return true; }
    if("kill".equalsIgnoreCase(args[0])) return handleKill(sender, args);
    sender.sendMessage("§7Unknown subcommand. Use: help, status, reload, chunksweep, itemsweep, toggle, kill"); return true;
  }
  private boolean sendStatus(CommandSender sender){
    VersionBridge br=plugin.bridge(); int worlds=Bukkit.getWorlds().size(); int chunks=0; int entities=0;
    for(World w:Bukkit.getWorlds()){ chunks+=w.getLoadedChunks().length; for(Entity e:w.getEntities()) entities++; }
    boolean cg=plugin.getConfig().getBoolean("chunk-guardian.enabled", true); boolean iflow=plugin.getConfig().getBoolean("item-flow.enabled", true);
    sender.sendMessage("§aOptiFoundry§7 v0.3.2 by §flegaapk §7(§fhttps://github.com/legaapk§7)");
    sender.sendMessage("§7Bridge: §f"+br.getClass().getSimpleName()+" §7| Worlds: §f"+worlds+" §7| Loaded chunks: §f"+chunks+" §7| Entities: §f"+entities);
    sender.sendMessage("§7Modules: §fChunkGuardian="+(cg?"ON":"OFF")+" §7| §fItemFlow="+(iflow?"ON":"OFF")); return true;
  }
  private boolean handleKill(CommandSender sender, String[] args){
    if(!sender.hasPermission("optifoundry.kill")){ sender.sendMessage("§cNo permission."); return true; }
    if(args.length<2){ sender.sendMessage("§7Usage: /opti kill <all|players|entities|items|mobs|animals|villagers|golems|armorstands|tnt|boats|minecarts|bosses|<EntityType>> [radius <blocks>]"); return true; }
    String what=args[1].toLowerCase(); int radius=-1; if(args.length>=4 && "radius".equalsIgnoreCase(args[2])){ try{ radius=Integer.parseInt(args[3]); }catch(NumberFormatException ignored){} }
    Player center=(sender instanceof Player)?(Player)sender:null; int killed=0;
    for(World w:Bukkit.getWorlds()){ for(Entity e:w.getEntities()){
      if(radius>0 && center!=null){ if(e.getLocation().distanceSquared(center.getLocation())>(radius*radius)) continue; }
      if("all".equals(what)){ if(e instanceof Player){ ((Player)e).setHealth(0.0); killed++; } else { e.remove(); killed++; } continue; }
      if("players".equals(what)){ if(e instanceof Player){ ((Player)e).setHealth(0.0); killed++; } continue; }
      if("entities".equals(what)){ if(!(e instanceof Player)){ e.remove(); killed++; } continue; }
      if("items".equals(what)){ if(com.optifoundry.util.KillFilters.isItem(e)){ e.remove(); killed++; } continue; }
      if("mobs".equals(what)){ if(com.optifoundry.util.KillFilters.isMob(e)){ e.remove(); killed++; } continue; }
      if("animals".equals(what)){ if(com.optifoundry.util.KillFilters.isAnimal(e)){ e.remove(); killed++; } continue; }
      if("villagers".equals(what)){ if(com.optifoundry.util.KillFilters.isVillager(e)){ e.remove(); killed++; } continue; }
      if("golems".equals(what)){ if(com.optifoundry.util.KillFilters.isGolem(e)){ e.remove(); killed++; } continue; }
      if("armorstands".equals(what)||"armorstand".equals(what)){ if(com.optifoundry.util.KillFilters.isArmorStand(e)){ e.remove(); killed++; } continue; }
      if("tnt".equals(what)){ if(com.optifoundry.util.KillFilters.isTNTPrimed(e)){ e.remove(); killed++; } continue; }
      if("boats".equals(what)){ if(com.optifoundry.util.KillFilters.isBoat(e)){ e.remove(); killed++; } continue; }
      if("minecarts".equals(what)){ if(com.optifoundry.util.KillFilters.isMinecart(e)){ e.remove(); killed++; } continue; }
      if("bosses".equals(what)){ if(com.optifoundry.util.KillFilters.isBoss(e)){ e.remove(); killed++; } continue; }
      try{ EntityType t=EntityType.valueOf(args[1].toUpperCase()); if(e.getType()==t){ if(e instanceof Player)((Player)e).setHealth(0.0); else e.remove(); killed++; } }catch(IllegalArgumentException ignored){}
    } }
    sender.sendMessage("§a[OptiFoundry] Removed/Killed: §f"+killed); return true;
  }
}
