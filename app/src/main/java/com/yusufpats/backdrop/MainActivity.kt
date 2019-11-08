package com.yusufpats.backdrop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        initBackdrop()
    }

    private fun initBackdrop() {
        backdrop_layout.frontSheet = front_layer
        backdrop_layout.duration = 200
        backdrop_layout.revealHeight = 300
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_activity_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_filter) {
            backdrop_layout.toggleBackdrop()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
