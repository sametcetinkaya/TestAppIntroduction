package com.sametcetinkaya.artbooktesting.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.RequestManager
import com.sametcetinkaya.artbooktesting.R
import com.sametcetinkaya.artbooktesting.databinding.FragmentArtDetailsBinding
import com.sametcetinkaya.artbooktesting.util.Status
import com.sametcetinkaya.artbooktesting.viewmodel.ArtViewModel
import javax.inject.Inject


class ArtDetailsFragment @Inject constructor(
     val glide : RequestManager
): Fragment(R.layout.fragment_art_details) {

    private var fragmentBinding : FragmentArtDetailsBinding? = null

    lateinit var viewModel : ArtViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(ArtViewModel::class.java)

        val binding = FragmentArtDetailsBinding.bind(view)
        fragmentBinding = binding

/*        binding.artImageView.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_artDetailsFragment_to_imageApiFragment)
        }*/
        subscribeToObservers()

        binding.artImageView.setOnClickListener {
            findNavController().navigate(
                R.id.action_artDetailsFragment_to_imageApiFragment
            )
        }

        val callBack = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(callBack)

        binding.saveButton.setOnClickListener {
            viewModel.makeArt(binding.nameText.text.toString(), binding.artistText.text.toString(),binding.yearText.text.toString())
        }

    }

    private fun subscribeToObservers(){
        viewModel.selectedImageUrl.observe(viewLifecycleOwner, Observer { url ->
            fragmentBinding?.let {
                glide.load(url).into(it.artImageView)
            }
        })

        viewModel.insertArtMessage.observe(viewLifecycleOwner, Observer {
            when(it.status){
                Status.SUCCESS ->{
                    Toast.makeText(requireContext(), "Success", Toast.LENGTH_LONG).show()
                    findNavController().popBackStack()
                    viewModel.resetInsertArtMsg()
                }

                Status.ERROR ->{
                    Toast.makeText(requireContext(), it.message ?: "Error", Toast.LENGTH_LONG).show()
                }

                Status.LOADING ->{

                }
            }
        })
    }

    override fun onDestroyView() {
        fragmentBinding = null
        super.onDestroyView()
    }


}