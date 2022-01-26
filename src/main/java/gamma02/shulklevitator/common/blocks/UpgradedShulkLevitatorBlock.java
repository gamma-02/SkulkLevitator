package gamma02.shulklevitator.common.blocks;

import gamma02.shulklevitator.Shulklevitator;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
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

public class UpgradedShulkLevitatorBlock extends FacingBlock implements BlockEntityProvider {

    public static int MAX_CAPACITY = 36000;

    public static VoxelShape UP = Block.createCuboidShape(0, 0, 0, 16, (0.8125*16), 16);
    public static VoxelShape DOWN = Block.createCuboidShape(0, (0.1875*16), 0, 16, 16, 16);
    public static VoxelShape SOUTH = Block.createCuboidShape(0, 0, 0, 16, 16, (0.8125*16));
    public static VoxelShape NORTH = Block.createCuboidShape(0, 0, (0.1875*16), 16, 16, 16);
    public static VoxelShape EAST = Block.createCuboidShape(0, 0, 0, (0.8125*16), 16, 16);
    public static VoxelShape WEST = Block.createCuboidShape((16*0.1875), 0, 0, 16, 16, 16);



    public UpgradedShulkLevitatorBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState((this.stateManager.getDefaultState()).with(Properties.FACING, Direction.UP).with(Properties.ENABLED, false));
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ShulkLevitatorBlockEntity(pos, state, true);
    }

    @SuppressWarnings("deprecation")
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {

        return RegularShulkLevitatorBlock.shouldUse(world, pos, player, hand);
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
        return RegularShulkLevitatorBlock.getVoxelShape(state, DOWN, UP, EAST, WEST, NORTH, SOUTH);
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
