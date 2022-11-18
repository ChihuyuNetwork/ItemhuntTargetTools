package love.chihuyu.commands

import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.arguments.ArgumentSuggestions
import dev.jorel.commandapi.arguments.StringArgument
import dev.jorel.commandapi.executors.PlayerCommandExecutor
import love.chihuyu.Plugin.Companion.prefix
import love.chihuyu.Plugin.Companion.targetConfig
import love.chihuyu.Plugin.Companion.targetFile
import org.bukkit.ChatColor

object IhtDelete {

    val main: CommandAPICommand = CommandAPICommand("delete")
        .withArguments(StringArgument("category").replaceSuggestions(ArgumentSuggestions.strings { targetConfig.getConfigurationSection("targets")?.getKeys(false)?.toTypedArray() }))
        .executesPlayer(
            PlayerCommandExecutor { sender, args ->
                val category = args[0] as String
                targetConfig.set("targets.$category", null)
                targetConfig.save(targetFile)

                sender.sendMessage("$prefix ${ChatColor.GREEN}\"$category\"を削除しました")

                CommandItemhuntTargetTools.updateSuggestions()
            }
        )
}