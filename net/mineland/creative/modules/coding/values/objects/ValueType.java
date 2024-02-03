package net.mineland.creative.modules.coding.values.objects;

import net.mineland.creative.modules.coding.values.CurrentAirRemainingValue;
import net.mineland.creative.modules.coding.values.CurrentArmorPointsValue;
import net.mineland.creative.modules.coding.values.CurrentEyeLocationValue;
import net.mineland.creative.modules.coding.values.CurrentFireTicksValue;
import net.mineland.creative.modules.coding.values.CurrentFoodLevelValue;
import net.mineland.creative.modules.coding.values.CurrentHealthValue;
import net.mineland.creative.modules.coding.values.CurrentHeldSlotValue;
import net.mineland.creative.modules.coding.values.CurrentLocationValue;
import net.mineland.creative.modules.coding.values.CurrentNameValue;
import net.mineland.creative.modules.coding.values.CurrentSaturationLevelValue;
import net.mineland.creative.modules.coding.values.CurrentXpLevelValue;
import net.mineland.creative.modules.coding.values.CurrentXpValue;
import net.mineland.creative.modules.coding.values.EventBlockLocationValue;
import net.mineland.creative.modules.coding.values.EventDamageValue;
import net.mineland.creative.modules.coding.values.MaxHealthValue;
import net.mineland.creative.modules.coding.values.MessageValue;
import net.mineland.creative.modules.coding.values.NewSlotValue;
import net.mineland.creative.modules.coding.values.OldSlotValue;
import net.mineland.creative.modules.coding.values.TargetBlockLocationValue;
import net.mineland.creative.modules.coding.values.TotalPlayerCountValue;
import net.mineland.creative.modules.coding.values.TotalVotesCountValue;

public enum ValueType {
   CURRENT_HEALTH(CurrentHealthValue::new),
   MAX_HEALTH(MaxHealthValue::new),
   CURRENT_FOOD_LEVEL(CurrentFoodLevelValue::new),
   CURRENT_SATURATION_LEVEL(CurrentSaturationLevelValue::new),
   CURRENT_XP_LEVEL(CurrentXpLevelValue::new),
   CURRENT_XP(CurrentXpValue::new),
   CURRENT_ARMOR_POINTS(CurrentArmorPointsValue::new),
   CURRENT_FIRE_TICKS(CurrentFireTicksValue::new),
   CURRENT_AIR_REMAINING(CurrentAirRemainingValue::new),
   CURRENT_EYE_LOCATION(CurrentEyeLocationValue::new),
   CURRENT_LOCATION(CurrentLocationValue::new),
   CURRENT_NAME(CurrentNameValue::new),
   CURRENT_HELD_SLOT(CurrentHeldSlotValue::new),
   TARGET_BLOCK_LOCATION(TargetBlockLocationValue::new),
   EVENT_BLOCK_LOCATION(EventBlockLocationValue::new),
   TOTAL_PLAYER_COUNT(TotalPlayerCountValue::new),
   EVENT_DAMAGE(EventDamageValue::new),
   NEW_SLOT(NewSlotValue::new),
   OLD_SLOT(OldSlotValue::new),
   VOTES_COUNT(TotalVotesCountValue::new),
   MESSAGE(MessageValue::new);

   private ValueCreator creator;

   private ValueType(ValueCreator creator) {
      this.creator = creator;
   }

   public Value create() {
      return this.creator.create();
   }
}
