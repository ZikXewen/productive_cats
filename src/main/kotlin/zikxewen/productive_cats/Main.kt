package zikxewen.test_neoforge_kt

import net.neoforged.fml.common.Mod
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

import thedarkcolour.kotlinforforge.neoforge.forge.getValue
import thedarkcolour.kotlinforforge.neoforge.forge.MOD_BUS

@Mod(Main.ID)
object Main {
  const val ID = "productive_cats"
  val LOGGER: Logger = LogManager.getLogger(ID)
  init {
    LOGGER.log(Level.INFO, "Hello, World!")
  }
}
