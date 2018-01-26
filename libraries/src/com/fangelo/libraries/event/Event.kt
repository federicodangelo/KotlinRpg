package kotlinx.event

class Event<T> {

    var list = listOf<(T) -> Unit>()

    inline operator fun plusAssign(noinline callback: (T) -> Unit) {
        list += callback
    }

    inline operator fun minusAssign(noinline callback: (T) -> Unit) {
        list -= callback
    }

    operator fun invoke(data: T) {
        for (value in list) value(data)
    }
}