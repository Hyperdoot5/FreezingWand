package hyperdoot5.freezingwand.events;

import hyperdoot5.freezingwand.init.FWSounds;
import hyperdoot5.freezingwand.item.FreezingWandItem;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;

import static hyperdoot5.freezingwand.FreezingWandMod.MODID;
import static hyperdoot5.freezingwand.events.FWKeyMapHandler.*;
import static hyperdoot5.freezingwand.util.AttunementUtil.cycleAttunement;
import static hyperdoot5.freezingwand.util.AttunementUtil.setAttunement;

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
				setAttunement(0, player.getMainHandItem());
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
				setAttunement(1, player.getMainHandItem());
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
				setAttunement(2, player.getMainHandItem());
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
				setAttunement(3, player.getMainHandItem());
				mc.level.playSound(player, new BlockPos((int) player.getX(), (int) player.getY(), (int) player.getZ()), FWSounds.WAND_ATTUNEMENT.get(), SoundSource.PLAYERS);
			} else if (mc.level != null && player != null) {
				player.sendSystemMessage(Component.translatable("The wand must be in your main hand to change the attunement!"));
			}
		}
	}
}
