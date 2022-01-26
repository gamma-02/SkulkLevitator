package gamma02.shulklevitator.common.misc;

import gamma02.shulklevitator.Shulklevitator;
import gamma02.shulklevitator.common.blocks.RegularShulkLevitatorBlock;
import gamma02.shulklevitator.common.blocks.ShulkLevitatorBlockEntity;
import gamma02.shulklevitator.common.blocks.UpgradedShulkLevitatorBlock;
import net.minecraft.block.Blocks;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.FallibleItemDispenserBehavior;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.PotionUtil;
import net.minecraft.util.math.BlockPointer;

public class DispenserUtils {





    public static PotionDispenserBehavior SHULK_POTION_BEHAVIOR = new PotionDispenserBehavior() {
        @Override
        public boolean isSuccess() {
            return super.isSuccess();
        }

        @Override
        protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
            if(pointer.getWorld().getBlockState(pointer.getPos()).getBlock() == Blocks.DISPENSER){
                if(pointer.getWorld().getBlockState(pointer.getPos().offset(pointer.getBlockState().get(DispenserBlock.FACING), 1)).getBlock() == Shulklevitator.REGULAR_SHULK_LEVITATOR_BLOCK || pointer.getWorld().getBlockState(pointer.getPos().offset(pointer.getBlockState().get(DispenserBlock.FACING), 1)).getBlock() == Shulklevitator.UPGRADED_SHULK_LEVITATOR_BLOCK){
                    if(pointer.getWorld().getBlockEntity(pointer.getPos().offset(pointer.getBlockState().get(DispenserBlock.FACING))) instanceof ShulkLevitatorBlockEntity entity){
                        int effTi = 0;
                        for(StatusEffectInstance instance : PotionUtil.getPotionEffects(stack)){
                            if(instance.getEffectType() == StatusEffects.LEVITATION){
                                effTi += instance.getDuration();
                            }
                        }
                        if(entity.effectTime < ( entity.level ? UpgradedShulkLevitatorBlock.MAX_CAPACITY-effTi : RegularShulkLevitatorBlock.MAX_CAPACITY-effTi)){
                            System.out.println("dispensing");
                            entity.addEffectTime(effTi);
                            stack.decrement(1);
                            this.setSuccess(true);
                        }
                    }

                }else{
                    this.setSuccess(false);
                }
            }else{
                System.out.println("java/mc is bein stoopid lmfao");
                this.setSuccess(false);
            }


            return stack;
        }

    };

}
