package starships.projectiles

import processing.core.PApplet
import starships.Starships
import starships.geom._
import starships.hostiles._
import starships.obstacles._
import starships.projectiles._
import starships.traits._
import starships.upgrades._
import starships.weapons._
import starships.world._

case class MachineGunProjectile(
    var location: Vec2,
    var size: Vec2,
    var direction: Int
) extends Actor
    with Projectile {
  var prevLoc = location

  def draw(p: PApplet): Unit = {
    p.fill(255, 0, 0, 50)
    blur.drawBox(p)

    p.fill(255, 0, 0)
    box.drawBox(p)
  }

  def box: Box2 = Box2(location, size)
  def oldBox: Box2 = Box2(prevLoc, size)
  override def blur: Box2 = box.union(oldBox)

  def update(timeFactor: Float): Unit = {
    shootForward(timeFactor)
  }
  def shootForward(timeFactor: Float): Unit = {
    prevLoc = location
    location = location.addX(direction * 6 * timeFactor)
    if (
      location.x > 1024 || World.walls.exists(wall => box.intersects(wall.box))
    ) {
      World.projectilesList =
        World.projectilesList.filterNot(projectile => projectile == this)
    }

  }
}
