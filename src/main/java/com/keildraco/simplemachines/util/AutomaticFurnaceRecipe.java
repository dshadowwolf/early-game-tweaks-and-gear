package com.keildraco.simplemachines.util;

import com.keildraco.simplemachines.SimpleMachines;
import com.mojang.datafixers.Products;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

import java.util.Collection;

public class AutomaticFurnaceRecipe implements Recipe<Container> {
    private final String group;

    private final Ingredient input;
    private final ItemStack output;
    private final int cookTime;

    private final boolean needsUgrade;
    private final FurnaceUpgradeType neededUpgrade;

    public AutomaticFurnaceRecipe(String group, Ingredient inputIngredient, ItemStack outputStack, int cookTime, boolean upgradeRequired, FurnaceUpgradeType typeOfUpgrade) {
        this.group = group;
        this.input = inputIngredient;
        this.output = outputStack;
        this.needsUgrade = upgradeRequired;
        this.neededUpgrade = typeOfUpgrade;
        this.cookTime = cookTime;
    }

    // TODO: Fix this, its an error and I don't actually know the code in this region
    public static Collection<AutomaticFurnaceRecipe> getAllRecipes(Level worldIn) {
        return worldIn.getRecipeManager().getAllRecipesFor(SimpleMachines.FURNACE_RECIPES.get());
    }

    @Override
    public String getGroup() {
        return group;
    }

    @Override
    public ItemStack getToastSymbol() {
        return Recipe.super.getToastSymbol();
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> rv = NonNullList.create();
        rv.add(input);
        return rv;
    }

    @Override
    public boolean isSpecial() {
        return Recipe.super.isSpecial();
    }

    @Override
    public boolean showNotification() {
        return Recipe.super.showNotification();
    }

    @Override
    public boolean matches(Container inv, Level worldIn) {
        // TODO: Implement this
        // need to work on things on the Furance Inventory side first

        return false;
    }

    @Override
    public ItemStack assemble(Container inv, RegistryAccess registryAccess)
    {
        return getResultItem(registryAccess).copy();
    }

    @Override
    public boolean canCraftInDimensions(int i, int i1) {
        return false;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess)
    {
        return output;
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(Container p_44004_) {
        return Recipe.super.getRemainingItems(p_44004_);
    }

    public ItemStack getResultItem()
    {
        return output;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return SimpleMachines.FURNACE_RECIPE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return SimpleMachines.FURNACE_RECIPES.get();
    }

    @Override
    public boolean isIncomplete() {
        return Recipe.super.isIncomplete();
    }

    public static class AutomaticFurnaceSerializer implements RecipeSerializer<AutomaticFurnaceRecipe> {
        private final Codec<AutomaticFurnaceRecipe> CODEC = RecordCodecBuilder.create( (records) -> {
            Products.P6<RecordCodecBuilder.Mu<AutomaticFurnaceRecipe>, String, Ingredient, ItemStack, Integer, Boolean, FurnaceUpgradeType> base = records.group(
                ExtraCodecs.strictOptionalField(Codec.STRING, "group", "").forGetter( (groupGetter) -> {
                    return groupGetter.group;
                }), Ingredient.CODEC_NONEMPTY.fieldOf("input").forGetter( (inputGetter) -> {
                    return inputGetter.input;
                }), BuiltInRegistries.ITEM.byNameCodec().xmap(ItemStack::new, ItemStack::getItem).fieldOf("result").forGetter( (resultGetter) -> {
                    return resultGetter.output;
                }), Codec.INT.fieldOf("cookTime").orElse(200).forGetter( (timeGetter) -> {
                    return timeGetter.cookTime;
                }), Codec.BOOL.fieldOf("requires_upgrade").orElse(false).forGetter( (getter) -> {
                    return getter.needsUgrade;
                }), Codec.STRING.xmap(FurnaceUpgradeType::valueOf, FurnaceUpgradeType::toString).fieldOf("upgrade_type").orElse(FurnaceUpgradeType.UPGRADE_NONE).forGetter( (getter) -> {
                    return getter.neededUpgrade;
                }));

            return base.apply(records, AutomaticFurnaceSerializer::create);
        });

        private static AutomaticFurnaceRecipe create(String group, Ingredient input, ItemStack output, int cooktime, boolean requiresUpgrade, FurnaceUpgradeType upgradeType) {
            return new AutomaticFurnaceRecipe(group, input, output, cooktime, requiresUpgrade, upgradeType);
        }
        public Codec<AutomaticFurnaceRecipe> codec() {
            return CODEC;
        }

        @Override
        public AutomaticFurnaceRecipe fromNetwork(FriendlyByteBuf inputBuffer) {
            String s = inputBuffer.readUtf();
            Ingredient input = Ingredient.fromNetwork(inputBuffer);
            ItemStack output = inputBuffer.readItem();
            int time = inputBuffer.readVarInt();
            boolean needsUpgrade = inputBuffer.readBoolean();
            FurnaceUpgradeType upgradeType = (FurnaceUpgradeType)inputBuffer.readEnum(FurnaceUpgradeType.class);

            return new AutomaticFurnaceRecipe(s, input, output, time, needsUpgrade, upgradeType);
        }

        @Override
        public void toNetwork(FriendlyByteBuf outputBuffer, AutomaticFurnaceRecipe toOut) {
            outputBuffer.writeUtf(toOut.group);
            toOut.input.toNetwork(outputBuffer);
            outputBuffer.writeItem(toOut.output);
            outputBuffer.writeVarInt(toOut.cookTime);
            outputBuffer.writeBoolean(toOut.needsUgrade);
            outputBuffer.writeEnum(toOut.neededUpgrade);
        }
    }
}
