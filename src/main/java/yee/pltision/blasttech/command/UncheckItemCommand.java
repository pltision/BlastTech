package yee.pltision.blasttech.command;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.SlotArgument;
import net.minecraft.commands.arguments.item.ItemArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class UncheckItemCommand {

    public static final DynamicCommandExceptionType ERROR_SOURCE_INAPPLICABLE_SLOT = new DynamicCommandExceptionType((p_180353_) -> {
        return Component.translatable("commands.item.source.no_such_slot", p_180353_);
    });

    @SubscribeEvent
    public static void registry(RegisterCommandsEvent event){
        CommandBuildContext buildContext= event.getBuildContext();
        event.getDispatcher().register(
                Commands.literal("bt_item").requires((stack)->stack.hasPermission(2))
                        .then(Commands.argument("entity",EntityArgument.entity())
                                .then(Commands.argument("slot", SlotArgument.slot())
                                        .then(Commands.argument("item", ItemArgument.item(buildContext))
                                                .then(Commands.argument("count", IntegerArgumentType.integer())
                                                        .executes((context)->replaceItem(context.getSource(),EntityArgument.getEntity(context,"entity"),SlotArgument.getSlot(context,"slot"),ItemArgument.getItem(context,"item").createItemStack(IntegerArgumentType.getInteger(context,"count"),true)))
                                                )
                                                .executes((context)->replaceItem(context.getSource(),EntityArgument.getEntity(context,"entity"),SlotArgument.getSlot(context,"slot"),ItemArgument.getItem(context,"item").createItemStack(1,true)))
                                        )
                                )
                        )
        );
    }

    public static int replaceItem(CommandSourceStack source, Entity entity, int slot, ItemStack item) throws CommandSyntaxException {

        if(entity instanceof ServerPlayer player){
            if(slot==99) player.getInventory().offhand.set(0,item);
            else if(slot>=100)
                player.getInventory().armor.set(slot-100,item);
            else player.containerMenu.broadcastChanges();
        }
        else{
            SlotAccess slotAccess= entity.getSlot(slot);
            if(slotAccess==SlotAccess.NULL) throw ERROR_SOURCE_INAPPLICABLE_SLOT.create(slot);
//            System.out.println(slotAccess);
            slotAccess.set(item);
        }
        source.sendSuccess(() -> {
            return Component.translatable("commands.item.entity.set.success.single", entity.getName(), item.getDisplayName()).append(" Slot="+slot);
        }, true);
        return 0;
    }
}
