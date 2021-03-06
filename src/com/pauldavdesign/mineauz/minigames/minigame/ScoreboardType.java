package com.pauldavdesign.mineauz.minigames.minigame;

public enum ScoreboardType {
	COMPLETIONS("Wins"),
	BEST_KILLS("Kills"),
	LEAST_DEATHS("Deaths"),
	BEST_SCORE("Points"),
	LEAST_TIME(""),
	LEAST_REVERTS("Reverts"),
	TOTAL_KILLS("Kills"),
	TOTAL_DEATHS("Deaths"),
	TOTAL_SCORE("Points"),
	TOTAL_REVERTS("Reverts"),
	TOTAL_TIME(""),
	FAILURES("Losses");
	
	private String typeName;
	
	private ScoreboardType(String type){
		typeName = type;
	}
	
	public String getTypeName(){
		return typeName;
	}
}
