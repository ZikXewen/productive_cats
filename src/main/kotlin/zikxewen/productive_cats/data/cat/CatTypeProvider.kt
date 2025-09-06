package zikxewen.productive_cats.data.cat

import net.minecraft.core.RegistrySetBuilder
import net.minecraft.data.worldgen.BootstrapContext
import net.minecraft.resources.ResourceKey
import net.minecraft.world.item.Rarity
import zikxewen.productive_cats.ProductiveCats
import zikxewen.productive_cats.common.data.CatType
import zikxewen.productive_cats.common.data.DataRegistries

object CatTypeProvider {
  val OAK_LOG = Builder("oak_log", 0, 0)
  val DUMMY = Builder("dummy", 0, 0)
  val SHINY = Builder("shiny", 0, 0)
  val ANCIENT = Builder("test_cat_4", 0, 0).unique().rarity(Rarity.EPIC)
  val builder =
    RegistrySetBuilder().add(DataRegistries.CAT_TYPE_KEY) {
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
    val key = ResourceKey.create(DataRegistries.CAT_TYPE_KEY, ProductiveCats.rl(type))
    fun unique() = apply { this.unique = true }
    fun rarity(rarity: Rarity) = apply { this.rarity = rarity }
    fun save(bootstrap: BootstrapContext<CatType>) =
      bootstrap.register(key, CatType(type, primaryColor, secondaryColor, unique, rarity))
  }
}
