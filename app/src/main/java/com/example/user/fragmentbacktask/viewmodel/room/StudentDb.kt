package com.example.user.fragmentbacktask.viewmodel.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

/**
 * Created by huangyuxi on 2019-08-20
 * Title:
 */
@Database(entities = arrayOf(Student::class), version = 1)
abstract class StudentDb : RoomDatabase() {
    abstract fun studentDao(): StudentDao

    companion object {
        private var instance: StudentDb? = null

        @Synchronized
        fun get(context: Context): StudentDb {
            if (instance == null) {
                instance = Room.databaseBuilder(context.applicationContext, StudentDb::class.java,
                        "StudentDatabase")
                        .addCallback(object : RoomDatabase.Callback() {
                            override fun onCreate(db: SupportSQLiteDatabase) {
                                Thread {
                                    val students = CHEESE_DATA.mapIndexed{ index, value ->
                                        Student(id =index , name = value)
                                    }
                                    get(context).studentDao().insert(students)
                                }.start()
                            }
                        }).build()
            }
            return instance!!
        }

        private val CHEESE_DATA = arrayListOf(
                "Abbaye de Belloc", "Abbaye du Mont des Cats", "Abertam", "Abondance", "Ackawi",
                "Acorn", "Adelost", "Affidelice au Chablis", "Afuega'l Pitu", "Airag", "Airedale",
                "Aisy Cendre", "Allgauer Emmentaler", "Alverca", "Ambert", "American Cheese",
                "Ami du Chambertin", "Anejo Enchilado", "Anneau du Vic-Bilh", "Anthoriro", "Appenzell",
                "Aragon", "Ardi Gasna", "Ardrahan", "Armenian String", "Aromes au Gene de Marc",
                "Asadero", "Asiago", "Aubisque Pyrenees", "Autun", "Avaxtskyr", "Baby Swiss",
                "Babybel", "Baguette Laonnaise", "Bakers", "Baladi", "Balaton", "Bandal", "Banon",
                "Barry's Bay Cheddar", "Basing", "Basket Cheese", "Bath Cheese", "Bavarian Bergkase",
                "Baylough", "Beaufort", "Beauvoorde", "Beenleigh Blue", "Beer Cheese", "Bel Paese",
                "Bergader", "Bergere Bleue", "Berkswell", "Beyaz Peynir", "Bierkase", "Bishop Kennedy",
                "Blarney", "Bleu d'Auvergne", "Bleu de Gex", "Bleu de Laqueuille",
                "Bleu de Septmoncel", "Bleu Des Causses", "Blue", "Blue Castello", "Blue Rathgore",
                "Blue Vein (Australian)", "Blue Vein Cheeses", "Bocconcini", "Bocconcini (Australian)"
        )
    }

}