package hr.algebra.todoapp.model

private const val DEL = '|'
data class Item(val text: String, var done: Boolean = false){
    fun prepareForFileLine() = "%s%s%b\n".format(text, DEL, done)
    companion object{
        fun parseFromFileLine(line: String) =
            line.split(DEL).let {
                Item(it[0], it[1].toBoolean())
            }
    }
}
