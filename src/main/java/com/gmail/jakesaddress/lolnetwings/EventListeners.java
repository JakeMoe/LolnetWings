package com.gmail.jakesaddress.lolnetwings;

import com.flowpowered.math.vector.Vector3d;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.gamemode.GameMode;
import org.spongepowered.api.entity.living.player.gamemode.GameModes;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.cause.entity.damage.source.DamageSource;
import org.spongepowered.api.event.entity.DamageEntityEvent;
import org.spongepowered.api.event.entity.MoveEntityEvent;
import org.spongepowered.api.event.entity.living.humanoid.ChangeGameModeEvent;
import org.spongepowered.api.event.filter.Getter;
import org.spongepowered.api.event.filter.cause.Root;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import org.spongepowered.common.registry.provider.DamageSourceToTypeProvider;

public class EventListeners {

  @Listener
  public void onChangeGameMode(ChangeGameModeEvent event, @Getter("getTargetEntity") Player player) {
    BossBarManager.setBossBarVisible(player, BossBarManager.isValidGameMode(event.getGameMode()) && player.get(Keys.IS_ELYTRA_FLYING).orElse(false));
    if (event.getGameMode() == GameModes.CREATIVE) {
      BossBarManager.setBossBarValue(player, 1.0F);
    }
  }

  @Listener
  public void onClientConnectionDisconnect(ClientConnectionEvent.Disconnect event, @Getter("getTargetEntity") Player player) {
    BossBarManager.removeBossBar(player);
  }

  @Listener
  public void onClientConnectionJoin(ClientConnectionEvent.Join event, @Getter("getTargetEntity") Player player) {
    BossBarManager.createBossBar(player);
  }

  @Listener
  public void onDamageEntity(DamageEntityEvent event, @Getter("getTargetEntity") Player player, @Root DamageSource source) {

    if (event.isCancelled()) {
      return;
    }

    if (DamageSourceToTypeProvider.getInstance().get("flyIntoWall").orElse(null) == source.getType()) {
      event.setCancelled(player.get(Keys.IS_ELYTRA_FLYING).orElse(false));
    }

  }

  @Listener
  public void onMoveEntity(MoveEntityEvent event, @Getter("getTargetEntity") Player player) {

    if (BossBarManager.exists(player)) {
      GameMode playerGameMode = player.get(Keys.GAME_MODE).orElse(GameModes.NOT_SET);

      if (player.get(Keys.IS_ELYTRA_FLYING).orElse(false) &&
        BossBarManager.isValidGameMode(playerGameMode)) {

        BossBarManager.setBossBarVisible(player, true);

        Vector3d velocity = player.getVelocity();
        double speed = Math.sqrt(Math.pow(velocity.getX(), 2) + Math.pow(velocity.getY(), 2) + Math.pow(velocity.getZ(), 2));

        Location<World> to = event.getToTransform().getLocation();

        if (player.get(Keys.IS_SNEAKING).orElse(false)) {

          if (BossBarManager.getBossBarValue(player) > 0 &&
            to.getBlockY() < to.getExtent().getDimension().getBuildHeight()) {

            Vector3d rotation = player.getRotation();
            Vector3d newVelocity = new Vector3d(speed * Math.sin((180 - rotation.getY()) * (Math.PI / 180)) * Math.cos((180 - rotation.getX()) * (Math.PI / 180)),
              speed * Math.sin(-rotation.getX() * (Math.PI / 180)),
              speed * Math.cos(rotation.getY() * (Math.PI / 180)) * Math.cos(rotation.getX() * (Math.PI / 180)));
            Vector3d adjustedVelocity = velocity.add(newVelocity).mul(0.5).mul(1.1);
            player.setVelocity(adjustedVelocity);
            double newSpeed = Math.sqrt(Math.pow(adjustedVelocity.getX(), 2) + Math.pow(adjustedVelocity.getY(), 2) + Math.pow(adjustedVelocity.getZ(), 2));

            if (!(playerGameMode == GameModes.CREATIVE)) {
              BossBarManager.changeBossBarValue(player, (float) -((newSpeed * LolnetWings.getInstance().getConfiguration().getConfigMapper().getSneakBoost()) / 100) * LolnetWings.getInstance().getConfiguration().getConfigMapper().getDrainMultiplier());
            }
          }
        } else if (player.getRotation().getX() > -20 &&
          to.getBlockY() < to.getExtent().getDimension().getBuildHeight() &&
          speed < LolnetWings.getInstance().getConfiguration().getConfigMapper().getTargetSpeed()) {

          player.setVelocity(player.getVelocity().mul(LolnetWings.getInstance().getConfiguration().getConfigMapper().getLevelBoost()));

        } else {
          if (playerGameMode == GameModes.CREATIVE) {
            BossBarManager.setBossBarValue(player, 1.0F);
          } else {
            BossBarManager.changeBossBarValue(player, (float) (speed / 100) * LolnetWings.getInstance().getConfiguration().getConfigMapper().getFillMultiplier());
          }
        }

      } else {
        BossBarManager.setBossBarVisible(player, false);
      }

    }

  }

}
