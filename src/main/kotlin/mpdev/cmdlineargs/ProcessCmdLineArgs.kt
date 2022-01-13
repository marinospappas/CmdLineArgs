package mpdev.cmdlineargs

import java.lang.System.err

const val OPTION_PREFIX = '-'
const val END_ARGS="end_of_args_list"

enum class ArgCode { option, parameter, argument, endOfList, none, any }

class ProcessCmdLineArgs(args: Array<String>) {

    var args: Array<String>

    class Arg(var value: String = "none", var code: ArgCode = ArgCode.none)

    var nextArg = Arg()
    var nextIndx = 0

    init {
        this.args = args
        nextArg = scan()
    }

    fun match(arg: ArgCode = ArgCode.any): Arg {
        if (arg != ArgCode.any && nextArg.code != arg)
            expected(arg)
        val thisArg = nextArg
        nextArg = scan()
        return thisArg
    }

    fun scan(): Arg {
        if (nextIndx >= args.size)  // end of list
            return Arg(END_ARGS, ArgCode.endOfList)
        val arg = args[nextIndx++]
        if (arg[0] == OPTION_PREFIX)     // option found (-x)
            return Arg(arg.substring(1), ArgCode.option)
        else
        if (nextArg.code == ArgCode.option)     // after an option a parameter is returned
            return Arg(arg, ArgCode.parameter)
        else                            // else an argument is returned
            return Arg(arg, ArgCode.argument)
    }

    fun expected(arg: ArgCode) {
        err.println("expected $arg found ${nextArg.code}")
    }
}