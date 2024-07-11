//package hyperdoot5.freezingwand.network;
//
//import hyperdoot5.freezingwand.FreezingWandMod;
//import hyperdoot5.freezingwand.config.FWConfig;
//import net.minecraft.network.FriendlyByteBuf;
//import net.minecraft.network.RegistryFriendlyByteBuf;
//import net.minecraft.network.codec.StreamCodec;
//import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
//import net.neoforged.neoforge.network.handling.IPayloadContext;
//
//import java.util.List;
//
//public record SyncAttunementPacket(
//	double uncraftingMultiplier, double repairingMultiplier,
//	boolean allowShapeless, boolean disableIngredientSwitching, boolean disabledUncrafting, boolean disabledTable,
//	List<? extends String> disabledRecipes, boolean flipRecipeList,
//	List<? extends String> disabledModids, boolean flipModidList) implements CustomPacketPayload {
//
//	public static final Type<SyncAttunementPacket> TYPE = new Type<>(FreezingWandMod.prefix("sync_attunement"));
//	public static final StreamCodec<RegistryFriendlyByteBuf, SyncAttunementPacket> STREAM_CODEC = CustomPacketPayload.codec(SyncAttunementPacket::write, SyncAttunementPacket::new);
//
//	public SyncAttunementPacket(FriendlyByteBuf buf) {
//		this(buf.readDouble(), buf.readDouble(),
//			buf.readBoolean(), buf.readBoolean(), buf.readBoolean(), buf.readBoolean(),
//			buf.readList(FriendlyByteBuf::readUtf), buf.readBoolean(),
//			buf.readList(FriendlyByteBuf::readUtf), buf.readBoolean());
//	}
//
//	public void write(FriendlyByteBuf buf) {
//		buf.writeDouble(this.uncraftingMultiplier());
//		buf.writeDouble(this.repairingMultiplier());
//		buf.writeBoolean(this.allowShapeless());
//		buf.writeBoolean(this.disableIngredientSwitching());
//		buf.writeBoolean(this.disabledUncrafting());
//		buf.writeBoolean(this.disabledTable());
//		buf.writeCollection(this.disabledRecipes(), FriendlyByteBuf::writeUtf);
//		buf.writeBoolean(this.flipRecipeList());
//		buf.writeCollection(this.disabledModids(), FriendlyByteBuf::writeUtf);
//		buf.writeBoolean(this.flipModidList());
//	}
//
//	@Override
//	public Type<? extends CustomPacketPayload> type() {
//		return TYPE;
//	}
//
//	public static void handle(SyncAttunementPacket message, IPayloadContext ctx) {
//		ctx.enqueueWork(() -> {
//			FWConfig.uncraftingXpCostMultiplier = message.uncraftingMultiplier();
//			FWConfig.repairingXpCostMultiplier = message.repairingMultiplier();
//			TFConfig.allowShapelessUncrafting = message.allowShapeless();
//			TFConfig.disableIngredientSwitching = message.disableIngredientSwitching();
//			TFConfig.disableUncraftingOnly = message.disabledUncrafting();
//			TFConfig.disableEntireTable = message.disabledTable();
//			TFConfig.disableUncraftingRecipes = message.disabledRecipes();
//			TFConfig.reverseRecipeBlacklist = message.flipRecipeList();
//			TFConfig.blacklistedUncraftingModIds = message.disabledModids();
//			TFConfig.flipUncraftingModIdList = message.flipModidList();
//		});
//	}
//}
