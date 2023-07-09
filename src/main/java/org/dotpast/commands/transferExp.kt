package org.dotpast.commands

import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.lang.NumberFormatException

class TransferExp : CommandExecutor {
    override fun onCommand(sender: CommandSender, cmd: Command, label: String, args: Array<out String>?): Boolean {
        if (sender !is Player) {
            Bukkit.getLogger().info("Console can't use this command")
            return false
        }
        if (args!!.isEmpty()) {
            sender.sendMessage("§cYou didn't specify arguments")
            return false
        }

        val transferTo = try {
            Bukkit.getPlayer(args[1])
        } catch (e: ArrayIndexOutOfBoundsException) {
            sender.sendMessage("§cYou didn't specify the player or player is offline")
            return false
        }

        if (transferTo == sender) {
            sender.sendMessage("§cYou can't give XP to yourself")
            return false
        }

        if (args[0] == "all") {
            val playerExp = sender.totalExperience
            sender.giveExp(-playerExp)
            sender.sendMessage("§aSuccessfully gave $playerExp XP to ${transferTo!!.name}!")
            transferTo.giveExp(playerExp)
            transferTo.sendMessage("§a${sender.name} just gave you $playerExp XP!")
            return true
        }

        try {
            val cost = args[0].toInt()
            val playerExp = sender.totalExperience

            if (cost > playerExp) {
                sender.sendMessage("§cNot enough XP (${cost} > ${playerExp}).")
            } else {
                sender.giveExp(-cost)
                sender.sendMessage("§aSuccessfully gave $cost XP to ${transferTo!!.name}!")
                transferTo.giveExp(cost)
                transferTo.sendMessage("§a${sender.name} just gave you $cost XP!")
            }

        } catch (e: NumberFormatException) {
            sender.sendMessage("§cFirst argument is not a number")
            return false
        }


        return true
    }
}