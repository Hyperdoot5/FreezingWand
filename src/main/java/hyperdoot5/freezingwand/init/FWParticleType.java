package hyperdoot5.freezingwand.init;

import com.mojang.serialization.MapCodec;
import hyperdoot5.freezingwand.FreezingWandMod;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class FWParticleType {

	public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(Registries.PARTICLE_TYPE, FreezingWandMod.MODID);

	public static final DeferredHolder<ParticleType<?>, SimpleParticleType> FROST = PARTICLE_TYPES.register("frost", () -> new SimpleParticleType(false));
}
