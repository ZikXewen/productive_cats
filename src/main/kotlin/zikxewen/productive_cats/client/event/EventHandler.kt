package zikxewen.productive_cats.client.event

import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.neoforge.client.event.EntityRenderersEvent
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent
import zikxewen.productive_cats.client.entity_renderer.ProductiveCatRenderer
import zikxewen.productive_cats.client.tooltip.Tooltip
import zikxewen.productive_cats.common.entity.EntityRegistries

@EventBusSubscriber(modid = "productive_cats")
object EventHandler {
  @SubscribeEvent
  fun registerEntityRenderers(event: EntityRenderersEvent.RegisterRenderers) {
    event.registerEntityRenderer(EntityRegistries.PRODUCTIVE_CAT, ::ProductiveCatRenderer)
  }
  @SubscribeEvent
  fun modifyTooltip(event: ItemTooltipEvent) =
    Tooltip.modifyTooltip(event.itemStack, event.toolTip)
}
