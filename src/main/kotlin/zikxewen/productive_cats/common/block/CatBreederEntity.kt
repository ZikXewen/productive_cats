package zikxewen.productive_cats.common.block

import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class CatBreederEntity(pos: BlockPos, state: BlockState) :
        BlockEntity(BlockRegistries.CAT_BREEDER_ENTITY, pos, state) {
  companion object {
    fun tick(level: Level, pos: BlockPos, state: BlockState, blockEntity: CatBreederEntity) {}
  }
}
