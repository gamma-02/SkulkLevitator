package gamma02.shulklevitator.common.blocks;

import gamma02.shulklevitator.Shulklevitator;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.*;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Random;

public class ShulkLevitatorBlockEntity extends BlockEntity {
    public boolean level = false;
    public int effectTime;
    private final ArrayList<Vec3d> particleLocations = new ArrayList<>();
    private ArrayList<ServerPlayerEntity> oldPlayers = new ArrayList<>();
    public static String EFFECT_KEY = "EffectTime";
    public static String LEVEL_KEY = "Upgraded";




    public ShulkLevitatorBlockEntity(BlockPos pos, BlockState state) {
        super(Shulklevitator.SHULK_LEVITATOR_TYPE, pos, state);
        this.effectTime = 0;


        Box thonk;
        Vec3d corner1 = Vec3d.ZERO;
        Vec3d corner2 = Vec3d.ZERO;
        Direction direction = null;
        if (state.getBlock() instanceof RegularShulkLevitatorBlock || state.getBlock() instanceof UpgradedShulkLevitatorBlock) {
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
            if (direction == Direction.UP && level) {
                corner1 = getVec3dFromBlockPos(pos.add(8, 19, 8));
                corner2 = getVec3dFromBlockPos(pos.add(-8, 1, -8));
            } else if (direction == Direction.DOWN && level) {
                corner1 = getVec3dFromBlockPos(pos.add(8, -19, 8));
                corner2 = getVec3dFromBlockPos(pos.add(-8, -1, -8));
            } else if (direction == Direction.EAST&& level) {
                corner1 = getVec3dFromBlockPos(pos.add(19, 8, 8));
                corner2 = getVec3dFromBlockPos(pos.add(1, -8, -8));
            } else if (direction == Direction.WEST&& level) {
                corner1 = getVec3dFromBlockPos(pos.add(-19, 8, 8));
                corner2 = getVec3dFromBlockPos(pos.add(-1, -8, -8));
            } else if (direction == Direction.SOUTH&& level) {
                corner1 = getVec3dFromBlockPos(pos.add(8, 8, 19));
                corner2 = getVec3dFromBlockPos(pos.add(-8, -8, 1));
            } else if (direction == Direction.NORTH&& level) {
                corner1 = getVec3dFromBlockPos(pos.add(8, 8, -19));
                corner2 = getVec3dFromBlockPos(pos.add(-8, -8, -1));
            }

        }
//        if (world != null) {
//            if(world.getBlockState(this.pos).getBlock() instanceof UpgradedShulkLevitatorBlock) {
//                corner1.multiply(2);
//                corner2.multiply(2, 0, 2);
//            }
//        }
        thonk = new Box(corner1, corner2);


        for (double i =  thonk.minX; i <= thonk.maxX; i++) {
            for (double j = thonk.minY; j <= thonk.maxY; j++) {
                for (double k =  thonk.minZ; k <= thonk.maxZ; k++) {
//                    if (isOnMaxOrMinX(i, thonk) && isOnMaxOrMinZ(k, thonk) || isOnMaxOrMinX(i, thonk) && isOnMaxOrMinY(j, thonk) || isOnMaxOrMinY(j, thonk) && isOnMaxOrMinZ(k, thonk) || isOnMaxOrMinZ(k, thonk) && isOnMaxOrMinX(i, thonk) && isOnMaxOrMinY(j, thonk)) {
//                        this.particleLocations.add(new Vec3d(i, j, k));
//                    }
                    if((isOnMaxOrMinX(i, thonk) && isOnMaxOrMinY(j, thonk)) || (isOnMaxOrMinY(j, thonk) && isOnMaxOrMinZ(k, thonk)) || (isOnMaxOrMinX(i, thonk) && isOnMaxOrMinZ(k, thonk)) || (isOnMaxOrMinY(j, thonk) && isOnMaxOrMinZ(k, thonk) && isOnMaxOrMinX(i, thonk))){
                        this.particleLocations.add(new Vec3d(i+0.5, j+0.5, k+0.5));
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
        if (state.getBlock() instanceof RegularShulkLevitatorBlock || state.getBlock() instanceof UpgradedShulkLevitatorBlock) {
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
            if (direction == Direction.UP && level) {
                corner1 = getVec3dFromBlockPos(pos.add(8, 19, 8));
                corner2 = getVec3dFromBlockPos(pos.add(-8, 1, -8));
            } else if (direction == Direction.DOWN && level) {
                corner1 = getVec3dFromBlockPos(pos.add(8, -19, 8));
                corner2 = getVec3dFromBlockPos(pos.add(-8, -1, -8));
            } else if (direction == Direction.EAST&& level) {
                corner1 = getVec3dFromBlockPos(pos.add(19, 8, 8));
                corner2 = getVec3dFromBlockPos(pos.add(1, -8, -8));
            } else if (direction == Direction.WEST&& level) {
                corner1 = getVec3dFromBlockPos(pos.add(-19, 8, 8));
                corner2 = getVec3dFromBlockPos(pos.add(-1, -8, -8));
            } else if (direction == Direction.SOUTH&& level) {
                corner1 = getVec3dFromBlockPos(pos.add(8, 8, 19));
                corner2 = getVec3dFromBlockPos(pos.add(-8, -8, 1));
            } else if (direction == Direction.NORTH&& level) {
                corner1 = getVec3dFromBlockPos(pos.add(8, 8, -19));
                corner2 = getVec3dFromBlockPos(pos.add(-8, -8, -1));
            }

        }



        corner1.add(0.5, 0, 0.5);
        corner2.add(0.5, 0, 0.5);

        thonk = new Box(corner1, corner2);


        for (int i = (int) thonk.minX; i <= thonk.maxX; i++) {
            for (int j = (int) thonk.minY; j <= thonk.maxY; j++) {
                for (int k = (int) thonk.minZ; k <= thonk.maxZ; k++) {
                    if ((isOnMaxOrMinX(i, thonk) && isOnMaxOrMinY(j, thonk)) || (isOnMaxOrMinY(j, thonk) && isOnMaxOrMinZ(k, thonk)) || (isOnMaxOrMinX(i, thonk) && isOnMaxOrMinZ(k, thonk))) {
                        this.particleLocations.add(new Vec3d(i+0.5, j+0.5, k+0.5));
                    }
                }
            }
        }
    }


    public static <T extends ShulkLevitatorBlockEntity> void TICK(World world, BlockPos pos, BlockState state, T t) {
        t.tick(world, pos, state);
    }

    public void addEffectTime(int addAmount){
        this.effectTime = this.effectTime + addAmount;
    }



    public void tick(World world, BlockPos pos, BlockState state) {

        ArrayList<ServerPlayerEntity> currentPlayers = new ArrayList<>();
        if(this.effectTime > 0) {
            Box box;
            Vec3d corner1 = Vec3d.ZERO;
            Vec3d corner2 = Vec3d.ZERO;
            Direction direction = null;
            if (state.getBlock() instanceof RegularShulkLevitatorBlock || state.getBlock() instanceof UpgradedShulkLevitatorBlock) {
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
                if (direction == Direction.UP && level) {
                    corner1 = getVec3dFromBlockPos(pos.add(8, 19, 8));
                    corner2 = getVec3dFromBlockPos(pos.add(-8, 1, -8));
                } else if (direction == Direction.DOWN && level) {
                    corner1 = getVec3dFromBlockPos(pos.add(8, -19, 8));
                    corner2 = getVec3dFromBlockPos(pos.add(-8, -1, -8));
                } else if (direction == Direction.EAST&& level) {
                    corner1 = getVec3dFromBlockPos(pos.add(19, 8, 8));
                    corner2 = getVec3dFromBlockPos(pos.add(1, -8, -8));
                } else if (direction == Direction.WEST&& level) {
                    corner1 = getVec3dFromBlockPos(pos.add(-19, 8, 8));
                    corner2 = getVec3dFromBlockPos(pos.add(-1, -8, -8));
                } else if (direction == Direction.SOUTH&& level) {
                    corner1 = getVec3dFromBlockPos(pos.add(8, 8, 19));
                    corner2 = getVec3dFromBlockPos(pos.add(-8, -8, 1));
                } else if (direction == Direction.NORTH&& level) {
                    corner1 = getVec3dFromBlockPos(pos.add(8, 8, -19));
                    corner2 = getVec3dFromBlockPos(pos.add(-8, -8, -1));
                }

            }




            corner1.add(0.5, 0, 0.5);
            corner2.add(0.5, 0, 0.5);

            box = new Box(corner1, corner2);
            //noinspection deprecation
            if (world.isRegionLoaded((int) box.minX, (int) box.minZ, (int) box.maxX, (int) box.maxZ)) {
                for (Entity entity : world.getOtherEntities(null, box)) {
                    if (entity instanceof ServerPlayerEntity) {
                            ((ServerPlayerEntity) entity).addStatusEffect(new StatusEffectInstance(Shulklevitator.FLIGHT, this.effectTime));
                            currentPlayers.add((ServerPlayerEntity) entity);
                            this.effectTime--;
                    }
                }
            }



        }
        if(!world.isClient) {
            world.setBlockState(pos, level ? Shulklevitator.UPGRADED_SHULK_LEVITATOR_BLOCK.getDefaultState().with(Properties.ENABLED, this.effectTime > 0).with(Properties.FACING, state.get(Properties.FACING)) : Shulklevitator.REGULAR_SHULK_LEVITATOR_BLOCK.getDefaultState().with(Properties.ENABLED, this.effectTime > 0).with(Properties.FACING, state.get(Properties.FACING)));
        }


        for(ServerPlayerEntity entity : this.oldPlayers){
            if(!currentPlayers.contains(entity)){
                entity.setStatusEffect(new StatusEffectInstance(Shulklevitator.FLIGHT, 2), null);
            }
        }
        this.oldPlayers = currentPlayers;
    }

    public void displayTick(BlockState state, World world, BlockPos pos, Random random) {
        Random random1 = new Random();
        Random random2 = new Random();
        if (world.isClient) {
            for (Vec3d element : this.particleLocations) {
                world.addParticle(ParticleTypes.END_ROD, element.x, element.y, element.z, 0.005 * MathHelper.nextFloat(random, 1, 2), 0.005 * MathHelper.nextFloat(random1, 1, 2), 0.005 * MathHelper.nextFloat(random2, 1, 2));
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

    @Override
    public void readNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        this.effectTime = nbt.getInt(EFFECT_KEY);
        this.level = nbt.getBoolean(LEVEL_KEY);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putInt(EFFECT_KEY, effectTime);
        nbt.putBoolean(LEVEL_KEY, this.level);
    }

    public boolean canAddEffect(int time){
        return this.level ? time < UpgradedShulkLevitatorBlock.MAX_CAPACITY-this.effectTime : time < RegularShulkLevitatorBlock.MAX_CAPACITY-this.effectTime;
    }
}
