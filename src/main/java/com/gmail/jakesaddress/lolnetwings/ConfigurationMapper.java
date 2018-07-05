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

  String getBossBarTitle() {
    return bossBarTitle;
  }

  float getDrainMultiplier() {
    return drainMultiplier;
  }

  float getFillMultiplier() {
    return fillMultiplier;
  }

}
