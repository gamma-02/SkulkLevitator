package gamma02.shulklevitator.common.blocks;

import gamma02.shulklevitator.Shulklevitator;
import net.fabricmc.fabric.impl.client.particle.FabricSpriteProviderImpl;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.particle.EndRodParticle;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.util.ClientPlayerTickable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import javax.swing.text.BadLocationException;
import java.util.Random;

public class ShulkLevitatorBlockEntity extends BlockEntity {
    private boolean level = false;
    public ShulkLevitatorBlockEntity(BlockPos pos, BlockState state) {
        super(Shulklevitator.SHULK_LEVITATOR_TYPE, pos, state);

    }
    public ShulkLevitatorBlockEntity(BlockPos pos, BlockState state, boolean level){
        super(Shulklevitator.SHULK_LEVITATOR_TYPE, pos, state);
        this.level = level;
    }


    public static <T extends ShulkLevitatorBlockEntity> void TICK(World world, BlockPos pos, BlockState state, T t){
        t.tick(world, pos, state, t);
    }



    public void tick(World world, BlockPos pos, BlockState state, ShulkLevitatorBlockEntity blockEntity) {
        Box effectBoundingBox;
        Vec3d corner1 = Vec3d.ZERO;
        Vec3d corner2 = Vec3d.ZERO;
        switch (state.get(RegularShulkLevitatorBlock.FACING)){
            case UP : {
                corner1 = getVec3dFromBlockPos(pos.add(4, 10, 4));
                corner2 = getVec3dFromBlockPos(pos.add(-4, 1, -4));
            }
            case DOWN : {
                corner1 = getVec3dFromBlockPos(pos.add(4, -10, 4));
                corner2 = getVec3dFromBlockPos(pos.add(-4, -1, -4));
            }case EAST : {
                corner1 = getVec3dFromBlockPos(pos.add(10, 4, 4));
                corner2 = getVec3dFromBlockPos(pos.add(1, -4, -4));
            }case WEST : {
                corner1 = getVec3dFromBlockPos(pos.add(-10, 4, 4));
                corner2 = getVec3dFromBlockPos(pos.add(-1, -4, -4));
            }case SOUTH : {
                corner1 = getVec3dFromBlockPos(pos.add(4, 4, 10));
                corner2 = getVec3dFromBlockPos(pos.add(-4, -4, 1));
            }case NORTH : {
                corner1 = getVec3dFromBlockPos(pos.add(4, 4, -10));
                corner2 = getVec3dFromBlockPos(pos.add(-4, -4, -1));
            }
        }


        if(level){
            corner1.multiply(2);
            corner2.multiply(2);
        }
        effectBoundingBox = new Box(corner1, corner2);
        if(world.isClient) {
            Random random = new Random();
            if(MathHelper.clamp(random.nextInt(), 0, 15) == 1)
            world.addParticle(ParticleTypes.END_ROD, effectBoundingBox.maxX, effectBoundingBox.maxY, effectBoundingBox.maxZ, -5, 0, 0);
            world.addParticle(ParticleTypes.END_ROD, effectBoundingBox.maxX, effectBoundingBox.maxY, effectBoundingBox.maxZ, 0, -5, 0);
            world.addParticle(ParticleTypes.END_ROD, effectBoundingBox.maxX, effectBoundingBox.maxY, effectBoundingBox.maxZ, 0, 0, -5);
            world.addParticle(ParticleTypes.END_ROD, effectBoundingBox.minX, effectBoundingBox.minY, effectBoundingBox.minZ, 5, 0, 0);
            world.addParticle(ParticleTypes.END_ROD, effectBoundingBox.minX, effectBoundingBox.minY, effectBoundingBox.minZ, 0, 5, 0);
            world.addParticle(ParticleTypes.END_ROD, effectBoundingBox.minX, effectBoundingBox.minY, effectBoundingBox.minZ, 0, 0, 5);

        }
        for(Entity entity : world.getOtherEntities(null, effectBoundingBox)){
            if(entity instanceof ServerPlayerEntity){
                ((ServerPlayerEntity) entity).addStatusEffect(new StatusEffectInstance(Shulklevitator.FLIGHT, 0));
            }
        }

    }

    public static Vec3d getVec3dFromBlockPos(BlockPos pos){
        return new Vec3d(pos.getX(), pos.getY(), pos.getZ());
    }
}
