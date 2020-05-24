package com.example.user.fragmentbacktask.kotlin.view

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.provider.Settings
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.user.fragmentbacktask.R
import com.example.user.fragmentbacktask.TestApplication
import com.example.user.fragmentbacktask.kotlin.adapter.ProjectAdapter
import com.example.user.fragmentbacktask.kotlin.base.BaseActivity
import com.example.user.fragmentbacktask.kotlin.model.User
import com.example.user.fragmentbacktask.kotlin.utils.CustomTextWatcher
import com.example.user.fragmentbacktask.kotlin.utils.click
import com.example.user.fragmentbacktask.kotlin.utils.ext.startActivity
import com.example.user.fragmentbacktask.kotlin.utils.extraDelegate
import com.example.user.fragmentbacktask.utils.StringUtils.toast
import com.example.user.fragmentbacktask.utils.status.StatusBarUtils
import com.example.user.fragmentbacktask.view.GeneralDialog
import com.irozon.sneaker.Sneaker
import com.orhanobut.logger.Logger

//import kotlinx.android.synthetic.main.activity_project_list.*

class ProjectListActivity : BaseActivity() {

    private val user: User? by extraDelegate("user")
    private val content: String? by extraDelegate("string")
    lateinit var adapter: ProjectAdapter
    lateinit var scContainer: NestedScrollView
    lateinit var tvError: TextView
    lateinit var tvSuccess: TextView
    lateinit var rv_list: RecyclerView
    lateinit var et_keyword: EditText
    lateinit var btn_jump: Button

    private var mList: List<String>? = listOf()
    val CHECK_OP_NO_THROW: String = "checkOpNoThrow"
    val OP_POST_NOTIFICATION: String = "OP_POST_NOTIFICATION"

    val handler = object : Handler() {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            Logger.d("receive message")
            tvError.text = "leak message"
        }
    }

    companion object {
        private const val ID: String = "id"
        private const val CONTENT: String = "id"

        fun getIntent(context: Context, id: Int, content: String): Intent {
            return Intent(context, ProjectListActivity::class.java).apply {
                putExtra(ID, id)
                putExtra(CONTENT, content)
            }
        }
    }

    override fun provideLayoutId(): Int {
        return R.layout.activity_project_list
    }

    fun hasEmpty(): Boolean {
        mList?.forEach {
            if (TextUtils.isEmpty(it)) {
                return true
            }
        }
        return false
    }

    override fun onDestroy() {
        super.onDestroy()
        TestApplication.getRefWatcher(this).watch(this)
    }

    override fun initCreate(savedInstanceState: Bundle?) {
        val id: Int = intent.getIntExtra(ID, 0)

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
//            window.statusBarColor = Color.TRANSPARENT
//        }

        rv_list.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
            }
        })

        handler.postDelayed({
            Logger.d("sleep some time")
        }, 10000)

        val bundle = intent.getBundleExtra("bundle")
        val extra = bundle["extra"]
        Logger.d("bundle + $extra")

        initView()

        StatusBarUtils.setStatusBarColor(this, ContextCompat.getColor(mContext, R.color.white), true)

//        StatusBarUtils.setTranslucentForImageView(this, scContainer)

        val mLayoutManger = LinearLayoutManager(mContext, RecyclerView.VERTICAL, false)
        rv_list.layoutManager = mLayoutManger


//        tv_content?.setText("content:" + content)
        user?.run {
            Logger.e("提示信息！！！")
//            tv_extra.text = "user" + name + "//" + password
        }

        val notifyEnable = isNotificationEnabled(mContext)
        Logger.d("通知是否开启：${notifyEnable}")
        if (notifyEnable) {

        }

        et_keyword.addTextChangedListener(makeTextWatcher(
                30,
                afterTextWatcher = { s, number ->
                    s?.run {
                        if (length > number) {
                            val currentInput = subSequence(0, number)
                            et_keyword.setText(currentInput)
                            et_keyword.setSelection(currentInput.length)
                            toast(mContext, "输入的内容不能超过${number}个字符")
                        }
                    }
                }
        ))
        et_keyword.addTextChangedListener(object : CustomTextWatcher(30) {
            override fun afterTextChanged(s: Editable?) {
                super.afterTextChanged(s)
                limitNum
            }
        })

        com.example.user.fragmentbacktask.kotlin.utils.dsl.toast {
            text = "一个toast 提示"
            showError = true
        }

        et_keyword.click {
            com.example.user.fragmentbacktask.kotlin.utils.ext.toast("一个点击toast 提示")
        }
        tvSuccess.click {
            val generalDialog = GeneralDialog.Builder(mContext).setMessage("弹窗消息").create()
            generalDialog.show()
        }
        tvError.click {
            Sneaker.with(this).setMessage("This is the error message").sneakError()
        }

        btn_jump.click {
            startActivity<NewTestActivity>()
        }
    }

    private fun initView() {
        scContainer = findViewById(R.id.sc_container)
        rv_list = findViewById(R.id.rv_list)
        tvError = findViewById(R.id.tv_error)
        tvSuccess = findViewById(R.id.tv_success)
        et_keyword = findViewById(R.id.et_keyword)
        btn_jump = findViewById(R.id.btn_jump)
    }

    private fun isNotificationEnabled(context: Context): Boolean {

        val mAppops = context.getSystemService(Context.APP_OPS_SERVICE)
        val appInfo = context.applicationInfo
        val pkg = context.applicationContext.packageName
        val uid = appInfo.uid

        val manager = NotificationManagerCompat.from(context)
        val opened = manager.areNotificationsEnabled()
        if (!opened) {
            val localIntent = Intent().apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
                    putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
                    putExtra(Settings.EXTRA_CHANNEL_ID, applicationInfo.uid)
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                    putExtra("app_package", packageName)
                    putExtra("app_uid", applicationInfo.uid)
                }
            }
            startActivity(localIntent)
//            localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//            localIntent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
//            localIntent.data = Uri.fromParts("package", mContext.packageName, null)
//            startActivity(localIntent)
        }
        return opened
    }

    private fun makeTextWatcher(
            limitNum: Int,
            afterTextWatcher: ((s: Editable?, number: Int) -> Unit)? = null,
            beforeTextChanged: ((s: CharSequence?, start: Int, count: Int, after: Int) -> Unit)? = null,
            onTextChanged: ((s: CharSequence?, start: Int, before: Int, count: Int) -> Unit)? = null
    ) = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            afterTextWatcher?.invoke(s, limitNum)
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            beforeTextChanged?.invoke(s, start, count, after)
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            onTextChanged?.invoke(s, start, before, count)
        }
    }

}
