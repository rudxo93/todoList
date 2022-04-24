package com.example.todolist.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.todolist.dao.TodoDao
import com.example.todolist.dto.Todo

@Database(entities = arrayOf(Todo::class), version = 1)
abstract class TodoDatabase: RoomDatabase() {
    abstract fun todoDao(): TodoDao
}

/*
*    entity는 투두 클래스로, RoomDatabase 라이브러리를 사용해 생성
*
*   entities - 해당 DB에 어떤 테이블들이 있는지 명시
*   version - Schema가 바뀔 때 이 version도 바뀌어야 한다.
*   exportSchema - Room의 Schema구조를 폴더로 Export 할 수 있다.
*                   데이터베이스의 버전 히스토리를 기록할 수 있다는 점에서 true로 설정하는 것이 좋다.
*/