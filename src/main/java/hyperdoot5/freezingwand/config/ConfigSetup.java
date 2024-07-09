//package hyperdoot5.freezingwand.config;
//
//import net.minecraft.server.MinecraftServer;
//import net.minecraft.server.level.ServerPlayer;
//import net.neoforged.fml.ModLoadingContext;
//import net.neoforged.fml.config.ModConfig;
//import net.neoforged.fml.event.config.ModConfigEvent;
//import net.neoforged.neoforge.common.ModConfigSpec;
//import net.neoforged.neoforge.event.entity.player.PlayerEvent;
//import net.neoforged.neoforge.network.PacketDistributor;
//import net.neoforged.neoforge.server.ServerLifecycleHooks;
//import org.apache.commons.lang3.tuple.Pair;
//
//public final class ConfigSetup {
//    private static final ModConfigSpec CLIENT_SPEC;
//    private static final ModConfigSpec COMMON_SPEC;
//    static final FWClientConfig CLIENT_CONFIG;
//    static final FWCommonConfig COMMON_CONFIG;
//
//    static {
//        {
//            final Pair<FWCommonConfig, ModConfigSpec> specPair = new ModConfigSpec.Builder().configure(FWCommonConfig::new);
//            ModLoadingContext.get().getActiveContainer().registerConfig(ModConfig.Type.COMMON, COMMON_SPEC = specPair.getRight());
//            COMMON_CONFIG = specPair.getLeft();
//        }
//        {
//            final Pair<FWClientConfig, ModConfigSpec> specPair = new ModConfigSpec.Builder().configure(FWClientConfig::new);
//            ModLoadingContext.get().getActiveContainer().registerConfig(ModConfig.Type.CLIENT, CLIENT_SPEC = specPair.getRight());
//            CLIENT_CONFIG = specPair.getLeft();
//        }
//    }
//
//    public static void loadConfigs(ModConfigEvent.Loading event) {
//        if (event.getConfig().getSpec() == CLIENT_SPEC) {
//            FWConfig.rebakeClientOptions(CLIENT_CONFIG);
//        } else if (event.getConfig().getSpec() == COMMON_SPEC) {
//            FWConfig.rebakeCommonOptions(COMMON_CONFIG);
//        }
//    }
//
//    public static void reloadConfigs(ModConfigEvent.Reloading event) {
//        if (event.getConfig().getSpec() == CLIENT_SPEC) {
//            FWConfig.rebakeClientOptions(CLIENT_CONFIG);
//        } else if (event.getConfig().getSpec() == COMMON_SPEC) {
//            FWConfig.rebakeCommonOptions(COMMON_CONFIG);
//        }
//    }
//}
