package com.gmail.jakesaddress.lolnetwings;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.plugin.Plugin;

@Plugin(id="lolnetwings",
        name = "Lolnet Wings",
        version = "0.2",
        description = "Lolnet Wings for Sponge")
public class LolnetWings {

  @Inject
  private Logger logger;

  @Listener
  public void onGameInitialization(GameInitializationEvent event) {
    Sponge.getEventManager().registerListeners(this, new EventListeners());
  }

  public Logger getLogger() {
     return logger;
  }

}
