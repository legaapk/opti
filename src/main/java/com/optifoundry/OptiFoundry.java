
package com.optifoundry;
import com.optifoundry.bridge.*; import com.optifoundry.commands.TickCommand; import com.optifoundry.modules.*; import org.bukkit.Bukkit; import org.bukkit.plugin.java.JavaPlugin;
public final class OptiFoundry extends JavaPlugin {
  private VersionBridge bridge; private ChunkGuardian chunkGuardian; private ItemFlow itemFlow; private TickManager tickManager;
  @Override public void onEnable(){
    saveDefaultConfig(); bridge=BridgeLoader.load(this);
    getCommand("opti").setExecutor(new OptiFoundryCommand(this));
    if(getConfig().getBoolean("item-flow.enabled", true)){ itemFlow=new ItemFlow(this); getServer().getPluginManager().registerEvents(itemFlow,this); Bukkit.getScheduler().runTaskTimer(this,itemFlow,40L,40L); }
    if(getConfig().getBoolean("chunk-guardian.enabled", true)){ chunkGuardian=new ChunkGuardian(this); int interval=Math.max(40,getConfig().getInt("chunk-guardian.scan-interval-ticks",60)); Bukkit.getScheduler().runTaskTimer(this,chunkGuardian,interval,interval); }
    tickManager=new TickManager(this); getServer().getPluginManager().registerEvents(tickManager,this); Bukkit.getScheduler().runTaskTimer(this,tickManager,1L,1L);
    getCommand("optick").setExecutor(new TickCommand(tickManager));
    getLogger().info("[OptiFoundry] Loaded bridge: "+bridge.getClass().getSimpleName());
  }
  public VersionBridge bridge(){ return bridge; } public ChunkGuardian chunkGuardian(){ return chunkGuardian; } public ItemFlow itemFlow(){ return itemFlow; } public TickManager tickManager(){ return tickManager; }
}
