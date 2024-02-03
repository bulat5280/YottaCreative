package net.mineland.creative.modules.coding.actions;

import net.mineland.creative.modules.coding.ChestParser;
import net.mineland.creative.modules.coding.actions.game.blockmanipulation.ActionBreakBlock;
import net.mineland.creative.modules.coding.actions.game.blockmanipulation.ActionChangeSign;
import net.mineland.creative.modules.coding.actions.game.blockmanipulation.ActionCopyBlocks;
import net.mineland.creative.modules.coding.actions.game.blockmanipulation.ActionDeleteBlocks;
import net.mineland.creative.modules.coding.actions.game.blockmanipulation.ActionEmptyHolder;
import net.mineland.creative.modules.coding.actions.game.blockmanipulation.ActionFillHolder;
import net.mineland.creative.modules.coding.actions.game.blockmanipulation.ActionSetBlock;
import net.mineland.creative.modules.coding.actions.game.codeutility.ActionCancelEvent;
import net.mineland.creative.modules.coding.actions.game.codeutility.ActionStartLoop;
import net.mineland.creative.modules.coding.actions.game.codeutility.ActionStopLoop;
import net.mineland.creative.modules.coding.actions.game.codeutility.ActionWait;
import net.mineland.creative.modules.coding.actions.game.entityspawning.ActionLaunchFirework;
import net.mineland.creative.modules.coding.actions.game.entityspawning.ActionSpawnItem;
import net.mineland.creative.modules.coding.actions.game.entityspawning.ActionSpawnMob;
import net.mineland.creative.modules.coding.actions.game.entityspawning.ActionSpawnTnt;
import net.mineland.creative.modules.coding.actions.game.entityspawning.ActionSpawnVehicle;
import net.mineland.creative.modules.coding.actions.game.entityspawning.ActionSpawnXpOrb;
import net.mineland.creative.modules.coding.actions.game.scoreboardmanipulation.ActionCreateScoreboard;
import net.mineland.creative.modules.coding.actions.game.scoreboardmanipulation.ActionRemoveScoreboard;
import net.mineland.creative.modules.coding.actions.game.scoreboardmanipulation.ActionRemoveScoreboardScore;
import net.mineland.creative.modules.coding.actions.game.scoreboardmanipulation.ActionSetScoreboardScore;
import net.mineland.creative.modules.coding.actions.game.specialeffects.ActionCreateExplosion;
import net.mineland.creative.modules.coding.actions.game.specialeffects.ActionCreateParticleLine;
import net.mineland.creative.modules.coding.actions.game.specialeffects.ActionPlayFireworkExplosion;
import net.mineland.creative.modules.coding.actions.game.specialeffects.ActionPlayParticleEffect;
import net.mineland.creative.modules.coding.actions.game.world.ActionChangeTime;
import net.mineland.creative.modules.coding.actions.game.world.ActionChangeWeather;
import net.mineland.creative.modules.coding.actions.ifentity.ActionIfEntityIsMob;
import net.mineland.creative.modules.coding.actions.ifentity.ActionIfEntityIsNear;
import net.mineland.creative.modules.coding.actions.ifentity.ActionIfEntityIsProjectile;
import net.mineland.creative.modules.coding.actions.ifentity.ActionIfEntityIsType;
import net.mineland.creative.modules.coding.actions.ifentity.ActionIfEntityNameEquals;
import net.mineland.creative.modules.coding.actions.ifentity.ActionIfEntityStandingOnBlock;
import net.mineland.creative.modules.coding.actions.ifgame.ActionIfGameBlockEquals;
import net.mineland.creative.modules.coding.actions.ifgame.ActionIfGameContainerHasAllItems;
import net.mineland.creative.modules.coding.actions.ifgame.ActionIfGameContainerHasItem;
import net.mineland.creative.modules.coding.actions.ifgame.ActionIfGameSignHasText;
import net.mineland.creative.modules.coding.actions.ifplayer.ActionIfPlayerBlockEquals;
import net.mineland.creative.modules.coding.actions.ifplayer.ActionIfPlayerBlocking;
import net.mineland.creative.modules.coding.actions.ifplayer.ActionIfPlayerCommandEquals;
import net.mineland.creative.modules.coding.actions.ifplayer.ActionIfPlayerFlying;
import net.mineland.creative.modules.coding.actions.ifplayer.ActionIfPlayerGliding;
import net.mineland.creative.modules.coding.actions.ifplayer.ActionIfPlayerHasAllItems;
import net.mineland.creative.modules.coding.actions.ifplayer.ActionIfPlayerHasItem;
import net.mineland.creative.modules.coding.actions.ifplayer.ActionIfPlayerHoldingItem;
import net.mineland.creative.modules.coding.actions.ifplayer.ActionIfPlayerHoldingItemMainHand;
import net.mineland.creative.modules.coding.actions.ifplayer.ActionIfPlayerHoldingItemOffHand;
import net.mineland.creative.modules.coding.actions.ifplayer.ActionIfPlayerItemEquals;
import net.mineland.creative.modules.coding.actions.ifplayer.ActionIfPlayerLookingAtBlock;
import net.mineland.creative.modules.coding.actions.ifplayer.ActionIfPlayerMessageEquals;
import net.mineland.creative.modules.coding.actions.ifplayer.ActionIfPlayerNameEquals;
import net.mineland.creative.modules.coding.actions.ifplayer.ActionIfPlayerNear;
import net.mineland.creative.modules.coding.actions.ifplayer.ActionIfPlayerSlotEquals;
import net.mineland.creative.modules.coding.actions.ifplayer.ActionIfPlayerSneaking;
import net.mineland.creative.modules.coding.actions.ifplayer.ActionIfPlayerSprinting;
import net.mineland.creative.modules.coding.actions.ifplayer.ActionIfPlayerStandingOnBlock;
import net.mineland.creative.modules.coding.actions.ifplayer.ActionIfPlayerSwimming;
import net.mineland.creative.modules.coding.actions.ifplayer.ActionIfPlayerVoted;
import net.mineland.creative.modules.coding.actions.ifplayer.ActionIfPlayerWearing;
import net.mineland.creative.modules.coding.actions.ifvariable.ActionIfVariableEquals;
import net.mineland.creative.modules.coding.actions.ifvariable.ActionIfVariableExist;
import net.mineland.creative.modules.coding.actions.ifvariable.ActionIfVariableGreater;
import net.mineland.creative.modules.coding.actions.ifvariable.ActionIfVariableGreaterOrEqual;
import net.mineland.creative.modules.coding.actions.ifvariable.ActionIfVariableLess;
import net.mineland.creative.modules.coding.actions.ifvariable.ActionIfVariableLessOrEqual;
import net.mineland.creative.modules.coding.actions.ifvariable.ActionIfVariableNotEquals;
import net.mineland.creative.modules.coding.actions.ifvariable.ActionIfVariableTextContains;
import net.mineland.creative.modules.coding.actions.ifvariable.ActionIfVariableTextEquals;
import net.mineland.creative.modules.coding.actions.player.animations.ActionBedLeaveAnimation;
import net.mineland.creative.modules.coding.actions.player.animations.ActionDamageAnimation;
import net.mineland.creative.modules.coding.actions.player.animations.ActionGetCritAnimation;
import net.mineland.creative.modules.coding.actions.player.animations.ActionGetMagicCritAnimation;
import net.mineland.creative.modules.coding.actions.player.animations.ActionMainHandAnimation;
import net.mineland.creative.modules.coding.actions.player.animations.ActionOffHandAnimation;
import net.mineland.creative.modules.coding.actions.player.animations.ActionRemoveSneaking;
import net.mineland.creative.modules.coding.actions.player.animations.ActionSetSneaking;
import net.mineland.creative.modules.coding.actions.player.animations.ActionSleepTitle;
import net.mineland.creative.modules.coding.actions.player.communication.ActionActionBar;
import net.mineland.creative.modules.coding.actions.player.communication.ActionClearChat;
import net.mineland.creative.modules.coding.actions.player.communication.ActionDialogue;
import net.mineland.creative.modules.coding.actions.player.communication.ActionMessage;
import net.mineland.creative.modules.coding.actions.player.communication.ActionSound;
import net.mineland.creative.modules.coding.actions.player.communication.ActionStopSound;
import net.mineland.creative.modules.coding.actions.player.communication.ActionTitle;
import net.mineland.creative.modules.coding.actions.player.hovers.ActionHoverMessage;
import net.mineland.creative.modules.coding.actions.player.inventory.ActionClearInventory;
import net.mineland.creative.modules.coding.actions.player.inventory.ActionGiveItems;
import net.mineland.creative.modules.coding.actions.player.inventory.ActionGiveRandomItem;
import net.mineland.creative.modules.coding.actions.player.inventory.ActionLoadInventory;
import net.mineland.creative.modules.coding.actions.player.inventory.ActionRemoveItems;
import net.mineland.creative.modules.coding.actions.player.inventory.ActionSaveInventory;
import net.mineland.creative.modules.coding.actions.player.inventory.ActionSetArmor;
import net.mineland.creative.modules.coding.actions.player.inventory.ActionSetItems;
import net.mineland.creative.modules.coding.actions.player.inventory.ActionSetOffHand;
import net.mineland.creative.modules.coding.actions.player.inventory.ActionSetSlot;
import net.mineland.creative.modules.coding.actions.player.movement.ActionKick;
import net.mineland.creative.modules.coding.actions.player.movement.ActionLaunchForward;
import net.mineland.creative.modules.coding.actions.player.movement.ActionLaunchToLoc;
import net.mineland.creative.modules.coding.actions.player.movement.ActionLaunchToward;
import net.mineland.creative.modules.coding.actions.player.movement.ActionLaunchUpwards;
import net.mineland.creative.modules.coding.actions.player.movement.ActionRandomTeleport;
import net.mineland.creative.modules.coding.actions.player.movement.ActionRideEntity;
import net.mineland.creative.modules.coding.actions.player.movement.ActionTeleport;
import net.mineland.creative.modules.coding.actions.player.movement.ActionTeleportSequence;
import net.mineland.creative.modules.coding.actions.player.settings.ActionDisableDeathDrops;
import net.mineland.creative.modules.coding.actions.player.settings.ActionDisableFlight;
import net.mineland.creative.modules.coding.actions.player.settings.ActionEnableDeathDrops;
import net.mineland.creative.modules.coding.actions.player.settings.ActionEnableFlight;
import net.mineland.creative.modules.coding.actions.player.settings.ActionHideScoreboard;
import net.mineland.creative.modules.coding.actions.player.settings.ActionSetAdventure;
import net.mineland.creative.modules.coding.actions.player.settings.ActionSetCreative;
import net.mineland.creative.modules.coding.actions.player.settings.ActionSetSpectator;
import net.mineland.creative.modules.coding.actions.player.settings.ActionSetSurvival;
import net.mineland.creative.modules.coding.actions.player.settings.ActionShowScoreboard;
import net.mineland.creative.modules.coding.actions.player.statistics.ActionClearPotionEffects;
import net.mineland.creative.modules.coding.actions.player.statistics.ActionDamage;
import net.mineland.creative.modules.coding.actions.player.statistics.ActionGivePotionEffect;
import net.mineland.creative.modules.coding.actions.player.statistics.ActionHeal;
import net.mineland.creative.modules.coding.actions.player.statistics.ActionRemovePotionEffect;
import net.mineland.creative.modules.coding.actions.player.statistics.ActionSetFlightSpeed;
import net.mineland.creative.modules.coding.actions.player.statistics.ActionSetFoodLevel;
import net.mineland.creative.modules.coding.actions.player.statistics.ActionSetMaxHealth;
import net.mineland.creative.modules.coding.actions.player.statistics.ActionSetOnFire;
import net.mineland.creative.modules.coding.actions.player.statistics.ActionSetSaturationLevel;
import net.mineland.creative.modules.coding.actions.player.statistics.ActionSetWalkSpeed;
import net.mineland.creative.modules.coding.actions.player.statistics.ActionSetXpLevel;
import net.mineland.creative.modules.coding.actions.player.statistics.ActionSetXpProgress;
import net.mineland.creative.modules.coding.actions.select.ActionSelectAllEntities;
import net.mineland.creative.modules.coding.actions.select.ActionSelectAllMobs;
import net.mineland.creative.modules.coding.actions.select.ActionSelectAllPlayers;
import net.mineland.creative.modules.coding.actions.select.ActionSelectDefaultEntity;
import net.mineland.creative.modules.coding.actions.select.ActionSelectDefaultPlayer;
import net.mineland.creative.modules.coding.actions.select.ActionSelectEntitiesByCondition;
import net.mineland.creative.modules.coding.actions.select.ActionSelectFilterSelectionRandomly;
import net.mineland.creative.modules.coding.actions.select.ActionSelectLastSpawnedMob;
import net.mineland.creative.modules.coding.actions.select.ActionSelectMobsByCondition;
import net.mineland.creative.modules.coding.actions.select.ActionSelectPlayersByCondition;
import net.mineland.creative.modules.coding.actions.select.ActionSelectRandomEntity;
import net.mineland.creative.modules.coding.actions.select.ActionSelectRandomMob;
import net.mineland.creative.modules.coding.actions.select.ActionSelectRandomPlayer;
import net.mineland.creative.modules.coding.actions.variable.ActionCreateSection;
import net.mineland.creative.modules.coding.actions.variable.ActionListCreate;
import net.mineland.creative.modules.coding.actions.variable.ActionListGetString;
import net.mineland.creative.modules.coding.actions.variable.ActionListSetString;
import net.mineland.creative.modules.coding.actions.variable.ActionVarChangeLocationCoordinates;
import net.mineland.creative.modules.coding.actions.variable.ActionVarChangeLocationView;
import net.mineland.creative.modules.coding.actions.variable.ActionVarDecrement;
import net.mineland.creative.modules.coding.actions.variable.ActionVarDifference;
import net.mineland.creative.modules.coding.actions.variable.ActionVarGetLocationPitch;
import net.mineland.creative.modules.coding.actions.variable.ActionVarGetLocationX;
import net.mineland.creative.modules.coding.actions.variable.ActionVarGetLocationY;
import net.mineland.creative.modules.coding.actions.variable.ActionVarGetLocationYaw;
import net.mineland.creative.modules.coding.actions.variable.ActionVarGetLocationZ;
import net.mineland.creative.modules.coding.actions.variable.ActionVarIncrement;
import net.mineland.creative.modules.coding.actions.variable.ActionVarLocsDistance;
import net.mineland.creative.modules.coding.actions.variable.ActionVarParseNumber;
import net.mineland.creative.modules.coding.actions.variable.ActionVarProduct;
import net.mineland.creative.modules.coding.actions.variable.ActionVarQuotient;
import net.mineland.creative.modules.coding.actions.variable.ActionVarRandomNumber;
import net.mineland.creative.modules.coding.actions.variable.ActionVarRemainder;
import net.mineland.creative.modules.coding.actions.variable.ActionVarSet;
import net.mineland.creative.modules.coding.actions.variable.ActionVarSetLocationCoordinates;
import net.mineland.creative.modules.coding.actions.variable.ActionVarSetLocationView;
import net.mineland.creative.modules.coding.actions.variable.ActionVarSum;
import net.mineland.creative.modules.coding.activators.Activator;
import net.mineland.creative.modules.coding.worldactivators.WorldActivator;
import org.bukkit.inventory.ItemStack;
import ua.govnojon.libs.bukkitutil.ItemData;

