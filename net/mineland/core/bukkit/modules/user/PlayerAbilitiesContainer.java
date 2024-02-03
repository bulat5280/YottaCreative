package net.mineland.core.bukkit.modules.user;

import net.minecraft.server.v1_12_R1.PlayerAbilities;

public class PlayerAbilitiesContainer extends PlayerAbilities {
   private User user;

   public PlayerAbilitiesContainer(PlayerAbilities pa, User user) {
      this.user = user;
      this.isInvulnerable = pa.isInvulnerable;
      this.isFlying = pa.isFlying;
      this.canFly = pa.canFly;
      this.canInstantlyBuild = pa.canInstantlyBuild;
      this.mayBuild = pa.mayBuild;
      this.flySpeed = pa.flySpeed;
      this.walkSpeed = pa.walkSpeed;
   }

   public User getUser() {
      return this.user;
   }
}
