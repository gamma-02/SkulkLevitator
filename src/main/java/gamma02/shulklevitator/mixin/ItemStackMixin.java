package gamma02.shulklevitator.mixin;

import gamma02.shulklevitator.Shulklevitator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ShulkerBulletEntity;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@SuppressWarnings("unused")
@Mixin(Entity.class)
public abstract class ItemStackMixin {

    @Shadow public abstract World getEntityWorld();

    @Shadow public abstract int getId();

    @Shadow public abstract boolean damage(DamageSource source, float amount);

    @Inject(method = "interact", at = @At("HEAD"), cancellable = true)
    public void useMixin(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir){
        if(player.getStackInHand(hand).getItem() == Items.GLASS_BOTTLE && this.getEntityWorld().getEntityById(this.getId()) instanceof ShulkerBulletEntity){
            player.getStackInHand(hand).decrement(1);
            player.giveItemStack(Shulklevitator.SHULKER_BULLET_BOTTLE.getDefaultStack());
            this.damage(DamageSource.mob(player), 1);
            cir.setReturnValue(ActionResult.SUCCESS);
        }
    }

}
