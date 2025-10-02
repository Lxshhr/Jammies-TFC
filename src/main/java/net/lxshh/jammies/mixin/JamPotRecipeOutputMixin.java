package net.lxshh.jammies.mixin;

import net.dries007.tfc.common.blockentities.PotBlockEntity;
import net.dries007.tfc.common.items.TFCItems;
import net.dries007.tfc.common.recipes.JamPotRecipe;
import net.dries007.tfc.util.Helpers;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = JamPotRecipe.JamOutput.class, remap = false)
public class JamPotRecipeOutputMixin {

    @Shadow @Final private ItemStack sealedStack;
    @Shadow @Final private ItemStack unsealedStack;

    @Inject(method = "onInteract", at = @At("HEAD"), cancellable = true, remap = false)
    private void onInteract(PotBlockEntity entity, Player player, ItemStack clickedWith, CallbackInfoReturnable<ItemInteractionResult> cir)
    {
        // ignore sealed jars
        if (Helpers.isItem(clickedWith, TFCItems.EMPTY_JAR_WITH_LID)) {
            cir.setReturnValue(ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION);
        }
//        if (Helpers.isItem(clickedWith, TFCItems.EMPTY_JAR_WITH_LID) && !sealedStack.isEmpty())
//        {
//            // take the player's empty jar
//            clickedWith.shrink(1);
//            unsealedStack.shrink(1);
//
//            // get the result
//            ItemStack result = sealedStack.split(1);
//
//            // check if the jar item has any custom data
//            if (clickedWith.has(DataComponents.CUSTOM_DATA)) {
//                System.out.println("Lid has custom data");
//                CompoundTag jarData = clickedWith.get(DataComponents.CUSTOM_DATA).copyTag();
//                System.out.println(jarData);
//                if (jarData.contains("lidType")) {
//                    // attach the custom data to the output
//                    CompoundTag resultJarData = new CompoundTag();
//                    resultJarData.putString("lidType", jarData.getString("lidType"));
//                    result.set(DataComponents.CUSTOM_DATA, CustomData.of(resultJarData));
//                }
//            }
//
//            ItemHandlerHelper.giveItemToPlayer(player, result);
//            cir.setReturnValue(ItemInteractionResult.sidedSuccess(player.level().isClientSide));
//        }
    }
}
