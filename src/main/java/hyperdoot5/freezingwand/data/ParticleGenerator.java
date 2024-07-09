package hyperdoot5.freezingwand.data;

import hyperdoot5.freezingwand.FreezingWandMod;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.ParticleDescriptionProvider;
import hyperdoot5.freezingwand.init.FWParticleType;

import java.util.Iterator;

public class ParticleGenerator extends ParticleDescriptionProvider {

	public ParticleGenerator(PackOutput output, ExistingFileHelper helper) {
		super(output, helper);
	}

	@Override
	protected void addDescriptions() {
		this.spriteSet(FWParticleType.FROST.get(), FreezingWandMod.prefix("snow"), 4, false);
	}
}
