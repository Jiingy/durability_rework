package net.jineric.durability_rework.mixin;

import net.jineric.durability_rework.access.ShapedRecipeJsonBuilderAccess;
import net.minecraft.component.ComponentChanges;
import net.minecraft.data.recipe.CraftingRecipeJsonBuilder;
import net.minecraft.data.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RawShapedRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryEntryLookup;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ShapedRecipeJsonBuilder.class)
public abstract class ShapedRecipeJsonBuilderMixin implements CraftingRecipeJsonBuilder, ShapedRecipeJsonBuilderAccess {
    @Shadow @Final private int count;
    @Shadow @Final private Item output;
    @Unique private final ItemStack outputStack = ItemStack.EMPTY;
    @Unique private ComponentChanges componentChanges = ComponentChanges.EMPTY;

    @Override
    public ShapedRecipeJsonBuilder durability_rework$componentChanges(ComponentChanges componentChanges) {
        this.componentChanges = componentChanges;
//        this.outputStack = new ItemStack(this.outputStack.getRegistryEntry(), this.count, componentChanges);
        return ((ShapedRecipeJsonBuilder)(Object) this);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void changeOutputToStack(RegistryEntryLookup<Item> registryLookup, RecipeCategory category, ItemConvertible output, int count, CallbackInfo ci) {
//        this.outputStack = output.asItem().getDefaultStack();
    }

//    @ModifyReturnValue(method = "getOutputItem", at = @At("RETURN"))
//    private Item getItemFromStack(Item original) {
//        return outputStack.getItem();
//    }

    @ModifyArg(
            method = "offerTo",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/recipe/ShapedRecipe;<init>(Ljava/lang/String;Lnet/minecraft/recipe/book/CraftingRecipeCategory;Lnet/minecraft/recipe/RawShapedRecipe;Lnet/minecraft/item/ItemStack;Z)V"
            ),
            index = 3
    )
    private ItemStack applyComponentChanges(String group, CraftingRecipeCategory category, RawShapedRecipe raw, ItemStack original, boolean showNotification) {
        if (!componentChanges.isEmpty()) {
            original.applyUnvalidatedChanges(this.componentChanges);
        }
        return original;
    }
}
