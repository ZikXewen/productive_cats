package zikxewen.productive_cats

import net.minecraft.resources.ResourceLocation
import net.neoforged.fml.common.Mod
import thedarkcolour.kotlinforforge.neoforge.forge.MOD_BUS
import zikxewen.productive_cats.common.block.BlockRegistries
import zikxewen.productive_cats.common.data.DataRegistries
import zikxewen.productive_cats.common.entity.EntityRegistries
import zikxewen.productive_cats.common.item.ItemRegistries
import zikxewen.productive_cats.common.recipe.RecipeRegistries
import org.apache.logging.log4j.LogManager

@Mod(ProductiveCats.MOD_ID)
object ProductiveCats {
  const val MOD_ID = "productive_cats"
  val LOGGER = LogManager.getLogger(MOD_ID)
  fun rl(path: String) = ResourceLocation.fromNamespaceAndPath(MOD_ID, path)
  init {
    DataRegistries.ATTACHMENT_REGISTRY.register(MOD_BUS)
    DataRegistries.COMPONENT_REGISTRY.register(MOD_BUS)
    EntityRegistries.REGISTRY.register(MOD_BUS)
    RecipeRegistries.TYPE_REGISTRY.register(MOD_BUS)
    RecipeRegistries.SERIALIZER_REGISTRY.register(MOD_BUS)
    RecipeRegistries.CATEGORY_REGISTRY.register(MOD_BUS)
    BlockRegistries.REGISTRY.register(MOD_BUS)
    BlockRegistries.BE_REGISTRY.register(MOD_BUS)
    ItemRegistries.REGISTRY.register(MOD_BUS)
    ItemRegistries.TAB_REGISTRY.register(MOD_BUS)
  }
}
