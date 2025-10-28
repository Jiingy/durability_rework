package net.jineric.durability_rework.access;

import net.minecraft.component.ComponentChanges;
import net.minecraft.data.recipe.ShapedRecipeJsonBuilder;

public interface ShapedRecipeJsonBuilderAccess {

    default ShapedRecipeJsonBuilder durability_rework$componentChanges(ComponentChanges componentChanges) {
        throw new RuntimeException("Failed to apply durability_rework$offerToWithItemStack");
    }
}
