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
            Bukkit.getLogger().info("Консоль не может использовать эту команду")
            return false
        }
        if (args.isEmpty()) {
            sender.sendMessage("§cВы не указали кол-во бутылок")
            return false
        }

        if (args[0] == "all") {
            val playerExp = sender.totalExperience
            val bottles = playerExp.floorDiv(7)
            val cost = bottles*7
            if (cost>playerExp) {
                sender.sendMessage("§cНедостаточно опыта (${cost}>${playerExp}).")
            } else {
                sender.giveExp(-cost)
                sender.inventory.addItem(ItemStack(Material.EXPERIENCE_BOTTLE, bottles))
                sender.sendMessage("§aУспешно выданно $bottles бутылок! (~${cost} опыта)")
            }
            return true


        }

        try {
            val bottles = args[0].toInt()
            val cost = bottles*7
            val playerExp = sender.totalExperience
            if (cost>playerExp) {
                sender.sendMessage("§cНедостаточно опыта (${cost}>${playerExp}). \nМаксимально возможное количество: ${playerExp.floorDiv(7)}")
            } else {
                sender.giveExp(-cost)
                sender.inventory.addItem(ItemStack(Material.EXPERIENCE_BOTTLE, bottles))
                sender.sendMessage("§aУспешно выданно $bottles бутылок! (~${cost} опыта)")
            }
        } catch (e: NumberFormatException) {
            sender.sendMessage("§cВы указали не число")
            return false
        }

        return true
    }

}