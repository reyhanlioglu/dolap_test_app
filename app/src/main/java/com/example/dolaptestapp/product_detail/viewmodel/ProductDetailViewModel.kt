package com.example.dolaptestapp.product_detail.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dolaptestapp.product_detail.model.ProductDetails
import com.example.dolaptestapp.product_detail.model.SocialDetails
import com.example.dolaptestapp.product_detail.model.api_product.ProductApiService
import com.example.dolaptestapp.product_detail.model.api_social.SocialApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class ProductDetailViewModel: ViewModel() {
    val socialDetails = MutableLiveData<SocialDetails>()
    val productDetails = MutableLiveData<ProductDetails>()
    val errorStatus = MutableLiveData<Boolean>()
    val loadingStatus = MutableLiveData<Boolean>()

    private var loadingBasic = true
    private var loadingSocial = true

    private val productDetailService = ProductApiService()
    private val socialDetailService = SocialApiService()

    // Used to prevent memory leaks
    private val disposable = CompositeDisposable()


    init {
        errorStatus.value = false
        loadingStatus.value = true
    }


    fun fetchProductDetailsFromApi(){
        loadingBasic = true

        disposable.add(
            productDetailService.getProductDetails()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<ProductDetails>(){
                    override fun onSuccess(pDetails: ProductDetails) {
                        println("Product details: $pDetails")
                        productDetails.value = pDetails
                        errorStatus.value = false

                        loadingBasic = false
                        if(!loadingBasic && !loadingSocial){
                            loadingStatus.value = false
                        }
                    }

                    override fun onError(e: Throwable) {
                        println("On error called")
                        errorStatus.value = true
                        loadingStatus.value = false
                        e.printStackTrace()
                    }
                })
        )
    }

    fun fetchSocialDetailsFromApi(){
        loadingSocial = true

        disposable.add(
            socialDetailService.getSocialDetails()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<SocialDetails>(){
                    override fun onSuccess(sDetails: SocialDetails) {
                        println("Social details: $sDetails")
                        socialDetails.value = sDetails
                        errorStatus.value = false

                        loadingSocial = false
                        if(!loadingBasic && !loadingSocial){
                            loadingStatus.value = false
                        }
                    }

                    override fun onError(e: Throwable) {
                        println("On error called")
                        errorStatus.value = true
                        loadingStatus.value = false
                        e.printStackTrace()
                    }
                })
        )
    }


    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }


}