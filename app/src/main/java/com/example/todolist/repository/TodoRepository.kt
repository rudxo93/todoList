package com.example.todolist.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.example.todolist.database.TodoDatabase
import com.example.todolist.dto.Todo
import java.lang.IllegalStateException

private const val DATABASE_NAME = "todo-database.db"

class TodoRepository private constructor(context: Context) {

    private val database: TodoDatabase = Room.databaseBuilder(
        context.applicationContext,
        TodoDatabase::class.java,
        DATABASE_NAME
    ).build()

    private val todoDao = database.todoDao()

    fun list(): LiveData<MutableList<Todo>> = todoDao.list()

    fun getTodo(id: Long): Todo = todoDao.selectOne(id)

    fun insert(dto: Todo) = todoDao.insert(dto)

    suspend fun update(dto: Todo) = todoDao.update(dto)

    fun delete(dto: Todo) = todoDao.delete(dto)

    companion object {
        private var INSTANCE: TodoRepository? = null

        fun initialize(context: Context) {
            if(INSTANCE == null) {
                INSTANCE = TodoRepository(context)
            }
        }

        fun get(): TodoRepository {
            return INSTANCE ?:
            throw IllegalStateException("TodoRepository must be initialized")
        }
    }

}

/*
*    - 먼저 Room.databaseBuilder().build() 를 통해 데이터베이스를 빌드
*    - companion object 객체는 클래스가 생성될 때 메모리에 적재되면서 동시에 생성하는
*      객체로, 데이터베이스 생성 및 초기화를 담당하기 위해 작성
*/