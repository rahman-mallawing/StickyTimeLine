package xyz.sangcomz.stickytimeline

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import xyz.sangcomz.stickytimelineview.ItemDecoration.VerticalSectionItemDecoration
import xyz.sangcomz.stickytimelineview.model.SectionInfo

class MainActivity : AppCompatActivity() {

    val icFinkl: Drawable? by lazy {
        AppCompatResources.getDrawable(this@MainActivity, R.drawable.ic_finkl)
    }
    val icBuzz: Drawable? by lazy {
        AppCompatResources.getDrawable(this@MainActivity, R.drawable.ic_buzz)
    }
    val icWannaOne: Drawable? by lazy {
        AppCompatResources.getDrawable(this@MainActivity, R.drawable.ic_wannaone)
    }
    val icGirlsGeneration: Drawable? by lazy {
        AppCompatResources.getDrawable(this@MainActivity, R.drawable.ic_girlsgeneration)
    }
    val icSolo: Drawable? by lazy {
        AppCompatResources.getDrawable(this@MainActivity, R.drawable.ic_solo)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initVerticalRecyclerView()
        initHorizontalRecyclerView()
    }

    private fun initVerticalRecyclerView() {
        val singerList = getSingerList()
        vertical_recycler_view.adapter = SingerAdapter(
            layoutInflater,
            singerList,
            R.layout.recycler_vertical_row
        )

        //Currently only LinearLayoutManager is supported.
        vertical_recycler_view.layoutManager = LinearLayoutManager(
            this,
            RecyclerView.VERTICAL,
            false
        )

        vertical_recycler_view.addItemDecoration(getSectionCallback(singerList))
    }

    private fun initHorizontalRecyclerView() {
        val singerList = getSingerList()
        horizontal_recycler_view.adapter = SingerAdapter(
            layoutInflater,
            singerList,
            R.layout.recycler_horizontal_row
        )

        //Currently only LinearLayoutManager is supported.
        horizontal_recycler_view.layoutManager = LinearLayoutManager(
            this,
            RecyclerView.HORIZONTAL,
            false
        )

        horizontal_recycler_view.addItemDecoration(getSectionCallback(singerList))
    }

    //Get data method
    private fun getSingerList(): List<Singer> = SingerRepo().singerList


    //Get SectionCallback method
    private fun getSectionCallback(singerList: List<Singer>): VerticalSectionItemDecoration.SectionCallback {
        return object : VerticalSectionItemDecoration.SectionCallback {
            //In your data, implement a method to determine if this is a section.
            override fun isSection(position: Int): Boolean =
                singerList[position].debuted != singerList[position - 1].debuted

            //Implement a method that returns a SectionHeader.
            override fun getSectionHeader(position: Int): SectionInfo? {
                val singer = singerList[position]
                val dot: Drawable? = when (singer.group) {
                    "FIN.K.L" -> {
                        icFinkl
                    }
                    "Girls' Generation" -> {
                        icGirlsGeneration
                    }
                    "Buzz" -> {
                        icBuzz
                    }
                    "Wanna One" -> {
                        icWannaOne
                    }
                    else -> icSolo
                }
                return SectionInfo(singer.debuted, singer.group, dot)
            }

        }
    }
}
