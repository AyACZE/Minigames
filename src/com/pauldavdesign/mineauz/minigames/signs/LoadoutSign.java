package com.pauldavdesign.mineauz.minigames.signs;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.event.block.SignChangeEvent;

import com.pauldavdesign.mineauz.minigames.MinigamePlayer;
import com.pauldavdesign.mineauz.minigames.MinigameUtils;
import com.pauldavdesign.mineauz.minigames.Minigames;
import com.pauldavdesign.mineauz.minigames.gametypes.MinigameType;
import com.pauldavdesign.mineauz.minigames.minigame.Minigame;

public class LoadoutSign implements MinigameSign {
	
	private static Minigames plugin = Minigames.plugin;

	@Override
	public String getName() {
		return "Loadout";
	}

	@Override
	public String getCreatePermission() {
		return "minigame.sign.create.loadout";
	}

	@Override
	public String getCreatePermissionMessage() {
		return MinigameUtils.getLang("sign.loadout.createPermission");
	}

	@Override
	public String getUsePermission() {
		return "minigame.sign.use.loadout";
	}

	@Override
	public String getUsePermissionMessage() {
		return MinigameUtils.getLang("sign.loadout.usePermission");
	}

	@Override
	public boolean signCreate(SignChangeEvent event) {
		event.setLine(1, ChatColor.GREEN + "Loadout");
		return true;
	}

	@Override
	public boolean signUse(Sign sign, MinigamePlayer player) {
		if(player.getPlayer().getItemInHand().getType() == Material.AIR && player.isInMinigame()){
			Minigame mgm = player.getMinigame();
			if(mgm == null || mgm.isSpectator(player)){
				return false;
			}
			
			if(mgm.hasLoadout(sign.getLine(2))){
				if(!mgm.getLoadout(sign.getLine(2)).getUsePermissions() || player.getPlayer().hasPermission("minigame.loadout." + sign.getLine(2).toLowerCase())){
					player.setLoadout(mgm.getLoadout(sign.getLine(2)));
					player.sendMessage(ChatColor.AQUA + "[Minigames] " + ChatColor.WHITE + MinigameUtils.formStr("sign.loadout.equipped", sign.getLine(2)));
					
					if(mgm.getType() == MinigameType.SINGLEPLAYER || (mgm.getMpTimer() != null && mgm.getMpTimer().getStartWaitTimeLeft() == 0)){
						if(sign.getLine(3).equalsIgnoreCase("respawn")){
							player.sendMessage(ChatColor.AQUA + "[Minigames] " + ChatColor.WHITE + MinigameUtils.getLang("sign.loadout.nextRespawn"));
						}
						else{
							mgm.getLoadout(sign.getLine(2)).equiptLoadout(player);
						}
					}
					return true;
				}
				else{
					player.sendMessage(ChatColor.RED + "[Minigames] " + ChatColor.WHITE + MinigameUtils.formStr("sign.loadout.noPermisson", sign.getLine(2)));
				}
			}
			else if(plugin.mdata.hasLoadout(sign.getLine(2))){
				if(!plugin.mdata.getLoadout(sign.getLine(2)).getUsePermissions() || player.getPlayer().hasPermission("minigame.loadout." + sign.getLine(2).toLowerCase())){
					player.setLoadout(mgm.getLoadout(sign.getLine(2)));
					player.sendMessage(ChatColor.AQUA + "[Minigames] " + ChatColor.WHITE + MinigameUtils.formStr("sign.loadout.equipped", sign.getLine(2)));

					if(mgm.getType() == MinigameType.SINGLEPLAYER || (mgm.getMpTimer() != null && mgm.getMpTimer().getStartWaitTimeLeft() == 0)){
						if(sign.getLine(3).equalsIgnoreCase("respawn")){
							player.sendMessage(ChatColor.AQUA + "[Minigames] " + ChatColor.WHITE + MinigameUtils.getLang("sign.loadout.nextRespawn"));
						}
						else{
							plugin.mdata.getLoadout(sign.getLine(2)).equiptLoadout(player);
						}
					}
					return true;
				}
				else{
					player.sendMessage(ChatColor.RED + "[Minigames] " + ChatColor.WHITE + MinigameUtils.formStr("sign.loadout.noPermisson", sign.getLine(2)));
				}
			}
			else{
				player.sendMessage(ChatColor.AQUA + "[Minigames] " + ChatColor.WHITE + MinigameUtils.getLang("sign.loadout.noLoadout"));
			}
		}
		else if(player.getPlayer().getItemInHand().getType() != Material.AIR)
			player.sendMessage(ChatColor.AQUA + "[Minigames] " + ChatColor.WHITE + MinigameUtils.getLang("sign.emptyHand"));
		return false;
	}

}
