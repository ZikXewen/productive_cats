package zikxewen.productive_cats.data.lang

import net.minecraft.data.PackOutput
import net.neoforged.neoforge.common.data.LanguageProvider
import zikxewen.productive_cats.ProductiveCats
import zikxewen.productive_cats.common.block.BlockRegistries
import zikxewen.productive_cats.common.data.CatData
import zikxewen.productive_cats.common.entity.EntityRegistries
import zikxewen.productive_cats.common.item.ItemRegistries
import zikxewen.productive_cats.data.cat.CatTypeProvider as Cats

class EnUsLanguageProvider(val output: PackOutput) :
        LanguageProvider(output, ProductiveCats.MOD_ID, "en_us") {
  override fun addTranslations() {
    add(ProductiveCats.MOD_ID, "Productive Cats")
    add(ItemRegistries.CAT_HOLDER, "Cat Holder")
    add(BlockRegistries.CAT_BREEDER, "Cat Breeder")
    add(BlockRegistries.CAT_HOUSE, "Cat House")
    add(EntityRegistries.PRODUCTIVE_CAT, "Productive Cat")
    add(CatData.HELD_KEY, "Held Cat")
    add(CatData.DISPLAY_KEY, "%s - Speed %s, Productivity %s")
    add(Cats.OAK_LOG.displayKey, "Oak Cat")
    add(Cats.DUMMY.displayKey, "Dummy Cat")
    add(Cats.SHINY.displayKey, "Shiny Cat")
    add(Cats.ANCIENT.displayKey, "Ancient Cat")
  }
}
