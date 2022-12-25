package fastcampus.aop.part2.chapter03

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.widget.addTextChangedListener

class DiaryActivity : AppCompatActivity() {

    private val handler = Handler(Looper.getMainLooper())

    private val diaryEditText: EditText by lazy {
        findViewById<EditText>(R.id.diaryEditText)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary)

        initDetailEditText()
        threadTest()



    }

    private fun threadTest() {
        val num = 3
        var count = 0
        while(count < num) {
            // fast_campers
            val t = Thread(Runnable {
                Log.d("DiaryActivity", "HELLO")
            }).start()

            // GPT3
            val thread = Thread.currentThread()
            // Get the thread's ID
            val threadId = thread.id
            // Get the thread's name
            val threadName = thread.name
            // Leave a log message with the thread information
            Log.d("Thread", "Thread ID: $threadId Thread Name: $threadName")
            Thread.sleep(1000);
            count ++
        }
    }

    private fun initDetailEditText() {
        val detail = getSharedPreferences("diary", Context.MODE_PRIVATE).getString("detail", "")
        diaryEditText.setText(detail)

        val runnable = Runnable {
            getSharedPreferences("diary", Context.MODE_PRIVATE).edit(true) {
                putString("detail", diaryEditText.text.toString())
            }
        }


        diaryEditText.addTextChangedListener {
            Log.d("DiaryActivity", "text Changed :: $it")
            handler.removeCallbacks(runnable) //
            handler.postDelayed(runnable, 500) // 0.5 sec
        }
    }
}