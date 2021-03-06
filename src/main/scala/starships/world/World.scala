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
import starships.missions._
import starships.world.World.missionList
object World {
  var player =
    Player(Vec2(-120, 256), 0, 0.9f, 3, MachineGun(50, 1, 0))
  var projectilesList = List.empty[Projectile]
  var upgradeList = List.empty[Upgrade]
  var walls = List.empty[Wall]
  var worldBorder = Border()
  var enemies = List.empty[Enemy]
  var bossList: List[Boss] =
    List(FirstBoss(Vec2(850, 236), 20, 4), Ancalagon(20, Vec2(900, 236), 0f))
  var currentBoss = 0
  def everything: List[Actor] = {
    player :: enemies ::: walls ::: upgradeList ::: projectilesList
  }
  var stars = List.empty[Star]
  var explosions = List.empty[Explosion]
  var selectWeapon = 0
  var missionList: List[Mission] =
    List(new Introduction, new IgnisLevel)
  var weaponList: List[Weapon] = {
    List(MachineGun(150, 1, 0), MissileArray(500, 3))

  }
  var currentMission = Option.empty[Mission]

  def reset: Unit = {
    player.isDead = false
    player.invuln = true
    Starships.missionsLoaded = false
    currentMission = missionList.headOption
    stars = List.empty
    player = Player(Vec2(-120, 256), 0, 0.9f, 3, MachineGun(50, 1, 0))
    projectilesList = List.empty[Projectile]
    upgradeList = List.empty[Upgrade]
    walls = List.empty[Wall]
    worldBorder = Border()
    enemies = List.empty[Combator]
    weaponList = List(MachineGun(150, 1, 0), MissileArray(500, 3))
    selectWeapon = 0
    Starships.score = 0
    Starships.scrollspeed = 1.0f
    Spawner.isBossFight = false
    Spawner.hasFoughtBoss = false
    for (i <- 0 until 1000) {
      stars = Star(
        Vec2((Math.random() * 1024).toFloat, (Math.random() * 512).toFloat),
        (Math.random() * 4).toFloat
      ) :: stars
    }
  }
  var weaponOptions: List[Weapon] =
    List(
      MissileArray(500, 3),
      PlasmaOrb(1000, 7),
      MachineGun(550, 1, 0)
    )
}
