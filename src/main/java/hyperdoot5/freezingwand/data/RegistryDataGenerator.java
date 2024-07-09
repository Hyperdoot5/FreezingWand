package hyperdoot5.freezingwand.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import hyperdoot5.freezingwand.FreezingWandMod;
import hyperdoot5.freezingwand.init.*;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class RegistryDataGenerator extends DatapackBuiltinEntriesProvider {

	public RegistryDataGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> provider) {
		super(output, provider, BUILDER, Set.of("minecraft", FreezingWandMod.MODID));
	}

	public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
		.add(Registries.DAMAGE_TYPE, FWDamageTypes::bootstrap);
}