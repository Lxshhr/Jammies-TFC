package net.lxshh.jammies.integration.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IVanillaCategoryExtensionRegistration;
import net.lxshh.jammies.Jammies;
import net.minecraft.resources.ResourceLocation;

@JeiPlugin
public class JeiIntegration implements IModPlugin {

    @Override
    public ResourceLocation getPluginUid() {
        return Jammies.loc("jei");
    }

    @Override
    public void registerVanillaCategoryExtensions(IVanillaCategoryExtensionRegistration registry) {
        JammiesCraftingExtension.register(registry);
    }
}
