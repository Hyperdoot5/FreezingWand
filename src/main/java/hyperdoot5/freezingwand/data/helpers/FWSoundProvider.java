package hyperdoot5.freezingwand.data.helpers;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.SoundDefinition;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.Nullable;
import hyperdoot5.freezingwand.FreezingWandMod;
import hyperdoot5.freezingwand.data.LangGenerator;

public abstract class FWSoundProvider extends SoundDefinitionsProvider {

	protected FWSoundProvider(PackOutput output, ExistingFileHelper helper) {
		super(output, FreezingWandMod.MODID, helper);
	}

	public void generateExistingSoundWithSubtitle(DeferredHolder<SoundEvent, SoundEvent> event, SoundEvent referencedSound, String subtitle) {
		this.generateExistingSoundWithSubtitle(event, referencedSound, subtitle, 1.0F, 1.0F);
	}

	public void generateExistingSoundWithSubtitle(DeferredHolder<SoundEvent, SoundEvent> event, SoundEvent referencedSound, String subtitle, float volume, float pitch) {
		this.generateExistingSound(event, referencedSound, subtitle, volume, pitch);
	}

	public void generateExistingSound(DeferredHolder<SoundEvent, SoundEvent> event, SoundEvent referencedSound, @Nullable String subtitle, float volume, float pitch) {
		SoundDefinition definition = SoundDefinition.definition();
		if (subtitle != null) {
			this.createSubtitleAndLangEntry(event, definition, subtitle);
		}
		this.add(event, definition
			.with(SoundDefinition.Sound.sound(referencedSound.getLocation(), SoundDefinition.SoundType.EVENT).volume(volume).pitch(pitch)));
	}

	private void createSubtitleAndLangEntry(DeferredHolder<SoundEvent, SoundEvent> event, SoundDefinition definition, String subtitle) {
		String[] splitSoundName = event.getId().getPath().split("\\.", 3);
		String subtitleKey = "subtitles.freezingwand." + splitSoundName[0] + "." + splitSoundName[2];
		definition.subtitle(subtitleKey);
		LangGenerator.SUBTITLE_GENERATOR.put(subtitleKey, subtitle);
	}
}
