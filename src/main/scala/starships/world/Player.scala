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
  var isDead = false
  var invuln = false
  var time: Long = System.currentTimeMillis
  def box: Box2 = Box2(Vec2(0, 0), Vec2(40, 40))
  def draw(p: PApplet): Unit = {

    if (!isDead) {
      if (!invuln) {
        p.tint(255, 255, 255, 255)
        p.image(Player.Swordfish, location.x, location.y, 40, 40)
      } else {
        p.tint(255, 255, 255, 70)
        p.image(Player.Swordfish, location.x, location.y, 40, 40)
      }
    }
  }

  def update(timeFactor: Float): Unit = {
    World.player.primary = World.weaponList(World.selectWeapon)
    if (location.x == 64) {
      invuln = false
    }
    if (!invuln) {
      checkForCollision()
    }
    if (!isDead) {
      moving(timeFactor)
      if (Controls.shooting) {
        shooting()
      }
      primary.special()
    }
  }
  def respawn: Unit = {
    invuln = true
    location = location.set(-120, 256)

    velocity = 0

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
          .intersects(
            new Box2(
              wall.location.x + wall.box.left,
              wall.location.y + wall.box.top,
              wall.box.width,
              wall.box.height
            )
          )
      ) || box.top + location.y < 20 || box.bottom + location.y > 492
    ) {
      lives -= 1
      World.explosions = Explosion(location.copy(), 0, 4) :: World.explosions
      respawn
    }
    for (i <- World.enemies) {
      if (
        new Box2(
          location.x + box.left,
          location.y + box.top,
          box.width,
          box.height
        ).intersects(
          new Box2(
            i.location.x + i.box.left,
            i.location.y + i.box.top,
            i.box.width,
            i.box.height
          )
        )
      ) {
        lives -= 1
        World.explosions = Explosion(location.copy(), 0, 4) :: World.explosions
        respawn
        i.health = 0
        Starships.score += (i.enemyQuantity * 4).toInt
      }
    }
    for (i <- World.projectilesList) {
      if (
        new Box2(
          location.x + box.left,
          location.y + box.top,
          box.width,
          box.height
        ).intersects(
          new Box2(
            i.location.x + i.box.left,
            i.location.y + i.box.top,
            i.box.width,
            i.box.height
          )
        ) && i.direction < 0
      ) {
        lives -= 1
        World.explosions = Explosion(location.copy(), 0, 4) :: World.explosions
        respawn
        World.projectilesList = World.projectilesList.filterNot(p => p == i)
      }
    }
    if (lives <= 0) {

      val currentTime = System.currentTimeMillis

      isDead = true
      if (currentTime > time + 2000) {
        Starships.state = GameState.Home
      }
    }
  }
  def moving(timeFactor: Float): Unit = {
    if (Controls.wPressed != Controls.sPressed) {
      velocity = clamp(velocity + (if (Controls.wPressed) -0.5f else 0.5f), 5f)

    } else
      velocity = velocity * deceleration
    location = location.addY(velocity * timeFactor)
    if (location.x < 64) {
      location.x += 2
    }
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
