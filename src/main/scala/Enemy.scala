import processing.core.PApplet

case class Enemy(
    var location: Vec2,
    var size: Vec2,
    var velocity: Float,
    var deceleration: Float,
    var health: Int
) extends Scrolling
    with Actor {

  def box: Box2 = Box2(location, size)
  var time: Long = System.currentTimeMillis
  def draw(p: PApplet): Unit = {
    p.fill(75, 175, 25)
    p.rect(location.x, location.y, 20, 20)
  }
  def update(): Unit = {
    move
    matchLoc
    checkForCollision
  }
  def matchLoc: Unit = {

    val currentTime = System.currentTimeMillis
    if (Math.abs(World.player.location.y - location.y) > 30) {
      velocity += clamp(
        Math.signum(World.player.location.y - location.y) * 0.1f,
        3f
      )
    } else {
      velocity = velocity * deceleration
    }

    if (currentTime > time + 900) {
      World.projectilesList = MachineGunProjectile(
        Vec2(location.x - 25, location.y + 10),
        Vec2(10, 10),
        -1
      ) :: World.projectilesList

      time = currentTime

    }
  }
  def clamp(value: Float, max: Float) = {
    if (value > max) max
    else if (value < -max) -max
    else value
  }
  def move: Unit = {
    location = location.addY(velocity)

  }
  def checkForCollision: Unit = {
    for (i <- World.projectilesList) {
      if (box.intersects(i.box) && i.direction == 1) {
        health -= World.player.primary.damage
        World.projectilesList = World.projectilesList.filterNot(p => p == i)

        Starships.score += World.player.primary.damage
      }
    }
    if (health <= 0) {
      World.enemies = World.enemies.filterNot(enemy => enemy == this)
    }
    if (box.top <= 20 || box.bottom >= 492) {
      velocity = -velocity
    }
    if (box.right <= 0) {
      World.enemies = World.enemies.filterNot(enemy => enemy == this)
    }
  }

}
