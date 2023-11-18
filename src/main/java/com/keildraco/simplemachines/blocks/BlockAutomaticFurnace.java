package com.keildraco.simplemachines.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
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
        return null;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return BaseEntityBlock.createTickerHelper(pBlockEntityType, SimpleMachines.FURNACE_ENTITY.get(), AutomaticFurnaceEntity::tickStatic);
    }
}
