package love.chihuyu.commands

import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.arguments.ArgumentSuggestions
import dev.jorel.commandapi.arguments.StringArgument
import dev.jorel.commandapi.executors.PlayerCommandExecutor
import love.chihuyu.Plugin.Companion.plugin
import love.chihuyu.Plugin.Companion.prefix
import love.chihuyu.Plugin.Companion.targetConfig
import love.chihuyu.Plugin.Companion.targetFile
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.event.HoverEvent
import net.kyori.adventure.text.format.Style
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.ChatColor
import org.bukkit.block.Chest

object IhtExport {

    val main: CommandAPICommand = CommandAPICommand("export")
        .withArguments(StringArgument("category").replaceSuggestions(ArgumentSuggestions.strings { targetConfig.getConfigurationSection("targets")?.getKeys(false)?.toTypedArray() }))
        .executesPlayer(
            PlayerCommandExecutor { sender, args ->
                val category = args[0] as String
                val block = sender.getTargetBlock(4)?.state as? Chest

                if (block == null) {
                    sender.sendMessage("$prefix${ChatColor.RED} チェストが見当たりません")
                    return@PlayerCommandExecutor
                }

                val data = block.blockInventory.contents.filterNotNull().toMutableSet()

                if (data.isEmpty()) {
                    sender.sendMessage("$prefix${ChatColor.RED} チェストが空です")
                    return@PlayerCommandExecutor
                }

                if (data.none {
                    try {
                        Integer.parseInt(it.itemMeta?.displayName)
                    } catch (e: NumberFormatException) {
                        -1
                    } != -1
                }
                ) {
                    sender.sendMessage("$prefix${ChatColor.RED} 設定できるアイテムが見当たりません")
                    return@PlayerCommandExecutor
                }

                data.forEach {
                    if (!it.hasItemMeta()) return@forEach
                    var point = targetConfig.getInt("targets.$category.${it.type.name}")
                    if (point == 0) point = try {
                        Integer.parseInt(it.itemMeta?.displayName)
                    } catch (e: NumberFormatException) {
                        plugin.logger.warning("${it.itemMeta?.displayName} is cannot parse to int.")
                        -1
                    }
                    targetConfig.set("targets.$category.${it.type.name}", point)
                }

                targetConfig.save(targetFile)

                sender.sendMessage(
                    Component.text("$prefix ${ChatColor.GREEN}アイテム群を\"$category\"で出力しました(${ChatColor.WHITE}")
                        .append(
                            Component.text("ここをホバーで内容を確認")
                                .style(Style.style(TextColor.color(255, 255, 255), TextDecoration.UNDERLINED))
                                .hoverEvent(
                                    HoverEvent.showText(
                                        Component.text(targetConfig.getConfigurationSection("targets.$category")?.getKeys(false)?.joinToString("\n") { "$it: ${targetConfig.getInt("targets.$category.$it")}" } ?: "null")
                                    )
                                )
                        )
                        .append(Component.text("${ChatColor.GREEN})"))
                )

                CommandItemhuntTargetTools.updateSuggestions()
            }
        )
}