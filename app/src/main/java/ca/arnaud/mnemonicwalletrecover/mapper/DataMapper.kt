package ca.arnaud.mnemonicwalletrecover.mapper

interface DataMapper<in Input, out Output> {

    fun mapTo(input: Input): Output
}