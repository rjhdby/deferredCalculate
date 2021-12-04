import deferred.DeferredCalculation

fun main(args: Array<String>) {
    val test = listOf("bbb", "ccc", "aaa")

    val root = DeferredCalculation.of { test }
    val half = root.branch { it.sorted() }.branch { it.map { it.uppercase() } }

    val result1 = half.branch { it.take(1) }
    val result2 = half.branch { it.map { it.substring(2) } }
    val result3 = root.branch { it.last() }
    println("ALL BRANCHED")

    println(result1.calculate())
    println(result2.calculate())
    println(result3.calculate())
}