package com.example.todolist.config

import android.app.Application
import com.example.todolist.repository.TodoRepository

class ApplicationClass: Application() {
    override fun onCreate() {
        super.onCreate()

        TodoRepository.initialize(this)
    }
}

/*
*   이 실행될 때 단 한번 실행되도록 하기 위해 작성
*   앱 실행과 동시에 Repository 초기화를 통해 데이터베이스가 없을 경우 새로 빌드하도록 한다.
*/