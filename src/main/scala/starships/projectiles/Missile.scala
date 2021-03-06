package starships.projectiles

import processing.core.{PApplet, PImage}
import starships.Starships
import starships.geom._
import starships.hostiles._
import starships.obstacles._
import starships.projectiles._
import starships.traits._
import starships.upgrades._
import starships.weapons._
import starships.world._

case class Missile(
    var location: Vec2,
    var target: Actor,
    var velX: Float,
    var velY: Float,
    var direction: Int
) extends Actor
    with Projectile {

  def box: Box2 = Box2(0, 0, 10, 10)
  def draw(p: PApplet): Unit = {
    p.fill(155, 155, 155)
    p.image(Missile.Missile, location.x, location.y, 10, 10)

  }
  def update(timeFactor: Float): Unit = {
    shootForward(timeFactor)
  }
  def shootForward(timeFactor: Float): Unit = {

    if (target.location.y > location.y) {
      velY = clamp(velY + 0.05f, 4f)
    } else if (target.location.y + target.box.height < location.y) {
      velY = clamp(velY - 0.05f, 4f)
    } else {
      velY *= 0.7f
    }
    if (
      location.x > 1024 || World.walls.exists(wall =>
        new Box2(
          location.x + box.left,
          location.y + box.top,
          box.width,
          box.height
        ).intersects(
          new Box2(
            wall.location.x + wall.box.left,
            wall.location.y + wall.box.top,
            wall.box.width,
            wall.box.height
          )
        )
      ) || location.y < 20 || location.y + box.bottom > 492
    ) {
      World.projectilesList =
        World.projectilesList.filterNot(projectile => projectile == this)
    }

    location = location.add(velX * timeFactor, velY * timeFactor)
    velX += .03f * direction.toFloat

  }
  def clamp(value: Float, max: Float) = {
    if (value > max) max
    else if (value < -max) -max
    else value
  }
}

object Missile {
  var Missile: PImage = _
  def loadImages(p: PApplet): Unit = {
    Missile = p.loadImage("src/main/Resources/Missile.png")

  }

}
