object ImplicitsExamples extends App {

  //------------------ Implicit Conversions ---------------

  val HELLO = "hello".toAllUpper






  class RichString(val str: String) {
    def toAllUpper = str.toUpperCase
  }

  implicit def string2RichString(str: String): RichString = new RichString(str)





  def takeRichString(rs: RichString) { rs.length }

  implicit def richString2String(rs: RichString): String = rs.str

  takeRichString("hello")






  //-------------------- Implicit Parameters --------------



  trait Converter { def doConversion(d: Double): Double }

  def euro2dollar(eur: Double)(implicit converter: Converter) = {
    converter.doConversion(eur)
  }

  val dollar = euro2dollar(2.0)




  implicit val euro2DollarConverter: Converter = new Converter {
    def doConversion(d: Double) = d * 1.1
  }





  //--------------------- Context bounds ------------------

  trait Comparator[A] { def isOrdered(a1: A, a2: A): Boolean }
  implicit object IntComparator extends Comparator[Int] {
    override def isOrdered(a1: Int, a2: Int): Boolean = if(a1 < a2) true else false
  }

  def orderPairList[A](list: List[(A,A)])(implicit cp: Comparator[A]) = {
    for { pair <- list } yield orderPair(pair)
  }

  def orderPair[A](pair: (A, A))(implicit cp: Comparator[A]) = {
    if(cp.isOrdered(pair._1, pair._2)) pair else pair.swap
  }


  println(orderPairList((1,2) :: (2,1) :: Nil))


}
