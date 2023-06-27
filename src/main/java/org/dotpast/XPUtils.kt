package org.dotpast

import org.bukkit.plugin.java.JavaPlugin
import org.dotpast.commands.CurrentExp
import org.dotpast.commands.ExportExp
import org.dotpast.commands.TransferExp

class XPUtils : JavaPlugin() {
    override fun onEnable() {
        // Plugin startup logic
        getCommand("exportxp")?.setExecutor(ExportExp())
        getCommand("currentxp")?.setExecutor(CurrentExp())
        getCommand("transferxp")?.setExecutor(TransferExp())
    }
}
