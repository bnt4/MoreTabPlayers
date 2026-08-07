package com.github.bnt4.moretabplayers.mixin;

import com.github.bnt4.moretabplayers.MoreTabPlayersClient;
import net.minecraft.client.gui.components.PlayerTabOverlay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(PlayerTabOverlay.class)
public class PlayerTabOverlayMixin {

    @ModifyConstant(method = "getPlayerInfos", constant = @Constant(longValue = 80L))
    private long modifyMaxPlayers(long originalLimit) {
        return MoreTabPlayersClient.config.enabled ? Math.max(1, MoreTabPlayersClient.config.maxPlayers) : originalLimit;
    }

    // PlayerTabOverlay defines a MAX_ROWS_PER_COL int as 20, but it is not used in extractRenderState?
    // so int value 20 is modified directly in the extractRenderState method.
    @ModifyConstant(method = "extractRenderState", constant = @Constant(intValue = 20))
    private int modifyMaxPlayersPerColumn(int originalLimit) {
        return MoreTabPlayersClient.config.enabled ? Math.max(1, MoreTabPlayersClient.config.maxPlayersPerColumn) : originalLimit;
    }

}
