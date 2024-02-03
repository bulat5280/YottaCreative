package net.mineland.core.bukkit.modules.myevents;

import com.destroystokyo.paper.event.player.PlayerUseUnknownEntityEvent;
import net.mineland.core.bukkit.modules.user.User;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Event.Result;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import ua.govnojon.libs.bukkitutil.ItemStackUtil;

public class UserInteractMyEvent extends UserEvent implements Cancellable {
   private static final HandlerList handlers = new HandlerList();
   private Cancellable cancellable;
   private EquipmentSlot hand;
   private int useEntityId = -1;
   private ItemStack useItem;
   private Block useBlock;
   private UserInteractMyEvent.Action action;

   public UserInteractMyEvent(PlayerInteractEntityEvent event) {
      super(User.getUser(event.getPlayer()));
      this.cancellable = event;
      this.hand = event.getHand() == null ? EquipmentSlot.HAND : event.getHand();
      this.useEntityId = event.getRightClicked().getEntityId();
      this.action = UserInteractMyEvent.Action.RIGHT_ENTITY;
      this.initItem();
   }

   public UserInteractMyEvent(EntityDamageByEntityEvent event) {
      super(User.getUser((CommandSender)event.getDamager()));
      this.cancellable = event;
      this.hand = EquipmentSlot.HAND;
      this.useEntityId = event.getEntity().getEntityId();
      this.action = UserInteractMyEvent.Action.LEFT_ENTITY;
      this.initItem();
   }

   public UserInteractMyEvent(PlayerInteractEvent event) {
      super(User.getUser(event.getPlayer()));
      this.cancellable = event;
      this.hand = event.getHand() == null ? EquipmentSlot.HAND : event.getHand();
      this.useBlock = event.getClickedBlock();
      switch(event.getAction()) {
      case RIGHT_CLICK_BLOCK:
         this.action = UserInteractMyEvent.Action.RIGHT_BLOCK;
         break;
      case LEFT_CLICK_BLOCK:
         this.action = UserInteractMyEvent.Action.LEFT_BLOCK;
         break;
      case RIGHT_CLICK_AIR:
         this.action = UserInteractMyEvent.Action.RIGHT_AIR;
         break;
      case LEFT_CLICK_AIR:
         this.action = UserInteractMyEvent.Action.LEFT_AIR;
      }

      this.initItem();
   }

   public UserInteractMyEvent(PlayerUseUnknownEntityEvent event) {
      super(User.getUser(event.getPlayer()));
      this.useEntityId = event.getEntityId();
      this.action = event.isAttack() ? UserInteractMyEvent.Action.LEFT_ENTITY : UserInteractMyEvent.Action.RIGHT_ENTITY;
      this.hand = event.getHand() == null ? EquipmentSlot.HAND : event.getHand();
      if (event.isAttack()) {
         this.hand = EquipmentSlot.HAND;
      }

      this.initItem();
   }

   public static HandlerList getHandlerList() {
      return handlers;
   }

   private void initItem() {
      this.useItem = this.hand.equals(EquipmentSlot.HAND) ? this.getPlayer().getInventory().getItemInMainHand() : this.getPlayer().getInventory().getItemInOffHand();
      if (ItemStackUtil.isNullOrAir(this.useItem)) {
         this.useItem = null;
      }

   }

   public boolean hasItem() {
      return this.useItem != null;
   }

   public boolean hasEntity() {
      return this.useEntityId != -1;
   }

   public boolean hasBlock() {
      return this.useBlock != null;
   }

   public Block getBlock() {
      return this.useBlock;
   }

   public EquipmentSlot getHand() {
      return this.hand;
   }

   public Cancellable getOriginalEvent() {
      return this.cancellable;
   }

   public int getEntityID() {
      return this.useEntityId;
   }

   public ItemStack getItem() {
      return this.useItem;
   }

   public HandlerList getHandlers() {
      return handlers;
   }

   public boolean isCancelled() {
      return this.cancellable != null && this.cancellable.isCancelled();
   }

   public void setCancelled(boolean cancel) {
      if (this.cancellable != null) {
         this.cancellable.setCancelled(cancel);
      }

   }

   public void setCancelledBlockUse(boolean cancel) {
      if (this.cancellable != null) {
         if (this.cancellable instanceof PlayerInteractEvent) {
            ((PlayerInteractEvent)this.cancellable).setUseInteractedBlock(cancel ? Result.DENY : Result.ALLOW);
         } else {
            this.cancellable.setCancelled(true);
         }
      }

   }

   public boolean isRight() {
      switch(this.action) {
      case RIGHT_AIR:
      case RIGHT_BLOCK:
      case RIGHT_ENTITY:
         return true;
      default:
         return false;
      }
   }

   public boolean isLeft() {
      switch(this.action) {
      case LEFT_AIR:
      case LEFT_BLOCK:
      case LEFT_ENTITY:
         return true;
      default:
         return false;
      }
   }

   public void setCancelledItemUse(boolean cancel) {
      if (this.cancellable != null) {
         if (this.cancellable instanceof PlayerInteractEvent) {
            ((PlayerInteractEvent)this.cancellable).setUseItemInHand(cancel ? Result.DENY : Result.ALLOW);
         } else {
            this.cancellable.setCancelled(true);
         }
      }

   }

   public UserInteractMyEvent.Action getAction() {
      return this.action;
   }

   public boolean isMainHand() {
      return this.hand.equals(EquipmentSlot.HAND);
   }

   public static enum Action {
      RIGHT_BLOCK,
      LEFT_BLOCK,
      RIGHT_AIR,
      LEFT_AIR,
      LEFT_ENTITY,
      RIGHT_ENTITY;
   }
}
