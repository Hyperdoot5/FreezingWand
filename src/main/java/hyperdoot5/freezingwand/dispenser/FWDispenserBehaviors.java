package hyperdoot5.freezingwand.dispenser;

import hyperdoot5.freezingwand.entity.projectile.IceBolt;
import hyperdoot5.freezingwand.init.FWItems;
import hyperdoot5.freezingwand.init.FWSounds;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;

public class FWDispenserBehaviors {
	public static void init() {
		DispenserBlock.registerBehavior(FWItems.FREEZING_WAND.get(), new DamageableStackDispenseBehavior() {
			@Override
			protected Projectile getProjectileEntity(Level level, Position position, ItemStack stack) {
				return new IceBolt(level, position.x(), position.y(), position.z());
			}

			@Override
			protected int getDamageAmount() {
				return 1;
			}

			@Override
			protected SoundEvent getFiredSound() {
				return FWSounds.BOLT_FIRED.get();
			}

			@Override
			protected float getProjectileInaccuracy() {
				return 6.0F;
			}
		});
	}
}

