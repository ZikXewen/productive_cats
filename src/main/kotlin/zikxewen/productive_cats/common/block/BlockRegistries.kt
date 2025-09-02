package zikxewen.productive_cats.common.block

import net.minecraft.core.registries.Registries
import net.minecraft.world.level.block.entity.BlockEntityType
import net.neoforged.neoforge.registries.DeferredRegister
import thedarkcolour.kotlinforforge.neoforge.forge.getValue
import zikxewen.productive_cats.ProductiveCats

object BlockRegistries {
  val REGISTRY = DeferredRegister.createBlocks(ProductiveCats.MOD_ID)
  val BE_REGISTRY = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, ProductiveCats.MOD_ID)
  val CAT_BREEDER: CatBreeder by REGISTRY.registerBlock("cat_breeder", ::CatBreeder)
  val CAT_BREEDER_ENTITY: BlockEntityType<CatBreederEntity> by
          BE_REGISTRY.register("cat_breeder") { ->
            BlockEntityType(::CatBreederEntity, CAT_BREEDER)
          }
}
