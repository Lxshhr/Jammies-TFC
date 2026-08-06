package net.lxshh.jammies.network;

import net.lxshh.jammies.Jammies;
import net.lxshh.jammies.common.data.LidProperties;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public record ModDataManagerSyncPacket(Map<ResourceLocation, LidProperties> values) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<ModDataManagerSyncPacket> TYPE =
            new Type<>(Jammies.identifier("data_manager_sync"));

    public static final StreamCodec<RegistryFriendlyByteBuf, ModDataManagerSyncPacket> CODEC =
            ByteBufCodecs.<RegistryFriendlyByteBuf, ResourceLocation, LidProperties, Map<ResourceLocation, LidProperties>>map(
                            HashMap::new,
                            ResourceLocation.STREAM_CODEC,
                            LidProperties.STREAM_CODEC
                    )
                    .map(ModDataManagerSyncPacket::new, ModDataManagerSyncPacket::values);

    public ModDataManagerSyncPacket() {
        this(LidProperties.MANAGER.getElements());
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void handle(boolean isMemoryConnection) {
        if (isMemoryConnection) {
            Jammies.LOGGER.debug("Ignoring Lid Properties DataManager sync on logical server");
            return;
        }
        LidProperties.MANAGER.bindValues(values);
    }
}
