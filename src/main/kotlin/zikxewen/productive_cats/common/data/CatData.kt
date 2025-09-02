package zikxewen.productive_cats.common.data

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.network.codec.ByteBufCodecs
import net.minecraft.network.codec.StreamCodec

data class CatData(val type: String, val speed: Int, val productivity: Int) {
  companion object {
    val DEFAULT = CatData("default", 1, 1)
    val CODEC = RecordCodecBuilder.create {
      it.group(
        Codec.STRING.fieldOf("type").forGetter(CatData::type),
        Codec.INT.fieldOf("speed").forGetter(CatData::speed),
        Codec.INT.fieldOf("productivity").forGetter(CatData::productivity),
      ).apply(it, ::CatData)
    }
    val STREAM_CODEC = StreamCodec.composite(
      ByteBufCodecs.STRING_UTF8, CatData::type, 
      ByteBufCodecs.INT, CatData::speed, 
      ByteBufCodecs.INT, CatData::productivity, ::CatData
    )
  }
}
