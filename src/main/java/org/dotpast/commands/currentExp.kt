package org.dotpast.commands


import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class CurrentExp : CommandExecutor {
    override fun onCommand(sender: CommandSender, cmd: Command, label: String, args: Array<out String>?): Boolean {
        if (sender !is Player) {
            Bukkit.getLogger().info("Console can't execute this command")
            return false
        }

        val playerExp = sender.totalExperience
        val maxBottles = playerExp.floorDiv(7)

        sender.sendMessage("§aYour XP: $playerExp (${maxBottles} bottles, ${maxBottles.floorDiv(64)} stacks)")

        return true
    }
}
