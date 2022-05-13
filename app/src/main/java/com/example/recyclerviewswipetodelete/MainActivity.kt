package com.example.recyclerviewswipetodelete

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.*
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var arrayList = ArrayList<ModelClass>()
    private var data = arrayOf("a", "b", "c", "d", "e", "f", "g", "h", "i")
    private var myAdapter: MyAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myAdapter = MyAdapter(this, getData())
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                this,
                LinearLayoutManager.VERTICAL
            )
        )
        recyclerView.adapter = myAdapter
        setSwipeToDelete()
    }

    private fun setSwipeToDelete() {
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val modelClass = arrayList[viewHolder.adapterPosition]
                val position = viewHolder.adapterPosition
                arrayList.removeAt(viewHolder.adapterPosition)
                myAdapter!!.notifyItemRemoved(viewHolder.adapterPosition)
                Snackbar.make(recyclerView!!, modelClass.name, Snackbar.LENGTH_LONG).setAction(
                    "Undo"
                ) {
                    arrayList.add(position, modelClass)
                    myAdapter!!.notifyItemInserted(position)
                }.show()
            }
        }).attachToRecyclerView(recyclerView)
    }

    private fun getData(): ArrayList<ModelClass> {
        for (i in data.indices) {
            arrayList.add(ModelClass(data[i]))
        }
        return arrayList
    }
}