public enum ActionType {
   PLAYER_GIVE_ITEMS(ActionGiveItems::new),
   PLAYER_SET_ITEMS(ActionSetItems::new),
   PLAYER_SET_ARMOR(ActionSetArmor::new),
   PLAYER_SET_OFF_HAND(ActionSetOffHand::new),
   PLAYER_REMOVE_ITEMS(ActionRemoveItems::new),
   PLAYER_CLEAR_INVENTORY(ActionClearInventory::new),
   PLAYER_SET_SLOT(ActionSetSlot::new),
   PLAYER_GIVE_RANDOM_ITEM(ActionGiveRandomItem::new),
   PLAYER_SAVE_INVENTORY(ActionSaveInventory::new),
   PLAYER_LOAD_INVENTORY(ActionLoadInventory::new),
   PLAYER_MESSAGE(ActionMessage::new),
   PLAYER_DIALOGUE(ActionDialogue::new),
   PLAYER_CLEAR_CHAT(ActionClearChat::new),
   PLAYER_SOUND(ActionSound::new),
   PLAYER_STOP_SOUND(ActionStopSound::new),
   PLAYER_TITLE(ActionTitle::new),
   PLAYER_ACTION_BAR(ActionActionBar::new),
   PLAYER_TELEPORT(ActionTeleport::new),
   PLAYER_RANDOM_TELEPORT(ActionRandomTeleport::new),
   PLAYER_TELEPORT_SEQUENCE(ActionTeleportSequence::new),
   PLAYER_LAUNCH_UPWARDS(ActionLaunchUpwards::new),
   PLAYER_LAUNCH_FORWARD(ActionLaunchForward::new),
   PLAYER_LAUNCH_TOWARD(ActionLaunchToward::new),
   PLAYER_LAUNCH_TOLOC(ActionLaunchToLoc::new),
   PLAYER_RIDE_ENTITY(ActionRideEntity::new),
   PLAYER_KICK(ActionKick::new),
   PLAYER_DAMAGE(ActionDamage::new),
   PLAYER_HEAL(ActionHeal::new),
   PLAYER_GIVE_POTION_EFFECT(ActionGivePotionEffect::new),
   PLAYER_CLEAR_POTION_EFFECTS(ActionClearPotionEffects::new),
   PLAYER_REMOVE_POTION_EFFECT(ActionRemovePotionEffect::new),
   PLAYER_SET_XP_LEVEL(ActionSetXpLevel::new),
   PLAYER_SET_XP_PROGRESS(ActionSetXpProgress::new),
   PLAYER_SET_FOOD_LEVEL(ActionSetFoodLevel::new),
   PLAYER_SET_SATURATION_LEVEL(ActionSetSaturationLevel::new),
   PLAYER_SET_MAX_HEALTH(ActionSetMaxHealth::new),
   PLAYER_SET_ON_FIRE(ActionSetOnFire::new),
   PLAYER_SET_FLIGHT_SPEED(ActionSetFlightSpeed::new),
   PLAYER_SET_WALK_SPEED(ActionSetWalkSpeed::new),
   PLAYER_ENABLE_FLIGHT(ActionEnableFlight::new),
   PLAYER_DISABLE_FLIGHT(ActionDisableFlight::new),
   PLAYER_SET_ADVENTURE(ActionSetAdventure::new),
   PLAYER_SET_SURVIVAL(ActionSetSurvival::new),
   PLAYER_SET_CREATIVE(ActionSetCreative::new),
   PLAYER_SET_SPECTATOR(ActionSetSpectator::new),
   PLAYER_ENABLE_DEATH_DROPS(ActionEnableDeathDrops::new),
   PLAYER_DISABLE_DEATH_DROPS(ActionDisableDeathDrops::new),
   PLAYER_SHOW_SCOREBOARD(ActionShowScoreboard::new),
   PLAYER_HIDE_SCOREBOARD(ActionHideScoreboard::new),
   GAME_SPAWN_MOB(ActionSpawnMob::new),
   GAME_SPAWN_ITEM(ActionSpawnItem::new),
   GAME_LAUNCH_FIREWORK(ActionLaunchFirework::new),
   GAME_SPAWN_TNT(ActionSpawnTnt::new),
   GAME_SPAWN_VEHICLE(ActionSpawnVehicle::new),
   GAME_SPAWN_XP_ORB(ActionSpawnXpOrb::new),
   GAME_WAIT(ActionWait::new),
   GAME_START_LOOP(ActionStartLoop::new),
   GAME_STOP_LOOP(ActionStopLoop::new),
   GAME_CANCEL_EVENT(ActionCancelEvent::new),
   GAME_SET_BLOCK(ActionSetBlock::new),
   GAME_DELETE_BLOCKS(ActionDeleteBlocks::new),
   GAME_BREAK_BLOCK(ActionBreakBlock::new),
   GAME_COPY_BLOCKS(ActionCopyBlocks::new),
   GAME_FILL_HOLDER(ActionFillHolder::new),
   GAME_EMPTY_HOLDER(ActionEmptyHolder::new),
   GAME_CHANGE_SIGN(ActionChangeSign::new),
   GAME_CREATE_EXPLOSION(ActionCreateExplosion::new),
   GAME_PLAY_FIREWORK_EXPLOSION(ActionPlayFireworkExplosion::new),
   GAME_PLAY_PARTICLE_EFFECT(ActionPlayParticleEffect::new),
   GAME_CREATE_PARTICLE_LINE(ActionCreateParticleLine::new),
   GAME_CREATE_SCOREBOARD(ActionCreateScoreboard::new),
   PLAYER_SLEEP_TITLE(ActionSleepTitle::new),
   PLAYER_BED_LEAVE(ActionBedLeaveAnimation::new),
   PLAYER_MAIN_HAND_ANIMATION(ActionMainHandAnimation::new),
   PLAYER_OFF_HAND_ANIMATION(ActionOffHandAnimation::new),
   PLAYER_SET_SNEAK(ActionSetSneaking::new),
   PLAYER_REMOVE_SNEAK(ActionRemoveSneaking::new),
   PLAYER_CRIT_ANIMATION(ActionGetCritAnimation::new),
   PLAYER_MAGIC_CRIT_ANIMATION(ActionGetMagicCritAnimation::new),
   PLAYER_DAMAGE_ANIMATION(ActionDamageAnimation::new),
   GAME_REMOVE_SCOREBOARD(ActionRemoveScoreboard::new),
   GAME_SET_SCOREBOARD_SCORE(ActionSetScoreboardScore::new),
   GAME_REMOVE_SCOREBOARD_SCORE(ActionRemoveScoreboardScore::new),
   GAME_WEATHER(ActionChangeWeather::new),
   GAME_TIME(ActionChangeTime::new),
   IF_VARIABLE_EXIST(ActionIfVariableExist::new),
   PLAYER_LIST_SETVALUE(ActionListSetString::new),
   PLAYER_LIST_GETSTRING(ActionListGetString::new),
   PLAYER_LIST_CREATE(ActionListCreate::new),
   PLAYER_LIST_CREATESECTION(ActionCreateSection::new),
   VARIABLE_GET_X(ActionVarGetLocationX::new),
   VARIABLE_GET_Y(ActionVarGetLocationY::new),
   VARIABLE_GET_Z(ActionVarGetLocationZ::new),
   VARIABLE_GET_YAW(ActionVarGetLocationYaw::new),
   VARIABLE_GET_PITCH(ActionVarGetLocationPitch::new),
   VARIABLE_LOCATIONS_DISTANCE(ActionVarLocsDistance::new),
   VARIABLE_CHANGE_LOCATION_COORDINATES(ActionVarChangeLocationCoordinates::new),
   VARIABLE_SET_LOCATION_COORDINATES(ActionVarSetLocationCoordinates::new),
   VARIABLE_CHANGE_LOCATION_VIEW(ActionVarChangeLocationView::new),
   VARIABLE_SET_LOCATION_VIEW(ActionVarSetLocationView::new),
   IF_PLAYER_SNEAKING(ActionIfPlayerSneaking::new),
   IF_PLAYER_HOLDING_ITEM(ActionIfPlayerHoldingItem::new),
   IF_PLAYER_HOLDING_ITEM_MAIN(ActionIfPlayerHoldingItemMainHand::new),
   IF_PLAYER_HOLDING_ITEM_OFF(ActionIfPlayerHoldingItemOffHand::new),
   IF_PLAYER_HAS_ITEM(ActionIfPlayerHasItem::new),
   IF_PLAYER_HAS_ALL_ITEMS(ActionIfPlayerHasAllItems::new),
   IF_PLAYER_LOOKING_AT(ActionIfPlayerLookingAtBlock::new),
   IF_PLAYER_STANDING_ON(ActionIfPlayerStandingOnBlock::new),
   IF_PLAYER_NEAR(ActionIfPlayerNear::new),
   IF_PLAYER_WEARING(ActionIfPlayerWearing::new),
   IF_PLAYER_NAME_EQUALS(ActionIfPlayerNameEquals::new),
   IF_PLAYER_BLOCKING(ActionIfPlayerBlocking::new),
   IF_PLAYER_ITEM_EQUALS(ActionIfPlayerItemEquals::new),
   IF_PLAYER_BLOCK_EQUALS(ActionIfPlayerBlockEquals::new),
   IF_PLAYER_SLOT_EQUALS(ActionIfPlayerSlotEquals::new),
   IF_PLAYER_GLIDING(ActionIfPlayerGliding::new),
   IF_PLAYER_SPRINTING(ActionIfPlayerSprinting::new),
   IF_PLAYER_FLYING(ActionIfPlayerFlying::new),
   IF_PLAYER_VOTED(ActionIfPlayerVoted::new),
   IF_PLAYER_SWIMMING(ActionIfPlayerSwimming::new),
   IF_PLAYER_COMMAND_EQUALS(ActionIfPlayerCommandEquals::new),
   IF_PLAYER_MESSAGE_EQUALS(ActionIfPlayerMessageEquals::new),
   IF_ENTITY_IS_TYPE(ActionIfEntityIsType::new),
   IF_ENTITY_NAME_EQUALS(ActionIfEntityNameEquals::new),
   IF_ENTITY_STANDING_ON_BLOCK(ActionIfEntityStandingOnBlock::new),
   IF_ENTITY_IS_NEAR(ActionIfEntityIsNear::new),
   IF_ENTITY_IS_MOB(ActionIfEntityIsMob::new),
   IF_ENTITY_IS_PROJECTILE(ActionIfEntityIsProjectile::new),
   IF_GAME_BLOCK_EQUALS(ActionIfGameBlockEquals::new),
   IF_GAME_CONTAINER_HAS_ITEM(ActionIfGameContainerHasItem::new),
   IF_GAME_CONTAINER_HAS_ALL_ITEMS(ActionIfGameContainerHasAllItems::new),
   IF_GAME_SIGN_HAS_TEXT(ActionIfGameSignHasText::new),
   VARIABLE_SET(ActionVarSet::new),
   VARIABLE_SUM(ActionVarSum::new),
   VARIABLE_DIFFERENCE(ActionVarDifference::new),
   VARIABLE_PRODUCT(ActionVarProduct::new),
   VARIABLE_QUOTIENT(ActionVarQuotient::new),
   VARIABLE_REMAINDER(ActionVarRemainder::new),
   VARIABLE_INCREMENT(ActionVarIncrement::new),
   VARIABLE_DECREMENT(ActionVarDecrement::new),
   VARIABLE_PARSE_NUMBER(ActionVarParseNumber::new),
   VARIABLE_RANDOM_NUMBER(ActionVarRandomNumber::new),
   IF_VARIABLE_EQUALS(ActionIfVariableEquals::new),
   IF_VARIABLE_NOT_EQUALS(ActionIfVariableNotEquals::new),
   IF_VARIABLE_GREATER(ActionIfVariableGreater::new),
   IF_VARIABLE_GREATER_OR_EQUAL(ActionIfVariableGreaterOrEqual::new),
   IF_VARIABLE_LESS(ActionIfVariableLess::new),
   IF_VARIABLE_LESS_OR_EQUAL(ActionIfVariableLessOrEqual::new),
   IF_VARIABLE_TEXT_EQUALS(ActionIfVariableTextEquals::new),
   PLAYER_HOVER_MESSAGE(ActionHoverMessage::new),
   IF_VARIABLE_TEXT_CONTAINS(ActionIfVariableTextContains::new),
   SELECT_DEFAULT_PLAYER(ActionSelectDefaultPlayer::new),
   SELECT_DEFAULT_ENTITY(ActionSelectDefaultEntity::new),
   SELECT_RANDOM_PLAYER(ActionSelectRandomPlayer::new),
   SELECT_RANDOM_MOB(ActionSelectRandomMob::new),
   SELECT_RANDOM_ENTITY(ActionSelectRandomEntity::new),
   SELECT_ALL_PLAYERS(ActionSelectAllPlayers::new),
   SELECT_ALL_MOBS(ActionSelectAllMobs::new),
   SELECT_ALL_ENTITIES(ActionSelectAllEntities::new),
   SELECT_LAST_SPAWNED_MOB(ActionSelectLastSpawnedMob::new),
   SELECT_FILTER_SELECTION_RANDOMLY(ActionSelectFilterSelectionRandomly::new),
   SELECT_PLAYERS_BY_CONDITION(ActionSelectPlayersByCondition::new),
   SELECT_MOBS_BY_CONDITION(ActionSelectMobsByCondition::new),
   SELECT_ENTITIES_BY_CONDITION(ActionSelectEntitiesByCondition::new),
   ELSE(ActionElse::new),
   CALL_FUNCTION(ActionCallFunction::new);

