package deferred

class DeferredCalculation<K, T> private constructor(
    private val input: DeferredCalculation<*, K>? = null,
    private val mutator: (K) -> T,
) {
    private var cachedResult: T? = null
    private var references: Int = 0

    fun calculate(): T {
        if (cachedResult !== null) {
            println("RESULT CACHED: " + cachedResult)
            return cachedResult!!
        }
        println("do calculate")
        if (references > 1) {
            cachedResult = mutator(input?.calculate() ?: Unit as K)
            return cachedResult!!
        }

        return mutator(input?.calculate() ?: Unit as K)
    }

    fun <R> branch(subscribeMutator: (T) -> R): DeferredCalculation<T, R> {
        references++
        return DeferredCalculation(input = this, mutator = subscribeMutator)
    }

    companion object {
        fun <T> of(initializer: () -> T): DeferredCalculation<Unit, T> {
            return DeferredCalculation(mutator = { initializer() })
        }
    }
}