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

import java.util.List;
import java.util.Optional;

import static hyperdoot5.freezingwand.FreezingWandMod.DEBUG;
import static hyperdoot5.freezingwand.FreezingWandMod.prefix;
import static hyperdoot5.freezingwand.util.AttunementUtil.*;

public class FreezingWandItem extends Item {
	public static final ResourceLocation BASIC = prefix("basic_attunement");
	public static final ResourceLocation ICE = prefix("ice_attunement");
	public static final ResourceLocation PACKED_ICE = prefix("packed_ice_attunement");
	public static final ResourceLocation BLUE_ICE = prefix("blue_ice_attunement");

	public FreezingWandItem(Properties properties) {
		super(properties);
	}

	//    int blockSelPos = 1;
	//Item Functionality
	@Override
	public InteractionResult useOn(UseOnContext context) {
		if (getAttunement() != 0) {
			// Variables for readability
			Player player = context.getPlayer();
			Level level = context.getLevel();
			Fluid water_flowing = Fluids.FLOWING_WATER.getFlowing();
			Fluid water_source = Fluids.WATER.getSource();
			BlockState ice_block = Blocks.ICE.defaultBlockState();
			BlockState packed_ice = Blocks.PACKED_ICE.defaultBlockState();
			BlockState blue_ice = Blocks.BLUE_ICE.defaultBlockState();
			BlockState snow_block = Blocks.SNOW_BLOCK.defaultBlockState();
			ItemStack ice_item = Items.ICE.getDefaultInstance();
			ItemStack packed_ice_item = Items.PACKED_ICE.getDefaultInstance();
			ItemStack blue_ice_item = Items.BLUE_ICE.getDefaultInstance();
			BlockPos clickedPos = context.getClickedPos();
			Direction cardinalDirection = context.getClickedFace();
			BlockState blockClicked = level.getBlockState(clickedPos);
			Fluid cardinalFluid = level.getFluidState(clickedPos.relative(cardinalDirection)).getType();

			//vars for selecting what ice block to place and collect
			BlockState selectedBlock = Blocks.ICE.defaultBlockState();
			ItemStack selectedItem = Items.ICE.getDefaultInstance();
			;
			boolean placedBlock = false;
			boolean replacedBlock = false;
			boolean iceCollected = false;


			// check if can place
			if (blockClicked == ice_block
				|| blockClicked == packed_ice
				|| blockClicked == blue_ice
				|| blockClicked == snow_block
				|| cardinalFluid == water_source
				|| cardinalFluid == water_flowing) {
				placedBlock = true;
			}
//        if (blockSelPos == 4) blockSelPos = 1;
			// change selected block

			switch (getAttunement()) {
				case 1 -> {
					selectedBlock = ice_block;
					selectedItem = ice_item;
				}
				case 2 -> {
					selectedBlock = packed_ice;
					selectedItem = packed_ice_item;
				}
				case 3 -> {
					selectedBlock = blue_ice;
					selectedItem = blue_ice_item;
				}
			}

			// LOGICAL SERVER SIDE EVENT
			//if try to place on a valid block, place selectedblock
			if (!level.isClientSide && player != null) {
				if (player.isShiftKeyDown() && blockClicked == selectedBlock) {
					level.setBlock(clickedPos, Blocks.AIR.defaultBlockState(), 3);
					ItemHandlerHelper.giveItemToPlayer(player, new ItemStack(selectedItem.getItem()));
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
					// if player is holding shift when rclick, replace the block they click
				} else if (!player.isShiftKeyDown()) {
					level.setBlock(clickedPos, selectedBlock, 3);
					replacedBlock = true;
				}
			}
			// PHYSICAL CLIENT SIDE EVENT
			//damage wand & play block place sound
			if (player != null) {
				if (placedBlock) {

					// Varywand damage if not building directly from water source or flowing water
					if ((cardinalFluid == water_source) || (cardinalFluid == water_flowing)) {
						damageWand(0, player, context);
					} else if (blockClicked != snow_block) {
						damageWand(2, player, context);
					} else {
						damageWand(5, player, context);
					}
					level.playSound(player, clickedPos, FWSounds.BLOCK_PLACED.get(), SoundSource.PLAYERS);
					return InteractionResult.SUCCESS;

				} else if (replacedBlock) {

					damageWand(10, player, context);
					level.playSound(player, clickedPos, FWSounds.BLOCK_PLACED.get(), SoundSource.PLAYERS);
					return InteractionResult.SUCCESS;

				} else if (iceCollected) {

					switch (getAttunement()) {
						case 1 -> {
							damageWand(1, player, context);
							player.awardStat(FWStats.ICE_COLLECTED.get());
						}
						case 2 -> {
							damageWand(10, player, context);
							player.awardStat(FWStats.PACKED_ICE_COLLECTED.get());
						}
						case 3 -> {
							damageWand(100, player, context);
							player.awardStat(FWStats.BLUE_ICE_COLLECTED.get());
						}
					}
					level.playSound(player, clickedPos, FWSounds.BLOCK_COLLECTED.get(), SoundSource.PLAYERS);
					return InteractionResult.SUCCESS;
				}
			}
		}
		return InteractionResult.PASS;
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		if (!player.isShiftKeyDown() && getAttunement() == 0) {
			DEBUG.info("BOMB");
			if (!level.isClientSide()) {
				if (!player.getAbilities().instabuild) {
					damageWand(20, player, hand);
				}
//				DEBUG.info("IceBomb");
				IceBomb ice = new IceBomb(FWEntities.THROWN_ICE.get(), level, player);
				ice.shootFromRotation(player, player.getXRot(), player.getYRot(), -5.0F, 1.25F, 1.0F);
				level.addFreshEntity(ice);
			}
			player.playSound(FWSounds.ICE_FIRED.get(), 0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
			player.getCooldowns().addCooldown(FWItems.FREEZING_WAND.asItem(), 20);
		}
		return new InteractionResultHolder<>(InteractionResult.SUCCESS, player.getItemInHand(hand));
	}

	//Dirty work around when mod loads to handle instances of null component
	//also fixes component null after item use
	@Override
	public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pItemSlot, boolean pIsSelected) {
		String component = pStack.get(FWDataComponents.WAND_ATTUNEMENT);
		//if component != correct attunement component, then correct it
		if (component != null) {
			if (!isCorrectComponent(component)) {
				setComponent(pStack);
			}
		}
		if (component == null) {
			setAttunement(getAttunement(), pStack);
		}
	}


	// If player attacks entity with wand, methods similar to IceBomb
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
		if (component != null) { // used for instances of null description (crafting and anvil repair, before item is in player inv)
			tooltip.add(Component.translatable("item.freezingwand.desc." + component).withStyle(ChatFormatting.GRAY));
		}
	}
}
