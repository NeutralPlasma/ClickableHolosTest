package eu.virtusdevelops.simpleholograms.commands

import eu.virtusdevelops.simpleholograms.SimpleHolograms
import eu.virtusdevelops.simpleholograms.hologram.HologramRegistry
import eu.virtusdevelops.virtuscore.command.AbstractCommand
import eu.virtusdevelops.virtuscore.managers.FileManager
import eu.virtusdevelops.virtuscore.utils.HexUtil
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.stream.Collector

class SetLineCommand(private var plugin: SimpleHolograms, val fileManager: FileManager, private val hologramRegistry: HologramRegistry) :
        AbstractCommand(CommandType.PLAYER_ONLY, "setline") {

    override fun runCommand(sender: CommandSender?, vararg args: String?): ReturnType {
        if(args.isNotEmpty() && sender is Player){
            val hologram = hologramRegistry.getHologram(args[0].toString())
            if(hologram != null && args.size > 2){
                val position = args[1]?.toInt()
                if(position != null) {
                    val configuration = fileManager.getConfiguration("holograms")
                    hologram.setLine(args.copyOfRange(2, args.size).joinToString(" "), position)
                    hologram.refresh()
                    configuration.set("${hologram.name}.lines", hologram.lines.toList())
                    fileManager.saveFile("holograms.yml")
                    return ReturnType.SUCCESS
                }else{
                    sender.sendMessage(HexUtil.colorify("&4Unknown number (position on hologram): &e${args[1]}"))
                }
            }else{
                sender.sendMessage(HexUtil.colorify("&4Unknown hologram or missing arguments. Check Syntax."))
            }
        }
        return ReturnType.SUCCESS
    }

    override fun getPermissionNode(): String {
        return "simpleholograms.command.setline"
    }

    override fun onTab(p0: CommandSender?, vararg p1: String?): List<String> {
        if(p1.size == 1) {
            val arg = p1[0]
            if(arg != null) {
                return hologramRegistry.getHolograms().filter { it.name.contains(arg) }.map { it.name }
            }
        }
        return mutableListOf()
    }

    override fun getDescription(): String {
        return "Set the hologram line."
    }

    override fun getSyntax(): String {
       return "/simpleholograms setline <hologram:text> <position:number> <line:text>"
    }
}