package com.cursosant.mviarch.updateModule.view

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.DialogInterface.OnShowListener
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.cursosant.mviarch.commonModule.utils.Constants
import com.cursosant.mviarch.R
import com.cursosant.mviarch.commonModule.entities.Wine
import com.cursosant.mviarch.WineApplication
import com.cursosant.mviarch.databinding.FragmentDialogUpdateBinding
import com.cursosant.mviarch.favouriteModule.model.FavouriteState
import com.cursosant.mviarch.updateModule.UpdateViewModel
import com.cursosant.mviarch.updateModule.UpdateViewModelFactory
import com.cursosant.mviarch.updateModule.intent.UpdateIntent
import com.cursosant.mviarch.updateModule.model.RoomDatabase
import com.cursosant.mviarch.updateModule.model.UpdateRepository
import com.cursosant.mviarch.updateModule.model.UpdateState
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/****
 * Project: Wines
 * From: com.cursosant.wines
 * Created by Alain Nicolás Tello on 06/02/24 at 20:23
 * All rights reserved 2024.
 *
 * All my Udemy Courses:
 * https://www.udemy.com/user/alain-nicolas-tello/
 * And Frogames formación:
 * https://cursos.frogamesformacion.com/pages/instructor-alain-nicolas
 *
 * Coupons on my Website:
 * www.alainnicolastello.com
 ***/
class UpdateDialogFragment : DialogFragment(), OnShowListener {

    private var _binding: FragmentDialogUpdateBinding? = null
    private val binding get() = _binding!!

    private var _wineId = -1.0
    private lateinit var wine: Wine

    private var onUpdateListener: () -> Unit = {}

    private lateinit var vm: UpdateViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            val id = it.getDouble(Constants.ARG_ID, 0.0)
            _wineId = id
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = FragmentDialogUpdateBinding.inflate(layoutInflater)//LayoutInflater.from(requireContext()))

        val builder = AlertDialog.Builder(requireContext()).apply {
            setTitle(R.string.dialog_update_title)
            setPositiveButton(R.string.dialog_update_ok, null)
            setNegativeButton(R.string.dialog_update_cancel, null)
            setView(binding.root)
        }

        val dialog = builder.create()
        dialog.setOnShowListener(this)

        return dialog
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?) = binding.root

    override fun onShow(dialogInterface: DialogInterface?) {
        val dialog = dialog as? AlertDialog
        dialog?.let {
            val positiveButton = it.getButton(DialogInterface.BUTTON_POSITIVE)
            val negativeButton = it.getButton(DialogInterface.BUTTON_NEGATIVE)
            positiveButton.setOnClickListener {
                showProgress(true)
                lifecycleScope.launch(Dispatchers.IO) {
                    wine.rating.average = binding.rating.rating.toString()
                    vm.channel.send(UpdateIntent.UpdateWine(wine))
//                    lifecycleScope.launch(Dispatchers.IO) {
//                        val result = WineApplication.database.wineDao().updateWine(wine)
//                        withContext(Dispatchers.Main) {
//                            if (result == 0) {
//                                Snackbar.make(binding.root, R.string.room_save_fail, Snackbar.LENGTH_SHORT).show()
//                            } else {
//                                //Snackbar.make(binding.root, R.string.room_save_success, Snackbar.LENGTH_SHORT).show()
//                                showMsg(R.string.room_save_success)
//                                dismiss()
//                            }
//                            showProgress(false)
//                        }
//                    }
                }
            }
            negativeButton.setOnClickListener { dismiss() }
        }
        setupViewModel()
        setpObservers()
        getWineById()
    }

    private fun setupViewModel() {
        vm = ViewModelProvider(this, UpdateViewModelFactory(UpdateRepository(RoomDatabase())))[UpdateViewModel::class.java]
    }

    private fun showMsg(msgRes: Int) {
        Toast.makeText(requireContext(), msgRes, Toast.LENGTH_SHORT).show()
    }

    private fun showProgress(isVisible: Boolean) {
        binding.contentProgress.root.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    private fun getWineById() {
        lifecycleScope.launch(Dispatchers.IO) {
            vm.channel.send(UpdateIntent.RequestwINE(_wineId))
//            val result = WineApplication.database.wineDao().getWineById(_wineId)
//            wine = result
//            withContext(Dispatchers.Main) {
//                setupUI()
//            }
        }
    }


    private fun setpObservers(){

        lifecycleScope.launch {
            vm.state.collect { state ->

                when(state) {

                    is UpdateState.Init -> {}
                    is UpdateState.ShowProgress -> showProgress(true)
                    is UpdateState.HideProgress -> showProgress(false)
                    is UpdateState.Fail -> showMsg(state.msgRes)
                    is UpdateState.UpdateWineSuccess -> {
                        showMsg(R.string.room_save_success)
                        dismiss()
                    }
                    is UpdateState.RequestWineSuccess -> {
                        wine = state.wine
                        setupUI()
                    }
                }


            }
        }

    }

    private fun setupUI() {
        binding.tvWine.text = wine.wine
        binding.rating.rating = wine.rating.average.toFloat()
    }

    fun setOnUpdateListener(block: () -> Unit) {
        onUpdateListener = block
    }

    override fun onDismiss(dialog: DialogInterface) {
        onUpdateListener()
        super.onDismiss(dialog)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}