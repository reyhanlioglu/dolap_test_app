package com.example.dolaptestapp.product_detail.view


import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.dolaptestapp.R
import com.example.dolaptestapp.databinding.FragmentProductDetailBinding
import com.example.dolaptestapp.product_detail.viewmodel.ProductDetailViewModel
import kotlinx.android.synthetic.main.fragment_product_detail.*



class ProductDetailFragment : Fragment() {

    private lateinit var dataBinding: FragmentProductDetailBinding
    private lateinit var viewModel: ProductDetailViewModel
    private val REFRESH_TIME_PERIOD = 20
    private var isTimerRunning = false


    private val timer = object: CountDownTimer(20000, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            isTimerRunning = true
            // Update progress bar
            progressBarTime.text = (millisUntilFinished/1000).toString()
            progressBar.progress -= 1
        }

        override fun onFinish() {
            isTimerRunning = false
            // Social details should be retreived from api in every 20 seconds
            viewModel.fetchSocialDetailsFromApi()

            // Reset the progress bar and timer
            progressBar.progress = REFRESH_TIME_PERIOD
            this.start()
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // ViewModel initialization
        viewModel = ViewModelProvider(this).get(ProductDetailViewModel::class.java)

        // Data binding
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_product_detail, container, false)
        // Setting the viewModel
        dataBinding.viewModel = viewModel

        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.fetchProductDetailsFromApi()
        viewModel.fetchSocialDetailsFromApi()


        refreshLayout.setOnRefreshListener {
            loadingState()
            viewModel.fetchProductDetailsFromApi()
            viewModel.fetchSocialDetailsFromApi()
            refreshLayout.isRefreshing = false
        }

        observeViewModel()

    }


    private fun observeViewModel(){
        viewModel.productDetails.observe(viewLifecycleOwner, Observer { productDetails ->

            productDetails?.let {
                // Update UI with viewModel
                dataBinding.viewModel = viewModel
                println("Product detail's observer is called")
            }
        })

        viewModel.socialDetails.observe(viewLifecycleOwner, Observer { socialDetails ->
            socialDetails?.let {details->
                // Update UI with viewModel
                dataBinding.viewModel = viewModel
                println("Social detail's observer is called")
            }
        })

        viewModel.loadingStatus.observe(viewLifecycleOwner, Observer {
            it?.let {isLoading->
                    // LOADING STATE
                    if(isLoading && !viewModel.errorStatus.value!!){
                        loadingState()
                    }
                    // LOADED STATE
                    else if(!isLoading && !viewModel.errorStatus.value!!){
                        loadedState()
                    }
                    // ERROR STATE
                    if(viewModel.errorStatus.value!!){
                        errorState()
                    }
                }
        })

        viewModel.errorStatus.observe(viewLifecycleOwner, Observer {
            it?.let { errorStatus->
                if(errorStatus){
                    errorState()
                }
            }
        })

    }

    private fun loadingState(){
        println("LOADING STATE")
        loadingProgressBar.visibility = View.VISIBLE
        productDetailContainer.visibility = View.GONE
        errorContainer.visibility = View.GONE
    }

    private fun loadedState(){
        println("LOADED STATE")
        loadingProgressBar.visibility = View.GONE
        productDetailContainer.visibility = View.VISIBLE
        errorContainer.visibility = View.GONE
        if(!isTimerRunning)
            timer.start()
    }

    private fun errorState(){
        println("ERROR STATE")
        loadingProgressBar.visibility = View.GONE
        productDetailContainer.visibility = View.GONE
        errorContainer.visibility = View.VISIBLE
    }

}
