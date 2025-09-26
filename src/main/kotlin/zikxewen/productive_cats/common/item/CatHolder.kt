package zikxewen.productive_cats.common.item

import java.util.function.Consumer
import net.minecraft.core.Direction
import net.minecraft.core.component.DataComponents
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntitySpawnReason
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.item.component.TooltipDisplay
import net.minecraft.world.item.context.UseOnContext
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.gameevent.GameEvent
import zikxewen.productive_cats.common.data.CatData
import zikxewen.productive_cats.common.entity.EntityRegistries
import zikxewen.productive_cats.common.entity.ProductiveCat

class CatHolder(props: Properties) : Item(props.stacksTo(1)) {
  override fun interactLivingEntity(
    stack: ItemStack,
    player: Player,
    entity: LivingEntity,
    hand: InteractionHand
  ): InteractionResult {
    super.interactLivingEntity(stack, player, entity, hand)
    if (entity !is ProductiveCat || entity.type != EntityRegistries.PRODUCTIVE_CAT) return InteractionResult.PASS // might be redundant
    val data = entity.catData
    if (data == null || stack.get(DataComponents.ENTITY_DATA) != null) return InteractionResult.PASS
    if (!player.level().isClientSide) {
      entity.remove(Entity.RemovalReason.DISCARDED)
      player.getItemInHand(hand).set(DataComponents.ENTITY_DATA, data.entityData)
    }
    return InteractionResult.SUCCESS
  }

  override fun useOn(ctx: UseOnContext): InteractionResult {
    super.useOn(ctx)
    val data = ctx.itemInHand.get(DataComponents.ENTITY_DATA)
    if (data == null) return InteractionResult.PASS
    if (ctx.level.isClientSide) return InteractionResult.SUCCESS
    val isBlockEmpty = ctx.level.getBlockState(ctx.clickedPos).getCollisionShape(ctx.level, ctx.clickedPos).isEmpty
    val cat = EntityRegistries.PRODUCTIVE_CAT.spawn(
      ctx.level as ServerLevel,
      ctx.itemInHand,
      ctx.player,
      if (isBlockEmpty) { ctx.clickedPos } else { ctx.clickedPos.relative(ctx.clickedFace) },
      EntitySpawnReason.SPAWN_ITEM_USE,
      true,
      isBlockEmpty && ctx.clickedFace == Direction.UP
    )
    if (cat == null) return InteractionResult.PASS
    ctx.itemInHand.remove(DataComponents.ENTITY_DATA)
    ctx.level.gameEvent(ctx.player, GameEvent.ENTITY_PLACE, ctx.clickedPos)
    return InteractionResult.SUCCESS
  }

  @Deprecated("Deprecated in Java")
  override fun appendHoverText(stack: ItemStack, ctx: TooltipContext, display: TooltipDisplay, adder: Consumer<Component>, flag: TooltipFlag) {
    val cat = CatData.from(stack.get(DataComponents.ENTITY_DATA))
    if (cat != null) {
      adder.accept(CatData.HELD_TEXT)
      adder.accept(cat.displayText)
    }
  }

  override fun getDestroySpeed(stack: ItemStack, block: BlockState) = 0f
}
