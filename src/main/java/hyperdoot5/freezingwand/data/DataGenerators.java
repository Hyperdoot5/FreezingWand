package hyperdoot5.freezingwand.data;

import hyperdoot5.freezingwand.FreezingWandMod;
import net.minecraft.DetectedVersion;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.metadata.PackMetadataGenerator;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.util.InclusiveRange;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import hyperdoot5.freezingwand.data.tags.*;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static hyperdoot5.freezingwand.FreezingWandMod.MODID;

@EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD)
public class DataGenerators {

	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {
		DataGenerator generator = event.getGenerator();
		PackOutput output = event.getGenerator().getPackOutput();
		ExistingFileHelper helper = event.getExistingFileHelper();

		//client generators
		generator.addProvider(event.includeClient(), new ItemModelGenerator(output, helper));
		generator.addProvider(event.includeClient(), new SoundGenerator(output, helper));
		generator.addProvider(event.includeClient(), new ParticleGenerator(output, helper));

		//registry-based stuff
		DatapackBuiltinEntriesProvider datapackProvider = new RegistryDataGenerator(output, event.getLookupProvider());
		CompletableFuture<HolderLookup.Provider> lookupProvider = datapackProvider.getRegistryProvider();
		generator.addProvider(event.includeServer(), datapackProvider);
		generator.addProvider(event.includeServer(), new DamageTypeTagGenerator(output, lookupProvider, helper));

		//normal tags
//        generator.addProvider(event.includeServer(), new ItemTagGenerator(output, lookupProvider, null, helper));
		generator.addProvider(event.includeServer(), new EntityTagGenerator(output, lookupProvider, helper));

		generator.addProvider(event.includeClient(), new LangGenerator(output));

		//pack.mcmeta
		generator.addProvider(true, new PackMetadataGenerator(output).add(PackMetadataSection.TYPE, new PackMetadataSection(
			Component.literal("Resources for Freezing Wand"),
			DetectedVersion.BUILT_IN.getPackVersion(PackType.SERVER_DATA))));
	}
}
