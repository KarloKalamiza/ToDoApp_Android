package hr.algebra.todoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.recyclerview.widget.LinearLayoutManager
import hr.algebra.todoapp.databinding.ActivityMainBinding
import hr.algebra.todoapp.model.Item
import hr.algebra.todoapp.model.ItemList

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setAdapter()
        setUpListeners()
    }

    private lateinit var items: ItemList
    private lateinit var itemAdapter: ItemAdapter

    private fun setAdapter() {
        items = ItemList(this)
        itemAdapter = ItemAdapter(items)
        binding.rvItems.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = itemAdapter
        }
    }

    private fun setUpListeners() {
        binding.btnAdd.setOnClickListener {
            addItem()
        }
        binding.etItem.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0?.endsWith("\n") == true) addItem()
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })
    }

    private fun addItem() {
        if (binding.etItem.text.toString().isNotBlank()){
            items.add(Item(binding.etItem.text.toString().trim()))
            itemAdapter.notifyItemInserted(items.size - 1)
            binding.etItem.text.clear()
        }
    }

    override fun onPause() {
        super.onPause()
        items.saveInFile()
    }

    override fun onResume() {
        super.onResume()
        if (items.isEmpty()) items.readFromFile()
    }
}