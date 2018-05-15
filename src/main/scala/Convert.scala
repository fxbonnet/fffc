import java.io.{File, FileWriter, PrintWriter}

import scala.io.Source

object Convert extends App {
  val usage = "Usage: Convert [--out <output filename>] --metadata netadata  input filename"

  override def main(args: Array[String]) {

    if (args.length == 0)
      exit(usage)

    val options = optionMap(args.toList)

    val metadata = options
      .get('metadata)
      .map(f=> Source.fromFile(new File(f), "UTF-8"))

    val infile = options
      .get('in)
      .map(f=> Source.fromFile(new File(f), "UTF-8"))

    val outfile = options
      .get('out)
      .map(f=> new PrintWriter(new File(f), "UTF-8"))

    if(metadata.isEmpty || infile.isEmpty || outfile.isEmpty)
      exit(usage)

    val converter = new Converter(Metadata(metadata.get))
    val writer = CSVWriter()

    try {
      converter
        .convert(infile.get, writer)
        .foreach(outfile.get.print)
    } catch {
      case t: Throwable =>
        println(t.getMessage)
    } finally {
      outfile.foreach { o=>
        o.flush()
        o.close()
      }
    }
  }

  type OptionMap = Map[Symbol, String]

  private def optionMap(list: List[String], map : OptionMap = Map()) : OptionMap = {
    def isSwitch(s : String) = (s(0) == '-')
    list match {
      case Nil => map
      case "--metadata" :: value :: tail =>
        optionMap(tail, map ++ Map('metadata -> value))
      case "--out" :: value :: tail =>
        optionMap(tail, map ++ Map('out -> value))
      case string :: Nil =>  optionMap(list.tail, map ++ Map('in -> string))
      case option :: tail =>
        exit(s"Unknown option $option")
        Map()
    }
  }

  private def exit(message: String) : Unit = {
    println(message)
    System.exit(1)
  }
}

