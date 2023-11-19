package com.keildraco.simplemachines.blocks;

import com.keildraco.simplemachines.util.AutomaticFurnaceMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

import com.keildraco.simplemachines.SimpleMachines;
import com.keildraco.simplemachines.entities.AutomaticFurnaceEntity;

public class BlockAutomaticFurnace extends BaseEntityBlock {
    public BlockAutomaticFurnace(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new AutomaticFurnaceEntity(blockPos, blockState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return BaseEntityBlock.createTickerHelper(pBlockEntityType, SimpleMachines.FURNACE_ENTITY.get(), AutomaticFurnaceEntity::tickStatic);
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand hanbdIn, BlockHitResult hit) {
        BlockEntity be = worldIn.getBlockEntity(pos);
        // if (!(te instanceof StoringSewingTableBlockEntity table))
        if (!(be instanceof AutomaticFurnaceEntity furnace))
            return InteractionResult.FAIL;

        if (worldIn.isClientSide)
            return InteractionResult.SUCCESS;

        player.openMenu(new SimpleMenuProvider((
                (id, playerInv, p) -> new AutomaticFurnaceMenu(id, playerInv, ContainerLevelAccess.create(worldIn, pos), furnace), Component.translatable("component.simplemachines.automagic_furnace")
                ));

        return InteractionResult.SUCCESS;
    }

    /*

    public void onRemove(BlockState p_49076_, Level p_49077_, BlockPos p_49078_, BlockState p_49079_, boolean p_49080_) {
    ... drop inventory and such here ...
    }
     */
}
