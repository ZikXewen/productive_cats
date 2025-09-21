package zikxewen.productive_cats.common.recipe

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.core.Holder
import net.minecraft.core.HolderLookup
import net.minecraft.network.codec.ByteBufCodecs
import net.minecraft.network.codec.StreamCodec
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.PlacementInfo
import net.minecraft.world.item.crafting.Recipe
import net.minecraft.world.item.crafting.RecipeBookCategory
import net.minecraft.world.item.crafting.RecipeInput
import net.minecraft.world.item.crafting.RecipeSerializer
import net.minecraft.world.level.Level
import zikxewen.productive_cats.common.cat.CatType

class CatProduceRecipe(val result: List<Output>, val cat: Holder<CatType>): Recipe<CatProduceRecipe.Input> {
  override fun getSerializer() = RecipeRegistries.CAT_PRODUCE_SERIALIZER
  override fun getType() = RecipeRegistries.CAT_PRODUCE_TYPE
  override fun recipeBookCategory() = RecipeRegistries.CAT_PRODUCE_CATEGORY
  override fun isSpecial() = true
  override fun assemble(input: Input, registries: HolderLookup.Provider) = ItemStack.EMPTY
  override fun placementInfo() = PlacementInfo.NOT_PLACEABLE
  override fun matches(input: Input, level: Level) = input.cat == cat

  class Input(val cat: Holder<CatType>): RecipeInput {
    override fun getItem(slot: Int) = ItemStack.EMPTY
    override fun size() = 0
    override fun isEmpty() = false
  }

  class Output(val item: Holder<Item>, val chance: Double, val min: Int, val max: Int) {
    companion object {
      val CODEC = RecordCodecBuilder.mapCodec {
        it.group(
          Item.CODEC.fieldOf("item").forGetter(Output::item),
          Codec.DOUBLE.optionalFieldOf("chance", 100.0).forGetter(Output::chance),
          Codec.INT.fieldOf("min").forGetter(Output::min),
          Codec.INT.fieldOf("max").forGetter(Output::max),
        ).apply(it, ::Output)
      }
      val STREAM_CODEC = StreamCodec.composite(
        Item.STREAM_CODEC, Output::item,
        ByteBufCodecs.DOUBLE, Output::chance,
        ByteBufCodecs.INT, Output::min,
        ByteBufCodecs.INT, Output::max,
        ::Output
      )
    }
  }

  class Serializer: RecipeSerializer<CatProduceRecipe> {
    companion object {
      val CODEC = RecordCodecBuilder.mapCodec {
        it.group(
          Output.CODEC.codec().listOf().fieldOf("result").forGetter(CatProduceRecipe::result),
          CatType.CODEC.fieldOf("cat").forGetter(CatProduceRecipe::cat)
        ).apply(it, ::CatProduceRecipe)
      }
      val STREAM_CODEC = StreamCodec.composite(
        Output.STREAM_CODEC.apply(ByteBufCodecs.list()), CatProduceRecipe::result,
        CatType.STREAM_CODEC, CatProduceRecipe::cat,
        ::CatProduceRecipe
      )
    }
    override fun codec() = CODEC
    override fun streamCodec() = STREAM_CODEC
  }

  class Category: RecipeBookCategory()
}
