package net.mineland.creative.modules.creative.commands;

import java.util.Collection;
import net.mineland.core.bukkit.module.Module;
import net.mineland.core.bukkit.modules.command.Command;
import net.mineland.core.bukkit.modules.command.CommandEvent;
import net.mineland.core.bukkit.modules.message.Message;
import net.mineland.core.bukkit.modules.mysql.SQL;
import net.mineland.core.bukkit.modules.user.User;
import net.mineland.creative.modules.coding.activators.player.PlayerVoteActivator;
import net.mineland.creative.modules.creative.ModuleCreative;
import net.mineland.creative.modules.creative.Plot;
import org.bukkit.event.Event;

public class CommandVote extends Command {
   private static ModuleCreative moduleCreative = (ModuleCreative)Module.getInstance(ModuleCreative.class);

   public CommandVote() {
      super("like", (String)null, "vote");
   }

   public boolean execute(CommandEvent event) {
      event.checkIsConsole();
      String[] args = event.getArgs();
      User user = event.getUser();
      Plot plot = moduleCreative.getPlotManager().getCurrentPlot(user);
      if (plot == null) {
         user.sendMessage("creative.vote.not_in_world");
         return true;
      } else if (plot.getVotes().contains(user.getName())) {
         user.sendMessage("creative.vote.already_voted");
         return true;
      } else {
         (new Message("creative.vote.success_vote", new String[]{"{player}", user.getPlayer().getDisplayName()})).broadcast((Collection)plot.getOnlinePlayers());
         if (!plot.isOwner(user)) {
            User owner = plot.getOwner();
            if (owner != null) {
               owner.sendMessage("creative.vote.reward");
            }
         }

         SQL.async((create) -> {
            create.execute("INSERT INTO `creative_votes`(`plot_id`, `player_id`, `last_vote`) VALUES (?,?,CURRENT_TIMESTAMP)", plot.getId(), user.getName());
            plot.updateVotes();
         });
         switch(plot.getPlotMode()) {
         case PLAYING:
            PlayerVoteActivator.Event gameEvent = new PlayerVoteActivator.Event(user, plot, (Event)null);
            gameEvent.callEvent();
            gameEvent.handle();
         default:
            return true;
         }
      }
   }
}
