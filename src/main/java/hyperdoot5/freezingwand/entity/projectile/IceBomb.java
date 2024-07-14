package hyperdoot5.freezingwand.entity.projectile;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Position;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import hyperdoot5.freezingwand.data.tags.BlockTagGenerator;
import hyperdoot5.freezingwand.enchantment.ApplyFrostedEffect;
import hyperdoot5.freezingwand.init.FWDamageTypes;
import hyperdoot5.freezingwand.init.FWEntities;
import hyperdoot5.freezingwand.init.FWParticleType;

import java.util.List;

public class IceBomb extends FWThrowable {
	private int zoneTimer = 100;
	private boolean hasHit;

	public IceBomb(EntityType<? extends IceBomb> type, Level level) {
		super(type, level);
	}

	public IceBomb(Level level, LivingEntity thrower) {
		super(FWEntities.THROWN_ICE.get(), level, thrower);
		this.shootFromRotation(thrower, thrower.getXRot(), thrower.getYRot(), -5F, 1F, 1F);
	}

	@Override
	protected void onHitBlock(BlockHitResult result) {
		this.setDeltaMovement(0.0D, 0.0D, 0.0D);
		this.hasHit = true;
		doTerrainEffects(2);
	}

	@Override
	protected void onHitEntity(EntityHitResult pResult) {
		if (pResult.getEntity() instanceof LivingEntity entity) inflictDamage(entity, 2);
	}

	private void doTerrainEffects(int range) {
		int ix = Mth.floor(this.xOld);
		int iy = Mth.floor(this.yOld);
		int iz = Mth.floor(this.zOld);

		for (int x = -range; x <= range; x++) {
			for (int z = -range; z <= range; z++) {
				if (Math.abs(x) == range && Math.abs(z) == range) continue;
				for (int y = -range; y <= range; y++) {
					BlockPos pos = new BlockPos(ix + x, iy + y, iz + z);
					this.doTerrainEffect(pos);
				}
			}
		}
	}

	/**
	 * Freeze water, put snow on snowable surfaces
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

		if (this.hasHit) {
			this.getDeltaMovement().multiply(0.1D, 0.1D, 0.1D);

			this.zoneTimer--;
			this.makeIceZone();

			if (!this.level().isClientSide() && this.zoneTimer <= 0) {
				this.level().levelEvent(2001, new BlockPos(this.blockPosition()), Block.getId(Blocks.ICE.defaultBlockState()));
				this.discard();
			}
		} else {
			this.makeTrail(FWParticleType.FROST.get(), 5);
		}
	}

	private void makeIceZone() {
		if (this.level().isClientSide()) {
			for (int i = 0; i < 16; i++) {
				double dx = this.getX() + (this.random.nextFloat() - this.random.nextFloat()) * 3.5F;
				double dy = this.getY() + (this.random.nextFloat() - this.random.nextFloat()) * 3.5F;
				double dz = this.getZ() + (this.random.nextFloat() - this.random.nextFloat()) * 3.5F;

				this.level().addParticle(FWParticleType.FROST.get(), dx, dy, dz, 0, 0, 0);
			}
		} else {
			if (this.zoneTimer == 98) this.doTerrainEffects(3);
			if (this.zoneTimer % 20 == 0) this.hitNearbyEntities();
		}
	}

	private void hitNearbyEntities() {
		List<LivingEntity> nearby = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(3, 2, 3));

		for (LivingEntity entity : nearby) {
			if (entity != this.getOwner()) {
				this.inflictDamage(entity, 1);
			}
		}
	}

	private void inflictDamage(LivingEntity entity, int dmgMultiplier) {
		if (!entity.getType().is(EntityTypeTags.FREEZE_IMMUNE_ENTITY_TYPES)) {
			entity.hurt(FWDamageTypes.getIndirectEntityDamageSource(this.level(), FWDamageTypes.FROZEN, this, this.getOwner()),
				(entity.getType().is(EntityTypeTags.FREEZE_HURTS_EXTRA_TYPES) ? 5.0F : 1.0F) * dmgMultiplier);
			ApplyFrostedEffect.doFrozoneEffect(entity, 100 * dmgMultiplier, 0, true);
		}
	}

	public BlockState getBlockState() {
		return Blocks.PACKED_ICE.defaultBlockState();
	}

	@Override
	protected double getDefaultGravity() {
		return this.hasHit ? 0F : 0.025F;
	}

	@Override
	protected void addAdditionalSaveData(CompoundTag pCompound) {
		pCompound.putInt("zone_timer", this.zoneTimer);
		pCompound.putBoolean("has_hit", this.hasHit);
	}

	@Override
	protected void readAdditionalSaveData(CompoundTag pCompound) {
		this.zoneTimer = pCompound.getInt("zone_timer");
		this.hasHit = pCompound.getBoolean("has_hit");
	}
}
