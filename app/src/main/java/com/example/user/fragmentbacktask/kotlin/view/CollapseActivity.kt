package com.example.user.fragmentbacktask.kotlin.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.user.fragmentbacktask.R
import com.google.android.material.appbar.CollapsingToolbarLayout

class CollapseActivity : AppCompatActivity() {

    lateinit var toolbar: Toolbar
    lateinit var collapsingToolbarLayout: CollapsingToolbarLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collpase_layout)

        toolbar = findViewById(R.id.toolbar)
        collapsingToolbarLayout = findViewById(R.id.toolbar_layout)

        setSupportActionBar(toolbar)
        collapsingToolbarLayout.setTitle("DesignLibrarySample");
    }
}
