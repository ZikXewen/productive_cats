package zikxewen.productive_cats.common.block

import net.minecraft.core.BlockPos
import net.minecraft.world.InteractionHand
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.BaseEntityBlock
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityTicker
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.BlockHitResult
import net.minecraft.world.InteractionResult
import zikxewen.productive_cats.common.item.ItemRegistries

class CatBreeder(props: BlockBehaviour.Properties) : Block(props), EntityBlock {
  override fun newBlockEntity(pos: BlockPos, state: BlockState) = CatBreederEntity(pos, state)
  override fun <T : BlockEntity> getTicker(
    level: Level,
    state: BlockState,
    type: BlockEntityType<T>
  ): BlockEntityTicker<T>? =
    BaseEntityBlock.createTickerHelper(type, BlockRegistries.CAT_BREEDER_ENTITY, CatBreederEntity::tick)
  override fun useItemOn(
    stack: ItemStack,
    state: BlockState,
    level: Level,
    pos: BlockPos,
    player: Player,
    hand: InteractionHand,
    hitResult: BlockHitResult
  ): InteractionResult {
    val be = level.getBlockEntity(pos)
    if (stack.item != ItemRegistries.CAT_HOLDER || be !is CatBreederEntity)
      return super.useItemOn(stack, state, level, pos, player, hand, hitResult)
    if (!level.isClientSide) be.useHolder(stack)
    return InteractionResult.SUCCESS
  }
}
