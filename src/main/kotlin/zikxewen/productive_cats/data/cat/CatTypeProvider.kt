package zikxewen.productive_cats.data.cat

import net.minecraft.core.RegistrySetBuilder
import net.minecraft.data.worldgen.BootstrapContext
import net.minecraft.resources.ResourceKey
import net.minecraft.world.item.Rarity
import zikxewen.productive_cats.ProductiveCats
import zikxewen.productive_cats.common.data.CatType
import zikxewen.productive_cats.common.data.DataRegistries

object CatTypeProvider {
  val builder =
    RegistrySetBuilder().add(DataRegistries.CAT_TYPE_KEY) {
      it.addCat(Builder("test_cat_1", 0, 0))
      it.addCat(Builder("test_cat_2", 0, 0))
      it.addCat(Builder("test_cat_3", 0, 0))
      it.addCat(Builder("test_cat_4", 0, 0).unique().rarity(Rarity.EPIC))
    }
  fun BootstrapContext<CatType>.addCat(builder: Builder) =
    this.register(
      ResourceKey.create(DataRegistries.CAT_TYPE_KEY, ProductiveCats.rl(builder.type.type)),
      builder.type
    )
  class Builder(
    type: String,
    primaryColor: Int,
    secondaryColor: Int,
    unique: Boolean = false,
    rarity: Rarity = Rarity.COMMON
  ) {
    val type = CatType(type, primaryColor, secondaryColor, unique, rarity)
    fun unique() = apply { type.unique = true }
    fun rarity(rarity: Rarity) = apply { type.rarity = rarity }
  }
}
