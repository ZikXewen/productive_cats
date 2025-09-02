package zikxewen.productive_cats.common.item

import net.minecraft.server.level.ServerLevel
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntitySpawnReason
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.context.UseOnContext
import net.minecraft.world.level.block.state.BlockState
import zikxewen.productive_cats.common.data.DataRegistries
import zikxewen.productive_cats.common.entity.EntityRegistries

class CatHolder(props: Properties) : Item(props.stacksTo(1)) {
  override fun interactLivingEntity(
    stack: ItemStack,
    player: Player,
    entity: LivingEntity,
    hand: InteractionHand
  ): InteractionResult {
    super.interactLivingEntity(stack, player, entity, hand)
    if (entity.type != EntityRegistries.PRODUCTIVE_CAT || stack.get(DataRegistries.CAT_DATA_COMPONENT) != null) return InteractionResult.PASS
    if (!player.level().isClientSide) {
      val data = entity.getData(DataRegistries.CAT_DATA_ATTACHMENT)
      entity.remove(Entity.RemovalReason.DISCARDED)
      val newStack = ItemStack(ItemRegistries.CAT_HOLDER)
      newStack.set(DataRegistries.CAT_DATA_COMPONENT, data)
      if(!player.addItem(newStack)) player.drop(newStack, false)
      stack.shrink(1)
    }
    return InteractionResult.SUCCESS
  }

  override fun useOn(ctx: UseOnContext): InteractionResult {
    super.useOn(ctx)
    val data = ctx.itemInHand.get(DataRegistries.CAT_DATA_COMPONENT)
    if (data == null) return InteractionResult.PASS
    if (!ctx.level.isClientSide) {
      val cat = EntityRegistries.PRODUCTIVE_CAT.spawn(ctx.level as ServerLevel, ctx.clickedPos.relative(ctx.clickedFace), EntitySpawnReason.SPAWN_ITEM_USE)
      if (cat == null) return InteractionResult.PASS
      cat.setData(DataRegistries.CAT_DATA_ATTACHMENT, data)
      val newStack = ItemStack(ItemRegistries.CAT_HOLDER)
      if(!(ctx.player?.addItem(newStack) ?: false)) ctx.player?.drop(newStack, false)
      ctx.itemInHand.shrink(1)
    }
    return InteractionResult.SUCCESS
  }

  override fun getDestroySpeed(stack: ItemStack, block: BlockState) = 0f
}
