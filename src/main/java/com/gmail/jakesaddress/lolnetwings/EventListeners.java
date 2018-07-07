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
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public class EventListeners {

  @Listener
  public void onClientConnectionDisconnect(ClientConnectionEvent.Disconnect event, @Getter("getTargetEntity") Player player) {
    BossBarManager.removeBossBar(player);
  }

  @Listener
  public void onClientConnectionJoin(ClientConnectionEvent.Join event, @Getter("getTargetEntity") Player player) {
    BossBarManager.createBossBar(player);
    BossBarManager.setBossBarVisible(player, BossBarManager.isValidGameMode(player.get(Keys.GAME_MODE).orElse(GameModes.NOT_SET)));
  }

  @Listener
  public void onChangeGameMode(ChangeGameModeEvent event, @Getter("getTargetEntity") Player player) {
    BossBarManager.setBossBarVisible(player, BossBarManager.isValidGameMode(event.getGameMode()));
  }

  @Listener
  public void onMoveEntity(MoveEntityEvent event, @Getter("getTargetEntity") Player player) {

    if (player.get(Keys.IS_ELYTRA_FLYING).orElse(false) &&
        BossBarManager.isValidGameMode(player.get(Keys.GAME_MODE).orElse(GameModes.NOT_SET))) {

      Vector3d velocity = player.getVelocity();
      double speed = Math.sqrt(Math.pow(velocity.getX(), 2) + Math.pow(velocity.getY(), 2) + Math.pow(velocity.getZ(), 2));

      if (player.get(Keys.IS_SNEAKING).orElse(false)) {

        Location<World> to = event.getToTransform().getLocation();

        if (BossBarManager.getBossBarValue(player) > 0 &&
            to.getBlockY() < to.getExtent().getDimension().getBuildHeight()) {

          Vector3d rotation = player.getRotation();
          Vector3d newVelocity = new Vector3d(speed * Math.sin((180 - rotation.getY()) * (Math.PI / 180)) * Math.cos((180 - rotation.getX()) * (Math.PI / 180)),
                                              speed * Math.sin(-rotation.getX() * (Math.PI / 180)),
                                              speed * Math.cos(rotation.getY() * (Math.PI / 180)) * Math.cos(rotation.getX() * (Math.PI / 180)));
/*          Vector3d adjustedVelocity = new Vector3d(((velocity.getX() + newVelocity.getX()) / 2) * 1.1,
                                                   ((velocity.getY() + newVelocity.getY()) / 2) * 1.1,
                                                   ((velocity.getZ() + newVelocity.getZ()) / 2) * 1.1); */
          Vector3d adjustedVelocity = velocity.add(newVelocity).mul(0.5).mul(1.1);
          player.setVelocity(adjustedVelocity);
          double newSpeed = Math.sqrt(Math.pow(adjustedVelocity.getX(), 2) + Math.pow(adjustedVelocity.getY(), 2) + Math.pow(adjustedVelocity.getZ(), 2));
          BossBarManager.changeBossBarValue(player, (float) -((newSpeed * 1.1) / 100) * LolnetWings.getInstance().getConfiguration().getConfigMapper().getDrainMultiplier());
        }
      } else {
        BossBarManager.changeBossBarValue(player, (float) (speed / 100) * LolnetWings.getInstance().getConfiguration().getConfigMapper().getFillMultiplier());
      }

    }

  }

}
