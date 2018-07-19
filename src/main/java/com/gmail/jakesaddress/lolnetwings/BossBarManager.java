package com.gmail.jakesaddress.lolnetwings;

import org.spongepowered.api.boss.BossBarColors;
import org.spongepowered.api.boss.BossBarOverlays;
import org.spongepowered.api.boss.ServerBossBar;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.gamemode.GameMode;
import org.spongepowered.api.entity.living.player.gamemode.GameModes;
import org.spongepowered.api.text.serializer.TextSerializers;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

final class BossBarManager {

  private static final Map<UUID, ServerBossBar> bossBars = new HashMap<>();

  static void changeBossBarValue(Player player, float change) {
    if (!bossBars.containsKey(player.getUniqueId())) {
      createBossBar(player);
    }
    ServerBossBar serverBossBar = bossBars.get(player.getUniqueId());
    float newValue = serverBossBar.getPercent() + change;
    if (newValue < 0) {
      newValue = 0;
    } else if (newValue > 1) {
      newValue = 1;
    }
    serverBossBar.setPercent(newValue);
    if (serverBossBar.getPercent() >= 1.0F) {
      serverBossBar.setColor(BossBarColors.GREEN);
    } else if (serverBossBar.getPercent() >= 0.5F) {
      serverBossBar.setColor(BossBarColors.YELLOW);
    } else {
      serverBossBar.setColor(BossBarColors.RED);
    }
  }

  static void setBossBarValue(Player player, float value) {
    if (!bossBars.containsKey(player.getUniqueId())) {
      createBossBar(player);
    }
    bossBars.get(player.getUniqueId()).setPercent(value);
  }

  static void createBossBar(Player player) {
    if (!bossBars.containsKey(player.getUniqueId())) {
      ServerBossBar serverBossBar = ServerBossBar.builder()
        .color(BossBarColors.RED)
        .createFog(false)
        .darkenSky(false)
        .name(TextSerializers.FORMATTING_CODE.deserialize(LolnetWings.getInstance().getConfiguration().getConfigMapper().getBossBarTitle()))
        .overlay(BossBarOverlays.PROGRESS)
        .percent(0.0F)
        .playEndBossMusic(false)
        .visible(false)
        .build();
      serverBossBar.addPlayer(player);
      bossBars.put(player.getUniqueId(), serverBossBar);
    }
  }

  static float getBossBarValue(Player player) {
    if (bossBars.containsKey(player.getUniqueId())) {
      return bossBars.get(player.getUniqueId()).getPercent();
    }
    return 0;
  }

  static boolean isValidGameMode(GameMode gameMode) {
    return ((gameMode == GameModes.SURVIVAL) ||
            (gameMode == GameModes.ADVENTURE) ||
            (gameMode == GameModes.CREATIVE));
  }

  static void removeBossBar(Player player) {
    if (bossBars.containsKey(player.getUniqueId())) {
      ServerBossBar serverBossBar = bossBars.remove(player.getUniqueId());
      serverBossBar.removePlayer(player);
      serverBossBar.setPercent(0.0F);
      serverBossBar.setVisible(false);
    }
  }

  static void setBossBarVisible(Player player, boolean visible) {
    bossBars.get(player.getUniqueId()).setVisible(visible);
  }

}
