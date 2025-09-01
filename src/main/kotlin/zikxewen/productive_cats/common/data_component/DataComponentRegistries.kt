package zikxewen.productive_cats.common.data_component

import net.minecraft.core.component.DataComponentType
import net.minecraft.core.registries.Registries
import net.neoforged.neoforge.registries.DeferredRegister
import thedarkcolour.kotlinforforge.neoforge.forge.getValue
import zikxewen.productive_cats.ProductiveCats

object DataComponentRegistries {
  val REGISTRY = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, ProductiveCats.MOD_ID)
  val CAT_DATA: DataComponentType<CatData> by REGISTRY.registerComponentType("cat_data") {
    it.persistent(CatData.CODEC).networkSynchronized(CatData.STREAM_CODEC)
  }
}
