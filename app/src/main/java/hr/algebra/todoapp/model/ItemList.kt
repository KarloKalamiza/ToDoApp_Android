package hr.algebra.todoapp.model

import android.content.Context
import android.util.Log
import hr.algebra.todoapp.Util.isExternalStorageReadable
import hr.algebra.todoapp.Util.isExternalStorageWriteable
import java.io.File
import kotlin.math.log

private const val DIR = "todo"
private const val FILE = "items.txt"
class ItemList(private val context: Context): ArrayList<Item>() {
    fun saveInFile(){
        if (!isExternalStorageWriteable()){
            return
        }
        try {
            File(context.getExternalFilesDir(DIR), FILE)
                .bufferedWriter().use {
                    for (item in this) {
                        it.write(item.prepareForFileLine())
                    }
                }
        } catch (e: Exception) {
            Log.e(javaClass.name, e.toString(), e)
        }
    }

    fun readFromFile(){
        if (!isExternalStorageReadable()){
            return
        }
        try {
            File(context.getExternalFilesDir(DIR), FILE).useLines {
                lines -> lines.forEach { line ->
                    add(Item.parseFromFileLine(line))
                }
            }
        } catch (e: Exception) {
            Log.e(javaClass.name, e.toString(), e)
        }
    }
}