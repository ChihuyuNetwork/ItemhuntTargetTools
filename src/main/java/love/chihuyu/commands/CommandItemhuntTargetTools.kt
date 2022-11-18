package love.chihuyu.commands

import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.CommandPermission
import dev.jorel.commandapi.arguments.ArgumentSuggestions
import love.chihuyu.Plugin

object CommandItemhuntTargetTools {

    fun updateSuggestions() = main.subcommands.forEach {
        if (it.arguments.size > 0) it.arguments[0].replaceSuggestions(ArgumentSuggestions.strings { Plugin.targetConfig.getConfigurationSection("targets")?.getKeys(false)?.toTypedArray() })
    }

    val main = CommandAPICommand("itemhunttargettools")
        .withAliases("iht")
        .withPermission(CommandPermission.OP)
        .withSubcommands(
            IhtExport.main,
            IhtDelete.main,
            IhtList.main
        )
}