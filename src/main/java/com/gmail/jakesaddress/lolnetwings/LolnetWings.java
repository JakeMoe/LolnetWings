package com.gmail.jakesaddress.lolnetwings;

import com.google.inject.Inject;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.*;
import org.spongepowered.api.plugin.Plugin;

@Plugin(id="lolnetwings",
        name = "Lolnet Wings",
        version = "0.8",
        description = "Lolnet Wings for Sponge")
public class LolnetWings {

  private Configuration configuration;

  @Inject
  @DefaultConfig(sharedRoot = true)
  private ConfigurationLoader<CommentedConfigurationNode> configLoader;

  @Inject
  private Logger logger;

  private static LolnetWings instance;

  @Listener
  public void onGameConstruction(GameConstructionEvent event) {
    instance = this;
  }

  @Listener
  public void onGamePreInitialization(GamePreInitializationEvent event) {
    configuration = new Configuration(getConfigLoader());
    getConfiguration().load();
  }

  @Listener
  public void onGameInitialization(GameInitializationEvent event) {
    Sponge.getEventManager().registerListeners(this, new EventListeners());
  }

  @Listener
  public void onGameStoppingEvent(GameStoppingEvent event) {
    getConfiguration().save();
  }

  ConfigurationLoader<CommentedConfigurationNode> getConfigLoader() {
    return configLoader;
  }

  Configuration getConfiguration() {
    return configuration;
  }

  static LolnetWings getInstance() {
    return instance;
  }

  Logger getLogger() {
     return logger;
  }

}
