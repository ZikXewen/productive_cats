package zikxewen.productive_cats.common.cat

import net.minecraft.core.Registry
import net.minecraft.resources.ResourceKey
import zikxewen.productive_cats.ProductiveCats

object CatRegistries {
  val CAT_TYPE_REGISTRY: ResourceKey<Registry<CatType>> =
    ResourceKey.createRegistryKey(ProductiveCats.rl("cat_type"))
}
