package org.fw.relearn_android_kotlin._android._room

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main4.*
import org.fw.relearn_android_kotlin.R
import org.fw.relearn_android_kotlin.util.LogShow
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main4)
        val studentDao = AppDatabase.getDatabase(this).studentDao()


        btn_adduser.setOnClickListener {
            thread {
                repeat(10){
                    val stu = Student("aaa$it","bbb$it", 29 + it)
                    studentDao.insertStudent(stu)
                }
            }
        }
        btn_deleteuser.setOnClickListener {
            thread {
                studentDao.deleteStudentByLastName("bbb9")
            }
        }
        btn_getuser.setOnClickListener {
            thread {
                studentDao.loadAllStudents().forEach {
                    "===> $it".LogShow()
                }
            }
        }
        btn_queryuser.setOnClickListener {
            thread {
                studentDao.loadStudentOlderThan(35).forEach {
                    "----> $it".LogShow()
                }
            }
        }
        btn_updateuser.setOnClickListener {
            thread {
                val student1 = Student("aaa0","bbb0", 1000)
                studentDao.updateStudent(student1)
            }
        }
    }
}