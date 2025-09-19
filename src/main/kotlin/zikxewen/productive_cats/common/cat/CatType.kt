package zikxewen.productive_cats.common.cat

import com.mojang.serialization.Codec
import com.mojang.serialization.DataResult
import com.mojang.serialization.codecs.RecordCodecBuilder
import kotlin.jvm.optionals.getOrNull
import net.minecraft.core.ClientAsset
import net.minecraft.core.RegistryAccess
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.Style
import net.minecraft.network.codec.ByteBufCodecs
import net.minecraft.network.codec.StreamCodec
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
    fun getNameCodec(access: RegistryAccess) =
      Codec.STRING.comapFlatMap({
        val registry = access.lookup(CatRegistries.CAT_TYPE_REGISTRY).getOrNull()
        if (registry == null) return@comapFlatMap DataResult.error { "CAT_TYPE_REGISTRY is not registered. How did this happen?" }
        val catType = registry.getValue(ProductiveCats.rl(it))
        if (catType == null) return@comapFlatMap DataResult.error { "$it is not registered as a cat type." }
        DataResult.success(catType)
      }, CatType::type)
  }
}
