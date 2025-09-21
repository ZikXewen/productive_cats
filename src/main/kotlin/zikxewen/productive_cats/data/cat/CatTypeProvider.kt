package zikxewen.productive_cats.data.cat

import net.minecraft.core.RegistrySetBuilder
import net.minecraft.data.worldgen.BootstrapContext
import net.minecraft.resources.ResourceKey
import net.minecraft.world.item.Rarity
import zikxewen.productive_cats.ProductiveCats
import zikxewen.productive_cats.common.cat.CatRegistries
import zikxewen.productive_cats.common.cat.CatType

object CatTypeProvider {
  val OAK_LOG = key("oak_log")
  val DUMMY = key("dummy")
  val SHINY = key("shiny")
  val ANCIENT = key("ancient")
  val builder = RegistrySetBuilder().add(CatRegistries.CAT_TYPE_REGISTRY) {
    Builder(OAK_LOG, 0, 0).save(it)
    Builder(DUMMY, 0, 0).save(it)
    Builder(SHINY, 0, 0).save(it)
    Builder(ANCIENT, 0, 0).unique().rarity(Rarity.EPIC).save(it)
  }
  class Builder(
    val key: ResourceKey<CatType>,
    val primaryColor: Int,
    val secondaryColor: Int,
  ) {
    var unique = false
    var rarity = Rarity.COMMON
    fun unique() = apply { this.unique = true }
    fun rarity(rarity: Rarity) = apply { this.rarity = rarity }
    fun save(bootstrap: BootstrapContext<CatType>) =
      bootstrap.register(
        key,
        CatType(key.location().path, primaryColor, secondaryColor, unique, rarity)
      )
  }
  fun key(type: String) = ResourceKey.create(CatRegistries.CAT_TYPE_REGISTRY, ProductiveCats.rl(type))
}
