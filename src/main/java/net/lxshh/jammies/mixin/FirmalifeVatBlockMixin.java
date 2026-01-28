package net.lxshh.jammies.mixin;

import com.eerussianguy.firmalife.common.blockentities.VatBlockEntity;
import com.eerussianguy.firmalife.common.blocks.oven.VatBlock;
import net.dries007.tfc.common.items.TFCItems;
import net.dries007.tfc.util.Helpers;
import net.lxshh.jammies.common.component.ModComponents;
import net.lxshh.jammies.common.component.LidDataComponent;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = VatBlock.class, remap = false)
public class FirmalifeVatBlockMixin {

    @Inject(method = "useItemOn", at = @At("HEAD"), cancellable = true, remap = false)
    public void useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result, CallbackInfoReturnable<ItemInteractionResult> cir) {
        if (!(level.getBlockEntity(pos) instanceof VatBlockEntity vat)) {
            return;
        }
        if (vat.isBoiling()) {
            return;
        }
        if (!vat.hasOutput()) {
            return;
        }

        // rework how sealed jars interact with the recipe
        if (Helpers.isItem(stack, TFCItems.EMPTY_JAR_WITH_LID)) {
            // get the component before we shrink
            LidDataComponent component = stack.get(ModComponents.JAR_LID_COMPONENT);

            stack.shrink(1);
            ItemStack output = vat.takeOutput();

            // transfer the component to the result item
            if (component != null) {
                output.set(ModComponents.JAR_LID_COMPONENT, component);
            }
            ItemHandlerHelper.giveItemToPlayer(player, output);
            cir.setReturnValue(ItemInteractionResult.sidedSuccess(level.isClientSide));
            return;
        }
        cir.setReturnValue(ItemInteractionResult.FAIL);
    }
}
