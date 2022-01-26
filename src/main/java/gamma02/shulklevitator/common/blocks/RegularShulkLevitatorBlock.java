package gamma02.shulklevitator.common.blocks;

import gamma02.shulklevitator.Shulklevitator;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PotionItem;
import net.minecraft.potion.PotionUtil;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class RegularShulkLevitatorBlock extends FacingBlock implements BlockEntityProvider {

    public static int MAX_CAPACITY = 18000;

    public static VoxelShape UP = Block.createCuboidShape(0, 0, 0, 16, (0.8125*16), 16);
    public static VoxelShape DOWN = Block.createCuboidShape(0, (0.1875*16), 0, 16, 16, 16);
    public static VoxelShape SOUTH = Block.createCuboidShape(0, 0, 0, 16, 16, (0.8125*16));
    public static VoxelShape NORTH = Block.createCuboidShape(0, 0, (0.1875*16), 16, 16, 16);
    public static VoxelShape EAST = Block.createCuboidShape(0, 0, 0, (0.8125*16), 16, 16);
    public static VoxelShape WEST = Block.createCuboidShape((16*0.1875), 0, 0, 16, 16, 16);



    public RegularShulkLevitatorBlock(Settings settings) {
        super(settings);
        this.setDefaultState((this.stateManager.getDefaultState()).with(Properties.FACING, Direction.UP).with(Properties.ENABLED, false));
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ShulkLevitatorBlockEntity(pos, state, false);
    }
    public static ActionResult shouldUse(World world, BlockPos pos, PlayerEntity player, Hand hand){
        System.out.println("h e l l o");
        if(world.getBlockEntity(pos) instanceof ShulkLevitatorBlockEntity entity && player.getStackInHand(hand).getItem() instanceof PotionItem){
            ItemStack stack = player.getStackInHand(hand);
            final int[] time = {0};
            boolean matches = PotionUtil.getPotionEffects(stack).stream().anyMatch((statusEffectInstance) ->  {
                if(statusEffectInstance.getEffectType() == StatusEffects.LEVITATION){
                    time[0] = time[0] + statusEffectInstance.getDuration();
                    return true;
                }else {
                    return false;
                }
            });
            if(matches){
                System.out.println(time[0]);
                if(entity.canAddEffect(time[0])){
                    stack.decrement(1);
                    entity.addEffectTime(time[0]);
                    return ActionResult.PASS;
                }
            }else{
                return ActionResult.FAIL;
            }

        }
        return ActionResult.FAIL;
    }
    @SuppressWarnings("deprecation")
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        return shouldUse(world, pos, player, hand);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if(world.getBlockEntity(pos) instanceof ShulkLevitatorBlockEntity blockEntity){
            blockEntity.displayTick(state, world, pos, random);
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return getVoxelShape(state, DOWN, UP, EAST, WEST, NORTH, SOUTH);


    }

    public static VoxelShape getVoxelShape(BlockState state, VoxelShape down, VoxelShape up, VoxelShape east, VoxelShape west, VoxelShape north, VoxelShape south) {
        Direction direction = state.get(Properties.FACING);

        if(direction == Direction.DOWN){
            return down;
        }else if(direction == Direction.UP){
            return up;
        }else if(direction == Direction.EAST){
            return east;
        }else if(direction == Direction.WEST){
            return west;
        }else if(direction == Direction.NORTH){
            return north;
        }else if(direction == Direction.SOUTH){
            return south;
        }else{
            System.out.println("what happened!?!?!");
            System.out.println("Direction was NOT A DIRECTION!!!!");
            return Block.createCuboidShape(0, 0, 0, 1, 1, 1);
        }
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return hackery.checkType1(type, Shulklevitator.SHULK_LEVITATOR_TYPE, ShulkLevitatorBlockEntity::TICK);
    }
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        Direction direction = ctx.getSide();
        BlockState blockState = ctx.getWorld().getBlockState(ctx.getBlockPos().offset(direction.getOpposite()));
        return blockState.isOf(this) && blockState.get(Properties.FACING) == direction ? this.getDefaultState().with(Properties.FACING, direction.getOpposite()) : this.getDefaultState().with(Properties.FACING, direction);
    }
    public void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(Properties.FACING, Properties.ENABLED);
    }

}
