package zikxewen.productive_cats.common.item

import net.neoforged.neoforge.registries.DeferredRegister
import thedarkcolour.kotlinforforge.neoforge.forge.getValue
import zikxewen.productive_cats.ProductiveCats

object ItemRegistries {
  val REGISTRY = DeferredRegister.createItems(ProductiveCats.MOD_ID)
  val CAT_HOLDER: CatHolder by REGISTRY.registerItem("cat_holder", ::CatHolder)
}
