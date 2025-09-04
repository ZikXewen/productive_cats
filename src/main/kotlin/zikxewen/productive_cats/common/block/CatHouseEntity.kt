package zikxewen.productive_cats.common.block

import kotlin.jvm.optionals.getOrNull
import net.minecraft.core.BlockPos
import net.minecraft.core.NonNullList
import net.minecraft.server.level.ServerLevel
import net.minecraft.util.RandomSource
import net.minecraft.world.Containers
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.neoforged.neoforge.items.ItemStackHandler
import zikxewen.productive_cats.common.data.DataRegistries
import zikxewen.productive_cats.common.recipe.CatProduceRecipe
import zikxewen.productive_cats.common.recipe.RecipeRegistries

class CatHouseEntity(pos: BlockPos, state: BlockState) :
        BlockEntity(BlockRegistries.CAT_HOUSE_ENTITY, pos, state) {
  var cooldown = 0
  var progress = 0
  val inventory = NonNullList.withSize(9, ItemStack.EMPTY)
  val itemHandler = ItemHandler(inventory)
  fun useHolder(stack: ItemStack) {
    val heldCat = stack.get(DataRegistries.CAT_DATA_COMPONENT)
    if (heldCat == null) {
      var beCat = this.removeData(DataRegistries.CAT_DATA_ATTACHMENT)
      if (beCat != null) stack.set(DataRegistries.CAT_DATA_COMPONENT, beCat)
    } else if (!this.hasData(DataRegistries.CAT_DATA_ATTACHMENT)) {
      this.setData(DataRegistries.CAT_DATA_ATTACHMENT, heldCat)
      stack.remove(DataRegistries.CAT_DATA_COMPONENT)
    }
  }
  fun useWithoutItem(level: Level, pos: BlockPos) {
    Containers.dropContents(level, pos, inventory)
    inventory.clear()
  }
  override fun preRemoveSideEffects(pos: BlockPos, state: BlockState) {
    super.preRemoveSideEffects(pos, state)
    if (level != null) Containers.dropContents(level!!, pos, inventory)
  }
  companion object {
    fun tick(
      level: Level,
      @Suppress("UNUSED_PARAMETER") pos: BlockPos,
      @Suppress("UNUSED_PARAMETER") state: BlockState,
      be: CatHouseEntity
    ) {
      if (level !is ServerLevel || ++be.cooldown < 20) return
      be.cooldown = 0
      if (!be.hasData(DataRegistries.CAT_DATA_ATTACHMENT)) {
        be.progress = 0
        return
      }
      if (++be.progress < 10) return
      be.progress = 0
      val cat = be.getData(DataRegistries.CAT_DATA_ATTACHMENT)
      val input = CatProduceRecipe.Input(cat.type)
      val recipe = level.recipeAccess().getRecipeFor(RecipeRegistries.CAT_PRODUCE_TYPE, input, level).getOrNull()
      if (recipe == null) return
      recipe.value().result.forEach {
        val count = level.random.nextIntBetweenInclusive(it.min, it.max) * cat.productivity
        val stack = ItemStack(it.item, count)
        be.itemHandler.tryInsertItem(stack)
      }
    }
    private fun getChild(val1: Int, val2: Int, random: RandomSource): Int {
      if (val1 == val2) return val1
      val min = val1.coerceAtLeast(val2)
      val max = val1.coerceAtMost(val2)
      return (random.nextIntBetweenInclusive(min, max) + random.nextIntBetweenInclusive(-2, 2))
        .coerceAtLeast(1).coerceAtMost(10)
    }
  }
  inner class ItemHandler(inventory: NonNullList<ItemStack>) : ItemStackHandler(inventory) {
    override fun onContentsChanged(slot: Int) = setChanged()
    override fun insertItem(slot: Int, stack: ItemStack, simulate: Boolean) = stack
    fun tryInsertItem(stack: ItemStack): ItemStack {
      var newStack = stack
      (0..8).forEach { slot -> newStack = super.insertItem(slot, newStack, false)}
      return newStack
    }
  }
}
