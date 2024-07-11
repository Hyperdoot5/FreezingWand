package hyperdoot5.freezingwand.item;

import hyperdoot5.freezingwand.enchantment.ApplyFrostedEffect;
import hyperdoot5.freezingwand.entity.projectile.IceBomb;
import hyperdoot5.freezingwand.init.*;
import hyperdoot5.freezingwand.network.ParticlePacket;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import net.neoforged.neoforge.network.PacketDistributor;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.*;

import static hyperdoot5.freezingwand.FreezingWandMod.DEBUG;
import static hyperdoot5.freezingwand.FreezingWandMod.prefix;
import static hyperdoot5.freezingwand.util.AttunementUtil.*;

public class FreezingWandItem extends Item {
	public static final String basicComponent = "basic_attunement";
	public static final String iceComponent = "ice_attunement";
	public static final String packedIceComponent = "packed_ice_attunement";
	public static final String blueIceComponent = "blue_ice_attunement";
	public static final ResourceLocation BASIC = prefix(basicComponent);
	public static final ResourceLocation ICE = prefix(iceComponent);
	public static final ResourceLocation PACKED_ICE = prefix(packedIceComponent);
	public static final ResourceLocation BLUE_ICE = prefix(blueIceComponent);

	public FreezingWandItem(Properties properties) {
		super(properties);
	}

