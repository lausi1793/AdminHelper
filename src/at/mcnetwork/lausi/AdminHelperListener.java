package at.mcnetwork.lausi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import at.mcnetwork.lausi.AdvancedAdminHelper;

public class AdminHelperListener implements CommandExecutor, Listener {
	

	public boolean globalmute = false;
	private AdvancedAdminHelper plugin;
	public List<String> muted = new ArrayList();
	public static HashMap<String, String> spammessage = new HashMap();
	
	public AdminHelperListener(AdvancedAdminHelper instance){
		this.plugin = instance;
		this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	  public void onChat(AsyncPlayerChatEvent e) {
	    if ((this.globalmute) && (!e.getPlayer().hasPermission("adminhelper.globalmute.bypass"))) {
	      e.getPlayer().sendMessage(this.plugin.getConfig().getString("messages.globalmuteenabled").replaceAll("&", "§"));
	      e.setCancelled(true);
	    }

	    if (this.muted.contains(e.getPlayer().getName())) {
	      e.getPlayer().sendMessage(this.plugin.getConfig().getString("messages.globalmuteenabled").replaceAll("&", "§"));
	      e.setCancelled(true);
	    }
	  }
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onAsyncPlayerChat(AsyncPlayerChatEvent event)
	  {
		if(this.plugin.getConfig().getBoolean("adminhelper.seventoslash")){
		if (event.getMessage().startsWith("7")) {
			  Player p = event.getPlayer();
		      p.chat(event.getMessage().replaceFirst("7", "/"));
		      event.setCancelled(true);
		    }
		}
		
		if ((!event.getPlayer().hasPermission("adminhelper.chat.bypass"))){
		      if (spammessage.containsKey(event.getPlayer().getName())) {
		        if (((String)spammessage.get(event.getPlayer().getName())).equalsIgnoreCase(event.getMessage())) {
		          event.setCancelled(true);
		          event.getPlayer().sendMessage(this.plugin.getConfig().getString("messages.dontspam").replaceAll("&", "§"));
		        } else {
		          spammessage.put(event.getPlayer().getName(), event.getMessage());
		        }
		      }
		      else spammessage.put(event.getPlayer().getName(), event.getMessage());
		  }
		
		
		
	    char[] message = event.getMessage().toCharArray();
	    if ((!event.getPlayer().hasPermission("adminhelper.chat.bypass"))) {
	      double totalCaps = 0.0D;
	      int maxcaps = 51;
	      if (maxcaps >= 100) {
	        maxcaps = 100;
	      }
	      if (maxcaps <= 0) {
	        maxcaps = 5;
	      }
	      for (int i = 0; i < message.length; i++) {
	        if (Character.isUpperCase(message[i])) {
	          totalCaps += 1.0D;
	        }
	      }
	      double percent = 100.0D * (totalCaps / message.length);
	      if (percent >= maxcaps) {
	        event.setCancelled(true);
	        event.getPlayer().sendMessage(this.plugin.getConfig().getString("messages.capslock").replaceAll("&", "§"));
	      }
	    }
	  }
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	  {
		if ((cmd.getName().equalsIgnoreCase("globalmute")) && ((sender instanceof Player)) && (sender.hasPermission("adminhelper.globalmute"))) {
			      if (this.globalmute) {
			        this.globalmute = false;
			        Bukkit.broadcastMessage(this.plugin.getConfig().getString("messages.globalmutedisabled").replaceAll("&", "§"));
			        return true;
			      }

			      if (!this.globalmute) {
			        this.globalmute = true;
			        Bukkit.broadcastMessage(this.plugin.getConfig().getString("messages.globalmuteenabled").replaceAll("&", "§"));
			        return true;
			      }
		}
	    
	    return true;

	  }
}

