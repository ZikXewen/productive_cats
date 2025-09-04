package zikxewen.productive_cats.data.lang

import net.minecraft.data.PackOutput
import net.neoforged.neoforge.common.data.LanguageProvider
import zikxewen.productive_cats.ProductiveCats
import zikxewen.productive_cats.common.block.BlockRegistries
import zikxewen.productive_cats.common.entity.EntityRegistries
import zikxewen.productive_cats.common.item.ItemRegistries

class EnUsLanguageProvider(val output: PackOutput) :
        LanguageProvider(output, ProductiveCats.MOD_ID, "en_us") {
  override fun addTranslations() {
    add(ItemRegistries.CAT_HOLDER, "Cat Holder")
    add(BlockRegistries.CAT_BREEDER, "Cat Breeder")
    add(BlockRegistries.CAT_HOUSE, "Cat House")
    add(EntityRegistries.PRODUCTIVE_CAT, "Productive Cat")
    // TODO: Find a way to rename cats per type
  }
}
