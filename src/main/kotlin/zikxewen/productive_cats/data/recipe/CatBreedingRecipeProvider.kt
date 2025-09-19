package zikxewen.productive_cats.data.recipe

import java.util.concurrent.CompletableFuture
import net.minecraft.advancements.Criterion
import net.minecraft.core.HolderLookup
import net.minecraft.core.registries.Registries
import net.minecraft.data.PackOutput
import net.minecraft.data.recipes.RecipeBuilder
import net.minecraft.data.recipes.RecipeOutput
import net.minecraft.data.recipes.RecipeProvider
import net.minecraft.resources.ResourceKey
import net.minecraft.world.item.Items
import net.minecraft.world.item.crafting.Recipe
import zikxewen.productive_cats.ProductiveCats
import zikxewen.productive_cats.common.cat.CatType
import zikxewen.productive_cats.common.recipe.CatBreedingRecipe
import zikxewen.productive_cats.data.cat.CatTypeProvider as Cats

class CatBreedingRecipeProvider(registries: HolderLookup.Provider, output: RecipeOutput) :
        RecipeProvider(registries, output) {
  override fun buildRecipes() {
    Builder(Cats.DUMMY, Cats.OAK_LOG, Cats.OAK_LOG).save(output)
    Builder(Cats.SHINY, Cats.OAK_LOG, Cats.DUMMY).save(output)
    Builder(Cats.ANCIENT, Cats.DUMMY, Cats.DUMMY).chance(50.0).save(output)
  }
  class Runner(output: PackOutput, registries: CompletableFuture<HolderLookup.Provider>) :
          RecipeProvider.Runner(output, registries) {
    override fun getName() = "Cat Breeding"
    override fun createRecipeProvider(registries: HolderLookup.Provider, output: RecipeOutput) =
            CatBreedingRecipeProvider(registries, output)
  }
  class Builder(val result: CatType, val parent1: CatType, val parent2: CatType) : RecipeBuilder {
    var group: String? = null
    var chance = 100.0
    override fun getResult() = Items.AIR
    override fun unlockedBy(name: String, criterion: Criterion<*>) = this
    override fun group(name: String?) = apply { this.group = name }
    override fun save(output: RecipeOutput, key: ResourceKey<Recipe<*>>) {
      val recipe = CatBreedingRecipe(result, parent1, parent2, chance)
      output.accept(key, recipe, null)
    }
    override fun save(output: RecipeOutput) = save(output, 0)
    fun save(output: RecipeOutput, variant: Int) {
      val key = ResourceKey.create(
        Registries.RECIPE, 
        ProductiveCats.rl("cat_breeding/${result}_${variant}")
      )
      save(output, key)
    }
    fun chance(chance: Double) = apply { this.chance = chance }
  }
}
