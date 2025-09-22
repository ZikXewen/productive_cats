package zikxewen.productive_cats.common.block

import kotlin.jvm.optionals.getOrNull
import net.minecraft.core.BlockPos
import net.minecraft.core.component.DataComponents
import net.minecraft.nbt.CompoundTag
import net.minecraft.server.level.ServerLevel
import net.minecraft.util.RandomSource
import net.minecraft.world.entity.EntitySpawnReason
import net.minecraft.world.entity.EntityType
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.component.CustomData
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.storage.ValueInput
import net.minecraft.world.level.storage.ValueOutput
import zikxewen.productive_cats.common.data.CatData
import zikxewen.productive_cats.common.entity.EntityRegistries
import zikxewen.productive_cats.common.recipe.CatBreedingRecipe
import zikxewen.productive_cats.common.recipe.RecipeRegistries

class CatBreederEntity(pos: BlockPos, state: BlockState) :
        BlockEntity(BlockRegistries.CAT_BREEDER_ENTITY, pos, state) {
  var cooldown = 0
  var progress = 0
  var customData: CustomData? = null
  var customData2: CustomData? = null
  override fun saveAdditional(output: ValueOutput) {
    super.saveAdditional(output)
    output.storeNullable("custom_data", CustomData.CODEC, customData)
    output.storeNullable("custom_data_2", CustomData.CODEC, customData2)
  }
  override fun loadAdditional(input: ValueInput) {
    super.loadAdditional(input)
    customData = input.read("custom_data", CustomData.CODEC).getOrNull()
    customData2 = input.read("custom_data_2", CustomData.CODEC).getOrNull()
  }
  fun useHolder(stack: ItemStack) {
    val heldData = stack.get(DataComponents.ENTITY_DATA)
    if (heldData == null) {
      if (customData2 != null) {
        stack.set(DataComponents.ENTITY_DATA, customData2)
        customData2 = null
      } else if (customData != null) {
        stack.set(DataComponents.ENTITY_DATA, customData)
        customData = null
      }
    } else {
      if (customData != null) {
        customData = heldData
        stack.remove(DataComponents.ENTITY_DATA)
      } else if (customData2 != null) {
        customData2 = heldData
        stack.remove(DataComponents.ENTITY_DATA)
      }
    }
  }
  companion object {
    fun tick(
      level: Level,
      pos: BlockPos,
      @Suppress("UNUSED_PARAMETER") state: BlockState,
      be: CatBreederEntity
    ) {
      if (level !is ServerLevel || ++be.cooldown < 20) return
      be.cooldown = 0
      val parent1 = CatData.from(be.customData)
      val parent2 = CatData.from(be.customData2)
      if (parent1 == null || parent2 == null) {
        be.progress = 0
        return
      }
      if (++be.progress < 10) return
      be.progress = 0
      val input = CatBreedingRecipe.Input(parent1.type, parent2.type)
      val matches = level.recipeAccess().recipeMap().getRecipesFor(RecipeRegistries.CAT_BREEDING_TYPE, input, level)
        .filter { level.random.nextDouble() * 100.0 < it.value().chance }.map{ it.value().result }.toList()
      val childType = 
        if (matches.isEmpty()) { if (level.random.nextBoolean()) parent1.type else parent2.type }
        else matches[level.random.nextInt(matches.size)]
      val childSpeed = getChild(parent1.speed, parent2.speed, level.random)
      val childProductivity = getChild(parent1.productivity, parent2.productivity, level.random)
      val childData = CatData(childType, childSpeed, childProductivity)
      val cat = EntityRegistries.PRODUCTIVE_CAT.spawn(level, pos, EntitySpawnReason.BREEDING)
      EntityType.updateCustomEntityTag(level, null, cat, childData.toCustomData())
    }
    private fun getChild(val1: Int, val2: Int, random: RandomSource): Int {
      val min = val1.coerceAtMost(val2)
      val max = val1.coerceAtLeast(val2)
      val base = random.nextIntBetweenInclusive(min, max)
      val bias = random.nextIntBetweenInclusive(-2, 2)
      return (base + bias).coerceAtLeast(1).coerceAtMost(10)
    }
  }
}
