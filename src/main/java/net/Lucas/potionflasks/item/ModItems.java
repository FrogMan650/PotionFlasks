package net.Lucas.potionflasks.item;

import net.Lucas.potionflasks.PotionFlasks;
import net.Lucas.potionflasks.item.flask.*;
import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, PotionFlasks.MOD_ID);

    //https://www.reddit.com/r/Minecraft/comments/c0z2jn/color_guide_youre_welcome/
    //Common = white text
    //Uncommon = yellow text
    //Rare = aqua text
    //Epic = light_purple text
    //when enchanted they go up a rarity tier

    public static final RegistryObject<Item> SMALL_FLASK = ITEMS.register("small_flask", () -> new SmallFlaskItem(new Item.Properties().rarity(Rarity.COMMON).stacksTo(1)));
    public static final RegistryObject<Item> SMALL_FLASK_POTION = ITEMS.register("small_flask_potion", () -> new SmallFlaskPotionItem(new Item.Properties().durability(2).rarity(Rarity.COMMON)));
    public static final RegistryObject<Item> MEDIUM_FLASK = ITEMS.register("medium_flask", () -> new MediumFlaskItem(new Item.Properties().rarity(Rarity.UNCOMMON).stacksTo(1)));
    public static final RegistryObject<Item> MEDIUM_FLASK_POTION = ITEMS.register("medium_flask_potion", () -> new MediumFlaskPotionItem(new Item.Properties().durability(5).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> LARGE_FLASK = ITEMS.register("large_flask", () -> new LargeFlaskItem(new Item.Properties().rarity(Rarity.RARE).stacksTo(1).fireResistant()));
    public static final RegistryObject<Item> LARGE_FLASK_POTION = ITEMS.register("large_flask_potion", () -> new LargeFlaskPotionItem(new Item.Properties().durability(10).rarity(Rarity.RARE).fireResistant()));
    public static final RegistryObject<Item> HOGLIN_LEATHER = ITEMS.register("hoglin_leather", () -> new Item(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
