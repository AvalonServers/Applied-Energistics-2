package appeng.server.subcommands;

import appeng.api.networking.IGridBlock;
import appeng.api.networking.IGridHost;
import appeng.api.networking.IGridNode;
import appeng.api.util.IReadOnlyCollection;
import appeng.hooks.TickHandler;
import appeng.me.Grid;
import appeng.server.ISubCommand;
import com.google.common.collect.Iterators;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

import java.util.Iterator;

public class Debugger implements ISubCommand {
    @Override
    public String getHelp(MinecraftServer srv) {
        return "commands.ae2.Debugger";
    }

    @Override
    public void call(MinecraftServer srv, String[] args, ICommandSender sender) {
        TickHandler handler = TickHandler.INSTANCE;
        sender.sendMessage(new TextComponentString("Printing Grid Debug Information"));

        int i = 0;
        for (Grid o : handler.getGridList()) {
            i++;
            sender.sendMessage(new TextComponentString("----- Grid " + i + " -----"));

            if (o.isEmpty()) {
                sender.sendMessage(new TextComponentString("Pivot: Empty Grid!"));
            } else {
                IGridNode pivot = o.getPivot();
                IGridBlock block = pivot.getGridBlock();
                if (block.isWorldAccessible()) {
                    sender.sendMessage(new TextComponentString("Pivot: " + block.getLocation()));
                } else {
                    sender.sendMessage(new TextComponentString("Pivot: Not World Accessible"));
                }
            }

            sender.sendMessage(new TextComponentString("Priority: " + o.getPriority()));
            sender.sendMessage(new TextComponentString("Node Count: " + o.getNodes().size()));

            // count machines
            sender.sendMessage(new TextComponentString("Machine Counts: "));
            IReadOnlyCollection<Class<? extends IGridHost>> classes = o.getMachinesClasses();
            for (Class<? extends IGridHost> c : classes) {
                sender.sendMessage(new TextComponentString("  " + c.getName() + ": " + o.getMachines(c).size()));
            }

            // count caches
//            sender.sendMessage(new TextComponentString("Cache Counts: "));
//            Map<Class<? extends IGridCache>, GridCacheWrapper> caches = o.getCaches();
//            for (Map.Entry<Class<? extends IGridCache>, GridCacheWrapper> e : caches.entrySet()) {
//                sender.sendMessage(new TextComponentString("  " + e.getClass() + ": " + e.getValue().getCache().));
//            }
//
//            sender.sendMessage(new TextComponentString(o.toString()));
        }
    }
}
