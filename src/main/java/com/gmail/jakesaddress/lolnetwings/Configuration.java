package com.gmail.jakesaddress.lolnetwings;

import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMapper;

class Configuration {

  private ConfigurationLoader<CommentedConfigurationNode> configLoader;
  private ConfigurationMapper configMapper;
  private ObjectMapper<ConfigurationMapper>.BoundInstance objectMapper;
  private ConfigurationNode rootNode;

  Configuration(ConfigurationLoader<CommentedConfigurationNode> configLoader) {

    this.configLoader = configLoader;

    try {
      this.objectMapper = ObjectMapper.forClass(ConfigurationMapper.class).bindToNew();
      LolnetWings.getInstance().getLogger().info("[SUCCESS] Map configuration");
    } catch (Exception ex) {
      LolnetWings.getInstance().getLogger().info("[FAIL] Map configuration", ex);
    }

  }

  void load() {
    try {
      rootNode = getConfigLoader().load();
      configMapper = getObjectMapper().populate(getRootNode());
      LolnetWings.getInstance().getLogger().info("[SUCCESS] Load configuration");
    } catch (Exception ex) {
      LolnetWings.getInstance().getLogger().info("[FAIL] Load configuration", ex);
    }
  }

  void save() {
    try {
      getConfigLoader().save(rootNode);
      LolnetWings.getInstance().getLogger().info("[SUCCESS] Save configuration");
    } catch (Exception ex) {
      LolnetWings.getInstance().getLogger().info("[FAIL] Save configuration", ex);
    }
  }

  ConfigurationLoader<CommentedConfigurationNode> getConfigLoader() {
    return configLoader;
  }

  ConfigurationMapper getConfigMapper() {
    return configMapper;
  }

  ObjectMapper<ConfigurationMapper>.BoundInstance getObjectMapper() {
    return objectMapper;
  }

  ConfigurationNode getRootNode() {
    return rootNode;
  }

}
