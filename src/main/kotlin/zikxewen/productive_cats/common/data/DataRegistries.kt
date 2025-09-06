package zikxewen.productive_cats.common.data

import net.minecraft.core.component.DataComponentType
import net.minecraft.core.registries.Registries
import net.minecraft.core.Registry
import net.minecraft.resources.ResourceKey
import net.neoforged.neoforge.attachment.AttachmentType
import net.neoforged.neoforge.registries.DeferredRegister
import net.neoforged.neoforge.registries.NeoForgeRegistries
import thedarkcolour.kotlinforforge.neoforge.forge.getValue
import zikxewen.productive_cats.ProductiveCats

object DataRegistries {
  val COMPONENT_REGISTRY =
          DeferredRegister.createDataComponents(
                  Registries.DATA_COMPONENT_TYPE,
                  ProductiveCats.MOD_ID
          )
  val ATTACHMENT_REGISTRY =
          DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, ProductiveCats.MOD_ID)
  val CAT_TYPE_KEY: ResourceKey<Registry<CatType>> = ResourceKey.createRegistryKey(ProductiveCats.rl("cat_type"))
  val CAT_DATA_COMPONENT: DataComponentType<CatData> by
          COMPONENT_REGISTRY.registerComponentType("cat_data") {
            it.persistent(CatData.CODEC).networkSynchronized(CatData.STREAM_CODEC)
          }
  val CAT_DATA_ATTACHMENT: AttachmentType<CatData> by
          ATTACHMENT_REGISTRY.register("cat_data") { ->
            AttachmentType.builder { -> CatData.DEFAULT }.sync(CatData.STREAM_CODEC).build()
          }
  val CAT_DATA_ATTACHMENT_2: AttachmentType<CatData> by
          ATTACHMENT_REGISTRY.register("cat_data_2") { ->
            AttachmentType.builder { -> CatData.DEFAULT }.sync(CatData.STREAM_CODEC).build()
          }
}
