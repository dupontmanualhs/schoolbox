package math.numbers

abstract class Real extends Number {
  def +(that: Real): Real
  def -(that: Real): Real
  def *(that: Real): Real
  def /(that: Real): Real
  def unary_-(): Real
  def repr: String
}

object Real {
  
}