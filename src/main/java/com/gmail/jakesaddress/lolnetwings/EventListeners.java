package com.gmail.jakesaddress.lolnetwings;

import com.flowpowered.math.vector.Vector3d;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.gamemode.GameModes;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.MoveEntityEvent;
import org.spongepowered.api.event.entity.living.humanoid.ChangeGameModeEvent;
import org.spongepowered.api.event.filter.Getter;
import org.spongepowered.api.event.network.ClientConnectionEvent;

public class EventListeners {

  @Listener
  public void onClientConnectionDisconnect(ClientConnectionEvent.Disconnect event, @Getter("getTargetEntity") Player player) {
    BossBarManager.removeBossBar(player);
  }

  @Listener
  public void onClientConnectionJoin(ClientConnectionEvent.Join event, @Getter("getTargetEntity") Player player) {
    BossBarManager.createBossBar(player);
    BossBarManager.setBossBarVisible(player, BossBarManager.isGameModeSurvival(player));
  }

  @Listener
  public void onChangeGameMode(ChangeGameModeEvent event, @Getter("getTargetEntity") Player player) {
    BossBarManager.setBossBarVisible(player, event.getGameMode() == GameModes.SURVIVAL);
  }

  @Listener
  public void onMoveEntity(MoveEntityEvent event, @Getter("getTargetEntity") Player player) {

    if (player.get(Keys.IS_ELYTRA_FLYING).orElse(false)) {

      Vector3d velocity = player.getVelocity();
      double speed = Math.sqrt(Math.pow(velocity.getX(), 2) + Math.pow(velocity.getZ(), 2));

      if (player.get(Keys.IS_SNEAKING).orElse(false)) {
        if (BossBarManager.getBossBarValue(player) > 0) {
          player.setVelocity(new Vector3d(velocity.getX() * 1.1, velocity.getY() * 1.1, velocity.getZ() * 1.1));
          BossBarManager.changeBossBarValue(player, (float) -((speed * 1.1) / 10));
        }
      } else {
        BossBarManager.changeBossBarValue(player, (float) (speed / 100));
      }

    }

  }

}
