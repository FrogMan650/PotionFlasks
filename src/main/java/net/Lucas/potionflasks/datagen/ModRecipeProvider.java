package net.Lucas.potionflasks.datagen;

import net.Lucas.potionflasks.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pRecipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.SMALL_FLASK.get())
                .pattern("B B")
                .pattern("SAS")
                .pattern(" S ")
                .define('A', Items.EXPERIENCE_BOTTLE)
                .define('B', Items.STRING)
                .define('S', Items.RABBIT_HIDE)
                .unlockedBy(getHasName(Items.GLASS_BOTTLE), has(Items.GLASS_BOTTLE))
                .save(pRecipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.MEDIUM_FLASK.get())
                .pattern("BDB")
                .pattern("SAS")
                .pattern(" S ")
                .define('A', ModItems.SMALL_FLASK.get())
                .define('B', Items.STRING)
                .define('D', Items.HEART_OF_THE_SEA)
                .define('S', Items.LEATHER)
                .unlockedBy(getHasName(ModItems.SMALL_FLASK.get()), has(ModItems.SMALL_FLASK.get()))
                .save(pRecipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.LARGE_FLASK.get())
                .pattern("BDB")
                .pattern("SAS")
                .pattern(" S ")
                .define('A', ModItems.MEDIUM_FLASK.get())
                .define('B', Items.STRING)
                .define('D', Items.NETHER_STAR)
                .define('S', ModItems.HOGLIN_LEATHER.get())
                .unlockedBy(getHasName(ModItems.MEDIUM_FLASK.get()), has(ModItems.MEDIUM_FLASK.get()))
                .save(pRecipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.LEATHER, 2)
                .requires(ModItems.HOGLIN_LEATHER.get())
                .unlockedBy(getHasName(ModItems.HOGLIN_LEATHER.get()), has(ModItems.HOGLIN_LEATHER.get()))
                .save(pRecipeOutput, "leather_from_hoglin_leather");
    }
}
