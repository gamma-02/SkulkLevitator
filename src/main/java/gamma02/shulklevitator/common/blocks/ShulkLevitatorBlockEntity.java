package gamma02.shulklevitator.common.blocks;

import gamma02.shulklevitator.Shulklevitator;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.*;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.World;
import org.lwjgl.system.CallbackI;

import java.util.ArrayList;
import java.util.Random;

public class ShulkLevitatorBlockEntity extends BlockEntity {
    private boolean level = false;
    private Box effectBoundingBox;
    public int effectTime;
    private ArrayList<Vec3d> particleLocations = new ArrayList<>();
    private ArrayList<ServerPlayerEntity> oldPlayers = new ArrayList<>();



    public ShulkLevitatorBlockEntity(BlockPos pos, BlockState state) {
        super(Shulklevitator.SHULK_LEVITATOR_TYPE, pos, state);
        this.effectTime = 0;
        this.level = level;
        Box thonk;
        Vec3d corner1 = Vec3d.ZERO;
        Vec3d corner2 = Vec3d.ZERO;
        Direction direction = null;
        if (state.getBlock() instanceof RegularShulkLevitatorBlock) {
            direction = state.get(Properties.FACING);
        }
        if (direction != null) {
            if (direction == Direction.UP) {
                corner1 = getVec3dFromBlockPos(pos.add(4, 10, 4));
                corner2 = getVec3dFromBlockPos(pos.add(-4, 1, -4));
            } else if (direction == Direction.DOWN) {
                corner1 = getVec3dFromBlockPos(pos.add(4, -10, 4));
                corner2 = getVec3dFromBlockPos(pos.add(-4, -1, -4));
            } else if (direction == Direction.EAST) {
                corner1 = getVec3dFromBlockPos(pos.add(10, 4, 4));
                corner2 = getVec3dFromBlockPos(pos.add(1, -4, -4));
            } else if (direction == Direction.WEST) {
                corner1 = getVec3dFromBlockPos(pos.add(-10, 4, 4));
                corner2 = getVec3dFromBlockPos(pos.add(-1, -4, -4));
            } else if (direction == Direction.SOUTH) {
                corner1 = getVec3dFromBlockPos(pos.add(4, 4, 10));
                corner2 = getVec3dFromBlockPos(pos.add(-4, -4, 1));
            } else if (direction == Direction.NORTH) {
                corner1 = getVec3dFromBlockPos(pos.add(4, 4, -10));
                corner2 = getVec3dFromBlockPos(pos.add(-4, -4, -1));
            }

        }


        if (level) {
            corner1.multiply(2);
            corner2.multiply(2);
        }
        thonk = new Box(corner1, corner2);


        for (double i =  thonk.minX; i <= thonk.maxX; i++) {
            for (double j = thonk.minY; j <= thonk.maxY; j++) {
                for (double k =  thonk.minZ; k <= thonk.maxZ; k++) {
//                    if (isOnMaxOrMinX(i, thonk) && isOnMaxOrMinZ(k, thonk) || isOnMaxOrMinX(i, thonk) && isOnMaxOrMinY(j, thonk) || isOnMaxOrMinY(j, thonk) && isOnMaxOrMinZ(k, thonk) || isOnMaxOrMinZ(k, thonk) && isOnMaxOrMinX(i, thonk) && isOnMaxOrMinY(j, thonk)) {
//                        this.particleLocations.add(new Vec3d(i, j, k));
//                    }
                    if((isOnMaxOrMinX(i, thonk) && isOnMaxOrMinY(j, thonk)) || (isOnMaxOrMinY(j, thonk) && isOnMaxOrMinZ(k, thonk)) || (isOnMaxOrMinX(i, thonk) && isOnMaxOrMinZ(k, thonk)) || (isOnMaxOrMinY(j, thonk) && isOnMaxOrMinZ(k, thonk) && isOnMaxOrMinX(i, thonk))){
                        this.particleLocations.add(new Vec3d(i, j, k));
                    }
                }
            }
        }

    }


    public ShulkLevitatorBlockEntity(BlockPos pos, BlockState state, boolean level) {
        super(Shulklevitator.SHULK_LEVITATOR_TYPE, pos, state);
        this.effectTime = 0;
        this.level = level;
        Box thonk;
        Vec3d corner1 = Vec3d.ZERO;
        Vec3d corner2 = Vec3d.ZERO;
        Direction direction = null;
        if (state.getBlock() instanceof RegularShulkLevitatorBlock) {
            direction = state.get(Properties.FACING);
        }
        if (direction != null) {
            if (direction == Direction.UP) {
                corner1 = getVec3dFromBlockPos(pos.add(4, 10, 4));
                corner2 = getVec3dFromBlockPos(pos.add(-4, 1, -4));
            } else if (direction == Direction.DOWN) {
                corner1 = getVec3dFromBlockPos(pos.add(4, -10, 4));
                corner2 = getVec3dFromBlockPos(pos.add(-4, -1, -4));
            } else if (direction == Direction.EAST) {
                corner1 = getVec3dFromBlockPos(pos.add(10, 4, 4));
                corner2 = getVec3dFromBlockPos(pos.add(1, -4, -4));
            } else if (direction == Direction.WEST) {
                corner1 = getVec3dFromBlockPos(pos.add(-10, 4, 4));
                corner2 = getVec3dFromBlockPos(pos.add(-1, -4, -4));
            } else if (direction == Direction.SOUTH) {
                corner1 = getVec3dFromBlockPos(pos.add(4, 4, 10));
                corner2 = getVec3dFromBlockPos(pos.add(-4, -4, 1));
            } else if (direction == Direction.NORTH) {
                corner1 = getVec3dFromBlockPos(pos.add(4, 4, -10));
                corner2 = getVec3dFromBlockPos(pos.add(-4, -4, -1));
            }

        }


        if (level) {
            corner1.multiply(2);
            corner2.multiply(2);
        }
        thonk = new Box(corner1, corner2);


        for (int i = (int) thonk.minX; i <= thonk.maxX; i++) {
            for (int j = (int) thonk.minY; j <= thonk.maxY; j++) {
                for (int k = (int) thonk.minZ; k <= thonk.maxZ; k++) {
                    if ((isOnMaxOrMinX(i, thonk) && isOnMaxOrMinY(j, thonk)) || (isOnMaxOrMinY(j, thonk) && isOnMaxOrMinZ(k, thonk)) || (isOnMaxOrMinX(i, thonk) && isOnMaxOrMinZ(k, thonk))) {
                        this.particleLocations.add(new Vec3d(i, j, k));
                    }
                }
            }
        }
    }


