package net.Lucas.potionflasks.datagen;

import net.Lucas.potionflasks.PotionFlasks;
import net.Lucas.potionflasks.item.ModItems;
import net.Lucas.potionflasks.loot.AddItemModifier;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.LootTableIdCondition;

public class ModGlobalLootModifiersProvider extends GlobalLootModifierProvider {
    public ModGlobalLootModifiersProvider(PackOutput output) {
        super(output, PotionFlasks.MOD_ID);
    }

    @Override
    protected void start() {
        add("hoglin_leather_from_hoglin", new AddItemModifier(new LootItemCondition[]{
                new LootTableIdCondition.Builder(new ResourceLocation("entities/hoglin")).build() },
                ModItems.HOGLIN_LEATHER.get()));
    }
}
