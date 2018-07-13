package com.gmail.jakesaddress.lolnetwings;

import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

@ConfigSerializable
class ConfigurationMapper {

  @Setting(value = "boss-bar-title", comment = "Boss bar title")
  private String bossBarTitle = "&r&6\u26A1 &9&lBoost Charge &r&6\u26A1";

  @Setting(value = "fill-multiplier", comment = "Adjust this to change the fill rate")
  private float fillMultiplier = 10;

  @Setting(value = "drain-multiplier", comment = "Adjust this to change the drain rate")
  private float drainMultiplier = 1;

  @Setting(value = "target-speed", comment = "When under this speed, boost the player")
  private float targetSpeed = 1.1F;

  @Setting(value = "sneak-boost", comment = "Amount to boost when sneaking")
  private float sneakBoost = 1.1F;

  @Setting(value = "level-boost", comment = "Amount to boost when level-ish")
  private float levelBoost = 1.1F;

  String getBossBarTitle() {
    return bossBarTitle;
  }

  float getDrainMultiplier() {
    return drainMultiplier;
  }

  float getFillMultiplier() {
    return fillMultiplier;
  }

  float getTargetSpeed() {
    return targetSpeed;
  }

  float getSneakBoost() {
    return sneakBoost;
  }

  float getLevelBoost() {
    return levelBoost;
  }

}
