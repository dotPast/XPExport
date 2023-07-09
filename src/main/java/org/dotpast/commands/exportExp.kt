package org.dotpast.commands

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class ExportExp : CommandExecutor{
    private fun getFreeSlots(player: Player): Int {
        var freeSlots = 0
        for (i in 0 until player.inventory.size) {
            val item = player.inventory.getItem(i)
            if (item == null && i !in 36..40) {
                freeSlots++
            }
        }
        return freeSlots*64
    }

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
            var bottles = playerExp.floorDiv(7)
            var cost = bottles*7

            if (bottles>getFreeSlots(sender)) {
                bottles = getFreeSlots(sender)
                cost = bottles*7
                sender.giveExp(-cost)
                sender.inventory.addItem(ItemStack(Material.EXPERIENCE_BOTTLE, bottles))
                sender.sendMessage("§aSuccessfully gave $bottles XP bottles! (~${cost} XP)")
                return true
            }

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

            if (bottles>getFreeSlots(sender)) {
                sender.sendMessage("§cNot enough free inventory space. ($bottles>${getFreeSlots(sender)})")
                return false
            }

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
