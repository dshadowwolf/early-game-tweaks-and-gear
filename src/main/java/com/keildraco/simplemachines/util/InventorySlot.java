package com.keildraco.simplemachines.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public class InventorySlot {
    private final ItemStack sample;
    private long count;

    public InventorySlot(ItemStack stack) {
        this.sample = stack.copy();
        sample.setCount(1);
        this.count = stack.getCount();
    }

    public InventorySlot(CompoundTag tag) {
        this.count = tag.getLong("Count");
        this.sample = ItemStack.of(tag.getCompound("Item"));
    }

    public ItemStack getItem() {
        return this.sample;
    }

    public long getCount() {
        return this.count;
    }

    public void grow() {
        increaseCount(1);
    }

    public void increaseCount(long amount) {
        this.count += amount;
    }

    public void shrink() {
        decreaseCount(1);
    }

    public void decreaseCount(long amount) {
        this.count -= amount;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
