package zikxewen.productive_cats.common.data

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.core.Holder
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.Style
import net.minecraft.network.codec.ByteBufCodecs
import net.minecraft.network.codec.StreamCodec
import net.minecraft.world.item.DyeColor
import zikxewen.productive_cats.ProductiveCats
import zikxewen.productive_cats.common.cat.CatType

data class CatData(val type: Holder<CatType>, val speed: Int, val productivity: Int) {
  val displayText get() = Component.translatable(DISPLAY_KEY, type.value().displayText, speed, productivity)
  companion object {
    val DISPLAY_KEY = "tooltip.${ProductiveCats.MOD_ID}.cat_data"
    val HELD_KEY = "tooltip.${ProductiveCats.MOD_ID}.held"
    val HELD_TEXT = Component.translatable(HELD_KEY).withStyle(Style.EMPTY.withColor(DyeColor.GRAY.textColor))
    val DEFAULT = CatData(Holder.direct(CatType.DEFAULT), 0, 0)
    val CODEC = RecordCodecBuilder.mapCodec {
      it.group(
        CatType.CODEC.fieldOf("type").forGetter(CatData::type),
        Codec.INT.fieldOf("speed").forGetter(CatData::speed),
        Codec.INT.fieldOf("productivity").forGetter(CatData::productivity),
      ).apply(it, ::CatData)
    }
    val STREAM_CODEC = StreamCodec.composite(
      CatType.STREAM_CODEC, CatData::type,
      ByteBufCodecs.INT, CatData::speed, 
      ByteBufCodecs.INT, CatData::productivity,
      ::CatData
    )
  }
}