   private ActionCreator actionCreator;
   private ActionCreatorTwo actionCreatorTwo;
   private Action actionInstance;
   private Action actionInstanceTwo;
   private boolean hasChest;

   private ActionType(ActionCreatorTwo actionCreatorTwo) {
      this.actionCreatorTwo = actionCreatorTwo;
      this.actionInstanceTwo = this.createTwo((WorldActivator)null);
      this.hasChest = this.actionInstanceTwo.parseChest(new ItemStack[36]);
      if (!this.hasChest) {
         this.hasChest = this.actionInstanceTwo.parseChest(new ChestParser(new ItemStack[36]));
      }

   }

   private ActionType(ActionCreator actionCreator) {
      this.actionCreator = actionCreator;
      this.actionInstance = this.create((Activator)null);
      this.hasChest = this.actionInstance.parseChest(new ItemStack[36]);
      if (!this.hasChest) {
         this.hasChest = this.actionInstance.parseChest(new ChestParser(new ItemStack[36]));
      }

   }

   public static Action createTwo(String name, WorldActivator activator) {
      try {
         return valueOf(name.toUpperCase()).actionCreatorTwo.createTwo(activator);
      } catch (IllegalArgumentException var3) {
         return null;
      }
   }

   public static Action create(String name, Activator activator) {
      try {
         return valueOf(name.toUpperCase()).actionCreator.create(activator);
      } catch (IllegalArgumentException var3) {
         return null;
      }
   }

   public ActionType getType() {
      return this.actionInstance.getType();
   }

   public Action.Category getCategory() {
      return this.actionInstance.getCategory();
   }

   public ItemData getIcon() {
      return this.actionInstance.getIcon();
   }

   public boolean hasChest() {
      return this.hasChest;
   }

   public Action createTwo(WorldActivator activator) {
      return this.actionCreatorTwo.createTwo(activator);
   }

   public Action create(Activator activator) {
      return this.actionCreator.create(activator);
   }
}
