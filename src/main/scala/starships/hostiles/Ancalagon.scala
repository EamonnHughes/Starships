package starships.hostiles

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

case class Ancalagon(var health: Int, var location: Vec2, var size: Vec2)
    extends Boss {
  var goingUp = false
  var time: Long = System.currentTimeMillis

  def box: Box2 = Box2(location, size)
  def draw(p: PApplet): Unit = {
    p.fill(240, 100, 240)
    p.rect(location.x, location.y, size.x, size.y)
    p.fill(255, 0, 0)
    p.rect(100, 502, health * 20, 10)
  }
  def shoot: Unit = {
    val currentTime = System.currentTimeMillis
    if (currentTime > time + 400) {
      World.projectilesList = MachineGunProjectile(
        Vec2(location.x - 25, location.y + 10),
        Vec2(10, 10),
        -1
      ) :: World.projectilesList

      time = currentTime

    }
  }
  def move(timeFactor: Float): Unit = {
    if (!goingUp && box.bottom < 487) {
      location = location.addY(2 * timeFactor)
    } else if (!goingUp && box.bottom >= 487) {
      goingUp = true
    }
    if (goingUp && location.y > 25) {
      location = location.addY(-2 * timeFactor)
    } else if (goingUp && location.y <= 25) {
      goingUp = false
    }
  }
  def update(timeFactor: Float): Unit = {
    shoot
    move(timeFactor)
    checkForCollision
  }
  def checkForCollision: Unit = {
    for (i <- World.projectilesList) {
      if (box.intersects(i.box)) {
        health -= World.player.primary.damage
        World.projectilesList = World.projectilesList.filterNot(p => p == i)

        Starships.score += World.player.primary.damage
      }
    }
    if (health <= 0) {
      Spawner.isBossFight = false
      Spawner.hasFoughtBoss = true
      Starships.score += 100
    }

  }
}
