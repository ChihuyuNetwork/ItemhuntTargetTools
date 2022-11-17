package love.chihuyu.commands

import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.CommandPermission

object CommandItemhuntTargetTools {

    val main = CommandAPICommand("itemhunttargettools")
        .withAliases("iht")
        .withPermission(CommandPermission.OP)
        .withSubcommands(
            IhtExport.main,
            IhtDelete.main,
            IhtList.main
        )
}