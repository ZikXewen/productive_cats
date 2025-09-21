package zikxewen.productive_cats.common.cat

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.core.ClientAsset
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.Style
import net.minecraft.network.codec.ByteBufCodecs
import net.minecraft.resources.RegistryFixedCodec
import net.minecraft.world.entity.EntityType
import net.minecraft.world.item.Rarity
import zikxewen.productive_cats.ProductiveCats
import zikxewen.productive_cats.common.entity.EntityRegistries

class CatType (
  val type: String,
  val primaryColor: Int,
  val secondaryColor: Int,
  val unique: Boolean = false,
  val rarity: Rarity = Rarity.COMMON
) {
  val texture by lazy { ClientAsset(EntityType.getKey(EntityRegistries.PRODUCTIVE_CAT).withSuffix(type)) } // should be client only
  val displayKey get() = "cat.${ProductiveCats.MOD_ID}.${type}"
  val displayText get() = Component.translatable(displayKey).withStyle(Style.EMPTY.withColor(primaryColor))
  override fun equals(other: Any?) = other is CatType && type == other.type
  companion object {
    val DEFAULT = CatType("default", 0, 0)
    val DIRECT_CODEC = RecordCodecBuilder.mapCodec {
      it.group(
        Codec.STRING.fieldOf("type").forGetter(CatType::type),
        Codec.INT.fieldOf("primaryColor").forGetter(CatType::primaryColor),
        Codec.INT.fieldOf("secondaryColor").forGetter(CatType::secondaryColor),
        Codec.BOOL.optionalFieldOf("unique", false).forGetter(CatType::unique),
        Rarity.CODEC.optionalFieldOf("rarity", Rarity.COMMON).forGetter(CatType::rarity)
      ).apply(it, ::CatType)
    }
    val CODEC = RegistryFixedCodec.create(CatRegistries.CAT_TYPE_REGISTRY)
    val STREAM_CODEC = ByteBufCodecs.holderRegistry(CatRegistries.CAT_TYPE_REGISTRY)
  }
}
