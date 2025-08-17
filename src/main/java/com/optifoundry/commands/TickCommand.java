
package com.optifoundry.commands;
import com.optifoundry.modules.TickManager;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
public final class TickCommand implements CommandExecutor{
  private final TickManager ticks; public TickCommand(TickManager tm){ this.ticks=tm; }
  @Override public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
    if(!sender.hasPermission("optifoundry.tick")){ sender.sendMessage("§cNo permission."); return true; }
    if(args.length==0 || args[0].equalsIgnoreCase("query")){
      double[] tps=getTps();
      sender.sendMessage(String.format("§aTick§7 | target: §f%d§7 | frozen: §f%s§7 | TPS(1/5/15): §f%.1f / %.1f / %.1f", ticks.getTargetRate(), ticks.isFrozen(), tps[0], tps[1], tps[2]));
      return true;
    }
    switch(args[0].toLowerCase()){
      case "rate":
        if(args.length<2){ sender.sendMessage("§7Usage: /"+label+" rate <1-10000>"); return true; }
        try{ int r=Integer.parseInt(args[1]); ticks.setTargetRate(r); sender.sendMessage("§aTick rate set to §f"+r+"§7 (server cap ~20 TPS)."); }
        catch(NumberFormatException ex){ sender.sendMessage("§cNot a number."); } return true;
      case "freeze": ticks.freeze(); sender.sendMessage("§aWorld frozen."); return true;
      case "unfreeze": ticks.unfreeze(); sender.sendMessage("§aWorld unfrozen."); return true;
      case "step":
        if(args.length>=2 && args[1].equalsIgnoreCase("stop")){ ticks.stopStep(); sender.sendMessage("§eTick step stopped; world frozen again."); return true; }
        if(args.length<2){ sender.sendMessage("§7Usage: /"+label+" step <ticks>"); return true; }
        try{ int n=Integer.parseInt(args[1]); ticks.step(n); sender.sendMessage("§aStepping §f"+n+"§a ticks."); }
        catch(NumberFormatException ex){ sender.sendMessage("§cNot a number."); } return true;
      case "sprint":
        if(args.length>=2 && args[1].equalsIgnoreCase("stop")){ ticks.stopSprint(); sender.sendMessage("§eTick sprint stopped."); return true; }
        if(args.length<2){ sender.sendMessage("§7Usage: /"+label+" sprint <seconds>"); return true; }
        try{ int n=Integer.parseInt(args[1]); ticks.sprint(n); sender.sendMessage("§aTick sprint for §f"+n+"§a seconds (best-effort)."); }
        catch(NumberFormatException ex){ sender.sendMessage("§cNot a number."); } return true;
      default:
        sender.sendMessage("§7Usage: /"+label+" [query|rate <1-10000>|freeze|unfreeze|step <ticks>|step stop|sprint <seconds>|sprint stop]"); return true;
    }
  }
  private double[] getTps(){ try{ Object server=Bukkit.getServer(); java.lang.reflect.Method m=server.getClass().getMethod("getTPS"); Object v=m.invoke(server); if(v instanceof double[]) return (double[])v; }catch(Throwable ignored){} return new double[]{-1.0,-1.0,-1.0}; }
}
