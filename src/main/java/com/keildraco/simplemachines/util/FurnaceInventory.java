package com.keildraco.simplemachines.util;

import com.google.common.collect.Lists;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandlerModifiable;
import org.jetbrains.annotations.NotNull;

import java.util.List;

// large chunks cribbed from the example provided by the mod EnderRift by David "Gigaherz" Quintana
public class FurnaceInventory implements IItemHandlerModifiable {
    final List<InventorySlot> slots = Lists.newArrayList();

    public FurnaceInventory() {

    }


    @Override
    public int getSlots() {
        return slots.size() + 1;
    }

    @Override
    public @NotNull ItemStack getStackInSlot(int index) {
        if (index > slots.size()) return ItemStack.EMPTY;

        InventorySlot slot = slots.get(index);
        ItemStack stack = slot.getItem().copy();
        stack.setCount((int)Math.min(Integer.MAX_VALUE, slot.getCount()));
        return stack;
    }

    @Override
    public @NotNull ItemStack insertItem(int index, @NotNull ItemStack itemStack, boolean simulate) {
        if (simulate) return ItemStack.EMPTY;

        if (index < slots.size() && index >= 0 && tryCombineStacks(index, itemStack)) return ItemStack.EMPTY;
        if (index >= slots.size()) return ItemStack.EMPTY; // this should never happen, but we should be careful

        for (int i = 0; i < slots.size(); i++) {
            if (index == i && !tryCombineStacks(i, itemStack)) continue;
            return ItemStack.EMPTY;
        }
        slots.add(new InventorySlot(itemStack));
        return ItemStack.EMPTY;
    }

    /*
     * shamelessly ~~stolen~~copied from EnderRift with changes made for type details
     */
    private boolean tryCombineStacks(int index, ItemStack stack) {
        InventorySlot slot = slots.get(index);
        ItemStack sample = slot.getItem();
        if (ItemStack.isSameItemSameTags(stack, sample)) {
            slot.increaseCount(stack.getCount());
            return true;
        }
        return false;
    }
    @Override
    public @NotNull ItemStack extractItem(int index, int wanted, boolean simulate) {
        if (index >= slots.size()) return ItemStack.EMPTY;

        InventorySlot slot = slots.get(index);
        ItemStack stack = slot.getItem().copy();
        long count = slot.getCount();

        if (simulate) {
            int actual = (int) Math.min(wanted, count);
            stack.setCount(actual);
        } else if (count <= wanted) {
            stack.setCount((int)count);
            slots.remove(index);
        } else {
            stack.setCount(wanted);
            slot.decreaseCount(wanted);
        }

        return stack;
    }

    @Override
    public int getSlotLimit(int i) {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isItemValid(int index, @NotNull ItemStack itemStack) {
        // each slot has a different allowed item type, I'll need to rework things here, but...
        // for now we can just do the whole thing with comparing to the empty itemstack
        return !ItemStack.isSameItem(ItemStack.EMPTY, itemStack);
    }

    /*
     * The following will need to change when I rework the mechanics here to have a defined set of slots
     * and they'll all have names...
     */
    CompoundTag save() {
        CompoundTag base = new CompoundTag();
        ListTag list = new ListTag();
        InventorySlot[] slots = this.slots.toArray(InventorySlot[]::new);
        for (InventorySlot s : slots) {
            CompoundTag p = new CompoundTag();
            p.putLong("Count", s.getCount());
            p.put("Item", s.getItem().save(new CompoundTag()));
            list.add(p);
        }
        base.put("Contents", list);
        return base;
    }

    void load(CompoundTag root) {
        slots.clear();
        ListTag itemList = root.getList("Contents", Tag.TAG_COMPOUND);
        for (Tag raw : itemList) {
            CompoundTag t = (CompoundTag) raw;
            long count = t.getLong("Count");
            ItemStack stack = ItemStack.of(t.getCompound("Item"));
            InventorySlot slot = new InventorySlot(stack);
            slot.setCount(count);
            slots.add(slot);
        }
    }

    @Override
    public void setStackInSlot(int index, @NotNull ItemStack itemStack) {
        slots.set(index, new InventorySlot(itemStack));
    }
}
