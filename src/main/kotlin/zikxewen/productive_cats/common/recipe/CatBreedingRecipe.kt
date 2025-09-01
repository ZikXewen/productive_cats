package zikxewen.productive_cats.common.recipe

import com.mojang.serialization.MapCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.core.HolderLookup
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.codec.ByteBufCodecs
import net.minecraft.network.codec.StreamCodec
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.item.crafting.PlacementInfo
import net.minecraft.world.item.crafting.Recipe
import net.minecraft.world.item.crafting.RecipeBookCategory
import net.minecraft.world.item.crafting.RecipeInput
import net.minecraft.world.item.crafting.RecipeSerializer
import net.minecraft.world.level.Level

class CatBreedingRecipe(val parent1: Ingredient, val parent2: Ingredient, val result: ItemStack): Recipe<CatBreedingRecipe.Input> {
  override fun getSerializer() = RecipeRegistries.CAT_BREEDING_SERIALIZER
  override fun getType() = RecipeRegistries.CAT_BREEDING_TYPE
  override fun recipeBookCategory(): RecipeBookCategory = RecipeRegistries.CAT_BREEDING_CATEGORY
  override fun isSpecial() = true
  override fun assemble(input: CatBreedingRecipe.Input, registries: HolderLookup.Provider) = result.copy()
  override fun placementInfo() = PlacementInfo.NOT_PLACEABLE
  override fun matches(input: CatBreedingRecipe.Input, level: Level) =
    (parent1.test(input.getItem(0)) && parent2.test(input.getItem(1))) ||
    (parent1.test(input.getItem(1)) && parent2.test(input.getItem(0)))

  class Input(val cat1: ItemStack, val cat2: ItemStack): RecipeInput {
    override fun getItem(slot: Int) =
      when (slot) {
        0 -> cat1
        1 -> cat2
        else -> error("Breeding recipe should only have 2 slots (0 or 1), but received ${slot}")
      }
    override fun size() = 2
  }

  class Serializer: RecipeSerializer<CatBreedingRecipe> {
    companion object {
      val CODEC: MapCodec<CatBreedingRecipe> = RecordCodecBuilder.mapCodec {
        it.group(
          Ingredient.CODEC.fieldOf("parent1").forGetter(CatBreedingRecipe::parent1),
          Ingredient.CODEC.fieldOf("parent2").forGetter(CatBreedingRecipe::parent2),
          ItemStack.CODEC.fieldOf("result").forGetter(CatBreedingRecipe::result)
        ).apply(it, ::CatBreedingRecipe)
      }
      val STREAM_CODEC: StreamCodec<RegistryFriendlyByteBuf, CatBreedingRecipe> = ByteBufCodecs.fromCodecWithRegistries(CODEC.codec())
    }
    override fun codec() = CODEC
    override fun streamCodec() = STREAM_CODEC
  }

  class Category: RecipeBookCategory()
}
