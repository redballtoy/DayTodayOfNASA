package com.example.redballtoy.daytodayofnasa.ui.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.redballtoy.daytodayofnasa.databinding.FragmentChipsBinding
import com.example.redballtoy.daytodayofnasa.ui.activities.MainActivity
import com.google.android.material.chip.Chip

class ChipsFragment : Fragment() {

    private var _binding: FragmentChipsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChipsBinding.inflate(inflater, container, false)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.chipGroup.setOnCheckedChangeListener { chipGroup, position ->
            chipGroup.findViewById<Chip>(position)?.let {
                Toast.makeText(context, "Выбран ${it.text}", Toast.LENGTH_SHORT).show()
            }
        }

       binding.chipClose.setOnCloseIconClickListener {
            Toast.makeText(
                    context,
                    "Close is Clicked",
                    Toast.LENGTH_SHORT
            ).show()
        }

        binding.cgThemeSelected.setOnCheckedChangeListener {chipGroups, position ->
            Toast.makeText(context,"position:$position", Toast.LENGTH_SHORT).show()
            var themeName = "Light"
            when(position){
                binding.chLightTheme.id -> themeName="Light"
                binding.chDarkTheme.id -> themeName="Dark"
                binding.chIndigoTheme.id -> themeName="Indigo"
                binding.chPinkTheme.id -> themeName="Pink"
            }
            setThemeToSharedPreferences(themeName)
        }
    }

    private fun setThemeToSharedPreferences(themeName: String) {
        Toast.makeText(this.activity, themeName, Toast.LENGTH_SHORT).show()
        val preferences = this.activity?.getSharedPreferences("Theme", Context.MODE_PRIVATE)
        val editor = preferences?.edit()
        editor?.putString("ThemeName",themeName)
        editor?.apply()
        val intent = Intent(this.context, MainActivity::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }
}
