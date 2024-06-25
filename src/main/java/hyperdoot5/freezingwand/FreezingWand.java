package hyperdoot5.freezingwand;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

import org.slf4j.Logger;

import static hyperdoot5.freezingwand.init.CreativeTabs.CREATIVE_MODE_TABS;
import static hyperdoot5.freezingwand.init.ModItems.ITEMS;


// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(FreezingWand.MODID)
public class FreezingWand
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "freezingwand";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

//    // Create a Deferred Register to hold Items which will all be registered under the "freezingwand" namespace
//    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);
//    // Create a Deferred Register to hold CreativeModeTabs which will all be registered under the "freezingwand" namespace
//    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);


    // Creates a new item with the id "freezingwand:freezing_wand"
//    public static final DeferredItem<Item> FREEZING_WAND = ITEMS.registerSimpleItem(
//            "freezing_wand", new Item.Properties()
//                    .durability(100)
//                    .stacksTo(1)
//                    .component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true));


//    // Test Item registering, fingers crossed
//    public static final DeferredItem<Item> FREEZING_WAND = ITEMS.register(
//            "freezing_wand", () -> new FreezingWandItem( new Item.Properties()
//                    .durability(100)
//                    .stacksTo(1)
//                    .component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true)){
//
//    //from TFItems
////    @Override
////    public InteractionResult useOn(UseOnContext context) {
////        return context.getLevel().getBlockState(context.getClickedPos()).is(this.getBlock()) ? super.useOn(context) : InteractionResult.PASS;
////    }
//
//    @Override
//    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
//        BlockHitResult fluidHitResult = getPlayerPOVHitResult(level, player, ClipContext.Fluid.SOURCE_ONLY);
//        BlockHitResult placeBlockResult = fluidHitResult.withPosition(fluidHitResult.getBlockPos().above());
//        InteractionResult result = super.useOn(new UseOnContext(player, hand, placeBlockResult));
//        return new InteractionResultHolder<>(result, player.getItemInHand(hand));
//    }});








//    // Creates a creative tab with the id "freezingwand: freezing_wand" for the example item, that is placed after the combat tab
//    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> FREEZING_WAND_TAB = CREATIVE_MODE_TABS.register("freezingwand", () -> CreativeModeTab.builder()
//            .title(Component.translatable("Hocus Pocus")) //The language key for the title of your CreativeModeTab
//            .withTabsBefore(CreativeModeTabs.COMBAT)
//            .icon(() -> FREEZING_WAND.get().getDefaultInstance())
//            .displayItems((parameters, output) -> {
//                output.accept(FREEZING_WAND.get()); // Add the item to the tab.
//            }).build());


    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public FreezingWand(IEventBus modEventBus, ModContainer modContainer)
    {

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register the Deferred Register to the mod event bus so items get registered
        ITEMS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so tabs get registered
        CREATIVE_MODE_TABS.register(modEventBus);

        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (ExampleMod) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);


        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        // Some common setup code
        LOGGER.info("Freezing Wand Setup Initiated");

        Config.items.forEach((item) -> LOGGER.info("ITEM >> {}", item.toString()));
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
        LOGGER.info("Freezing Wand Acknowledges the Server");
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            // Some client setup code
            LOGGER.info("Freezing Wand Acknowledges the Client");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }
    }
}
