<p align="left">
  <a href="https://github.com/legaapk/opti/">
    <img src="https://i.postimg.cc/FKc06sCz/9185b6a8-2632-4d74-a8fb-49fdad44a0e0-1-1.png" alt="GitHub" height="50">
  </a>   
                                                                                                             
  <a href="https://github.com/legaapk/opti/">
    <img src="https://i.postimg.cc/Kzq8kZqp/image-2.png" alt="GitHub" height="50">
  </a>   
                                                                                                                                                             
  <a href="https://t.me/legaapk">
    <img src="https://i.postimg.cc/7LN641gk/image-3.png" alt="Telegram" height="50">
  </a>     
                                                                                                                                                           
  <a href="https://discord.gg/EsgEe4sPpw">
    <img src="https://i.postimg.cc/QtxMdBbW/image-4.png" alt="Discord" height="50">
  </a>  
                                                              
  <a href="https://modrinth.com/plugin/optifoundry">
    <img src="https://i.postimg.cc/P5TJRFw4/image-1.png" alt="Modrinth" height="50">
  </a>                                                                                                                                                                                                
</p>

# OptiFoundry

A lightweight performance-oriented plugin for Spigot / Paper servers.  
Designed to give server owners more control over tick rates, entity management, and chunk optimization.  

## ✨ Features
- `/tick` — advanced tick control (query, freeze, unfreeze, step, sprint, rate).
- `/opti kill` — flexible entity remover (mobs, animals, boats, minecarts, armor stands, TNT, items, and even players with `/opti kill all`).
- `/opti chunksweep` — clears unused or stuck chunks.
- `/opti help` — lists all available commands.
- `/optick` — shortcut for tick commands.

## 📜 Commands
- `/tick query` – shows current TPS, average tick time, and statistics.  
- `/tick rate <1–10000>` – sets target tick speed (default: 20).  
- `/tick freeze` – freezes the world (players can still move, but items, TNT, rain, etc. stop).  
- `/tick unfreeze` – unfreezes the game.  
- `/tick step <ticks>` – advances the game by a set number of ticks while frozen.  
- `/tick sprint <seconds>` – runs the game at maximum speed for a limited time.  
- `/opti kill <type>` – removes specific entities (`mobs`, `animals`, `boats`, `minecarts`, `armorstands`, `tnt`, `items`, `players`, `all`).  
- `/opti chunksweep` – cleans up broken or inactive chunks.  
- `/opti help` – command reference.  

## 🛠 Compatibility
- **Minecraft 1.12 → 1.21+**  
- Tested on **Spigot** and **Paper**.

## 📦 Installation
1. Download the latest release from [Modrinth](https://modrinth.com/plugin/optifoundry).
2. Drop the `.jar` into your `plugins` folder.
3. Restart your server.

## 📄 License
MIT License.  
Free to use, modify, and share.
