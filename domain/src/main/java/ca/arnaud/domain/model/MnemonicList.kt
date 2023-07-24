package ca.arnaud.domain.model

class MnemonicList<Data>(
    init: (index: Int) -> Data
) : List<Data> {

    companion object {
        private const val WORDS_COUNT = 12
    }

    private val list = List(WORDS_COUNT, init)

    fun <Output> map(transform: (Data) -> Output): MnemonicList<Output> {
        return MnemonicList { index -> transform(list[index]) }
    }

    // region List

    override val size: Int = list.size

    override fun get(index: Int): Data = list[index]

    override fun isEmpty(): Boolean = list.isEmpty()

    override fun iterator(): Iterator<Data> = list.iterator()

    override fun listIterator(): ListIterator<Data> = list.listIterator()

    override fun listIterator(index: Int): ListIterator<Data> = list.listIterator()

    override fun subList(fromIndex: Int, toIndex: Int): List<Data> =
        list.subList(fromIndex, toIndex)

    override fun lastIndexOf(element: Data): Int = list.lastIndexOf(element)

    override fun indexOf(element: Data): Int = list.indexOf(element)

    override fun containsAll(elements: Collection<Data>): Boolean = list.containsAll(elements)

    override fun contains(element: Data): Boolean = list.contains(element)

    // endregion
}