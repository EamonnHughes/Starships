package geom

import processing.core.PApplet

import geom._
import hostiles._
import obstacles._
import projectiles._
import traits._
import upgrades._
import weapons._
import world._

case class Box2(left: Float, top: Float, width: Float, height: Float) {
  def right: Float = left + width
  def bottom: Float = top + height

  def intersects(box: Box2): Boolean = {
    left < box.right && right >= box.left && top < box.bottom && bottom >= box.top
  }
  def union(box: Box2): Box2 = {
    Box2.ofLTRB(
      box.left min left,
      box.top min top,
      box.right max right,
      box.bottom max bottom
    )
  }
  def drawBox(p: PApplet): Unit = {
    p.rect(left, top, width, height)
  }
}

object Box2 {
  def apply(loc: Vec2, size: Vec2): Box2 = Box2(loc.x, loc.y, size.x, size.y)

  def ofLTRB(left: Float, top: Float, right: Float, bottom: Float): Box2 =
    Box2(left = left, top = top, width = right - left, height = bottom - top)
}
