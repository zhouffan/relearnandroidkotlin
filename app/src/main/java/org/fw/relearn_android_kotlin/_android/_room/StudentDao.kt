package org.fw.relearn_android_kotlin._android._room

import androidx.room.*

/**
 *    author : 进击的巨人
 *    e-mail : zhouffan@qq.com
 *    date   : 2021/1/4 21:30
 *    desc   :
 *    version: 1.0
 */

@Dao
interface StudentDao {
    @Insert
    fun insertStudent(student: Student): Long
    @Update
    fun updateStudent(student: Student)
    @Query("select * from Student")
    fun loadAllStudents(): List<Student>
    @Query("select * from student where age > :age")
    fun loadStudentOlderThan(age: Int): List<Student>
    @Delete
    fun deleteStudent(student: Student)
    @Query("delete from Student where lastName = :lastName")
    fun deleteStudentByLastName(lastName: String): Int
}