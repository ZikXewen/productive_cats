package zikxewen.productive_cats.common.event

import net.minecraft.world.entity.animal.Cat
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent
import zikxewen.productive_cats.common.entity.EntityRegistries

@EventBusSubscriber(modid = "productive_cats")
object EventHandler {
  @SubscribeEvent
  fun createDefaultAttributes(event: EntityAttributeCreationEvent) {
    event.put(EntityRegistries.PRODUCTIVE_CAT, Cat.createAttributes().build())
  }
}
