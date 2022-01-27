package gamma02.shulklevitator.common.blocks;

import gamma02.shulklevitator.Shulklevitator;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffectInstance;
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

@SuppressWarnings("deprecation")
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
    public static ActionResult shouldUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit){
        if(player.getStackInHand(hand).getItem() instanceof PotionItem && world.getBlockEntity(pos) instanceof ShulkLevitatorBlockEntity entity){
            ItemStack potion = player.getStackInHand(hand);
            int time = 0;
            for(StatusEffectInstance instance : PotionUtil.getPotionEffects(potion)){
                if(instance.getEffectType() == StatusEffects.LEVITATION){
                    time += instance.getDuration();

                }
            }
            if( MAX_CAPACITY - entity.effectTime > time ){
                entity.addEffectTime(time);
                potion.decrement(1);
                return ActionResult.SUCCESS;
            }
        }
        return ActionResult.FAIL;
    }
    @SuppressWarnings("deprecation")
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {

        return shouldUse(state, world, pos, player, hand, hit);
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
        builder.add(Properties.FACING, Properties.ENABLED);
    }

    @Override
    public boolean hasComparatorOutput(BlockState state) {
        return true;
    }

    @Override
    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        if(world.getBlockEntity(pos) instanceof ShulkLevitatorBlockEntity entity){
            return entity.getComparatorOutput();
        }
        return 0;

    }

    @Override
    public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack stack) {
        if(blockEntity instanceof ShulkLevitatorBlockEntity entity){
            world.getOtherEntities(null, entity.getBox(state)).forEach((Entity entity1) -> {
                if(entity1 instanceof PlayerEntity entity2){
                    entity2.setStatusEffect(new StatusEffectInstance(Shulklevitator.FLIGHT, 0), null);
                }
            });
        }
        if(!world.isClient) {
//            ItemEntity item = new ItemEntity(world, pos.getX(), pos.getY()+0.2, pos.getZ(), this.asItem().getDefaultStack());
//            world.spawnEntity(item);
            Block.dropStack(world, pos, this.asItem().getDefaultStack());

        }
    }
}
