package org.fw.relearn_android_kotlin._android._07

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_test_content_provider.*
import org.fw.relearn_android_kotlin.R


/**
 *    author : 进击的巨人
 *    e-mail : zhouffan@qq.com
 *    date   : 2020/12/29 21:17
 *    desc   :
 *
 *    1 android6.0 系统中引入了  运行时权限     (java.lang.SecurityException: Permission Denial: starting Intent { act=android.intent.action.CALL dat=tel:xxxxx cmp=com.android.server.telecom/.components.UserCallActivity } )
 *    2 原则上 同意了某个权限，同组 的其他权限也会被自动授权
 *    同组 如：LOCATION   -->access_fine_location
 *                       -->access_coarse_location
 *                       -->access_background_location
 *    3 6.0及以上使用危险权限， 必须进行运行时权限处理
 *
 *    version: 1.0
 */
class TestPermissionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_content_provider)

        btn.setOnClickListener {
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CALL_PHONE), 1)
            }else{
                call()
            }
        }
    }

    private fun call(){
        val intent = Intent(Intent.ACTION_CALL)
        intent.data = Uri.parse("tel:10086")
        startActivity(intent)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            1 -> {
                if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    call()
                }else{
                    Toast.makeText(this, "denied the permission", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    /**
     * 1 contentResolver 不接收表名参数，使用Uri参数代替。 authority（不同的应用程序）和path（一个应用不同表做区分）
     * content://com.fuwa.app.provider/table1     哪个程序的哪个表
     * content://com.fuwa.app.provider/table2
     *
     */

}