package zikxewen.productive_cats.client.entity_renderer

import net.minecraft.client.model.CatModel
import net.minecraft.client.model.geom.ModelLayers
import net.minecraft.client.renderer.entity.EntityRendererProvider
import net.minecraft.client.renderer.entity.MobRenderer
import net.minecraft.client.renderer.entity.state.CatRenderState
import zikxewen.productive_cats.common.cat.CatType
import zikxewen.productive_cats.common.entity.ProductiveCat

class ProductiveCatRenderer(ctx: EntityRendererProvider.Context) :
  MobRenderer<ProductiveCat, CatRenderState, CatModel>(
    ctx,
    CatModel(ctx.bakeLayer(ModelLayers.CAT)),
    0.4F
  ) {
  override fun createRenderState() = CatRenderState()
  override fun getTextureLocation(state: CatRenderState) = state.texture
  override fun extractRenderState(cat: ProductiveCat, state: CatRenderState, scale: Float) {
    super.extractRenderState(cat, state, scale)
    state.apply {
      val type = cat.catData?.type?.value() ?: CatType.DEFAULT
      texture = type.texture.texturePath()
      isCrouching = cat.isCrouching()
      isSprinting = cat.isSprinting()
    }
  }
}
