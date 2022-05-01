package starships.world

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

case class MenuItem(
    location: Vec2,
    size: Vec2,
    text: String,
    var lightness: Float
) {
  def box: Box2 = Box2(location, size)
  def isMouseOn(mouseBox: Box2): Boolean = {
    if (mouseBox.intersects(box)) {
      lightness = 0.6f
    } else {
      lightness = 1f
    }
    if (mouseBox.intersects(box) && Controls.mousePressed) true
    else false

  }
  def draw(p: PApplet): Unit = {
    p.fill(255 * lightness, 255 * lightness, 255 * lightness)
    p.rect(box.left, box.top, size.x, size.y)
    p.fill(0, 0, 0)
    p.text(text, box.left + 5, box.top + 15)
  }
}