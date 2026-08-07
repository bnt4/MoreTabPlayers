package com.github.bnt4.moretabplayers.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "moretabplayers")
public class ModConfig implements ConfigData {

    /**
     * whether the mod is enabled (if disabled, the vanilla values will be used)
     */
    @ConfigEntry.Gui.Tooltip
    public boolean enabled = true;

    /**
     * the number of players in the tab list
     */
    @ConfigEntry.Gui.Tooltip
    public long maxPlayers = 260L;

    /**
     * the number of players displayed per column
     */
    @ConfigEntry.Gui.Tooltip
    public int maxPlayersPerColumn = 40;

    @Override
    public void validatePostLoad() {
        if (maxPlayers < 1) maxPlayers = 1;
        if (maxPlayersPerColumn < 1) maxPlayersPerColumn = 1;
    }

}
