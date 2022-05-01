package starships.world

import starships.Starships
import starships.geom._
import starships.hostiles._
import starships.obstacles._
import starships.projectiles._
import starships.traits._
import starships.upgrades._
import starships.weapons._
import starships.world._

object World {
  var player =
    Player(Vec2(64, 256), 0, 0.9f, 3, MachineGun(50, 1, 0))
  var projectilesList = List.empty[Projectile]
  var upgradeList = List.empty[Upgrade]
  var walls = List.empty[Wall]
  var worldBorder = Border()
  var enemies = List.empty[Enemy]
  var bossList = List(Ancalagon(20, Vec2(900, 236)))
  var currentBoss = 0
  def everything: List[Actor] = {
    player :: projectilesList ::: enemies ::: upgradeList ::: walls
  }
  var selectWeapon = 0
  var weaponList: List[Weapon] =
    List(MachineGun(150, 1, 0), MissileArray(500, 3))
  def reset: Unit = {
    player = Player(Vec2(64, 256), 0, 0.9f, 3, MachineGun(50, 1, 0))
    projectilesList = List.empty[Projectile]
    upgradeList = List.empty[Upgrade]
    walls = List.empty[Wall]
    worldBorder = Border()
    enemies = List.empty[Enemy]
    bossList = List(Ancalagon(20, Vec2(900, 236)))
    currentBoss = 0
    weaponList = List(MachineGun(150, 1, 0), MissileArray(500, 3))
    selectWeapon = 0
    Starships.score = 0
    Starships.scrollspeed = 1.0f
    Spawner.isBossFight = false
    Spawner.hasFoughtBoss = false
  }
  var weaponOptions: List[Weapon] =
    List(MachineGun(150, 1, 0), MissileArray(500, 3), PlasmaOrb(1000, 7))
}
