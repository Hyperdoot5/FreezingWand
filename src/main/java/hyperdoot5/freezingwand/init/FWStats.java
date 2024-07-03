package hyperdoot5.freezingwand.init;

import hyperdoot5.freezingwand.FreezingWandMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.StatFormatter;
import net.minecraft.stats.Stats;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;

import static hyperdoot5.freezingwand.FreezingWandMod.MODID;
import static hyperdoot5.freezingwand.FreezingWandMod.DEBUG;

public class FWStats {
    public static final DeferredRegister<ResourceLocation> STATS = DeferredRegister.create(Registries.CUSTOM_STAT, MODID);
    private static final List<Runnable> STAT_SETUP = new ArrayList<>();

    public static final DeferredHolder<ResourceLocation, ResourceLocation> ICE_COLLECTED = makeFWStat("ice_collected");
    public static final DeferredHolder<ResourceLocation, ResourceLocation> PACKED_ICE_COLLECTED = makeFWStat("packed_ice_collected");
    public static final DeferredHolder<ResourceLocation, ResourceLocation> BLUE_ICE_COLLECTED = makeFWStat("blue_ice_collected");


    private static DeferredHolder<ResourceLocation, ResourceLocation> makeFWStat(String key) {
        ResourceLocation resourcelocation = FreezingWandMod.prefix(key);
        STAT_SETUP.add(() -> Stats.CUSTOM.get(resourcelocation, StatFormatter.DEFAULT));
        return STATS.register(key, () -> resourcelocation);
    }

    public static void init() {
        STAT_SETUP.forEach(Runnable::run);
    }
}
