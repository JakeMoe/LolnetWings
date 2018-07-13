## LolnetWings

### Description

LolnetWings is a custom plugin for the Minecraft servers at https://www.lolnet.co.nz.  It features a boost bar that will fill up as you fly, and then get drained as you press the sneak key to boost.

### Version History

* 0.1 - Jake Moe - 1 July 2018
  * Initial commit of working plugin
* 0.2 - Jake Moe - 3 July 2018
  * Turns out the code logic was wrong, so code has been reworked
  * Boost Bar added
  * Boost on shift added
  * Added this README.md
* 0.3 - Jake Moe - 5 July 2018
  * Fixed game mode check for all modes elytra flight are possible
  * Added check for valid game mode in move event
  * Added support for configuration via config file
* 0.4 - Jake Moe - 8 July 2018
  * Fixed revision history in this README
  * Fixed boost logic to factor in direction player is looking
  * Fixed boost bar to display colours based on value (to match Jump boost bar)
* 0.5 - Jake Moe - 8 July 2018
  * Changed to make BossBarVisible visible when elytra flying and hidden when not
* 0.6 - Jake Moe - 13 July 2018
  * Fixed false to true in EventListeners when flying
  * Added original code back in to boost if level-ish and slow
  * Added config options for sneak and level boost amounts
  * Added check for flying on game mode change