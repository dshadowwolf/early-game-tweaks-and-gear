package com.keildraco.simplemachines.entities;

import com.keildraco.simplemachines.SimpleMachines;
import com.keildraco.simplemachines.util.AutomaticFurnaceRecipe;
import com.keildraco.simplemachines.util.FurnaceInventory;
import com.keildraco.simplemachines.util.FurnaceInventoryContext;
import com.keildraco.simplemachines.util.InventorySlot;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AutomaticFurnaceEntity extends BlockEntity implements ContainerData, MenuProvider {
    public final ItemStackHandler inventory = new ItemStackHandler(9);
    private int remainingBurnTime;
    private int totalBurnTime;
    private int cookTime;
    private int totalCookTime;

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

    @Override
    public Component getDisplayName() {
        return Component.translatable("entity.simplemachines.automagic_furnace");
    }

    @Override
    public int get(int index) {
        switch (index)
        {
            case 0:
                return remainingBurnTime;
            case 1:
                return totalBurnTime;
            case 2:
                return cookTime;
            case 3:
                return totalCookTime;
        }
        return 0;
    }

    @Override
    public void set(int index, int value) {
        switch (index)
        {
            case 0:
                remainingBurnTime = value;
            case 1:
                totalBurnTime = value;
            case 2:
                cookTime = value;
            case 3:
                totalCookTime = value;
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

    // TODO: Everything below this line...
    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return null;
    }

    CompoundTag save() {
        return null;
    }

    void load(CompoundTag root) {

    }

    private boolean canWork(FurnaceInventoryContext ctx, AutomaticFurnaceRecipe choppingRecipe)
    {
        return false;
    }

    private void processItem(FurnaceInventoryContext ctx, AutomaticFurnaceRecipe choppingRecipe)
    {

    }

    private ItemStack getResult(FurnaceInventoryContext ctx, @Nullable AutomaticFurnaceRecipe choppingRecipe)
    {
        return ItemStack.EMPTY;
    }
}
