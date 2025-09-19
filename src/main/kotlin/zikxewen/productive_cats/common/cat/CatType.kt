package zikxewen.productive_cats.common.cat

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.Style
import net.minecraft.network.codec.ByteBufCodecs
import net.minecraft.network.codec.StreamCodec
import net.minecraft.world.item.Rarity
import zikxewen.productive_cats.ProductiveCats

class CatType (
  val type: String,
  val primaryColor: Int,
  val secondaryColor: Int,
  val unique: Boolean = false,
  val rarity: Rarity = Rarity.COMMON
) {
  val displayText get() = Component.translatable("cat.${ProductiveCats.MOD_ID}.${type}").withStyle(Style.EMPTY.withColor(primaryColor))
  override fun equals(other: Any?) = other is CatType && type == other.type
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
    val STREAM_CODEC = StreamCodec.composite(
      ByteBufCodecs.STRING_UTF8, CatType::type, 
      ByteBufCodecs.INT, CatType::primaryColor, 
      ByteBufCodecs.INT, CatType::secondaryColor, 
      ByteBufCodecs.BOOL, CatType::unique,
      Rarity.STREAM_CODEC, CatType::rarity,
      ::CatType
    )
  }
}
