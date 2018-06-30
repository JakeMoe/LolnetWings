package com.gmail.JakesAddress.LolnetWings;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.text.Text;

@Plugin(id="lolnetwings",
        name = "Lolnet Wings",
        version = "0.1",
        description = "Lolnet Wings for Sponge")
public class LolnetWings {

  @Inject
  private Logger logger;

  @Inject
  private PluginContainer pluginContainer;

  @Listener
  public void onGameInitialization(GameInitializationEvent event) {
    Commands commands = new Commands(this);
    CommandSpec commandSpec = CommandSpec.builder()
      .description(Text.of("Lolnet Wings Command"))
      .permission("lolnetwings.command")
      .executor(commands)
      .build();
    Sponge.getCommandManager().register(this.pluginContainer, commandSpec, "lolnetwings", "lw");
    Sponge.getEventManager().registerListeners(this, new MoveListener(this));
  }

  public Logger getLogger() {
     return logger;
  }

}
