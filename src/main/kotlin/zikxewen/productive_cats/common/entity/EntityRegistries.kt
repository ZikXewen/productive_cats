package zikxewen.productive_cats.common.entity

import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.MobCategory
import net.neoforged.neoforge.registries.DeferredRegister
import thedarkcolour.kotlinforforge.neoforge.forge.getValue
import zikxewen.productive_cats.ProductiveCats

object EntityRegistries {
  val REGISTRY = DeferredRegister.createEntities(ProductiveCats.MOD_ID)
  val PRODUCTIVE_CAT: EntityType<ProductiveCat> by
          REGISTRY.registerEntityType("productive_cat", ::ProductiveCat, MobCategory.CREATURE)
}
