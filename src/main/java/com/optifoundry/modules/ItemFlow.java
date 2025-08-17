
package com.optifoundry.modules;
import com.optifoundry.OptiFoundry;
import org.bukkit.Bukkit;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import java.util.*;
public final class ItemFlow implements Listener, Runnable {
  private static final String META_BIRTH="optifoundry_birth_ms";
  private final OptiFoundry plugin; private final double mergeRadius; private final int maxStack; private final long ttlMillis; private final Set<String> blacklist;
  public ItemFlow(OptiFoundry plugin){ this.plugin=plugin; this.mergeRadius=plugin.getConfig().getDouble("item-flow.merge-radius",2.5D); this.maxStack=plugin.getConfig().getInt("item-flow.max-stack",64); this.ttlMillis=plugin.getConfig().getInt("item-flow.ttl-seconds",180)*1000L; this.blacklist=new HashSet<>(plugin.getConfig().getStringList("item-flow.blacklist-materials")); }
  @EventHandler public void onItemSpawn(ItemSpawnEvent e){
    Item item=e.getEntity(); if(item==null||item.getItemStack()==null) return; if(blacklist.contains(item.getItemStack().getType().name())) return;
    item.setMetadata(META_BIRTH,new FixedMetadataValue(plugin,System.currentTimeMillis()));
    for(org.bukkit.entity.Entity ent: item.getNearbyEntities(mergeRadius,mergeRadius,mergeRadius)){
      if(!(ent instanceof Item)) continue; Item other=(Item)ent; if(!other.isValid()||other.isDead()) continue;
      if(other.getItemStack().getType()!=item.getItemStack().getType()) continue;
      int total=item.getItemStack().getAmount()+other.getItemStack().getAmount();
      int moved=Math.min(maxStack,total);
      item.getItemStack().setAmount(moved);
      other.getItemStack().setAmount(total-moved);
      if(other.getItemStack().getAmount()<=0) other.remove();
      if(item.getItemStack().getAmount()>=maxStack) break;
    }
  }
  @Override public void run(){ sweepTTL(); }
  public int sweepTTL(){ long now=System.currentTimeMillis(); int removed=0;
    for(org.bukkit.World w: Bukkit.getWorlds()){
      for(Item it: w.getEntitiesByClass(Item.class)){
        if(!it.isValid()||it.isDead()) continue;
        List<MetadataValue> meta=it.getMetadata(META_BIRTH); long birth=-1; if(!meta.isEmpty()){ try{ birth=meta.get(0).asLong(); }catch(Throwable ignored){} }
        if(birth>0 && now-birth>=ttlMillis){ it.remove(); removed++; }
      }
    } return removed;
  }
}
