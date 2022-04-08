import processing.core.PApplet

case class Wall(
    var x: Float,
    var y: Float,
    var dimensionX: Float,
    var dimensionY: Float
) extends Scrolling {
  def draw(p: PApplet): Unit = {
    p.fill(100, 100, 100)
    p.rect(x, y, dimensionX, dimensionY)
  }
}