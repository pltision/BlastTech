package yee.pltision.blasttech.block;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import yee.pltision.blasttech.structures.StructureEntity;
import yee.pltision.blasttech.structures.StructureType;

import java.util.Map;

import static yee.pltision.blasttech.structures.BlastStructures.STRUCTURE_TYPE_REGISTRY;

@SuppressWarnings("deprecation")
public class DisplayFurnaceBlock extends Block implements EntityBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty LIT = BlockStateProperties.LIT;

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_48725_) {
        p_48725_.add(FACING);
        p_48725_.add(LIT);
    }

    public DisplayFurnaceBlock(Properties p_49795_) {
        super(p_49795_);
        registerDefaultState(defaultBlockState().setValue(LIT,false));
    }

    public void onPlace(@NotNull BlockState state,@NotNull Level level,@NotNull BlockPos pos,@NotNull BlockState replaced,boolean p_60570_/*玩家放置?*/) {
        for(Map.Entry<ResourceKey<StructureType<?>>, StructureType<?>> entry:STRUCTURE_TYPE_REGISTRY.entrySet()){
            System.out.println(entry.getKey());
            StructureEntity entity=entry.getValue().tryCreate(state,level,pos,replaced);
            if(entity!=null && level.getBlockEntity(pos) instanceof DisplayedFurnaceEntity blockEntity&&blockEntity.entity==null){
                blockEntity.type=entry.getKey().location();
                blockEntity.entity=entity;
                if(entity.createTicker()) level.setBlock(pos,state.setValue(LIT,true),3);
                break;
            }
        }
    }

    @Override
    public @NotNull InteractionResult use(@NotNull BlockState state,@NotNull  Level level,@NotNull  BlockPos pos,@NotNull  Player player,@NotNull  InteractionHand hand,@NotNull  BlockHitResult result) {
        if(level.getBlockEntity(pos) instanceof DisplayedFurnaceEntity blockEntity){
            if(blockEntity.entity!=null)
                return blockEntity.entity.use(state,level,pos,player,hand,result);
        }
        return super.use(state, level, pos, player, hand, result);
    }

    @NotNull
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos p_153215_, @NotNull BlockState p_153216_) {
        return new DisplayedFurnaceEntity(p_153215_,p_153216_);
    }

    public @NotNull BlockState rotate(BlockState p_48722_, Rotation p_48723_) {
        return p_48722_.setValue(FACING, p_48723_.rotate(p_48722_.getValue(FACING)));
    }

    public @NotNull BlockState mirror(BlockState p_48719_, Mirror p_48720_) {
        return p_48719_.rotate(p_48720_.getRotation(p_48719_.getValue(FACING)));
    }
    public BlockState getStateForPlacement(BlockPlaceContext p_48689_) {
        return this.defaultBlockState().setValue(FACING, p_48689_.getHorizontalDirection().getOpposite());
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level p_153212_, @NotNull BlockState p_153213_, @NotNull BlockEntityType<T> p_153214_) {
        if(p_153213_.hasProperty(LIT)&&p_153213_.getValue(LIT))
            return (level, pos, state, blockEntity) -> {
                if(blockEntity instanceof DisplayedFurnaceEntity entity){
                    if(entity.entity!=null)
                        entity.entity.tick(level,pos,state,entity);
                }
            };
        else return null;
    }
}
