package gamma02.shulklevitator.common.blocks;

import gamma02.shulklevitator.Shulklevitator;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PotionItem;
import net.minecraft.potion.PotionUtil;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
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

import static net.minecraft.block.FacingBlock.FACING;

public class RegularShulkLevitatorBlock extends FacingBlock implements BlockEntityProvider {
    public static VoxelShape UP = Block.createCuboidShape(0, 0, 0, 16, (0.8125*16), 16);
    public static VoxelShape DOWN = Block.createCuboidShape(0, (0.1875*16), 0, 16, 16, 16);
    public static VoxelShape SOUTH = Block.createCuboidShape(0, 0, 0, 16, 16, (0.8125*16));
    public static VoxelShape NORTH = Block.createCuboidShape(0, 0, (0.1875*16), 16, 16, 16);
    public static VoxelShape EAST = Block.createCuboidShape(0, 0, 0, (0.8125*16), 16, 16);
    public static VoxelShape WEST = Block.createCuboidShape((16*0.1875), 0, 0, 16, 16, 16);



    public RegularShulkLevitatorBlock(Settings settings) {
        super(settings);
        this.setDefaultState((this.stateManager.getDefaultState()).with(Properties.FACING, Direction.UP));
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ShulkLevitatorBlockEntity(pos, state, false);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
            if(player.getStackInHand(hand).getItem() instanceof PotionItem && world.getBlockEntity(pos) instanceof ShulkLevitatorBlockEntity){
                ItemStack potion = player.getStackInHand(hand);
                ShulkLevitatorBlockEntity blockEntity = (ShulkLevitatorBlockEntity) world.getBlockEntity(pos);
                if(PotionUtil.getPotion(potion) == Shulklevitator.LEVITATION_POTION){
                    blockEntity.addEffectTime(3600);
                    potion.decrement(1);
                    return ActionResult.SUCCESS;
                }else if(PotionUtil.getPotion(potion) == Shulklevitator.LONG_LEVITATION_POTION){
                    blockEntity.addEffectTime(9600);
                    potion.decrement(1);
                    return ActionResult.SUCCESS;
                }
            }

        return super.onUse(state, world, pos, player, hand, hit);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if(world.getBlockEntity(pos) instanceof ShulkLevitatorBlockEntity){
            ShulkLevitatorBlockEntity blockEntity = (ShulkLevitatorBlockEntity) world.getBlockEntity(pos);
            blockEntity.displayTick(state, world, pos, random);
        }
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Direction direction = state.get(Properties.FACING);

        if(direction == Direction.DOWN){
            return DOWN;
        }else if(direction == Direction.UP){
            return UP;
        }else if(direction == Direction.EAST){
            return EAST;
        }else if(direction == Direction.WEST){
            return WEST;
        }else if(direction == Direction.NORTH){
            return NORTH;
        }else if(direction == Direction.SOUTH){
            return SOUTH;
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
        builder.add(Properties.FACING);
    }

}
