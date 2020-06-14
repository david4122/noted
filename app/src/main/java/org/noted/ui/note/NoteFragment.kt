package org.noted.ui.note

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import org.noted.R
import org.noted.databinding.FragmentNoteBinding

class NoteFragment : Fragment() {

    val viewModel: NoteViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentNoteBinding>(inflater, R.layout.fragment_note, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        binding.root.findViewById<Button>(R.id.cancel).setOnClickListener { binding.root.findNavController().popBackStack() }
        binding.root.findViewById<Button>(R.id.save).setOnClickListener { viewModel.save() }

        return binding.root
    }

    override fun onStop() {
        super.onStop()

        val inputm = requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputm.hideSoftInputFromWindow(requireView().windowToken, 0)
    }
}