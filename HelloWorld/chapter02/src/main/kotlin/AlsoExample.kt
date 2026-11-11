fun main() {
    val number = mutableListOf(1, 2, 3).also {
        println("리스트의 크기: ${it.size}")
        it.add(4)
    }

    println(number)
}
