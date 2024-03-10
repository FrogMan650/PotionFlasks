package net.Lucas.potionflasks.item;

import net.Lucas.potionflasks.PotionFlasks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, PotionFlasks.MOD_ID);

    public static final RegistryObject<CreativeModeTab> FLASK_TAB = CREATIVE_MODE_TABS.register("flask_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.SMALL_FLASK.get()))
                    .title(Component.translatable("creativetab.potionflasks.flask_tab"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModItems.SMALL_FLASK.get());
                        pOutput.accept(ModItems.SMALL_FLASK_POTION.get());
                        pOutput.accept(ModItems.MEDIUM_FLASK.get());
                        pOutput.accept(ModItems.MEDIUM_FLASK_POTION.get());
                        pOutput.accept(ModItems.LARGE_FLASK.get());
                        pOutput.accept(ModItems.LARGE_FLASK_POTION.get());
                        pOutput.accept(ModItems.HOGLIN_LEATHER.get());
                    }).build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
