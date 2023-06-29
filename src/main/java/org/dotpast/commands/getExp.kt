package org.dotpast.commands

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class GetExp : CommandExecutor {
    private fun getXPBottles(player: Player): Int {
        var xpBottles = 0
        for (item in player.inventory) {
            if (item?.type == Material.EXPERIENCE_BOTTLE) {
                xpBottles += item.amount

            }
        }
        return xpBottles
    }

    override fun onCommand(sender: CommandSender, cmd: Command, label: String, args: Array<out String>?): Boolean {
        if (sender !is Player) {
            Bukkit.getLogger().info("Console can't use this command")
            return false
        }

        val bottles = getXPBottles(sender)
        val xp = bottles*7
        val playerInventory = sender.inventory

        if (bottles == 0) {
            sender.sendMessage("§cYou don't have bottles in your inventory")
            return false
        }

        playerInventory.remove(Material.EXPERIENCE_BOTTLE)
        sender.giveExp(xp)
        sender.sendMessage("§aSuccessfully converted $bottles XP bottles into $xp XP!")

        return true
    }
}