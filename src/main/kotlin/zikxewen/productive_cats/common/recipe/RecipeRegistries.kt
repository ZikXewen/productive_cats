package zikxewen.productive_cats.common.recipe

import net.minecraft.core.registries.Registries
import net.minecraft.world.item.crafting.RecipeBookCategory
import net.minecraft.world.item.crafting.RecipeSerializer
import net.minecraft.world.item.crafting.RecipeType
import net.neoforged.neoforge.registries.DeferredRegister
import thedarkcolour.kotlinforforge.neoforge.forge.getValue
import zikxewen.productive_cats.ProductiveCats

object RecipeRegistries {
  val TYPE_REGISTRY = DeferredRegister.create(Registries.RECIPE_TYPE, ProductiveCats.MOD_ID)
  val SERIALIZER_REGISTRY =
          DeferredRegister.create(Registries.RECIPE_SERIALIZER, ProductiveCats.MOD_ID)
  val CATEGORY_REGISTRY =
          DeferredRegister.create(Registries.RECIPE_BOOK_CATEGORY, ProductiveCats.MOD_ID)

  val CAT_BREEDING_TYPE: RecipeType<CatBreedingRecipe> by
          TYPE_REGISTRY.register("cat_breeding") { loc ->
            RecipeType.simple<CatBreedingRecipe>(loc)
          }
  val CAT_BREEDING_SERIALIZER: RecipeSerializer<CatBreedingRecipe> by
          SERIALIZER_REGISTRY.register("cat_breeding") { -> CatBreedingRecipe.Serializer() }
  val CAT_BREEDING_CATEGORY: RecipeBookCategory by
          CATEGORY_REGISTRY.register("cat_breeding") { -> CatBreedingRecipe.Category() }
}
