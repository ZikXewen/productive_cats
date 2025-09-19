package zikxewen.productive_cats.data.cat

import net.minecraft.core.RegistrySetBuilder
import net.minecraft.data.worldgen.BootstrapContext
import net.minecraft.resources.ResourceKey
import net.minecraft.world.item.Rarity
import zikxewen.productive_cats.ProductiveCats
import zikxewen.productive_cats.common.cat.CatRegistries
import zikxewen.productive_cats.common.cat.CatType

object CatTypeProvider {
  val OAK_LOG = Builder("oak_log", 0, 0).build()
  val DUMMY = Builder("dummy", 0, 0).build()
  val SHINY = Builder("shiny", 0, 0).build()
  val ANCIENT = Builder("test_cat_4", 0, 0).unique().rarity(Rarity.EPIC).build()
  val builder =
    RegistrySetBuilder().add(CatRegistries.CAT_TYPE_REGISTRY) {
      OAK_LOG.save(it)
      DUMMY.save(it)
      SHINY.save(it)
      ANCIENT.save(it)
    }
  class Builder(
    val type: String,
    val primaryColor: Int,
    val secondaryColor: Int,
  ) {
    var unique = false
    var rarity = Rarity.COMMON
    fun unique() = apply { this.unique = true }
    fun rarity(rarity: Rarity) = apply { this.rarity = rarity }
    fun build() = CatType(type, primaryColor, secondaryColor, unique, rarity)
  }
  fun CatType.save(bootstrap: BootstrapContext<CatType>) =
    bootstrap.register(
      ResourceKey.create(CatRegistries.CAT_TYPE_REGISTRY, ProductiveCats.rl(type)),
      this
    )
}
