package gamma02.shulklevitator.common.statusEffects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.server.network.ServerPlayerEntity;

public class FlightStatusEffect extends StatusEffect {
    private int duration;
    public FlightStatusEffect() {
        super(StatusEffectCategory.BENEFICIAL, 0xe4a6ff);
    }
    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier){
        this.duration = duration;
        return true;
    }
    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier){
        if(entity instanceof ServerPlayerEntity && this.duration > 1){
            ((ServerPlayerEntity) entity).getAbilities().allowFlying = true;
            ((ServerPlayerEntity) entity).sendAbilitiesUpdate();
        }else if(entity instanceof ServerPlayerEntity){
            ((ServerPlayerEntity) entity).getAbilities().allowFlying = false;
            ((ServerPlayerEntity) entity).sendAbilitiesUpdate();
            ((ServerPlayerEntity) entity).stopFallFlying();
        }
    }
}
