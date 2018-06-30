package com.gmail.JakesAddress.LolnetWings;

import com.flowpowered.math.vector.Vector3d;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.MoveEntityEvent;
import org.spongepowered.api.event.filter.cause.Root;

public class MoveListener {

  private LolnetWings plugin;

  public MoveListener(LolnetWings plugin) {
    this.plugin = plugin;
  }

  @Listener
  public void onMoveEntity(MoveEntityEvent event, @Root Player player) {

    double pitch = player.getRotation().getX();
    Vector3d velocity = player.getVelocity();
    double speed = Math.sqrt(Math.pow(velocity.getX(), 2) + Math.pow(velocity.getZ(), 2));
    double multiplier = (20 + ((pitch > 20) ? 20 : pitch)) / 40;
    double targetSpeed = 0.90 + (multiplier * 0.40);

    if (player.get(Keys.IS_ELYTRA_FLYING).get() &&
        pitch > -20 &&
        speed < targetSpeed) {
      double boost = 1.1 + (multiplier * 0.2);
      // plugin.getLogger().info(player.getName() + " p = " + pitch + ", s = " + String.valueOf(speed) + ", m = " + String.valueOf(multiplier) + ", tS = " + targetSpeed + ", b = " + String.valueOf(boost));
      player.setVelocity(new Vector3d(velocity.getX() * boost, velocity.getY(), velocity.getZ() * boost));
    }

  }

}
