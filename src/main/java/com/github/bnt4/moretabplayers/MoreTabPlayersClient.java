package com.github.bnt4.moretabplayers;

import com.github.bnt4.moretabplayers.config.ModConfig;
import com.mojang.blaze3d.platform.InputConstants;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.AutoConfigClient;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MoreTabPlayersClient implements ClientModInitializer {

	public static final String MOD_ID = "moretabplayers";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static ModConfig config;

	@Override
	public void onInitializeClient() {
		AutoConfig.register(ModConfig.class, GsonConfigSerializer::new);
		config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();

		registerKeybinds();
	}

	private void registerKeybinds() {
		KeyMapping.Category category = KeyMapping.Category.register(Identifier.fromNamespaceAndPath(MOD_ID, "category"));

		KeyMapping toggleModKey = KeyMappingHelper.registerKeyMapping(
				new KeyMapping("key." + MOD_ID + ".toggle_mod",
						InputConstants.Type.KEYSYM,
						InputConstants.KEY_I,
						category));

		KeyMapping openModSettingsKey = KeyMappingHelper.registerKeyMapping(
				new KeyMapping("key." + MOD_ID + ".open_settings",
						InputConstants.Type.KEYSYM,
						InputConstants.UNKNOWN.getValue(), // default unbound
						category));

		listenToKeybinds(toggleModKey, openModSettingsKey);
	}

	private void listenToKeybinds(KeyMapping toggleModKey, KeyMapping openModSettingsKey) {
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			while (toggleModKey.consumeClick()) {
				if (client.player != null) {
					config.enabled = !config.enabled;
					AutoConfig.getConfigHolder(ModConfig.class).save();
					client.player.sendOverlayMessage(Component.literal("[More Tab Players] " + (config.enabled ? "Enabled" : "Disabled")));
				}
			}

			while (openModSettingsKey.consumeClick()) {
				client.gui.setScreen(AutoConfigClient.getConfigScreen(ModConfig.class, client.gui.screen()).get());
			}
		});
	}

}
