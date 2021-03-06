package com.pauldavdesign.mineauz.minigames.signs;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.block.SignChangeEvent;

import com.pauldavdesign.mineauz.minigames.MinigamePlayer;
import com.pauldavdesign.mineauz.minigames.MinigameUtils;
import com.pauldavdesign.mineauz.minigames.Minigames;
import com.pauldavdesign.mineauz.minigames.gametypes.MinigameType;
import com.pauldavdesign.mineauz.minigames.minigame.Minigame;

public class FinishSign implements MinigameSign {
	
	private static Minigames plugin = Minigames.plugin;

	@Override
	public String getName() {
		return "Finish";
	}

	@Override
	public String getCreatePermission() {
		return "minigame.sign.create.finish";
	}

	@Override
	public String getCreatePermissionMessage() {
		return MinigameUtils.getLang("sign.finish.createPermission");
	}

	@Override
	public String getUsePermission() {
		return null;
	}

	@Override
	public String getUsePermissionMessage() {
		return null;
	}

	@Override
	public boolean signCreate(SignChangeEvent event) {
		event.setLine(1, ChatColor.GREEN + "Finish");
		if(!event.getLine(2).isEmpty() && plugin.mdata.hasMinigame(event.getLine(2))){
			event.setLine(2, plugin.mdata.getMinigame(event.getLine(2)).getName());
		}
		else if(!event.getLine(2).isEmpty()){
			event.getPlayer().sendMessage(ChatColor.RED + "[Minigames] " + ChatColor.WHITE + MinigameUtils.getLang("minigame.error.noMinigame"));
			return false;
		}
		return true;
	}

	@Override
	public boolean signUse(Sign sign, MinigamePlayer player) {
		if(player.isInMinigame() && player.getPlayer().getItemInHand().getType() == Material.AIR){
			Minigame minigame = player.getMinigame();

			if(minigame.isSpectator(player)){
				return false;
			}
			
			if(!minigame.getFlags().isEmpty()){
				if(((LivingEntity)player.getPlayer()).isOnGround()){
					if(plugin.pdata.checkRequiredFlags(player, minigame.getName()).isEmpty()){
						if(sign.getLine(2).isEmpty() || sign.getLine(2).equals(player.getMinigame().getName())){
							if(player.getMinigame().getType() == MinigameType.TEAMS){
								if(player.getMinigame().getRedTeam().contains(player.getPlayer().getPlayer()))
									plugin.pdata.endTeamMinigame(0, minigame);
								else
									plugin.pdata.endTeamMinigame(1, minigame);
							}
							else
								plugin.pdata.endMinigame(player);
							
							plugin.pdata.partyMode(player, 3, 10L);
						}
					}
					else{
						List<String> requiredFlags = plugin.pdata.checkRequiredFlags(player, minigame.getName());
						String flags = "";
						int num = requiredFlags.size();
						
						for(int i = 0; i < num; i++){
							flags += requiredFlags.get(i);
							if(i != num - 1){
								flags += ", ";
							}
						}
						player.sendMessage(ChatColor.AQUA + "[Minigames] " + ChatColor.WHITE + MinigameUtils.getLang("sign.finish.requireFlags"));
						player.sendMessage(ChatColor.GRAY + flags);
					}
				}
				return true;
			}
			else{
				if(((LivingEntity)player.getPlayer()).isOnGround()){
					if(player.getMinigame().getType() == MinigameType.TEAMS){
						if(player.getMinigame().getRedTeam().contains(player.getPlayer().getPlayer()))
							plugin.pdata.endTeamMinigame(0, minigame);
						else
							plugin.pdata.endTeamMinigame(1, minigame);
					}
					else
						plugin.pdata.endMinigame(player);
					plugin.pdata.partyMode(player);
					return true;
				}
			}
		}
		else if(player.getPlayer().getItemInHand().getType() != Material.AIR){
			player.sendMessage(ChatColor.RED + "[Minigames] " + ChatColor.WHITE + MinigameUtils.getLang("sign.emptyHand"));
		}
		return false;
	}

}
