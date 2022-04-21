package com.example.todolist.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.todolist.dto.Todo

@Dao
interface TodoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(dto: Todo)

    @Query("select * from todoTable")
    fun list(): LiveData<MutableList<Todo>>

    @Query("select * from todoTable where id = (:id)")
    fun selectOne(id: Long): Todo

    @Update
    suspend fun update(dto: Todo)

    @Delete
    fun delete(dto: Todo)
}

/*
*   insert, query, update, delete는 Room 어노테이션을 사용해 구성
*   모든 항목을 불러오는 list 함수의 경우 LiveData를 사용해, 추가, 수정, 삭제에 의해
*   변화하는 값에 대해 즉시 반영이 가능하도록 한다.
*/