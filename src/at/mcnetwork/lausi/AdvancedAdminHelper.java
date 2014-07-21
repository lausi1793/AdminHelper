package at.mcnetwork.lausi;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Set;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class AdvancedAdminHelper extends JavaPlugin implements Listener{
	
	String version;
	String name;
	static Runtime runtime = Runtime.getRuntime();
	
	public void onEnable() {
		version = getDescription().getVersion();
		name = getDescription().getName();
		try {
			Metrics metrics = new Metrics(this); metrics.start();
			} catch (IOException e) { // Failed to submit the stats
			System.out.println("Error Submitting stats!");
			}

		if(getConfig().getBoolean("updateNotification")){
		try {
			new Updater(this, 82647, "http://dev.bukkit.org/bukkit-plugins/hideandcustomplugins/", "SearchForUpdates").search();
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
		}
		AdminHelperListener globalmute = new AdminHelperListener(this);
		getCommand("globalmute").setExecutor(globalmute);
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
		this.reloadConfig();
		loadConfig();
		Logger.getLogger("Minecraft").info("[" + name + "] Version: " + version + " Plugin has been activated successfully.");
	}
	
	public void onDisable() {
		this.saveConfig();
		Logger.getLogger("Minecraft").info("[" + name + "] Version: " + version + " Plugin was disabled successfully.");
	}
	
	private void loadConfig() {
		FileConfiguration cfg = this.getConfig();
		cfg.options().copyDefaults(true);
		this.saveConfig();
		Logger.getLogger("Minecraft").info("[" + name + "] Version: " + version+ " Successfully reloaded config.yml");
	}
	
	public static double usedMemory() {
	    double maxmemory = runtime.maxMemory();
	    double usedmemory = runtime.totalMemory();
	    double maxm = maxmemory / 1048576.0D;
	    maxm /= 100.0D;
	    double usedm = usedmemory / 1048576.0D;
	    double auslastung = usedm / maxm;
	    auslastung *= 100.0D;
	    auslastung = Math.round(auslastung);
	    auslastung /= 100.0D;
	    return auslastung;
	  }
	
	public static ChatColor ramCheck() {
	    double raminpro = usedMemory();
	    if (raminpro <= 50.0D)
	      return ChatColor.GREEN;
	    if ((raminpro >= 50.100000000000001D) && (raminpro <= 80.0D))
	      return ChatColor.YELLOW;
	    if ((raminpro >= 80.099999999999994D) && (raminpro <= 100.0D)) {
	      return ChatColor.DARK_RED;
	    }
	    return ChatColor.WHITE;
	  }
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	  {
	    Player p = null;
	    if ((sender instanceof Player)) {
	      p = (Player)sender;
	    }
	
	    if (cmd.getName().equalsIgnoreCase("ah"))
	    {
	      if (p != null)
	      {
	    	  if (p.hasPermission("adminhelper.info")) {
	    	  if(args.length == 0){
	        p.sendMessage("§e==========[ AdminHelper Help Version: " + ChatColor.YELLOW + version + " §e]==========");
        	p.sendMessage(ChatColor.GREEN + "Hy " + p.getDisplayName() + ChatColor.GREEN + "! Here are all the commands of AdminHelper");
        	p.sendMessage("§9/help AdminHelper §aShows you all the commands that are available.\n");

        	p.sendMessage("§5Version: " + ChatColor.DARK_PURPLE + version);
        	p.sendMessage("§5Created by: " + ChatColor.DARK_PURPLE + "lausi1793");
        	p.sendMessage("§e==========[ AdminHelper Help Version: " + ChatColor.YELLOW + version + " §e]==========");

	        return true;
	    	  }
	    	  if(args[0].equalsIgnoreCase("reload")){
	    		  if (p.hasPermission("adminhelper.reload")) {
			          reloadConfig();
			          p.sendMessage(ChatColor.GREEN + "Reloaded " + getDescription().getName() + " config.yml!");
			          return true;
			        }else{
			        	 p.sendMessage("§cYou dont have the permission\n§c-adminhelper.reload");
			        	 return true;
			        }
	    	  }
	    	  
	    	  if(args.length > 1){
	    		  
	    		  p.sendMessage("§cToo many arguments!\n§a/ami - Information about the plugin\n/ami reload - reloads the config.yml");
	    		  
	    	  }
	    	  
	    	  }else{
	    	        p.sendMessage("§cYou dont have the permission\n§c-adminhelper.info");
	    	        return true;
	    	      }
	    	  
	      }else{
	      sender.sendMessage("This command is not supported for the console.");
	      }
	   }
	    
	    if (cmd.getName().equalsIgnoreCase("coords")) {
	        sender.sendMessage(ChatColor.BLUE + "X: " + p.getLocation().getBlockX() + ChatColor.YELLOW + " Y: " + p.getLocation().getBlockY() + ChatColor.GREEN + " Z: " + p.getLocation().getBlockZ());
	        return true;
	      }
	  
	    if (label.equalsIgnoreCase("pcc")) {
	        if (p.hasPermission("adminhelper.privateclearchat")) {
	          String message = "";
	          for (int i = 0; i <= 200; i++) {
	            p.sendMessage(message);
	            message = message + " ";
	            if (i == 200) {
	              p.sendMessage(getConfig().getString("messages.clearprivatechat").replaceAll("&", "§"));
	            }
	          }
	          return true;
	        }else{
	        p.sendMessage("§cYou dont have the permission\n§c-adminhelper.privateclearchat");
	        return true;
	      }
	    }
	    
	    if (cmd.getName().equalsIgnoreCase("cc")) {
	    	    if(p.hasPermission("adminhelper.clearchat")) {
	    	      Bukkit.broadcastMessage("                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          ");
	    	      Bukkit.broadcastMessage("                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          ");
	    	      Bukkit.broadcastMessage("                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          ");
	    	      Bukkit.broadcastMessage("                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          ");
	    	      Bukkit.broadcastMessage("                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          ");
	    	      Bukkit.broadcastMessage("                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          ");
	    	      Bukkit.broadcastMessage("                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          ");
	    	      Bukkit.broadcastMessage("                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          ");
	    	      Bukkit.broadcastMessage("                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          ");
	    	      Bukkit.broadcastMessage("                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          ");
	    	      Bukkit.broadcastMessage("                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          ");
	    	      Bukkit.broadcastMessage("                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          ");
	    	      Bukkit.broadcastMessage("                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          ");
	    	      Bukkit.broadcastMessage("                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          ");
	    	      Bukkit.broadcastMessage("                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          ");
	    	      Bukkit.broadcastMessage("                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          ");
	    	      Bukkit.broadcastMessage("                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          ");
	    	      Bukkit.broadcastMessage("                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          ");
	    	      Bukkit.broadcastMessage("                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          ");
	    	      Bukkit.broadcastMessage("                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          ");
	    	      Bukkit.broadcastMessage("                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          ");
	    	      Bukkit.broadcastMessage("                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          ");
	    	      Bukkit.broadcastMessage("                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          ");
	    	      Bukkit.broadcastMessage("                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          ");
	    	      Bukkit.broadcastMessage("                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          ");
	    	      p.sendMessage(getConfig().getString("messages.clearchat").replaceAll("&", "§").replaceAll("%player%", p.getDisplayName()));
	    	      return true;
	    	    }else{
	    	        p.sendMessage("§cYou dont have the permission\n§c-adminhelper.clearchat");
	    	        return true;
	    	      }
	    }
	    
	    if (cmd.getName().equalsIgnoreCase("report")) {
	    	if(p.hasPermission("adminhelper.report")) {
		      if (args.length < 1) {
		          sender.sendMessage("§cToo few arguments!\n/report <player> <reportreason>");
		        }
		        else {
		          String ReportName = args[0];
		          String ReportReason = "";
		          p.sendMessage(getConfig().getString("messages.reportsuccessfull").replaceAll("&", "§").replaceAll("%player%", ReportName).replaceAll("%sender%", sender.getName()).replaceAll("%reason%", ReportReason));
		          for (int i = 1; i <= args.length - 1; i++)
		          {
		            ReportReason = ReportReason + args[i];
		            if (i != args.length - 1)
		              ReportReason = ReportReason + " ";
		          }
		          getLogger().info(sender.getName() + " hat " + ReportName + " reportet für \"" + ReportReason + "\"");
		          for (Player player : getServer().getOnlinePlayers())
		          {
		            if (player.hasPermission("adminhelper.report.notification")) {
		              if (ReportReason != "")
		            	p.sendMessage(getConfig().getString("messages.reportmessage").replaceAll("&", "§").replaceAll("%player%", ReportName).replaceAll("%sender%", sender.getName()).replaceAll("%reason%", ReportReason));
		              else
		                player.sendMessage("§a" + sender.getName() + "§e has reported §c" + ReportName + "§e for no reason");
		            }
		          }
		        }
		      return true;
	    	}else{
    	        p.sendMessage("§cYou dont have the permission\n§c-adminhelper.report");
    	        return true;
    	      }
		      }
	    
	    if (label.equalsIgnoreCase("ram")) {
	        if (p.hasPermission("adminhelper.*")) {
	          p.sendMessage(ChatColor.AQUA + "----" + ChatColor.GOLD + "Server" + ChatColor.AQUA + "----");
	          p.sendMessage(ChatColor.AQUA + "Free-Memory: " + ChatColor.GOLD + (runtime.maxMemory() - runtime.totalMemory()) / 1048576L + "MB");
	          p.sendMessage(ChatColor.AQUA + "Used-Memory: " + ChatColor.GOLD + runtime.totalMemory() / 1048576L + "MB");
	          p.sendMessage(ChatColor.AQUA + "Maxi-Memory: " + ChatColor.GOLD + runtime.maxMemory() / 1048576L + "MB");
	          p.sendMessage(ChatColor.AQUA + "Utilization: " + ramCheck() + usedMemory() + "%");
	          return true;
	        }
	        p.sendMessage("§cYou dont have the permission\n§c-adminhelper.ram");
	        return true;
	      }
	    
	    if (cmd.getName().equalsIgnoreCase("team"))
	    {
	      if (args.length > 0)
	      {
	    	  p.sendMessage("§cToo many arguments!\n/team");
	      }

	      if (args.length == 0)
	      {
	        p.sendMessage("§b============ §aServer Team §b============");
	        Set ak = getConfig().getConfigurationSection("team").getKeys(false);
	        int s = ak.size() + 1;

	        for (int i = 1; i < s; i++)
	        {
	          String staff1 = getConfig().getString("team.staff" + i + ".name");
	          String prefix1 = getConfig().getString("team.staff" + i + ".prefix");

	          Player p1 = getServer().getPlayer(staff1);

	          if (p1 != null)
	          {
	              p.sendMessage(ChatColor.GREEN + "[Online]   " + ChatColor.RESET + prefix1.replaceAll("&", "§") + " " +  staff1);
	            }
	          else {
	            p.sendMessage(ChatColor.RED + "[Offline]  " + ChatColor.RESET + prefix1.replaceAll("&", "§") + " " +  staff1);
	          }
	        }

	        p.sendMessage("§b=======================================");
	        return true;
	      }

	    }
	    
	    
      return false;
	  }

}

