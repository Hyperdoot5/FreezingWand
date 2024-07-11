package hyperdoot5.freezingwand.events;

import hyperdoot5.freezingwand.init.FWSounds;
import hyperdoot5.freezingwand.item.FreezingWandItem;
import hyperdoot5.freezingwand.network.AttunementChangePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import static hyperdoot5.freezingwand.FreezingWandMod.MODID;
import static hyperdoot5.freezingwand.events.FWKeyMapHandler.*;
import static hyperdoot5.freezingwand.util.AttunementUtil.*;
import static hyperdoot5.freezingwand.item.FreezingWandItem.basicComponent;
import static hyperdoot5.freezingwand.item.FreezingWandItem.iceComponent;
import static hyperdoot5.freezingwand.item.FreezingWandItem.packedIceComponent;
import static hyperdoot5.freezingwand.item.FreezingWandItem.blueIceComponent;

@EventBusSubscriber(modid = MODID, value = Dist.CLIENT)
public class FWKeyEvents {
	@SubscribeEvent
	private static void Attunement(ClientTickEvent.Post event) {
		Minecraft mc = Minecraft.getInstance();
		Player player = mc.player;
		while (ATTUNEMENT_KEYMAP.get().consumeClick()) {
			if (mc.level != null && player != null && player.getMainHandItem().getItem() instanceof FreezingWandItem) {
				cycleAttunement(player.getMainHandItem());
				mc.level.playSound(player, new BlockPos((int) player.getX(), (int) player.getY(), (int) player.getZ()), FWSounds.WAND_ATTUNEMENT.get(), SoundSource.PLAYERS);
			} else if (mc.level != null && player != null) {
				player.sendSystemMessage(Component.translatable("The wand must be in your main hand to change the attunement!"));
			}
		}
	}

	@SubscribeEvent
	private static void basicOverride(ClientTickEvent.Post event) {
		Minecraft mc = Minecraft.getInstance();
		Player player = mc.player;
		while (BASIC_OVERRIDE_KEYMAP.get().consumeClick()) {
			if (mc.level != null && player != null && player.getMainHandItem().getItem() instanceof FreezingWandItem) {
				setComponent(basicComponent, player.getMainHandItem());
				PacketDistributor.sendToServer(new AttunementChangePacket(basicComponent));
				mc.level.playSound(player, new BlockPos((int) player.getX(), (int) player.getY(), (int) player.getZ()), FWSounds.WAND_ATTUNEMENT.get(), SoundSource.PLAYERS);
			} else if (mc.level != null && player != null) {
				player.sendSystemMessage(Component.translatable("The wand must be in your main hand to change the attunement!"));
			}
		}
	}

	@SubscribeEvent
	private static void iceOverride(ClientTickEvent.Post event) {
		Minecraft mc = Minecraft.getInstance();
		Player player = mc.player;
		while (ICE_OVERRIDE_KEYMAP.get().consumeClick()) {
			if (mc.level != null && player != null && player.getMainHandItem().getItem() instanceof FreezingWandItem) {
				setComponent(iceComponent, player.getMainHandItem());
				PacketDistributor.sendToServer(new AttunementChangePacket(iceComponent));
				mc.level.playSound(player, new BlockPos((int) player.getX(), (int) player.getY(), (int) player.getZ()), FWSounds.WAND_ATTUNEMENT.get(), SoundSource.PLAYERS);
			} else if (mc.level != null && player != null) {
				player.sendSystemMessage(Component.translatable("The wand must be in your main hand to change the attunement!"));
			}
		}
	}

	@SubscribeEvent
	private static void packedIceOverride(ClientTickEvent.Post event) {
		Minecraft mc = Minecraft.getInstance();
		Player player = mc.player;
		while (PACKED_ICE_OVERRIDE_KEYMAP.get().consumeClick()) {
			if (mc.level != null && player != null && player.getMainHandItem().getItem() instanceof FreezingWandItem) {
				setComponent(packedIceComponent, player.getMainHandItem());
				PacketDistributor.sendToServer(new AttunementChangePacket(packedIceComponent));
				mc.level.playSound(player, new BlockPos((int) player.getX(), (int) player.getY(), (int) player.getZ()), FWSounds.WAND_ATTUNEMENT.get(), SoundSource.PLAYERS);
			} else if (mc.level != null && player != null) {
				player.sendSystemMessage(Component.translatable("The wand must be in your main hand to change the attunement!"));
			}
		}
	}

	@SubscribeEvent
	private static void blueIceOverride(ClientTickEvent.Post event) {
		Minecraft mc = Minecraft.getInstance();
		Player player = mc.player;
		while (BLUE_ICE_OVERRIDE_KEYMAP.get().consumeClick()) {
			if (mc.level != null && player != null && player.getMainHandItem().getItem() instanceof FreezingWandItem) {
				setComponent(blueIceComponent, player.getMainHandItem());
				PacketDistributor.sendToServer(new AttunementChangePacket(blueIceComponent));
				mc.level.playSound(player, new BlockPos((int) player.getX(), (int) player.getY(), (int) player.getZ()), FWSounds.WAND_ATTUNEMENT.get(), SoundSource.PLAYERS);
			} else if (mc.level != null && player != null) {
				player.sendSystemMessage(Component.translatable("The wand must be in your main hand to change the attunement!"));
			}
		}
	}
}
