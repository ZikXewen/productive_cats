package zikxewen.productive_cats.common.data

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.codec.ByteBufCodecs
import net.minecraft.network.codec.StreamCodec
import net.minecraft.world.item.Rarity

class CatType (
  val type: String,
  val primaryColor: Int,
  val secondaryColor: Int,
  var unique: Boolean = false,
  var rarity: Rarity = Rarity.COMMON
) {
  fun equals(another: CatType) = type == another.type
  companion object {
    val DEFAULT = CatType("default", 0, 0)
    val CODEC = RecordCodecBuilder.create {
      it.group(
        Codec.STRING.fieldOf("type").forGetter(CatType::type),
        Codec.INT.fieldOf("primaryColor").forGetter(CatType::primaryColor),
        Codec.INT.fieldOf("secondaryColor").forGetter(CatType::secondaryColor),
        Codec.BOOL.optionalFieldOf("unique", false).forGetter(CatType::unique),
        Rarity.CODEC.optionalFieldOf("rarity", Rarity.COMMON).forGetter(CatType::rarity)
      ).apply(it, ::CatType)
    }
    val STREAM_CODEC: StreamCodec<RegistryFriendlyByteBuf, CatType> =
      ByteBufCodecs.fromCodecWithRegistries(CODEC)
  }
}
