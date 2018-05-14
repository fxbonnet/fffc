import java.io.File

import scala.io.Source

object Convert extends App {
  val usage = "Usage: Convert [--metadata netadata] filename"

  override def main(args: Array[String]) {

    if (args.length == 0)
      println(usage)

    val options = optionMap(args.toList)
    val metadata = Source.fromFile(new File(options('metadata)), "UTF-8")
    val infile = Source.fromFile(new File(options('infile)), "UTF-8")
    val converter = new Converter(Metadata(metadata))
    val writer = CSVWriter()

    converter.convert(infile, writer)
  }

  type OptionMap = Map[Symbol, String]

  private def optionMap(list: List[String], map : OptionMap = Map()) : OptionMap = {
    def isSwitch(s : String) = (s(0) == '-')
    list match {
      case Nil => map
      case "--metadata" :: value :: tail =>
        optionMap(tail, map ++ Map('metadata -> value))
      case string :: Nil =>  optionMap(list.tail, map ++ Map('infile -> string))
      case option :: tail => println(s"Unknown option $option")
        System.exit(1)
        Map()
    }
  }
}

