package love.chihuyu

import love.chihuyu.commands.CommandItemhuntTargetTools
import org.bukkit.ChatColor
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

class Plugin : JavaPlugin(), Listener {

    companion object {
        lateinit var plugin: JavaPlugin
        lateinit var targetConfig: YamlConfiguration
        val prefix = "${ChatColor.GOLD}[IHT]"
        lateinit var targetFile: File
    }

    init {
        plugin = this
        targetFile = File("${plugin.dataFolder}/targets.yml")
        targetConfig = YamlConfiguration()
    }

    override fun onEnable() {
        server.pluginManager.registerEvents(this, this)

        val dir = File(plugin.dataFolder.path)

        if (!dir.exists()) {
            dir.mkdir()
        }

        if (!targetFile.exists()) {
            targetFile.createNewFile()
        }

        targetConfig.load(targetFile)

        CommandItemhuntTargetTools.main.register()
    }
}