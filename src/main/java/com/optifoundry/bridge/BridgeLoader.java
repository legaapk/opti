
package com.optifoundry.bridge; import org.bukkit.plugin.java.JavaPlugin;
public final class BridgeLoader {
  public static VersionBridge load(JavaPlugin plugin){
    String cb=plugin.getServer().getClass().getPackage().getName();
    try{
      if(cb.contains("v1_12")) return (VersionBridge)Class.forName("com.optifoundry.bridge.impl.BridgeV112").newInstance();
      if(cb.contains("v1_13")||cb.contains("v1_14")||cb.contains("v1_15")||cb.contains("v1_16")||cb.contains("v1_17")||cb.contains("v1_18"))
        return (VersionBridge)Class.forName("com.optifoundry.bridge.impl.BridgeV113_118").newInstance();
      return (VersionBridge)Class.forName("com.optifoundry.bridge.impl.BridgeV119_121").newInstance();
    }catch(Throwable t){ plugin.getLogger().warning("[OptiFoundry] Bridge load failed, using Noop."); return new Noop(); }
  }
  private static final class Noop implements VersionBridge{ public boolean isForceloadSupported(){return false;} public boolean isChunkForceLoaded(org.bukkit.Chunk c){return false;} public void setChunkForceLoaded(org.bukkit.Chunk c, boolean v){} }
}
