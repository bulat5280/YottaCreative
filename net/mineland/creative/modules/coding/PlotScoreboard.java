package net.mineland.creative.modules.coding;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import net.mineland.core.bukkit.module.Module;
import net.mineland.core.bukkit.modules.scoreboard.ModuleScoreboard;
import net.mineland.core.bukkit.modules.user.User;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class PlotScoreboard {
   private static ModuleScoreboard moduleScoreboard = (ModuleScoreboard)Module.getInstance(ModuleScoreboard.class);
   private String name;
   private String displayName;
   private Map<String, Integer> scores = Maps.newHashMap();
   private Set<User> users = Sets.newHashSet();

   public PlotScoreboard(String name) {
      this.name = this.displayName = name;
   }

   public String getName() {
      return this.name;
   }

   public String getDisplayName() {
      return this.displayName;
   }

   public void setDisplayName(String displayName) {
      this.displayName = displayName;
   }

   public Set<User> getUsers() {
      return this.users;
   }

   public void show(User user) {
      PlotScoreboard plotScoreboard = (PlotScoreboard)user.getMetadata("plot_scoreboard");
      if (plotScoreboard != null) {
         plotScoreboard.hide(user);
      }

      Objective objective = this.getObjective(user);
      String set = this.displayName;
      if (set.length() > 32) {
         set = this.displayName.substring(0, 32);
      }

      objective.setDisplayName(set);
      this.scores.forEach((tag, value) -> {
         if (tag.length() > 40) {
            tag = tag.substring(0, 40);
         }

         objective.getScore(tag).setScore(value);
      });
      this.users.add(user);
      user.setMetadata("plot_scoreboard", this);
   }

   private Objective getObjective(User user) {
      Scoreboard scoreboard = user.getPlayer().getScoreboard();
      Objective objective = scoreboard.getObjective(DisplaySlot.SIDEBAR);
      if (objective != null && objective.getName().equals("Creative")) {
         return objective;
      } else {
         if (objective != null) {
            objective.unregister();
         }

         objective = scoreboard.getObjective("Creative");
         if (objective != null) {
            objective.unregister();
         }

         objective = scoreboard.registerNewObjective("Creative", "dummy");
         objective.setDisplaySlot(DisplaySlot.SIDEBAR);
         return objective;
      }
   }

   public void hide(User user) {
      this.getObjective(user);
      Scoreboard scoreboard = user.getPlayer().getScoreboard();
      this.scores.forEach((tag, value) -> {
         if (tag.length() > 40) {
            tag = tag.substring(0, 40);
         }

         scoreboard.resetScores(tag);
      });
      this.users.remove(user);
      user.removeMetadata("plot_scoreboard");
   }

   public void setScore(String name, int value) {
      if (name != null) {
         String tag = name.length() > 40 ? name.substring(0, 40) : name;
         this.users.forEach((user) -> {
            Objective objective = this.getObjective(user);
            objective.getScore(tag).setScore(value);
         });
         this.scores.put(name, value);
      }
   }

   public void removeScore(String name) {
      if (name != null) {
         String tag = name.length() > 40 ? name.substring(0, 40) : name;
         this.users.forEach((user) -> {
            this.getObjective(user);
            Scoreboard scoreboard = user.getPlayer().getScoreboard();
            scoreboard.resetScores(tag);
         });
         this.scores.remove(name);
      }
   }

   public void removeScore(int score) {
      List<String> collect = (List)this.scores.entrySet().stream().filter((entry) -> {
         return (Integer)entry.getValue() == score;
      }).map(Entry::getKey).collect(Collectors.toList());
      collect.forEach(this::removeScore);
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         PlotScoreboard that = (PlotScoreboard)o;
         return Objects.equals(this.name, that.name) && Objects.equals(this.scores, that.scores) && Objects.equals(this.users, that.users);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return Objects.hash(new Object[]{this.name, this.scores, this.users});
   }
}
