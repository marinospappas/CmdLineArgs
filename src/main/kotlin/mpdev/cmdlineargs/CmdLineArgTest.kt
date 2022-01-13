package mpdev.cmdlineargs

import java.lang.System.err
import kotlin.system.exitProcess

var outfile = ""
var infile = ""
var config = ""

fun processCmdLineArgs1(args: Array<String>) {
    val c = ProcessCmdLineArgs(args)
    var argCount = 0    // excludes options and their params (e.g. -o outfile)

    while (true) {
        val arg = c.match()
        if (arg.code == ArgCode.endOfList)
            break
        if (arg.code == ArgCode.option)
            when (arg.value) {
                "o", "O", "output", "Output" -> {
                    val next = c.match()
                    if (next.code == ArgCode.parameter)
                        outfile = next.value
                    else {
                        err.println("option -o requires output_file_name")
                        if (next.code != ArgCode.endOfList)
                            err.println("invalid option: [-${next.value}] ignored")
                    }
                }
                "h", "H", "help", "?" -> {
                    println("help message.....")
                    exitProcess(0)
                }
                else -> err.println("invalid option: [-${arg.value}] ignored")
            }
        else
            when (++argCount) {
                1 -> infile = arg.value     // 1st argument
                2 -> config = arg.value     // 2nd argument
                else -> err.println("command line argument: [${arg.value}] ignored")
            }

    }
}

fun main(args: Array<String>) {

    processCmdLineArgs1(args)

    println("outfile=[$outfile] infile=[$infile] config=[$config]")
}