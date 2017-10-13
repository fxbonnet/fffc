package Leon

import org.apache.commons.io.FileUtils
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import java.io.File
import java.nio.charset.Charset

class FormatterTest {
    @Before
    fun setUp() {

        val strRec1   = "1970-01-01John           Smith           81.5"
        val strRec1_1 = "1970-01-01Joh,n          Smith           81.5"
        val strRec1_2 = "1970-01-01John           Smith           s1.5"
        val strRec1_3 = "1970-01-01John           Smith          -81.5"
        val strRec2   = "1975-01-31Jane           Doe             61.1"
        val strRec3   = "1988-11-28Bob            Big            102.4"

        val strMeta1   = "Birth date,10,date"
        val strMeta2   = "First name,15,string"
        val strMeta3   = "Last name,15,string"
        val strMeta3_1 = "Last name,14,string"
        val strMeta4   = "Weight,5,numeric"
        val strMeta4_1 = "Weight,6,numeric"

        val strLineEnd = "\r\n"

        val sb1 = StringBuilder()

        //Generate a normal srcFile
        for(i in 1..300){
            sb1.append(strRec1)
            sb1.append(strLineEnd)
            sb1.append(strRec2)
            sb1.append(strLineEnd)
            sb1.append(strRec3)
            sb1.append(strLineEnd)
        }
        FileUtils.writeStringToFile(File("./srcFile.txt"), sb1.toString(), Charset.forName("UTF-8"), false)
        sb1.setLength(0)

        //Generate a srcFile (string with comma)
        sb1.append(strRec1_1)
        sb1.append(strLineEnd)
        sb1.append(strRec2)
        sb1.append(strLineEnd)
        sb1.append(strRec3)
        sb1.append(strLineEnd)

        FileUtils.writeStringToFile(File("./srcFile_stringwithcomma.txt"), sb1.toString(), Charset.forName("UTF-8"), false)
        sb1.setLength(0)

        //Generate a srcFile (numeric with string)
        sb1.append(strRec1_2)
        sb1.append(strLineEnd)
        sb1.append(strRec2)
        sb1.append(strLineEnd)
        sb1.append(strRec3)
        sb1.append(strLineEnd)

        FileUtils.writeStringToFile(File("./srcFile_numericwithstring.txt"), sb1.toString(), Charset.forName("UTF-8"), false)
        sb1.setLength(0)

        //Generate a srcFile (field width changed)
        sb1.append(strRec1_3)
        sb1.append(strLineEnd)
        sb1.append(strRec2)
        sb1.append(strLineEnd)
        sb1.append(strRec3)
        sb1.append(strLineEnd)

        FileUtils.writeStringToFile(File("./srcFile_fieldwidthchanged.txt"), sb1.toString(), Charset.forName("UTF-8"), false)
        sb1.setLength(0)

        //Generate a normal metaFile
        sb1.append(strMeta1)
        sb1.append(strLineEnd)
        sb1.append(strMeta2)
        sb1.append(strLineEnd)
        sb1.append(strMeta3)
        sb1.append(strLineEnd)
        sb1.append(strMeta4)
        sb1.append(strLineEnd)

        FileUtils.writeStringToFile(File("./metaFile.txt"), sb1.toString(), Charset.forName("UTF-8"), false)
        sb1.setLength(0)

        //Generate a metaFile (field width changed)
        sb1.append(strMeta1)
        sb1.append(strLineEnd)
        sb1.append(strMeta2)
        sb1.append(strLineEnd)
        sb1.append(strMeta3_1)
        sb1.append(strLineEnd)
        sb1.append(strMeta4_1)
        sb1.append(strLineEnd)

        FileUtils.writeStringToFile(File("./metaFile_fieldwidthchanged.txt"), sb1.toString(), Charset.forName("UTF-8"), false)
        sb1.setLength(0)
    }

    @Test
    fun execute() {

        val ff = Formatter()
        //Test - Normal
        println("==========================\"Test - Normal\" started==========================")
        ff.execute("./srcFile.txt", "./metaFile.txt", "./destFile_normal.txt", 100)
        println("==========================\"Test - Normal\" finished=========================")
        //Test - String with Comma
        println("==========================\"Test - String with Comma\" started==========================")
        ff.execute("./srcFile_stringwithcomma.txt", "./metaFile.txt", "./destFile_stringwithcomma.txt", 100)
        println("==========================\"Test - String with Comma\" finished==========================")
        //Test - Numeric with String
        println("==========================\"Test - Numeric with String\" started==========================")
        ff.execute("./srcFile_numericwithstring.txt", "./metaFile.txt", "./destFile_numericwithstring.txt", 100)
        println("==========================\"Test - Numeric with String\" finished==========================")
        //Test - Field Width Changed
        println("==========================\"Test - Field Width Changed\" started==========================")
        ff.execute("./srcFile_fieldwidthchanged.txt", "./metaFile_fieldwidthchanged.txt", "./destFile_fieldwidthchanged.txt", 100)
        println("==========================\"Test - Field Width Changed\" finished==========================")

    }

}