package org.dotpast.commands

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class ExportExp : CommandExecutor{
    override fun onCommand(sender: CommandSender, cmd: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            Bukkit.getLogger().info("Console can't use this command")
            return false
        }
        if (args.isEmpty()) {
            sender.sendMessage("§cYou didn't specify amount of bottles")
            return false
        }

        if (args[0] == "all") {
            val playerExp = sender.totalExperience
            val bottles = playerExp.floorDiv(7)
            val cost = bottles*7
            if (cost>playerExp) {
                sender.sendMessage("§cNot enough XP (${cost}>${playerExp}).")
            } else {
                sender.giveExp(-cost)
                sender.inventory.addItem(ItemStack(Material.EXPERIENCE_BOTTLE, bottles))
                sender.sendMessage("§aSuccessfully gave $bottles XP bottles! (~${cost} XP)")
            }
            return true


        }

        try {
            val bottles = args[0].toInt()
            val cost = bottles*7
            val playerExp = sender.totalExperience
            if (cost>playerExp) {
                sender.sendMessage("§cNot enough XP (${cost}>${playerExp}). \nMax. Amount: ${playerExp.floorDiv(7)}")
            } else {
                sender.giveExp(-cost)
                sender.inventory.addItem(ItemStack(Material.EXPERIENCE_BOTTLE, bottles))
                sender.sendMessage("§aSuccessfully gave $bottles XP bottles! (~${cost} XP)")
            }
        } catch (e: NumberFormatException) {
            sender.sendMessage("§cAmount is not a number or all")
            return false
        }

        return true
    }

}
