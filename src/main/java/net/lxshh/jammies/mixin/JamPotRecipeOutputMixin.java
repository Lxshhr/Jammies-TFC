package net.lxshh.jammies.mixin;

import net.dries007.tfc.common.blockentities.IPotInventory;
import net.dries007.tfc.common.items.TFCItems;
import net.dries007.tfc.common.recipes.JamPotRecipe;
import net.dries007.tfc.util.Helpers;
import net.lxshh.jammies.common.component.ModComponents;
import net.lxshh.jammies.common.component.LidDataComponent;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = JamPotRecipe.JamOutput.class, remap = false)
public abstract class JamPotRecipeOutputMixin {

    @Shadow @Final private ItemStack sealedStack;

    @Shadow @Final private ItemStack unsealedStack;

    @Inject(method = "onInteract", at = @At("HEAD"), cancellable = true, remap = false)
    private void jammies$onInteract(IPotInventory entity, Player player, ItemStack clickedWith, CallbackInfoReturnable<ItemInteractionResult> cir)
    {
        // rework how sealed jars interact with the recipe
        if (Helpers.isItem(clickedWith, TFCItems.EMPTY_JAR_WITH_LID)) {
            // get the component before we shrink
            LidDataComponent component = clickedWith.get(ModComponents.JAR_LID_COMPONENT);

            clickedWith.shrink(1);
            unsealedStack.shrink(1);
            ItemStack result = sealedStack.copy();
            result.setCount(1);

            // transfer the component to the result item
            if (component != null) {
                result.set(ModComponents.JAR_LID_COMPONENT, component);
            }
            ItemHandlerHelper.giveItemToPlayer(player, result);
        }
        cir.setReturnValue(ItemInteractionResult.sidedSuccess(player.level().isClientSide));
    }
}
