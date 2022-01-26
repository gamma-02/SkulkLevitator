package gamma02.shulklevitator;

import gamma02.shulklevitator.common.blocks.RegularShulkLevitatorBlock;
import gamma02.shulklevitator.common.blocks.ShulkLevitatorBlockEntity;
import gamma02.shulklevitator.common.blocks.UpgradedShulkLevitatorBlock;
import gamma02.shulklevitator.common.misc.DispenserUtils;
import gamma02.shulklevitator.common.misc.PotionDispenserBehaviors;
import gamma02.shulklevitator.common.statusEffects.FlightStatusEffect;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.potion.Potion;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.lwjgl.system.CallbackI;

public class Shulklevitator implements ModInitializer {

    public static String modid = "shulklevitator";
    public static Block REGULAR_SHULK_LEVITATOR_BLOCK = Registry.register(Registry.BLOCK, new Identifier(modid, "regular_shulk_levitator_block"), new RegularShulkLevitatorBlock(FabricBlockSettings.copy(Blocks.SHULKER_BOX)));
    public static BlockEntityType<ShulkLevitatorBlockEntity> SHULK_LEVITATOR_TYPE;
    public static Block UPGRADED_SHULK_LEVITATOR_BLOCK = Registry.register(Registry.BLOCK, new Identifier(modid, "upgraded_shulk_levitator_block"), new UpgradedShulkLevitatorBlock(FabricBlockSettings.copy(Blocks.SHULKER_BOX)));
    public static BlockItem REGULAR_SHULK_BLOCK = Registry.register(Registry.ITEM, new Identifier(modid, "regular_shulk_levitator_item"), new BlockItem(REGULAR_SHULK_LEVITATOR_BLOCK, new FabricItemSettings().group(ItemGroup.REDSTONE)));
    public static BlockItem UPGRADED_SHULK_BLOCK = Registry.register(Registry.ITEM, new Identifier(modid, "upgraded_shulk_levitator_item"), new BlockItem(UPGRADED_SHULK_LEVITATOR_BLOCK, new FabricItemSettings().group(ItemGroup.REDSTONE)));
    public static Potion LEVITATION_POTION;
    public static Potion LONG_LEVITATION_POTION;
    public static Item SHULKER_BULLET_BOTTLE = Registry.register(Registry.ITEM, new Identifier(modid, "shulker_bullet_bottle"), new Item(new FabricItemSettings().group(ItemGroup.BREWING).maxCount(16)));
    public static StatusEffect FLIGHT;

    @Override
    public void onInitialize() {
        LEVITATION_POTION = Registry.register(Registry.POTION, new Identifier(modid, "levitation"), new Potion(new StatusEffectInstance(StatusEffects.LEVITATION, 3600)));
        LONG_LEVITATION_POTION = Registry.register(Registry.POTION, new Identifier(modid, "long_levitation"), new Potion("levitation", new StatusEffectInstance(StatusEffects.LEVITATION, 9600)));
        FLIGHT = Registry.register(Registry.STATUS_EFFECT, new Identifier(modid, "flight_effect"), new FlightStatusEffect());
        SHULK_LEVITATOR_TYPE = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(modid, "shulk_levitator_block_entity_type"), FabricBlockEntityTypeBuilder.create(ShulkLevitatorBlockEntity::new, REGULAR_SHULK_LEVITATOR_BLOCK, UPGRADED_SHULK_LEVITATOR_BLOCK).build(null));
        PotionDispenserBehaviors.registerPotionBehavior(DispenserUtils.SHULK_POTION_BEHAVIOR, LEVITATION_POTION, LONG_LEVITATION_POTION);
    }
}
