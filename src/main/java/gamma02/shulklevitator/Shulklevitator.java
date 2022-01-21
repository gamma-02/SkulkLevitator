package gamma02.shulklevitator;

import gamma02.shulklevitator.common.blocks.RegularShulkLevitatorBlock;
import gamma02.shulklevitator.common.blocks.ShulkLevitatorBlockEntity;
import gamma02.shulklevitator.common.statusEffects.FlightStatusEffect;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.potion.Potion;
import net.minecraft.recipe.BrewingRecipeRegistry;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Shulklevitator implements ModInitializer {

    public static String modid = "shulklevitator";
    public static Block REGULAR_SHULK_LEVITATOR_BLOCK = Registry.register(Registry.BLOCK, new Identifier(modid, "shulk_levitator_block"), new RegularShulkLevitatorBlock(FabricBlockSettings.copy(Blocks.GRASS_BLOCK)));
    public static BlockEntityType<ShulkLevitatorBlockEntity> SHULK_LEVITATOR_TYPE;
    public static Potion LEVITATION_POTION;
    public static Potion LONG_LEVITATION_POTION;
    public static StatusEffect FLIGHT;

    @Override
    public void onInitialize() {
        LEVITATION_POTION = Registry.register(Registry.POTION, new Identifier(modid, "levitation"), new Potion(new StatusEffectInstance(StatusEffects.LEVITATION, 3600)));
        LONG_LEVITATION_POTION = Registry.register(Registry.POTION, new Identifier(modid, "long_levitation"), new Potion(new StatusEffectInstance(StatusEffects.LEVITATION, 9600)));
        FLIGHT = Registry.register(Registry.STATUS_EFFECT, new Identifier(modid, "flight_effect"), new FlightStatusEffect());
        SHULK_LEVITATOR_TYPE = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(modid, "shulk_levitator_block_entity_type"), FabricBlockEntityTypeBuilder.create(ShulkLevitatorBlockEntity::new, REGULAR_SHULK_LEVITATOR_BLOCK).build(null));
    }
}
