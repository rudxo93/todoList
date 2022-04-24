package com.example.todolist.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.todolist.dto.Todo

@Dao
interface TodoDao {

    // onConflict - 충돌처리방식 / OnConflictStrategy.REPLACE - 충돌이 발생할 경우 덮어쓰기
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
*
*   @Query를 사용해서 DB를 조회
*
*   insert의 충돌처리방식에는 5가지가 있다.
*   OnConflictStrategy.ABORT - 충돌이 발생ㄴ할 경우 처리 중단
*   OnConflictStrategy.FAIL - 충돌이 발생할 경우 실패처리
*   OnConflictStrategy.IGNORE - 충돌이 발생할 경우 무시
*   OnConflictStrategy.REPLACE - 충돌이 발생할 경우 덮어쓰기
*   OnConflictStrategy.ROLLBACK - 충돌이 발생할 경우 이전으로 되돌리기
*/