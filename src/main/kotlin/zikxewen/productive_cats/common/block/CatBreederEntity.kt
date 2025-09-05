package zikxewen.productive_cats.common.block

import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerLevel
import net.minecraft.util.RandomSource
import net.minecraft.world.entity.EntitySpawnReason
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import zikxewen.productive_cats.common.data.CatData
import zikxewen.productive_cats.common.data.DataRegistries
import zikxewen.productive_cats.common.entity.EntityRegistries
import zikxewen.productive_cats.common.recipe.CatBreedingRecipe
import zikxewen.productive_cats.common.recipe.RecipeRegistries

class CatBreederEntity(pos: BlockPos, state: BlockState) :
        BlockEntity(BlockRegistries.CAT_BREEDER_ENTITY, pos, state) {
  var cooldown = 0
  var progress = 0
  fun useHolder(stack: ItemStack) {
    val heldCat = stack.get(DataRegistries.CAT_DATA_COMPONENT)
    if (heldCat == null) {
      var beCat = this.removeData(DataRegistries.CAT_DATA_ATTACHMENT_2)
      if (beCat == null) beCat = this.removeData(DataRegistries.CAT_DATA_ATTACHMENT)
      if (beCat != null) stack.set(DataRegistries.CAT_DATA_COMPONENT, beCat)
    } else if (!this.hasData(DataRegistries.CAT_DATA_ATTACHMENT)) {
      this.setData(DataRegistries.CAT_DATA_ATTACHMENT, heldCat)
      stack.remove(DataRegistries.CAT_DATA_COMPONENT)
    } else if (!this.hasData(DataRegistries.CAT_DATA_ATTACHMENT_2)) {
      this.setData(DataRegistries.CAT_DATA_ATTACHMENT_2, heldCat)
      stack.remove(DataRegistries.CAT_DATA_COMPONENT)
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
      if (!be.hasData(DataRegistries.CAT_DATA_ATTACHMENT) || 
          !be.hasData(DataRegistries.CAT_DATA_ATTACHMENT_2)) {
        be.progress = 0
        return
      }
      if (++be.progress < 10) return
      be.progress = 0
      val parent1 = be.getData(DataRegistries.CAT_DATA_ATTACHMENT)
      val parent2 = be.getData(DataRegistries.CAT_DATA_ATTACHMENT_2)
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
      cat?.setData(DataRegistries.CAT_DATA_ATTACHMENT, childData)
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
