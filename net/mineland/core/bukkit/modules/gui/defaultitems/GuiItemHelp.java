package net.mineland.core.bukkit.modules.gui.defaultitems;

import net.mineland.api.user.IUser;
import net.mineland.core.bukkit.modules.gui.GuiMenu;
import net.mineland.core.bukkit.modules.message.Message;
import org.bukkit.Material;

public class GuiItemHelp extends GuiItemText {
   public GuiItemHelp(GuiMenu guiMenu, String keyMessage, String... replaced) {
      this(guiMenu, 5, guiMenu.getSize() / 9, keyMessage, replaced);
   }

   public GuiItemHelp(GuiMenu guiMenu, int x, int y, String keyMessage, String... replaced) {
      super(guiMenu, x, y, Material.SIGN, (short)0, 1);
      this.setText(Message.getMessage((IUser)this.getUser(), keyMessage, replaced));
   }
}
