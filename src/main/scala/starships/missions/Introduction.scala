package starships.missions
import starships.Starships
import starships.geom._
import starships.hostiles._
import starships.obstacles._
import starships.projectiles._
import starships.traits._
import starships.upgrades._
import starships.weapons._
import starships.world.Spawner._
import starships.world._
case class Introduction(
    var name: String = "Introduction",
    var enemies: List[EnemyFactory] = List(PrecursorFactory),
    var finished: Boolean = false
) extends Mission {
  def load: Unit = {
    World.currentBoss = 0
  }
}
