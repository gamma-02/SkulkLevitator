package gamma02.shulklevitator.mixin;

import gamma02.shulklevitator.common.misc.PotionDispenserBehaviors;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.DispenserBehavior;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.checkerframework.checker.units.qual.A;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DispenserBlock.class)
public class DispenserMixin {

    @Inject(method = "getBehaviorForItem", at = @At("HEAD"), cancellable = true)
    public void behaviorMixin(ItemStack stack, CallbackInfoReturnable<DispenserBehavior> cir){
        if( PotionUtil.getPotion(stack) != null){
            Potion pot = PotionUtil.getPotion(stack);
            if(PotionDispenserBehaviors.hasBehavior(pot)){
                cir.setReturnValue(PotionDispenserBehaviors.getBehavior(pot));
            }
        }
    }



}
