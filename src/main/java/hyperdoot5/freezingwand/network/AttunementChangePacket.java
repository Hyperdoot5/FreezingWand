package hyperdoot5.freezingwand.network;

import hyperdoot5.freezingwand.FreezingWandMod;
import hyperdoot5.freezingwand.init.FWItems;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import static hyperdoot5.freezingwand.util.AttunementUtil.setComponent;

public record AttunementChangePacket(String attuneID) implements CustomPacketPayload {
	public static final Type<AttunementChangePacket> TYPE = new Type<>(FreezingWandMod.prefix("attunement_change"));
	public static final StreamCodec<RegistryFriendlyByteBuf, AttunementChangePacket> STREAM_CODEC = CustomPacketPayload.codec(AttunementChangePacket::write, AttunementChangePacket::new);

	public AttunementChangePacket(FriendlyByteBuf buf) {
		this(buf.readUtf());
	}

	public void write(FriendlyByteBuf buf) {
		buf.writeUtf(this.attuneID());
	}

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}

	public static void handle(AttunementChangePacket message, IPayloadContext ctx) {
		ctx.enqueueWork(() -> {
			ItemStack stack = ctx.player().getMainHandItem();
			if (stack.is(FWItems.FREEZING_WAND)) setComponent(message.attuneID(), stack);
		});
	}
}
