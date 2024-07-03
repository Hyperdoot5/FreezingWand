package hyperdoot5.freezingwand.data.helpers;

import hyperdoot5.freezingwand.FreezingWandMod;
import net.neoforged.neoforge.common.data.LanguageProvider;
import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import net.minecraft.ChatFormatting;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import org.apache.commons.lang3.text.WordUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public abstract class FWLangProvider extends LanguageProvider {
    private final Map<String, String> FW_TIPS = new HashMap<>();
    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private final PackOutput output;
    public final Map<String, String> upsideDownEntries = new HashMap<>();

    public FWLangProvider(PackOutput output) {
        super(output, FreezingWandMod.MODID, "en_us");
        this.output = output;
    }

    @Override
    public void add(String key, String value) {
        super.add(key, value);
        List<LangFormatSplitter.Component> splitEnglish = LangFormatSplitter.split(value);
        this.upsideDownEntries.put(key, LangConversionHelper.convertComponents(splitEnglish));
    }


    public void addAdvancement(String key, String title, String desc) {
        this.add("advancement.freezingwand." + key, title);
        this.add("advancement.freezingwand." + key + ".desc", desc);
    }

    public void addDeathMessage(String key, String name) {
        this.add("death.attack.freezingwand." + key, name);
    }

    public void addStat(String key, String name) {
        this.add("stat.freezingwand." + key, name);
    }

    public void addMessage(String key, String name) {
        this.add("misc.freezingwand." + key, name);
    }

    public void addScreenMessage(String key, String name) {
        this.add("gui.freezingwand." + key, name);
    }

    public void createTip(String key, String translation) {
        String fullKey = "freezingwand.tips." + key;
        this.add(fullKey, translation);
        FW_TIPS.put(fullKey, key);
    }

    public void translateTag(TagKey<?> tag, String name) {
        this.add(String.format("tag.%s.%s.%s", tag.registry().location().getPath(), tag.location().getNamespace(), tag.location().getPath().replace('/', '.')), name);
    }


    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        //generate normal lang file
        CompletableFuture<?> languageGen = super.run(cache);
        ImmutableList.Builder<CompletableFuture<?>> futuresBuilder = new ImmutableList.Builder<>();
        futuresBuilder.add(languageGen);

        //generate en_ud file
        JsonObject upsideDownFile = new JsonObject();
        this.upsideDownEntries.forEach(upsideDownFile::addProperty);
        futuresBuilder.add(DataProvider.saveStable(cache, upsideDownFile, this.output.getOutputFolder(PackOutput.Target.RESOURCE_PACK).resolve(FreezingWandMod.MODID).resolve("lang").resolve("en_ud.json")));

        //generate tips
        for (Map.Entry<String, String> entry : FW_TIPS.entrySet()) {
            JsonObject object = new JsonObject();

            Component tooltipText = Component.translatable(entry.getKey()).withStyle(ChatFormatting.GREEN);
            object.add("tip", ComponentSerialization.CODEC.encodeStart(JsonOps.INSTANCE, tooltipText).getOrThrow());
            futuresBuilder.add(DataProvider.saveStable(cache, GSON.toJsonTree(object), this.output.getOutputFolder().resolve("assets/freezingwand/tips/" + entry.getValue() + ".json")));
        }
        return CompletableFuture.allOf(futuresBuilder.build().toArray(CompletableFuture[]::new));
    }
}
