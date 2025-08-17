
package com.optifoundry.modules;
import org.bukkit.*; import org.bukkit.entity.*; import org.bukkit.event.*; import org.bukkit.event.block.*; import org.bukkit.event.entity.*; import org.bukkit.event.vehicle.VehicleMoveEvent; import org.bukkit.util.Vector;
public final class TickManager implements Listener, Runnable{
  private final org.bukkit.plugin.Plugin plugin; private boolean frozen=false; private int targetRate=20; private boolean stepping=false; private int stepTicks=0; private long sprintUntilMs=0L; private int tickCounter=0;
  public TickManager(org.bukkit.plugin.Plugin plugin){ this.plugin=plugin; }
  public boolean isFrozen(){ return frozen; } public int getTargetRate(){ return targetRate; }
  public void setTargetRate(int rate){ if(rate<1) rate=1; if(rate>10000) rate=10000; this.targetRate=rate; }
  public void freeze(){ frozen=true; for(World w:Bukkit.getWorlds()) for(Entity e:w.getEntities()){ if(e instanceof Player) continue; try{ e.setVelocity(new Vector(0,0,0)); }catch(Throwable ignored){} if(e instanceof LivingEntity){ try{ ((LivingEntity)e).setAI(false);}catch(Throwable ignored){} } if(e instanceof Item){ Item it=(Item)e; try{ it.setGravity(false); it.setPickupDelay(0); it.setVelocity(new Vector(0,0,0)); }catch(Throwable ignored){} } } }
  public void unfreeze(){ frozen=false; stepping=false; stepTicks=0; for(World w:Bukkit.getWorlds()) for(Entity e:w.getEntities()){ if(e instanceof LivingEntity){ try{ ((LivingEntity)e).setAI(true);}catch(Throwable ignored){} } if(e instanceof Item){ try{ ((Item)e).setGravity(true);}catch(Throwable ignored){} } } }
  public void step(int ticks){ if(ticks<=0) return; stepping=true; stepTicks=ticks; frozen=false; }
  public void stopStep(){ stepping=false; stepTicks=0; frozen=true; }
  public void sprint(int seconds){ if(seconds<=0) seconds=1; sprintUntilMs=System.currentTimeMillis()+seconds*1000L; frozen=false; }
  public void stopSprint(){ sprintUntilMs=0L; }
  @Override public void run(){ tickCounter++; if(sprintUntilMs>0 && System.currentTimeMillis()>=sprintUntilMs) stopSprint(); if(stepping){ if(stepTicks>0) stepTicks--; if(stepTicks==0){ stepping=false; frozen=true; } } }
  private boolean gateActive(){ if(sprintUntilMs>0) return false; if(stepping) return false; return frozen; }
  @EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true) public void onBlockPhysics(BlockPhysicsEvent e){ if(gateActive()) e.setCancelled(true); }
  @EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true) public void onBlockForm(BlockFormEvent e){ if(gateActive()) e.setCancelled(true); }
  @EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true) public void onSpread(BlockSpreadEvent e){ if(gateActive()) e.setCancelled(true); }
  @EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true) public void onGrow(BlockGrowEvent e){ if(gateActive()) e.setCancelled(true); }
  @EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true) public void onFade(BlockFadeEvent e){ if(gateActive()) e.setCancelled(true); }
  @EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true) public void onFromTo(BlockFromToEvent e){ if(gateActive()) e.setCancelled(true); }
  @EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true) public void onExplode(EntityExplodeEvent e){ if(gateActive()) e.setCancelled(true); }
  @EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true) public void onPrime(ExplosionPrimeEvent e){ if(gateActive()) e.setCancelled(true); }
  @EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true) public void onSpawn(CreatureSpawnEvent e){ if(gateActive()) e.setCancelled(true); }
  @EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true) public void onProjectile(ProjectileLaunchEvent e){ if(gateActive()) e.setCancelled(true); }
  @EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true) public void onEntityMove(EntityChangeBlockEvent e){ if(gateActive()) e.setCancelled(true); }
  @EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true) public void onItemSpawn(ItemSpawnEvent e){ if(gateActive()){ /* let it spawn, we already stop motion in freeze() */ } }
}
