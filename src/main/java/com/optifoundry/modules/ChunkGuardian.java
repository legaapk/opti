
package com.optifoundry.modules;
import com.optifoundry.OptiFoundry; import com.optifoundry.bridge.VersionBridge;
import org.bukkit.*; import org.bukkit.entity.Player;
public final class ChunkGuardian implements Runnable {
  private final OptiFoundry plugin; private final VersionBridge bridge; private final int playerBufferChunks; private final boolean respectForceload=true; private final boolean skipSpawnArea=true; private final int spawnBufferChunks=8;
  public ChunkGuardian(OptiFoundry plugin){ this.plugin=plugin; this.bridge=plugin.bridge(); this.playerBufferChunks=plugin.getConfig().getInt("chunk-guardian.player-buffer-chunks",1); }
  @Override public void run(){ sweep(); }
  public int sweep(){ int unloaded=0; for(World w: Bukkit.getWorlds()){ for(Chunk ch: w.getLoadedChunks()){ if(shouldUnload(w,ch)){ try{ boolean ok=w.unloadChunkRequest(ch.getX(),ch.getZ()) || ch.unload(true); if(ok) unloaded++; }catch(Throwable ignored){} } } } return unloaded; }
  private boolean shouldUnload(World w, Chunk ch){
    if(skipSpawnArea && w.getKeepSpawnInMemory()){ int sx=w.getSpawnLocation().getBlockX()>>4, sz=w.getSpawnLocation().getBlockZ()>>4; if(Math.abs(ch.getX()-sx)<=spawnBufferChunks && Math.abs(ch.getZ()-sz)<=spawnBufferChunks) return false; }
    if(respectForceload && bridge.isForceloadSupported()){ try{ if(bridge.isChunkForceLoaded(ch)) return false; }catch(Throwable ignored){} }
    int view=Bukkit.getViewDistance()+playerBufferChunks; for(Player p: w.getPlayers()){ if(!p.isOnline()) continue; int px=p.getLocation().getBlockX()>>4, pz=p.getLocation().getBlockZ()>>4; if(Math.abs(ch.getX()-px)<=view && Math.abs(ch.getZ()-pz)<=view) return false; }
    return true;
  }
}
