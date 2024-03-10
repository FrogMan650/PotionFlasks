package net.Lucas.potionflasks.item.flask;

import net.Lucas.potionflasks.item.ModItems;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BottleItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.Level;

public class MediumFlaskItem extends BottleItem {
    public MediumFlaskItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand pHand) {
        ItemStack flaskStack = player.getItemInHand(pHand);
        int flaskSlot = player.getInventory().findSlotMatchingItem(flaskStack);
        ItemStack potionStack = null;
        for (ItemStack itemStack : player.getInventory().items) {
            //searches left to right starting with hotbar then top left inventory slot
            if (itemStack.getItem() == Items.POTION) {
                potionStack = itemStack;
                player.getInventory().removeItem(itemStack);
                player.getInventory().add(Items.GLASS_BOTTLE.getDefaultInstance());
                break;
            }
        }
        if (potionStack != null) {
            player.getInventory().removeItem(flaskStack);
            ItemStack newPotionStack = assemblePotion(potionStack);
            player.getInventory().add(flaskSlot, newPotionStack);
            player.getItemInHand(pHand).setDamageValue(player.getItemInHand(pHand).getMaxDamage()-1);
            return InteractionResultHolder.consume(flaskStack);
        } else return InteractionResultHolder.fail(flaskStack);
    }

    public ItemStack assemblePotion(ItemStack potionStack) {
        ItemStack newFlaskStack = new ItemStack(ModItems.MEDIUM_FLASK_POTION.get());
        PotionUtils.setPotion(newFlaskStack, PotionUtils.getPotion(potionStack));
        PotionUtils.setCustomEffects(newFlaskStack, PotionUtils.getCustomEffects(potionStack));
        return newFlaskStack;
    }
}