    public static <T extends ShulkLevitatorBlockEntity> void TICK(World world, BlockPos pos, BlockState state, T t) {
        t.tick(world, pos, state, t);
    }

    public void addEffectTime(int addAmount){
        this.effectTime += addAmount;
    }
    public void setEffectTime(int time){
        this.effectTime = time;
    }
    public void removeEffectTime(int removeAmount){
        this.effectTime -= removeAmount;
    }


    public void tick(World world, BlockPos pos, BlockState state, ShulkLevitatorBlockEntity blockEntity) {
        ArrayList<ServerPlayerEntity> currentPlayers = new ArrayList<>();
        if(this.effectTime > 0) {
            Box box = new Box(0, 0, 0, 0, 0, 0);
            Box thonk;
            Vec3d corner1 = Vec3d.ZERO;
            Vec3d corner2 = Vec3d.ZERO;
            Direction direction = null;
            if (state.getBlock() instanceof RegularShulkLevitatorBlock) {
                direction = state.get(Properties.FACING);
            }
            if (direction != null) {
                if (direction == Direction.UP) {
                    corner1 = getVec3dFromBlockPos(pos.add(4, 10, 4));
                    corner2 = getVec3dFromBlockPos(pos.add(-4, 1, -4));
                } else if (direction == Direction.DOWN) {
                    corner1 = getVec3dFromBlockPos(pos.add(4, -10, 4));
                    corner2 = getVec3dFromBlockPos(pos.add(-4, -1, -4));
                } else if (direction == Direction.EAST) {
                    corner1 = getVec3dFromBlockPos(pos.add(10, 4, 4));
                    corner2 = getVec3dFromBlockPos(pos.add(1, -4, -4));
                } else if (direction == Direction.WEST) {
                    corner1 = getVec3dFromBlockPos(pos.add(-10, 4, 4));
                    corner2 = getVec3dFromBlockPos(pos.add(-1, -4, -4));
                } else if (direction == Direction.SOUTH) {
                    corner1 = getVec3dFromBlockPos(pos.add(4, 4, 10));
                    corner2 = getVec3dFromBlockPos(pos.add(-4, -4, 1));
                } else if (direction == Direction.NORTH) {
                    corner1 = getVec3dFromBlockPos(pos.add(4, 4, -10));
                    corner2 = getVec3dFromBlockPos(pos.add(-4, -4, -1));
                }

            }


            if (level) {
                corner1.multiply(2);
                corner2.multiply(2);
            }
            box = new Box(corner1, corner2);
            if (world.isRegionLoaded((int) box.minX, (int) box.minZ, (int) box.maxX, (int) box.maxZ)) {
                for (Entity entity : world.getOtherEntities(null, box)) {
                    if (entity instanceof ServerPlayerEntity) {
                        ((ServerPlayerEntity) entity).addStatusEffect(new StatusEffectInstance(Shulklevitator.FLIGHT, this.effectTime));
                        currentPlayers.add((ServerPlayerEntity) entity);
                    }
                }
            }


            this.effectTime--;
        }
        for(ServerPlayerEntity entity : this.oldPlayers){
            if(!currentPlayers.contains(entity)){
                entity.removeStatusEffect(Shulklevitator.FLIGHT);
            }
        }



    }

    public void displayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (world.isClient) {
            for (Vec3d element : this.particleLocations) {
                world.addParticle(ParticleTypes.END_ROD, element.x, element.y, element.z, 0.005 * MathHelper.nextFloat(random, 1, 2), 0.005 * MathHelper.nextFloat(random, 1, 2), 0.005 * MathHelper.nextFloat(random, 1, 2));
            }
        }
    }

    public static Vec3d getVec3dFromBlockPos(BlockPos pos) {
        return new Vec3d(pos.getX(), pos.getY(), pos.getZ());
    }/*Static helper methods for extended code readability, nothing else lol*/

    public static boolean isOnMaxOrMinX(double i, Box box) {
        if (box == null) {
            return false;
        }
        return i == box.minX || i == box.maxX;
    }

    public static boolean isOnMaxOrMinY(double i, Box box) {
        if (box == null) {
            return false;
        }
        return i == box.minY || i == box.maxY;
    }

    public static boolean isOnMaxOrMinZ(double i, Box box) {
        if (box == null) {
            return false;
        }
        return i == box.minZ || i == box.maxZ;
    }
}
