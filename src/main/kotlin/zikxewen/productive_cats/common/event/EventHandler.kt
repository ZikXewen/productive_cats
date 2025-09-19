package zikxewen.productive_cats.common.event

import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.neoforge.capabilities.Capabilities
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent
import net.neoforged.neoforge.registries.DataPackRegistryEvent
import zikxewen.productive_cats.common.block.BlockRegistries
import zikxewen.productive_cats.common.cat.CatRegistries
import zikxewen.productive_cats.common.cat.CatType
import zikxewen.productive_cats.common.entity.EntityRegistries
import zikxewen.productive_cats.common.entity.ProductiveCat

@EventBusSubscriber(modid = "productive_cats")
object EventHandler {
  @SubscribeEvent
  fun createCatAttr(event: EntityAttributeCreationEvent) =
          event.put(EntityRegistries.PRODUCTIVE_CAT, ProductiveCat.createAttributes().build())
  @SubscribeEvent
  fun registerItemHandlerBlock(event: RegisterCapabilitiesEvent) =
          event.registerBlockEntity(
                  Capabilities.ItemHandler.BLOCK,
                  BlockRegistries.CAT_HOUSE_ENTITY
          ) { be, _ -> be.itemHandler }
  @SubscribeEvent
  fun registerCatType(event: DataPackRegistryEvent.NewRegistry) =
          event.dataPackRegistry(CatRegistries.CAT_TYPE_REGISTRY, CatType.CODEC, CatType.CODEC)
}
