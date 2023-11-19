package com.keildraco.simplemachines.util;

import com.keildraco.simplemachines.SimpleMachines;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.inventory.*;
import net.neoforged.neoforge.items.IItemHandlerModifiable;
import net.neoforged.neoforge.registries.ObjectHolder;
import org.apache.commons.compress.utils.Lists;

import javax.annotation.Nullable;

import java.util.List;
import java.util.Random;

public class AutomaticFurnaceMenu extends RecipeBookMenu<Container> {
    private static final int NUM_INPUTS = 6;
    private static final int NUM_OUTPUTS = 2;
    private static final int NUM_INVENTORY = 9 * 3;
    private static final int NUM_HOTBAR = 9;
    private static final int OUTPUTS_START = NUM_INPUTS;
    private static final int PLAYER_START = OUTPUTS_START + NUM_OUTPUTS;
    private static final int HOTBAR_START = PLAYER_START + NUM_INVENTORY;
    private static final int PLAYER_END = HOTBAR_START + NUM_HOTBAR;
    private static final Random RANDOM = new Random();

    private final Level world;
    private final ContainerLevelAccess openedFrom;
    private final DataSlot selectedRecipe = DataSlot.standalone();
    private final ItemStack[] inputStacksCache = new ItemStack[] {
            ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY,
            ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY
    };

    private final FurnaceInventory furnaceInventory;
    private List<AutomaticFurnaceRecipe> recipes = Lists.newArrayList();

    private Runnable inventoryUpdateListener = () -> {
    };

    public final IItemHandlerModifiable inputInventory;
    private final ResultContainer inventory = new ResultContainer();

    public AutomaticFurnaceMenu(int windowId, Inventory playerInventory) {
        this(windowId, playerInventory,ContainerLevelAccess.NULL);
    }

    public AutomaticFurnaceMenu(int windowId, Inventory playerInventory, final ContainerLevelAccess worldPosCallableIn) {
        this(windowId, playerInventory, worldPosCallableIn, new FurnaceInventory());
    }

    public AutomaticFurnaceMenu(int windowId, Inventory playerInventory, final ContainerLevelAccess worldPosCallableIn, IItemHandlerModifiable inventory) {
        super(SimpleMachines.AUTOMATIC_FURNACE_MENU.get(), windowId);

        this.openedFrom = worldPosCallableIn;
        this.world = playerInventory.player.level();
        this.inputInventory = inventory;
        this.furnaceInventory = new FurnaceInventory(); // temporary placeholder

        // TODO: Complete this
        // add slots and whatnot here

    }
    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        return null;
    }

    @Override
    public boolean stillValid(Player player) {
        return false;
    }

    @Override
    public void fillCraftSlotsStackedContents(StackedContents stackedContents) {

    }

    @Override
    public void clearCraftingContent() {

    }

    @Override
    public boolean recipeMatches(RecipeHolder<? extends Recipe<Container>> recipeHolder) {
        return false;
    }

    @Override
    public int getResultSlotIndex() {
        return 0;
    }

    @Override
    public int getGridWidth() {
        return 0;
    }

    @Override
    public int getGridHeight() {
        return 0;
    }

    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public RecipeBookType getRecipeBookType() {
        return null;
    }

    @Override
    public boolean shouldMoveToInventory(int i) {
        return false;
    }
}
