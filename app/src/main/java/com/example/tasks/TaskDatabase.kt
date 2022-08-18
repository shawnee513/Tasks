package com.example.tasks

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

//entities specified any classes - marked with @Entity - that define the tables you want Room to add to the database, in this case Task
//version is an Int that specified the version. in this case 1, since this is the first version of the database
//Note** the version number needs to be updated if the database entity files are modified. If the schema changes without updating the version number, the app will crash when ran
//exportSchema tells Room whether to export the database schema into a folder so that you can record its version history.
@Database(entities = [Task::class], version = 1, exportSchema = false)
abstract class TaskDatabase  : RoomDatabase(){
    //specify any interfaces(marked with @Dao) that will be used for data access. Add a property for each interface.
    abstract val taskDao: TaskDao

    //create the database and return an instance of it
    //getInstance() goes into a companion object so that we can call it without first creating a TaskDatabase.
    companion object{
        @Volatile
        private var INSTANCE: TaskDatabase? = null

        fun getInstance(context: Context) : TaskDatabase {
            synchronized(this) {
                var instance = INSTANCE
                //if the database doesn't already exist, the getInstance() method goes and builds it
                if (instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        TaskDatabase::class.java,
                        "tasks_database"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}