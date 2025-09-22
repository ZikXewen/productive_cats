package zikxewen.productive_cats.common.entity

import net.minecraft.core.component.DataComponents
import net.minecraft.server.level.ServerLevel
import net.minecraft.sounds.SoundEvents
import net.minecraft.tags.ItemTags
import net.minecraft.world.damagesource.DamageSource
import net.minecraft.world.entity.AgeableMob
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.ai.attributes.Attributes
import net.minecraft.world.entity.ai.goal.*
import net.minecraft.world.entity.animal.Animal
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import zikxewen.productive_cats.common.data.CatData

// No taming, no in-world breeding
class ProductiveCat(entityType: EntityType<out ProductiveCat>, level: Level) :
        Animal(entityType, level) {
  companion object {
    fun createAttributes() =
            Animal.createAnimalAttributes()
                    .add(Attributes.MAX_HEALTH, 10.0)
                    .add(Attributes.MOVEMENT_SPEED, 0.3)
                    .add(Attributes.ATTACK_DAMAGE, 3.0)
  }
  override fun registerGoals() {
    goalSelector.addGoal(0, FloatGoal(this))
    goalSelector.addGoal(1, PanicGoal(this, 1.5))
    goalSelector.addGoal(2, TemptGoal(this, 1.0, { it.`is`(ItemTags.CAT_FOOD) }, false))
    goalSelector.addGoal(3, WaterAvoidingRandomStrollGoal(this, 0.8, 1E-5F))
    goalSelector.addGoal(4, LookAtPlayerGoal(this, Player::class.java, 10.0F))
  }
  override fun getBreedOffspring(p0: ServerLevel, p1: AgeableMob) = null
  override fun isFood(p0: ItemStack) = false
  override fun getAmbientSound() =
          when (random.nextInt(8)) {
            0 -> SoundEvents.CAT_PURREOW
            in 1..3 -> SoundEvents.CAT_AMBIENT
            else -> SoundEvents.CAT_STRAY_AMBIENT
          }
  override fun getHurtSound(damageSource: DamageSource) = SoundEvents.CAT_HURT
  override fun getDeathSound() = SoundEvents.CAT_DEATH
}
