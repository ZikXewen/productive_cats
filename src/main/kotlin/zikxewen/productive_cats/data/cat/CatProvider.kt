package zikxewen.productive_cats.data.cat

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import java.util.concurrent.CompletableFuture
import net.minecraft.core.HolderLookup
import net.minecraft.data.PackOutput
import net.minecraft.world.item.Rarity
import net.neoforged.neoforge.common.data.JsonCodecProvider
import zikxewen.productive_cats.ProductiveCats

class CatProvider(
  val output: PackOutput,
  provider: CompletableFuture<HolderLookup.Provider>
) : JsonCodecProvider<CatProvider.Data>(
  output,
  PackOutput.Target.DATA_PACK,
  "cat",
  Data.CODEC,
  provider,
  ProductiveCats.MOD_ID
) {
  override fun gather() {
    register("test_cat_1", 0, 0)
    register("test_cat_2", 0, 0)
    register("test_cat_3", 0, 0)
    register(Data("test_cat_4", 0, 0).unique().rarity(Rarity.EPIC))
  }
  fun register(cat: Data) = unconditional(ProductiveCats.rl(cat.type), cat)
  fun register(type: String, col1: Int, col2: Int) = register(Data(type, col1, col2))
  class Data(
    val type: String, 
    val primaryColor: Int,
    val secondaryColor: Int,
    var unique: Boolean = false, 
    var rarity: Rarity = Rarity.COMMON
  ) {
    companion object {
      val CODEC = RecordCodecBuilder.create {
        it.group(
          Codec.STRING.fieldOf("type").forGetter(Data::type),
          Codec.INT.fieldOf("primaryColor").forGetter(Data::primaryColor),
          Codec.INT.fieldOf("secondaryColor").forGetter(Data::secondaryColor),
          Codec.BOOL.optionalFieldOf("unique", false).forGetter(Data::unique),
          Rarity.CODEC.optionalFieldOf("rarity", Rarity.COMMON).forGetter(Data::rarity)
        ).apply(it, ::Data)
      }
    }
    fun unique() = apply { this.unique = true }
    fun rarity(rarity: Rarity) = apply { this.rarity = rarity }
  }
}
