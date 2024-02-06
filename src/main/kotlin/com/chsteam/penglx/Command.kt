package com.chsteam.penglx

import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import taboolib.common.platform.command.CommandBody
import taboolib.common.platform.command.CommandHeader
import taboolib.common.platform.command.subCommand

@CommandHeader("elitehelp")
object Command {

    @CommandBody
    val createItem = subCommand {
        dynamic {
            dynamic {
                execute<CommandSender> { sender, context, _ ->
                    val item = Util.spawnItem(context.argument(-1), context.argument(0).toInt())
                    if(sender is Player) {
                        item?.let {
                            sender.inventory.addItem(it)
                        }
                    }
                }
            }
        }
    }

    @CommandBody
    val reload = subCommand {
        execute<CommandSender> { _, _, _ ->
            EliteHelper.load()
        }
    }
}