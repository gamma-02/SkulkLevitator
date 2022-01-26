package gamma02.shulklevitator.mixin;

import gamma02.shulklevitator.Shulklevitator;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.recipe.BrewingRecipeRegistry;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BrewingRecipeRegistry.class)
public abstract class BrewingMixin {

    @Inject(method = "registerDefaults", at = @At("HEAD"))
    private static void registryMixin(CallbackInfo ci){
        Shulklevitator.registerPotionRecipies();
    }

    @Redirect(method = "craft", at = @At(value = "INVOKE", target = "Lnet/minecraft/potion/PotionUtil;setPotion(Lnet/minecraft/item/ItemStack;Lnet/minecraft/potion/Potion;)Lnet/minecraft/item/ItemStack;"))
    private static ItemStack redirect(ItemStack stack, Potion potion){
        System.out.println(Registry.POTION.getId(potion));
        if(potion == Shulklevitator.LEVITATION_POTION){
            stack.getOrCreateNbt().putString(PotionUtil.POTION_KEY, "shulklevitator:levitation_potion");
            return stack;
        }else if(potion == Shulklevitator.LONG_LEVITATION_POTION){
            stack.getOrCreateNbt().putString(PotionUtil.POTION_KEY, "shulklevitator:long_levitation_potion");
        }
        return PotionUtil.setPotion(stack, potion);
    }

}
