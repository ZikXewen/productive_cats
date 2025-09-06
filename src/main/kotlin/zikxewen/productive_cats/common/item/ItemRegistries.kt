package zikxewen.productive_cats.common.item

import net.minecraft.core.registries.Registries
import net.minecraft.network.chat.Component
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.ItemStack
import net.neoforged.neoforge.registries.DeferredRegister
import thedarkcolour.kotlinforforge.neoforge.forge.getValue
import zikxewen.productive_cats.ProductiveCats
import zikxewen.productive_cats.common.block.BlockRegistries

object ItemRegistries {
  val REGISTRY = DeferredRegister.createItems(ProductiveCats.MOD_ID)
  val TAB_REGISTRY = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ProductiveCats.MOD_ID)
  val TAB: CreativeModeTab by TAB_REGISTRY.register("main") { ->
    CreativeModeTab.builder()
      .title(Component.translatable(ProductiveCats.MOD_ID))
      .icon { ItemStack(CAT_HOLDER) }
      .displayItems { _, output ->
        output.accept(CAT_HOLDER)
        output.accept(CAT_BREEDER)
        output.accept(CAT_HOUSE)
      }
      .build()
  }
  val CAT_HOLDER: CatHolder by REGISTRY.registerItem("cat_holder", ::CatHolder)
  val CAT_BREEDER by REGISTRY.registerSimpleBlockItem("cat_breeder") { BlockRegistries.CAT_BREEDER }
  val CAT_HOUSE by REGISTRY.registerSimpleBlockItem("cat_house") { BlockRegistries.CAT_HOUSE }
}
