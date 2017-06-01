package me.deceptions.scalaplugin

import org.bukkit.command.{Command, CommandSender}
import org.bukkit.event.block.{BlockBreakEvent, BlockPlaceEvent}
import org.bukkit.event.entity.FoodLevelChangeEvent
import org.bukkit.{Bukkit, ChatColor, Location}
import org.bukkit.event.{EventHandler, EventPriority, Listener}
import org.bukkit.event.player.{AsyncPlayerChatEvent, PlayerCommandPreprocessEvent, PlayerJoinEvent, PlayerQuitEvent}
import org.bukkit.event.server.ServerListPingEvent
import org.bukkit.plugin.java.JavaPlugin

class Main extends JavaPlugin with Listener {

  override def onEnable(): Unit = {
    Bukkit.getPluginManager.registerEvents(this, this)
    getCommand("core").setExecutor(this)
  }

  @EventHandler
  def onJoin(e: PlayerJoinEvent): Unit = {
    val p = e.getPlayer
    e.setJoinMessage(null)
    Bukkit.broadcastMessage(col("&7[&a+&7] &7" + p.getName))
  }

  @EventHandler
  def onLeave(e: PlayerQuitEvent): Unit = {
    val p = e.getPlayer
    e.setQuitMessage(null)
    Bukkit.broadcastMessage(col("&7[&c-&7] &7" + p.getName))
  }

  @EventHandler
  def onPing(e: ServerListPingEvent): Unit = {
    e.setMaxPlayers(-1)
    e.setMotd(col("&7Corey's test server. &f| &7Github: &citsfxa\n&7https://deceptions.me &f| &7Twitter: &c@itsfxa"))
  }

  override def onCommand(sender: CommandSender, command: Command, label: String, args: Array[String]): Boolean = {
    val p = sender
    p.sendMessage(col("&7Core coded by: &cFXA"))
    false
  }

  @EventHandler(ignoreCancelled = true)
  def onVer(e: PlayerCommandPreprocessEvent): Unit = {
    val msg = e.getMessage.toLowerCase
    if (msg.startsWith("/ver") || msg.startsWith("/pl") || msg.startsWith("/?") || msg.startsWith("/bukkit") || msg.contains(":") || msg.startsWith("/canihasbukkit")) {
      e.getPlayer.sendMessage(col("&cno."))
      // yes I know this wont stop /ver <tab> but that doesn't really bother me.
      e.setCancelled(true)
    }
  }

  @EventHandler
  def spawnJoin(e: PlayerJoinEvent): Unit = {
    val p = e.getPlayer
    val x: Double = -694.594
    val y: Double = 78.0625
    val z: Double = 205.495
    val spawn: Location = new Location(p.getWorld, x, y, z)
    p.teleport(spawn)
  }

  @EventHandler
  def onBreak(e: BlockBreakEvent): Unit = {
    val p = e.getPlayer
    if (!p.isOp) {
      e.setCancelled(true)
    } else {
      e.setCancelled(false)
    }
  }

  @EventHandler
  def onPlace(e: BlockPlaceEvent): Unit = {
    val p = e.getPlayer
    if (!p.isOp) {
      e.setCancelled(true)
    } else {
      e.setCancelled(false)
    }
  }

  @EventHandler
  def foodLoss(e: FoodLevelChangeEvent): Unit = {
    e.setCancelled(true)
  }

  @EventHandler
  def chat(e: AsyncPlayerChatEvent): Unit = {
    val p = e.getPlayer
    val msg = e.getMessage
    if (!p.isOp) {
      e.setFormat(col("&f" + p.getName + "&7: &f" + msg))
    } else {
      e.setFormat(col("&7[&cOP&7] &7" + p.getName + ": &f" + msg))
    }
  }

  def col(text: String): String = ChatColor.translateAlternateColorCodes('&', text)
}