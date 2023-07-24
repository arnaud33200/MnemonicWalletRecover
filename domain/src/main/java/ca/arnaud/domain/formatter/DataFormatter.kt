package ca.arnaud.domain.formatter

interface DataFormatter<Data> {

    fun format(data: Data): Data
}