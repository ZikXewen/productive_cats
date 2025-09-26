package zikxewen.productive_cats.common.data

import kotlin.jvm.optionals.getOrNull
import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.core.Holder
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.Style
import net.minecraft.world.item.DyeColor
import net.minecraft.world.item.component.CustomData
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.entity.EntityType
import zikxewen.productive_cats.ProductiveCats
import zikxewen.productive_cats.common.cat.CatType
import zikxewen.productive_cats.common.entity.EntityRegistries

class CatData(val type: Holder<CatType>, val speed: Int, val productivity: Int) {
  val displayText get() = Component.translatable(DISPLAY_KEY, type.value().displayText, speed, productivity)
  val entityData get() = CustomData.of(CompoundTag().also {
    it.store("id", EntityType.CODEC, EntityRegistries.PRODUCTIVE_CAT)
    it.store("CatData", CODEC.codec(), this)
  })
  companion object {
    val DISPLAY_KEY = "tooltip.${ProductiveCats.MOD_ID}.cat_data"
    val HELD_KEY = "tooltip.${ProductiveCats.MOD_ID}.held"
    val HELD_TEXT = Component.translatable(HELD_KEY).withStyle(Style.EMPTY.withColor(DyeColor.GRAY.textColor))
    val CODEC = RecordCodecBuilder.mapCodec {
      it.group(
        CatType.CODEC.fieldOf("type").forGetter(CatData::type),
        Codec.INT.fieldOf("speed").forGetter(CatData::speed),
        Codec.INT.fieldOf("productivity").forGetter(CatData::productivity),
      ).apply(it, ::CatData)
    }
    fun from(customData: CustomData?) = customData?.unsafe?.read("CatData", CODEC.codec())?.getOrNull()
  }
}
