package net.Lucas.potionflasks.item.flask;

import net.Lucas.potionflasks.item.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MediumFlaskPotionItem extends PotionItem {
    public MediumFlaskPotionItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand pHand) {
        ItemStack flaskStack = player.getItemInHand(pHand);
        ItemStack potionStack = null;
        if (flaskStack.getDamageValue() != 0) {
            for (ItemStack itemStack : player.getInventory().items) {
                //searches left to right starting with hotbar then top left inventory slot
                if (itemStack.getItem() == Items.POTION) {
                    if (PotionUtils.getPotion(flaskStack).getEffects() == PotionUtils.getPotion(itemStack).getEffects()) {
                        flaskStack.setDamageValue(flaskStack.getDamageValue() - 1);
                        potionStack = itemStack;
                        player.getInventory().removeItem(itemStack);
                        player.getInventory().add(Items.GLASS_BOTTLE.getDefaultInstance());
                        break;
                    }
                }
            }
        }
        if (potionStack != null) {
            return InteractionResultHolder.consume(flaskStack);
        } else return ItemUtils.startUsingInstantly(level, player, pHand);
    }

    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pEntityLiving) {
        InteractionHand pHand;
        Player player = pEntityLiving instanceof Player ? (Player)pEntityLiving : null;
        if (player.getItemInHand(InteractionHand.MAIN_HAND) == pStack) {
            pHand = InteractionHand.MAIN_HAND;
        } else {
            pHand = InteractionHand.OFF_HAND;
        }
        if (player instanceof ServerPlayer) {
            CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayer)player, pStack);
        }

        if (!pLevel.isClientSide) {
            for(MobEffectInstance mobeffectinstance : PotionUtils.getMobEffects(pStack)) {
                if (mobeffectinstance.getEffect().isInstantenous()) {
                    mobeffectinstance.getEffect().applyInstantenousEffect(player, player, pEntityLiving, mobeffectinstance.getAmplifier(), 1.0D);
                } else {
                    pEntityLiving.addEffect(new MobEffectInstance(mobeffectinstance));
                }
            }
        }

        if (player != null) {
            player.awardStat(Stats.ITEM_USED.get(this));
            if (!player.getAbilities().instabuild) {
                pStack.setDamageValue(pStack.getDamageValue()+1);
                if (pStack.getDamageValue() == pStack.getMaxDamage()) {
                    pStack.shrink(1);
                }
            }
        }

        if (player == null || !player.getAbilities().instabuild) {
            if (pStack.isEmpty()) {
                if (pHand == InteractionHand.MAIN_HAND) {
                    player.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(ModItems.MEDIUM_FLASK.get()));
                } else {
                    player.setItemSlot(EquipmentSlot.OFFHAND, new ItemStack(ModItems.MEDIUM_FLASK.get()));
                }
                return new ItemStack(ModItems.MEDIUM_FLASK.get());
            }
        }

        pEntityLiving.gameEvent(GameEvent.DRINK);
        return pStack;
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        //empty the flask by using it on a water cauldron (works with any water level)
        InteractionHand pHand;
        Level level = pContext.getLevel();
        Player player = pContext.getPlayer();
        ItemStack itemStack = pContext.getItemInHand();
        if (player.getItemInHand(InteractionHand.MAIN_HAND) == itemStack) {
            pHand = InteractionHand.MAIN_HAND;
        } else {
            pHand = InteractionHand.OFF_HAND;
        }
        Block blockClicked = level.getBlockState(pContext.getClickedPos()).getBlock();
        if (blockClicked == Blocks.WATER_CAULDRON) {
            if (pHand == InteractionHand.MAIN_HAND) {
                player.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(ModItems.MEDIUM_FLASK.get()));
            } else {
                player.setItemSlot(EquipmentSlot.OFFHAND, new ItemStack(ModItems.MEDIUM_FLASK.get()));
            }
            level.playSound((Player)null, pContext.getClickedPos(), SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
            return InteractionResult.sidedSuccess(level.isClientSide);
        } else return InteractionResult.PASS;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pFlag) {
        final ChatFormatting GREY_TEXT = ChatFormatting.GRAY;
        ChatFormatting VARIABLE_TEXT = ChatFormatting.DARK_GREEN;
        int currentCharges = pStack.getDamageValue();
        int maxCharges = pStack.getMaxDamage();
        int remainingCharges = maxCharges-currentCharges;
        if (pStack.getDamageValue() == 3) {
            VARIABLE_TEXT = ChatFormatting.YELLOW;
        }
        if (pStack.getDamageValue() > 3) {
            VARIABLE_TEXT = ChatFormatting.RED;
        }

        Component flask_doses_remaining = Component.translatable(Util.makeDescriptionId("tooltip", new ResourceLocation
                ("potionflasks:generic.flask.doses_remaining"))).withStyle(GREY_TEXT);
        pTooltipComponents.add(flask_doses_remaining);

        Component totem_current_charges = Component.literal(" "+remainingCharges+" / "+maxCharges).withStyle(VARIABLE_TEXT);
        pTooltipComponents.add(totem_current_charges);
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pFlag);
    }
}