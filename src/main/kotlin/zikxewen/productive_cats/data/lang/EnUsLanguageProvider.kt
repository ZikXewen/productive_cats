package zikxewen.productive_cats.data.lang

import java.util.concurrent.CompletableFuture
import net.minecraft.core.HolderLookup
import net.minecraft.data.CachedOutput
import net.minecraft.data.PackOutput
import net.minecraft.resources.ResourceKey
import net.neoforged.neoforge.common.data.LanguageProvider
import zikxewen.productive_cats.ProductiveCats
import zikxewen.productive_cats.common.block.BlockRegistries
import zikxewen.productive_cats.common.cat.CatRegistries
import zikxewen.productive_cats.common.cat.CatType
import zikxewen.productive_cats.common.data.CatData
import zikxewen.productive_cats.common.entity.EntityRegistries
import zikxewen.productive_cats.common.item.ItemRegistries
import zikxewen.productive_cats.data.cat.CatTypeProvider as Cats

class EnUsLanguageProvider(output: PackOutput, val registries: CompletableFuture<HolderLookup.Provider>) :
        LanguageProvider(output, ProductiveCats.MOD_ID, "en_us") {
  fun addTranslations(registries: HolderLookup.Provider) {
    add(ProductiveCats.MOD_ID, "Productive Cats")
    add(ItemRegistries.CAT_HOLDER, "Cat Holder")
    add(BlockRegistries.CAT_BREEDER, "Cat Breeder")
    add(BlockRegistries.CAT_HOUSE, "Cat House")
    add(EntityRegistries.PRODUCTIVE_CAT, "Productive Cat")
    add(CatData.HELD_KEY, "Held Cat")
    add(CatData.DISPLAY_KEY, "%s - Speed %s, Productivity %s")
    val lookup = registries.lookupOrThrow(CatRegistries.CAT_TYPE_REGISTRY)
    fun addCat(cat: ResourceKey<CatType>, name: String) =
            add(lookup.getOrThrow(cat).value().displayKey, name)
    addCat(Cats.OAK_LOG, "Oak Cat")
    addCat(Cats.DUMMY, "Dummy Cat")
    addCat(Cats.SHINY, "Shiny Cat")
    addCat(Cats.ANCIENT, "Ancient Cat")
  }
  override fun addTranslations() = Unit
  override fun run(cache: CachedOutput) = registries.thenCompose {
    addTranslations(it)
    super.run(cache)
  }
}
