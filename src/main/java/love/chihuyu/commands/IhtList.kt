package love.chihuyu.commands

import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.arguments.ArgumentSuggestions
import dev.jorel.commandapi.arguments.StringArgument
import dev.jorel.commandapi.executors.PlayerCommandExecutor
import love.chihuyu.Plugin.Companion.plugin
import love.chihuyu.Plugin.Companion.prefix
import org.bukkit.ChatColor

object IhtList {

    val main = CommandAPICommand("list")
        .withArguments(StringArgument("category").replaceSuggestions(ArgumentSuggestions.strings { plugin.config.getConfigurationSection("targets")?.getKeys(false)?.toTypedArray() }))
            .executesPlayer(
            PlayerCommandExecutor { sender, args ->
                val category = args[0] as String
                val list = plugin.config.getConfigurationSection("targets.$category")

                if (list == null) {
                    sender.sendMessage("$prefix ${ChatColor.RED}カテゴリーがありません")
                }

                sender.sendMessage(
                    "$prefix ${ChatColor.RESET}\"$category\"のアイテム一覧です\n" + (list?.getKeys(false)?.joinToString("\n") { "$prefix ${ChatColor.RESET}$it: ${plugin.config.getInt("targets.$category.$it")}" } ?: "$prefix null")
                )
            }
        )
}