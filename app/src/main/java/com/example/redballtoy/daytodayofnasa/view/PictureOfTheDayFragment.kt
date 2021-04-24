package com.example.redballtoy.daytodayofnasa.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.example.redballtoy.daytodayofnasa.MainActivity
import com.example.redballtoy.daytodayofnasa.R
import com.example.redballtoy.daytodayofnasa.databinding.MainFragmentBinding
import com.example.redballtoy.daytodayofnasa.model.PictureOfTheDayData
import com.example.redballtoy.daytodayofnasa.viewmodel.PictureOfTheDayViewModel
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.chip.ChipGroup
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import geekbarains.material.ui.chips.ChipsFragment
import java.text.SimpleDateFormat
import java.util.*

class PictureOfTheDayFragment : Fragment() {

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
    private var _bindingMainFragment: MainFragmentBinding? = null
    private val bindingMainFragment get() = _bindingMainFragment!!


    private val viewModel: PictureOfTheDayViewModel by lazy {
        ViewModelProvider(this).get(PictureOfTheDayViewModel::class.java)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bindingMainFragment = MainFragmentBinding.inflate(inflater, container, false)
        return _bindingMainFragment!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBottomSheetBehavior(bindingMainFragment.includedBottomSheet.bottomSheetContainer)
        val inputLayout = bindingMainFragment.inputLayout
        val inputEditText = bindingMainFragment.inputEditText
        inputLayout.setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("https://en.wikipedia.org/wiki/${inputEditText.text.toString()}")
            })
        }
        bindingMainFragment.cgDay.setOnCheckedChangeListener { group, checkedId ->
            setChips(group, checkedId)
        }
        setBottomAppBar(view)
    }

    private fun setChips(group: ChipGroup, checkedId: Int) {
        setDefaultText()
        val date: String
        when (checkedId) {
            bindingMainFragment.chDayBeforeyesterday.id -> {
                bindingMainFragment.chDayBeforeyesterday.text = getChipsData(-2)
                date =getChipsData(-2)
                        viewModel.getData(date)
                        .observe(viewLifecycleOwner, Observer<PictureOfTheDayData> { renderData(it) })
                //Log.d("myLog", viewModel.getData().toString())

            }
            bindingMainFragment.chDayYesterday.id -> {
                bindingMainFragment.chDayYesterday.text = getChipsData(-1)
                date =getChipsData(-1)
                viewModel.getData(date)
                        .observe(viewLifecycleOwner, Observer<PictureOfTheDayData> { renderData(it) })
                //Log.d("myLog", viewModel.getData().toString())
            }
            bindingMainFragment.chDayToday.id -> {
                bindingMainFragment.chDayToday.text = getChipsData(-0)
                //Log.d("myLog", viewModel.getData().toString())
                date =getChipsData(-1)
                viewModel.getData(date)
                        .observe(viewLifecycleOwner, Observer<PictureOfTheDayData> { renderData(it) })
            }
        }
    }

    private fun setDefaultText() {
        bindingMainFragment.chDayBeforeyesterday.text = getText(R.string.day_after_yesterday)
        bindingMainFragment.chDayYesterday.text = getText(R.string.yesterday)
        bindingMainFragment.chDayToday.text = getText(R.string.today)
    }

    private fun getChipsData(dayBefore: Int): String {
        var dt: String = "2021-04-21"
        val sdf: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        val c: java.util.Calendar = Calendar.getInstance()
        c.time = sdf.parse(dt)
        c.add(Calendar.DATE, dayBefore)
        dt = sdf.format(c.time)
        toast(dt)
        return dt
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_bottom_bar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.app_bar_fav -> toast("Favourite")
            R.id.app_bar_settings -> activity?.supportFragmentManager?.beginTransaction()
                ?.add(R.id.container, ChipsFragment())?.addToBackStack(null)?.commit()
            android.R.id.home -> {
                activity?.let {
                    BottomNavigationDrawerFragment().show(it.supportFragmentManager, "tag")
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }


    //parsing data returned by the server
    private fun renderData(data: PictureOfTheDayData) {
        when (data) {

            is PictureOfTheDayData.Success -> {
                val serverResponseData = data.serverResponseData
                val url = serverResponseData.url
                if (url.isNullOrEmpty()) {
                    //showError("Сообщение, что ссылка пустая")
                    toast("Link is empty")
                } else {
                    //showSuccess()
                    val descriptionHeader: TextView =
                        bindingMainFragment.includedBottomSheet.bottomSheetDescriptionHeader
                    descriptionHeader.text = serverResponseData.title
                    val bottomSheetDescription: TextView =
                        bindingMainFragment.includedBottomSheet.bottomSheetDescription
                    bottomSheetDescription.text = serverResponseData.explanation


                    if (url.contains("youtube")) {
                        chooseViaSnackbar(url)
                    }
                    val image_view: EquilateralImageView = bindingMainFragment.imageView
                    image_view.load(url) {
                        //who will manage the life cycle
                        lifecycle(this@PictureOfTheDayFragment)
                        //what to do if an error occurs
                        error(R.drawable.ic_load_error_vector)
                        //where to put all this
                        placeholder(R.drawable.ic_no_photo_vector)
                    }
                }
            }

            is PictureOfTheDayData.Loading -> {
                //showLoading()
            }

            is PictureOfTheDayData.Error -> {
                //showError(data.error.message)
                toast(data.error.message)
            }
        }
    }


    //choice to show youtube video
    private fun chooseViaSnackbar(url: String) {
        Snackbar
            .make(bindingMainFragment.root, "This is video", Snackbar.LENGTH_LONG)
            .setAction("Click to show") {
                startActivity(Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse(url)
                })
            }
            .show()
    }

    private fun setBottomAppBar(view: View) {
        val context = activity as MainActivity
        context.setSupportActionBar(view.findViewById(R.id.bottom_app_bar))
        val fab: FloatingActionButton = view.findViewById(R.id.fab)
        val bottom_app_bar: BottomAppBar = view.findViewById(R.id.bottom_app_bar)
        setHasOptionsMenu(true)
        fab.setOnClickListener {
            if (isMain) {
                isMain = false
                bottom_app_bar.navigationIcon = null
                bottom_app_bar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
                fab.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_back_fab))
                bottom_app_bar.replaceMenu(R.menu.menu_bottom_bar_other_screen)
            } else {
                isMain = true
                bottom_app_bar.navigationIcon =
                    ContextCompat.getDrawable(context, R.drawable.ic_hamburger_menu_bottom_bar)
                bottom_app_bar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
                fab.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_plus_fab))
                bottom_app_bar.replaceMenu(R.menu.menu_bottom_bar)
            }
        }
    }

    private fun setBottomSheetBehavior(bottomSheet: ConstraintLayout) {
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
//                when (newState){
//                    BottomSheetBehavior.STATE_COLLAPSED -> toast("STATE_COLLAPSED")
//                    BottomSheetBehavior.STATE_DRAGGING -> toast("STATE_DRAGGING")
//                    BottomSheetBehavior.STATE_EXPANDED -> toast("STATE_EXPANDED")
//                    BottomSheetBehavior.STATE_HIDDEN -> toast("STATE_HIDDEN")
//                    BottomSheetBehavior.STATE_SETTLING -> toast("STATE_SETTLING")
//                    BottomSheetBehavior.STATE_HALF_EXPANDED-> toast("STATE_HALF_EXPANDED")
//                }

            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
//                toast("onSlide")

            }

        })
    }

    private fun Fragment.toast(string: String?) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).apply {
            setGravity(Gravity.BOTTOM, 0, 250)
            show()
        }
    }

    companion object {
        fun newInstance() = PictureOfTheDayFragment()

        //if we are on the main screen
        private var isMain = true
    }
}

