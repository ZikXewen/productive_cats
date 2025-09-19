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
import net.minecraft.world.item.Item
import net.minecraft.world.item.Items
import net.minecraft.world.item.crafting.Recipe
import zikxewen.productive_cats.ProductiveCats
import zikxewen.productive_cats.common.recipe.CatProduceRecipe
import zikxewen.productive_cats.common.cat.CatType
import zikxewen.productive_cats.data.cat.CatTypeProvider as Cats

class CatProduceRecipeProvider(registries: HolderLookup.Provider, output: RecipeOutput) :
        RecipeProvider(registries, output) {
  override fun buildRecipes() {
    Builder(Cats.OAK_LOG).add(Items.OAK_LOG, 4).save(output)
    Builder(Cats.SHINY).add(Items.DIAMOND, 50.0, 2, 4).add(Items.GOLD_INGOT, 1, 3).save(output)
    Builder(Cats.ANCIENT).add(Items.ANCIENT_DEBRIS, 20.0, 1).save(output)
  }
  class Runner(output: PackOutput, registries: CompletableFuture<HolderLookup.Provider>) :
          RecipeProvider.Runner(output, registries) {
    override fun getName() = "Cat Produce"
    override fun createRecipeProvider(registries: HolderLookup.Provider, output: RecipeOutput) =
            CatProduceRecipeProvider(registries, output)
  }
  class Builder(val type: CatType) : RecipeBuilder {
    var group: String? = null
    var result: MutableList<CatProduceRecipe.Output> = mutableListOf()
    override fun getResult() = Items.AIR
    override fun unlockedBy(name: String, criterion: Criterion<*>) = this
    override fun group(name: String?) = apply { this.group = name }
    override fun save(output: RecipeOutput, key: ResourceKey<Recipe<*>>) {
      val recipe = CatProduceRecipe(result, type)
      output.accept(key, recipe, null)
    }
    override fun save(output: RecipeOutput) {
      val key = ResourceKey.create(Registries.RECIPE, ProductiveCats.rl("cat_produce/${type}"))
      save(output, key)
    }
    fun add(item: Item, chance: Double, min: Int, max: Int = min) = 
      apply { result.add(CatProduceRecipe.Output(item, chance, min, max)) }
    fun add(item: Item, min: Int, max: Int = min) = add(item, 100.0, min, max)
  }
}
