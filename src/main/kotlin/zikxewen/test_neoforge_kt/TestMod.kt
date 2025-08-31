package zikxewen.test_neoforge_kt

import net.neoforged.fml.common.Mod
import net.neoforged.neoforge.registries.DeferredRegister
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

import thedarkcolour.kotlinforforge.neoforge.forge.getValue
import thedarkcolour.kotlinforforge.neoforge.forge.MOD_BUS

@Mod(TestMod.ID)
object TestMod {
  const val ID = "test_neoforge_kt"
  val ITEM_REGISTRY = DeferredRegister.createItems(ID)
  val TEST_ITEM by ITEM_REGISTRY.registerSimpleItem("test_item")
  val LOGGER: Logger = LogManager.getLogger(ID)
  init {
    LOGGER.log(Level.INFO, "Hello, World!")
    ITEM_REGISTRY.register(MOD_BUS)
  }
}
