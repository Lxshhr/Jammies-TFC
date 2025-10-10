package net.lxshh.jammies.mixin;

import net.dries007.tfc.common.blockentities.PotBlockEntity;
import net.dries007.tfc.common.items.TFCItems;
import net.dries007.tfc.common.recipes.JamPotRecipe;
import net.dries007.tfc.util.Helpers;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = JamPotRecipe.JamOutput.class, remap = false)
public class JamPotRecipeOutputMixin {

    @Inject(method = "onInteract", at = @At("HEAD"), cancellable = true, remap = false)
    private void onInteract(PotBlockEntity entity, Player player, ItemStack clickedWith, CallbackInfoReturnable<ItemInteractionResult> cir)
    {
        // ignore sealed jars
        if (Helpers.isItem(clickedWith, TFCItems.EMPTY_JAR_WITH_LID)) {
            cir.setReturnValue(ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION);
        }
    }
}
