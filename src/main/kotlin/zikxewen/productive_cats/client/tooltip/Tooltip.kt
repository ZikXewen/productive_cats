package zikxewen.productive_cats.client.tooltip

import net.minecraft.network.chat.Component
import net.minecraft.network.chat.contents.TranslatableContents
import net.minecraft.world.item.ItemStack
import zikxewen.productive_cats.common.data.DataRegistries
import zikxewen.productive_cats.common.data.CatData
import zikxewen.productive_cats.common.item.ItemRegistries

object Tooltip {
  fun modifyTooltip(stack: ItemStack, tooltip: MutableList<Component>) {
    if (stack.`is`(ItemRegistries.CAT_HOLDER)) {
      val cat = stack.get(DataRegistries.CAT_DATA_COMPONENT)
      if (cat == null) return
      tooltip.add(CatData.HELD_TEXT)
      tooltip.add(cat.display)
    }
  }
}