	//    int blockSelPos = 1;
	//Item Functionality
	@Override
	public InteractionResult useOn(UseOnContext context) {
		Player player = context.getPlayer();
		if (player != null && !getComponent(player.getMainHandItem()).equals(basicComponent)) {
			// Variables for readability
			String component = getComponent(player.getMainHandItem());
			Level level = context.getLevel();
			BlockPos clickedPos = context.getClickedPos();
			Direction cardinalDirection = context.getClickedFace();
			BlockState blockClicked = level.getBlockState(clickedPos);
			Fluid cardinalFluid = level.getFluidState(clickedPos.relative(cardinalDirection)).getType();

			// Whitelists for blocks, fluids, and items
			List<Fluid> fluidList = new ArrayList<>(Arrays.asList(Fluids.FLOWING_WATER.getFlowing(), Fluids.WATER.getSource()));
			List<BlockState> blockList = new ArrayList<>(Arrays.asList(
				Blocks.ICE.defaultBlockState(), Blocks.PACKED_ICE.defaultBlockState(), Blocks.BLUE_ICE.defaultBlockState()
			));
			List<ItemStack> itemList = new ArrayList<>(Arrays.asList(
				Items.ICE.getDefaultInstance(), Items.PACKED_ICE.getDefaultInstance(), Items.BLUE_ICE.getDefaultInstance()
			));

			// vars to assist in item function logic
			boolean placedBlock = false;
			boolean replacedBlock = false;
			boolean iceCollected = false;
			// initializing vars for selected block and item
			BlockState selectedBlock = blockList.getFirst();
			ItemStack selectedItem = itemList.getFirst();


			// check if can place
			if (blockList.contains(blockClicked) || fluidList.contains(cardinalFluid)) placedBlock = true;

			// change block to place based on wand data component (attunement)
			switch (component) {
				case iceComponent -> {
					selectedBlock = blockList.getFirst();
					selectedItem = itemList.getFirst();
				}
				case packedIceComponent -> {
					selectedBlock = blockList.get(1);
					selectedItem = itemList.get(1);
				}
				case blueIceComponent -> {
					selectedBlock = blockList.get(2);
					selectedItem = itemList.getLast();
				}
			}

			// LOGICAL SERVER SIDE EVENT
			// Collect? -> Place? -> Replace?, MUST BE THIS ORDER DUE TO HOW ITS WRITTEN
			if (!level.isClientSide) {
				if (player.isShiftKeyDown() && blockClicked == selectedBlock) {
					level.setBlock(clickedPos, Blocks.AIR.defaultBlockState(), 3);
					ItemHandlerHelper.giveItemToPlayer(player, new ItemStack(selectedItem.getItem()));
					// Heal wand when collecting ice
					switch (component) {
						case iceComponent -> {
							damageWand(-1, player, context);
							player.awardStat(FWStats.ICE_COLLECTED.get());
						}
						case packedIceComponent -> {
							damageWand(-5, player, context);
							player.awardStat(FWStats.PACKED_ICE_COLLECTED.get());
						}
						case blueIceComponent -> {
							damageWand(-50, player, context);
							player.awardStat(FWStats.BLUE_ICE_COLLECTED.get());
						}
					}
					iceCollected = true;
				} else if (placedBlock) {
					switch (cardinalDirection) {
						case UP -> level.setBlock(clickedPos.relative(Direction.UP), selectedBlock, 3);
						case DOWN -> level.setBlock(clickedPos.relative(Direction.DOWN), selectedBlock, 3);
						case NORTH -> level.setBlock(clickedPos.relative(Direction.NORTH), selectedBlock, 3);
						case EAST -> level.setBlock(clickedPos.relative(Direction.EAST), selectedBlock, 3);
						case SOUTH -> level.setBlock(clickedPos.relative(Direction.SOUTH), selectedBlock, 3);
						case WEST -> level.setBlock(clickedPos.relative(Direction.WEST), selectedBlock, 3);
					}
					// Vary damage
					if (fluidList.contains(cardinalFluid)) {
						damageWand(0, player, context);
					} else if (selectedBlock == blockList.getFirst()) {
						damageWand(2, player, context);
					} else if (selectedBlock == blockList.get(1)) {
						damageWand(20, player, context);
					} else if (selectedBlock == blockList.getLast()) {
						damageWand(200, player, context);
					}
				} else if (!player.isShiftKeyDown()) {
					level.setBlock(clickedPos, selectedBlock, 3);
					// Vary damage
					if (selectedBlock == blockList.getFirst()) {
						damageWand(4, player, context);
					} else if (selectedBlock == blockList.get(1)) {
						damageWand(40, player, context);
					} else if (selectedBlock == blockList.getLast()) {
						damageWand(400, player, context);
					}
					replacedBlock = true;
				}
			}
			// PHYSICAL CLIENT SIDE
			// play relevant sound
			if (placedBlock || replacedBlock) {
				level.playSound(player, clickedPos, FWSounds.BLOCK_PLACED.get(), SoundSource.PLAYERS);
			} else if (iceCollected) {
				level.playSound(player, clickedPos, FWSounds.BLOCK_COLLECTED.get(), SoundSource.PLAYERS);
			}
			return InteractionResult.SUCCESS_NO_ITEM_USED;
		}
		return InteractionResult.PASS;
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		if (!player.isShiftKeyDown() && getComponent(player.getMainHandItem()).equals(basicComponent)) {
			if (!level.isClientSide()) {
					damageWand(20, player, hand);
				IceBomb ice = new IceBomb(FWEntities.THROWN_ICE.get(), level, player);
				ice.shootFromRotation(player, player.getXRot(), player.getYRot(), -5.0F, 1.25F, 1.0F);
				level.addFreshEntity(ice);
			}
			player.playSound(FWSounds.ICE_FIRED.get(), 0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
			player.getCooldowns().addCooldown(FWItems.FREEZING_WAND.asItem(), 20);
		}
		return new InteractionResultHolder<>(InteractionResult.SUCCESS_NO_ITEM_USED, player.getItemInHand(hand));
	}

	// If player attacks entity with wand apply frost effect and add particles each hit
	public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		if (!super.hurtEnemy(stack, target, attacker)) {
			int damageDuration = 100; // value in ticks
			int damageStrength = 5; // big freeze
			ApplyFrostedEffect.doFrozoneEffect(target, damageDuration, damageStrength, true);
			// Apply Frost Particle every time player hits entity with wand
			ParticlePacket particlePacket = new ParticlePacket();
			for (int i = 0; i < 20; i++) {
				particlePacket.queueParticle(FWParticleType.FROST.get(), false,
					target.getX() + (target.getRandom().nextGaussian() * target.getBbWidth() * 0.5),
					target.getY() + target.getBbHeight() * 0.5F + (target.getRandom().nextGaussian() * target.getBbHeight() * 0.5),
					target.getZ() + (target.getRandom().nextGaussian() * target.getBbWidth() * 0.5),
					0, 0, 0);
			}
			PacketDistributor.sendToPlayersTrackingEntity(target, particlePacket);
			damageWand(1, (Player) attacker, attacker.getUsedItemHand());
			return true;
		}
		return false;
	}

	private void damageWand(int damage, Player player, InteractionHand hand) {
		player.getItemInHand(hand).hurtAndBreak(damage, player, EquipmentSlot.MAINHAND);
	}

	private void damageWand(int damage, Player player, UseOnContext context) {
		context.getItemInHand().hurtAndBreak(damage, player, EquipmentSlot.MAINHAND);
	}

	// Item Properties
	@Override
	public boolean isEnchantable(ItemStack stack) {
		return false;
	}

	@Override
	public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
		return false;
	}

	// Cant break blocks with wand lol
	@Override
	public float getDestroySpeed(@NonNull ItemStack stack, BlockState state) {
		return 0;
	}

	// custom tooltip
	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flags) {
		super.appendHoverText(stack, context, tooltip, flags);
		String component = getComponent(stack);
		tooltip.add(Component.translatable("item.freezingwand.desc." + component).withStyle(ChatFormatting.GRAY));
		if (stack.isDamaged()) {
			tooltip.add(Component.translatable("Durability: " + (getMaxDamage(stack) - getDamage(stack)) + "/" + getMaxDamage(stack)));
		}
	}
}
