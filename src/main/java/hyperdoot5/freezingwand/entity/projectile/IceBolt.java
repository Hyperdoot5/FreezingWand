package hyperdoot5.freezingwand.entity.projectile;

import hyperdoot5.freezingwand.data.tags.BlockTagGenerator;
import hyperdoot5.freezingwand.enchantment.ApplyFrostedEffect;
import hyperdoot5.freezingwand.init.FWDamageTypes;
import hyperdoot5.freezingwand.init.FWEntities;
import hyperdoot5.freezingwand.init.FWParticleType;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

import java.util.List;

public class IceBolt extends FWThrowable {

	public IceBolt(Level level, LivingEntity thrower) {
		super(FWEntities.THROWN_ICE.get(), level, thrower);
		this.shootFromRotation(thrower, thrower.getXRot(), thrower.getYRot(), 0F, 2F, 1F);
	}

	public IceBolt(Level worldIn, double x, double y, double z) {
		super(FWEntities.THROWN_ICE.get(), worldIn, x, y, z);
	}

	@Override
	protected void onHitBlock(BlockHitResult result) {
		hitNearbyEntities(2);
		this.doTerrainEffects();
	}

	@Override
	protected void onHitEntity(EntityHitResult pResult) {
		super.onHitEntity(pResult);
		if (!this.level().isClientSide()) {
			inflictDamage((LivingEntity) pResult.getEntity(), 3);
			hitNearbyEntities(1);
			this.level().broadcastEntityEvent(this, (byte) 3);
			this.discard();
		}
	}

	/**
	 * Copied from Icebomb
	 */
	private void doTerrainEffects() {
		int ix = Mth.floor(this.xOld);
		int iy = Mth.floor(this.yOld);
		int iz = Mth.floor(this.zOld);

		for (int x = -1; x <= 1; x++) {
			for (int z = -1; z <= 1; z++) {
				if (Math.abs(x) == 1 && Math.abs(z) == 1) continue;
				for (int y = -1; y <= 1; y++) {
					BlockPos pos = new BlockPos(ix + x, iy + y, iz + z);
					this.doTerrainEffect(pos);
				}
			}
		}
	}

	/**
	 * Copied from Icebomb
	 */
	private void doTerrainEffect(BlockPos pos) {
		BlockState state = this.level().getBlockState(pos);
		if (!this.level().isClientSide()) {
			if (state.is(Blocks.WATER)) {
				this.level().setBlockAndUpdate(pos, Blocks.ICE.defaultBlockState());
			}
			if (state == Blocks.LAVA.defaultBlockState()) {
				this.level().setBlockAndUpdate(pos, Blocks.OBSIDIAN.defaultBlockState());
			}
			if (this.level().isEmptyBlock(pos) && Blocks.SNOW.defaultBlockState().canSurvive(this.level(), pos)) {
				this.level().setBlockAndUpdate(pos, Blocks.SNOW.defaultBlockState());
			}
			if (state.is(BlockTagGenerator.ICE_BOMB_REPLACEABLES)) {
				this.level().setBlock(pos, Blocks.SNOW.defaultBlockState().canSurvive(this.level(), pos) ? Blocks.SNOW.defaultBlockState() : Blocks.AIR.defaultBlockState(), 3);
			}
			if (state.is(Blocks.SNOW) && state.getValue(SnowLayerBlock.LAYERS) < 8) {
				this.level().setBlockAndUpdate(pos, state.setValue(SnowLayerBlock.LAYERS, state.getValue(SnowLayerBlock.LAYERS) + 1));
			}
		}
	}

	@Override
	public void tick() {
		super.tick();
		this.makeTrail(FWParticleType.FROST.get(),
			this.random.nextGaussian() * 0.05,
			-this.getDeltaMovement().y * 0.5,
			this.random.nextGaussian() * 0.05,
			5);
	}

	@Override
	public void handleEntityEvent(byte id) {
		if (id == 3) {
			for (int i = 0; i < 8; i++) {
				this.level().addParticle(FWParticleType.FROST.get(), false, this.getX(), this.getY(), this.getZ(),
					this.random.nextGaussian() * 0.05D,
					random.nextDouble() * 0.2D,
					random.nextGaussian() * 0.05D);
			}
		} else {
			super.handleEntityEvent(id);
		}
	}

	private void hitNearbyEntities(int dmgMultiplier) {
		List<LivingEntity> nearby = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(3, 2, 3));

		for (LivingEntity entity : nearby) {
			if (entity != this.getOwner()) {
				this.inflictDamage(entity, dmgMultiplier);
			}
		}
	}

	private void inflictDamage(LivingEntity entity, int dmgMultiplier) {
		if (!entity.getType().is(EntityTypeTags.FREEZE_IMMUNE_ENTITY_TYPES)) {
			entity.hurt(FWDamageTypes.getIndirectEntityDamageSource(this.level(), FWDamageTypes.FROZEN, this, this.getOwner()),
				(entity.getType().is(EntityTypeTags.FREEZE_HURTS_EXTRA_TYPES) ? 5.0F : 1.0F) * dmgMultiplier);
			ApplyFrostedEffect.doFrozoneEffect(entity, 100 * dmgMultiplier, 2, true);
		}
	}

	@Override
	protected double getDefaultGravity() {
		return 0F;
	}

//	@Override
//	protected void addAdditionalSaveData(CompoundTag pCompound) {
////		pCompound.putInt("zone_timer", this.zoneTimer);
//		pCompound.putBoolean("has_hit", this.hasHit);
//	}

//	@Override
//	protected void readAdditionalSaveData(CompoundTag pCompound) {
////		this.zoneTimer = pCompound.getInt("zone_timer");
//		this.hasHit = pCompound.getBoolean("has_hit");
//	}
}
