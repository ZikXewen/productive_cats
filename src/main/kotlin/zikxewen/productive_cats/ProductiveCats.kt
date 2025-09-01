package zikxewen.productive_cats

import net.neoforged.fml.common.Mod
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import thedarkcolour.kotlinforforge.neoforge.forge.MOD_BUS
import thedarkcolour.kotlinforforge.neoforge.forge.getValue
import zikxewen.productive_cats.common.block.BlockRegistries
import zikxewen.productive_cats.common.entity.EntityRegistries
import zikxewen.productive_cats.common.item.ItemRegistries
import zikxewen.productive_cats.common.recipe.RecipeRegistries
import zikxewen.productive_cats.common.data_component.DataComponentRegistries

@Mod(ProductiveCats.MOD_ID)
object ProductiveCats {
  const val MOD_ID = "productive_cats"
  val LOGGER: Logger = LogManager.getLogger(MOD_ID)
  init {
    LOGGER.log(Level.INFO, "Hello, World!")
    DataComponentRegistries.REGISTRY.register(MOD_BUS)
    EntityRegistries.REGISTRY.register(MOD_BUS)
    RecipeRegistries.TYPE_REGISTRY.register(MOD_BUS)
    RecipeRegistries.SERIALIZER_REGISTRY.register(MOD_BUS)
    RecipeRegistries.CATEGORY_REGISTRY.register(MOD_BUS)
    BlockRegistries.REGISTRY.register(MOD_BUS)
    ItemRegistries.REGISTRY.register(MOD_BUS)
  }
}
