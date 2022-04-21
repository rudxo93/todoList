package com.example.todolist.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "todoTable")
class Todo (
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) var id: Long = 0,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "content") val content: String,
    @ColumnInfo(name = "timestamp") val timestamp: String,
    @ColumnInfo(name = "isChecked") var isChecked: Boolean
): Serializable

/*
* @Entitiy는 테이블 이름, @ColumnInfo는 컬럼에 들어갈 이름, @PrimaryKey는 기본키를 의미한다.
*
* Todo는
*  - id : 기본키 autoGenerate = true 를 해주었기 때문에, id 값을 자동으로 증가하게 된다.
*  - title : 제목
*  - content : 내용
*  - timestamp : 생성/수정 날짜'
*  - isChecked : 체크박스 클릭(할 일 완료) 여부
*  로 구성
*  - Intent에 객체를 담기 위해 Serializable을 상속받는다.
* */