package com.example.todolist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist.adapter.TodoAdapter
import com.example.todolist.databinding.ActivityMainBinding
import com.example.todolist.dto.Todo
import com.example.todolist.viewmodel.TodoViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    lateinit var todoViewModel: TodoViewModel
    lateinit var todoAdapter: TodoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        todoViewModel = ViewModelProvider(this)[TodoViewModel::class.java]

        todoViewModel.todoList.observe(this) {
            todoAdapter.update(it)
        }

        todoAdapter = TodoAdapter(this)
        binding.rvTodoList.layoutManager = LinearLayoutManager(this)
        binding.rvTodoList.adapter = todoAdapter

        binding.fabAdd.setOnClickListener {
            val intent = Intent(this, EditTodoActivity::class.java).apply {
                putExtra("type", "ADD")
            }
            requestActivity.launch(intent)
        }

        todoAdapter.setItemCheckBoxClickListener(object: TodoAdapter.ItemCheckBoxClickListener{ // checkbox의 클릭 이벤트를 처리
            override fun onClick(view: View, position: Int, itemId: Long) {
                CoroutineScope(Dispatchers.IO).launch {
                    val todo = todoViewModel.getOne(itemId) // todoViewModel.getOne(itemId)를 통해 클릭된 아이템의 정보를 가져온다.
                    todo.isChecked = !todo.isChecked // isChecked를 토글
                    todoViewModel.update(todo)
                }
            }
        })

    }

    /*
        intent를 통해 this로부터 EditTodoActivity로 엑티비티를 이동시킨다.
        apply를 통해 함께 보낼 데이터를 key:value 형태로 담는다.
        EditTodoActivity는 추가와 수정을 동시에 담당하기 때문에, 추가를 할 것인지, 수정을 할 것인지를 구분해주어야 한다.
        "ADD"라는 문자열을 putExtra를 통해 담고 requestActivity는 다시 넘어올 데이터를 처리하기 위해 작성한다.
    */
    private val requestActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if(it.resultCode == RESULT_OK) {
            val todo = it.data?.getSerializableExtra("todo") as Todo

            when(it.data?.getIntExtra("flag", -1)) {
                0 -> {
                    CoroutineScope(Dispatchers.IO).launch {
                        todoViewModel.insert(todo)
                    }
                    Toast.makeText(this, "추가되었습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}