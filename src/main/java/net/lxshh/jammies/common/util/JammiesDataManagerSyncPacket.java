package net.lxshh.jammies.common.util;

import net.lxshh.jammies.Jammies;
import net.lxshh.jammies.common.util.data.LidProperties;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public record JammiesDataManagerSyncPacket(Map<ResourceLocation, LidProperties> values) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<JammiesDataManagerSyncPacket> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(Jammies.MOD_ID, "data_manager_sync"));

    public static final StreamCodec<RegistryFriendlyByteBuf, JammiesDataManagerSyncPacket> CODEC =
            ByteBufCodecs.<RegistryFriendlyByteBuf, ResourceLocation, LidProperties, Map<ResourceLocation, LidProperties>>map(
                            HashMap::new,
                            ResourceLocation.STREAM_CODEC,
                            LidProperties.STREAM_CODEC
                    )
                    .map(JammiesDataManagerSyncPacket::new, JammiesDataManagerSyncPacket::values);

    public JammiesDataManagerSyncPacket() {
        this(LidProperties.MANAGER.getElements());
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void handle(boolean isMemoryConnection) {
        if (isMemoryConnection) {
            Jammies.LOGGER.info("Ignoring Lid Properties DataManager sync on logical server");
            return;
        }
        LidProperties.MANAGER.bindValues(values);
    }
}
