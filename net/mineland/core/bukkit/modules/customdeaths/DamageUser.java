package net.mineland.core.bukkit.modules.customdeaths;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.Nullable;
import net.mineland.api.user.IUser;
import net.mineland.core.bukkit.modules.message.Message;
import net.mineland.core.bukkit.modules.myevents.EntityDamageByEntityMyEvent;
import net.mineland.core.bukkit.modules.user.GetterUser;
import net.mineland.core.bukkit.modules.user.User;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.projectiles.BlockProjectileSource;
import org.bukkit.projectiles.ProjectileSource;
import ua.govnojon.libs.bukkitutil.EntityUtil;
import ua.govnojon.libs.bukkitutil.PlayerUtil;

public class DamageUser implements GetterUser {
   private static long deprecatedTime = 6000L;
   private User user;
   private long lastClear;
   private List<DamageUser.Damage> history;

   public DamageUser(User user) {
      this.lastClear = System.currentTimeMillis() - deprecatedTime;
      this.history = new LinkedList();
      this.user = user;
   }

   public static DamageUser get(User user) {
      DamageUser damageUser = (DamageUser)user.getMetadata("damage_user");
      if (damageUser == null) {
         user.setMetadata("damage_user", damageUser = new DamageUser(user));
      }

      return damageUser;
   }

   public User getUser() {
      return this.user;
   }

   public void add(EntityDamageByEntityMyEvent event) {
      this.add(event, event.getDamager(), event.getCause().name());
   }

   public void add(EntityDamageEvent event) {
      DamageUser.Damage last = this.getLast();
      if (last != null && System.currentTimeMillis() - last.time < 6000L) {
         this.add(event, last.getDamager(), event.getCause().name());
      } else {
         this.add(event, (Entity)null, event.getCause().name());
      }

   }

   public void add(Event event, Entity damager, String cause) {
      long current = System.currentTimeMillis();
      this.checkIsDeprecated(current);
      this.history.add(new DamageUser.Damage(current, damager, cause, event));
   }

   private void checkIsDeprecated(long current) {
      if (current - this.lastClear > deprecatedTime) {
         this.history.removeIf((damage) -> {
            return damage.isDeprecated(current);
         });
      }

   }

   public List<DamageUser.Damage> getHistory() {
      this.checkIsDeprecated(System.currentTimeMillis());
      return this.history;
   }

   public DamageUser.Damage getLast() {
      return this.history.isEmpty() ? null : (DamageUser.Damage)this.history.get(this.history.size() - 1);
   }

