package org.fw.relearn_android_kotlin._android._08

import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import androidx.core.app.NotificationCompat
import androidx.core.content.FileProvider
import kotlinx.android.synthetic.main.activity_notification.*
import org.fw.relearn_android_kotlin.R
import java.io.File

class NotificationActivity : AppCompatActivity() {
    private lateinit var manager: NotificationManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)
        manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        //8.0新增api
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
//            val channel = NotificationChannel("normal", "Normal", NotificationManager.IMPORTANCE_DEFAULT)
            val channel = NotificationChannel("import", "Important", NotificationManager.IMPORTANCE_HIGH)//重要通知
            manager.createNotificationChannel(channel)
        }
        btn.setOnClickListener {
            doNotify()
        }

        btn2.setOnClickListener {
            takePhone()
        }

        btn3.setOnClickListener {
            fromAlbum()
        }
    }

    /**
     * 从相册选择
     */
    private fun fromAlbum(){
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "image/*"
        startActivityForResult(intent, 2)
    }

    lateinit var imageUri: Uri
    lateinit var outputImage: File
    /**
     * 照相
     * 1 应用关联缓存目录： sd卡中专门用于存放当前应用缓存数据的位置，getExternalCacheDir()可获取
     *   因为从6.0开始 读写SD卡要使用作用域存储才行。
     *
     */
    private fun takePhone(){
        outputImage = File(externalCacheDir, "output_image.jpg")
        if(outputImage.exists()){
            outputImage.delete()
        }
        outputImage.createNewFile()

        //7.0开始，直接使用本地真实路径的Uri是不安全的，抛异常
        imageUri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            FileProvider.getUriForFile(this, "com.fuwa.fileprovider", outputImage)
        }else{
            Uri.fromFile(outputImage)
        }
        val intent = Intent("android.media.action.IMAGE_CAPTURE")
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        startActivityForResult(intent, 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            //拍照
            1 -> {
                if (resultCode == Activity.RESULT_OK){
                    val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(imageUri))
                    imageView.setImageBitmap(rotateRequired(bitmap))
                }
            }
            //从相册选择
            2 ->{
                if (resultCode == Activity.RESULT_OK){
                    data?.data?.let {
                        val bitmap = contentResolver.openFileDescriptor(it, "r")?.use { it2 ->
                            BitmapFactory.decodeFileDescriptor(it2.fileDescriptor)
                        }
                        imageView.setImageBitmap(bitmap)
                    }
                }
            }
        }
    }

    /**
     * 手机打开摄像头时应该是横屏，因此回到竖屏需要旋转。 需要旋转时，进行旋转相应角度
     */
    private fun  rotateRequired(bitmap: Bitmap): Bitmap{
        val exif = ExifInterface(outputImage.path)
        var orientation =
            exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)
        return when(orientation){
            ExifInterface.ORIENTATION_ROTATE_90 -> rotateBitmap(bitmap, 90)
            ExifInterface.ORIENTATION_ROTATE_180 -> rotateBitmap(bitmap, 180)
            ExifInterface.ORIENTATION_ROTATE_270 -> rotateBitmap(bitmap, 270)
            else -> bitmap
        }
    }
    private fun rotateBitmap(bitmap: Bitmap, degree: Int): Bitmap{
        val matrix = Matrix()
        matrix.postRotate(degree.toFloat())
        val rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
        bitmap.recycle()
        return rotatedBitmap
    }


    /**
     * 弹出通知
     */
    private fun doNotify(){
        val intent = Intent(this, TestActivity::class.java)
        val pi = PendingIntent.getActivity(this, 0, intent, 0)

        var notification = NotificationCompat
//                .Builder(this, "normal")
            .Builder(this, "import") //重要通知
            .setContentTitle("this is title")
            //单行
//                .setContentText("this is text setSmallIcon(R.drawable.ic_launcher_foreground) setSmallIcon(R.drawable.ic_launcher_foreground) setSmallIcon(R.drawable.ic_launcher_foreground) setSmallIcon(R.drawable.ic_launcher_foreground)")
            //可以换行
//                .setStyle(NotificationCompat.BigTextStyle().bigText("this is text setSmallIcon(R.drawable.ic_launcher_foreground) setSmallIcon(R.drawable.ic_launcher_foreground) setSmallIcon(R.drawable.ic_launcher_foreground) setSmallIcon(R.drawable.ic_launcher_foreground)"))
            //显示大图
            .setStyle(NotificationCompat.BigPictureStyle().bigPicture(
                BitmapFactory.decodeResource(resources, R.drawable.big_image)
            ))
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setLargeIcon(
                BitmapFactory.decodeResource(
                    resources,
                    R.drawable.ic_launcher_foreground
                )
            )
            .setAutoCancel(true) //自动关闭
            .setContentIntent(pi)//跳转
            .build()
        manager.notify(1, notification)

        //其他类调用该方法 关闭通知
//            manager.cancel(1)
    }
}