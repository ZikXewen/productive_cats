package zikxewen.productive_cats.common.recipe

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.core.Holder
import net.minecraft.core.HolderLookup
import net.minecraft.network.codec.ByteBufCodecs
import net.minecraft.network.codec.StreamCodec
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.PlacementInfo
import net.minecraft.world.item.crafting.Recipe
import net.minecraft.world.item.crafting.RecipeBookCategory
import net.minecraft.world.item.crafting.RecipeInput
import net.minecraft.world.item.crafting.RecipeSerializer
import net.minecraft.world.level.Level
import zikxewen.productive_cats.common.cat.CatType

class CatBreedingRecipe(val result: Holder<CatType>, val parent1: Holder<CatType>, val parent2: Holder<CatType>, val chance: Double): Recipe<CatBreedingRecipe.Input> {
  override fun getSerializer() = RecipeRegistries.CAT_BREEDING_SERIALIZER
  override fun getType() = RecipeRegistries.CAT_BREEDING_TYPE
  override fun recipeBookCategory() = RecipeRegistries.CAT_BREEDING_CATEGORY
  override fun isSpecial() = true
  override fun assemble(input: Input, registries: HolderLookup.Provider) = ItemStack.EMPTY
  override fun placementInfo() = PlacementInfo.NOT_PLACEABLE
  override fun matches(input: Input, level: Level) =
    (input.parent1 == parent1 && input.parent2 == parent2) ||
    (input.parent1 == parent2 && input.parent2 == parent1)

  class Input(val parent1: Holder<CatType>, val parent2: Holder<CatType>): RecipeInput {
    override fun getItem(slot: Int) = ItemStack.EMPTY
    override fun size() = 0
    override fun isEmpty() = false
  }

  class Serializer: RecipeSerializer<CatBreedingRecipe> {
    companion object {
      val CODEC = RecordCodecBuilder.mapCodec {
        it.group(
          CatType.CODEC.fieldOf("result").forGetter(CatBreedingRecipe::result),
          CatType.CODEC.fieldOf("parent1").forGetter(CatBreedingRecipe::parent1),
          CatType.CODEC.fieldOf("parent2").forGetter(CatBreedingRecipe::parent2),
          Codec.DOUBLE.fieldOf("chance").forGetter(CatBreedingRecipe::chance)
        ).apply(it, ::CatBreedingRecipe)
      }
      val STREAM_CODEC = StreamCodec.composite(
        CatType.STREAM_CODEC, CatBreedingRecipe::result,
        CatType.STREAM_CODEC, CatBreedingRecipe::parent1,
        CatType.STREAM_CODEC, CatBreedingRecipe::parent2,
        ByteBufCodecs.DOUBLE, CatBreedingRecipe::chance,
        ::CatBreedingRecipe
      )
    }
    override fun codec() = CODEC
    override fun streamCodec() = STREAM_CODEC
  }

  class Category: RecipeBookCategory()
}