   public void printDeathMessage(Collection<User> recievers) {
      DamageUser.Damage damage = this.getLast();
      Event event = damage.getEvent();
      Message deathMessage;
      if (event instanceof EntityDamageEvent) {
         EntityDamageEvent entityDamageEvent = (EntityDamageEvent)event;
         DamageCause cause = entityDamageEvent.getCause();
         Entity damager;
         ProjectileSource shooter;
         Projectile projectile;
         switch(cause) {
         case FALLING_BLOCK:
            if (event instanceof EntityDamageByEntityEvent) {
               damager = ((EntityDamageByEntityEvent)event).getDamager();
               if (damager != null && damager.getType() == EntityType.FALLING_BLOCK) {
                  FallingBlock fallingBlock = (FallingBlock)damager;
                  if (fallingBlock.getMaterial() == Material.ANVIL) {
                     deathMessage = new Message("death.anvil", new String[]{"{player}", this.user.getPlayer().getPlayer().getDisplayName()});
                     break;
                  }
               }
            }
         case PROJECTILE:
            if (event instanceof EntityDamageByEntityEvent) {
               damager = ((EntityDamageByEntityEvent)event).getDamager();
               if (damager instanceof Projectile) {
                  projectile = (Projectile)damager;
                  shooter = projectile.getShooter();
                  if (shooter instanceof Player) {
                     deathMessage = new Message("death.projectile.killer", new String[]{"{player}", this.user.getPlayer().getPlayer().getDisplayName(), "{killer}", ((Player)shooter).getDisplayName()});
                     break;
                  }
               }
            }
         case CONTACT:
         case CRAMMING:
         case DRAGON_BREATH:
         case DROWNING:
         case ENTITY_EXPLOSION:
            if (event instanceof EntityDamageByEntityEvent) {
               damager = ((EntityDamageByEntityEvent)event).getDamager();
               if (damager instanceof TNTPrimed) {
                  TNTPrimed tntPrimed = (TNTPrimed)damager;
                  Entity source = tntPrimed.getSource();
                  if (source instanceof Player) {
                     deathMessage = new Message("death.entity_explosion.killer", new String[]{"{player}", this.user.getPlayer().getPlayer().getDisplayName(), "{killer}", ((Player)source).getDisplayName()});
                     break;
                  }
               }
            }
         case BLOCK_EXPLOSION:
         case FALL:
         case FLY_INTO_WALL:
         case CUSTOM:
         case HOT_FLOOR:
         case FIRE:
         case FIRE_TICK:
         case SUFFOCATION:
         case MAGIC:
         case LAVA:
         case LIGHTNING:
         case POISON:
         case ENTITY_ATTACK:
         case VOID:
         case STARVATION:
         case THORNS:
         case WITHER:
         default:
            damager = damage.getDamager();
            if (damager != null) {
               int hp;
               if (damager instanceof Player) {
                  String damagerName = ((Player)damager).getDisplayName();
                  deathMessage = new Message("death." + cause + ".killer", new String[]{"{player}", this.user.getPlayer().getPlayer().getDisplayName(), "{killer}", damagerName});
                  Location pLoc = this.user.getPlayer().getLocation();
                  Location dLoc = damager.getLocation();
                  hp = -1;
                  if (pLoc.getWorld().equals(dLoc.getWorld())) {
                     hp = (int)this.user.getPlayer().getLocation().distanceSquared(dLoc);
                  }

                  int hp = (int)((Player)damager).getHealth();
                  this.user.sendMessage("death.entity_atack.killer.info", "{player}", ((Player)damager).getDisplayName(), "{hp}", String.valueOf(hp), "{range}", String.valueOf(hp));
               } else {
                  if (!(damager instanceof Projectile)) {
                     new Message("death." + cause + ".killer", new String[]{"{player}", this.user.getPlayer().getPlayer().getDisplayName(), "{killer}", this.getEntityKillerName(damager, this.getUser())});
                     recievers.forEach((users) -> {
                        users.sendMessage("death." + cause + ".killer", "{player}", this.user.getPlayer().getPlayer().getDisplayName(), "{killer}", this.getEntityKillerName(damager, users));
                     });
                     return;
                  }

                  projectile = (Projectile)damager;
                  shooter = projectile.getShooter();
                  if (shooter instanceof Entity) {
                     new Message("death." + cause + ".killer", new String[]{"{player}", this.user.getPlayer().getPlayer().getDisplayName(), "{killer}", this.getEntityKillerName((Entity)shooter, this.getUser())});
                     recievers.forEach((users) -> {
                        users.sendMessage("death." + cause + ".killer", "{player}", this.user.getPlayer().getPlayer().getDisplayName(), "{killer}", this.getEntityKillerName((Entity)shooter, users));
                     });
                     int range = (int)this.user.getPlayer().getPlayer().getLocation().distanceSquared(((Entity)shooter).getLocation());
                     hp = (int)((LivingEntity)shooter).getHealth();
                     Player playerDamager = PlayerUtil.getDamager((Entity)shooter);
                     String displayName;
                     if (playerDamager != null) {
                        displayName = playerDamager.getDisplayName();
                     } else {
                        displayName = "None";
                     }

                     this.user.sendMessage("death.entity_atack.killer.info", "{player}", displayName, "{hp}", String.valueOf(hp), "{range}", String.valueOf(range));
                     return;
                  }

                  if (shooter instanceof BlockProjectileSource) {
                     Material block = ((BlockProjectileSource)shooter).getBlock().getType();
                     deathMessage = new Message("death." + cause + ".killer", new String[]{"{player}", this.user.getPlayer().getPlayer().getDisplayName(), "{killer}", block.toString()});
                  } else {
                     deathMessage = new Message("death." + cause + ".killer", new String[]{"{player}", this.user.getPlayer().getPlayer().getDisplayName(), "{killer}", shooter.toString()});
                  }
               }
            } else {
               deathMessage = new Message("death." + cause, new String[]{"{player}", this.user.getPlayer().getPlayer().getDisplayName()});
            }
         }
      } else {
         deathMessage = new Message("смерть_" + damage.getCause(), new String[]{"%player%", this.user.getPlayer().getPlayer().getDisplayName()});
      }

      deathMessage.broadcast(recievers);
      this.user.getPlayer().setFireTicks(0);
      PlayerUtil.sendTitleKey(this.user, "вы.погибли", "{cause}", deathMessage.translate(this.user.getLang()));
   }

   private String getEntityKillerName(Entity entity, User user) {
      if (entity instanceof Player) {
         return ((Player)entity).getDisplayName();
      } else {
         String entityType = "§7" + Message.getMessage((IUser)user, "entity." + entity.getType().name()) + "§r";
         return entity.getCustomName() == null ? entityType : Message.getMessage((IUser)user, "entity.с_кастомным_именем", "{entity}", entityType, "{name}", entity.getCustomName().startsWith("lang:") ? Message.getMessage((IUser)user, entity.getCustomName().substring(5)) : entity.getCustomName());
      }
   }

   public class Damage {
      private long time;
      private Entity damager;
      private Entity damagerOriginal;
      private Player damagerPlayer;
      private String cause;
      private Event event;

      private Damage(long time, Entity damager, String cause, Event event) {
         this.time = time;
         this.damager = damager;
         this.cause = cause;
         if (damager != null) {
            this.damagerOriginal = EntityUtil.getDamager(damager);
            this.damagerPlayer = PlayerUtil.getDamager(damager);
         }

         this.event = event;
      }

      private boolean isDeprecated(long current) {
         return current - this.time > DamageUser.deprecatedTime;
      }

      @Nullable
      public Entity getDamager() {
         return this.damager;
      }

      public Event getEvent() {
         return this.event;
      }

      public boolean hasDamager() {
         return this.damager != null;
      }

      @Nullable
      public Entity getDamagerOriginal() {
         return this.damagerOriginal;
      }

      public String getCause() {
         return this.cause;
      }

      @Nullable
      public Player getDamagerPlayer() {
         return this.damagerPlayer;
      }

      // $FF: synthetic method
      Damage(long x1, Entity x2, String x3, Event x4, Object x5) {
         this(x1, x2, x3, x4);
      }
   }
}
