package nuclearscience.datagen.server.radiation;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;
import nuclearscience.NuclearScience;
import nuclearscience.common.tags.NuclearScienceTags;
import voltaic.api.radiation.util.RadioactiveObject;

import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

public class NuclearScienceRadioactiveFluidsProvider implements DataProvider {
    private final PackOutput output;
    private final String modID;
    private final String loc;

    public NuclearScienceRadioactiveFluidsProvider(PackOutput output) {
        this.output = output;
        this.modID = NuclearScience.ID;
        this.loc = "data/voltaic/radiation/" + modID + "_radioactive_fluids";
    }

    public CompletableFuture<?> run(CachedOutput cache) {
        JsonObject json = new JsonObject();
        this.getRadioactiveItems(json);
        Path parent = this.output.getOutputFolder().resolve(this.loc + ".json");
        return CompletableFuture.allOf(DataProvider.saveStable(cache, json, parent));
    }

    public void getRadioactiveItems(JsonObject json) {
        addTag(NuclearScienceTags.Fluids.URANIUM_HEXAFLUORIDE, 1, 1, json);
    }

    public void addFluid(Fluid fluid, double radiationAmount, double radiationStrength, JsonObject json) {
        JsonObject data = new JsonObject();
        json.add(BuiltInRegistries.FLUID.getKey(fluid).toString(), (JsonElement) RadioactiveObject.CODEC.encode(new RadioactiveObject(radiationStrength, radiationAmount), JsonOps.INSTANCE, data).getOrThrow());
    }

    public void addTag(TagKey<Fluid> tag, double radiationAmount, double radiationStrength, JsonObject json) {
        JsonObject data = new JsonObject();
        json.add("#" + tag.location().toString(), (JsonElement) RadioactiveObject.CODEC.encode(new RadioactiveObject(radiationStrength, radiationAmount), JsonOps.INSTANCE, data).getOrThrow());
    }

    public String getName() {
        return this.modID + " Radioactive Fluids Provider";
    }
}
