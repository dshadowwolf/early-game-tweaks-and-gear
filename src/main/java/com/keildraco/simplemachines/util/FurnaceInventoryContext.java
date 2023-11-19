package com.keildraco.simplemachines.util;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.items.IItemHandlerModifiable;

import javax.annotation.Nullable;
import java.util.Random;
import java.util.function.Supplier;

public class FurnaceInventoryContext extends ItemHandlerWrapper {
    protected final Player player;
    protected final int blargh = -1; // reserved, just here for other things, really
    protected final Random random;

    public FurnaceInventoryContext(IItemHandlerModifiable inner, @Nullable Player player, @Nullable Supplier<Vec3> location, @Nullable Random random) {
        super(inner, location, 64);
        this.player = player;
        this.random = random;
    }

    @Nullable
    public Player getPlayer() {
        return this.player;
    }

    @Nullable
    public Random getRandom() {
        return this.random;
    }
}
