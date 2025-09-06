package zikxewen.productive_cats.data.event

import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.neoforge.data.event.GatherDataEvent
import zikxewen.productive_cats.data.cat.CatTypeProvider
import zikxewen.productive_cats.data.lang.EnUsLanguageProvider
import zikxewen.productive_cats.data.recipe.CatBreedingRecipeProvider
import zikxewen.productive_cats.data.recipe.CatProduceRecipeProvider

@EventBusSubscriber(modid = "productive_cats")
object EventHandler {
  @SubscribeEvent
  fun gatherData(event: GatherDataEvent.Client) {
    event.createDatapackRegistryObjects(CatTypeProvider.builder)
    event.createProvider(CatBreedingRecipeProvider::Runner)
    event.createProvider(CatProduceRecipeProvider::Runner)
    event.createProvider(::EnUsLanguageProvider)
  }
}
