object World {
  var player = Player(50, 50, 0, 0.9f)
  var projectilesList = List.empty[Projectile]
  var walls = List(Wall(1024, 0, 60, 100), Wall(1024, 412, 60, 100))
  var worldBorder = Border()
  var enemies = List(Enemy(900, 200), Enemy(900, 300))
}
