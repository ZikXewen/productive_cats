package zikxewen.productive_cats.common.block

import kotlin.jvm.optionals.getOrNull
import net.minecraft.core.BlockPos
import net.minecraft.core.NonNullList
import net.minecraft.core.component.DataComponents
import net.minecraft.server.level.ServerLevel
import net.minecraft.util.RandomSource
import net.minecraft.world.Containers
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.component.CustomData
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.storage.ValueInput
import net.minecraft.world.level.storage.ValueOutput
import net.neoforged.neoforge.items.ItemStackHandler
import zikxewen.productive_cats.common.data.CatData
import zikxewen.productive_cats.common.recipe.CatProduceRecipe
import zikxewen.productive_cats.common.recipe.RecipeRegistries

class CatHouseEntity(pos: BlockPos, state: BlockState) :
        BlockEntity(BlockRegistries.CAT_HOUSE_ENTITY, pos, state) {
  var cooldown = 0
  var progress = 0
  val inventory = NonNullList.withSize(9, ItemStack.EMPTY)
  val itemHandler = ItemHandler(inventory)
  var customData: CustomData? = null
  override fun saveAdditional(output: ValueOutput) {
    super.saveAdditional(output)
    output.storeNullable("custom_data", CustomData.CODEC, customData)
  }
  override fun loadAdditional(input: ValueInput) {
    super.loadAdditional(input)
    customData = input.read("custom_data", CustomData.CODEC).getOrNull()
  }
  fun useHolder(stack: ItemStack) {
    val heldData = stack.get(DataComponents.ENTITY_DATA)
    if (heldData == null && customData != null) {
      stack.set(DataComponents.ENTITY_DATA, customData)
      customData = null
    } else if (customData == null && heldData != null) {
      customData = heldData
      stack.remove(DataComponents.ENTITY_DATA)
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
      val cat = CatData.from(be.customData)
      if (cat == null) {
        be.progress = 0
        return
      }
      if (++be.progress < 10) return
      be.progress = 0
      val input = CatProduceRecipe.Input(cat.type)
      val recipe = level.recipeAccess().getRecipeFor(RecipeRegistries.CAT_PRODUCE_TYPE, input, level).getOrNull()
      if (recipe == null) return
      recipe.value().result.forEach {
        if (level.random.nextFloat() * 100.0 >= it.chance) return@forEach
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
