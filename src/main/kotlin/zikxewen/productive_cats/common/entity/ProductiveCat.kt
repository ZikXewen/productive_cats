package zikxewen.productive_cats.common.entity

import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.animal.Cat
import net.minecraft.world.level.Level

class ProductiveCat(entityType: EntityType<out Cat>, level: Level) : Cat(entityType, level) {}
