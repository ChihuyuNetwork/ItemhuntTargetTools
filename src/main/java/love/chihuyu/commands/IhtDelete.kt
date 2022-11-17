package love.chihuyu.commands

import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.arguments.ArgumentSuggestions
import dev.jorel.commandapi.arguments.StringArgument
import dev.jorel.commandapi.executors.PlayerCommandExecutor
import love.chihuyu.Plugin.Companion.plugin
import love.chihuyu.Plugin.Companion.prefix
import org.bukkit.ChatColor

object IhtDelete {

    val main = CommandAPICommand("delete")
        .withArguments(StringArgument("category").replaceSuggestions(ArgumentSuggestions.strings { plugin.config.getConfigurationSection("targets")?.getKeys(false)?.toTypedArray() }))
        .executesPlayer(
            PlayerCommandExecutor { sender, args ->
                val category = args[0] as String
                plugin.config.set("targets.$category", null)
                plugin.saveConfig()

                sender.sendMessage("$prefix ${ChatColor.GREEN}\"$category\"を削除しました")

                IhtList.main.arguments[0].replaceSuggestions(ArgumentSuggestions.strings { plugin.config.getConfigurationSection("targets")?.getKeys(false)?.toTypedArray() })
            }
        )
}