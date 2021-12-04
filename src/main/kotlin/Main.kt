import deferred.DeferredCalculation

fun main(args: Array<String>) {
    val test = listOf("bbb", "aaa", "ccc")

    val root = DeferredCalculation.of { test }
    val half = root.branch { it.sorted() }.branch { it.map { it.uppercase() } }

    val result1 = half.branch { it.take(1) }.branch { it.map { "NOPE $it" } }
    val result2 = half.branch { it.map { it.substring(2) } }
    val result3 = root.branch { it.last() }
    val result4 = result2.branch { it.map { it.lowercase() } }

    println("-- result1 --")
    println(result1.calculate())
    println("-- result2 --")
    println(result2.calculate())
    println("-- result3 --")
    println(result3.calculate())
    println("-- result4 --")
    println(result4.calculate())
}