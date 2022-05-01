package starships.world

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

case class Player(
    var location: Vec2,
    var velocity: Float,
    var deceleration: Float,
    var lives: Int,
    var primary: Weapon
) extends Actor {

  def box: Box2 = Box2(Vec2(0, 0), Vec2(40, 40))
  def draw(p: PApplet): Unit = {

    p.image(Player.Swordfish, location.x, location.y, 40, 40)

  }

  def update(timeFactor: Float): Unit = {
    World.player.primary = World.weaponList(World.selectWeapon)
    checkForCollision()
    moving(timeFactor)
    if (Controls.shooting) {
      shooting()
    }
    primary.special()
  }
  def checkForCollision(): Unit = {
    if (
      World.walls.exists(wall =>
        new Box2(
          location.x + box.left,
          location.y + box.top,
          box.width,
          box.height
        )
          .intersects(wall.blur)
      ) || box.top < 20 || box.bottom > 492
    ) {
      lives -= 1
      location = location.setY(256)
      velocity = 0
    }
    for (i <- World.enemies) {
      if (
        new Box2(
          location.x + box.left,
          location.y + box.top,
          box.width,
          box.height
        ).intersects(i.blur)
      ) {
        lives -= 1
        location = location.setY(256)
        velocity = 0
        World.enemies = World.enemies.filterNot(p => p == i)
      }
    }
    for (i <- World.projectilesList) {
      if (
        new Box2(
          location.x + box.left,
          location.y + box.top,
          box.width,
          box.height
        ).intersects(i.blur) && i.direction < 0
      ) {
        lives -= 1
        location = location.setY(256)
        velocity = 0
        World.projectilesList = World.projectilesList.filterNot(p => p == i)
      }
    }
    if (lives <= 0) {
      println("YOU DIED! SCORE: " + Starships.score)
      Starships.state = "Home"
    }
  }
  def moving(timeFactor: Float): Unit = {
    if (Controls.wPressed != Controls.sPressed) {
      velocity = clamp(velocity + (if (Controls.wPressed) -0.5f else 0.5f), 5f)

    } else
      velocity = velocity * deceleration
    location = location.addY(velocity * timeFactor)
  }

  def clamp(value: Float, max: Float) = {
    if (value > max) max
    else if (value < -max) -max
    else value
  }

  def shooting(): Unit = {
    primary.shoot()
  }

}

object Player {
  var Swordfish: PImage = _
  def loadImages(p: PApplet): Unit = {
    Swordfish = p.loadImage("src/main/Resources/Swordfish.png")

  }

}