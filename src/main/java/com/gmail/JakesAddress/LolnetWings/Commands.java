package com.gmail.JakesAddress.LolnetWings;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;

public class Commands implements CommandExecutor {

  private LolnetWings plugin;

  public Commands(LolnetWings plugin) {
    this.plugin = plugin;
  }

  @Override
  public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
    plugin.getLogger().info("Test command");
    return CommandResult.success();
  }
}
