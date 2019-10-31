package com.egco428.ex15_basicsqlite

import android.app.ListActivity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import kotlin.random.Random


class MainActivity : ListActivity() {
    private var dataSource:CommentDataSource? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dataSource = CommentDataSource(this)
        dataSource!!.open()
        val values = dataSource!!.allComments
        val adapter = ArrayAdapter<Comment>(this,android.R.layout.simple_list_item_1,values)
        setListAdapter(adapter)
    }

    fun OnClick(view: View){
        val adapter = getListAdapter() as ArrayAdapter<Comment>
        var comment: Comment? = null
        when(view.getId()){
            R.id.add->{
                val comments = arrayOf("Very Good", "Cool", "Not too bad","Nice")
                var nextInt = Random.nextInt(3)
                comment = dataSource!!.createComment(comments[nextInt])
                adapter.add(comment)
            }
            R.id.delete-> if(getListAdapter().getCount() > 0){
                comment = getListAdapter().getItem(0) as Comment
                dataSource!!.deleteComment(comment!!)
                adapter.remove(comment)
            }
        }
        adapter.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()
        dataSource!!.open()
    }

    override fun onPause() {
        super.onPause()
        dataSource!!.close()
    }
}

