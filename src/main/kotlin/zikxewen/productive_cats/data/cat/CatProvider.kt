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
    Data("test_cat_1", 0, 0).save()
    Data("test_cat_2", 0, 0).save()
    Data("test_cat_3", 0, 0).save()
    Data("test_cat_4", 0, 0).unique().rarity(Rarity.EPIC).save()
  }
  fun Data.save() = unconditional(ProductiveCats.rl(this.type), this)
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
