package love.chihuyu.commands

import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.arguments.ArgumentSuggestions
import dev.jorel.commandapi.arguments.StringArgument
import dev.jorel.commandapi.executors.PlayerCommandExecutor
import love.chihuyu.Plugin.Companion.prefix
import love.chihuyu.Plugin.Companion.targetConfig
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.event.HoverEvent
import org.bukkit.ChatColor
import org.bukkit.Material

object IhtList {

    val main: CommandAPICommand = CommandAPICommand("list")
        .withArguments(StringArgument("category").replaceSuggestions(ArgumentSuggestions.strings { targetConfig.getConfigurationSection("targets")?.getKeys(false)?.toTypedArray() }))
            .executesPlayer(
            PlayerCommandExecutor { sender, args ->
                val category = args[0] as String
                val list = targetConfig.getConfigurationSection("targets.$category")

                if (list == null) {
                    sender.sendMessage("$prefix ${ChatColor.RED}カテゴリーがありません")
                }

                sender.sendMessage("$prefix ${ChatColor.RESET}\"$category\"のアイテム一覧です")

                list?.getKeys(false)?.forEach {
                    val prefixComp = Component.text("$prefix ")
                    val materialPoint = targetConfig.getInt("targets.$category.$it")
                    val comp = Component.text("${ChatColor.RESET}$it: $materialPoint")
                        .hoverEvent(HoverEvent.showItem(Material.valueOf(it).key(), materialPoint))
                    sender.sendMessage(prefixComp.append(comp))
                }

                CommandItemhuntTargetTools.updateSuggestions()
            }
        )
}