package zikxewen.productive_cats.common.block

import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.BaseEntityBlock
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityTicker
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.block.state.BlockState

class CatBreeder(props: BlockBehaviour.Properties) : Block(props), EntityBlock {
  override fun newBlockEntity(pos: BlockPos, state: BlockState) = CatBreederEntity(pos, state)
  override fun <T : BlockEntity> getTicker(
          level: Level,
          state: BlockState,
          type: BlockEntityType<T>
  ): BlockEntityTicker<T>? =
    BaseEntityBlock.createTickerHelper(type, BlockRegistries.CAT_BREEDER_ENTITY, CatBreederEntity::tick)
}
