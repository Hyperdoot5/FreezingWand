package hyperdoot5.freezingwand.data;

import net.minecraft.data.PackOutput;
import net.minecraft.sounds.SoundEvents;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import hyperdoot5.freezingwand.data.helpers.FWSoundProvider;
import hyperdoot5.freezingwand.init.FWSounds;

public class SoundGenerator extends FWSoundProvider {

	public SoundGenerator(PackOutput output, ExistingFileHelper helper) {
		super(output, helper);
	}

	@Override
	public void registerSounds() {

		this.generateExistingSoundWithSubtitle(FWSounds.WAND_ATTUNEMENT, SoundEvents.AXOLOTL_SPLASH, "Changed wand attunement");
		this.generateExistingSoundWithSubtitle(FWSounds.BLOCK_PLACED, SoundEvents.STONE_PLACE, "Ice placed");
		this.generateExistingSoundWithSubtitle(FWSounds.BLOCK_COLLECTED, SoundEvents.ITEM_PICKUP, "Ice gathered");
		this.generateExistingSoundWithSubtitle(FWSounds.ICE_FIRED, SoundEvents.ARROW_SHOOT, "Ice fired");
	}
}
