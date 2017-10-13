package Leon

import org.apache.commons.io.FileUtils
import java.io.File
import java.nio.charset.Charset
import org.apache.commons.io.LineIterator


class Formatter{

    data class MetaData(val fieldIndex:Int, val fieldName:String, val fieldLength:Int, val fieldType:String)

    fun execute(srcFile:String, metaFile:String, destFile:String, batch:Int){

        //Delete Destination File
        try{
            FileUtils.forceDelete(FileUtils.getFile(destFile))
        }
        catch (ex:Exception){}

        //Read Metadata
        val metaList = ArrayList<MetaData>()
        var it = FileUtils.lineIterator(File(metaFile), "UTF-8")
        var fieldIndex = 0
        try {
            while (it.hasNext()) {
                val line = it.nextLine()

                //Parse Metadata Records and Populate into a List
                val temArr1 = line.split(",")
                val fieldName = temArr1[0].trim()
                val fieldLength = temArr1[1].toInt()
                val fieldType = temArr1[2].trim()
                val metadata = MetaData(fieldIndex++, fieldName, fieldLength, fieldType)
                metaList.add(metadata)
            }
        }
        catch (ex:Exception){
            //If any error reading metadata file, then terminate
            println("Error reading metadata file: " + ex.toString())
            return
        }
        finally {
            LineIterator.closeQuietly(it)
        }

        //Calculate the Line Width
        val lineWidth = metaList.sumBy { x->x.fieldLength }

        //Read from Data File and Write to Desination File
        it = FileUtils.lineIterator(File(srcFile), "UTF-8")
        var lineIndex = 0
        val sb1 = StringBuilder()
        var strWrite:String
        try {
            //Write Column Header
            for(i in metaList.indices) {
                var strFieldName = metaList[i].fieldName
                sb1.append(strFieldName)

                if(i < metaList.size - 1){
                    sb1.append(",")
                }
                else{
                    sb1.append("\r\n")
                }
            }
            strWrite = sb1.toString()
            FileUtils.writeStringToFile(File(destFile), strWrite, Charset.forName("UTF-8"), true)
            sb1.setLength(0)

            //Read line by line from srcFile
            while (it.hasNext()) {
                val line = it.nextLine()
                lineIndex++

                //Check Line Width first; If it doesn't match with the Metadata, then terminate
                if(line.length != lineWidth){
                    throw Exception("Line[$lineIndex] width incorrect: " + line)
//                    return
                }

                var posIndex = 0
                for(i in metaList.indices) {
                    var m = metaList[i]
                    val rawFieldValue = line.substring(posIndex, (posIndex + m.fieldLength ))
                    var newFieldValue = ""

                    if(m.fieldType.toLowerCase().contains("date")){
                        val tempArr2 = rawFieldValue.split("-")
                        val strYear = tempArr2[0]
                        val strMonth = tempArr2[1]
                        val strDay = tempArr2[2]

                        newFieldValue = strDay + "/" + strMonth + "/" + strYear
                    }
                    if(m.fieldType.toLowerCase().contains("string")){
                        newFieldValue = rawFieldValue.trim()
                        if(rawFieldValue.contains(",")){
                            newFieldValue = "\"" + newFieldValue + "\""
                        }
                    }
                    if(m.fieldType.toLowerCase().contains("numeric")){
                        try{
                            newFieldValue = rawFieldValue.toDouble().toString()
                        }
                        catch (ex:Exception){
                            //If any error reading metadata file, then terminate
                            throw Exception("Error converting field[${m.fieldName}] to numeric at line[$lineIndex], field value: " + rawFieldValue)
//                            return
                        }
                    }

                    sb1.append(newFieldValue)
                    if(i < metaList.size - 1){
                        sb1.append(",")
                    }
                    else{
                        sb1.append("\r\n")
                    }

                    posIndex += m.fieldLength
                }

                //Write to destFile by batch
                if(lineIndex % batch ==0){
                    strWrite = sb1.toString()
                    FileUtils.writeStringToFile(File(destFile), strWrite, Charset.forName("UTF-8"), true)
                    sb1.setLength(0)
                }
            }

            //Write the last batch
            strWrite = sb1.toString()
            FileUtils.writeStringToFile(File(destFile), strWrite, Charset.forName("UTF-8"), true)
        }
        catch (ex:Exception){
            println("Error: " + ex.toString())
        }
        finally {
            LineIterator.closeQuietly(it)
        }
    }
}

fun main(args: Array<String>) {

    for (arg in args){
        println("Arg: " + arg)
    }

    val strSrcFile = args[0]
    val strMetaFile = args[1]
    val strDestFile = args[2]
    val strBatch = args[3]

    println("==========================\"Processing\" started==========================!")

    val ff = Formatter()
//    ff.execute("./srcFile.txt", "./metaFile.txt", "./destFile.txt", 10000)
    ff.execute(strSrcFile, strMetaFile, strDestFile, strBatch.toInt())

    println("==========================\"Processing\" finished==========================!")
}