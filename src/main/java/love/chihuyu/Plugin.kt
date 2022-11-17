package love.chihuyu

import love.chihuyu.commands.CommandItemhuntTargetTools
import org.bukkit.ChatColor
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin

class Plugin : JavaPlugin(), Listener {

    companion object {
        lateinit var plugin: JavaPlugin
        val prefix = "${ChatColor.GOLD}[IHT]"
    }

    init {
        plugin = this
    }

    override fun onEnable() {
        server.pluginManager.registerEvents(this, this)

        CommandItemhuntTargetTools.main.register()
    }
}
