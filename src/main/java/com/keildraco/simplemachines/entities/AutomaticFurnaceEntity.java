package com.keildraco.simplemachines.entities;

import com.keildraco.simplemachines.SimpleMachines;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class AutomaticFurnaceEntity extends BlockEntity {
    public AutomaticFurnaceEntity(BlockPos pos, BlockState state) {
        super(SimpleMachines.FURNACE_ENTITY.get(), pos, state);
    }

    // TODO: Implement Item Handling Bits
    public void tick() {
        // TODO: Implement Recipe Processing Logic
    }

    public static void tickStatic(Level level, BlockPos blockPos, BlockState blockState, AutomaticFurnaceEntity te)
    {
        te.tick();
    }
}
